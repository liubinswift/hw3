package com.viewscenes.dao.logic;

import java.util.*;

import org.jdom.*;
import com.viewscenes.dao.*;
import com.viewscenes.dao.database.*;
import com.viewscenes.dao.innerdao.*;
import com.viewscenes.pub.*;

/**
 * 逻辑表的插入

 * 包括多表的插入

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class DAOLogicComponent {

  DAOComponent dao = new DAOComponent();

  /**
   * 判断表是否属于caption类的操作

   * @param caption 操作类型的名称

   * @param tabName 表名

   * @return

   * null:表不属于caption类的操作

   * String:该tab对应的type

   */

  private String operContains(String caption, String tabName) {

    String lowerTab = tabName.toLowerCase();

    Element ele = XmlReader.getConfigItem("updateconfig");

    Element task_ele = ele.getChild(caption);

    List total_tab = task_ele.getChildren();

    for (int i = 0; i < total_tab.size(); i++) {

      Element tab = (Element) total_tab.get(i);

      String tname = tab.getAttributeValue("table");

      if (tname == null) {

	return null; //不属于该项操作

      }

      if (tname.equals(lowerTab)) {

	return tab.getAttributeValue("type");

      }

    }

    return null;

  }

  /**
   * 插入（自动生成主键的，支持多行插入），得到一组主键key

   */

  public void Insert(GDSet data, long[] key) throws DbException {

    beforeInsert(data, key);

    commonInsert(data, key);

    afterInsert(data);

  }

  /**
   * 插入

   * @param data

   * @param key

   * @throws DbException

   */

  private void commonInsert(GDSet data, long[] key) throws DbException {

    String tab = data.getTableName();

    //如果属于任务表，则把主键(task_id)的列加入到记录中

    if (operContains("task", tab) != null) {

      heandleTask(data, key);

    }
    else { //一般表的插入

      dao.Insert(data, key);

    }

  }

  /**
   * 插入的前操作

   * @param data

   * @return

   * @throws DbException

   */

  private void beforeInsert(GDSet data, long[] key) throws DbException {

    String tab = data.getTableName();

    String type = null;

    //如果插入的表是任务表，则先往mon_task_tab中插入记录，得到主键

    if ( (type = operContains("task", tab)) != null) {

      handleBeforeTask(type, key);

    }

  }

  /**
   * 插入的后操作

   * @param data

   */

  private void afterInsert(GDSet data) {

  }

  /**
   * 把long形数组转成字符串数组

   * @param key

   * @return

   */

  private String[] long2String(long[] key) {

    String[] s = new String[key.length];

    for (int i = 0; i < s.length; i++) {

      s[i] = String.valueOf(key[i]);

    }

    return s;

  }

  /**
   * 插入任务表的基表,并返回主键

   * @param type 任务的类型

   * @param key[out] 主键

   * @return

   * @throws DbException

   */

  private void handleBeforeTask(String type, long[] key) throws DbException {

    try {

      String[] field = {

	  "type"};

      GDSet set = new GDSet("mon_task_tab", field);

      String rec[] = {

	  type};

      //插入,得到主键

      for (int i = 0; i < key.length; i++) {

	set.addRow(rec);

      }

      dao.Insert(set, key);

    }
    catch (GDSetException ex) {

      throw new DbException("DAOLogicComponent handleBeforeTask:GDSet错误", ex);

    }

  }

  /**
   * 处理任务

   * @param data

   * @param key

   * @throws DbException

   */

  private void heandleTask(GDSet data, long[] key) throws DbException {

    try {

      GDSet ret = data.getSubGDSet(0, data.getRowCount());

      String[] skey = long2String(key);

      ret.addColumn("task_id", "task_id", Column.COLUMN_TYPE_LONG, skey);

      dao.Insert(ret);

    }
    catch (GDSetException ex) {

      throw new DbException("DAOLogicComponent commonInsert:GDSet错误", ex);

    }

  }

}
