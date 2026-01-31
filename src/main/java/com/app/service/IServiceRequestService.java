package com.app.service;

import java.util.List;

import com.app.pojos.ServiceRequest;

public interface IServiceRequestService {

	ServiceRequest getByCustomerId(int customer_id);

	ServiceRequest getByCustomerIdAndStatus(int customer_id, String status);

	ServiceRequest addServiceRequest(ServiceRequest service_request);

	ServiceRequest getByRequestId(int request_id);

	List<ServiceRequest> getAllServices(int customer_id);
}
