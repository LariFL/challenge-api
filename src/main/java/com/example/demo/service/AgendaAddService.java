package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ChallengeException;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.request.AgendaRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgendaAddService {

	@Autowired
	private AgendaRepository agendaRepository;
	
	public Agenda execute(AgendaRequest agendaRequest) {		
		validatesBusinessRules(agendaRequest);
		log.info("Agenda created successfully.");
		return agendaRepository.save(createAgenda(agendaRequest));
	}
	
	private void validatesBusinessRules(AgendaRequest agendaRequest) {		
		if(agendaRequest.getName() == null)
			throw new ChallengeException("Name information is required.");		
		
		if(agendaRequest.getName().trim().equals(""))
			throw new ChallengeException("Name information cannot be empty.");		
	}
	
	private Agenda createAgenda(AgendaRequest agendaRequest) {
		return Agenda.builder()
				.name(agendaRequest.getName())
				.description(agendaRequest.getDescription())
				.build();
	}
}
