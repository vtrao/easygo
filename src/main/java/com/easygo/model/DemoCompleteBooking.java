package com.easygo.model;

import java.util.Date;

import com.easygo.model.Car.CarTypeEnum;
import com.easygo.model.Customer.CustomerTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

// TODO: For demo purpose we are using an end to end demo booking which does sale and add trips
public class DemoCompleteBooking {
	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("customerid")
	private Long customerId = null;

	@JsonProperty("carid")
	private Long carId = null;

	@JsonProperty("branchid")
	private Long branchId = null;

	@JsonProperty("from")
	private Date from = null;

	@JsonProperty("to")
	private Date to = null;

	@JsonProperty("price")
	private Float price = null;

	@JsonProperty("totaldays")
	private Integer totalDays = null;

	@JsonProperty("customertype")
	private CustomerTypeEnum customerType = null;

	@JsonProperty("cartype")
	private CarTypeEnum carType = null;

	@JsonProperty("outsstationtrip")
	private Integer outStationTrip = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("bookingtime")
	private Date bookingTime = null;

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
	 * Get carid
	 * 
	 * @return carid
	 **/
	@JsonProperty("carid")
	@ApiModelProperty(value = "")
	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	/**
	 * Get branchid
	 * 
	 * @return branchid
	 **/
	@JsonProperty("branchid")
	@ApiModelProperty(value = "")
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * Get from
	 * 
	 * @return from
	 **/
	@JsonProperty("from")
	@ApiModelProperty(value = "")
	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	/**
	 * Get to
	 * 
	 * @return to
	 **/
	@JsonProperty("to")
	@ApiModelProperty(value = "")
	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
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

	/**
	 * Get totaldays
	 * 
	 * @return totaldays
	 **/
	@JsonProperty("totaldays")
	@ApiModelProperty(value = "")
	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totaldays) {
		this.totalDays = totaldays;
	}

	/**
	 * cartype in the store
	 * 
	 * @return cartype
	 **/
	@JsonProperty("cartype")
	@ApiModelProperty(value = "Car type")
	public CarTypeEnum getCarType() {
		return carType;
	}

	public void setCarType(CarTypeEnum carType) {
		this.carType = carType;
	}

	/**
	 * customertype
	 * 
	 * @return customertype
	 **/
	@JsonProperty("customertype")
	@ApiModelProperty(value = "customer type")
	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerTypeEnum customertype) {
		this.customerType = customertype;
	}

	/**
	 * Get outsstationtrip
	 * 
	 * @return outsstationtrip
	 **/
	@JsonProperty("outsstationtrip")
	@ApiModelProperty(value = "")
	public Integer getOutStationTrip() {
		return outStationTrip;
	}

	public void setOutStationTrip(Integer outsstationtrip) {
		this.outStationTrip = outsstationtrip;
	}

	/**
	 * Get description
	 * 
	 * @return description
	 **/
	@JsonProperty("description")
	@ApiModelProperty(value = "")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get bookingtime
	 * 
	 * @return bookingtime
	 **/
	@JsonProperty("bookingtime")
	@ApiModelProperty(value = "")
	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}
}
