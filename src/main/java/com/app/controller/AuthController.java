package com.app.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.customException.UserDeactivateException;
import com.app.pojos.Admin;
import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.LoginCredentials;
import com.app.pojos.User;
import com.app.pojos.Vendor;
import com.app.service.AdminService;
import com.app.service.CustomerService;
import com.app.service.EmployeeService;
import com.app.service.UserService;
import com.app.service.VendorService;
import com.app.utils.JwtTokenUtil;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	AdminService adminService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	EmployeeService employeeService;
	
	
	@Autowired
	CustomerService customerService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthController() {
	}

	@PostMapping("/signin")
	public ResponseEntity<?> userLogin(@RequestBody LoginCredentials loginCredentials)  throws AuthenticationException {

		System.out.println("before findbyEmail" + loginCredentials.getEmail());
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword()));
		
		System.out.println("before findbyEmail" + loginCredentials.getEmail());
		
		
		User foundedUser = userService.findByEmail(loginCredentials.getEmail()); 
		
		System.out.println("user details" + foundedUser.toString());
		
		
		if(foundedUser.getActive() == 0)
			throw new UserDeactivateException("User is Deactivated");
		
		System.out.println(foundedUser);

		Map<String, String> claims = new HashMap<String, String>();
		
	    claims.put("email", foundedUser.getEmail());
		claims.put("role", foundedUser.getRole());
		
	
		final String token = jwtTokenUtil.generateToken(claims);

		Map<String, Object> map = new HashMap<String, Object>();
		int id = 0;
		if(foundedUser.getRole().equals("ADMIN"))
		{
			Admin admin = adminService.getByEmailId(foundedUser.getEmail());
			id = admin.getId();
		}else if(foundedUser.getRole().equals("VENDOR"))
		{
			Vendor vendor = vendorService.getByEmailId(foundedUser.getEmail());
			id = vendor.getId();
		}else if(foundedUser.getRole().equals("EMPLOYEE"))
		{
			Employee employee = employeeService.getByEmailId(foundedUser.getEmail());
			id = employee.getId();
		}else if(foundedUser.getRole().equals("CUSTOMER"))
		{
			Customer customer = customerService.getByEmailId(foundedUser.getEmail());
			id = customer.getId();
		}else
		{
			
		}
		map.put("status", "success");
		map.put("token", token);
		map.put("role", foundedUser.getRole());
        map.put("email", foundedUser.getEmail());
        map.put("id", id);
		ResponseEntity<?> resp = new ResponseEntity<>(map, HttpStatus.OK);
		return resp;
	}
	
	
	
	
	@PostMapping("/admin/signup")
	public ResponseEntity<?> adminSignup(@RequestBody Admin newAdmin)  throws AuthenticationException {
		
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("New Admin : "+newAdmin);
		
		// Encrypt password before saving
		String encodedPassword = passwordEncoder.encode(newAdmin.getPassword());
		newAdmin.setPassword(encodedPassword);
		
		if (userService
				.addUser(new User(newAdmin.getEmail(), encodedPassword,"ADMIN",1)) != null && adminService.addAdmin(newAdmin) != null) {
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Can't Add Admin");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@PostMapping("/customer/signup")
	public ResponseEntity<?> customerSignup(@RequestBody Customer newCustomer)  throws AuthenticationException {
		
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("New Customer : "+ newCustomer);
		
		// Encrypt password before saving
		String encodedPassword = passwordEncoder.encode(newCustomer.getPassword());
		newCustomer.setPassword(encodedPassword);
		
		if (userService
				.addUser(new User(newCustomer.getEmail(), encodedPassword,"CUSTOMER",1)) != null && customerService.addCustomer(newCustomer) != null) {
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Can't Add Customer");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
}
