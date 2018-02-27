package com.viewscenes.dao.innerdao;



import com.viewscenes.pub.*;

import com.viewscenes.dao.database.*;

import org.jdom.*;

import com.viewscenes.dao.*;



/**

 * sql语句生成器

 *

 * 根据传入的通用数据对象生成sql语句

 */

public class SQLGenerator {



  final static String _alias = "tab.";

  private String _key = "-1";



  /**

   * 得到单条插入语句

   * @param data 插入的一行数据(包含主键）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * @param keyCreate 是否让部件生成主键

   *     true:部件生成主键

   *     false:不需要生成主键

   * @return String

   */

  public String generateInsertSQL(GDSet data, boolean keyCreate) throws DbException {

    String field = "";

    String value = "";



    String sql = null;

    String key = "";

    try {

      //得到主键值

      String tab = data.getTableName();

      if (keyCreate == true) {

        key = Common.getKeyName(tab);



      }



      for (int i = 0; i < data.getColumnCount(); i++) {

        //如果data含有主键,并且主键只是""

        if (key.equals(data.getColumnName(i)) && data.getString(0,i).equals("")) {

          continue;

        }



        field += data.getColumnName(i) + ",";

        value += "'" + data.getString(0, i) + "',";

      }



      //如果主键需要生成

      if (keyCreate == true) {

        if(data.getColumnIndex(key) == -1 || (data.getColumnIndex(key)!= -1 && data.getString(0,key).equals("")))

        {

          DbComponent db = new DbComponent(null);

          String keyValue = _key = db.getKey(tab);



          field += key + ",";

          value += keyValue + ",";

        }

      }



      sql = "INSERT INTO " + data.getTableName() + "(" +

          field.substring(0, field.length() - 1) + ") VALUES(" +

          value.substring(0, value.length() - 1) + ")";

    }

    catch (GDSetException ex) {

      throw new DbException("SQLGenerator generateInsertSQL:", ex);

    }



    return sql;

  }



  /**

   * 得到单条更新语句

   * @param data 更新的数据（主键作为更新条件，传入的数据必须包含主键）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * @return String

   */

  public String generateUpdateSQL(GDSet data) throws DbException {

    String field = "";

    String value = "";

    String prefix = "";

    String suffix = "";

    String sql = null;

    boolean hasKey = false;

    String key = "";

    try {

      for (int i = 0; i < data.getColumnCount(); i++) {

        field = data.getColumnName(i);

        value = data.getString(0, i);



        //如果该值是主键，则把它提取到suffix中作为更新条件

        key = Common.getKeyName(data.getTableName());

        if (key.equals(field)) {

          suffix = field + "='" + value + "'";

          hasKey = true; //存在主键

        }

        else {

          prefix += field + "='" + value + "',";

        }

      }



      if (!hasKey) {

        throw new DbException("data中找不到主键,表" + data.getTableName() + "的主键为" + key);

      }



      sql = "UPDATE " + data.getTableName() + " SET " +

          prefix.substring(0, prefix.length() - 1) + "  WHERE " + suffix;

    }

    catch (GDSetException ex) {

      throw new DbException("通用数据集合错误", ex);

    }



    return sql;



  }



  /**

   * field是否在GDSet的列标题中

   * @param gd

   * @param field

   * @return

   */

  private boolean GDColContain(GDSet gd, String field) {

    String[] col = gd.getAllColumnName();

    for (int i = 0; i < col.length; i++) {

      if (col[i].equals(field)) {

        return true;

      }

    }

    return false;

  }



  /**

   * 得到单条删除语句

   * @param data 删除的数据（如果传入数据不带主键,则以传入的所有数据为删除条件）

   *     data不能为null或空

   *     必须保证data.getRow(0)和data.getTableName()不为空

   * @return String

   */

