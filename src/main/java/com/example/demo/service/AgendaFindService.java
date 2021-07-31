package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exception.ApiException;
import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;

@Service
public class AgendaFindService {

	@Autowired
	private AgendaRepository agendaRepository;

	public Agenda execute(Long id) {
		
		if(id == null)
			throw new ApiException("It's necessary to inform the id to be searched.");

		return agendaRepository.findAgenda(id);
	}
}
