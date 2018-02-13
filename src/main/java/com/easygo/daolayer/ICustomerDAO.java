package com.easygo.daolayer;

import java.util.List;

import com.easygo.model.Customer;

public interface ICustomerDAO {
	public Customer getCustomer(Long id) throws Exception;
	public List<Customer> getCustomer() throws Exception;
	public Customer addCustomer(Customer customer) throws Exception;
	public int removeCustomer(Long id) throws Exception;
	public int updateCustomer(Customer customer) throws Exception;
	public List<String> getTypes();
}
