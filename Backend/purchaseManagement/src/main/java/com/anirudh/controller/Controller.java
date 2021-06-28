package com.anirudh.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anirudh.service.KafkaProducerService;
import com.anirudh.service.PurchaseService;
import com.anirudh.entity.Product;
import com.anirudh.entity.ProductCategories;
import com.anirudh.entity.UserPurchaseHistory;
import com.anirudh.vo.Order;
import com.anirudh.vo.Status;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*")
public class Controller {
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private Product product;
	@Autowired
	private ProductCategories productCategory;
	@Autowired
	private KafkaProducerService kafka;
	@PostMapping(value = "addProduct") //adding the product 
	public Status addProduct(@RequestParam ("pname") String pname,@RequestParam ("company") String company,@RequestParam ("price") float price,@RequestParam (value="description",defaultValue="best") String description,@RequestParam (value="user_review_stars",defaultValue="0") float user_review_stars,@RequestParam ("categoryid") int categoryid,@RequestParam(value="image_url",defaultValue="no_image") String image_url) {
		product.setPname(pname);
		product.setCompany(company);
		product.setPrice(price);
		product.setDescription(description);
		product.setUser_review_stars(user_review_stars);
		product.setCategoryid(categoryid);
		product.setImage_url(image_url);
		int s=purchaseService.addProduct(product);
		Status st=new Status(); //status 201 when created and status 404 when category id not found
		st.setStatus(s);
		JSONObject obj=new JSONObject();  //kafka message
		obj.put("message", "product "+pname+" added");
		obj.put("time", System.currentTimeMillis()+"");
		kafka.sendMessage(obj.toString());
		return st;
			
	}
	@DeleteMapping(value="deleteProduct/{pid}") // deleting the product 
	public Status delProduct(@PathVariable("pid") Integer pid ) {
		int s=purchaseService.delProduct(pid);
		Status st=new Status();// status 200 when deleted and status 404 when product not found
		st.setStatus(s);
		if(s==200) {
		JSONObject obj=new JSONObject(); //kafka message
		obj.put("message", "product "+pid+" deleted");
		obj.put("time", System.currentTimeMillis()+"");
		kafka.sendMessage(obj.toString());}
		
		return st;
	 }
	@PostMapping(value="addCategory") // adding the category
	public Map<String,String> addCategory(@RequestParam ("categoryname") String categoryname ) {
		productCategory.setCategoryname(categoryname);
		HashMap<String,String> map=purchaseService.addCategory(productCategory);// return hashmap contain status and categoryid
		JSONObject obj=new JSONObject(); //kafka message
		obj.put("message", "category "+categoryname+" added");
		obj.put("time", System.currentTimeMillis()+"");
		kafka.sendMessage(obj.toString());
		return map;
	}
	@PutMapping(value="updateProduct") // updating the product
	public Status updateProduct(@RequestParam ("pid") int pid,@RequestParam ("pname") String pname,@RequestParam ("company") String company,@RequestParam ("price") float price,@RequestParam (value="description",defaultValue="best") String description,@RequestParam (value="user_review_stars",defaultValue="0") float user_review_stars,@RequestParam ("categoryid") int categoryid,@RequestParam(value="image_url",defaultValue="no_image") String image_url) {
		product.setPid(pid);
		product.setPname(pname);
		product.setCompany(company);
		product.setPrice(price);
		product.setDescription(description);
		product.setUser_review_stars(user_review_stars);
		product.setCategoryid(categoryid);
		product.setImage_url(image_url);
		int s=purchaseService.updateProduct(product);
		Status st=new Status(); //status 200 when updated and 404 when product not found
		st.setStatus(s);
		if(s==200) {
		JSONObject obj=new JSONObject(); //kafka message
		obj.put("message", "product "+pid+" updated");
		obj.put("time", System.currentTimeMillis()+"");
		kafka.sendMessage(obj.toString());}
		
		return st;
	}
	
	
}
