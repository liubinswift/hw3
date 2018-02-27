package com.viewscenes.dao.database;



import java.sql.*;



/**

 * 数据库主键生成器接口

 */

public interface KeyCreator {



    /**

     * 得到主键

     * @return String

     */

    public String getKey(String tab,String dbName) throws DbException;



    /**

     * 更改时间格式（只适用于ORACLE)

     * @param con

     * @throws SQLException

     */

    public void alterSession(Connection con) throws SQLException;



}

