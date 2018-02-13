package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.DAOFacade;
import com.easygo.daolayer.ICarDAO;
import com.easygo.daolayer.ICarDAO;
import com.easygo.model.Branch;
import com.easygo.model.Car;
import com.easygo.model.Car.CarTypeEnum;
import com.easygo.model.Customer;
import com.easygo.model.Customer.CustomerTypeEnum;
import com.easygo.system.db.SQLStatement;
import com.easygo.system.db.pool.ObjectPool;
import com.example.helloworld.core.Category;

public class CarDAO extends AbstractDAO implements ICarDAO {
	public static final String DB_TABLENAME = "car";
	private static final String CAR_INSERT = "INSERT INTO " + DB_TABLENAME
			+ " (id, name, reg, status, type, homebranchid, currentbranchid, costperday )" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String CAR_INSERT_NOID = "INSERT INTO " + DB_TABLENAME
			+ " (name, reg, status, type, homebranchid, currentbranchid, costperday )" + "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String CAR_SELECT = " SELECT id, name, reg, status, type, homebranchid, currentbranchid, costperday FROM "
			+ DB_TABLENAME;
	private static final String CAR_SELECT_FILTER = " where id = ?";
	private static final String CAR_SELECTHB_FILTER = " where homebranchid = ?";
	private static final String CAR_SELECTCB_FILTER = " where currentbranchid = ?";
	private static final String CAR_UPDATE = "UPDATE " + DB_TABLENAME
			+ " set name = ?, reg = ?, status = ?, type = ?, homebranchid = ?, currentbranchid = ?, costperday = ? " + " where id  = ?";
	private static final String CAR_DELETE = "DELETE FROM " + DB_TABLENAME + " where id  = ?";

	private SQLStatement mCarInsert = null;
	private SQLStatement mCarInsertNoID = null;
	private PreparedStatement mCarGet = null;
	private PreparedStatement mCarGetAll = null;
	private PreparedStatement mCarGetAllForHomeBranch = null;
	private PreparedStatement mCarGetAllForCurrentBranch = null;
	private SQLStatement mCarUpdate = null;
	private SQLStatement mCarDelete = null;

