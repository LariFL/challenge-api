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

import com.example.demo.client.CPFClient;
import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.model.Session;
import com.example.demo.model.Vote;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.response.CPFResponse;

@RunWith(MockitoJUnitRunner.class)
public class VoteSaveServiceTest {

	@InjectMocks
	private VoteSaveService service;

	@Mock
	private VoteRepository voteRepository;
	
	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private CPFClient cpfClient;
	
	private static Long ID_RANDOM = new Random().nextLong();	
	private String MEMBER_CANNOT_VOTE = "UNABLE_TO_VOTE";
	private String MEMBER_CAN_VOTE = "ABLE_TO_VOTE";
	
	@Test
	public void mustSaveVote() {
		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
		
		when(cpfClient.getCpf(any(String.class)))
			.thenReturn(createCPFResponseMock(MEMBER_CAN_VOTE));
		
		Vote vote = getSuccessVote();
		service.execute(vote);

		verify(voteRepository).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveVoteNoSession(){	
		
		Vote vote = getSuccessVote();
		vote.setSession(null);
		service.execute(vote);
	
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveVoteAndMemberCannotVote(){	
		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
	
		when(cpfClient.getCpf(any(String.class)))
			.thenReturn(createCPFResponseMock(MEMBER_CANNOT_VOTE));
		
		Vote vote = getSuccessVote();
		service.execute(vote);
	
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenSaveVoteAndMemberAlreadyCounted(){	
		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
		
		when(voteRepository.findByAssociateAccountedVote(any(Long.class), any(String.class)))
			.thenReturn(getSuccessVote());
		
		Vote vote = getSuccessVote();
		service.execute(vote);
	
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	private static Vote getSuccessVote() {
		return Vote
				.builder()
				.id(new Random().nextLong())
				.cpfAssociate("96887565048")
				.answer("Y")
				.session(Session.builder().id(ID_RANDOM).build())
				.build();
	}
	
	private static Session createSessionMock() {
		return Session
				.builder()
				.id(ID_RANDOM)
				.openingTimeInMinutes(1)
				.dateEndTime(LocalDateTime.now().plusMinutes(30))
				.agenda(Agenda.builder().id(ID_RANDOM).build())
				.build();
	}
	
	private static CPFResponse createCPFResponseMock(String status) {
		return CPFResponse
				.builder()
				.status(status)
				.build();
	}
	
}