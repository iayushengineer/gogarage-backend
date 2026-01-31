package com.app.service;

import com.app.pojos.Vendor;

public interface IVendorService {

	
	Vendor addVendor(Vendor newVendor);
	
Vendor findById(int id);
Vendor getByEmailId(String email);
void deleteVendor(int id);
	
}
