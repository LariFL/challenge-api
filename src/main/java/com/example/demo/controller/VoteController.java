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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.model.Vote;
import com.example.demo.response.VoteResponse;
import com.example.demo.service.VoteGetService;
import com.example.demo.service.VoteResponseService;
import com.example.demo.service.VoteSaveService;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/v1/vote")
@Api(value = "Vote API")
public class VoteController {
	
	@Autowired
	private VoteGetService voteGetService;
	
	@Autowired
	private VoteSaveService voteSaveService;
	
	@Autowired
	private VoteResponseService voteResponseService;
	
	@GetMapping(value="/get")
	public ResponseEntity<List<Vote>> get() {
		
		List<Vote> lista = voteGetService.execute();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping(value="/save")
	public ResponseEntity<Vote> save(
			@RequestBody Vote vote) {
		
		voteSaveService.execute(vote);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(value="/result")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public VoteResponse result(
			@RequestParam(required=false) Long id) {
		
		VoteResponse voteResult = voteResponseService.execute(id);
		return voteResult;
	}
}