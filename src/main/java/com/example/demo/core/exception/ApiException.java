package com.example.demo.core.exception;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException{
	
	private static final long serialVersionUID = new Random().nextLong();
	private String message;
}