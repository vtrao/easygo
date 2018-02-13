package com.example.helloworld.core;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String name;
	private String displayName;
	private String description;
	private int id;
	private int parentId;
	private float siblingNumber;
	
	public float getSiblingNumber() {
		return siblingNumber;
	}

	public void setSiblingNumber(float siblingNumber) {
		this.siblingNumber = siblingNumber;
	}
	private List<Category> children = new ArrayList<Category>();
	
	public boolean addChild(Category child) {
		return children.add(child);
	}
	
	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public Category() {
		
	}
	
	public Category(String name, String description) {
		this.name = this.displayName = name;
		this.description = description;
	}
	public Category(final int id, final String name, final String displayName, final String description, final int parentId, final float siblingNumber) {
		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.parentId = parentId;
		this.id = id;
		this.siblingNumber = siblingNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
