package com.viewscenes.dao;

import com.viewscenes.dao.database.DbComponent;



/**
 * 客户端调用数据库工厂

 */

public class DaoFactory {

  public static final short DAO_OBJECT = 0;

  public static final short DB_OBJECT = 1;

  /**
   * 得到数据库实例

   * DAO_OBJECT:产生数据访问接口，返回DAOComponent

   * DB_OBJECT:产生数据库底层接口，返回DbComponent

   * @return

   */

  public synchronized static Object create(short name) {
    System.gc();
    if (name == DAO_OBJECT) {
      return new DaoProxy();
    }
    else if (name == DB_OBJECT) {
      return new DbComponent();
    }
    return null;
  }
}
