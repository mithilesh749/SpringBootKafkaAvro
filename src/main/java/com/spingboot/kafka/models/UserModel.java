package com.spingboot.kafka.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spingboot.kafka.repository.UserRepo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "users")
@Data
public class UserModel implements Serializable {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long id;

    private String name;
}
