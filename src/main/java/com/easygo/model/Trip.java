package com.easygo.model;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;

public class Trip {

	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("bookingid")
	private Long bookingId = null;

	@JsonProperty("customerid")
	private Long customerId = null;

	@JsonProperty("carid")
	private Long carId = null;

	@JsonProperty("car")
	private Car car = null;

	@JsonProperty("customer")
	private Customer customer = null;

	@JsonProperty("sale")
	private Sale sale = null;

	@JsonProperty("booking")
	private Booking booking = null;

	@JsonProperty("branchid")
	private Long branchId = null;

	@JsonProperty("from")
	private String from = null;

	@JsonProperty("to")
	private String to = null;

	@JsonProperty("totaldays")
	private Integer totalDays = null;

	@JsonProperty("outsstationtrip")
	private Integer outStationTrip = null;

	@JsonProperty("status")
	private TripStatusEnum status = null;

	/**
	 * Car status in the store
	 * 
	 * @return status
	 **/
	@JsonProperty("status")
	@ApiModelProperty(value = "Trip status")
	public TripStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TripStatusEnum status) {
		this.status = status;
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
	 * Get car
	 * 
	 * @return car
	 **/
	@JsonProperty("car")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	/**
	 * Get customer
	 * 
	 * @return customer
	 **/
	@JsonProperty("customer")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Get booking
	 * 
	 * @return booking
	 **/
	@JsonProperty("booking")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	/**
	 * Get sale
	 * 
	 * @return sale
	 **/
	@JsonProperty("sale")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
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
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Get to
	 * 
	 * @return to
	 **/
	@JsonProperty("to")
	@ApiModelProperty(value = "")
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

	public static TripStatusEnum getTripStatus(String type) {
		TripStatusEnum returnValue = TripStatusEnum.COMPLETED;
		switch (type) {
		case "SCHEDULED":
			returnValue = TripStatusEnum.SCHEDULED;
			break;
		case "CANCELLED":
			returnValue = TripStatusEnum.CANCELLED;
			break;
		}
		return returnValue;
	}

	/**
	 * Trip status
	 */
	public enum TripStatusEnum {
		SCHEDULED("SCHEDULED"), COMPLETED("COMPLETED"), CANCELLED("CANCELLED");

		private String value;

		TripStatusEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static TripStatusEnum fromValue(String text) {
			for (TripStatusEnum b : TripStatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}
}
