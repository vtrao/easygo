package com.easygo.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

public class Branch {
	@JsonProperty("id")
    private Long id = null;
	
	@JsonProperty("name")
	private String name = null;
	
	@JsonProperty("min")
	private Integer min = null;
	
	@JsonProperty("max")
	private Integer max = null;
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
	@ApiModelProperty(example = "doggie", required = true, value = "")
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get min
	 * 
	 * @return min
	 **/
	@JsonProperty("min")
	@ApiModelProperty(value = "")
	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}
	
	/**
	 * Get max
	 * 
	 * @return max
	 **/
	@JsonProperty("max")
	@ApiModelProperty(value = "")
	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}
