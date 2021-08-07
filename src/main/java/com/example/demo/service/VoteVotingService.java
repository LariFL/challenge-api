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
import com.example.demo.request.VoteRequest;

import feign.FeignException;

@Service
public class VoteVotingService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private CPFClient cpfClient;
	
	Session session;
	
	private String ASSOCIATE_CANNOT_VOTE = "UNABLE_TO_VOTE";	
	private String ANSWER_YES = "Y";
	private String ANSWER_NO = "N";
	
	public Vote execute(VoteRequest voteRequest) {		
		validatesBusinessRules(voteRequest);
		return voteRepository.save(createVote(voteRequest));
	}
	
	private void validatesBusinessRules(VoteRequest voteRequest) {
		validateRequiredInformations(voteRequest);
		validateIfAnswerIsValid(voteRequest);
		validateIfExistsSessionOrIsClosed(voteRequest);
		validateIfAssociateAlreadyVote(voteRequest);
		validateIfAssociateCannotVoteOrCPFIsInvalid(voteRequest);		
	}
	
	private  void validateRequiredInformations(VoteRequest voteRequest) {
		if(voteRequest.getId_session() == null)
			throw new ApiException("Session information is required.");
		
		if(voteRequest.getAnswer() == null)
			throw new ApiException("Answer information is required.");
		
		if(voteRequest.getCpfAssociate() == null)
			throw new ApiException("Associate's CPF information is required.");		
	}
	
	private void putAnswerToUpperAndRemoveSpaces(VoteRequest voteRequest) {
		voteRequest.setAnswer(voteRequest.getAnswer().toUpperCase().trim());
	}
	
	private void validateIfAnswerIsValid(VoteRequest voteRequest) {
		putAnswerToUpperAndRemoveSpaces(voteRequest);
		if(!(ANSWER_YES).equals(voteRequest.getAnswer()) && !(ANSWER_NO).equals(voteRequest.getAnswer()))
			throw new ApiException("Invalid vote! Use 'Y' for yes or 'N' for no.");
	}
	
	private void validateIfExistsSessionOrIsClosed(VoteRequest voteRequest) {
		session = sessionRepository.findSession(voteRequest.getId_session());
		if(session == null)
			throw new ApiException("Voting session not found.");
		
		if(session.getDateEndTime().isBefore(LocalDateTime.now()))
			throw new ApiException("Voting session closed.");		
	}
	
	private void validateIfAssociateAlreadyVote(VoteRequest voteRequest) {
		if(voteRepository.findByAssociateAccountedVote(voteRequest.getId_session(), voteRequest.getCpfAssociate()) != null)
			throw new ApiException("Associate's vote already counted.");
	}
	
	private void validateIfAssociateCannotVoteOrCPFIsInvalid(VoteRequest voteRequest) {
		try {
			if(cpfClient.getCpf(voteRequest.getCpfAssociate()).getStatus().equals(ASSOCIATE_CANNOT_VOTE))
				throw new ApiException("Associate cannot vote.");
		} catch (FeignException e) {
			throw new ApiException("Associate's CPF is invalid.");
		}
	}
	
	private Vote createVote(VoteRequest voteRequest) {
		return Vote.builder()
				.cpfAssociate(voteRequest.getCpfAssociate())
				.answer(voteRequest.getAnswer())
				.session(session)
				.build();
	}
}
