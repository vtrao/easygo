package com.easygo.daolayer.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.easygo.daolayer.AbstractDAO;
import com.easygo.daolayer.IBranchDAO;
import com.easygo.daolayer.IUserDAO;
import com.easygo.model.Branch;
import com.easygo.system.db.pool.ObjectPool;

public class UserDAO extends AbstractDAO implements IUserDAO {

	public UserDAO(ObjectPool<Connection> connectionPool) throws SQLException {
		super(connectionPool,null);
	}

	@Override
	protected void setConnectionAndStatements() throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
