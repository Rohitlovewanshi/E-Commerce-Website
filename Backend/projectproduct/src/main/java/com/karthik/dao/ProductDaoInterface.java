package com.karthik.dao;

import java.util.List;

import com.karthik.entity.Product;
import com.karthik.entity.ProductCategories;

public interface ProductDaoInterface {

	public List<Product> getallprods();
	public List<Product> getprobycat(int cid);
	public List<Product> getprodbyprice(int cid, float price) ;
	public List<Product> getprobycompany(int cid, String company);
	public List<Product> getprodbystars(int cid, float stars);
	public List<ProductCategories> getbycat() ;
	public List<Product> getacompanyallcat(String company);
	public List<Product> getallprodbystas(float review_stars);
	public List<Product> getpriceallcat(float price);
}
