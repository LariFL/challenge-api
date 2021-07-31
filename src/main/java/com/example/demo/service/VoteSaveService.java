package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.CPFClient;
import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Vote;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.response.CPFResponse;

@Service
public class VoteSaveService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private CPFClient cpfClient;
	
	public void execute(Vote vote) {
		vote.setAnswer(vote.getAnswer().toUpperCase().trim());
		validatesBusinessRules(vote);
		voteRepository.save(vote);
	}
	
	private void validatesBusinessRules(Vote vote) {
		
		if(!sessionRepository.findById(vote.getSession().getId()).isPresent())
			throw new ApiException("Sessao de votação não encontrada.");
		
		if(sessionRepository.findByOpenSession(vote.getSession().getId()) == null)
			throw new ApiException("Sessao de votação encerrada.");
		
		CPFResponse cpfResponse;
		try {
			cpfResponse = cpfClient.getCpf(vote.getCpfAssociate());
		} catch (Exception e) {
			throw new ApiException("CPF inválido.");
		}
		
		if(cpfResponse.getStatus().equals("UNABLE_TO_VOTE"))
			throw new ApiException("Associado não pode votar.");
		
		if(voteRepository.findByAssociateAccountedVote(vote.getSession().getId(), vote.getCpfAssociate()).size() > 0)
			throw new ApiException("Voto do associado já contabilizado.");
				
		if(!vote.getAnswer().equals("S") && !vote.getAnswer().equals("N"))
			throw new ApiException("Voto inválido! Utilize 'S' para sim ou 'N' para não.");
	}

}
