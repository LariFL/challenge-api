package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.exception.ChallengeException;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.request.AgendaRequest;

@RunWith(MockitoJUnitRunner.class)
public class AgendaAddServiceTest {

	@InjectMocks
	private AgendaAddService service;

	@Mock
	private AgendaRepository repository;
	
	@Test
	public void mustAddAgenda() {		
		service.execute(getSuccessAgendaRequest());
		verify(repository).save(any(Agenda.class));
	}
	
	@Test(expected = ChallengeException.class)
	public void errorShouldOccurWhenAddAgendaWithNullName() {		
		AgendaRequest agendaRequest = getSuccessAgendaRequest();
		agendaRequest.setName(null);

		service.execute(agendaRequest);
		verify(repository, never()).save(any(Agenda.class));
	}
	
	@Test(expected = ChallengeException.class)
	public void errorShouldOccurWhenAddAgendaWithEmptyName() {		
		AgendaRequest agendaRequest = getSuccessAgendaRequest();
		agendaRequest.setName("");

		service.execute(agendaRequest);
		verify(repository, never()).save(any(Agenda.class));
	}
	
	private static AgendaRequest getSuccessAgendaRequest() {
		return AgendaRequest
				.builder()
				.name("Agenda test name")
				.description("Agenda test description")
				.build();
	}
}