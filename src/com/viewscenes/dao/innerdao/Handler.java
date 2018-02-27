package com.viewscenes.dao.innerdao;

import com.viewscenes.dao.database.DbException;



/**
 * 操作符处理器接口

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public interface Handler {

  /**
   * 根据不同的操作符组合sql表达式

   * @param con 条件对象

   * @param sql [out]sql对象

   * @throws DbException

   */

  public void execute(Condition con, SqlObject sql) throws DbException;

}
