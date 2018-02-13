package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.DAOFacade;
import com.easygo.daolayer.ISaleDAO;
import com.easygo.model.Car.CarTypeEnum;
import com.easygo.model.Customer;
import com.easygo.model.Customer.CustomerTypeEnum;
import com.easygo.system.db.SQLStatement;
import com.easygo.system.db.pool.ObjectPool;
import com.easygo.model.Sale;

public class SaleDAO extends AbstractDAO implements ISaleDAO {
	public static final String DB_TABLENAME = "sale";
	private static final String SALES_INSERT = "INSERT INTO " + DB_TABLENAME
			+ " ( bookingid, customerid, price)"
			+ "VALUES (?, ?, ?)";
	private static final String SALES_SELECT = "SELECT id, bookingid, customerid, price FROM "
			+ DB_TABLENAME;
	private static final String SALESTRIP_SELECT_FINAL = "SELECT distinct s.id, s.bookingid, s.customerid, s.price FROM "
			+ DB_TABLENAME + " s ";
	private static final String SALES_SELECT_FILTER = " where id = ?";
	private static final String SALES_SELECTCUSTOMER_FILTER = " where customerid = ?";
	private static final String SALES_SELECTBOOKING_FILTER = " where bookingid = ?";

	private SQLStatement mSaleInsert = null;
	private PreparedStatement mSaleGet = null;
	private PreparedStatement mSaleGetCustomer = null;
	private PreparedStatement mSaleGetBooking = null;
	private PreparedStatement mSaleGetOSI = null;

