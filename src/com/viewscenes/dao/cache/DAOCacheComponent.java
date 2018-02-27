package com.viewscenes.dao.cache;

import java.util.*;

import org.jdom.*;
import com.viewscenes.dao.DAOOperator;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.dao.innerdao.Common;
import com.viewscenes.util.LogTool;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.cache.CacheThread;



/**
 * 数据库缓存访问部件

 * query:查询缓存

 * hitCache:是否命中缓存

 */

public class DAOCacheComponent

    extends DAOOperator {

  /**
   * 包含CacheRowset

   */



  //CacheObject和表名的Map，保证线性安全的

  private static Map _map = Collections.synchronizedMap(new HashMap());

  //缓存表和物理表关系的映射表,用于物理表变动后找出对应的缓存表名；

  //key为物理表名；object为ArrayList:记录相应的缓存表名

  private static Map _tabNameMap = Collections.synchronizedMap(new HashMap());

  static String[] _tabName;

  static {

    //加载配置文件

    getCacheTab();

    //加载缓存表和物理表关系的映射表

    loadTabMap();

    //加载数据到缓存

    load();

    //启动刷新线程

    new CacheThread().start();

  }

  /**
   * 得到所有缓存的表名

   */

  private static void getCacheTab() {

    try {
      Element ele = XmlReader.getConfigItem("cachconfig");

      List list = ele.getChildren();

      int size = list.size();

      _tabName = new String[size];

      for (int i = 0; i < size; i++) {

        Element sub = (Element) list.get(i);

        _tabName[i] = sub.getAttributeValue("table");

      }
    }
    catch (Exception e) {
      LogTool.debug(e);
    }

  }

  /**
   * 单表查询

   * @param field

   * "*"查询全部

   * @param condition

   * condition的格式为

   *     必须设定TableName

   *     1行5列的二维数据集(暂时支持一个条件）

   *     列标题固定为：

   *         field：属性名称

   *         type：属性类型(VARCHAR,NUMBER,DATE）

   *               现在只支持VARCHAR,NUMBER

   *         operator：操作符(现在只支持=)

   *         value：值

   *         between：和前一个查询条件之间的关系，第一条记录默认为and

   * @return String

   */

  public GDSet Query(String field, GDSet condition) throws DbException {

    //assert (field != null && !field.equals("") && condition != null):"参数为空";

    GDSet gd = null;

    String tname = condition.getTableName();

    //得到表的缓存对象

    CacheObject obj = getCatchObject(tname);

    GDSet gdData = obj.getData();

    try {

      gd = createGDStruct(gdData, field);

      //如果rs为空，则立刻返回

      if (gdData.getRowCount() == 0) {

        return gd;

      }

      //查询条件可能是center_id=5或name="广电总局"

      //center_id是索引，则按索引查询；name不是索引，则遍历gd一遍

      String fd = getConditionField(condition);

      //命中索引

      if (obj.hitIndex(fd)) {

        getCachebyIndex(condition, obj, gd);

        return gd;

      }

      //不命中索引

      getCache(condition, gdData, gd);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent Query:GDSet的错误", ex);

    }

    return gd;

  }

  public GDSet Query(String field, GDSet condition, int start, int end) throws

      DbException {

    //assert (field != null && !field.equals("") && condition != null):"参数为空";

    //assert (start >= -1 && end >= 0 && start < end):

    //"start和end必须要大于0,start必须<end";

    try {

      return Query(field, condition).getSubGDSet(start, end);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent Query:GDSet的错误", ex);

    }

  }

  /**
   * 把数据库数据装载到缓存

   */

  public static void load() {

    if (_tabName == null) {

      return;

    }

    for (int i = 0; i < _tabName.length; i++) {

      DB2Cache(_tabName[i]);

    }

  }

  /**
   * 得到缓存表的map

   * @return

   */

  protected Map getMap() {

    return _map;

  }

  /**
   *  缓存是否命中

   * @param tname

   * @return

   */

  public boolean hitCache(String tname) {

    if (_map == null || _map.get(tname) == null) {

      return false;

    }

    return true;

  }

  /**
   * 取得GDSet某行指定的某些字段值，组合成数组返回

   * @param gdData 数据源

   * @param Column 指定返回的字段名

   * @param row 定位gdData的行

   * @return

   * @throws DbException

   */

  private String[] getRow(GDSet gdData, String[] Column, int row) throws
      DbException {

    //assert Column != null:"DAOCacheComponent getRow:Column 不能为null";

    String[] value = new String[Column.length];

    String curField = null;

    try {

      for (int i = 0; i < Column.length; i++) {

        curField = Column[i];

        value[i] = gdData.getString(row, Column[i]);

      }

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent getRow: " + curField + "取不到数据",
                            ex);

    }

    return value;

  }

  /**
   * 返回查询字段数组

   * @param gdData

   * @param field

   * @return

   */

  private String[] getFieldAry(GDSet gdData, String field) {

    String[] fieldArray = null;

    //如果查询全部，则返回该表的全部字段

    if (field.equals("*")) {

      fieldArray = gdData.getAllColumnName();

    }

    else {

      fieldArray = Common.Field2Array(field);

    }

    return fieldArray;

  }

  /**
   * 加载一个表到_map中

   * 根据配置文件cache.property得到缓存表与数据库资源表的关系

   * @param tab 表名

   */

  protected static synchronized void DB2Cache(String tab) {

    DbComponent db = new DbComponent();

    String sql = XmlReader.getNextAttrValue("cachconfig", "table", tab, "sql");

    try {

      GDSet gdData = db.Query(sql);

      CacheObject obj = new CacheObject(gdData, tab);

      _map.put(tab, obj);

    }

    catch (DbException ex) {

      ex.printStackTrace();

    }

  }

  /**
   * 得到命中的CacheObject

   * @param tname

   * @return

   */

  private CacheObject getCatchObject(String tname) {

    //如果该缓存数据不存在或为dirty,则reload 缓存

    if (_map == null || _map.get(tname) == null

        || ( (CacheObject) _map.get(tname)).IsDirty()) {

      DB2Cache(tname);

    }

    return (CacheObject) _map.get(tname);

  }

  /**
   * 如果增删改，则设该表标志位为dirty

   * @param tab

   */

  public void setDirty(String tab) {

    ArrayList list = (ArrayList) _tabNameMap.get(tab);

    if (list == null) {
      return;
    }

    for (int i = 0; i < list.size(); i++)

    {

      CacheObject cache = (CacheObject) _map.get(list.get(i));

      if (cache != null) {

        cache.setDirty();

      }

    }

  }

  /**
   * 用索引获得缓存

   * @param condition 查询条件

   * @param obj 缓存对象

   * @param gd[out] 返回的GDSet

   * @return

   * @throws DbException

   */

  private void getCachebyIndex(GDSet condition, CacheObject obj, GDSet gd) throws
      DbException {

    try {

      LogTool.debug("hit index");

      //得到查询条件的属性和值

      String field = condition.getString(0, "field");

      String value = condition.getString(0, "value");

      //根据索引条件查找数据

      GDSet data = obj.getDataByIndex(field, value);

      //如果没有找到记录，则直接返回

      if (data == null) {

        return;

      }

      String[] row = getRow(data, gd.getAllColumnName(), 0);

      gd.addRow(row);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent getCachebyIndex:GDSet的错误", ex);

    }

  }

  /**
   * 遍历缓存得到符合查询条件的记录

   * @param retfdArray 返回的字段数组

   * @param conField 查询条件字段

   * @param conValue 查询条件值

   * @param gdData

   * @param gd 返回的GDSet

   * @throws DbException

   */

  private void getCache(GDSet condition, GDSet srcData, GDSet gd) throws
      DbException {

    LogTool.debug("not hit Index");

    try {

      for (int i = 0; i < srcData.getRowCount(); i++) {

        //如果满足条件，则加上该行记录

        if (gdFillCondition(condition, srcData, i)) {

          String[] row = getRow(srcData, gd.getAllColumnName(), i);

          gd.addRow(row);

        }

      }

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent getHitCache:GDSet的错误", ex);

    }

  }

  /**
   * 当前gd是否满足查询条件

   * @param condition

   * @param gdData

   * @return

   * true:成功

   * false:失败

   */

  private boolean gdFillCondition(GDSet condition, GDSet gdData, int curRow) throws
      DbException {

    //如果没有查询条件，则返回真

    if (condition.getRowCount() == 0) {

      return true;

    }

    try {

      //得到查询条件的各属性

      String field = condition.getString(0, "field");

      String value

          = condition.getString(0, "value");

      if (gdData.getString(curRow, field).equals(value)) {

        return true;

      }

      else {

        return false;

      }

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent fillCondition:GDSet的错误", ex);

    }

  }

  /**
   * 得到查询条件中字段的值

   * @param condition

   * @return

   * @throws DbException

   */

  private String getConditionField(GDSet condition) throws DbException {

    try {

      if (condition.getRowCount() == 0) {

        return "";

      }

      return condition.getString(0, "field");

    }

    catch (GDSetException ex) {

      throw new DbException("DAOCacheComponent getField:GDSet的错误", ex);

    }

  }

  /**
   * 以field为列标题构造一个GDSet,如果field为*,则构造一个结构和srcGd一样的GDSet

   * @param srcGd 源

   * @param field 字段名，以","分隔

   * @return

   */

  private GDSet createGDStruct(GDSet srcGd, String field) throws GDSetException {

    GDSet gd = null;

    //如果field为*,则构造一个结构和srcGd一样的GDSet

    if (field.equals("*")) {

      gd = srcGd.getSubGDSet(0, 0);

    }

    else {

      //以field为列标题构造一个GDSet

      String[] fieldArray = Common.Field2Array(field);

      gd = new GDSet(srcGd.getTableName(), fieldArray);

    }

    return gd;

  }

  /**
   * 加载缓存表和物理表关系的映射表

   * key为物理表名；object为ArrayList:记录相应的缓存表名

   */

  private static void loadTabMap() {

    List list = XmlReader.getConfigItem("cachconfig").getChildren();

    for (int i = 0; i < list.size(); i++) {

      Element ele = (Element) list.get(i);

      String cacheTab = ele.getAttributeValue("table");

      String[] phyTabs = Common.Field2Array(ele.getAttributeValue("alltable"));

      for (int j = 0; j < phyTabs.length; j++) {

        ArrayList ary = (ArrayList) _tabNameMap.get(phyTabs[j]);

        if (ary == null) {

          ary = new ArrayList();

        }

        ary.add(cacheTab);

        _tabNameMap.put(phyTabs[j], ary);

      }

    }

  }

}
