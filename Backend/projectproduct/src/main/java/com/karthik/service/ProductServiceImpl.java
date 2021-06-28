package com.karthik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.karthik.dao.ProductDaoImpl;
import com.karthik.dao.ProductDaoInterface;
import com.karthik.entity.Product;
import com.karthik.entity.ProductCategories;

@Service
public class ProductServiceImpl implements ProductServiceInterface{
	@Autowired
	private ProductDaoInterface productdao;
	
	public List<Product> getallprods() {
		// TODO Auto-generated method stub
		return productdao.getallprods();
	}

	public List<Product> getprodbyid(int cid) {
		
		return productdao.getprobycat(cid);
	}

	public List<Product> getprodbyprice(int cid, float price) {
		// TODO Auto-generated method stub
		return productdao.getprodbyprice(cid,price);
	}

	public List<Product> getprobycomp(int cid, String company) {
		// TODO Auto-generated method stub
		return productdao.getprobycompany(cid,company);
	}

	public List<Product> getprodbystars(int cid, float stars) {
		// TODO Auto-generated method stub
		return productdao.getprodbystars(cid,stars);
	}



	public List<ProductCategories> getbycat() {
		// TODO Auto-generated method stub
		return productdao.getbycat();
	}

	public List<Product> getpriceallcat( float price) {
		// TODO Auto-generated method stub
		
		return productdao.getpriceallcat(price);
	}

	public List<Product> getcompanyallcat(String company) {
		// TODO Auto-generated method stub
		return productdao.getacompanyallcat(company);
	}

	public List<Product> getallprodbystars(float review_stars) {
		// TODO Auto-generated method stub
		return productdao.getallprodbystas(review_stars);
	}


}