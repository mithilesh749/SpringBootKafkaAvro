package com.spingboot.kafka.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "customer_reviews")
@Data
public class CustomerReviewModel implements Serializable {

	@Transient
	public static final String SEQUENCE_NAME = "customer_reviews_sequence";
	@Id
	private Long id;
	
	private String headline;
	private String comment;
	private Double rating;
	
	@DBRef(lazy = true)
	private UserModel user;

	@DBRef(lazy = true)
	private ProductModel product;

	@DBRef(lazy = true)
	private LanguageModel language;
}
