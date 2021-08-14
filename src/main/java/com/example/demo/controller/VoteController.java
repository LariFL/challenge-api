package com.example.demo.controller;

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
import com.example.demo.request.VoteRequest;
import com.example.demo.response.VoteResponse;
import com.example.demo.service.VoteResultService;
import com.example.demo.service.VoteVotingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/vote")
@Api(value = "Vote API")
public class VoteController {
	
	@Autowired
	private VoteVotingService voteVotingService;
	
	@Autowired
	private VoteResultService voteResultService;
	
	@PostMapping(value="/voteSessionAgenda")
	@ApiOperation(value = "Takes a associate's vote in a session")
	public ResponseEntity<Vote> voteSessionAgenda(
			@RequestBody VoteRequest voteRequest) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(voteVotingService.execute(voteRequest));
	}
	
	@GetMapping(value="/resultBySession")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Session voting result")
	public VoteResponse resultBySession(
			@RequestParam(required=true) Long id) {
		return voteResultService.execute(id);
	}
}