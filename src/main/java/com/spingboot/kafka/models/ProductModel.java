package com.spingboot.kafka.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "products")
@Data
public class ProductModel implements Serializable
{
	@Transient
	public static final String SEQUENCE_NAME = "products_sequence";
	@Id
	private Long id;
	private String name;
}
