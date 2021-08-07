package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.exception.ChallengeException;
import com.example.demo.model.Agenda;
import com.example.demo.model.Session;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.request.SessionRequest;

@RunWith(MockitoJUnitRunner.class)
public class SessioOpenServiceTest {

	@InjectMocks
	private SessioOpenService service;

	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private AgendaRepository agendaRepository;
	
	private static Long ID_RANDOM = new Random().nextLong();
	
	@Test
	public void mustOpenSession() {
		when(agendaRepository.findAgenda(any(Long.class)))
			.thenReturn(createAgendaMock());
		
		service.execute(getSuccessSessionRequest());
		verify(sessionRepository).save(any(Session.class));
	}
	
	@Test(expected = ChallengeException.class)
	public void errorShouldOccurWhenOpenSessionWithNullAgenda() {		
		SessionRequest sessionRequest = getSuccessSessionRequest();
		sessionRequest.setId_agenda(null);
		
		service.execute(sessionRequest);
		verify(sessionRepository, never()).save(any(Session.class));
	}
	
	@Test(expected = ChallengeException.class)
	public void errorShouldOccurWhenOpenSessionAndNotFoundAgenda() {			
		service.execute(getSuccessSessionRequest());
		verify(sessionRepository, never()).save(any(Session.class));
	}

	@Test(expected = ChallengeException.class)
	public void errorShouldOccurWhenOpenSessionAndThereIsAlreadyAnOpenSession() {		
		
		when(agendaRepository.findAgenda(any(Long.class)))
			.thenReturn(createAgendaMock());
				
		when(sessionRepository.findByOpenAgendaSession(any(Long.class)))
			.thenReturn(createSessionMock());
		
		service.execute(getSuccessSessionRequest());
		verify(sessionRepository, never()).save(any(Session.class));
	}	
	
	private static SessionRequest getSuccessSessionRequest() {
		return SessionRequest
				.builder()
				.id_agenda(ID_RANDOM)
				.openingTimeInMinutes(1)
				.build();
	}
	
	private static Agenda createAgendaMock() {
		return  Agenda
				.builder()
				.id(ID_RANDOM)
				.name("Test agenda name")
				.description("Test agenda description")
				.build();
	}
	
	private static Session createSessionMock() {
		return Session
				.builder()
				.id(ID_RANDOM)
				.openingTimeInMinutes(30)
				.dateEndTime(LocalDateTime.now().plusMinutes(30))
				.agenda(createAgendaMock())
				.build();
	}
}