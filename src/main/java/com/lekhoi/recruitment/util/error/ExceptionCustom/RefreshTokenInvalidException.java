package com.lekhoi.recruitment.util.error.ExceptionCustom;

public class RefreshTokenInvalidException extends RuntimeException{
    public RefreshTokenInvalidException(){
        super("Invalid refresh token");
    }
}
