package com.anirudh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anirudh.entity.UserOrderItems;



public interface UserOrderRepository extends JpaRepository<UserOrderItems,Integer> {
	List<UserOrderItems> findByOrderid(int orderid);

}
