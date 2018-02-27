package com.viewscenes.dao;

import com.viewscenes.dao.cache.DAOCacheComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.dao.innerdao.DAOComponent;
import com.viewscenes.dao.logic.LogicFactory;
import com.viewscenes.util.LogTool;
import com.viewscenes.pub.GDSetException;



/**
 * <p>Title: </p>
 * <p>代理，决定调用哪种部件进行操作</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DaoProxy

    extends DAOOperator {

  private DAOCacheComponent cache = new DAOCacheComponent(); //缓存

  /**
   * 插入（自动生成主键的，支持多行插入），得到一组主键key
   * 调用逻辑表部件插入
   */

  public int[] Insert(GDSet data, long[] key) throws DbException {

    checkData(data, "insert");

    //assert (key == null || key.length == data.getRowCount()):"DaoProxy Insert:key的个数和data的个数不一致";

    DAOComponent dao = LogicFactory.create(data.getTableName());

    return dao.Insert(data, key);

  }

  /**
   * 插入(不生成主键，支持多行)
   * 调用数据库部件插入
   */

  public int[] Insert(GDSet data) throws DbException {

    checkData(data, "insert");

    DAOComponent dao = LogicFactory.create(data.getTableName());

    return dao.Insert(data);

  }

  /**
   * 更新
   * 调用数据库部件更新
   */

  public int[] Update(GDSet data) throws DbException {

    checkData(data, "update");

    DAOComponent dao = LogicFactory.create(data.getTableName());

    return dao.Update(data);

  }

  /**
   * 根据条件更新
   * 调用数据库部件更新
   */

  public int[] UpdateX(GDSet data, GDSet con) throws DbException {

    checkData(data, "UpdateX");

    //assert (con != null):"UpdateX:con为空！";

    DAOComponent dao = LogicFactory.create(data.getTableName());

    return dao.UpdateX(data, con);

  }

  /**
   * 根据条件更新
   * @param content(必须包括表名）
   * @param con（可以不包括表名）
   * @return
   * @throws DbException
   */

  public int[] UpdatebyCon(GDSet content, GDSet con) throws DbException {

    checkData(content, "UpdatebyCon");

    //assert (con.getRowCount() > 0):"DaoProxy UpdatebyCon:data没有记录！";

    DAOComponent dao = LogicFactory.create(content.getTableName());

    return dao.UpdatebyCon(content, con);

  }

  /**
   * 删除
   * 调用数据库部件删除
   * @param data
   * @return
   * @throws DbException
   */

  public int[] Delete(GDSet data) throws DbException {

    checkData(data, "delete");

    DAOComponent dao = LogicFactory.create(data.getTableName());

    return dao.Delete(data);

  }

  /**
   * 查询
   * 决定用缓存查询或是直接查询数据库
   * @param field 返回字段名
   * @param condition 查询条件
   * @return
   * @throws DbException
   */

  public GDSet Query(String field, GDSet condition) throws DbException {

    checkGDSetData(condition, "Query");

    if (UseCacheStragy(field, condition)) {

      LogTool.debug("Use Cache");

      return cache.Query(field, condition);

    }

    DAOComponent dao = LogicFactory.create(condition.getTableName());

    return dao.Query(field, condition);

  }

  /**
   * 翻页查询
   * 决定用缓存查询或是直接查询数据库
   * @param field 返回字段名
   * @param condition 查询条件
   * @param start 开始行数（0为第一行）
   * @param end 结束行数
   * @return
   * @throws DbException
   */

  public GDSet Query(String field, GDSet condition, int start, int end) throws

      DbException {

    checkGDSetData(condition, "Query");

    //assert (start >= -1 && end >= 0 && start < end):"DaoProxy Query: start和end必须要大于0,start必须<end";

    //如果只有一个查询条件并且表在缓存中，则从缓存中取

    if (UseCacheStragy(field, condition)) {

      LogTool.debug("Use Cache");

      return cache.Query(field, condition, start, end);

    }

    DAOComponent dao = LogicFactory.create(condition.getTableName());

    return dao.Query(field, condition, start, end);

  }

//{{ Added by ld 2003-12-10
  public GDSet Query(String field, String singlesql, String tablename,
                     int start, int end) throws DbException {

    DAOComponent dao = LogicFactory.create(tablename);

    return dao.Query(field, singlesql, tablename, start, end);
  }

//}}

  /**
   * 是否使用缓存
   * 使用缓存策略：表在缓存,配置文件指定使用缓存，只有一个查询条件，并且操作符是=
   * @return true:使用缓存
   * false:不使用缓存
   */

  private boolean UseCacheStragy(String field, GDSet condition) throws

      DbException {

    String tab = condition.getTableName();

    String para = XmlReader.getAttrValue("dbconfig", "para", "usecache");

    final boolean xmlUseCache = para.equals("1");

    final boolean conditionRange = condition.getRowCount() <= 1;

    final boolean funDefine = !field.equals("count(*)");

    final boolean hitCache = cache.hitCache(tab);

    final boolean EqualsContains = IsEqualsContains(condition);

    //如果只有一个查询条件并且表在缓存中，则从缓存中取

    if (xmlUseCache && conditionRange && funDefine && hitCache &&
        EqualsContains) {

      return true;

    }

    return false;

  }

  /**
   * 删除（目前支持单条记录更新）
   * 如果返回值失败，则整个事务回滚
   * @param condition
   * <pre>
   * @param condition
   * condition的格式为
   *     多行5列的二维数据集
   *     必须设定TableName
   *     列标题固定为：
   *         field：属性名称
   *         type：属性类型(VARCHAR,NUMBER,DATE）
   *         operator：操作符
   *         value：值(如果是DATE类型，则格式固定为:'YYYY-MM-DD HH24:MI:SS',可以省略后面的'HH24:MI:SS')
   *         between：和前一个查询条件之间的关系，第一条记录默认为and
   * </pre>
   * @return
   */

  public int[] DeleteX(GDSet condition) throws DbException {

    checkGDSetData(condition, "DeleteX");

    DAOComponent dao = LogicFactory.create(condition.getTableName());

    return dao.DeleteX(condition);

  }

  /**
   * 条件中是否=操作符
   * @param condition
   * @return
   * true:条件包含=
   * false:条件不包含=
   */

  private boolean IsEqualsContains(GDSet condition) throws DbException {

    try {

      for (int i = 0; i < condition.getRowCount(); i++) {

        String oper = condition.getString(i, "operator");

        if (oper.equals(Operator._EQUAL)) {

          return true;

        }

      }

      return false;

    }

    catch (GDSetException ex) {

      throw new DbException("DaoProxy IsEqualsContains: GDSet错误 ", ex);

    }

  }

  /**
   * 检查data的正确性
   * data不能为null,表名不为null,且行数不能为0，
   * @param method 方法名
   * @param data
   * @return
   */

  private void checkData(GDSet data, String method) {

    String cls = "DAOProxy ";

    checkGDSetData(data, method);

    //assert (data.getRowCount() > 0):method + ":data没有记录！";

  }

  private void checkGDSetData(GDSet data, String method) {

    String cls = "DAOProxy ";

    //assert (data != null):method + ":data没有记录！";

    //assert (data.getTableName() != null && !data.getTableName().equals("")):method + ":表名不能为空！";

  }

}
