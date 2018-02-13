package com.easygo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

public class Car {

	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("reg")
	private String reg = null;
	
	@JsonProperty("currentbranch")
	private Branch currentBranch = null;

	@JsonProperty("homebranch")
	private Branch homeBranch = null;

	@JsonProperty("status")
	private CarStatusEnum status = null;

	@JsonProperty("type")
	private CarTypeEnum type = null;
	
	@JsonProperty("costperday")
	private Float costPerDay;
	
	/**
	 * Car status in the store
	 * 
	 * @return status
	 **/
	@JsonProperty("status")
	@ApiModelProperty(value = "Car status in the Branch")
	public CarStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CarStatusEnum status) {
		this.status = status;
	}

	/**
	 * Car type in the store
	 * 
	 * @return type
	 **/
	@JsonProperty("type")
	@ApiModelProperty(value = "Car type")
	public CarTypeEnum getType() {
		return type;
	}

	public void setType(CarTypeEnum type) {
		this.type = type;
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
	 * Get reg
	 * 
	 * @return reg
	 **/
	@JsonProperty("reg")
	@ApiModelProperty(example = "", required = true, value = "")
	@NotNull
	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}
	
	/**
	 * Get current branch
	 * 
	 * @return Branch
	 **/
	@JsonProperty("currentbranch")
	@ApiModelProperty(value = "")
	public Branch getCurrentbranch() {
		return currentBranch;
	}

	public void setCurrentbranch(Branch currentBranch) {
		this.currentBranch = currentBranch;
	}

	/**
	 * Get current branch
	 * 
	 * @return Branch
	 **/
	@JsonProperty("homebranch")
	@ApiModelProperty(value = "")
	public Branch getHomebranch() {
		return homeBranch;
	}

	public void setHomebranch(Branch homeBranch) {
		this.homeBranch = homeBranch;
	}

	
	/**
	 * Get costperday
	 * 
	 * @return costperday
	 **/
	@JsonProperty("costperday")
	@ApiModelProperty(value = "")
	public Float getCostPerDay() {
		return costPerDay;
	}

	public void setCostPerDay(Float costPerDay) {
		this.costPerDay = costPerDay;
	}
	
	public static CarStatusEnum getCarStatus(String type) {
		CarStatusEnum returnValue = CarStatusEnum.UNDERSERVICE;
		switch(type) {
		case "AVAILABLE":
			returnValue = CarStatusEnum.AVAILABLE;
			break;
		case "BOOKED":
			returnValue = CarStatusEnum.BOOKED; 
			break;
		}
		return returnValue;
	}

	
	/**
	 * Car status in the Branch
	 */
	public enum CarStatusEnum {
		AVAILABLE("AVAILABLE"),BOOKED("BOOKED"),UNDERSERVICE("UNDERSERVICE");

		private String value;

		CarStatusEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static CarStatusEnum fromValue(String text) {
			for (CarStatusEnum b : CarStatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}
	public static CarTypeEnum getCarType(String type) {
		CarTypeEnum returnValue = CarTypeEnum.COMPACT;
		switch(type) {
		case "SUV":
			returnValue = CarTypeEnum.SUV;
			break;
		case "SEDAN":
			returnValue = CarTypeEnum.SEDAN; 
			break;
		}
		return returnValue;
	}
	/*
	 * Car type
	 */
	public enum CarTypeEnum {
		COMPACT("COMPACT"), SUV("SUV"), SEDAN("SEDAN");
		private String value;

		CarTypeEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static CarTypeEnum fromValue(String text) {
			for (CarTypeEnum b : CarTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

}
