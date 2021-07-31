package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;

@Service
public class AgendaSaveService {

	@Autowired
	private AgendaRepository agendaRepository;
	
	public void execute(Agenda agenda) {
		validatesBusinessRules(agenda);
		agendaRepository.save(agenda);
	}
	
	private void validatesBusinessRules(Agenda agenda) {
		if(agenda.getName() == null)
			throw new ApiException("Campo 'name' é obrigatório");
	}

}
