package com.viewscenes.dao;

import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbException;



/**
 * 对外提供数据操作的类

 */

public class DAOOperator {

  /**
   * 插入（自动生成主键的，支持多行插入），得到一组主键key

   * @param data

   * <pre>

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @param key[OUT] 主键

   * key的个数必须和data的行数要一致

   */

  public int[] Insert(GDSet data, long[] key) throws DbException {

    return null;

  }

  /**
   * 插入(不生成主键，支持多行)

   * @param data

   * <pre>

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   */

  public int[] Insert(GDSet data) throws DbException {

    return null;

  }

  /**
   * 更新（目前支持单条记录更新，如果返回值失败，则整个事务回滚）

   * @param data

   * <pre>

   * 更新的数据（主键作为更新条件，传入的数据必须包含主键）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @return

   * @throws DbException

   */

  public int[] Update(GDSet data) throws DbException {

    return null;

  }

  /**
   * 删除（目前支持单条记录更新）

   * 如果返回值失败，则整个事务回滚

   * @param data

   * <pre>

   * 删除的数据（如果传入数据不带主键,则以传入的所有数据为删除条件）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @return

   */

  public int[] Delete(GDSet data) throws DbException {

    return null;

  }

  /**
   * 查询(表名可以为物理表，也可以为逻辑表）

   * @param field

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

   * @return String

   */

  public GDSet Query(String field, GDSet condition) throws DbException {

    return null;

  }

  /**
   * 翻页查询(表名可以为物理表，也可以为逻辑表）

   * @param field

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

   *  @param start 开始行（第一行由0行开始）

   *    -1表示全部查询

   *  @param end 结束行

   * 如查前10行：start =0,end =10

   * @return String

   */

  public GDSet Query(String field, GDSet condition, int start, int end) throws
      DbException {

    return null;

  }

//{{ Added by ld 2002-12-10
  public GDSet Query(String field, String singlesql, String tablename,
                     int start, int end) throws DbException {
    return null;
  }

//}}

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

    return null;

  }

  /**
   * 根据条件更新（支持多条记录更新，如果返回值失败，则整个事务回滚）

   * @param data

   * <pre>

   * 更新的数据

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @param con

   * 表示更新的条件

   * 只需要设定column的属性即可

   * @return

   * 更新的行数

   * @throws DbException

   */

  public int[] UpdateX(GDSet data, GDSet con) throws DbException {

    return null;

  }

  /**
   *

   * @param content

   * @param con

   * @return

   * @throws DbException

   */

  public int[] UpdatebyCon(GDSet content, GDSet con) throws DbException {

    return null;

  }

}
