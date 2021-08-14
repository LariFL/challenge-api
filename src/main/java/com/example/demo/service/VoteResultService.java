package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ChallengeException;
import com.example.demo.model.Session;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.response.VoteResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VoteResultService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;

	public VoteResponse execute(Long id_session) {		
		validatesBusinessRules(id_session);
		VoteResponse voteResponse = voteRepository.calcVoteResult(id_session);
		log.info("Voting result calculated successfully.");
		return voteResponse;
	}
	
	private void validatesBusinessRules(Long id) {
		if(id == null)
			throw new ChallengeException("It's necessary to inform the id of the session to be counted.");
		
		Session session = sessionRepository.findSession(id);
		if(session == null)
			throw new ChallengeException("Voting session not found.");
		
		if(session.getDateEndTime().isAfter(LocalDateTime.now()))
			throw new ChallengeException("Voting session is still open.");
	}
}
