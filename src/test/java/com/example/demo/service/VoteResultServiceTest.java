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

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.model.Session;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;

@RunWith(MockitoJUnitRunner.class)
public class VoteResultServiceTest {

	@InjectMocks
	private VoteResultService service;

	@Mock
	private VoteRepository voteRepository;
	
	@Mock
	private SessionRepository sessionRepository;
	
	private static Long ID_RANDOM = new Random().nextLong();
	
	@Test
	public void mustCalcVoteResult() {		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
		
		service.execute(ID_RANDOM);
		verify(voteRepository).calcVoteResult(any(Long.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenCalcVoteResultWithNullIdSession() {	
		service.execute(null);
		verify(voteRepository, never()).calcVoteResult(any(Long.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenCalcVoteResultAndSessionNotFound() {	
		service.execute(ID_RANDOM);
		verify(voteRepository, never()).calcVoteResult(any(Long.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenCalcVoteResultAndSessionIsStillOpen() {
		Session session = createSessionMock();
		session.setDateEndTime(LocalDateTime.now().plusMinutes(30));
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(session);
		
		service.execute(ID_RANDOM);
		verify(voteRepository, never()).calcVoteResult(any(Long.class));
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
				.openingTimeInMinutes(1)
				.dateEndTime(LocalDateTime.now().plusMinutes(-30))
				.agenda(createAgendaMock())
				.build();
	}
}
