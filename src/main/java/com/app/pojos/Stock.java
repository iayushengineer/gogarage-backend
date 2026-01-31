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
@Table(name = "stock")
@JsonInclude(value = Include.NON_DEFAULT)
public class Stock {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // strategy = AUTO will be replaced : auto_increment
	@Column(name = "stock_id")
	@JsonProperty("stock_id")
	private int stockId;

	@Column(name = "item_name", length = 30, unique = true)
	@JsonProperty("item_name")
	private String itemName;

	@JsonProperty("price")
	private double price;

	@JsonProperty("quantity")
	private int quantity;

	// -------------------------------------------------------------------------
	// constructor
	// --------------------------------------------------------------------------

	public Stock() {
	}

	public Stock(int stockId, String itemName, double price, int quantity) {
		super();
		this.stockId = stockId;
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;

	}

	// -------------------------------------------------------------
	// getter and setter
	// -----------------------------------------------------------------

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// -------------------------------------------------------------
	// to string
	// -----------------------------------------------------------------
	@Override
	public String toString() {
		return "Stock [stockId=" + stockId + ", itemName=" + itemName + ", price=" + price + ", quantity=" + quantity
				+ "]";
	}
}
