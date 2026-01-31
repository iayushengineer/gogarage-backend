package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {

	Employee findByEmailAndPassword(String email, String password);

	Employee findByEmail(String email);

	List<Employee> findAllByVendorId(int vendorId);

}
