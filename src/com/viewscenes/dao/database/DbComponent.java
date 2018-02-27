package com.viewscenes.dao.database;



import java.sql.*;




import com.viewscenes.dao.pool.*;

import com.viewscenes.pub.*;

import com.viewscenes.util.*;
import com.viewscenes.dao.innerdao.SQLGenerator;


/**
 * 数据库底层接口
 *
 * 完成数据库的打开，关闭，单表的数据查询，增删改，主键生成的功能
 */

public class DbComponent {

    final static String method = "DBComponent:";

    final static int _INSERT = 0;

    final static int _UPDATE = 1;

    //主键

  static long _key;



    static String dbName;

    private String DB_DRIVER ;

    private String DB_URL;

    private String DB_USER ;

    private String DB_PASSWORD ;

    public DbComponent(String dbName){
      this.dbName = dbName;
    }
    public DbComponent(){

    }
    /**
     * 执行sql(包括insert,update,delete)
     * @param sql
     * @return 成功与否
     * @throws DbException
     */
    public static boolean exeUpdate(String sql) throws DbException {
        String msg = method + "exeUpdate  ";

        //assert (sql != null && sql.compareTo("") != 0):msg + "sql为空";
        Connection con = null;

        Statement st = null;

        try {

            con = getDB();

            st = con.createStatement();

            LogTool.debug(sql);

            if (st.executeUpdate(sql) > 0) {

                return true;

            } else {

                return false;

            }

        } catch (SQLException ex) {

            try{

                con.rollback();

            }catch(Exception ex1){

                ex1.printStackTrace();

            }

            System.out.println(sql);

            throw new DbException(msg + sql, ex);

        } finally {

            closeDB(con, st);

        }
    }

    public static boolean exeUpdate(String sql,String dbName) throws DbException {
        String msg = method + "exeUpdate  ";

        //assert (sql != null && sql.compareTo("") != 0):msg + "sql为空";
        Connection con = null;

        Statement st = null;

        try {

            con = getDB(dbName);

            st = con.createStatement();

            LogTool.debug(sql);

            if (st.executeUpdate(sql) > 0) {

                return true;

            } else {

                return false;

            }

        } catch (SQLException ex) {

            try{

                con.rollback();

            }catch(Exception ex1){

                ex1.printStackTrace();

            }

            System.out.println(sql);

            throw new DbException(msg + sql, ex);

        } finally {

            closeDB(con, st);

        }
    }




