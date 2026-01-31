package com.app.pojos;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "service_request")
@JsonInclude(value = Include.NON_DEFAULT)
public class ServiceRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("request_id")
  private int requestId;

  // [CAR or BIKE]
  @Column(length = 45)
  @JsonProperty("vehicle_type")
  private String vehicleType;

  @Column(length = 45)
  @JsonProperty("vehicle_model")
  private String vehicleModel;

  @Column(length = 45)
  @JsonProperty("vehicle_brand")
  private String vehicleBrand;

  @Column(length = 45)
  @JsonProperty("vehicle_reg_no")
  private String vehicleRegNo;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonProperty("service_date")
  private LocalDate serviceDate = LocalDate.now();

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonProperty("out_date")
  private LocalDate outDate;

  // [PICKUP or DROPBY]
  @JsonProperty("delivery_type")
  private String deliveryType;

  @JsonProperty("customer_Id")
  private int customerId;
  // [PENDING or COMPLETE]

  @JsonProperty("status")
  private String status;

  @JsonProperty("labour_charges")
  private double labourCharges = 0.0;

  @JsonProperty("discount")
  private double discount = 1.0;

  @JsonProperty("product_charges")
  private double productCharges = 0.0;

  @JsonProperty("total")
  private double total = 0.0;

  @JsonProperty("package_id")
  private int pacakgeId;

  public ServiceRequest() {
    super();
  }

  public ServiceRequest(int requestId, String vehicleType, String vehicleModel, String vehicleBrand,
      String vehicleRegNo, LocalDate serviceDate, LocalDate outDate, String deliveryType,
      String status, double labourCharges, double discount, double productCharges, double total,
      int packageId) {
    super();
    this.requestId = requestId;
    this.vehicleType = vehicleType;
    this.vehicleModel = vehicleModel;
    this.vehicleBrand = vehicleBrand;
    this.vehicleRegNo = vehicleRegNo;
    this.serviceDate = serviceDate;
    this.outDate = outDate;
    this.deliveryType = deliveryType;
    this.status = status;
    this.labourCharges = labourCharges;
    this.discount = discount;
    this.productCharges = productCharges;
    this.total = total;
    this.pacakgeId = packageId;
  }


  public int getRequestId() {
    return requestId;
  }

  public void setRequestId(int requestId) {
    this.requestId = requestId;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getVehicleModel() {
    return vehicleModel;
  }

  public void setVehicleModel(String vehicleModel) {
    this.vehicleModel = vehicleModel;
  }

  public String getVehicleBrand() {
    return vehicleBrand;
  }

  public void setVehicleBrand(String vehicleBrand) {
    this.vehicleBrand = vehicleBrand;
  }

  public LocalDate getServiceDate() {
    return serviceDate;
  }

  public void setServiceDate(LocalDate serviceDate) {
    this.serviceDate = serviceDate;
  }

  public String getDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getVehicleRegNo() {
    return vehicleRegNo;
  }

  public void setVehicleRegNo(String vehicleRegNo) {
    this.vehicleRegNo = vehicleRegNo;
  }

  public LocalDate getOutDate() {
    return outDate;
  }

  public void setOutDate(LocalDate outDate) {
    this.outDate = outDate;
  }

  public double getLabourCharges() {
    return labourCharges;
  }

  public void setLabourCharges(double labourCharges) {
    this.labourCharges = labourCharges;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public double getProductCharges() {
    return productCharges;
  }

  public void setProductCharges(double productCharges) {
    this.productCharges = productCharges;
  }

  public int getPacakgeId() {
    return pacakgeId;
  }

  public void setPacakgeId(int pacakgeId) {
    this.pacakgeId = pacakgeId;
  }

  @Override
  public String toString() {
    return "ServiceRequest [requestId=" + requestId + ", vehicleType=" + vehicleType
        + ", vehicleModel=" + vehicleModel + ", vehicleBrand=" + vehicleBrand + ", vehicleRegNo="
        + vehicleRegNo + ", serviceDate=" + serviceDate + ", outDate=" + outDate + ", deliveryType="
        + deliveryType + ", customerId=" + customerId + ", status=" + status + ", labourCharges="
        + labourCharges + ", discount=" + discount + ", total=" + total + "]";
  }

}
