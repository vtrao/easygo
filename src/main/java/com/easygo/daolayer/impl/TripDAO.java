package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.DAOFacade;
import com.easygo.daolayer.ITripDAO;
import com.easygo.model.Customer;
import com.easygo.model.Sale;
import com.easygo.model.Trip;
import com.easygo.system.db.SQLStatement;
import com.easygo.system.db.pool.ObjectPool;

public class TripDAO extends AbstractDAO implements ITripDAO  {
	public static final String DB_TABLENAME = "trip";
	private static final String TRIP_INSERT = "INSERT INTO " + DB_TABLENAME
			+ " (bookingid, customerid, carid, branchid, fromdate, todate, status, totaldays, outsstationtrip)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String TRIPSELECT = " SELECT id, bookingid, customerid, carid, branchid, fromdate, todate, status, totaldays, outsstationtrip FROM "
			+ DB_TABLENAME;
	private static final String TRIP_SELECTCUSTOMER_FILTER = " where customerid = ?";
	private static final String TRIP_SELECTBOOKING_FILTER = " where bookingid = ?";
	
	private SQLStatement mTripInsert = null;
	private PreparedStatement mTripGet = null;
	private PreparedStatement mTripGetForCustomer = null;
	private PreparedStatement mTripGetForBooking = null;
	
