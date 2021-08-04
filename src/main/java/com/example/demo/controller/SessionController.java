package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Session;
import com.example.demo.service.SessionFindService;
import com.example.demo.service.SessionGetService;
import com.example.demo.service.SessionSaveService;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/v1/session")
@Api(value = "Session API")
public class SessionController {
	
	@Autowired
	private SessionGetService sessionGetService;
	
	@Autowired
	private SessionFindService sessionFindService;
	
	@Autowired
	private SessionSaveService sessionSaveService;
	
	@GetMapping(value="/get")
	public ResponseEntity<List<Session>> get() {
		
		List<Session> lista = sessionGetService.execute();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping(value="/find")
	public ResponseEntity<Session> find(
			@RequestParam(required=false) Long id) {
		
		Session session = sessionFindService.execute(id);
		
		if (session == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(session);
	}
	
	@PostMapping(value="/save")
	public ResponseEntity<Session> save(
			@RequestBody Session session) {
		
		sessionSaveService.execute(session);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
		
}
