package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.app.dao.ServiceRequestDao;
import com.app.dao.VendorDao;
import com.app.pojos.Customer;
import com.app.pojos.Feedback;
import com.app.pojos.ServiceRequest;
import com.app.service.CustomerService;
import com.app.service.ServiceRequestService;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private VendorDao vendorDao;

  @Autowired
  private EmployeeDao employeeDao;

  @Autowired
  private ServiceRequestDao serviceRequestDao;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ServiceRequestService serviceRequestService;

  @Autowired
  private FeedbackDao feedbackDao;

  public CustomerController() {}

  @GetMapping("/{id}")
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

  @PutMapping("/editCustomer/{id}")
  public ResponseEntity<?> updateCustomer(@PathVariable(value = "id") int customer_id,
      @Valid @RequestBody Customer customerDetails) throws Exception {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    customerDetails.setId(customer_id);

    if (customerDao.save(customerDetails) != null) {
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Student Not Found");
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

  @PostMapping("/addFeedback")
  public ResponseEntity<?> createFeedback(@Valid @RequestBody Feedback feedback) {

    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();

    if (feedbackDao.save(feedback) != null) {
      map.put("status", "success");
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      map.put("status", "error");
      map.put("error", "Can't Add feedback");
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
      map.put("error", "Can't delete feedback");
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
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      map.put("error", "Customers Not Found");
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return resp;

  }

  @GetMapping("/AllCount")
  public ResponseEntity<?> getAllCount() {
    ResponseEntity<?> resp = null;
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      long vendors = vendorDao.count();
      long employees = employeeDao.count();
      long customers = customerDao.count();
      long serviceRequest = serviceRequestDao.count();

      map.put("status", "success");
      map.put("vendors", vendors);
      map.put("employees", employees);
      map.put("customers", customers);
      map.put("serviceRequest", serviceRequest);
      resp = new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println("Exception : " + e.getMessage());
      map.put("status", "error");
      map.put("error", e.getMessage());
      resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return resp;

  }

  @GetMapping("/service/{customer_id}")
  public ResponseEntity<?> getCustomerServiceRequestById(@PathVariable int customer_id) {

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

}
