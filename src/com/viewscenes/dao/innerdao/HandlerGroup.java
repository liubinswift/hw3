package com.viewscenes.dao.innerdao;

import com.viewscenes.dao.database.DbException;



/**
 * group by操作符的处理器

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class HandlerGroup

    implements Handler {

  /**
   * 返回sql子句

   * @param con Condition对象

   * @param sql SqlObject对象

   * @throws DbException

   */

  public void execute(Condition con, SqlObject sql) throws DbException {

    String oper = con.getOper();

    String col = con.getColumn();

    String condition = " " + oper + " " + col;

    sql.setGroup_sql(condition);

  }

}
