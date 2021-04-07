package com.spingboot.kafka.repository;

import com.spingboot.kafka.models.CustomerReviewModel;
import com.spingboot.kafka.models.LanguageModel;
import com.spingboot.kafka.models.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerReviewRepo extends MongoRepository<CustomerReviewModel, Long> {
    @Query("select reviews from CustomerReviewModel reviews where reviews.product = ?1")
    List<CustomerReviewModel> getAllReviews(ProductModel product);

    @Query("select count(reviews) from CustomerReviewModel reviews where reviews.product = ?1")
    Integer getNumberOfReviews(ProductModel product);

    @Query("select avg(reviews.rating) from CustomerReviewModel reviews where reviews.product = ?1")
    Double getAverageRating(ProductModel product);

    @Query("select reviews from CustomerReviewModel reviews where reviews.product = ?1 and reviews.language = ?2")
    List<CustomerReviewModel> getReviewsForProductAndLanguage(ProductModel product, LanguageModel language);

    @Query("select reviews from CustomerReviewModel reviews where reviews.product = ?1 and (reviews.rating BETWEEN ?2 AND ?3)")
    List<CustomerReviewModel> getReviewsForProductAndBetweenRating(ProductModel product, Double from, Double to);

    @Query("select reviews from CustomerReviewModel reviews where reviews.product = ?1 and reviews.rating >= ?2")
    List<CustomerReviewModel> getReviewsForProductAndFromRating(ProductModel product, Double from);

    @Transactional
    @Query("delete from CustomerReviewModel reviews where reviews.id = ?1")
    void deleteReview(Long id);
}