  public String generateDeleteSQL(GDSet data) throws DbException {

    String field = "";

    String value = "";

    String s = "";

    String sql = null;

    String key = null;

    try {

      for (int i = 0; i < data.getColumnCount(); i++) {

        field = data.getColumnName(i);



        //如果传入数据不带主键,则以传入的所有数据为删除条件

        value = data.getString(0, i);

        s += " and " + field + "='" + value + "' ";



        //如果该值是主键，则把它提取到suffix中作为删除条件

        key = Common.getKeyName(data.getTableName());

        if (key.equalsIgnoreCase(field)) {

          value = data.getString(0, i);

          s = " and " + field + "='" + value + "'";

          break;

        }

      }



      sql = "DELETE " + data.getTableName() + "  WHERE 1=1 " + s;

    }

    catch (GDSetException ex) {

      throw new DbException("通用数据集合错误", ex);

    }



    return sql;



  }



  /**

   * 得到单条查询语句

   * @param field (*:表示返回全部字段)

   * @param condition

   * 没有查询条件则只设表名

   * condition的格式为

   *     多行5列的二维数据集

   *     列标题固定为：

   *         field：属性名称

   *         type：属性类型(VARCHAR,NUMBER,DATE）

   *         operator：操作符

   *         value：值(如果是DATE类型，则格式固定为:'YYYY-MM-DD HH24:MI:SS',可以省略后面的'HH24:MI:SS')

   *         between：和前一个查询条件之间的关系，第一条记录默认为and

   * @return String

   */

  public String generateQuerySQL(String field, GDSet condition) throws DbException {

    ConBaseFilter filter = FilterFactory.create(field, condition);

    String cond = getCondition(filter);

    String con = "SELECT " + filter.getField() + " FROM " + filter.getTabName() + cond;



    return con;

  }



  private String getCondition(ConBaseFilter filter) {

    String con = filter.getCondition();

    return Common.combineSQL(con, "WHERE");

  }



  public String getKey() {

    return _key;

  }



  /**

   * 得到删除语句

   * @param data

   * <pre>

   * data的格式为

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

  public String generateDeleteXSQL(GDSet data) throws DbException {

    ConBaseFilter filter = FilterFactory.create("", data);

    String cond = getCondition(filter);

    String con = "DELETE  FROM " + filter.getTabName() + cond;



    return con;

  }



  /**

   * 得到单条更新语句（根据条件而不是主键更新,更新条件和更新内容的数据均在data中）

   * @param data 更新的内容

   * @param con 更新的条件(只规定字段)

   * @return 更新的语句

   * @throws DbException

   */

  public String generateUpdateXSQL(GDSet data, GDSet con) throws DbException {

    String field = "";

    String value = "";

    String prefix = "";

    String suffix = "";

    String sql = null;

    String key = "";

    try {

      for (int i = 0; i < data.getColumnCount(); i++) {

        field = data.getColumnName(i);

        value = data.getString(0, i);



        //如果该字段是更新条件

        if (GDColContain(con, field)) {

          suffix += field + "='" + value + "' and ";

        }

        else {

          prefix += field + "='" + value + "',";

        }

      }



      sql = "UPDATE " + data.getTableName() + " SET " +

          prefix.substring(0, prefix.length() - 1) + "  WHERE " +

          suffix.substring(0, suffix.length() - 4);

    }

    catch (GDSetException ex) {

      throw new DbException("通用数据集合错误", ex);

    }



    return sql;



  }



  /**

   * 根据条件更新

   * @param content 更新内容

   * @param condition 更新条件

   * @return

   * @throws DbException

   */

  public String generateUpdatebyConSQL(GDSet content, GDSet condition) throws DbException {

    ConBaseFilter filter = FilterFactory.create("", condition);

    StringBuffer prefix = new StringBuffer();

    String sql = "";

    try {

      for (int i = 0; i < content.getColumnCount(); i++) {

        prefix.append(content.getColumnName(i));

        prefix.append("='").append(content.getString(0, i)).append("',");

      }



      sql = "UPDATE " + content.getTableName() + " SET " +

          prefix.toString().substring(0, prefix.length() - 1) + getCondition(filter);

    }

    catch (GDSetException ex) {

      throw new DbException("通用数据集合错误", ex);

    }



    return sql;

  }



}

