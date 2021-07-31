package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;
import com.example.demo.service.AgendaSaveService;

@RunWith(MockitoJUnitRunner.class)
public class AgendaSaveServiceTest {

	@InjectMocks
	private AgendaSaveService service;

	@Mock
	private AgendaRepository repository;
	
	@Test
	public void mustSaveAgenda() {
		
		Agenda agenda = getSuccessAgenda();
		service.execute(agenda);

		verify(repository).save(any(Agenda.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveAgendaNoName(){	
		
		Agenda agenda = getSuccessAgenda();
		agenda.setName(null);

		service.execute(agenda);

		verify(repository, never()).save(any(Agenda.class));
	}
	
	private static Agenda getSuccessAgenda() {
		return Agenda
				.builder()
				.id(new Random().nextLong())
				.name("Test agenda name")
				.description("Test agenda description")
				.build();
	}
}