package com.easygo.daolayer;

import java.sql.Connection;
import java.sql.SQLException;

import com.easygo.system.db.pool.ObjectPool;

public abstract class AbstractDAO {
	protected ObjectPool<Connection> connectionPool = null;
	protected Connection connection = null;
	
	public AbstractDAO(ObjectPool<Connection> connectionPool, Connection connection) throws SQLException {
		super();
		this.connectionPool = connectionPool;
		this.connection = connection;
	}

	protected Connection getConnection() {
		return connectionPool.checkOut();
	}
	
	protected void handleSQLException(SQLException e) throws SQLException {
		connectionPool.handleExceptionOnObjectInPool(connection, e);
		setConnectionAndStatements();
	}
	protected abstract void setConnectionAndStatements() throws SQLException;
	//Override close and commit methods here
}
