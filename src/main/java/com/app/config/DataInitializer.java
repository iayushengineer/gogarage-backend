package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.dao.AdminDao;
import com.app.dao.CustomerDao;
import com.app.dao.EmployeeDao;
import com.app.dao.OfferDao;
import com.app.dao.ServicePackageDao;
import com.app.dao.ServiceRequestDao;
import com.app.dao.StockDao;
import com.app.dao.UserDao;
import com.app.dao.VendorDao;
import com.app.pojos.Admin;
import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.Offer;
import com.app.pojos.ServicePackage;
import com.app.pojos.ServiceRequest;
import com.app.pojos.Stock;
import com.app.pojos.User;
import com.app.pojos.Vendor;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${app.data.initialize:false}")
    private boolean initializeData;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private VendorDao vendorDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ServicePackageDao servicePackageDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private ServiceRequestDao serviceRequestDao;

    @Override
    public void run(String... args) throws Exception {
        if (initializeData) {
            System.out.println("Initializing dummy data...");
            initializeDummyData();
            System.out.println("Dummy data initialization completed!");
        }
    }

    private void initializeDummyData() {
        // Create Users with BCrypt encoded passwords
        User adminUser = new User("admin@cs.com", "$2a$10$3N12wrn/OlcmTKKy49b2BO2nL1ppzW.lo66KRvN.5cUV5/QWv0JlW", "ADMIN", 1);
        User vendorUser = new User("vendor@cs.com", "$2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We", "VENDOR", 1);
        User employeeUser = new User("employee@cs.com", "$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16", "EMPLOYEE", 1);
        User customerUser = new User("customer@cs.com", "$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.", "CUSTOMER", 1);

        userDao.save(adminUser);
        userDao.save(vendorUser);
        userDao.save(employeeUser);
        userDao.save(customerUser);

        // Create Admin
        Admin admin = new Admin();
        admin.setName("System Administrator");
        admin.setEmail("admin@cs.com");
        admin.setPassword("$2a$10$3N12wrn/OlcmTKKy49b2BO2nL1ppzW.lo66KRvN.5cUV5/QWv0JlW");
        adminDao.save(admin);

        // Create Vendor
        Vendor vendor = new Vendor();
        vendor.setName("AutoCare Services");
        vendor.setAddress("123 Main Street, Mumbai, Maharashtra");
        vendor.setContact("9876543210");
        vendor.setEmail("vendor@cs.com");
        vendor.setPassword("$2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We");
        Vendor savedVendor = vendorDao.save(vendor);

        // Create Employee
        Employee employee = new Employee();
        employee.setName("Rajesh Kumar");
        employee.setBirthDate(LocalDate.of(1990, 5, 15));
        employee.setEmail("employee@cs.com");
        employee.setPassword("$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16");
        employee.setVendorId(savedVendor.getId());
        Employee savedEmployee = employeeDao.save(employee);

        // Create Customer
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setBirthDate(LocalDate.of(1985, 3, 25));
        customer.setContact("9123456789");
        customer.setAddress("789 Customer Lane, Delhi");
        customer.setEmail("customer@cs.com");
        customer.setPassword("$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.");
        customer.setEmployeeId(savedEmployee.getId());
        Customer savedCustomer = customerDao.save(customer);

        // Create Service Packages
        ServicePackage basicService = new ServicePackage();
        basicService.setPackageName("Basic Service");
        basicService.setPackageDescription("Oil change, filter replacement, basic inspection");
        basicService.setPackagePrice(2500.00);
        ServicePackage savedPackage = servicePackageDao.save(basicService);

        ServicePackage premiumService = new ServicePackage();
        premiumService.setPackageName("Premium Service");
        premiumService.setPackageDescription("Complete service including engine tune-up, brake check, AC service");
        premiumService.setPackagePrice(5000.00);
        servicePackageDao.save(premiumService);

        ServicePackage fullService = new ServicePackage();
        fullService.setPackageName("Full Service");
        fullService.setPackageDescription("Comprehensive service with all checks and replacements");
        fullService.setPackagePrice(8000.00);
        servicePackageDao.save(fullService);

        // Create Stock Items
        Stock engineOil = new Stock();
        engineOil.setItemName("Engine Oil");
        engineOil.setPrice(500.00);
        engineOil.setQuantity(50);
        stockDao.save(engineOil);

        Stock airFilter = new Stock();
        airFilter.setItemName("Air Filter");
        airFilter.setPrice(300.00);
        airFilter.setQuantity(30);
        stockDao.save(airFilter);

        Stock brakePads = new Stock();
        brakePads.setItemName("Brake Pads");
        brakePads.setPrice(1200.00);
        brakePads.setQuantity(20);
        stockDao.save(brakePads);

        // Create Offers
        Offer newCustomerOffer = new Offer();
        newCustomerOffer.setOffer_name("New Customer Discount");
        newCustomerOffer.setOffer_discount(10.0);
        newCustomerOffer.setMin_value(2000.0);
        offerDao.save(newCustomerOffer);

        Offer premiumOffer = new Offer();
        premiumOffer.setOffer_name("Premium Service Special");
        premiumOffer.setOffer_discount(15.0);
        premiumOffer.setMin_value(5000.0);
        offerDao.save(premiumOffer);

        // Create Sample Service Request
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setVehicleType("CAR");
        serviceRequest.setVehicleModel("Swift");
        serviceRequest.setVehicleBrand("Maruti");
        serviceRequest.setVehicleRegNo("MH 12 AB 1234");
        serviceRequest.setServiceDate(LocalDate.of(2024, 1, 15));
        serviceRequest.setOutDate(LocalDate.of(2024, 1, 16));
        serviceRequest.setDeliveryType("PICKUP");
        serviceRequest.setCustomerId(savedCustomer.getId());
        serviceRequest.setStatus("COMPLETED");
        serviceRequest.setLabourCharges(1500.00);
        serviceRequest.setDiscount(0.9);
        serviceRequest.setProductCharges(1000.00);
        serviceRequest.setTotal(2250.00);
        serviceRequest.setPacakgeId(savedPackage.getPackageId().intValue());
        serviceRequestDao.save(serviceRequest);

        System.out.println("Sample data created successfully!");
        System.out.println("Admin: admin@cs.com / admin123");
        System.out.println("Vendor: vendor@cs.com / vendor123");
        System.out.println("Employee: employee@cs.com / emp123");
        System.out.println("Customer: customer@cs.com / cust123");
    }
}