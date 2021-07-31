package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Session;
import com.example.demo.model.VoteResult;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;

@Service
public class VoteResultService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;

	public VoteResult execute(Long id) {
		
		validatesBusinessRules(id);
		return voteRepository.calcVoteResult(id);
	}
	
	private void validatesBusinessRules(Long id) {
		if(id == null)
			throw new ApiException("It's necessary to inform the id of the session to be counted.");
		
		Session session = sessionRepository.findSession(id);
		if(session == null)
			throw new ApiException("Voting session not found.");
		
		if(session.getDateEndTime().isAfter(LocalDateTime.now()))
			throw new ApiException("Voting session is still open.");
	}
}
