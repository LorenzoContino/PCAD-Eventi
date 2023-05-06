package com.eventi.calvino.calvino_exceptions;

public class TopicAlreadyHasProducerException extends IllegalArgumentException{
     public TopicAlreadyHasProducerException(String errorMessage){
        super(errorMessage);
    } 
}
