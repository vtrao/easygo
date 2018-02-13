package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.DAOFacade;
import com.easygo.daolayer.IBranchDAO;
import com.easygo.model.Branch;
import com.easygo.model.Car;
import com.easygo.model.Customer;
import com.easygo.system.db.SQLStatement;
import com.easygo.system.db.pool.ObjectPool;

public class BranchDAO extends AbstractDAO implements IBranchDAO {
	public static final String DB_TABLENAME = "branch";
	private static final String BRANCH_INSERT = "INSERT INTO " + DB_TABLENAME + " (id, name, location, minstock, maxstock )"
			+ "VALUES (?, ?, ?, ?, ?)";
	private static final String BRANCH_INSERT_NOID = "INSERT INTO " + DB_TABLENAME + " (name, location, minstock, maxstock )"
			+ "VALUES (?, ?, ?, ?)";
	private static final String BRANCH_SELECT = " SELECT id, name, location, minstock, maxstock FROM " + DB_TABLENAME;
	private static final String BRANCH_SELECT_FILTER = " where id = ?";
	private static final String BRANCH_UPDATE = "UPDATE " + DB_TABLENAME + " set name = ?, location = ?, minstock = ?, maxstock = ? "
			+ " where id  = ?";
	private static final String BRANCH_DELETE = "DELETE FROM " + DB_TABLENAME + " where id  = ?";

	private SQLStatement mBranchInsert = null;
	private SQLStatement mBranchInsertNoID = null;
	private PreparedStatement mBranchGet = null;
	private PreparedStatement mBranchGetAll = null;
	private SQLStatement mBranchUpdate = null;
	private SQLStatement mBranchDelete = null;
	
	public BranchDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool, null);
		setConnectionAndStatements();
	}

	@Override
	protected void setConnectionAndStatements() throws SQLException {
		this.connection = this.getConnection();
		mBranchInsert = new SQLStatement(connection, BRANCH_INSERT, this.connectionPool.getCoreInvoker());
		mBranchInsertNoID = new SQLStatement(connection, BRANCH_INSERT_NOID, this.connectionPool.getCoreInvoker());
		mBranchUpdate = new SQLStatement(connection, BRANCH_UPDATE, this.connectionPool.getCoreInvoker());
		mBranchDelete = new SQLStatement(connection, BRANCH_DELETE, this.connectionPool.getCoreInvoker());
		mBranchGet = connection.prepareStatement(BRANCH_SELECT + BRANCH_SELECT_FILTER);
		mBranchGetAll = connection.prepareStatement(BRANCH_SELECT);
	}

	@Override
	public Branch getBranch(Long id) throws Exception  {
		Branch branch = null;
		try {
			mBranchGet.setLong(1, id);
			ResultSet rs = mBranchGet.executeQuery();// retrieve the data
			while (rs.next()) {
				branch = new Branch();
				branch.setId(new Long(rs.getInt("id")));
				branch.setName(rs.getString("name"));
				//branch.setLocation(rs.getString("location"));
				branch.setMin(rs.getInt("minstock"));
				branch.setMax(rs.getInt("maxstock"));
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return branch;
	}

	@Override
	public List<Branch> getBranch() throws Exception  {
		List<Branch> returnList = new ArrayList();
		try {
			ResultSet rs = mBranchGetAll.executeQuery();// retrieve the data
			while (rs.next()) {
				Branch br = new Branch();
				br.setId(new Long(rs.getInt("id")));
				br.setName(rs.getString("name"));
				//br.setLocation(rs.getString("location"));
				br.setMin(rs.getInt("minstock"));
				br.setMax(rs.getInt("maxstock"));
				returnList.add(br);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnList;
	}

	@Override
	public Branch addBranch(Branch branch) throws Exception {
		Branch returnValue = branch;
		Long id = branch.getId();
		SQLStatement sStatement = null;
		int i = 0;
		if (id != null) {
			sStatement = mBranchInsert;
			sStatement.setNString(++i, branch.getId().toString());
		} else
			sStatement = mBranchInsertNoID;
		sStatement.setNString(++i, branch.getName());
		sStatement.setNString(++i, branch.getName());//should be location
		sStatement.setInt(++i, branch.getMin());
		sStatement.setInt(++i, branch.getMax());
		try {
			if(id==null) {
				branch.setId(new Long(sStatement.executeInsert()));
			}else
				sStatement.executeInsert();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int removeBranch(Long id) throws Exception {
		int returnValue = 0;
		mBranchDelete.setNString(1, id.toString());
		try {
			returnValue = mBranchDelete.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}

	@Override
	public int updateBranch(Branch branch) throws Exception  {
		int returnValue = 0;
		mBranchUpdate.setNString(1, branch.getName());
		mBranchUpdate.setNString(2, branch.getName());//location 
		mBranchUpdate.setInt(3, branch.getMin());
		mBranchUpdate.setInt(4, branch.getMax());
		mBranchUpdate.setNString(5, branch.getId().toString());
		try {
			returnValue = mBranchUpdate.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e);
		}
		return returnValue;
	}


}
