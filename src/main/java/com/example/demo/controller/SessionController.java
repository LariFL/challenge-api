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

import com.example.demo.model.Session;
import com.example.demo.service.SessionDeleteService;
import com.example.demo.service.SessionFindService;
import com.example.demo.service.SessionGetService;
import com.example.demo.service.SessionSaveService;

@Controller
@RequestMapping("/session")
public class SessionController {
	
	@Autowired
	private SessionGetService sessionGetService;
	
	@Autowired
	private SessionFindService sessionFindService;
	
	@Autowired
	private SessionSaveService sessionSaveService;
	
	@Autowired
	private SessionDeleteService sessionDeleteService;
	
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
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public ResponseEntity<Session> save(
			@RequestBody Session session) {
		
		sessionSaveService.execute(session);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@RequestMapping(value="/delete" , method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(
			@RequestParam Long id) {
		
		if(sessionDeleteService.execute(id))
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.notFound().build();
	}
	
}