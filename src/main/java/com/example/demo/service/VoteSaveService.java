package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.CPFClient;
import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Session;
import com.example.demo.model.Vote;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;

@Service
public class VoteSaveService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private CPFClient cpfClient;
	
	private String MEMBER_CANNOT_VOTE = "UNABLE_TO_VOTE";	
	private String ANSWER_YES = "Y";
	private String ANSWER_NO = "N";
	
	public void execute(Vote vote) {		
		validatesBusinessRules(vote);
		voteRepository.save(vote);
	}
	
	private void validatesBusinessRules(Vote vote) {
		
		if(vote.getSession() == null || (vote.getSession() != null && vote.getSession().getId() == null))
			throw new ApiException("Session information is required.");
		
		if(vote.getAnswer() == null)
			throw new ApiException("Answer information is required.");
		
		putAnswerToUpperAndRemoveSpaces(vote);
		if(!(ANSWER_YES).equals(vote.getAnswer()) && !(ANSWER_NO).equals(vote.getAnswer()))
			throw new ApiException("Invalid vote! Use 'Y' for yes or 'N' for no.");
		
		Session session = sessionRepository.findSession(vote.getSession().getId());
		if(session == null)
			throw new ApiException("Voting session not found.");
		
		if(session.getDateEndTime().isBefore(LocalDateTime.now()))
			throw new ApiException("Voting session closed.");
		
		if(voteRepository.findByAssociateAccountedVote(vote.getSession().getId(), vote.getCpfAssociate()) != null)
			throw new ApiException("Member's vote already counted.");
		
		try {
			if(cpfClient.getCpf(vote.getCpfAssociate()).getStatus().equals(MEMBER_CANNOT_VOTE))
				throw new ApiException("Member cannot vote.");
		} catch (Exception e) {
			throw new ApiException("Member's CPF is invalid.");
		}
		
	}
	
	private void putAnswerToUpperAndRemoveSpaces(Vote vote) {
		vote.setAnswer(vote.getAnswer().toUpperCase().trim());
	}

}
