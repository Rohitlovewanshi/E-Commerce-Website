package com.anirudh.vo;

import java.io.Serializable;
import java.util.List;

import com.anirudh.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable{
     private int orderid;
     private String time;
     private List<Product> product;
}
