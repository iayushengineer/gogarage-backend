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

import com.app.customException.RecordNotFoundException;
import com.app.dao.AdminDao;
import com.app.dao.CustomerDao;
import com.app.dao.EmployeeDao;
import com.app.dao.FeedbackDao;
import com.app.dao.OfferDao;
import com.app.dao.VendorDao;
import com.app.pojos.Admin;
import com.app.pojos.Offer;
import com.app.pojos.User;
import com.app.pojos.Vendor;
import com.app.service.AdminService;
import com.app.service.UserService;
import com.app.service.VendorService;

@CrossOrigin
@RestController 
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private VendorDao vendorDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private FeedbackDao feedbackDao;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private OfferDao offerDao;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public AdminController() {
	}
//admin list
	@GetMapping("/list")
	public ResponseEntity<?> fetchAllAdmins() {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Admin> admins = adminDao.findAll();
			map.put("status", "success");
			map.put("data", admins);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", e.getMessage());
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

	// get admin by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getAdminById(@PathVariable int id) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Admin admin = adminService.findById(id);
			map.put("status", "success");
			map.put("data", admin);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", e.getMessage());
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}
	
	// List of all vendors
	
	@GetMapping("/vendorlist")
	public ResponseEntity<?> fetchAllVendors() {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Vendor> vendors = vendorDao.findAll();

			map.put("status", "success");
			map.put("data", vendors);
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", e.getMessage());
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	// Get vendor by id
	
	@GetMapping("/vendor/{id}")
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

	// Add vendor 
	
	@PostMapping("/vendor/signup")
	public ResponseEntity<?> VendorSignup(@RequestBody Vendor newVendor) throws AuthenticationException {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		// Encrypt password before saving
		String encodedPassword = passwordEncoder.encode(newVendor.getPassword());
		newVendor.setPassword(encodedPassword);
		
		if (userService.addUser(new User(newVendor.getEmail(), encodedPassword, "VENDOR", 1)) != null
				&& vendorService.addVendor(newVendor) != null) {
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Can't Add Vendor");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// Delete vendor
	 
	@DeleteMapping("/deleteVendor/{id}")
	public ResponseEntity<?> deleteVendor(@PathVariable int id) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		Optional<Vendor> vendor = vendorDao.findById(id);

		try {
			if (vendor.isPresent()) {
				userService.deleteUser(vendor.get().getEmail());

				vendorService.deleteVendor(id);
				map.put("status", "success");
				resp = new ResponseEntity<>(map, HttpStatus.OK);
			} else {

			}

		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
			map.put("status", "error");
			map.put("error", "Can't delete Vendor");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	// Edit vendor
	
	@PutMapping("/editVendor/{id}")
	public ResponseEntity<?> updateVendor(@PathVariable(value = "id") int vendor_Id,
			@Valid @RequestBody Vendor vendorDetails) throws RecordNotFoundException {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		vendorDetails.setId(vendor_Id);

		if (vendorDao.save(vendorDetails) != null) {
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);

		} else {
			map.put("status", "error");
			map.put("error", "Student Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;

	}
	
  //Create Offer
	
		@PostMapping("/addOffer")
	public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer) {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("New Offer : " + offer);
		if (offerDao.save(offer) != null) {
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Can't Add Offer");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
		// List of all Offers
		
	@GetMapping("/offerlist")
	public ResponseEntity<?> fetchAllOffers() {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("in fetch all offer");

		List<Offer> offers = offerDao.findAll();

		if (offers.isEmpty()) {
			map.put("status", "error");
			map.put("error", "no offer found");
			resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
		} // non empty list
		else {
			map.put("status", "success");
			map.put("data", offers);
			resp = new ResponseEntity<>(map, HttpStatus.OK);

		}
		return resp;
	}

	//Edit Offer
	
	@PutMapping("/editOffer/{id}")
	public ResponseEntity<?> updateOffer(@PathVariable(value = "id") int offer_id,
			@Valid @RequestBody Offer offerDetails) throws Exception {
		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("in edit offer");

		Offer offer = null;
		offer = offerDao.findById(offer_id)
				.orElseThrow(() -> new Exception("Vendor not found for this id :: " + offer_id));

		if (offer != null) {
			offer.setOffer_name(offerDetails.getOffer_name());
			offer.setOffer_discount(offerDetails.getOffer_discount());
			offer.setMin_value(offerDetails.getMin_value());
			final Offer updatedOffer = offerDao.save(offer);
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "Student Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}
	
	// Delete offer
	
	@DeleteMapping("/deleteOffer/{id}")
	public ResponseEntity<?> deleteOffer(@PathVariable(value = "id") int offer_id) throws Exception {

		ResponseEntity<?> resp = null;
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("in delete offer");

		Offer offer = null;
		offer = offerDao.findById(offer_id)
				.orElseThrow(() -> new Exception("Offer not found for this id :: " + offer_id));

		if (offer != null) {
			offerDao.delete(offer);
			map.put("status", "success");
			resp = new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put("status", "error");
			map.put("error", "offer Not Found");
			resp = new ResponseEntity<>(map, HttpStatus.NO_CONTENT);
		}

		return resp;
	}

}
