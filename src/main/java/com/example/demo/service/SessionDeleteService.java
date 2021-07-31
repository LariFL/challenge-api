package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.repository.SessionRepository;

@Service
public class SessionDeleteService {

	@Autowired
	private SessionRepository sessionRepository;
	
	public boolean execute(Long id) {
		
		if(id == null)
			throw new ApiException("Necessário informar o id a ser deletado");
		
		if(sessionRepository.findSession(id) == null)
			return false;
		
		sessionRepository.deleteById(id);		
		return true;
	}
}
