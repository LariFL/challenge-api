package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Agenda;
import com.example.demo.repository.AgendaRepository;

@Service
public class AgendaGetService {

	@Autowired
	private AgendaRepository agendaRepository;

	public List<Agenda> execute() {
		return agendaRepository.findAll();
	}
}
