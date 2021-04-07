package com.spingboot.kafka.service;

import com.spingboot.kafka.models.CustomerReviewModel;
import com.spingboot.kafka.models.LanguageModel;
import com.spingboot.kafka.models.ProductModel;
import com.spingboot.kafka.models.UserModel;

import java.util.List;


public interface CustomerReviewService
{
	CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, ProductModel product, UserModel user);

	void updateCustomerReview(CustomerReviewModel customer, UserModel user, ProductModel product);

	List<CustomerReviewModel> getReviewsForProduct(ProductModel product);

	Double getAverageRating(ProductModel product);

	Integer getNumberOfReviews(ProductModel product);

	List<CustomerReviewModel> getReviewsForProductAndRating(ProductModel product, Double ratingFrom, Double RatingTo);

	List<CustomerReviewModel> getReviewsForProductAndLanguage(ProductModel product, LanguageModel language);

	void deleteCustomerReview(Long id);
}
