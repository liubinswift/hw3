package com.viewscenes.dao.innerdao;

import java.sql.*;
import com.viewscenes.dao.DAOOperator;
import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.innerdao.SQLGenerator;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.pub.GDSetTool;
import com.viewscenes.pub.*;
import com.viewscenes.dao.cache.DAOCacheComponent;




/**
 * <p>通用对象层 </p>

 * <p>根据传入的通用数据对象实现对单表的增删改查操作</p>

 */

public class DAOComponent

    extends DAOOperator {

  //sql语句类型

  final static int _INSERT = 0;

  final static int _UPDATE = 1;

  final static int _DELETE = 2;

  //主键

  long _key;

  //数据库名称

  short _dbname;

  //数据库名称--ORACLE

  final static short _ORACLE = 1;

  /**
   * 得到当前的数据库名字

   * @return

   */

  private short getDbName() {

    return _dbname;

  }

  /**
   * 设置当前的数据库名字

   * @return

   */

  private void setDbName(short dbname) {

    _dbname = dbname;

  }

  /**
   * 插入（自动生成主键的，支持多行插入），得到一组主键key

   * @param data

   * <pre>

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @param key[OUT] 主键

   * key的个数必须和data的行数要一致

   * return

   * 插入的行数

   */

  public int[] Insert(GDSet data, long[] key) throws DbException {

    return Insert(data, key, true);

  }

  /**
   * 插入(不生成主键，支持多行)

   * @param data

   * <pre>

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * return

   * 插入的行数

   */

  public int[] Insert(GDSet data) throws DbException {

    return Insert(data, null, false);

  }

  /**
   * 插入

   * @param data

   * @param key 返回的主键

   * @param autoCreate 是否让部件生成主键

   * <pre>

   *     true:部件生成主键

   *     false:不需要生成主键

   * </pre>

   * @return

   * @throws DbException

   */

  private int[] Insert(GDSet data, long[] key, boolean autoCreate) throws

      DbException {

    DbComponent db = new DbComponent();

    //得到主键及插入语句

    String[] sql = getSqlArray(data, _INSERT, autoCreate, key);

    //执行

    int[] ret = db.exeBatch(sql);

    //如果成功，刷新缓存

    if (IsDirty(ret)) {

      setDirty(data.getTableName());

    }

    return ret;

  }

  /**
   * 更新（支持多条记录更新，如果返回值失败，则整个事务回滚）

   * @param data

   * <pre>

   * 更新的数据（主键作为更新条件，传入的数据必须包含主键）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @return

   * 更新的行数

   * @throws DbException

   */

  public int[] Update(GDSet data) throws DbException {

    DbComponent db = new DbComponent();

    //得到主键及插入语句

    String[] sql = getSqlArray(data, _UPDATE, false, null);

    //执行

    int[] ret = db.exeBatch(sql);

    //如果成功，刷新缓存

    if (IsDirty(ret)) {

      setDirty(data.getTableName());

    }

    return ret;

  }

  /**
   * 删除

   * 如果返回值失败，则整个事务回滚

   * @param data

   * <pre>

   * 删除的数据（如果传入数据不带主键,则以传入的所有数据为删除条件）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * </pre>

   * @return

   * 删除的行数

   */

  public int[] Delete(GDSet data) throws DbException {

    DbComponent db = new DbComponent();

    //得到删除语句

    String[] sql = getSqlArray(data, _DELETE, false, null);

    //向数据库执行sql语句

    int[] ret = db.exeBatch(sql);

    //如果成功，刷新缓存

    if (IsDirty(ret)) {

      setDirty(data.getTableName());

    }

    return ret;

  }

  /**
   * 单表查询

   * @param field  返回字段名称

   *     格式为："a,b,c"

   * 支持count(*)和*

   * @param condition 查询条件

   * condition的格式为

   * <pre>

   *     多行5列的二维数据集

   *     必须设定TableName

   *     列标题固定为：

   *         field：属性名称

   *         type：属性类型(VARCHAR,NUMBER,DATE）

   *         operator：操作符

   *         value：值(如果是DATE类型，则格式固定为:'YYYY-MM-DD HH24:MI:SS',可以省略后面的'HH24:MI:SS')

   *         between：和前一个查询条件之间的关系，第一条记录默认为and

   * </pre>

   * @return 查询结果

   * 如果查询记录为空，则GDSet的getRowSize为 0

   */

  public GDSet Query(String field, GDSet condition) throws DbException {

    return Query(field, condition, -1, 0);

  }

  /**
   * 单表查询

   * @param field

   *支持count(*)和*

   * @param condition

   * null:表示没有查询条件

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

    //组合查询的sql语句

    SQLGenerator sg = new SQLGenerator();

    String sql = sg.generateQuerySQL(field, condition);

    DbComponent db = new DbComponent();

    //优化分页查询语句

    String s = OptimizePageSQL(sql, start, end);

    //向数据库执行sql语句

    GDSet dataset = db.Query(s);
    if (dataset != null) {
      dataset.setTableName(condition.getTableName());

      //把rs转换成GDSet返回

    }
    return dataset;

  }

//{{ Added by ld 2003-12-10
  public GDSet Query(String field, String singlesql, String tablename,
                     int start, int end) throws DbException {
    if (tablename == null || tablename.equalsIgnoreCase("")) {
      throw new DbException("输入tablename为空");
    }
    if (singlesql == null || singlesql.equalsIgnoreCase("")) {
      throw new DbException("输入singlesql为空");
    }

    DbComponent db = new DbComponent();

    //优化分页查询语句
    String s = OptimizePageSQL(singlesql, start, end);
    System.out.println(s);
    //向数据库执行sql语句
    GDSet dataset = db.Query(s);
    if (dataset != null) {
      dataset.setTableName(tablename);

      //把rs转换成GDSet返回
    }
    return dataset;
  }

//}}

  /**
   * 分页的查询语句优化

   * 如果数据库是ORACLE，则用rownum来进行分页查询

   * @param sql

   * @param start

   * @param end

   * @return

   */

  private String OptimizePageSQL(String sql, int start, int end) {

    String s = null;

    String name = XmlReader.getAttrValue("dbconfig", "para", "optimize");

    //如果是oracle,则rownum来定位

    if (start != -1 && name.equals("ORACLE")) {

      setDbName(_ORACLE);

      s = "select * from (select rownum ora_nc,tab.* from (" + sql +

          ")tab) where ora_nc between " + start + " and " + end;

    }
    else {
      s = sql;
    }

    return s;

  }

  /**
   * 是否使用了ORACLE的ROWNUM进行优化

   * @return

   */

  private boolean UseOraRowNum() {

    if (getDbName() == _ORACLE) {

      return true;

    }

    return false;

  }

  /**
   * 是否查询所有的值

   * @param start

   * @return

   * true:查所有的值

   * false:按range查询

   */

  private boolean IsQueryAll(int start) {

    if (start == -1) {

      return true;

    }

    return false;

  }

  /**
   * 把rs转成GDSet返回,GDSet的range在start和end之间

   * @return

   */

  private GDSet getGDSet(ResultSet rs, GDSet condition, int start, int end) throws
      DbException {

    GDSet set = null;

    try {

      set = new GDSet(condition.getTableName());

      //如果查询全集或者使用了oracle的rownum,则返回全集

      if (IsQueryAll(start) || UseOraRowNum()) {

        try {
            GDSetTool.addResultSet(set, rs);
        } catch (GDSetException ex1) {
        }

      }

      else { //否则，返回指定范围的集合

        GDSetTool.addResultSet(set, rs, start, end);

      }

      //如果ora_nc存在，则去掉ora_nc该列

      removeNcColumn(set);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOComponent getGDSet: GDSet 错误", ex);

    }

    return set;

  }

  /**
   * 去掉ora_nc该列(ora_nc是oracle中rownum的别名)

   * @param set

   * @throws DbException

   */

  private void removeNcColumn(GDSet set) throws DbException {

    try {

      //如果返回值包括rownum的值，则去掉该列

      if (set.getColumnIndex("ora_nc") != -1) {

        set.removeColumn("ora_nc");

      }

    }

    catch (GDSetException ex) {

      throw new DbException("DAOComponent removeOraRowNum: GDSet 错误", ex);

    }

  }

  /**
   * insert,update或delete时把缓存的标示置脏

   * @param tab

   */

  private void setDirty(String tab) {

    DAOCacheComponent dao = new DAOCacheComponent();

    dao.setDirty(tab);

  }

  /**
   * 根据data得到一组sql语句

   * @param data

   * @param keyCreate 主键是否自动生成

   * true:自动生成

   * false:非自动生成

   * @param type sql语句类型

   * @return

   */

  private String[] getSqlArray(GDSet data, int type, boolean keyCreate,
                               long[] key) throws

      DbException {

    String s[] = new String[data.getRowCount()];

    try {

      for (int i = 0; i < data.getRowCount(); i++) {

        //得到sql

        GDSet sub = data.getSubGDSet(i, i + 1);

        s[i] = getSql(sub, type, keyCreate);

        //如果是插入语句并且是主键自动生成，则记录主键值

        if (type == _INSERT && key != null) {

          key[i] = _key;

        }

      }

    }

    catch (GDSetException ex) {

      throw new DbException("DAOComponent getSqlArray:GDSet错误" + ex);

    }

    return s;

  }

  /**
   * 根据单条记录的data得到一条sql

   * @param data

   * @param type 语句类型（_INSERT，_UPDATE，_DELETE)

   * @param keyCreate

   * 只对type = _INSERT有效，表示是否需要主动产生主键

   * @return

   * @throws DbException

   */

  private String getSql(GDSet data, int type, boolean keyCreate) throws
      DbException {

    String sql = null;

    SQLGenerator sg = new SQLGenerator();

    if (type == _INSERT) {

      sql = sg.generateInsertSQL(data, keyCreate);

      _key = Long.parseLong(sg.getKey());

    }

    if (type == _UPDATE) {

      sql = sg.generateUpdateSQL(data);

    }

    if (type == _DELETE) {

      sql = sg.generateDeleteSQL(data);

    }

    return sql;

  }

  /**
   * 多个条件删除

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

   * 删除的行数

   */

  public int[] DeleteX(GDSet condition) throws DbException {

    //组合删除的sql语句

    SQLGenerator sg = new SQLGenerator();

    String[] sql = new String[1];

    sql[0] = sg.generateDeleteXSQL(condition);

    //向数据库执行sql语句

    DbComponent db = new DbComponent();

    int[] ret = db.exeBatch(sql);

    //如果操作成功，刷新缓存

    if (IsDirty(ret)) {

      setDirty(condition.getTableName());

    }

    return ret;

  }

  /**
   * 是否需要设置脏标志

   * @param ret

   * @return

   * true:需要设置脏标志

   * false:不需要设置脏标志

   */

  private boolean IsDirty(int[] ret) {

    for (int i = 0; i < ret.length; i++) {

      if (ret[i] > 0) {

        return true;

      }

    }

    return false;

  }

  /**
   * 根据条件更新（支持多条记录更新，如果返回值失败，则整个事务回滚）

   * @param data（包括更新条件）

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

    DbComponent db = new DbComponent();

    SQLGenerator sg = new SQLGenerator();

    String sql[] = new String[data.getRowCount()];

    int[] ret = null;

    try {

      for (int i = 0; i < data.getRowCount(); i++) {

        //得到sql

        GDSet sub = data.getSubGDSet(i, i + 1);
        //change by xyw for multi line update
        //sql[i] = sg.generateUpdateXSQL(data, con);
        sql[i] = sg.generateUpdateXSQL(sub, con);

      }

      //执行

      ret = db.exeBatch(sql);

      //如果成功，刷新缓存

      if (IsDirty(ret)) {

        setDirty(data.getTableName());

      }

    }

    catch (GDSetException ex) {

      throw new DbException("DAOComponent UpdateX:GDSet错误" + ex);

    }

    return ret;

  }

  /**
   * 根据条件更新

   * @param content 更新的内容（不包括更新条件）

   * @param con 更新的条件

   * @return

   * @throws DbException

   */

  public int[] UpdatebyCon(GDSet content, GDSet con) throws DbException {

    DbComponent db = new DbComponent();

    SQLGenerator sg = new SQLGenerator();

    String sql[] = new String[1];

    int[] ret = null;

    //得到sql

    sql[0] = sg.generateUpdatebyConSQL(content, con);

    //执行

    ret = db.exeBatch(sql);

    //如果成功，刷新缓存

    if (IsDirty(ret)) {

      setDirty(content.getTableName());

    }

    return ret;

  }

}
