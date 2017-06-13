package com.jasonwjones.hyperion.parentinferrer;

@SuppressWarnings("serial")
public class ParentInferrerProcessingException extends RuntimeException {
	
	public ParentInferrerProcessingException(String message) {
		super(message);
	}
	
	public ParentInferrerProcessingException(Throwable throwable) {
		super(throwable);
	}
	
}
