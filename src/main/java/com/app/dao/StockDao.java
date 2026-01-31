package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Stock;

public interface StockDao extends JpaRepository<Stock, Integer> {
	
	
	 
	
	List<Stock> findByQuantity(int quantity); 
	

}
