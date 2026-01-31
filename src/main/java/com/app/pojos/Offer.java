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
@Table(name = "offer")
@JsonInclude(value = Include.NON_DEFAULT)
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("offer_id")
	private int offer_id;

	@Column(length = 100)
	@JsonProperty("offer_name")
	private String offer_name;

	@JsonProperty("offer_discount")
	private double offer_discount;

	@JsonProperty("min_value")
	private double min_value;

	/* ============================== Constructor ============================== */
	public Offer() {
		super();
	}

	public Offer(int offer_id, String offer_name, double offer_discount, double min_value) {
		super();
		this.offer_id = offer_id;
		this.offer_name = offer_name;
		this.offer_discount = offer_discount;
		this.min_value = min_value;
	}

	/* =========================== Getters & Setters =========================== */
	public int getOfferId() {
		return offer_id;
	}

	public void setOfferId(int offer_id) {
		this.offer_id = offer_id;
	}

	public String getOffer_name() {
		return offer_name;
	}

	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}

	public double getOffer_discount() {
		return offer_discount;
	}

	public void setOffer_discount(double offer_discount) {
		this.offer_discount = offer_discount;
	}

	public double getMin_value() {
		return min_value;
	}

	public void setMin_value(double min_value) {
		this.min_value = min_value;
	}

	/* ================================ toString =============================== */
	@Override
	public String toString() {
		return "Offer [offer_id=" + offer_id + ", offer_name=" + offer_name + ", offer_discount=" + offer_discount
				+ ", min_value=" + min_value + "]";
	}

}
