package com.freddie.marketplace.Exceptions;

public class ThereAreNoProductsException extends RuntimeException {
    public ThereAreNoProductsException(String message){
        super(message);
    }
}
