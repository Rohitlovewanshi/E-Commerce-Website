package com.myaccount.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory implements Serializable {

	private int orderid;
	private String time;
	private List<Product> product;
	
}
