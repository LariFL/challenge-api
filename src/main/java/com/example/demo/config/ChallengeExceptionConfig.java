package com.example.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.exception.ChallengeException;
import com.example.demo.response.ChallengeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ChallengeExceptionConfig {

    @ExceptionHandler(ChallengeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ChallengeResponse handleSecurityException(ChallengeException se) {
    	log.error(se.getMessage());
        return new ChallengeResponse(se.getMessage());
    }
}