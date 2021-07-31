package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
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
			throw new ApiException("Necessário informar o id da sessão a ser contabilizada");
		
		if(sessionRepository.findSession(id) == null)
			throw new ApiException("Sessão não encontrada.");
		
		if(sessionRepository.findByOpenSession(id) != null)
			throw new ApiException("Sessão de votação anda está aberta.");
	}
}
