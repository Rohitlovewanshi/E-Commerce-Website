package com.karthik.service;

import java.util.List;

import com.karthik.entity.Product;
import com.karthik.entity.ProductCategories;

public interface ProductServiceInterface {
	public List<Product> getallprods();
	public List<Product> getprodbyid(int cid);
	public List<Product> getprodbyprice(int cid, float price);
	public List<Product> getprobycomp(int cid, String company);
	public List<Product> getprodbystars(int cid, float stars);
	public List<ProductCategories> getbycat() ;
	public List<Product> getpriceallcat( float price);
	public List<Product> getcompanyallcat(String company);
	public List<Product> getallprodbystars(float review_stars);
}
