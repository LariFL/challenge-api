package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Agenda;
import com.example.demo.service.AgendaDeleteService;
import com.example.demo.service.AgendaFindService;
import com.example.demo.service.AgendaGetService;
import com.example.demo.service.AgendaSaveService;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/agenda")
@Api(value = "API Agenda")
public class AgendaController {
	
	@Autowired
	private AgendaGetService agendaGetService;
	
	@Autowired
	private AgendaFindService agendaFindService;
	
	@Autowired
	private AgendaSaveService agendaSaveService;
	
	@Autowired
	private AgendaDeleteService agendaDeleteService;
	
	@GetMapping(value="/get")
	public ResponseEntity<List<Agenda>> get() {
		
		List<Agenda> lista = agendaGetService.execute();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping(value="/find")
	public ResponseEntity<Agenda> find(
			@RequestParam(required=false) Long id) {
		
		Agenda agenda = agendaFindService.execute(id);
		
		if (agenda == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(agenda);
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public ResponseEntity<Agenda> save(
			@RequestBody Agenda agenda) {
		
		agendaSaveService.execute(agenda);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@RequestMapping(value="/delete" , method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(
			@RequestParam Long id) {
		
		if(agendaDeleteService.execute(id))
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.notFound().build();
	}
}