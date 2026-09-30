package com.lekhoi.recruitment.util.error.ExceptionCustom;

public class DateTimeInvalidException extends RuntimeException{
    public DateTimeInvalidException(String message){
        super(message);
    }
    public DateTimeInvalidException(){
        super("Date time invalid");
    }
}
