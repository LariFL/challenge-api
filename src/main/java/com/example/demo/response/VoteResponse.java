package com.example.demo.response;

import com.example.demo.model.Session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {

	private Session session;
	private Long countAnswerYes;
	private Long countAnswerNo;
	
}