	public CarDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool, null);
		setConnectionAndStatements();
	}

	@Override
	protected void setConnectionAndStatements() throws SQLException {
		this.connection = this.getConnection();
		mCarInsert = new SQLStatement(connection, CAR_INSERT, this.connectionPool.getCoreInvoker());
		mCarInsertNoID = new SQLStatement(connection, CAR_INSERT_NOID, this.connectionPool.getCoreInvoker());
		mCarUpdate = new SQLStatement(connection, CAR_UPDATE, this.connectionPool.getCoreInvoker());
		mCarDelete = new SQLStatement(connection, CAR_DELETE, this.connectionPool.getCoreInvoker());
		mCarGet = connection.prepareStatement(CAR_SELECT + CAR_SELECT_FILTER);
		mCarGetAllForHomeBranch = connection.prepareStatement(CAR_SELECT + CAR_SELECTHB_FILTER);
		mCarGetAllForCurrentBranch = connection.prepareStatement(CAR_SELECT + CAR_SELECTCB_FILTER);
		mCarGetAll = connection.prepareStatement(CAR_SELECT);

	}

	@Override
	public Car getCar(Long id) throws Exception {
		Car car = null;
		try {
			mCarGet.setLong(1, id);
			ResultSet rs = mCarGet.executeQuery();// retrieve the data
			while (rs.next()) {
				car = new Car();
				car.setId(new Long(rs.getInt("id")));
				car.setName(rs.getString("name"));
				car.setType(Car.getCarType(rs.getString("type")));
				car.setStatus(Car.getCarStatus(rs.getString("status")));
				car.setReg(rs.getString("reg"));
				int hbranchid = rs.getInt("homebranchid");
				int cbranchid = rs.getInt("currentbranchid");
				Branch hbr = DAOFacade.getInstance().getBranchDAO().getBranch(new Long(hbranchid));
				Branch cbr = hbr;
				if (hbranchid != cbranchid)
					cbr = DAOFacade.getInstance().getBranchDAO().getBranch(new Long(cbranchid));
				car.setHomebranch(hbr);
				car.setCurrentbranch(cbr);
				car.setCostPerDay(rs.getFloat("costperday"));
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return car;
	}

	// branchid == -1 returns all cars, for branch id if homebranch==false
	// returns the ones in current branch
	@Override
	public List<Car> getCars(Long branchid, boolean homebranch) throws Exception {
		List<Car> returnList = new ArrayList();
		PreparedStatement pStatement = null;
		if (branchid > 0) {
			if (homebranch)
				pStatement = mCarGetAllForHomeBranch;
			else
				pStatement = mCarGetAllForCurrentBranch;
			pStatement.setLong(1, branchid);
		} else
			pStatement = mCarGetAll;
		try {
			ResultSet rs = pStatement.executeQuery();// retrieve the data
			while (rs.next()) {
				Car car = new Car();
				car.setId(new Long(rs.getInt("id")));
				car.setName(rs.getString("name"));
				car.setType(Car.getCarType(rs.getString("type")));
				car.setStatus(Car.getCarStatus(rs.getString("status")));
				car.setReg(rs.getString("reg"));
				int hbranchid = rs.getInt("homebranchid");
				int cbranchid = rs.getInt("currentbranchid");
				Branch hbr = DAOFacade.getInstance().getBranchDAO().getBranch(new Long(hbranchid));
				Branch cbr = hbr;
				if (hbranchid != cbranchid)
					cbr = DAOFacade.getInstance().getBranchDAO().getBranch(new Long(cbranchid));
				car.setHomebranch(hbr);
				car.setCurrentbranch(cbr);
				car.setCostPerDay(rs.getFloat("costperday"));
				returnList.add(car);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}

	@Override
	public Car addCar(Car car) throws Exception {
		Car returnValue = car;
		Long id = car.getId();
		SQLStatement sStatement = null;
		int i = 0;
		if (id != null) {
			sStatement = mCarInsert;
			sStatement.setNString(++i, car.getId().toString());
		} else
			sStatement = mCarInsertNoID;

		sStatement.setNString(++i, car.getName());
		sStatement.setNString(++i, car.getReg());
		sStatement.setNString(++i, car.getStatus().toString());
		sStatement.setNString(++i, car.getType().toString());
		sStatement.setNString(++i, car.getHomebranch().getId().toString());
		sStatement.setNString(++i, car.getCurrentbranch().getId().toString());
		sStatement.setFloat(++i, car.getCostPerDay());
		try {
			if (id == null)
				car.setId(new Long(sStatement.executeInsert()));
			else
				sStatement.executeInsert();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int removeCar(Long id) throws Exception {
		int returnValue = 0;
		mCarDelete.setNString(1, id.toString());
		try {
			returnValue = mCarDelete.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int updateCar(Car car) throws Exception {
		int returnValue = 0;
		mCarUpdate.setNString(1, car.getName());
		mCarUpdate.setNString(2, car.getReg());
		mCarUpdate.setNString(3, car.getStatus().toString());
		mCarUpdate.setNString(4, car.getType().toString());
		mCarUpdate.setNString(5, car.getHomebranch().getId().toString());
		mCarUpdate.setNString(6, car.getCurrentbranch().getId().toString());
		mCarUpdate.setFloat(7, car.getCostPerDay());
		if (car.getId() == null)
			return -1;
		else
			mCarUpdate.setNString(8, car.getId().toString());
		try {
			returnValue = mCarUpdate.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}
	
	@Override
	public List<String> getTypes() {
		List<String> returnList = new ArrayList();
		for(CarTypeEnum e: CarTypeEnum.values())
			returnList.add(e.toString());
		return returnList;
	}
}
