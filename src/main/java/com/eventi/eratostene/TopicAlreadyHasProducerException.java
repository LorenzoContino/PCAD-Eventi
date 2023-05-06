package com.eventi.eratostene;

public class TopicAlreadyHasProducerException extends IllegalArgumentException{
     public TopicAlreadyHasProducerException(String errorMessage){
        super(errorMessage);
    } 
}
