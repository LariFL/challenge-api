package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ChallengeException;
import com.example.demo.model.Session;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.request.SessionRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SessionOpenService {

	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	private static int ZERO_SESSION_TIME_IN_MINUTES = 0;
	private static int DEFAULT_SESSIONN_TIME_IN_MINUTES = 1;
	
	public Session execute(SessionRequest sessionRequest) {
		validatesBusinessRules(sessionRequest);		
		Session session = sessionRepository.save(createSession(sessionRequest));
		log.info("Session opened successfully.");
		return session;
	}
	
	private void validatesBusinessRules(SessionRequest sessionRequest) {
		if(sessionRequest.getId_agenda() == null)
			throw new ChallengeException("Agenda information is required.");

		if(!agendaRepository.existsById(sessionRequest.getId_agenda()))
			throw new ChallengeException("Agenda not found.");
		
		if(sessionRepository.findByOpenAgendaSession(sessionRequest.getId_agenda()) != null)
			throw new ChallengeException("There's already an open session for the informed agenda.");
		
		if(sessionRequest.getOpeningTimeInMinutes() <= ZERO_SESSION_TIME_IN_MINUTES)
			sessionRequest.setOpeningTimeInMinutes(DEFAULT_SESSIONN_TIME_IN_MINUTES);
	}
	
	private Session createSession(SessionRequest sessionRequest) {
		return Session.builder()
				.agenda(agendaRepository.findAgenda(sessionRequest.getId_agenda()))
				.openingTimeInMinutes(sessionRequest.getOpeningTimeInMinutes())
				.dateEndTime(LocalDateTime.now().plusMinutes(sessionRequest.getOpeningTimeInMinutes()))
				.build();
	}
}
