package com.eventi.calvino.calvinoExceptions;

public class TopicAlreadyHasProducerException extends IllegalArgumentException{
     public TopicAlreadyHasProducerException(String errorMessage){
        super(errorMessage);
    } 
}
