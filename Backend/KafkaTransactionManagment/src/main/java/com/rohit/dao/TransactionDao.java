package com.rohit.dao;

import java.util.List;

import com.rohit.entity.Transaction;

public interface TransactionDao {

	public void saveTransaction(Transaction transaction);

	List<Transaction> getAllTransactions();
}
