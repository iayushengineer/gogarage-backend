package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CustomerDao;
import com.app.pojos.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public Customer findById(int id) {
		Optional<Customer> customer = customerDao.findById(id);
		if (customer.isPresent())
			return customer.get();
		else
			return null;
	}

	public Customer addCustomer(Customer newCustomer) {
		return customerDao.save(newCustomer);
	}

	public Customer getByEmailId(String email) {

		return customerDao.findByEmail(email);
	}

}
