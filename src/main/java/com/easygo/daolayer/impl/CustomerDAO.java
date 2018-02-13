package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.DAOFacade;
import com.easygo.daolayer.ICustomerDAO;
import com.easygo.model.Booking;
import com.easygo.model.Branch;
import com.easygo.model.Customer;
import com.easygo.model.Customer.CustomerTypeEnum;
import com.easygo.system.db.SQLStatement;
import com.easygo.system.db.pool.ObjectPool;
import com.easygo.model.Sale;
import com.example.helloworld.core.Category;

public class CustomerDAO extends AbstractDAO implements ICustomerDAO {

	public static final String DB_TABLENAME = "customer";
	private static final String CUSTOMER_INSERT = "INSERT INTO " + DB_TABLENAME + " (id, name, type )"
			+ "VALUES (?, ?, ?)";
	private static final String CUSTOMER_INSERT_NOID = "INSERT INTO " + DB_TABLENAME + " (name, type )"
			+ "VALUES (?, ?)";
	private static final String CUSTOMER_SELECT = " SELECT id, name, type FROM " + DB_TABLENAME;
	private static final String CUSTOMER_SELECT_FILTER = " where id = ?";
	private static final String CUSTOMER_UPDATE = "UPDATE " + DB_TABLENAME + " set name = ?, type = ? "
			+ " where id  = ?";
	private static final String CUSTOMER_DELETE = "DELETE FROM " + DB_TABLENAME + " where id  = ?";

	private SQLStatement mCustomerInsert = null;
	private SQLStatement mCustomerInsertNoID = null;
	private PreparedStatement mCustomerGet = null;
	private PreparedStatement mCustomerGetAll = null;
	private SQLStatement mCustomerUpdate = null;
	private SQLStatement mCustomerDelete = null;

	public CustomerDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool, null);
		setConnectionAndStatements();
	}

	protected void setConnectionAndStatements() throws SQLException {
		this.connection = this.getConnection();
		mCustomerInsert = new SQLStatement(connection, CUSTOMER_INSERT, this.connectionPool.getCoreInvoker());
		mCustomerInsertNoID = new SQLStatement(connection, CUSTOMER_INSERT_NOID, this.connectionPool.getCoreInvoker());
		mCustomerUpdate = new SQLStatement(connection, CUSTOMER_UPDATE, this.connectionPool.getCoreInvoker());
		mCustomerDelete = new SQLStatement(connection, CUSTOMER_DELETE, this.connectionPool.getCoreInvoker());
		mCustomerGet = connection.prepareStatement(CUSTOMER_SELECT + CUSTOMER_SELECT_FILTER);
		mCustomerGetAll = connection.prepareStatement(CUSTOMER_SELECT);
	}

	@Override
	public Customer getCustomer(Long id) throws Exception {
		Customer customer = null;
		try {
			mCustomerGet.setLong(1, id);
			ResultSet rs = mCustomerGet.executeQuery();// retrieve the data
			while (rs.next()) {
				customer = new Customer();
				customer.setId(new Long(rs.getInt("id")));
				customer.setName(rs.getString("name"));
				customer.setType(Customer.getCustomerType(rs.getString("type")));
				List <Sale> sales = DAOFacade.getInstance().getSaleDAO().getSalesForCustomer(customer.getId());
				List <Booking> bookings = new ArrayList();
				for(Sale sale: sales) {
					bookings.add(DAOFacade.getInstance().getBookingDAO().getBooking(sale.getBookingId()));
				}
				customer.setBookings(bookings);
				customer.setSales(sales);
				customer.setTrips(DAOFacade.getInstance().getTripDAO().getTripsForCustomer(customer.getId()));
				
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return customer;
	}

	@Override
	public List<Customer> getCustomer() throws Exception {
		List<Customer> returnList = new ArrayList();
		try {
			ResultSet rs = mCustomerGetAll.executeQuery();// retrieve the data
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(new Long(rs.getInt("id")));
				customer.setName(rs.getString("name"));
				customer.setType(Customer.getCustomerType(rs.getString("type")));
				List <Sale> sales = DAOFacade.getInstance().getSaleDAO().getSalesForCustomer(customer.getId());
				List <Booking> bookings = new ArrayList();
				for(Sale sale: sales) {
					bookings.add(DAOFacade.getInstance().getBookingDAO().getBooking(sale.getBookingId()));
				}
				customer.setBookings(bookings);
				customer.setSales(sales);
				customer.setTrips(DAOFacade.getInstance().getTripDAO().getTripsForCustomer(customer.getId()));
				returnList.add(customer);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}

	@Override
	public Customer addCustomer(Customer customer) throws Exception {
		Customer returnValue = customer;
		Long id = customer.getId();
		SQLStatement sStatement = null;
		int i = 0;
		if (id != null) {
			sStatement = mCustomerInsert;
			sStatement.setNString(++i, customer.getId().toString());
		} else
			sStatement = mCustomerInsertNoID;
		sStatement.setNString(++i, customer.getName());
		sStatement.setNString(++i, customer.getType().toString());

		try {
			if(id==null) {
				customer.setId(new Long(sStatement.executeInsert()));
			}else
				sStatement.executeInsert();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int removeCustomer(Long id) throws Exception {
		int returnValue = 0;
		mCustomerDelete.setNString(1, id.toString());
		try {
			returnValue = mCustomerDelete.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int updateCustomer(Customer customer) throws Exception {
		int returnValue = 0;
		
		mCustomerUpdate.setNString(1, customer.getName());
		mCustomerUpdate.setNString(2, customer.getType().toString());
		mCustomerUpdate.setNString(3, customer.getId().toString());
		try {
			returnValue = mCustomerUpdate.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public List<String> getTypes() {
		List<String> returnList = new ArrayList();
		for(CustomerTypeEnum e: CustomerTypeEnum.values())
			returnList.add(e.toString());
		return returnList;
	}
}