    public static GDSet Query(String sql) throws DbException {

        String msg = method + "Query  ";

        Connection con = null;

        Statement st = null;

        ResultSet rs = null;


        try {

          con = getDB();

          st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_READ_ONLY);
    
          LogTool.debug(sql);
          rs = st.executeQuery(sql);

          GDSet gd = new GDSet("");
          GDSetTool.addResultSet(gd, rs);

          return gd;

        } catch (GDSetException ex) {
            throw new DbException(msg + "GDSet的addResultSet错误", ex);
        }
          catch (SQLException ex) {
            throw new DbException(msg + sql, ex);
        } finally {
            closeDB(con, st, rs);
            
        }
    }

    public static GDSet Query(String sql,String dbName) throws DbException {

        String msg = method + "Query  ";

        Connection con = null;

        Statement st = null;

        ResultSet rs = null;


        try {

          con = getDB(dbName);

          st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_READ_ONLY);

          LogTool.debug(sql);
          rs = st.executeQuery(sql);

          GDSet gd = new GDSet("");
          GDSetTool.addResultSet(gd, rs);

          return gd;

        } catch (GDSetException ex) {
            throw new DbException(msg + "GDSet的addResultSet错误", ex);
        }
          catch (SQLException ex) {
            throw new DbException(msg + sql, ex);
        } finally {
            closeDB(con, st, rs);
        }
    }
    public static GDSet Query(String sql,int ReturnType) throws DbException {
    	
    	String msg = method + "Query  ";
    	
    	Connection con = null;
    	
    	Statement st = null;
    	
    	ResultSet rs = null;
    	
    	
    	try {
    		
    		con = getDB();
    		
    		st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
    				ResultSet.CONCUR_READ_ONLY);
    		
    		LogTool.debug(sql);
    		rs = st.executeQuery(sql);
    		
    		GDSet gd = new GDSet("");
    		GDSetTool.addResultSet(gd, rs, ReturnType);
    		
    		return gd;
    		
    	} catch (GDSetException ex) {
    		throw new DbException(msg + "GDSet的addResultSet错误", ex);
    	}
    	catch (SQLException ex) {
    		throw new DbException(msg + sql, ex);
    	} finally {
    		closeDB(con, st, rs);
    	}
    }
    
    public static GDSet Query(String sql,String dbName,int ReturnType) throws DbException {
    	
    	String msg = method + "Query  ";
    	
    	Connection con = null;
    	
    	Statement st = null;
    	
    	ResultSet rs = null;
    	
    	
    	try {
    		
    		con = getDB(dbName);
    		
    		st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
    				ResultSet.CONCUR_READ_ONLY);
    		
    		LogTool.debug(sql);
    		rs = st.executeQuery(sql);
    		
    		GDSet gd = new GDSet("");
    		GDSetTool.addResultSet(gd, rs, ReturnType);
    		
    		return gd;
    		
    	} catch (GDSetException ex) {
    		throw new DbException(msg + "GDSet的addResultSet错误", ex);
    	}
    	catch (SQLException ex) {
    		throw new DbException(msg + sql, ex);
    	} finally {
    		closeDB(con, st, rs);
    	}
    }




    /**

     * 执行查询（支持absolute)

     * @param sql

     * @return ResultSet

     */

    public ResultSet exeQuery(String sql) throws DbException {

        String msg = method + "exeQuery  ";

        //assert (sql != null && sql.compareTo("") != 0):msg + "sql为空";

        Connection con = null;

        Statement st = null;

        ResultSet rs = null;



        try {

          //  CachedRowSet rowSet = new CachedRowSet();

            con = getDB();

            //查询结果会在

            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,

                    ResultSet.CONCUR_READ_ONLY);

            LogTool.debug(sql);

            rs = st.executeQuery(sql);

           // rowSet.populate(rs);

            return rs;



        } catch (SQLException ex) {

            throw new DbException(msg + sql, ex);

        } finally {

            closeDB(con, st, rs);

        }

    }



    /**

     * 处理批处理的sql语句（包括INSERT,DELETE,UPDATE)

     * @param s

     * @return

     * @throws DbException

     */

    public static int[] exeBatch(String[] s) throws DbException {

        String msg = method + "exeBatch  ";

        //assert s != null:msg + "传入sql为空";



        Connection _con = null;

        Statement stmt = null;

        try {

            //得到数据库的连接和创建Statement

            _con = getDB();

            _con.setAutoCommit(false);



            stmt = _con.createStatement();

            //得到多条Sql语句

            for (int i = 0; i < s.length; i++) {

                LogTool.debug(s[i]);

                stmt.addBatch(s[i]);

            }

            //执行批处理

            int[] ret = stmt.executeBatch();

            _con.commit();

            _con.setAutoCommit(true); //add by zhaoyahui 2009/12/24

            return ret;

        } catch (Exception ex) {

            try {

                _con.rollback();

            } catch (Exception ex1) {

                ex1.printStackTrace();

            }

            for (int i = 0; i < s.length; i++) {

                System.out.println(s[i]);

            }

            throw new DbException(msg, ex);

        } finally {

            try {

            	if(_con!=null){ //add by zhaoyahui 2009/12/24
            		_con.setAutoCommit(true);
            	}

                closeDB(_con, stmt);

            } catch (Exception ex) {

                ex.printStackTrace();

            }



        }

    }

    public static int[] exeBatch(String[] s,String dbName) throws DbException {

        String msg = method + "exeBatch  ";

        //assert s != null:msg + "传入sql为空";



        Connection _con = null;

        Statement stmt = null;

        try {

            //得到数据库的连接和创建Statement

            _con = getDB(dbName);

            _con.setAutoCommit(false);



            stmt = _con.createStatement();

            //得到多条Sql语句

            for (int i = 0; i < s.length; i++) {

                LogTool.debug(s[i]);

                stmt.addBatch(s[i]);

            }

            //执行批处理

            int[] ret = stmt.executeBatch();

            _con.commit();

            return ret;

        } catch (Exception ex) {

            try {

                _con.rollback();

            } catch (Exception ex1) {

                ex1.printStackTrace();

            }

            for (int i = 0; i < s.length; i++) {

                System.out.println(s[i]);

            }

            throw new DbException(msg, ex);

        } finally {

            try {

//                _con.setAutoCommit(true);

                closeDB(_con, stmt);

            } catch (Exception ex) {

                ex.printStackTrace();

            }



        }

    }




    /**

     * 得到主键

     * @param tab 表名

     * @return

     * @throws DbException

     */

    public static String getKey(String tab) throws DbException {

        keyFactory fac = new keyFactory();

        KeyCreator key = fac.create(keyFactory.ORA_KEY);

        try {

            return key.getKey(tab,dbName);

        } catch (DbException ex) {

            throw ex;

        }

    }



    /**

     * 读文件

     */

    private void ReadConFile() {

        DB_DRIVER = com.viewscenes.dao.XmlReader.getAttrValue("dbconfig", "para", "driver");

        DB_URL = com.viewscenes.dao.XmlReader.getAttrValue("dbconfig", "para", "url");

        DB_USER = com.viewscenes.dao.XmlReader.getAttrValue("dbconfig", "para", "user");

        DB_PASSWORD = com.viewscenes.dao.XmlReader.getAttrValue("dbconfig", "para", "password");

    }



    /**

     * 得到数据连接(从配置文件dbconfig.property中读DBPool属性，1：使用POOL;0:直接连接

     * @return Connection 对象

     */
