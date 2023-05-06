package com.eventi.calvino.calvino_exceptions;

import java.security.InvalidAlgorithmParameterException;

public abstract class SubscriberException extends InvalidAlgorithmParameterException { 
    
    public SubscriberException(String errorMessage){
        super(errorMessage);
    } 

}
