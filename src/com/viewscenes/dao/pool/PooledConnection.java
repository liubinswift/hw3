/*

 * PooledConnection.java

 *

 * Copyright 2002 Richardson Publications All Rights Reserved.

 *

 * This file is part of the RP Database Connection Pool.

 * The RP Database Connection Pool is free software; you can redistribute it and/or modify

 * it under the terms of the GNU General Public License as published by

 * the Free Software Foundation; either version 2 of the License, or

 * (at your option) any later version.

 * The RP Database Connection Pool is distributed in the hope that it will be useful,

 * but WITHOUT ANY WARRANTY; without even the implied warranty of

 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the

 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License

 * along with the RP Database Connection Pool; if not, write to the Free Software

 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 * http://www.richardsonpublications.com

 * CONTACT: email = contact@richardsonpublications.com

 */



package com.viewscenes.dao.pool;



import java.sql.*;

import java.util.*;
import java.util.concurrent.Executor;



/**

 * Implementation of a database connection that cannot be physically closed.  All calls to

 * close() will return this connection to the pool registered with this connection.

 * JDK 1.4 version

 *

 * @author  Dan Richardson

 * @version 1.1, 03/2003

 */



public class PooledConnection

        implements Connection {

    Connection con = null;
    DBConnectionPool pool = null;
    String callerClass,callerMethod;
    long callTime;
    String executSql;



    public PooledConnection(Connection con, DBConnectionPool pool) {

        this.con = con;

        this.pool = pool;

    }



    public Statement createStatement() throws SQLException {

        return con.createStatement();

    }



    public PreparedStatement prepareStatement(String sql) throws SQLException {

        return con.prepareStatement(sql);

    }



    public CallableStatement prepareCall(String sql) throws SQLException {

        return con.prepareCall(sql);

    }

    public void setTypeMap(Map<String,java.lang.Class<?>> map) throws SQLException {

        con.setTypeMap(map);

    }


    public String nativeSQL(String sql) throws SQLException {

        return con.nativeSQL(sql);

    }



    public void setAutoCommit(boolean b) throws SQLException {

        con.setAutoCommit(b);

    }



    public boolean getAutoCommit() throws SQLException {

        return con.getAutoCommit();

    }



    public void commit() throws SQLException {

        con.commit();

    }



    public void rollback() throws SQLException {

        con.rollback();

    }



    public void close() throws SQLException {

        this.pool.freeConnection(this.con);

    }



    public boolean isClosed() throws SQLException {

        return con.isClosed();

    }



    public DatabaseMetaData getMetaData() throws SQLException {

        return con.getMetaData();

    }



    public void setReadOnly(boolean readOnly) throws SQLException {

        con.setReadOnly(readOnly);

    }



    public boolean isReadOnly() throws SQLException {

        return con.isReadOnly();

    }



    public void setCatalog(String catalog) throws SQLException {

        con.setCatalog(catalog);

    }



    public String getCatalog() throws SQLException {

        return con.getCatalog();

    }



    public void setTransactionIsolation(int level) throws SQLException {

        con.setTransactionIsolation(level);

    }



    public int getTransactionIsolation() throws SQLException {

        return con.getTransactionIsolation();

    }



    public SQLWarning getWarnings() throws SQLException {

        return con.getWarnings();

    }



    public void clearWarnings() throws SQLException {

        con.clearWarnings();

    }



    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {

        return con.createStatement(resultSetType, resultSetConcurrency);

    }



    public PreparedStatement prepareStatement(String sql, int resultSetType,

                                              int resultSetConcurrency) throws SQLException {

        return con.prepareStatement(sql, resultSetType, resultSetConcurrency);

    }



    public CallableStatement prepareCall(String sql, int resultSetType,

                                         int resultSetConcurrency) throws SQLException {

        return con.prepareCall(sql, resultSetType, resultSetConcurrency);

    }



    public Map getTypeMap() throws SQLException {

        return con.getTypeMap();

    }






    public void setHoldability(int holdability) throws SQLException {

        con.setHoldability(holdability);

    }



    public int getHoldability() throws SQLException {

        return con.getHoldability();

    }



    public Savepoint setSavepoint() throws SQLException {

        return con.setSavepoint();

    }



    public Savepoint setSavepoint(String name) throws SQLException {

        return con.setSavepoint(name);

    }



    public void rollback(Savepoint savepoint) throws SQLException {

        con.rollback(savepoint);

    }



    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        con.releaseSavepoint(savepoint);

    }



    public Statement createStatement(int resultSetType, int resultSetConcurrency,

                                     int resultSetHoldability) throws SQLException {

        return con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);

    }



    public PreparedStatement prepareStatement(String sql, int resultSetType,

                                              int resultSetConcurrency, int resultSetHoldability) throws

            SQLException {

        return con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);

    }



    public CallableStatement prepareCall(String sql, int resultSetType,

                                         int resultSetConcurrency,

                                         int resultSetHoldability) throws SQLException {

        return con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);

    }



    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {

        return con.prepareStatement(sql, autoGeneratedKeys);

    }



    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {

        return con.prepareStatement(sql, columnIndexes);

    }



    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {

        return con.prepareStatement(sql, columnNames);

    }



    public void cleanUpConnection() throws SQLException {

        con.close();

    }



	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		
	}



	public String getSchema() throws SQLException {
		return null;
	}



	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		
	}



	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}



	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}

