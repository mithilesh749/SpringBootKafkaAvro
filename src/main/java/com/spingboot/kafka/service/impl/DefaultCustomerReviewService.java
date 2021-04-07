package com.spingboot.kafka.service.impl;

import com.spingboot.kafka.models.CustomerReviewModel;
import com.spingboot.kafka.models.LanguageModel;
import com.spingboot.kafka.models.ProductModel;
import com.spingboot.kafka.models.UserModel;
import com.spingboot.kafka.repository.CustomerReviewRepo;
import com.spingboot.kafka.repository.SequenceGeneratorRepo;
import com.spingboot.kafka.service.CustomerReviewService;
import com.spingboot.kafka.util.ServicesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DefaultCustomerReviewService implements CustomerReviewService
{
	private CustomerReviewRepo customerReviewDao;
	private SequenceGeneratorRepo dbSeq;

	@Autowired
	public DefaultCustomerReviewService(CustomerReviewRepo customerReviewDao, SequenceGeneratorRepo dbSeq)
	{
		this.customerReviewDao = customerReviewDao;
		this.dbSeq = dbSeq;
	}

	@Override
	public CustomerReviewModel createCustomerReview(final Double rating, final String headline, final String comment,
													final ProductModel product, final UserModel user)
	{
		final CustomerReviewModel review = new CustomerReviewModel();
		review.setId(dbSeq.generateSequence(CustomerReviewModel.SEQUENCE_NAME));
		review.setRating(rating);
		review.setHeadline(headline);
		review.setComment(comment);
		review.setProduct(product);
		review.setUser(user);
		customerReviewDao.save(review);
		return review;
	}

	@Override
	public void updateCustomerReview(final CustomerReviewModel review, UserModel user, ProductModel product)
	{
		customerReviewDao.save(review);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProduct(final ProductModel product)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		return customerReviewDao.getAllReviews(product);
	}

	@Override
	public Double getAverageRating(final ProductModel product)
	{
		return customerReviewDao.getAverageRating(product);
	}

	@Override
	public Integer getNumberOfReviews(final ProductModel product)
	{
		return customerReviewDao.getNumberOfReviews(product);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProductAndRating(ProductModel product, Double ratingFrom, Double ratingTo) {
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		// Checking for the ratingFrom Value if not present setting it to 0, as it is the minimum Rating limit
		if (ratingFrom == null) {
			ratingFrom = 0.0d; // Considering Lowest rating possible
		}
		List<CustomerReviewModel> ratings = null;
		// Based on the value of the ratingTo trigger the query to get the list of ratings
		if (ratingTo == null) {
			ratings = customerReviewDao.getReviewsForProductAndFromRating(product, ratingFrom);
		} else {
			ratings = customerReviewDao.getReviewsForProductAndBetweenRating(product, Math.min(ratingFrom, ratingTo), Math.max(ratingFrom, ratingTo));
		}
		return ratings;
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProductAndLanguage(final ProductModel product, final LanguageModel language)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		ServicesUtil.validateParameterNotNullStandardMessage("language", language);
		return customerReviewDao.getReviewsForProductAndLanguage(product, language);
	}

	@Override
	public void deleteCustomerReview(final Long id)
	{
		customerReviewDao.deleteReview(id);
	}
}