/*
    synchronized Connection getDB() throws DbException {

        String msg = method + "getDB:";

        Connection con = null;

        try {

            String pool = com.viewscenes.dao.XmlReader.getAttrValue("dbconfig", "para", "dbpool");

            //使用连接池

            if (pool.equals("1")) {

                con = DatabaseManager.getConnection();

            } else { //直接连接数据库

                ReadConFile();

                Class.forName(DB_DRIVER);

                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                alterSession(con);

            }

        } catch (ClassNotFoundException ex) {

            throw new DbException(msg + "没有找到数据库驱动" + DB_DRIVER, ex);

        } catch (Exception ex) {

            throw new DbException(msg + "未知错误", ex);

        }



        return con;

    }
*/
    /**

     * 得到数据连接(从配置文件dbconfig.property中读DBPool属性，1：使用POOL;0:直接连接

     * @return Connection 对象

     */

    public static Connection getDB() throws DbException {

        String msg = method + "getDB:";

        Connection con = null;
      Throwable t = new Throwable();
      StackTraceElement[] elements = t.getStackTrace();
      if (elements.length>=2){
        String sClass = elements[2].getClassName();
        int lineNum = elements[2].getLineNumber();
        String sMethod = elements[2].getMethodName();
      }

        try {
          if (dbName!=null)
                con = DatabaseManager.getConnection(dbName);
          else
                con = DatabaseManager.getConnection();

        } catch (Exception ex) {
            throw new DbException(msg + "数据库连接错误:"+ex.getMessage(), ex);
        }
        return con;
    }

    synchronized static Connection getDB(String dbName) throws DbException {

        String msg = method + "getDB:";

        Connection con = null;
      Throwable t = new Throwable();
      StackTraceElement[] elements = t.getStackTrace();
      if (elements.length>=2){
        String sClass = elements[2].getClassName();
        int lineNum = elements[2].getLineNumber();
        String sMethod = elements[2].getMethodName();
      }

        try {
                con = DatabaseManager.getConnection(dbName);

        } catch (Exception ex) {
            throw new DbException(msg + "数据库连接错误:"+ex.getMessage(), ex);
        }
        return con;
    }



    /**

     * 关闭数据连接（Connection,Statement)

     * @param con

     * @param stmt

     * @throws DbException

     */



    static void closeDB(Connection con, Statement stmt) throws DbException {

        String msg = method + "closeDB(con,stmt):";



        try {

            if (stmt != null) {

                stmt.close();

            }

        } catch (SQLException ex) {

            throw new DbException(msg + "statement close error", ex);

        }



        try {

            if (con != null) {

                con.close();

            }

        } catch (SQLException ex) {

            throw new DbException(msg + "connection close error", ex);

        }



    }



    /**

     * 关闭数据连接（Connection,Statement,ResultSet)

     * @param con

     * @param stmt

     * @param rs

     * @throws DbException

     */

   static void closeDB(Connection con, Statement stmt, ResultSet rs) throws

            DbException {

        String msg = method + "closeDB(con,stmt,rs):";

        try {

            if (rs != null) {

                rs.close();

            }

        } catch (SQLException ex) {

            throw new DbException(msg + "resultset close error", ex);

        }



        try {

            if (stmt != null) {

                stmt.close();

            }

        } catch (SQLException ex) {

            throw new DbException(msg + "statement close error", ex);

        }



        try {

            if (con != null) {

                con.close();

            }

        } catch (SQLException ex) {

            throw new DbException(msg + "connection close error", ex);

        }

    }



    /**

     * 得到批处理的返回

     * 因为exeBatch返回的是int[],所以要转换成boolean

     * @param a

     * @return

     */

    private boolean getBatReturn(int a[], String[] s) {

        for (int i = 0; i < a.length; i++) {

            if (a[i] <= 0) {

                System.out.println("sql语句 :" + s[i] + "执行不成功");

                return false;

            }

        }

        return true;

    }



    private void alterSession(Connection con) {

        try {

            keyFactory fac = new keyFactory();

            KeyCreator key = fac.create(keyFactory.ORA_KEY);

            key.alterSession(con);

        } catch (SQLException ex) {

        }

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

  public static int[] Insert(GDSet data, long[] key) throws DbException {

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

  public static int[] Insert(GDSet data) throws DbException {

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



  private static int[] Insert(GDSet data, long[] key, boolean autoCreate) throws

      DbException {

//    DbComponent db = new DbComponent(dbName);



    //得到主键及插入语句

    String[] sql = getSqlArray(data, _INSERT, autoCreate, key);

    //执行

    int[] ret = exeBatch(sql);



//    //如果成功，刷新缓存
//
//    if (IsDirty(ret)) {
//
//      setDirty(data.getTableName());
//
//    }

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

  public static int[] Update(GDSet data) throws DbException {

//    DbComponent db = new DbComponent(dbName);

    //得到主键及插入语句

    String[] sql = getSqlArray(data, _UPDATE, false, null);

    //执行

    int[] ret = exeBatch(sql);

    return ret;

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

  public static int[] UpdateX(GDSet data, GDSet con) throws DbException {

//    DbComponent db = new DbComponent(dbName);

    SQLGenerator sg = new SQLGenerator();

    String sql[] = new String[data.getRowCount()];



    int[] ret = null;

    try {

      for (int i = 0; i < data.getRowCount(); i++) {

        //得到sql

        GDSet sub = data.getSubGDSet(i, i + 1);

        sql[i] = sg.generateUpdateXSQL(data, con);

      }

      //执行

      ret = exeBatch(sql);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOComponent UpdateX:GDSet错误"+ex);

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

  public static int[] UpdatebyCon(GDSet content, GDSet con) throws DbException {

//    DbComponent db = new DbComponent(dbName);

    SQLGenerator sg = new SQLGenerator();

    String sql[] = new String[1];

    int[] ret = null;



    //得到sql

    sql[0] = sg.generateUpdatebyConSQL(content, con);

    //执行

    ret = exeBatch(sql);

    return ret;

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

  private static String[] getSqlArray(GDSet data, int type, boolean keyCreate, long[] key) throws

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

      throw new DbException("DAOComponent getSqlArray:GDSet错误"+ex);

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

  private static String getSql(GDSet data, int type, boolean keyCreate) throws DbException {

    String sql = null;



    SQLGenerator sg = new SQLGenerator();

    if (type == _INSERT) {

      sql = sg.generateInsertSQL(data, keyCreate);

      _key = Long.parseLong(sg.getKey());

    }

    if (type == _UPDATE) {

      sql = sg.generateUpdateSQL(data);

    }

    return sql;

  }


  public class DbQuickExeSQL {

         public DbQuickExeSQL(String sql) {
             this.sql = sql;
             System.out.println(sql);
         }

         private PreparedStatement ps = null;
         private Connection cn = null;
         private String sql = "";
         
         /**
          * 得到一个连接，数据为自动提交，不用手动commit
          * @detail  
          * @method  
          * @return 
          * @return  boolean  
          * @author  zhaoyahui
          * @version 2013-4-19 下午02:57:57
          */
         public boolean getConnect() {
             try {
                 cn = getDB();
                 ps = cn.prepareStatement(sql);
                 cn.setAutoCommit(true);
             } catch (Exception ex) {
                 closeConnect();
                 return false;
             }
             return true;
         }
         /**
          * 关闭连接并且提交数据
          * @detail  
          * @method  
          * @return 
          * @return  boolean  
          * @author  zhaoyahui
          * @version 2013-4-19 下午02:57:30
          */
         public boolean closeConnect() {
             try {
                 if (ps != null) {
                     ps.close();
                 }
                 if (cn != null) {
                	 cn.commit();
                	 cn.setAutoCommit(true);
                     cn.close();
                 }
             } catch (SQLException ex) {
                 return false;
             }
             return true;
         }
         /**
          * 得到一个连接并且设置自动提交为false
          * @detail  
          * @method  
          * @return 
          * @return  boolean  
          * @author  zhaoyahui
          * @version 2013-4-19 下午02:56:44
          */
         public boolean getConnectAndAutoCommitFalse() {
             try {
                 cn = getDB();
                 ps = cn.prepareStatement(sql);
                 cn.setAutoCommit(false);
             } catch (Exception ex) {
                 closeConnect();
                 return false;
             }
             return true;
         }
         /**
          * 关闭连接并且回滚数据
          * @detail  
          * @method  
          * @return 
          * @return  boolean  
          * @author  zhaoyahui
          * @version 2013-4-19 下午02:57:06
          */
         public boolean closeConnectAndRollback() {
             try {
                 if (ps != null) {
                     ps.close();
                 }
                 if (cn != null) {
                	 cn.rollback();
                	 cn.setAutoCommit(true);
                     cn.close();
                 }
             } catch (SQLException ex) {
                 return false;
             }
             return true;
         }
         
         public boolean exeSQL() throws Exception{
             //try {
                 ps.execute();
             //} catch (SQLException ex) {
             //    ex.printStackTrace();
                 return false;
             //}
            // return true;
         }

         public ResultSet exeQuery() {
        	 ResultSet rs = null;
             try {
            	 rs = ps.executeQuery();
             } catch (SQLException ex) {
                 return null;
             }
             return rs;
         }

         public void setInt(int index, int intValue) {
             try {
                 ps.setInt(index, intValue);
             } catch (SQLException ex) {
             }
         }

         public void setString(int index, String stringValue) {
             try {
                 ps.setString(index, stringValue);
             } catch (SQLException ex) {
             }
         }

         public void setDate(int index, java.sql.Date dateValue) {
             try {
                 ps.setDate(index, dateValue);

             } catch (SQLException ex) {
             }
         }

         public void setBoolean(int index, boolean booleanValue) {
             try {
                 ps.setBoolean(index, booleanValue);
             } catch (SQLException ex) {
             }

         }

         public void setTime(int index, java.sql.Time timeValue) {
             try {
                 ps.setTime(index, timeValue);
             } catch (SQLException ex) {
             }
         }

         public void setDouble(int index, double doubleValue) {
             try {
                 ps.setDouble(index, doubleValue);
             } catch (SQLException ex) {
             }
         }

         public void setFloat(int index, float floatValue) {
             try {
                 ps.setFloat(index, floatValue);
             } catch (SQLException ex) {
             }

         }

         public void setLong(int index, long longValue) {
             try {
                 ps.setLong(index, longValue);
             } catch (SQLException ex) {
             }
         }

         public void setNull(int index, int typeValue) {
             try {
                 ps.setNull(index, typeValue);
             } catch (SQLException ex) {
             }
         }

         public void setTimestamp(int index, java.sql.Timestamp timestampValue) {
             try {
                 ps.setTimestamp(index, timestampValue);

             } catch (SQLException ex) {
             }
         }
     }



}
