package com.easygo.daolayer;

import java.util.List;

import com.easygo.model.Car.CarTypeEnum;
import com.easygo.model.Customer.CustomerTypeEnum;
import com.easygo.model.Sale;

public interface ISaleDAO {
	public Sale getSale(Long id) throws Exception;

	public Sale getSaleBooking(Long bookingId) throws Exception;

	public List<Sale> getSales(int outstationtrip, String carType, String customerType) throws Exception;

	public List<Sale> getSalesForCustomer(Long customerId) throws Exception;

	public int addSale(Sale sale) throws Exception;

	public int removeSale(Long id) throws Exception;

	public int updateSale(Sale car) throws Exception;
}
