package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.response.CPFResponse;

@FeignClient(url="${client.url.cpf-validator}", name="cpf-validator")
public interface CPFClient {
	
	@GetMapping("/users/{cpf}")
	CPFResponse getCpf(@PathVariable String cpf);
}