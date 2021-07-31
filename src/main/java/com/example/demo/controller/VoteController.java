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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.model.Vote;
import com.example.demo.model.VoteResult;
import com.example.demo.service.VoteGetService;
import com.example.demo.service.VoteResultService;
import com.example.demo.service.VoteSaveService;

@Controller
@RequestMapping("/vote")
public class VoteController {
	
	@Autowired
	private VoteGetService voteGetService;
	
	@Autowired
	private VoteSaveService voteSaveService;
	
	@Autowired
	private VoteResultService voteResultService;
	
	@GetMapping(value="/get")
	public ResponseEntity<List<Vote>> get() {
		
		List<Vote> lista = voteGetService.execute();
		return ResponseEntity.ok(lista);
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public ResponseEntity<Vote> save(
			@RequestBody Vote vote) {
		
		voteSaveService.execute(vote);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(value="/result")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public VoteResult result(
			@RequestParam(required=false) Long id) {
		
		VoteResult voteResult = voteResultService.execute(id);
		return voteResult;
	}
}