	public SaleDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool, null);
		setConnectionAndStatements();
	}

	@Override
	protected void setConnectionAndStatements() throws SQLException {
		this.connection = this.getConnection();
		mSaleInsert = new SQLStatement(connection, SALES_INSERT, this.connectionPool.getCoreInvoker());
		mSaleGet = connection.prepareStatement(SALES_SELECT + SALES_SELECT_FILTER);
		mSaleGetCustomer = connection.prepareStatement(SALES_SELECT + SALES_SELECTCUSTOMER_FILTER);
		mSaleGetBooking = connection.prepareStatement(SALES_SELECT + SALES_SELECTBOOKING_FILTER);
	}

	@Override
	public int addSale(Sale sale) throws Exception {
		int saleId = 0;
		mSaleInsert.setNString(1, sale.getBookingId().toString());
		mSaleInsert.setNString(2, sale.getCustomerId().toString());
		mSaleInsert.setFloat(3, sale.getPrice());
		try {
			saleId = mSaleInsert.executeInsert();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return saleId;
	}

	@Override
	public Sale getSale(Long id) throws Exception {
		Sale sale = null;
		try {
			mSaleGet.setLong(1, id);
			ResultSet rs = mSaleGet.executeQuery();// retrieve the data
			while (rs.next()) {
				sale = new Sale();
				sale.setId(new Long(rs.getInt("id")));
				sale.setBookingId(new Long(rs.getInt("bookingid")));
				sale.setCustomerId(new Long(rs.getInt("customerid")));
				sale.setPrice(new Float(rs.getFloat("price")));
				sale.setBooking(DAOFacade.getInstance().getBookingDAO().getBooking(sale.getBookingId()));
				sale.setCustomer(DAOFacade.getInstance().getCustomerDAO().getCustomer(sale.getCustomerId()));
				sale.setTrips(DAOFacade.getInstance().getTripDAO().getTripsForBooking(sale.getBookingId()));
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return sale;
	}

	@Override
	public Sale getSaleBooking(Long bookingId) throws Exception {
		Sale sale = null;
		try {
			mSaleGetBooking.setLong(1, bookingId);
			ResultSet rs = mSaleGetBooking.executeQuery();// retrieve the data
			while (rs.next()) {
				sale = new Sale();
				sale.setId(new Long(rs.getInt("id")));
				sale.setBookingId(new Long(rs.getInt("bookingid")));
				sale.setCustomerId(new Long(rs.getInt("customerid")));
				sale.setPrice(new Float(rs.getFloat("price")));
				sale.setBooking(null);
				sale.setCustomer(null);
				sale.setTrips(null);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return sale;
	}

	@Override
	public List<Sale> getSales(int outstationtrip, String carType, String customerType) throws Exception {
		List<Sale> returnList = new ArrayList();
		boolean outstationPart = false;
		boolean carPart = false;
		boolean customerPart = false;
		boolean wherePart = false;
		String tripTablePart = "";
		String customerTablePart = "";
		String carTablePart = "";
		String outStationTripQuery = "";
		String carTypeQuery = "";
		String customerTypeQuery = "";
		String tripQuery = "";
		// TODO: Sqlite jdbc driver limitation for prepared statement and
		// strings
		String query = SALESTRIP_SELECT_FINAL;
		if (outstationtrip == 0 || outstationtrip == 1) { // is in trips table
			tripQuery = " s.bookingid=t.bookingid ";
			outStationTripQuery = " t.outsstationtrip = " + outstationtrip;
			tripTablePart = " , " + TripDAO.DB_TABLENAME + " t";
			wherePart = true;
			outstationPart = true;
		}
		if (carType != null && carType.length()>0) { // is in car table and must use trips table also
			tripQuery = " s.bookingid=t.bookingid ";
			tripTablePart = " , " + TripDAO.DB_TABLENAME + " t";
			carTablePart = " , " + CarDAO.DB_TABLENAME + " c";
			carTypeQuery = " t.carid = c.id and c.type='" + carType + "' ";
			carPart = true;
			wherePart = true;
		}
		if (customerType != null && customerType.length()>0) { // is in customers table
			customerTablePart = " , " + CustomerDAO.DB_TABLENAME + " cust ";
			customerTypeQuery = " cust.id=s.customerid and cust.type='" + customerType + "' ";
			wherePart = true;
			customerPart = true;
		}
		if (wherePart) {
			query = query + tripTablePart + carTablePart + customerTablePart;
			query = query + " where ";
			if(outstationPart)
				query = query + tripQuery + " and " + outStationTripQuery;
			if(carPart) {
				if(outstationPart==false)
					query = query + tripQuery + " and " + carTypeQuery;
				else
					query = query + " and " + carTypeQuery;
			}
			if(customerPart) {
				if(carPart||outstationPart)
					query = query + " and " + customerTypeQuery;
				else
					query = query + customerTypeQuery;
			}
		}
		
		Statement stmt = connection.createStatement();
		try {
			ResultSet rs = stmt.executeQuery(query);// retrieve the data
			while (rs.next()) {
				Sale sale = new Sale();
				sale.setId(new Long(rs.getInt("id")));
				sale.setBookingId(new Long(rs.getInt("bookingid")));
				sale.setCustomerId(new Long(rs.getInt("customerid")));
				sale.setPrice(new Float(rs.getFloat("price")));
				sale.setBooking(DAOFacade.getInstance().getBookingDAO().getBooking(sale.getBookingId()));
				sale.setCustomer(DAOFacade.getInstance().getCustomerDAO().getCustomer(sale.getCustomerId()));
				sale.setTrips(DAOFacade.getInstance().getTripDAO().getTripsForBooking(sale.getBookingId()));
				returnList.add(sale);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}

	@Override
	public List<Sale> getSalesForCustomer(Long customerId) throws Exception {
		List<Sale> returnList = new ArrayList();
		mSaleGetCustomer.setLong(1, customerId);
		try {
			ResultSet rs = mSaleGetCustomer.executeQuery();// retrieve the data
			while (rs.next()) {
				Sale sale = new Sale();
				sale.setId(new Long(rs.getInt("id")));
				sale.setBookingId(new Long(rs.getInt("bookingid")));
				sale.setCustomerId(new Long(rs.getInt("customerid")));
				sale.setPrice(new Float(rs.getFloat("price")));
				sale.setBooking(null);
				sale.setCustomer(null);
				sale.setTrips(null);
				returnList.add(sale);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}



	@Override
	public int removeSale(Long id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSale(Sale car) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
