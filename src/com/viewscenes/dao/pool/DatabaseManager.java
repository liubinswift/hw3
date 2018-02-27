package com.viewscenes.dao.pool;



import java.sql.*;



public final class DatabaseManager {

    private static String configPath = "DBPoolConfig.xml";

    private static DBPoolManager poolManager = new DBPoolManager(configPath);


    public synchronized static DBPoolManager getDBPoolManager() {

        if (poolManager == null) {

            poolManager = new DBPoolManager(configPath);

        }
        return poolManager;
    }


    public synchronized static Connection getConnection() {

        if (poolManager == null) {

            poolManager = new DBPoolManager(configPath);

        }

        Connection con = null;

        try {

            con = poolManager.getConnection();

        } catch (SQLException ex) {

        }

        return con;

    }



    public synchronized static Connection getConnection(String poolName) {

        if (poolManager == null) {

            poolManager = new DBPoolManager(configPath);

        }

        Connection con = null;

        try {

            con = poolManager.getConnection(poolName);

        } catch (SQLException ex) {

        }

        return con;

    }



}

