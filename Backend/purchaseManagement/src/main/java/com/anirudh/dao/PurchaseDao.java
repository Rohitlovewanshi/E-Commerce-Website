package com.anirudh.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anirudh.repository.Categories;
import com.anirudh.repository.PurchaseHistoryRepository;
import com.anirudh.repository.PurchaseRepository;
import com.anirudh.repository.UserOrderRepository;
import com.anirudh.vo.Order;
import com.anirudh.entity.Product;
import com.anirudh.entity.ProductCategories;
import com.anirudh.entity.UserOrderItems;
import com.anirudh.entity.UserPurchaseHistory;
@Repository
public class PurchaseDao {
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private Categories categories;
	@Autowired
	private PurchaseHistoryRepository purchaseHistory;
	@Autowired
	private UserOrderRepository orderRepository;

	public int addProduct(Product product) {                           //adding the product
		Optional<ProductCategories> c=categories.findById(product.getCategoryid());
		if (c.isPresent()) {
			purchaseRepository.save(product);   
			return 201;
		}
		
		return 404;
		
		
	}
	public int delProduct(Integer id) {       //deleting the product by id     
		Optional<Product> p=purchaseRepository.findById(id);
		if(p.isPresent()) {
			purchaseRepository.deleteById(id); 
			return 200;
		}
		return 404;
	}
	public HashMap<String, String> addCategory(ProductCategories productCategorie) {     //adding the category
		ProductCategories temp=categories.save(productCategorie);
		HashMap<String, String> map=new HashMap<>();
		map.put("Status", "201");
		map.put("categoryid", String.valueOf(temp.getCategoryid()));
		return map;
	}
	public int updateProduct(Product product) {                // updating the product
		Optional<Product> p=purchaseRepository.findById(product.getPid());
		if(p.isPresent()) {
			purchaseRepository.save(product);
			return 200;	
		}
		return 404;
		
		
	}
	
	public int userPurchase(UserOrderItems order) {       //adding the order details
		orderRepository.save(order);
		return 201;
	}
	public List<UserPurchaseHistory> orderHistory(int uid) {   //fetching the orderhistory 
		List<UserPurchaseHistory> l=purchaseHistory.findByUid(uid);
		return l;
		
	}
	public Product getProduct(int pid) {    //fetching the product
		return purchaseRepository.findById(pid).get();
	}
	public int getOrderid(UserPurchaseHistory userPurchaseHistory) {     //fetching the order id
		UserPurchaseHistory temp=purchaseHistory.save(userPurchaseHistory);
		
		return temp.getOrderid();
	}
	public List<UserOrderItems> orderItems(int orderid) {     //fetching order items of that particular order
		List<UserOrderItems> l=   orderRepository.findByOrderid(orderid);
		return l;
	}

}
