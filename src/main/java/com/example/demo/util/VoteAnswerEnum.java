package com.example.demo.util;

public enum VoteAnswerEnum {
	
	ANSWER_YES("Y"), 
	ANSWER_NO("N");	
	private final String value;
	
	VoteAnswerEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}