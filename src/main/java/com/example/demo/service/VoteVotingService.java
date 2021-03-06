package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.CPFClient;
import com.example.demo.enums.AssociateStatusEnum;
import com.example.demo.enums.VoteAnswerEnum;
import com.example.demo.exception.ChallengeException;
import com.example.demo.model.Session;
import com.example.demo.model.Vote;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.request.VoteRequest;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VoteVotingService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private CPFClient cpfClient;
	
	private Session session;
		
	public Vote execute(VoteRequest voteRequest) {		
		validatesBusinessRules(voteRequest);
		Vote vote = voteRepository.save(createVote(voteRequest));
		log.info("Vote successfully counted.");
		return vote;
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
			throw new ChallengeException("Session information is required.");
		
		if(voteRequest.getAnswer() == null)
			throw new ChallengeException("Answer information is required.");
		
		if(voteRequest.getCpfAssociate() == null)
			throw new ChallengeException("Associate's CPF information is required.");		
	}
	
	private void putAnswerToUpperAndRemoveSpaces(VoteRequest voteRequest) {
		voteRequest.setAnswer(voteRequest.getAnswer().toUpperCase().trim());
	}
	
	private void validateIfAnswerIsValid(VoteRequest voteRequest) {
		putAnswerToUpperAndRemoveSpaces(voteRequest);
		if(!(VoteAnswerEnum.ANSWER_YES.getValue()).equals(voteRequest.getAnswer()) && !(VoteAnswerEnum.ANSWER_NO.getValue()).equals(voteRequest.getAnswer()))
			throw new ChallengeException("Invalid vote! Use 'Y' for yes or 'N' for no.");
	}
	
	private void validateIfExistsSessionOrIsClosed(VoteRequest voteRequest) {
		session = sessionRepository.findSession(voteRequest.getId_session());
		if(session == null)
			throw new ChallengeException("Voting session not found.");
		
		if(session.getDateEndTime().isBefore(LocalDateTime.now()))
			throw new ChallengeException("Voting session closed.");		
	}
	
	private void validateIfAssociateAlreadyVote(VoteRequest voteRequest) {
		if(voteRepository.findByAssociateAccountedVote(voteRequest.getId_session(), voteRequest.getCpfAssociate()) != null)
			throw new ChallengeException("Associate's vote already counted.");
	}
	
	private void validateIfAssociateCannotVoteOrCPFIsInvalid(VoteRequest voteRequest) {
		try {
			if(cpfClient.getCpf(voteRequest.getCpfAssociate()).getStatus().equals(AssociateStatusEnum.ASSOCIATE_CANNOT_VOTE.getValue()))
				throw new ChallengeException("Associate cannot vote.");
		} catch (FeignException e) {
			throw new ChallengeException("Associate's CPF is invalid.");
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