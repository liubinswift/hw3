package com.viewscenes.dao.innerdao;

import java.util.*;
import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.dao.ConditionColumn;
import com.viewscenes.dao.Operator;



/**
 * 条件对象的过滤器

 * 把表示条件的GDSet对象分解成表名，需要返回的字段名，查询条件

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class ConBaseFilter {

  protected String _tabName; //表名

  protected String _field; //字段

  protected String _condition = " "; //条件

  private static Map _map;

  static {

    _map = new HashMap();

    //加载操作符处理器

    Handler orderHandler = new HandlerOrder();

    Handler comHandler = new HandlerCommon();

    Handler inHandler = new HandlerIn();

    Handler braHandler = new HandlerBracket();

    Handler groupHandler = new HandlerGroup();

    _map.put(Operator._IN, new HandlerIn());

    _map.put(Operator._NOTIN, new HandlerIn());

    _map.put(Operator._ORDERBY, orderHandler);

    _map.put(Operator._GREAT, comHandler);

    _map.put(Operator._GREATEQUS, comHandler);

    _map.put(Operator._EQUAL, comHandler);

    _map.put(Operator._LESS, comHandler);

    _map.put(Operator._LESSEQUS, comHandler);

    _map.put(Operator._UNEQUAL, comHandler);

    _map.put(Operator._UNEQUAL1, comHandler);

    _map.put(Operator._LIKE, comHandler);

    _map.put(Operator._NOTLIKE, comHandler); //add by xyw

    _map.put(Operator._BRACKET, braHandler);

    _map.put(Operator._GROUP, groupHandler);

  }

  public ConBaseFilter() {

  }

  public ConBaseFilter(String field, GDSet condition) throws DbException {

    String tab = condition.getTableName();

    //设置各个属性

    setField(field);

    setTabName(tab);

    setCondition(condition);

  }

  /**
   * 设置查询条件的属性

   * @param condition

   * @throws DbException

   */

  private void setCondition(GDSet condition) throws DbException {

    _condition = combinCondition(condition);

  }

  /**
   * 设置字段的属性

   * @param field

   */

  private void setField(String field) {

    _field = field;

  }

  /**
   * 设置表名的属性

   * @param tabName

   */

  private void setTabName(String tabName) {

    _tabName = tabName;

  }

  /**
   * 得到条件

   * @return

   */

  public String getCondition() {

    return _condition.trim();

  }

  /**
   * 得到选择字段

   * @return

   */

  public String getField() {

    return _field;

  }

  /**
   * 得到表名

   * @return

   */

  public String getTabName() {

    return _tabName;

  }

  /**
   * 得到第i行的field值

   * @param condition

   * @param conditioni 的行数

   * @return 字段值

   * @throws GDSetException

   */

  protected String getConColumn(GDSet condition, int i) throws GDSetException {

    return condition.getString(i, ConditionColumn.FIELD);

  }

  /**
   * 得到第i行的type值

   * @param condition

   * @param conditioni 的行数

   * @return 字段值

   * @throws GDSetException

   */

  protected String getConType(GDSet condition, int i) throws GDSetException {

    return condition.getString(i, ConditionColumn.TYPE);

  }

  /**
   * 得到第i行的OPERATOR值

   * @param condition

   * @param conditioni 的行数

   * @return 字段值

   * @throws GDSetException

   */

  protected String getConOperator(GDSet condition, int i) throws GDSetException {

    return condition.getString(i, ConditionColumn.OPERATOR).trim();

  }

  /**
   * 得到第i行的VALUE值

   * @param condition

   * @param conditioni 的行数

   * @return 字段值

   * @throws GDSetException

   */

  protected String getConValue(GDSet condition, int i) throws GDSetException {

    return condition.getString(i, ConditionColumn.VALUE);

  }

  /**
   * 得到第i行的BETWEEN值

   * @param condition

   * @param conditioni 的行数

   * @return 字段值

   * @throws GDSetException

   */

  protected String getConBetwwen(GDSet condition, int i) throws GDSetException {

    return condition.getString(i, ConditionColumn.BETWEEN);

  }

  /**
   * 组合查询条件

   * @param condition

   * @return

   */

  protected String combinCondition(GDSet condition) throws DbException {

    try {

      SqlObject sqlObj = new SqlObject();

      for (int i = 0; i < condition.getRowCount(); i++) {

        Condition subCon = new Condition();

        subCon.setColumn(getConColumn(condition, i));

        subCon.setType(getConType(condition, i));

        subCon.setOper(getConOperator(condition, i));

        subCon.setValue(getConValue(condition, i));

        subCon.setBetween(getConBetwwen(condition, i));

        String oper = getConOperator(condition, i);

        getHandler(oper).execute(subCon, sqlObj);

      }

      return sqlObj.toString();

    }
    catch (GDSetException ex) {

      throw new DbException("ConBaseFilter combinCondition", ex);

    }

  }

  /**
   * 得到操作符相应的处理器

   * @param oper 操作符的名称

   * @return

   */

  private Handler getHandler(String oper) throws DbException {

    Handler hander = (Handler) _map.get(oper);

    if (hander == null) {

      throw new DbException("ConBaseFilter getHandler:该运算符" + oper + "不存在");
    }

    return hander;

  }

}
