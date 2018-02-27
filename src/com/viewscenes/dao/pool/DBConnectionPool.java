package com.viewscenes.dao.pool;



import java.sql.*;

import java.util.*;

import com.viewscenes.util.LogTool;

import com.viewscenes.dao.database.*;



/**

 * Implementation of a Connection Pool for database connections.

 *

 * @author  ChenGang

 * @version 1.0, 2003-04

 *

 */



public class DBConnectionPool {

    private List availableConnections = Collections.synchronizedList(new ArrayList());
    private List usedConnections = Collections.synchronizedList(new ArrayList());
    private int totalConnections = 0;
    private int minConnections = 0;
    private String user = null;
    private String password = null;
    private String url = null;
    private String driver = null;
    private String checkQuery = "select sysdate from dual";

    public DBConnectionPool(PoolProperty poolProp) throws SQLException, ClassNotFoundException {
        this.url = poolProp.m_sUrl;
        this.user = poolProp.m_sUser;
        this.password = poolProp.m_sPassword;
        this.totalConnections = poolProp.m_nMaxConn;
        this.driver = poolProp.m_sDriver;
        this.minConnections = poolProp.m_nMinConn;
        this.init();
    }

    protected void init() throws SQLException {
        try {
            Class.forName(driver);
            for (int i = 0; i < minConnections; i++) {
                Connection con = createConnection();
                availableConnections.add(con);
            }
        } catch (ClassNotFoundException cex) {
            throw new SQLException("ConnectionPool:init()::ClassNotFoundException--" + cex.toString());
        } catch (SQLException e) {
            throw new SQLException("ConnectionPool:init()::SQLException--" + e.toString());
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection con = null;

        if (availableConnections.size() > 0) {
            con = (Connection) availableConnections.get(0);
            availableConnections.remove(0);
            usedConnections.add(con);

            try {
                if (con.isClosed()) {
                    con = createConnection();
                } else if(DBPoolManager.ischecked == 0){//分中心测试连接
                    con = checkConnection(con);
                }
            } catch (SQLException e) {
                this.freeConnection(con);
                throw new SQLException(
                        "ConnectionPool:getConnection()::Connection Problem -- SQLException: " + e.toString());
            }
        } else {

            if ((availableConnections.size() + usedConnections.size()) < totalConnections) {
                try {

                    con = createConnection();
                    usedConnections.add(con);
                } catch (SQLException se) {
                    throw new SQLException(
                            "ConnectionPool:getConnection()::Cannot create new connection -- Exception:" +
                            se.toString());
                }
            } else {
                System.out.println("等待连接++++++++++++++++++++++");
				
				this.destroy();
                con = getConnection();
            }
        }

        if(con == null)
        {
          LogTool.debug("availableConnections.size():"+availableConnections.size());
          LogTool.debug("usedConnections.size():"+usedConnections.size());
          LogTool.debug("totalConnections:"+totalConnections);
        }
  
        return con;
    }

    public synchronized void freeConnection(Connection con) {

        int index = usedConnections.indexOf(con);

        if (index >= 0) {
            availableConnections.add(con);
            usedConnections.remove(index);
            notifyAll();
        }
    }

    protected Connection createConnection() throws SQLException {

        Connection con = null;

        try {

            con = DriverManager.getConnection(this.url, this.user, this.password);

            alterSession(con);

        } catch (SQLException e) {
            throw new SQLException("ConnectionPool:createConnection::SQLException " + e.toString());
        }
        return con;
    }

    protected Connection checkConnection(Connection con) throws SQLException {

        if (!isNull(this.checkQuery)) {

            PreparedStatement st = null;

            try {
                st = con.prepareStatement(this.checkQuery);
//                st.execute();
            } catch (SQLException sqle) {
                try {
                    int index = this.usedConnections.indexOf(con);
                    this.usedConnections.remove(index);
                    con = this.createConnection();
                    this.usedConnections.add(con);
                } catch (Exception e) {
                    throw new SQLException(
                            "ConnectionPool:checkConnection()::BAD CONNECTION COULD NOT BE FIXED");
                }
            } finally {
                try {
                    if (st != null) {
                        st.close();
                    }
                } catch (Exception ex) {
            }

            }
        }

        return con;
   }



    public synchronized boolean checkUsedConnections() throws SQLException {

        boolean ret = false;

        try {

            for (int i = 0; i < usedConnections.size(); i++) {

                Connection con = (Connection) usedConnections.get(i);

                if (con.isClosed()) {

                    con = createConnection();

                    availableConnections.add(con);

                    usedConnections.remove(i);

                    ret = true;

                }

            }

        } catch (SQLException e) {

            throw new SQLException("ConnectionPool:checkUsedConnections::Exception " + e.toString());

        }

        return ret;

    }



    public synchronized void closeAvailableConnections() {

        closeConnections(availableConnections);

    }



    public synchronized void closeUsedConnections() {

        closeConnections(usedConnections);

    }



    public synchronized void closeAllConnections() {

        closeAvailableConnections();

        closeConnections(usedConnections);

    }



    public synchronized void destroy() {

        closeAllConnections();

        availableConnections.clear();

        usedConnections.clear();

    }



    protected void closeConnections(List aList) {

        for (int i = 0; i < aList.size(); i++) {

            Connection con = (Connection) aList.get(i);

            try {
                
                con.close();

            } catch (Exception e) {

            }

        }

    }



    protected boolean isNull(String s) {

        if (s == null || s.equals("") || s.length() < 1) {

            return true;

        }

        return false;

    }



    protected void finalize() {

        destroy();

    }



    private void alterSession(Connection con) throws SQLException {

        try {

            if (driver.indexOf("oracle") != -1) {

                keyFactory fac = new keyFactory();

                KeyCreator key = fac.create(keyFactory.ORA_KEY);

                key.alterSession(con);

            }

        } catch (SQLException e) {

            throw new SQLException("ConnectionPool:alterSession::Exception " + e.toString());

        }

    }

}
