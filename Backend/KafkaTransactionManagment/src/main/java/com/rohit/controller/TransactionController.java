package com.rohit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.entity.Transaction;
import com.rohit.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService service;

	@GetMapping("/all")
	public List<Transaction> getAllTransactions() {
		return service.getAllTransactions();
	}
}
