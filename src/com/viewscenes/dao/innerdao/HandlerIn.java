package com.viewscenes.dao.innerdao;

import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetTool;
import com.viewscenes.pub.GDSetException;



/**
 * In操作符的处理器

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class HandlerIn
    implements Handler {

  /**
   * 返回in子句

   * @param con Condition对象

   * @param sql SqlObject对象

   * @throws DbException

   */

  public void execute(Condition con, SqlObject sql) throws DbException {

    //add by xyw for diffrent field name match such as head_code:code
    String field = con.getColumn();
    int start = field.indexOf(":");
    String infield = field;
    if (start > 0) {
      infield = field.substring(start + 1);
      field = field.substring(0, start);
    }

    String ret = " " + con.getBetween() + " " + field + " " + con.getOper();

    ret += handleSubString(con.getValue(), infield);

    sql.setSql(ret);

  }

  /**
   * 返回In后跟的value的字串

   * @param value 值，表示一个GDSet的字符串，符合DAOCondition的parseString的格式

   * @param field 字段名

   * @return sql语句

   * @throws DbException

   */

  protected String handleSubString(String value, String field) throws
      DbException {

    String con = null;

    try {

      //由表示条件的GDSet的字符串转成GDSet的对象

      GDSet dao_con = GDSetTool.parseStringToGDSet(value);

      //由SQL生成器把in的子句生成

      SQLGenerator gener = new SQLGenerator();

      con = " (" + gener.generateQuerySQL(getField(field), dao_con) + ") ";

    }
    catch (GDSetException ex) {

      throw new DbException("InHandler handleSubString:GDSet错误", ex);

    }

    return con;

  }

  /**
   * 如果字段属性包含表的别名，则把别名去掉

   * ch.ch_id->ch_id

   * @param field

   * @return

   */

  private String getField(String field)

  {

    int start = 0;

    if ( (start = field.indexOf(".")) != -1) {

      return field.substring(start + 1);
    }

    return field;

  }

}
