package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Vote;
import com.example.demo.repository.VoteRepository;

@Service
public class VoteGetService {

	@Autowired
	private VoteRepository voteRepository;

	public List<Vote> execute() {
		return voteRepository.findAll();
	}
}
