package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Session;
import com.example.demo.repository.SessionRepository;

@Service
public class SessionFindService {

	@Autowired
	private SessionRepository sessionRepository;

	public Session execute(Long id) {
		
		if(id == null)
			throw new ApiException("Necessário informar o id a ser pesquisado");

		return sessionRepository.findSession(id);
	}
}
