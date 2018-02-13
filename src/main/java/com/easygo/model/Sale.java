package com.easygo.model;

import java.util.List;

import com.easygo.model.Customer.CustomerTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class Sale {
	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("bookingid")
	private Long bookingId = null;

	@JsonProperty("customerid")
	private Long customerId = null;

	@JsonProperty("price")
	private Float price = null;

	@JsonProperty("customer")
	private Customer customer = null;

	@JsonProperty("trips")
	private List<Trip> trips = null;

	@JsonProperty("booking")
	private Booking booking = null;

	/**
	 * Get customer
	 * 
	 * @return customer
	 **/
	@JsonProperty("customer")
	@ApiModelProperty(value = "")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Get trip
	 * 
	 * @return trip
	 **/
	@JsonProperty("trips")
	@ApiModelProperty(value = "")
	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	/**
	 * Get booking
	 * 
	 * @return booking
	 **/
	@JsonProperty("booking")
	@ApiModelProperty(value = "")
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	/**
	 * Get id
	 * 
	 * @return id
	 **/
	@JsonProperty("id")
	@ApiModelProperty(value = "")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get bookingid
	 * 
	 * @return bookingid
	 **/
	@JsonProperty("bookingid")
	@ApiModelProperty(value = "")
	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Get customerid
	 * 
	 * @return customerid
	 **/
	@JsonProperty("customerid")
	@ApiModelProperty(value = "")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * Get price
	 * 
	 * @return price
	 **/
	@JsonProperty("price")
	@ApiModelProperty(value = "")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
