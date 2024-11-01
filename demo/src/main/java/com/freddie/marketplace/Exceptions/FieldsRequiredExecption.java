package com.freddie.marketplace.Exceptions;

public class FieldsRequiredExecption extends RuntimeException {
    public FieldsRequiredExecption(String fieldRequired) {
        super(fieldRequired);
    }
}
