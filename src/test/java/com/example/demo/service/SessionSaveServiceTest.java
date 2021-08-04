package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.model.Session;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.repository.SessionRepository;

@RunWith(MockitoJUnitRunner.class)
public class SessionSaveServiceTest {

	@InjectMocks
	private SessionSaveService service;

	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private AgendaRepository agendaRepository;
	
	private static Long ID_RANDOM = new Random().nextLong();
	
	@Test
	public void mustSaveSession() {
		
		when(agendaRepository.findAgenda(any(Long.class)))
			.thenReturn(createAgendaMock());
				
		Session session = getSuccessSession();
		service.execute(session);

		verify(sessionRepository).save(any(Session.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveSessionNoAgenda(){	
		
		Session session = getSuccessSession();
		session.setAgenda(null);

		service.execute(session);

		verify(sessionRepository, never()).save(any(Session.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveSessionAndNotFoundAgenda(){	
		
		Session session = getSuccessSession();

		service.execute(session);

		verify(sessionRepository, never()).save(any(Session.class));
	}

	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveSessionAndThereIsAlreadyAnOpenSession(){			
		
		when(agendaRepository.findAgenda(any(Long.class)))
			.thenReturn(createAgendaMock());
				
		when(sessionRepository.findByOpenAgendaSession(any(Long.class)))
			.thenReturn(getSuccessSession());
		
		Session session = getSuccessSession();

		service.execute(session);

		verify(sessionRepository, never()).save(any(Session.class));
	}	
	
	private static Session getSuccessSession() {
		return Session
				.builder()
				.id(ID_RANDOM)
				.openingTimeInMinutes(1)
				.agenda(Agenda.builder().id(ID_RANDOM).build())
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
}