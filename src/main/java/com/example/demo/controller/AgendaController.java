package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Agenda;
import com.example.demo.request.AgendaRequest;
import com.example.demo.service.AgendaAddService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/agenda")
@Api(value = "Agenda API")
public class AgendaController {
	
	@Autowired
	private AgendaAddService agendaAddService;
	
	@PostMapping(value="/add")
	@ApiOperation(value = "Add a new agenda")
	public ResponseEntity<Agenda> add(
			@RequestBody AgendaRequest agendaRequest) {		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(agendaAddService.execute(agendaRequest));
	}	
}