package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Session;
import com.example.demo.repository.SessionRepository;

@Service
public class SessionGetService {

	@Autowired
	private SessionRepository sessionRepository;

	public List<Session> execute() {
		return sessionRepository.findAll();
	}
}
