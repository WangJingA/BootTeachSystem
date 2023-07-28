package com.boot.teach.common.exception;

public class LoginException extends IllegalArgumentException{
    public LoginException(String msg){
        super(msg);
    }
}
