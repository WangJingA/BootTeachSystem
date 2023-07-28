package com.boot.teach.common.exception;

public class CaptchaException extends IllegalArgumentException{
    public CaptchaException(String msg){
        super(msg);
    }
}
