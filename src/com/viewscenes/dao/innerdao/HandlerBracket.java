package com.viewscenes.dao.innerdao;

import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetTool;
import com.viewscenes.pub.GDSetException;


/**
 * 括号操作符处理器

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class HandlerBracket
    implements Handler {

  /**
   * 返回()子句

   * @param con 条件对象

   * @param sql Sql对象

   * @throws DbException

   */

  public void execute(Condition con, SqlObject sql) throws DbException {

    String ret = " " + con.getBetween() + handleSubString(con.getValue());

    sql.setSql(ret);

  }

  /**
   * 返回()的子句

   * @param value 值，表示一个GDSet的字符串，符合DAOCondition的parseString的格式

   * @return sql语句

   * @throws DbException

   */

  protected String handleSubString(String value) throws DbException {

    String con = null;

    try {

      //由GDSet的字符串转成GDSet的对象

      GDSet dao_con = GDSetTool.parseStringToGDSet(value);

      //把表示条件的GDSet通过条件过滤器转成条件表达式

      ConBaseFilter filter = FilterFactory.create("", dao_con);

      con = " ( " + filter.getCondition() + ")";

      ;

    }
    catch (GDSetException ex) {

      throw new DbException("bracketHandler handleSubString:GDSet错误", ex);

    }

        return con;

  }

}
