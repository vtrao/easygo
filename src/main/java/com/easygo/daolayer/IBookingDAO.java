package com.easygo.daolayer;

import java.util.List;

import com.easygo.model.Booking;
import com.easygo.model.DemoCompleteBooking;

public interface IBookingDAO {
	public List<Booking> getBooking() throws Exception;

	public Booking getBooking(Long id) throws Exception;
	
	public int addBooking(List<DemoCompleteBooking> booking) throws Exception;

	public int removeBooking(Booking booking) throws Exception;

	public int updateBooking(Booking booking) throws Exception;
}
