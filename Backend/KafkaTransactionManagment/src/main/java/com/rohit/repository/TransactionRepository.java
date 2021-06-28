package com.rohit.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>{

	
}
