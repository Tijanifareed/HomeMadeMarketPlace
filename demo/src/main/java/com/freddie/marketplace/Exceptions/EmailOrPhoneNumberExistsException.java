package com.freddie.marketplace.Exceptions;

public class EmailOrPhoneNumberExistsException extends RuntimeException{
    public EmailOrPhoneNumberExistsException(String message){super(message);}
}
