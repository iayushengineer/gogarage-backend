package com.app.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "service_package")
@JsonInclude(value = Include.NON_DEFAULT)
public class ServicePackage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("package_id")
  private Long packageId;
  @Column(name = "package_name")
  @JsonProperty("package_name")
  private String packageName;
  @Column(name = "package_description", length = 500)
  @JsonProperty("package_description")
  private String packageDescription;
  @Column(name = "package_price")
  @JsonProperty("package_price")
  private Double packagePrice;

  public ServicePackage() {}

  public ServicePackage(Long packageId, String packageName, String packageDescription,
      Double packagePrice) {
    super();
    this.packageId = packageId;
    this.packageName = packageName;
    this.packageDescription = packageDescription;
    this.packagePrice = packagePrice;
  }

  public Long getPackageId() {
    return packageId;
  }

  public void setPackageId(Long packageId) {
    this.packageId = packageId;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getPackageDescription() {
    return packageDescription;
  }

  public void setPackageDescription(String packageDescription) {
    this.packageDescription = packageDescription;
  }

  public Double getPackagePrice() {
    return packagePrice;
  }

  public void setPackagePrice(Double packagePrice) {
    this.packagePrice = packagePrice;
  }

  @Override
  public String toString() {
    return "ServicePackage [packageId=" + packageId + ", packageName=" + packageName
        + ", packageDescription=" + packageDescription + ", packagePrice=" + packagePrice + "]";
  }

}
