package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Session;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.SessionRepository;

@Service
public class SessionSaveService {

	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	public void execute(Session session) {
		validatesBusinessRules(session);
		
		if(session.getOpeningTime() <= 0)
			session.setOpeningTime(1);
		
		session.setDateEndTime(LocalDateTime.now().plusMinutes(session.getOpeningTime()));
		
		sessionRepository.save(session);
	}
	
	private void validatesBusinessRules(Session session) {

		if(!agendaRepository.findById(session.getAgenda().getId()).isPresent())
			throw new ApiException("Pauta não encontrada");
		
		if(sessionRepository.findByOpenAgendaSession(session.getAgenda().getId()).size() > 0)
			throw new ApiException("Já existe uma sessão em aberto para pauta informada.");
			
	}

}
