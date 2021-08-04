package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.request.AgendaRequest;

@Service
public class AgendaSaveService {

	@Autowired
	private AgendaRepository agendaRepository;
	
	public Agenda execute(AgendaRequest agendaRequest) {		
		validatesBusinessRules(agendaRequest);
		return agendaRepository.save(Agenda.builder()
										.name(agendaRequest.getName())
										.description(agendaRequest.getDescription())
										.build());
	}
	
	private void validatesBusinessRules(AgendaRequest agendaRequest) {		
		if(agendaRequest.getName() == null)
			throw new ApiException("Name information is required.");		
		
		if(agendaRequest.getName().trim().equals(""))
			throw new ApiException("Name information cannot be empty.");		
	}
}
