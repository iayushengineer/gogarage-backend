package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.Feedback;
import com.app.pojos.ServiceRequest;
import com.app.pojos.User;
import com.app.service.CustomerService;
import com.app.service.ServiceRequestService;
import com.app.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  private EmployeeDao employeeDao;
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private ServiceRequestService serviceRequestService;
  @Autowired
  private UserService userService;
  @Autowired
  private CustomerService customerService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public EmployeeController() {}

  @GetMapping("/{emp_id}")
  public ResponseEntity<?> getProfile(@PathVariable(value = "emp_id") int emp_id) {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    Optional<Employee> emp = employeeDao.findById(emp_id);

    if (emp.isPresent()) {
      map.put("status", "success");
      map.put("data", emp.get());
      resp = new ResponseEntity<>(map, HttpStatus.OK);

    } else {
      map.put("status", "error");
      map.put("error", "employee not found");
      resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);

    }
    return resp;
  }

  @PutMapping("/editEmployee/{id}")
  public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") int employee_id,
      @Valid @RequestBody Employee employeeDetails) throws Exception {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    employeeDetails.setId(employee_id);

    if (employeeDao.save(employeeDetails) != null) {
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);

    } else {
      map.put("status", "error");
      map.put("error", "Employee Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;
  }

  @PostMapping("/customer/signup")
  public ResponseEntity<?> customerSignup(@RequestBody Customer newCustomer)
      throws AuthenticationException {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    // Validate required fields
    if (newCustomer.getPassword() == null || newCustomer.getPassword().trim().isEmpty()) {
      map.put("status", "error");
      map.put("error", "Password is required");
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    if (newCustomer.getEmail() == null || newCustomer.getEmail().trim().isEmpty()) {
      map.put("status", "error");
      map.put("error", "Email is required");
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    // Encrypt password before saving
    String encodedPassword = passwordEncoder.encode(newCustomer.getPassword());
    newCustomer.setPassword(encodedPassword);

    if (userService
        .addUser(new User(newCustomer.getEmail(), encodedPassword, "CUSTOMER", 1)) != null
        && customerService.addCustomer(newCustomer) != null) {
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Can't Add Customer");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }

  @GetMapping("/customerList/{employeeId}")
  public ResponseEntity<?> fetchAllCustomer(@PathVariable int employeeId) {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    try {
      List<Customer> customer = customerDao.findAllByEmployeeId(employeeId);
      map.put("status", "success");
      map.put("data", customer);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
    }

    return resp;
  }

  @GetMapping("/customer/{id}")
  public ResponseEntity<?> getCustomerById(@PathVariable int id) {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    try {
      Customer customer = customerService.findById(id);
      map.put("status", "success");
      map.put("data", customer);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      map.put("error", "Customers Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;

  }

  @DeleteMapping("/deleteCustomer/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") int employee_id)
      throws Exception {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    Customer customer = null;
    customer = customerDao.findById(employee_id)
        .orElseThrow(() -> new Exception("Customer not found for this id :: " + employee_id));

    if (customer != null) {
      userService.deleteUser(customer.getEmail());
      customerDao.delete(customer);
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Customer Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;
  }

  @GetMapping("/service/{customer_id}")
  public ResponseEntity<?> getCustomerServiceRequestById(@PathVariable int customer_id) {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    try {
      ServiceRequest service =
          serviceRequestService.getByCustomerIdAndStatus(customer_id, "PENDING");
      map.put("status", "success");
      map.put("data", service);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      map.put("error", "Customers Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;

  }

  @PostMapping("/addService/{customer_id}")
  public ResponseEntity<?> createService(@Valid @RequestBody ServiceRequest serviceRequest,
      @PathVariable int customer_id) {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();
    serviceRequest.setCustomerId(customer_id);
    serviceRequest.setStatus("CREATED");
    if (serviceRequestService.addServiceRequest(serviceRequest) != null) {
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Can't Add service request");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }

  @PostMapping("/updateService/{request_id}")
  public ResponseEntity<?> updateService(@Valid @RequestBody ServiceRequest serviceRequest,
      @PathVariable int request_id) {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();
    serviceRequest.setRequestId(request_id);
    if (serviceRequestService.addServiceRequest(serviceRequest) != null) {
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Can't Update service request");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }

  @PutMapping("/service/acceptServiceRequest/{customer_id}")
  public ResponseEntity<?> updateStatus(@PathVariable int customer_id) throws Exception {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    ServiceRequest request = null;
    request = serviceRequestService.getByCustomerIdAndStatus(customer_id, "REQUESTED");

    if (request != null) {
      request.setStatus("PENDING");
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);

    } else {
      map.put("status", "error");
      map.put("error", "Customer Service  Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;
  }

  @PutMapping("/service/createInvoice/{request_id}")
  public ResponseEntity<?> makeInvoiceFromServiceRequest(@PathVariable int request_id,
      @RequestBody ServiceRequest serviceRequest) throws Exception {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();
    ServiceRequest request = null;
    request = serviceRequestService.getByRequestId(request_id);
    if (request != null) {
      request.setOutDate(serviceRequest.getOutDate());
      request.setDiscount(serviceRequest.getDiscount());
      request.setStatus("INVOICEGENERATED");
      request.setLabourCharges(serviceRequest.getLabourCharges());
      request.setProductCharges(serviceRequest.getProductCharges());
      request.setTotal(serviceRequest.getTotal());
      request.setPacakgeId(serviceRequest.getPacakgeId());
      serviceRequestService.addServiceRequest(request);
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Customer Service Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }

  @GetMapping("/viewInvoice/{customer_id}")
  public ResponseEntity<?> getInvoiceById(@PathVariable int customer_id) {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    try {
      ServiceRequest service = serviceRequestService.getByCustomerId(customer_id);
      map.put("status", "success");
      map.put("data", service);
      System.out.println("get servicing");
      System.out.println(service);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      map.put("error", "Customers Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;

  }

  @GetMapping("/customerlist")
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

  @GetMapping("/services/{customer_id}")
  public ResponseEntity<?> getServiceRequestByCustomerId(@PathVariable int customer_id) {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      List<ServiceRequest> serviceList = serviceRequestService.getAllServices(customer_id);
      map.put("status", "success");
      map.put("data", serviceList);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      map.put("error", "Customers Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }



  @PutMapping("/editCustomer/{id}")
  public ResponseEntity<?> updateCustomer(@PathVariable(value = "id") int customer_id,
      @Valid @RequestBody Customer customerDetails) throws Exception {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    Customer customer = null;
    customer = customerDao.findById(customer_id)
        .orElseThrow(() -> new Exception("Customer not found for this id :: " + customer_id));

    if (customer != null) {
      customer.setName(customerDetails.getName());
      customer.setBirthDate(customerDetails.getBirthDate());
      customer.setAddress(customerDetails.getAddress());
      customer.setContact(customerDetails.getContact());

      customerDao.save(customer);
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "customer Not Found");
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

  @GetMapping("/servicereq/{requestId}")
  public ResponseEntity<?> getServiceById(@PathVariable int requestId) {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      ServiceRequest service = serviceRequestService.getByRequestId(requestId);
      map.put("status", "success");
      map.put("data", service);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      map.put("status", "error");
      map.put("error", "service request Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;
  }

}
