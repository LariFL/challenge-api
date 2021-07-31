package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

	@Query(value="SELECT a FROM Agenda a WHERE id = ?1")
	Agenda findAgenda(Long id);
	
}