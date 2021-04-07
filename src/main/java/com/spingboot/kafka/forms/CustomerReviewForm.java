package com.spingboot.kafka.forms;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerReviewForm implements Serializable
{
	private Double rating;
	private String headline;
	private String comment;
}
