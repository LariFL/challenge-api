package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.model.Session;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.request.SessionRequest;

@Service
public class SessioOpenService {

	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	private Agenda agenda;
	
	public Session execute(SessionRequest sessionRequest) {
		validatesBusinessRules(sessionRequest);		
		if(sessionRequest.getOpeningTimeInMinutes() <= 0)
			sessionRequest.setOpeningTimeInMinutes(1);
		
		return sessionRepository.save(createSession(sessionRequest));
	}
	
	private void validatesBusinessRules(SessionRequest sessionRequest) {
		if(sessionRequest.getId_agenda() == null)
			throw new ApiException("Agenda information is required.");

		agenda = agendaRepository.findAgenda(sessionRequest.getId_agenda());
		if(agenda == null)
			throw new ApiException("Agenda not found.");
		
		if(sessionRepository.findByOpenAgendaSession(sessionRequest.getId_agenda()) != null)
			throw new ApiException("There's already an open session for the informed agenda.");
	}
	
	private Session createSession(SessionRequest sessionRequest) {
		return Session.builder()
				.agenda(agenda)
				.openingTimeInMinutes(sessionRequest.getOpeningTimeInMinutes())
				.dateEndTime(LocalDateTime.now().plusMinutes(sessionRequest.getOpeningTimeInMinutes()))
				.build();
	}
}
