package com.freddie.marketplace.Exceptions;

public class UserCanNotApplyTwiceException extends RuntimeException{
    public UserCanNotApplyTwiceException(String message){
        super(message);
    }
}
