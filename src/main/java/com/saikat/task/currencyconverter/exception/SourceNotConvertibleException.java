package com.saikat.task.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SourceNotConvertibleException extends RuntimeException{
    public SourceNotConvertibleException(String message) {
        super(message);
    }
}
