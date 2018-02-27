package com.viewscenes.dao.logic;

import com.viewscenes.dao.database.*;
import com.viewscenes.dao.innerdao.*;
import com.viewscenes.pub.*;

/**
 * 频道的插入

 */

public class Channel

    extends DAOComponent {

  /**
   * 插入（自动生成主键的，支持多行插入），得到一组主键key

   */

  public int[] Insert(GDSet data, long[] key) throws DbException {

    int[] row = {

	1, 1};

    long[] temkey = new long[data.getRowCount()];

    beforeInsert(data, temkey);

    commonInsert(data, temkey);

    //如果key不为空，则把主键数组复制

    if (key != null) {

      System.arraycopy(temkey, 0, key, 0, temkey.length);

    }

    return row;

  }

  /**
   * 插入

   * @param data

   * @param key

   * @throws DbException

   */

  private int[] commonInsert(GDSet data, long[] key) throws DbException {

    try {

      GDSet ret = data.getSubGDSet(0, data.getRowCount());

      String[] skey = long2String(key);

      if (ret.getColumnIndex("ch_id") == -1) {

	ret.addColumn("ch_id", "ch_id", Column.COLUMN_TYPE_LONG);

      }

      ret.updateColumnData("ch_id", 0, skey);

      DAOComponent dao = new DAOComponent();

      return dao.Insert(ret);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOLogicComponent commonInsert:GDSet错误", ex);

    }

  }

  /**
   * 先往mon_channel_tab插入记录

   * @param data

   * @return

   * @throws DbException

   */

  private int[] beforeInsert(GDSet data, long[] key) throws DbException {

    try {

      String[] field = {

	  "type", "code", "name", "broadcaster"};

      GDSet set = new GDSet("mon_channel_tab", field);

      //插入,得到主键

      for (int i = 0; i < data.getRowCount(); i++) {

	String type = data.getString(i, "type");

	String code = data.getString(i, "code");

	String name = getField(data, i, "name");

	String caster = getField(data, i, "broadcaster");

	String rec[] = {

	    type, code, name, caster};

	set.addRow(rec);

      }

      DAOComponent dao = new DAOComponent();

      return dao.Insert(set, key);

    }

    catch (GDSetException ex) {

      throw new DbException("DAOLogicComponent handleBeforeTask:GDSet错误", ex);

    }

  }

  private String getField(GDSet data, int i, String name) throws GDSetException {

    if (data.getColumnIndex(name) != -1) {

      return data.getString(i, name);

    }

    return "";

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

}
