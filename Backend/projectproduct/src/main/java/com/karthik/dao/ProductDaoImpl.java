package com.karthik.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.karthik.entity.Product;
import com.karthik.entity.ProductCategories;
import com.karthik.repository.ProductRepository;
import com.karthik.repository.ProductRepositoryCat;


@Repository
public class ProductDaoImpl implements ProductDaoInterface{
	@Autowired
	private ProductRepository productrepository;
		
	@Autowired
	private ProductRepositoryCat productrepositorycat;
	
	public List<Product> getallprods() 
		{
			return productrepository.findAll();
		}

	public List<Product> getprobycat(int cid) 
	{
		
		return productrepository.getprodbycat(cid);
	}

	public List<Product> getprodbyprice(int cid, float price) 
	{
	
		return productrepository.getprodbyprice(cid,price);
	}

	public List<Product> getprobycompany(int cid, String company) 
	{
		
		return productrepository.getprodbycompany(cid,company);
	}

	public List<Product> getprodbystars(int cid, float stars) 
	{
		return productrepository.getbyprodstars(cid,stars);
	}


	public List<ProductCategories> getbycat() 
	{
	
		return productrepositorycat.findAll();
	}

	public List<Product> getpriceallcat( float price) 
	{
	
		return productrepository.getprodallcatprice(price);
	}

	public List<Product> getacompanyallcat(String company) 
	{
	
		return productrepository.getallprodcompanycat(company);
	}

	public List<Product> getallprodbystas(float review_stars) {

		return productrepository.getallprodbystars(review_stars);
	}

	
	}
		

	