	public TripDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool, null);
		setConnectionAndStatements();
	}
	@Override
	protected void setConnectionAndStatements() throws SQLException {
		this.connection = this.getConnection();
		mTripInsert = new SQLStatement(connection, TRIP_INSERT, this.connectionPool.getCoreInvoker());
		mTripGet = connection.prepareStatement(TRIPSELECT);
		mTripGetForCustomer = connection.prepareStatement(TRIPSELECT + TRIP_SELECTCUSTOMER_FILTER);
		mTripGetForBooking = connection.prepareStatement(TRIPSELECT + TRIP_SELECTBOOKING_FILTER);
	}
	
	@Override
	public int addTrip(Trip trip) throws Exception {
		int tripId = 0;
		mTripInsert.setNString(1, trip.getBookingId().toString());
		mTripInsert.setNString(2, trip.getCustomerId().toString());
		mTripInsert.setNString(3, trip.getCarId().toString());
		mTripInsert.setNString(4, trip.getBranchId().toString());
		mTripInsert.setNString(5, trip.getFrom().toString());
		mTripInsert.setNString(6, trip.getTo().toString());
		mTripInsert.setNString(7, trip.getStatus().toString());
		mTripInsert.setInt(8, trip.getTotalDays());
		mTripInsert.setInt(9, trip.getOutStationTrip());
		try {
			tripId = mTripInsert.executeInsert();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return tripId;
	}
	
	@Override
	public List<Trip> getTrip() throws Exception {
		List<Trip> returnList = new ArrayList();
		try {
			ResultSet rs = mTripGet.executeQuery();// retrieve the data
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(new Long(rs.getInt("id")));
				trip.setBookingId(new Long(rs.getInt("bookingid")));
				trip.setCustomerId(new Long(rs.getInt("customerid")));
				trip.setCarId(new Long(rs.getInt("carid")));
				trip.setBranchId(new Long(rs.getInt("branchid")));
				trip.setFrom(rs.getString("fromdate"));
				trip.setTo(rs.getString("todate"));
				trip.setStatus(Trip.getTripStatus(rs.getString("status")));
				trip.setCar(DAOFacade.getInstance().getCarDAO().getCar(trip.getCarId()));
				trip.setCustomer(DAOFacade.getInstance().getCustomerDAO().getCustomer(trip.getCustomerId()));
				trip.setSale(DAOFacade.getInstance().getSaleDAO().getSaleBooking(trip.getBookingId()));
				trip.setTotalDays(new Integer(rs.getInt("totaldays")));
				trip.setOutStationTrip(new Integer(rs.getInt("outsstationtrip")));
				returnList.add(trip);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}
	
	@Override
	public List<Trip> getTripsForCustomer(Long customerId) throws Exception {
		List<Trip> returnList = new ArrayList();
		try {
			mTripGetForCustomer.setLong(1, customerId);
			ResultSet rs = mTripGetForCustomer.executeQuery();// retrieve the data
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(new Long(rs.getInt("id")));
				trip.setBookingId(new Long(rs.getInt("bookingid")));
				trip.setCustomerId(new Long(rs.getInt("customerid")));
				trip.setCarId(new Long(rs.getInt("carid")));
				trip.setBranchId(new Long(rs.getInt("branchid")));
				trip.setFrom(rs.getString("fromdate"));
				trip.setTo(rs.getString("todate"));
				trip.setStatus(Trip.getTripStatus(rs.getString("status")));
				trip.setCar(DAOFacade.getInstance().getCarDAO().getCar(trip.getCarId()));
				trip.setCustomer(null);
				trip.setSale(null);
				trip.setTotalDays(new Integer(rs.getInt("totaldays")));
				trip.setOutStationTrip(new Integer(rs.getInt("outsstationtrip")));
				returnList.add(trip);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}
	
	@Override
	public List<Trip> getTripsForBooking(Long bookingId) throws Exception {
		List<Trip> returnList = new ArrayList();
		try {
			mTripGetForBooking.setLong(1, bookingId);
			ResultSet rs = mTripGetForBooking.executeQuery();// retrieve the data
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(new Long(rs.getInt("id")));
				trip.setBookingId(new Long(rs.getInt("bookingid")));
				trip.setCustomerId(new Long(rs.getInt("customerid")));
				trip.setCarId(new Long(rs.getInt("carid")));
				trip.setBranchId(new Long(rs.getInt("branchid")));
				trip.setFrom(rs.getString("fromdate"));
				trip.setTo(rs.getString("todate"));
				trip.setStatus(Trip.getTripStatus(rs.getString("status")));
				trip.setCar(DAOFacade.getInstance().getCarDAO().getCar(trip.getCarId()));
				trip.setCustomer(null);
				trip.setSale(null);
				trip.setTotalDays(new Integer(rs.getInt("totaldays")));
				trip.setOutStationTrip(new Integer(rs.getInt("outsstationtrip")));
				returnList.add(trip);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}
	
	@Override
	public List<Trip> getTrip(Date date, int before) throws Exception {
		List<Trip> returnList = new ArrayList();
		Statement stmt = connection.createStatement();
		String compareop = ">=";
		
		// TODO: Sqlite jdbc driver limitation for prepared statement and
		// strings
		String query ="";
		if(before==1) {
			query = TRIPSELECT  + " where todate <'"
				+ DAOFacade.dataFormat.format(date) + "'";
		} else {
			query = TRIPSELECT  + " where fromdate >='"
					+ DAOFacade.dataFormat.format(date) + "'";
		}
		
		try {
			ResultSet rs = stmt.executeQuery(query);// retrieve the data
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(new Long(rs.getInt("id")));
				trip.setBookingId(new Long(rs.getInt("bookingid")));
				trip.setCustomerId(new Long(rs.getInt("customerid")));
				trip.setCarId(new Long(rs.getInt("carid")));
				trip.setBranchId(new Long(rs.getInt("branchid")));
				trip.setFrom(rs.getString("fromdate"));
				trip.setTo(rs.getString("todate"));
				trip.setStatus(Trip.getTripStatus(rs.getString("status")));
				trip.setCar(DAOFacade.getInstance().getCarDAO().getCar(trip.getCarId()));
				trip.setCustomer(DAOFacade.getInstance().getCustomerDAO().getCustomer(trip.getCustomerId()));
				trip.setSale(DAOFacade.getInstance().getSaleDAO().getSaleBooking(trip.getBookingId()));
				trip.setBooking(DAOFacade.getInstance().getBookingDAO().getBooking(trip.getBookingId()));
				trip.setTotalDays(new Integer(rs.getInt("totaldays")));
				trip.setOutStationTrip(new Integer(rs.getInt("outsstationtrip")));
				returnList.add(trip);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}
	
	@Override
	public int removeTrip(Long customerId, Long bookingId, Long carId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int updateTrip(Trip trip) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
