package com.anirudh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anirudh.entity.ProductCategories;
@Repository
public interface Categories extends JpaRepository<ProductCategories,Integer>{

}
