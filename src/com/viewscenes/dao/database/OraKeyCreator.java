package com.viewscenes.dao.database;



import java.sql.*;

import java.util.*;



/**

 * 基于oracle主键生成器

 * 应用oracle的序列生成主键

 */



public class OraKeyCreator

        implements KeyCreator {

    String m = "OraKeyCreator:";



    /**

     * 根据表名得到主键

     * @param tab 表名

     * @return 加1后的主键,如果失败返回-1

     * @throws DbException

     */

    public String getKey(String tab,String dbName) throws DbException {

        String msg = m + "getKey";



        try {

            List l = getSequence(tab, 1,dbName);

            if (l != null && l.isEmpty() == false) {

                return (String) l.get(0);

            }

        } catch (DbException ex) {

            throw ex;

        } catch (Exception ex) {

            throw new DbException(msg, ex);

        }

        return "-1";

    }



    /**

     * 得到表的主键

     * @param tab 表名

     * @param num 一次取多个主键的数目

     * @return 主键值列表

     * @throws DbException

     */

    private List getSequence(String tab, long num,String dbName) throws DbException {

        String msg = m + "getSequence: ";

        DbComponent db = new DbComponent(dbName);

        Connection con = null;

        String sql = null;

        Statement stmt = null;

        ResultSet rs = null;

        long key = -1;

        ArrayList l = new ArrayList();



        try {

            //得到数据库的连接和创建Statement

            con = db.getDB();

            stmt = con.createStatement();

            sql = getSeqSql(tab);



            while (num-- > 0) {

                //每次执行resultset前先关闭

                if (rs != null) {

                    rs.close();

                }

                //得到多个主键值

                rs = stmt.executeQuery(sql);

                if (rs.next()) {

                    key = rs.getLong("s");

                    l.add(String.valueOf(key));

                }

            }

        } catch (SQLException ex) {

            throw new DbException(msg + sql, ex);

        } finally {

            db.closeDB(con, stmt, rs);

        }

        return l;

    }



    /**

     * 根据表名得到相应的序列值

     * @param tab

     * @return

     */

    private String getSeqSql(String tab) {

        //传入的参数是表名，对应的序列名称是表名去掉_tab再加上_seq.



        String name = tab.toLowerCase().substring(0,

                (tab.length() - 4));

        return "select " + name + "_seq.nextval s from DUAL";



    }



    /**

     * 更改时间格式（只适用于ORACLE)

     * @param con

     * @throws SQLException

     */

    public void alterSession(Connection con) throws SQLException {

        try {

            Statement stmt = con.createStatement();

            String sql =

                    "ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI:SS'";

            stmt.execute(sql);

            if (stmt != null) {

                stmt.close();

            }

        } catch (SQLException e) {

            throw new SQLException("OraKeyCreator:alterSession::Exception " + e.toString());

        }



    }



}
