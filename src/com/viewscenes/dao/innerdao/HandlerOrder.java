package com.viewscenes.dao.innerdao;

import com.viewscenes.dao.database.DbException;



/**
 * order by操作符处理器

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class HandlerOrder

    implements Handler {

  /**
   * 返回oder by子句

   * @param con Condition对象

   * @param sql SqlObject对象

   * @throws DbException

   */

  public void execute(Condition con, SqlObject sql) throws DbException {

    String order = con.getColumn() + " " + con.getValue();

    //设置sql对象的order子句

    sql.setOrder_sql(order);

  }

}
