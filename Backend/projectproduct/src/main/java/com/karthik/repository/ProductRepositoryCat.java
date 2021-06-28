package com.karthik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karthik.entity.ProductCategories;

public interface ProductRepositoryCat extends JpaRepository<ProductCategories,Integer>{

}
