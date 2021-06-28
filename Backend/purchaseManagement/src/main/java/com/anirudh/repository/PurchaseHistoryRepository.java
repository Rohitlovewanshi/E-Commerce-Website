package com.anirudh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anirudh.entity.UserPurchaseHistory;
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<UserPurchaseHistory,Integer>{
	//@Query("select pid from user_purchase_history u where u.uid=?1")
	//List<Integer> findOrders(int uid);
	List<UserPurchaseHistory> findByUid(int uid);

}
