package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Vote;
import com.example.demo.model.VoteResult;

public interface VoteRepository extends JpaRepository<Vote, Long> {

	@Query("SELECT v FROM Vote v WHERE ID_SESSION = ?1 AND cpfAssociate = ?2 ")
	Vote findByAssociateAccountedVote(Long id_session, String cpfAssociate);
	
	@Query(value= "SELECT NEW com.example.demo.model.VoteResult(s, "
				+ "SUM(CASE WHEN ANSWER = 'Y' THEN 1 ELSE 0 END) AS countAnswerYes, "
				+ "SUM(CASE WHEN ANSWER = 'N' THEN 1 ELSE 0 END) AS countAnswerNo) "
				+ "FROM Vote v "
				+ "INNER JOIN v.session s "
				+ "WHERE ID_SESSION = ?1 "
				+ "GROUP BY ID_SESSION ")
	VoteResult calcVoteResult(Long id);
}
