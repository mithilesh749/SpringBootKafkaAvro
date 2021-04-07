package com.spingboot.kafka.controllers;

import com.spingboot.kafka.exceptions.InvalidRequestException;
import com.spingboot.kafka.exceptions.ProductNotFoundException;
import com.spingboot.kafka.exceptions.UserNotFoundException;
import com.spingboot.kafka.forms.CustomerReviewForm;
import com.spingboot.kafka.models.CustomerReviewModel;
import com.spingboot.kafka.models.ProductModel;
import com.spingboot.kafka.models.UserModel;
import com.spingboot.kafka.repository.ProductRepo;
import com.spingboot.kafka.repository.SequenceGeneratorRepo;
import com.spingboot.kafka.repository.UserRepo;
import com.spingboot.kafka.service.CustomerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerReviewController {

    // Setting restricted Words list in the properties file to maintain and scale
    // if the list grows beyond an extent we can use the DB to save the list and cache it at the app start to minimise DB reads operations
    @Value("#{'${restricted.words}'.split(',')}")
    private List<String> restrictedWords;

    @Autowired
    private ProductRepo productDao;

    @Autowired
    private SequenceGeneratorRepo dbSeq;

    @Autowired
    private UserRepo userDao;

    @Autowired
    private CustomerReviewService customerReviewService;

    @GetMapping({"products/{productId:\\d+}/reviews"})
    public List<CustomerReviewModel> getReviews(@PathVariable final Long productId,
                                                @RequestParam(required = false) final Double ratingFrom, @RequestParam(required = false) final Double ratingTo) {

        final ProductModel product = productDao.findById(productId).get();
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        // if ratingFrom or ratingTo are provided it would use getReviews based on product and ratings
        if (ratingFrom != null || ratingTo != null) {
            return customerReviewService.getReviewsForProductAndRating(product, ratingFrom, ratingTo);
        } else {
            // Getting all the reviews for the product
            return customerReviewService.getReviewsForProduct(product);
        }
    }

    @PostMapping({"products/{productId:\\d+}/users/{userId:\\d+}/reviews"})
    public CustomerReviewModel createReview(@PathVariable final Long userId, @PathVariable final Long productId,
                                            @RequestBody final CustomerReviewForm customerReviewForm) {
        final ProductModel product = productDao.findById(productId).get();
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        final UserModel user = userDao.findById(userId).get();
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        // Validating Request Body
        if (customerReviewForm.getRating() == null || customerReviewForm.getRating() < 0) {
            throw new InvalidRequestException("Rating must not be null or negative");
        }
        if (restrictedWords.stream().parallel().anyMatch(str -> customerReviewForm.getComment().toLowerCase().contains(str.toLowerCase()))) {
            throw new InvalidRequestException("Comment contain restricted word(s)");
        }
        if (restrictedWords.stream().parallel().anyMatch(str -> customerReviewForm.getHeadline().toLowerCase().contains(str.toLowerCase()))) {
            throw new InvalidRequestException("Headline contain restricted word(s)");
        }

        return customerReviewService
                .createCustomerReview(customerReviewForm.getRating(), customerReviewForm.getHeadline(),
                        customerReviewForm.getComment(), product, user);
    }

    @PostMapping({"products"})
    public ProductModel createProduct() {
        final ProductModel product = new ProductModel();
        product.setId(dbSeq.generateSequence(ProductModel.SEQUENCE_NAME));
        productDao.save(product);
        return product;
    }

    @PostMapping({"users"})
    public UserModel createUser() {
        final UserModel user = new UserModel();
        user.setId(dbSeq.generateSequence(UserModel.SEQUENCE_NAME));
        userDao.save(user);
        return user;
    }

    @DeleteMapping({"reviews/{reviewId:\\d+}"})
    public void deleteReview(@PathVariable final Long reviewId) {
        customerReviewService.deleteCustomerReview(reviewId);
    }
}
