package com.myaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

	@Autowired
	KafkaTemplate<String, String> kafkatemplate;

	public void sendMessage(String message) {
		kafkatemplate.send("Transaction", message);
	}
}
