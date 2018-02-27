package com.viewscenes.dao.innerdao;

import com.viewscenes.dao.database.DbException;



/**
 * 普通操作符的处理器(如=,<>,>,<,>=,<=,<>,!=,like)

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class HandlerCommon

    implements Handler {

  /**
   * 返回sql子句

   * @param con Condition对象

   * @param sql SqlObject对象

   * @throws DbException

   */

  public void execute(Condition con, SqlObject sql) throws DbException {

    String condition = " " + con.getBetween() + " " + con.getColumn() + " " +
	con.getOper() + " ";

    String type = con.getType();

    String value = con.getValue();

    if (type.equals("VARCHAR")) {

      condition += " '" + value + "'" + " ";

    }
    else if (type.equals("DATE")) {

      condition += "TO_DATE('" + value + "','YYYY-MM-DD HH24:MI:SS') ";

    }
    else {

      condition += value + " ";

    }

    sql.setSql(condition);

  }

}
