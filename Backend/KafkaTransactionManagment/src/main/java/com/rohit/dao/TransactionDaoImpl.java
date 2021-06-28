package com.rohit.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rohit.entity.Transaction;
import com.rohit.repository.TransactionRepository;

@Repository
public class TransactionDaoImpl implements TransactionDao{
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

}
