package com.eventi.server;

public class InvalidMessageException extends IllegalArgumentException{
     public InvalidMessageException(String errorMessage){
        super(errorMessage);
    }   
}
