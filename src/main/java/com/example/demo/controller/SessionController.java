package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Session;
import com.example.demo.request.SessionRequest;
import com.example.demo.service.SessioOpenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/session")
@Api(value = "Session API")
public class SessionController {
	
	@Autowired
	private SessioOpenService sessioOpenService;
	
	@PostMapping(value="/open")
	@ApiOperation(value = "Open a session for an agenda")
	public ResponseEntity<Session> open(
			@RequestBody SessionRequest sessionRequest) {		
		return ResponseEntity.status(HttpStatus.OK)
				.body(sessioOpenService.execute(sessionRequest));
	}
}