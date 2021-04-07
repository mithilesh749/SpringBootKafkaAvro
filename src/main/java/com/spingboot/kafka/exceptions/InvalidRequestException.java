package com.spingboot.kafka.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String reason)
    {
        super("Request Validation Failed with reason: " + reason);
    }
}
