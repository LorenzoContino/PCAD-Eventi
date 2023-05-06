package com.eventi.calvino.calvino_exceptions;

public class TopicAlreadyHasConsumerException extends IllegalArgumentException{
     public TopicAlreadyHasConsumerException(String errorMessage){
        super(errorMessage);
    } 
}
