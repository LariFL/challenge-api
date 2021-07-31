package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.repository.AgendaRepository;

@Service
public class AgendaDeleteService {

	@Autowired
	private AgendaRepository agendaRepository;
	
	public boolean execute(Long id) {
		
		if(id == null)
			throw new ApiException("It's necessary to inform the id to be delete.");
		
		if(agendaRepository.existsById(id)) {
			agendaRepository.deleteById(id);		
			return true;
		}
		
		return false;
	}
}
