package com.easygo.daolayer;

import java.util.List;

import com.easygo.model.Trip;
import java.util.Date;

public interface ITripDAO {
	public List<Trip> getTrip() throws Exception;

	public List<Trip> getTrip(Date from, int before) throws Exception;
	
	public List<Trip> getTripsForCustomer(Long customerId) throws Exception;
	
	public List<Trip> getTripsForBooking(Long bookingId) throws Exception;
	
	public int addTrip(Trip trip) throws Exception;

	public int removeTrip(Long customerId, Long bookingId, Long carId) throws Exception;

	public int updateTrip(Trip trip) throws Exception;
}
