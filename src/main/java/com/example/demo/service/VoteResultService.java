package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Session;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.response.VoteResponse;

@Service
public class VoteResultService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	private Session session;

	public VoteResponse execute(Long id_session) {		
		validatesBusinessRules(id_session);
		return voteRepository.calcVoteResult(id_session);
	}
	
	private void validatesBusinessRules(Long id) {
		if(id == null)
			throw new ApiException("It's necessary to inform the id of the session to be counted.");
		
		session = sessionRepository.findSession(id);
		if(session == null)
			throw new ApiException("Voting session not found.");
		
		if(session.getDateEndTime().isAfter(LocalDateTime.now()))
			throw new ApiException("Voting session is still open.");
	}
}
