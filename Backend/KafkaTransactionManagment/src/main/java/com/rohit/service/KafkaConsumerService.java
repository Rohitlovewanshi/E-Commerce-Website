package com.rohit.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rohit.dao.TransactionDao;
import com.rohit.entity.Transaction;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private TransactionDao transactionDao;

	@KafkaListener(topics = "Transaction",groupId = "group1")
	public void consume(String message) {
		JSONObject jsonObject=new JSONObject(message);
		Transaction transaction=new Transaction();
		transaction.setMessage(jsonObject.getString("message"));
		transaction.setTime(jsonObject.getString("time"));
		transactionDao.saveTransaction(transaction);
	}
}
