package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.CustomerDao;
import com.app.dao.EmployeeDao;
import com.app.dao.FeedbackDao;
import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.Feedback;
import com.app.pojos.User;
import com.app.pojos.Vendor;
import com.app.service.EmployeeService;
import com.app.service.UserService;
import com.app.service.VendorService;

@CrossOrigin
@RestController 
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private FeedbackDao feedbackDao;

	@Autowired
	private UserService userService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public VendorController() {
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVendorById(@PathVariable int id) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Vendor vendor = vendorService.findById(id);

			map.put("status", "success");
			map.put("data", vendor);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", e.getMessage());
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	@GetMapping("/byEmail/{emailId}")
	public ResponseEntity<?> getVendorByEmailId(@PathVariable String emailId) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Vendor vendor = vendorService.getByEmailId(emailId);

			map.put("status", "success");
			map.put("data", vendor);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", e.getMessage());
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}


	@GetMapping("/employeeList")
	public ResponseEntity<?> fetchAllEmployees() {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Employee> employees = employeeDao.findAll();
			map.put("status", "success");
			map.put("data", employees);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
		}

		return resp;
	}


	@GetMapping("/employeeList/{vendorId}")
	public ResponseEntity<?> fetchAllEmployees(@PathVariable int vendorId) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Employee> employees = employeeDao.findAllByVendorId(vendorId);
			map.put("status", "success");
			map.put("data", employees);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", "Employees Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Employee employee = employeeService.findById(id);
			map.put("status", "success");
			map.put("data", employee);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
		}

		return resp;
	}

	@PostMapping("/addEmployee")
	public ResponseEntity<?> EMployeeSignup(@RequestBody Employee newEmployee) throws AuthenticationException {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		// Encrypt password before saving
		String encodedPassword = passwordEncoder.encode(newEmployee.getPassword());
		newEmployee.setPassword(encodedPassword);

		if (userService.addUser(new User(newEmployee.getEmail(), encodedPassword, "EMPLOYEE", 1)) != null
				&& employeeService.addEmployee(newEmployee) != null) {
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Can't Add Employee");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int employee_id) throws Exception {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Employee employee = employeeDao.findById(employee_id)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employee_id));

		try {

			userService.deleteUser(employee.getEmail());
			employeeDao.delete(employee);
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {

			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", "Employee Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
		}

		return resp;
	}


	@PutMapping("/editEmployee/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") int employee_id,
			@Valid @RequestBody Employee employeeDetails) throws Exception {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Employee employee = null;
		employee = employeeDao.findById(employee_id)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employee_id));

		if (employee != null) {
			employee.setEmail(employeeDetails.getEmail());
			employee.setPassword(employeeDetails.getPassword());
			employee.setName(employeeDetails.getName());
			employee.setBirthDate(employeeDetails.getBirthDate());
			final Employee updatedEmployee = employeeDao.save(employee);
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Employee Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;

	}

	@GetMapping("/customer-management/list")
	public ResponseEntity<?> fetchAllCustomers() {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Customer> customers = customerDao.findAll();
			map.put("status", "success");
			map.put("data", customers);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", "Customers Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable int id) {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Customer customer = customerDao.findById(id)
					.orElseThrow(() -> new Exception("Customer not found for this id :: " + id));
			map.put("status", "success");
			map.put("data", customer);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", "Customer Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;

	}

	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") int customer_id) throws Exception {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Customer customer = customerDao.findById(customer_id)
				.orElseThrow(() -> new Exception("Customer not found for this id :: " + customer_id));

		try {
			userService.deleteUser(customer.getEmail());
			customerDao.delete(customer);
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", "Customer Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}
	@GetMapping("/Feedbacklist")
	public ResponseEntity<?> fetchAllFeedbacks() {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Feedback> feedbacks = Feedback.findAll();
			map.put("status", "success");
			map.put("data", feedbacks);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}
	@DeleteMapping("/deleteFeedback/{id}")
	public ResponseEntity<?> deleteFeedback(@PathVariable(value = "id") int id) throws Exception {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Feedback feedback = feedbackDao.findById(id)
				.orElseThrow(() -> new Exception("Feedback not found for this id :: " + id));

		try {
			feedbackDao.delete(feedback);
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}
}
