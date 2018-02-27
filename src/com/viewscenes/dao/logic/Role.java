package com.viewscenes.dao.logic;

import com.viewscenes.dao.database.*;
import com.viewscenes.dao.innerdao.*;
import com.viewscenes.pub.*;

public class Role
    extends DAOComponent {

  /**
   *  删除

   * @param data

   * @return

   * @throws DbException

   */

  public int[] Delete(GDSet data) throws DbException {

    String[] sql = getSql(data);

    DbComponent db = new DbComponent();

    return db.exeBatch(sql);

  }

  /**
   * 得到连串sql语句

   * @param data

   * @return

   * @throws DbException

   */

  protected String[] getSql(GDSet data) throws DbException {

    String[] sql = new String[1];

    sql[0] = commonDelete(data);

    return sql;

  }

  /**
   * 通用删除

   * @param data

   * @throws DbException

   */

  protected String commonDelete(GDSet data) throws DbException {

    try {

      GDSet sub = data.getSubGDSet(0, data.getRowCount());

      sub.setTableName("SEC_ROLE_TAB");

      SQLGenerator sql = new SQLGenerator();

      return sql.generateDeleteSQL(sub);

    }
    catch (GDSetException ex) {

      throw new DbException("Role commonDelete:GDSet错误", ex);

    }

  }

  /**
   * 删除前操作

   * @param data

   * @throws DbException

   */

  protected String beforeDelete(GDSet data) throws DbException {

    return null;

  }

  /**
   * 删除后操作

   * @param data

   */

  protected String afterDelete(GDSet data) throws DbException {

    return null;

  }

}
