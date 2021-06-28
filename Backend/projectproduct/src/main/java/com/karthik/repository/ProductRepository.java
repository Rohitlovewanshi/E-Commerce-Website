package com.karthik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.karthik.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query(value="SELECT p FROM Product p WHERE p.categoryid = ?1")
	public List<Product> getprodbycat(Integer cid);
	
	
	@Query(value="SELECT p from Product p WHERE p.categoryid=?1 and  p.price<?2 or p.price=?2 ORDER BY price DESC")
	public List<Product> getprodbyprice(int cid, float price);

	@Query(value="SELECT p from Product p WHERE p.categoryid=?1 and p.company=?2")
	public List<Product> getprodbycompany(int cid, String company);

	@Query(value="SELECT p from Product p WHERE p.categoryid=?1 and (p.user_review_stars>?2 or p.user_review_stars=?2) ORDER BY user_review_stars DESC")
	public List<Product> getbyprodstars(int cid, float stars);

	@Query(value="SELECT p from Product p where p.price<?1 or p.price=?1 ORDER BY price DESC")
	public List<Product> getprodallcatprice( float price);

	@Query(value="SELECT p from Product p WHERE p.company=?1 ORDER BY categoryid")
	public List<Product> getallprodcompanycat(String company);

	@Query(value="SELECT p from Product p where p.user_review_stars=?1 or p.user_review_stars>?1 ORDER BY user_review_stars DESC")
	public List<Product> getallprodbystars(float review_stars);
	

}
