package com.example.demo.enums;

public enum AssociateStatusEnum {
	
	ASSOCIATE_CAN_VOTE("ABLE_TO_VOTE"), 
	ASSOCIATE_CANNOT_VOTE("UNABLE_TO_VOTE");	
	private final String value;
	
	AssociateStatusEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
