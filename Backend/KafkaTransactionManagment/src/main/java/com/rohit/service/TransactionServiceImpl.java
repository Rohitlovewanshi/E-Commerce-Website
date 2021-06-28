package com.rohit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.dao.TransactionDao;
import com.rohit.entity.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionDao dao;
	
	@Override
	public List<Transaction> getAllTransactions() {
		return dao.getAllTransactions();
	}
}
