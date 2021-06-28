package com.karthik.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Product {
	@Id
	@GeneratedValue
	private int pid;
	private String pname;
	private String company;
	private float price;
	private String description;
	private float user_review_stars;
	private int categoryid;
	private String image_url;
	
	

}