package com.anirudh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anirudh.dao.PurchaseDao;
import com.anirudh.entity.Product;
import com.anirudh.entity.ProductCategories;
import com.anirudh.entity.UserOrderItems;
import com.anirudh.entity.UserPurchaseHistory;
import com.anirudh.vo.Order;
@Service
public class PurchaseService {
	@Autowired
	private PurchaseDao purchaseDao;
	

	public int addProduct(Product product) {        //adding product
	
		return purchaseDao.addProduct(product);
	}
	public int delProduct(Integer id) {             //deleting product
		return purchaseDao.delProduct(id);
	}
	public HashMap<String, String> addCategory(ProductCategories productCategorie) {    //adding category
		
		return purchaseDao.addCategory(productCategorie);
	}
	public int updateProduct(Product product) {          //updating product
	
		return purchaseDao.updateProduct(product);
	}
	
	public int userPurchase(UserOrderItems order) {    //adding purchase order details
		
		return purchaseDao.userPurchase(order);
		
	}
	public List<Order> orderHistory(int uid) {   //fetching order history details of user
	
		List<UserPurchaseHistory> list=purchaseDao.orderHistory(uid);
		System.out.println(list);
		
		List<Order> orders = new ArrayList<>();
		for(UserPurchaseHistory u:list) {
			Order order=new Order();
			order.setTime(u.getTime());
			order.setOrderid(u.getOrderid());
			List<UserOrderItems> list2=purchaseDao.orderItems(u.getOrderid());
			List<Product> products=new ArrayList<>();
			for(UserOrderItems a:list2) {
				products.add(purchaseDao.getProduct(a.getPid()));
			}
			order.setProduct(products);
			orders.add(order);
		}
		return orders;
		
		
	}
	public int getOrderid(UserPurchaseHistory userPurchaseHistory) { //fetching orderid
		
		return purchaseDao.getOrderid( userPurchaseHistory);
	}

}
