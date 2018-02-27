package com.viewscenes.dao.innerdao;



import java.sql.*;

import java.util.*;



import com.viewscenes.dao.database.DbException;

import com.viewscenes.dao.*;

import com.viewscenes.pub.*;

import org.jdom.*;



/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class Common {



  public static void print(String s) {

    System.out.println(s);

  }



  /**

   * 得到表的主键属性名

   * 在配置文件TabProperty中记录表名和主键的映射关系

   * @return

   */

  public static String getKeyName(String tab) throws DbException {

    Element ele = XmlReader.getItem("keyconfig", "table", tab);

    if (ele == null) {

      throw new DbException("Common getKeyName:daoconfig.xml文件的keyconfig节点中找不到" + tab);

    }



    String indexs = ele.getAttributeValue("key");

    if (indexs == null) {

      throw new DbException("Common getKeyName:daoconfig.xml文件的keyconfig节点的" + tab + "中找不到key的值");

    }



    return indexs;

  }



  /**

   * 显示数据集

   * @param cs

   */

  public static void displayRs(ResultSet cs) {

    try {

      ResultSetMetaData reMeta = cs.getMetaData(); //得到字段各个属性

      int columnCount = reMeta.getColumnCount();

      String columnName = null;

      while (cs.next()) {

        for (int i = 1; i <= columnCount; i++) { //注意!!! 从1开始

          if (cs.getObject(i) != null) {

            System.out.print(cs.getObject(i).toString() + " ");

          }

        }

        System.out.println("\r");

      }

    }

    catch (SQLException ex) {

      ex.printStackTrace();

    }

  }



  public static void displayGDSet(GDSet gd) {

    try {

      for (int j = 0; j < gd.getRowCount(); j++) {

        String[] ss = gd.getRow(j);

        for (int k = 0; k < ss.length; k++) {

          System.out.print(ss[k] + " ");

        }

        System.out.println("");

      }

    }

    catch (GDSetException ex) {

    }

  }



  public static GDSet getGDHead(String tname) {

    GDSet gd = null;

    try {

      String f[] = {

          "field", "type", "operator", "value", "between"};

      gd = new GDSet(tname, f);

    }

    catch (GDSetException ex) {

      ex.printStackTrace();

    }

    return gd;

  }



  /**

   * 把字段名称分隔成字段数组

   * a,b,c->array[0]=a;array[1]=b;array[2]=c

   * @param field

   * @return

   */

  public static String[] Field2Array(String field) {

    StringTokenizer st = new StringTokenizer(field, ",");

    String[] s = new String[st.countTokens()];

    int i = 0;

    while (st.hasMoreTokens()) {

      s[i++] = st.nextToken().trim();

    }

    return s;

  }



  /**

   * 把字段名称分隔成字段数组

   * @param field

   * @return

   */

  public static String[] Field2Array(String field, String delim) {

    StringTokenizer st = new StringTokenizer(field, delim);

    String[] s = new String[st.countTokens()];

    int i = 0;

    while (st.hasMoreTokens()) {

      s[i++] = st.nextToken().trim();

    }

    return s;

  }



  /**

   * 过滤开始为and或or的字符串

   * @param sql

   * @return

   */

  public static String FilStartOper(String sql) {

    int i = 0,found = 0;

    String ret = sql.trim();

    String temp = ret.toLowerCase();

    String[] operAry = {

        "and ", "or "};



    for (i = 0; i < operAry.length; i++) {

      if (temp.startsWith(operAry[i])) {

        found = 1;

        break;

      }

    }



    if(found == 1)

      ret = ret.substring(operAry[i].length());



    return ret;

  }



  /**

   * 字符串是否包括指定操作符

   * @param oper

   * @param sql

   * @return

   */

  public static boolean OperContains(String oper, String sql) {

    String temp = sql.trim().toLowerCase();



    if (temp.startsWith(oper)) {

      return true;

    }



    return false;

  }



  /**

   * 组合sql

   * 如果sql是""或group by,order by子句，则无需加上操作符oper；否则，则加上操作符oper

   * @param sql

   * @param oper

   * @return

   */

  public static String combineSQL(String sql, String oper) {

    boolean EmptyCondition = sql.equals("");

    boolean containStartOrder = Common.OperContains("order", sql);

    boolean containStartGroup = Common.OperContains("group", sql);



    String cond = null;

    if (EmptyCondition || containStartOrder || containStartGroup) {

      cond = " " + sql;

    }

    else {

      cond = " " + oper + " " + sql;



    }

    return cond;

  }



}
