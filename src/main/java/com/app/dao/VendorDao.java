package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Vendor;;

public interface VendorDao extends JpaRepository<Vendor, Integer> {

	Vendor findByEmailAndPassword(String email,String password); 
	
	Vendor findByEmail(String email); 
	
}
