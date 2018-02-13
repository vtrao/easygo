package com.easygo.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class Booking {
	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("description")
	private String description = null;

	@JsonProperty("bookingtime")
	private String bookingTime = null;
	
	@JsonProperty("price")
	private Float price = null;

	@JsonProperty("totaldays")
	private Integer totalDays = null;

	@JsonProperty("outsstationtrip")
	private Integer outStationTrip = null;


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
	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
}
