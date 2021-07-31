package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResult {

	private Session session;
	private Long countAnswerYes;
	private Long countAnswerNo;
	
}
