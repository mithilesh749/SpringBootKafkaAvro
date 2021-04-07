package com.spingboot.kafka.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "language")
@Data
public class LanguageModel
{
	@Transient
	public static final String SEQUENCE_NAME = "language_sequence";
	@Id
	private Long id;
	private String name;
	private String isocode;
}
