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
import com.example.demo.request.VoteRequest;
import com.example.demo.response.CPFResponse;

@RunWith(MockitoJUnitRunner.class)
public class VoteVotingServiceTest {

	@InjectMocks
	private VoteVotingService service;

	@Mock
	private VoteRepository voteRepository;
	
	@Mock
	private SessionRepository sessionRepository;
	
	@Mock
	private CPFClient cpfClient;
	
	private static Long ID_RANDOM = new Random().nextLong();	
	private String ASSOCIATE_CANNOT_VOTE = "UNABLE_TO_VOTE";
	private String ASSOCIATE_CAN_VOTE = "ABLE_TO_VOTE";
	
	@Test
	public void mustVotingVote() {		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
		
		when(cpfClient.getCpf(any(String.class)))
			.thenReturn(createCPFResponseMock(ASSOCIATE_CAN_VOTE));
		
		service.execute(getSuccessVoteRequest());
		verify(voteRepository).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteWithNullSession() {	
		VoteRequest voteRequest = getSuccessVoteRequest();
		voteRequest.setId_session(null);
		service.execute(voteRequest);
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteWithNullAnswer() {	
		VoteRequest voteRequest = getSuccessVoteRequest();
		voteRequest.setAnswer(null);
		service.execute(voteRequest);
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteWithNullAssociateCPF() {	
		VoteRequest voteRequest = getSuccessVoteRequest();
		voteRequest.setCpfAssociate(null);
		service.execute(voteRequest);
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteAndSessionNotFound() {
		service.execute(getSuccessVoteRequest());
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteAndSessionClose() {		
		Session session = createSessionMock();
		session.setDateEndTime(LocalDateTime.now().plusMinutes(-30));		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(session);		
		
		service.execute(getSuccessVoteRequest());
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteWithInvalidAnswer() {	
		VoteRequest voteRequest = getSuccessVoteRequest();
		voteRequest.setAnswer("");
		service.execute(voteRequest);
		verify(voteRepository, never()).save(any(Vote.class));
	}

	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteAndAssociateCannotVote() {	
		
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
	
		when(cpfClient.getCpf(any(String.class)))
			.thenReturn(createCPFResponseMock(ASSOCIATE_CANNOT_VOTE));
		
		service.execute(getSuccessVoteRequest());
		verify(voteRepository, never()).save(any(Vote.class));
	}
	
	@Test(expected = ApiException.class)
	public void errorShouldOccurWhenVotingVoteAndAssociateAlreadyCounted() {			
		when(sessionRepository.findSession(any(Long.class)))
			.thenReturn(createSessionMock());
		
		when(voteRepository.findByAssociateAccountedVote(any(Long.class), any(String.class)))
			.thenReturn(createVoteMock());
		
		service.execute(getSuccessVoteRequest());	
		verify(voteRepository, never()).save(any(Vote.class));
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
	
	private static Vote createVoteMock() {
		return Vote
				.builder()
				.id(ID_RANDOM)
				.cpfAssociate("96887565048")
				.answer("Y")
				.session(createSessionMock())
				.build();
	}
	
	private static CPFResponse createCPFResponseMock(String status) {
		return CPFResponse
				.builder()
				.status(status)
				.build();
	}
	
	private static VoteRequest getSuccessVoteRequest() {
		return VoteRequest
				.builder()
				.id_session(ID_RANDOM)
				.cpfAssociate("96887565048")
				.answer("Y")
				.build();
	}	
}