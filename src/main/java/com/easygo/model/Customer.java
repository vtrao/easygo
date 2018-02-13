package com.easygo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.*;

public class Customer {
	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("type")
	private CustomerTypeEnum type = null;
	
	@JsonProperty("sales")
	private List<Sale> sales = null;
	
	@JsonProperty("bookings")
	private List<Booking> bookings = null;
	
	@JsonProperty("trips")
	private List<Trip> trips = null;
	
	/**
	 * Get sales
	 * 
	 * @return sales
	 **/
	@JsonProperty("sales")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
	/**
	 * Get trips
	 * 
	 * @return trips
	 **/
	@JsonProperty("trips")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}
	
	/**
	 * Get bookings
	 * 
	 * @return bookings
	 **/
	@JsonProperty("bookings")
	@ApiModelProperty(example = "", value = "")
	@NotNull
	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
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
	 * Get name
	 * 
	 * @return name
	 **/
	@JsonProperty("name")
	@ApiModelProperty(example = "", required = true, value = "")
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Customer type in the store
	 * 
	 * @return status
	 **/
	@JsonProperty("type")
	@ApiModelProperty(value = "customer type")
	public CustomerTypeEnum getType() {
		return type;
	}

	public void setType(CustomerTypeEnum type) {
		this.type = type;
	}
	
	// is this required??
	public Customer id(Long id) {
		this.id = id;
		return this;
	}
	
	public static CustomerTypeEnum getCustomerType(String type) {
		CustomerTypeEnum returnValue = CustomerTypeEnum.INDIVIDUAL;
		switch(type) {
		case "CORPORATE":
			returnValue = CustomerTypeEnum.CORPORATE;
			break;
		case "PREMIUM":
			returnValue = CustomerTypeEnum.PREMIUM; 
			break;
		}
		return returnValue;
	}
	
	/*
	 * Customer type
	 */
	public enum CustomerTypeEnum {
		CORPORATE("CORPORATE"), INDIVIDUAL("INDIVIDUAL"), PREMIUM("PREMIUM");
		private String value;

		CustomerTypeEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static CustomerTypeEnum fromValue(String text) {
			for (CustomerTypeEnum b : CustomerTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}
}
