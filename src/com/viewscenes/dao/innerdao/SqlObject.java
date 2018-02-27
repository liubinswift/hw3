package com.viewscenes.dao.innerdao;



/**

 * sql语句对象

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class SqlObject {

    private StringBuffer _sql = new StringBuffer();//主句

    private String group_sql = "";//group by子句

    private String order_sql = "";//order by子句



    /**

     * 返回完整的sql句子

     * @return

     */

    public String toString() {

        _sql.append(" ").append(group_sql).append(order_sql);

        return _sql.toString().trim();

    }



    /**

     * 设置group by子句

     * @param group_sql

     */

    public void setGroup_sql(String group_sql) {

        this.group_sql = group_sql;

    }



    /**

     * 设置order by子句

     * @param order_sql

     */

    public void setOrder_sql(String order_sql) {

        if (this.order_sql.equals("")) {

            this.order_sql = " order by ";

        } else {

            this.order_sql += ",";

        }

        this.order_sql += " " + order_sql;

    }



    /**

     * 设置sql主句

     * 如果主句是第一条，则过滤掉and或or,如果不是，则

     * @param sql

     */

    public void setSql(String sql) {

        if(_sql.length() == 0)

            _sql.append(Common.FilStartOper(sql));

        else

            _sql.append(sql);

    }



}
