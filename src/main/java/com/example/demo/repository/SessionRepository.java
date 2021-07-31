package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

	@Query(value="SELECT s FROM Session s WHERE id = ?1")
	Session findSession(Long id);
	
	@Query("SELECT s FROM Session s WHERE ID_AGENDA = ?1 AND dateEndTime > CURRENT_TIMESTAMP")
	Session findByOpenAgendaSession(Long id_agenda);
}