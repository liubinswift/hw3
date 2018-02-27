package com.viewscenes.dao.pool;



/**

 *

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author unascribed

 * @version 1.0

 */



import java.sql.*;

import java.util.*;

import org.jdom.*;

import com.viewscenes.dao.*;

public class DBPoolManager {

    static Map poolPropertyMap = null;

    static Map poolMap = null;

    static String m_sDefaultPoolName = "default";

    public static int ischecked = 1;//是否检查数据库的连接，0为检查，1为不检查

    public DBPoolManager() {
        loadConfigFile(null);
        createMultiPool();
    }

    public DBPoolManager(String sConfigPath) {
        loadConfigFile(sConfigPath);
        createMultiPool();
    }

    public void loadConfigFile(String sConfigPath) {
        try {

            poolPropertyMap = new HashMap();



            Element dbpoolconfig = XmlReader.getConfigItem("DBPoolConfig");

            List dbpoollist = dbpoolconfig.getChildren();

            for (int i = 0; i < dbpoollist.size(); i++) {

                Element pool = (Element) dbpoollist.get(i);

                if (pool.getName().equals("DBPool")) {

                    String name = pool.getAttributeValue("name");

                    String driver = pool.getAttributeValue("driver");

                    String url = pool.getAttributeValue("url");

                    String user = pool.getAttributeValue("user");

                    String password = pool.getAttributeValue("password");

                    int minConn = Integer.parseInt(pool.getAttributeValue("minconnection"));

                    int maxConn = Integer.parseInt(pool.getAttributeValue("maxconnection"));

                    ischecked =  Integer.parseInt(pool.getAttributeValue("ischecked"));

                    PoolProperty poolProperty = new PoolProperty(name, driver, url, user

                            , password, minConn, maxConn);

                    poolPropertyMap.put(name, poolProperty);
                }
            }

            Element defaultpool = dbpoolconfig.getChild("DefaultDBPool");

            m_sDefaultPoolName = defaultpool.getAttributeValue("name");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    public void createMultiPool() {

        poolMap = new HashMap();

        Iterator it = poolPropertyMap.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry entry = (Map.Entry) it.next();

            String sKey = (String) entry.getKey();

            PoolProperty poolProp = (PoolProperty) entry.getValue();

            try {

                DBConnectionPool pool = new DBConnectionPool(poolProp);

                poolMap.put(poolProp.m_sName, pool);

            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    }

    public DBConnectionPool getDBConnectionPool(String poolName) {

        return (DBConnectionPool) poolMap.get(poolName);

    }



    public PooledConnection getConnection(String poolName) throws SQLException {

        DBConnectionPool pool = null;

        Connection con = null;

        try {

            pool = getDBConnectionPool(poolName);

            con = pool.getConnection();

        } catch (SQLException e) {

            throw new SQLException("ConnectionManager:getConnection():SQLException " + e.toString());

        }

        return new PooledConnection(con, pool);

    }



    public PooledConnection getConnection() throws SQLException {

        DBConnectionPool pool = null;

        Connection con = null;

        try {

            pool = getDBConnectionPool(m_sDefaultPoolName);

            con = pool.getConnection();

        } catch (SQLException e) {

            throw new SQLException("ConnectionManager:getConnection():SQLException " + e.toString());

        }

        return new PooledConnection(con, pool);

    }



}
