package com.anirudh.controller;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anirudh.entity.UserOrderItems;
import com.anirudh.entity.UserPurchaseHistory;
import com.anirudh.service.KafkaProducerService;
import com.anirudh.service.PurchaseService;
import com.anirudh.vo.Order;
import com.anirudh.vo.Purchase;
import com.anirudh.vo.Status;
@RestController
@RequestMapping("purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private KafkaProducerService kafka;
	@PostMapping("userPurchase") 
	@CrossOrigin(origins = "*")
	public Status userPurchase(@RequestBody Purchase purchase ) { //adding the purchase products  of the user
		int c=0;
	    long l=System.currentTimeMillis();
	    int[] pids=purchase.getPids();
	    UserPurchaseHistory userPurchaseHistory=new UserPurchaseHistory();
	    userPurchaseHistory.setTime(String.valueOf(l));
	    userPurchaseHistory.setUid(purchase.getUid());
	    int orderid=purchaseService.getOrderid(userPurchaseHistory); // fetching the orderid of that order 
	    
	    for(int i=0;i<purchase.getPids().length;i++) { //adding the each product of the order to the orderitems db
	    	UserOrderItems order=new UserOrderItems(); 
	    	order.setOrderid(orderid);
	    	order.setPid(pids[i]);
	  
	    	int ch=purchaseService.userPurchase(order); //checking product is added or not 
	    	if(ch==201) {
	    		c+=1;
	    	}
	    }
		Status st=new Status();// status 201 when added successfully
		if(c==pids.length)
		    st.setStatus(201);
		else
			st.setStatus(404);
		JSONObject obj=new JSONObject();
		obj.put("message","userid:"+purchase.getUid()+ " purchased "+Arrays.toString(pids));
		obj.put("time", System.currentTimeMillis()+"");
		kafka.sendMessage(obj.toString());
		return st;
		
		
	}
	@GetMapping("orderHistory")// fetching the order history
	public List<Order>  orderHistroy(@RequestParam("uid") int uid){
		
		return purchaseService.orderHistory(uid);
	}

}
