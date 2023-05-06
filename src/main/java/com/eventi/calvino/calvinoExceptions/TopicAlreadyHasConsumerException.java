package com.eventi.calvino.calvinoExceptions;

public class TopicAlreadyHasConsumerException extends IllegalArgumentException{
     public TopicAlreadyHasConsumerException(String errorMessage){
        super(errorMessage);
    } 
}
