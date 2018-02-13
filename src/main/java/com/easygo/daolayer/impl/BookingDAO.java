package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.DAOFacade;
import com.easygo.daolayer.IBookingDAO;
import com.easygo.daolayer.ITripDAO;
import com.easygo.model.DemoCompleteBooking;
import com.easygo.model.Booking;
import com.easygo.model.Sale;
import com.easygo.model.Trip;
import com.easygo.model.Trip.TripStatusEnum;
import com.easygo.system.db.SQLStatement;
import com.easygo.system.db.pool.ObjectPool;

public class BookingDAO extends AbstractDAO implements IBookingDAO {
	public static final String DB_TABLENAME = "booking";
	private static final String BOOKING_INSERT = "INSERT INTO booking ( description, bookingtime, price, totaldays, outsstationtrip)"
			+ "VALUES (?, ?, ?, ?, ?)";

	private static final String BOOKINGSELECT = " SELECT id, description, bookingtime, price, totaldays, outsstationtrip FROM "
			+ DB_TABLENAME;
	private static final String BOOKING_SELECT_FILTER = " where id = ?";
	private SQLStatement mBookingInsert = null;
	private PreparedStatement mBookingGet = null;
	private PreparedStatement mBookingGetId = null;

	public BookingDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool, null);
		setConnectionAndStatements();
	}

	@Override
	protected void setConnectionAndStatements() throws SQLException {
		this.connection = this.getConnection();
		mBookingInsert = new SQLStatement(connection, BOOKING_INSERT, this.connectionPool.getCoreInvoker());
		mBookingGet = connection.prepareStatement(BOOKINGSELECT);
		mBookingGetId = connection.prepareStatement(BOOKINGSELECT + BOOKING_SELECT_FILTER);
	}

	@Override
	public List<Booking> getBooking() throws Exception {
		List<Booking> returnList = new ArrayList();
		try {
			ResultSet rs = mBookingGet.executeQuery();// retrieve the data
			while (rs.next()) {
				Booking booking = new Booking();
				booking.setId(new Long(rs.getInt("id")));
				booking.setDescription(rs.getString("description"));
				booking.setBookingTime(rs.getString("bookingtime"));
				booking.setPrice(new Float(rs.getFloat("price")));
				booking.setTotalDays(new Integer(rs.getInt("totaldays")));
				booking.setOutStationTrip(new Integer(rs.getInt("outsstationtrip")));
				returnList.add(booking);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}

	@Override
	public Booking getBooking(Long id) throws Exception {
		Booking booking = null;
		try {
			mBookingGetId.setLong(1, id);
			ResultSet rs = mBookingGetId.executeQuery();// retrieve the data
			while (rs.next()) {
				booking = new Booking();
				booking.setId(new Long(rs.getInt("id")));
				booking.setDescription(rs.getString("description"));
				booking.setBookingTime(rs.getString("bookingtime"));
				booking.setPrice(new Float(rs.getFloat("price")));
				booking.setTotalDays(new Integer(rs.getInt("totaldays")));
				booking.setOutStationTrip(new Integer(rs.getInt("outsstationtrip")));
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return booking;
	}

	@Override
	public int addBooking(List<DemoCompleteBooking> bookingList) throws Exception {
		// TODO: to optimize this code to do bulk insert into the table
		int bookingId = 0, saleId = 0;
		if (bookingList.size() > 0) {
			// create booking table entry
			bookingId = addBooking(bookingList.get(0));

			// TODO: WORKFLOW management and sale to be done separately
			// TODO: IF THIS IS DONE DIRECTLY: Transaction management is needed
			// TODO: FOR THROW AWAY PROTOTYPE PURPOSES, SQLITE IS IN AUTOCOMMIT
			// MODE
			Sale sale = new Sale();
			sale.setBookingId(new Long(bookingId));
			sale.setCustomerId(bookingList.get(0).getCustomerId());
			sale.setPrice(bookingList.get(0).getPrice());
			saleId = DAOFacade.getInstance().getSaleDAO().addSale(sale);

			// TODO: WORKFLOW management and trips to be done separately
			// Schedule the trip for the cars

			Date dt = new Date(java.lang.System.currentTimeMillis());
			String currentDate = DAOFacade.dataFormat.format(dt);
			ITripDAO tripDAO = DAOFacade.getInstance().getTripDAO();
			int tripId = 0;
			for (DemoCompleteBooking booking : bookingList) {
				Trip trip = new Trip();
				trip.setBookingId(new Long(bookingId));
				trip.setCustomerId(booking.getCustomerId());
				trip.setCarId(booking.getCarId());
				trip.setBranchId(booking.getBranchId());
				trip.setFrom(DAOFacade.dataFormat.format(booking.getFrom()));
				// DEMO PURPOSE: LOGIC TO ADD TRIPS AS COMPLETED OR SCHEDULED
				// DEPENDING ON CURRENT DATE
				String toDate = DAOFacade.dataFormat.format(booking.getTo());
				if(currentDate.compareTo(toDate)>0)
					trip.setStatus(TripStatusEnum.COMPLETED);
				else
					trip.setStatus(TripStatusEnum.SCHEDULED);
				trip.setTo(DAOFacade.dataFormat.format(booking.getTo()));
				trip.setTotalDays(booking.getTotalDays());
				trip.setOutStationTrip(booking.getOutStationTrip());
				tripId = tripDAO.addTrip(trip);
			}
		}
		return bookingId;
	}

	private int addBooking(DemoCompleteBooking booking) throws Exception {
		int returnValue = 0;
		String outTrip = "";
		if (booking.getOutStationTrip() != 0)
			outTrip = "Outstation trip";
		String description = outTrip + " from " + DAOFacade.dataFormat.format(booking.getFrom()) + " to "
				+ DAOFacade.dataFormat.format(booking.getTo()) + " for " + booking.getTotalDays()
				+ " and total cost is " + booking.getPrice();
		Date dt = new Date(java.lang.System.currentTimeMillis());
		mBookingInsert.setNString(1, description);
		mBookingInsert.setNString(2, DAOFacade.dataFormat.format(dt));
		mBookingInsert.setFloat(3, booking.getPrice());
		mBookingInsert.setInt(4, booking.getTotalDays());
		mBookingInsert.setInt(5, booking.getOutStationTrip());
		try {
			returnValue = mBookingInsert.executeInsert();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int removeBooking(Booking booking) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBooking(Booking booking) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
