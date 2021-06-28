package com.karthik;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.entity.Product;
import com.karthik.entity.ProductCategories;
import com.karthik.service.ProductServiceImpl;
import com.karthik.service.ProductServiceInterface;

	@RestController
	@CrossOrigin(origins = "*")
	@RequestMapping("/products")
	public class ProductController {
		
		@Autowired
		private ProductServiceInterface productservice;
		
		//url:/products/
		@RequestMapping("/")
		public List<Product> getproducts()//return all the products irrespective of category
		{
			 return productservice.getallprods();
		}
		
		
		//url=/products/categoryid/2
		@GetMapping("/categoryid/{categoryid}")
		public List<Product> prodbycategory(@PathVariable("categoryid") int categoryid)
		{
			
			return productservice.getprodbyid(categoryid);
			
		}
		
		//url:/products/filter/price?price=10&categoryid=1
		@GetMapping("/filter/price")
		public List<Product> prodbycatprice(@RequestParam(name="price") float price,@RequestParam(name="categoryid") int categoryid)
		{
			if(categoryid==-1)//To return all the products independent  of the category based on price
			{
				return productservice.getpriceallcat(price);
				
			}
			return productservice.getprodbyprice(categoryid,price);
		}
		
		//url:/products/filter/company?company=abc&categoryid=2
		@GetMapping("/filter/company")
		public List<Product> prodbycatcompany(@RequestParam(name="company") String company,@RequestParam(name="categoryid") int categoryid)
		{
			if(categoryid==-1)//To return all the products independent  of the category based on company
			{
				return productservice.getcompanyallcat(company);
			}
		return productservice.getprobycomp(categoryid,company);
		}		
		
		
		//url : products/filter/rating?review_stars=4&categoryid=2
		@GetMapping("/filter/rating")
		public List<Product> prodbystars(@RequestParam("review_stars") float review_stars,@RequestParam(name="categoryid") int categoryid)
		{	
			
			if(categoryid==-1)//To return all the products independent  of the category based on rating
			{
				
				return productservice.getallprodbystars(review_stars);
						
			}
			return productservice.getprodbystars(categoryid,review_stars);
		}

		//Path : /products/categories
		@GetMapping("/categories")//returns list of categories available
		public List<ProductCategories> prodbycat()
		{
			return productservice.getbycat();
		}
		//3.21.230.115
	}


