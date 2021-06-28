package com.anirudh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anirudh.entity.Product;


@Repository
public interface PurchaseRepository extends JpaRepository<Product,Integer>{

}
