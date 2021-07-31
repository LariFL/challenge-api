package com.example.demo;

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
import com.example.demo.repository.AgendaRepository;
import com.example.demo.service.AgendaFindService;

@RunWith(MockitoJUnitRunner.class)
public class AgendaFindServiceTest {

	@InjectMocks
	private AgendaFindService service;

	@Mock
	private AgendaRepository repository;

	@Test
	public void mustLoadAgenda() {

		long codigo = new Random().nextLong();
		
		Agenda agenda = Agenda
				.builder()
				.id(codigo)
				.build();
		
		when(repository.findAgenda(
				any(Long.class))).thenReturn(agenda);

		Agenda mock = service.execute(codigo);

		verify(repository).findAgenda(codigo);

		assert(mock.getId() == codigo);
	}
	
	@Test(expected = ApiException.class)
	public void mustNotLoadAgenda() {
		
		service.execute(null);
		verify(repository, never()).findAgenda(any(Long.class));
	}
}