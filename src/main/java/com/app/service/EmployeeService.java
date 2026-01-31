package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.EmployeeDao;
import com.app.pojos.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	public EmployeeService() {
	}

	public Employee findById(int id) {
		Optional<Employee> employee = employeeDao.findById(id);
		if (employee.isPresent())
			return employee.get();
		else
			return null;
	}

	public List<Employee> getAllByVendorId(int vendorId) {
		List<Employee> list = null;
		list = employeeDao.findAllByVendorId(vendorId);

		if (list != null)
			return list;
		else
			return null;

	}

	public Employee addEmployee(Employee newEmployee) {
		return employeeDao.save(newEmployee);
	}

	public Employee getByEmailId(String emailId) {

		return employeeDao.findByEmail(emailId);

	}

}
