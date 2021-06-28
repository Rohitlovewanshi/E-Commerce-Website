package com.myaccount.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private int pid;
	private String pname;
	private String company;
	private double price;
	private String description;
	private double user_review_stars;
	private String image_url;
	private int categoryid;
	
}
