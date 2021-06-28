package com.anirudh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderItems {
	 @Id
	 @GeneratedValue
     private int orderitemid;
     private int orderid;
     private int pid;
}
