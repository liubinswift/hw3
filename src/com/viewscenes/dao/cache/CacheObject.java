package com.viewscenes.dao.cache;

import java.util.*;
import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.innerdao.Common;
import com.viewscenes.pub.GDSetException;



/**
 * 缓存对象

 * 包括用ResultSet存储的数据和存储索引的keysMap

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class CacheObject {

  private GDSet _gd; //缓存数据

  private HashMap keysMap; //key:每张表的索引（1或多个） object:keyToIndexMap

  private String[] keysArray; //记录该表的索引值

  private HashMap[] keyToIndexMap; //记录表中索引值和行号的关系

  private String tabName;

  //脏标志，如果insert,update,delete改变数据库，则设置此值为脏，CacheThread负责定时刷新

  private boolean _dirty;

  public CacheObject(GDSet gd, String tabName) {

    this._gd = gd;

    this.tabName = tabName;

    try {

      //加载索引与行号关系

      loadKeysArray();

      loadIndexMap();

      loadKeysMap();

    }
    catch (Exception ex) {

      ex.printStackTrace();

    }

  }

  /**
   * 设置脏标志

   */

  public void setDirty() {

    _dirty = true;

  }

  /**
   * 清除脏标志

   */

  public void clearDirty() {

    _dirty = false;

  }

  /**
   * 是否为脏

   * @return

   */

  public boolean IsDirty() {

    return _dirty;

  }

  /**
   * 得到缓存数据

   * @return

   */

  public GDSet getData() {

    return _gd;

  }

  /**
   * 加载索引

   */

  private void loadKeysArray() {

    //得到表的索引

    String indexs = XmlReader.getNextAttrValue("cachconfig", "table", tabName,
                                               "index");

    keysArray = Common.Field2Array(indexs);

  }

  /**
   * 加载loadKeysMap，即加载索引值(keysArray)和索引值和行号的关系map(keyToIndexMap)

   */

  private void loadKeysMap() {

    keysMap = new HashMap();

    for (int i = 0; i < keysArray.length; i++) {

      keysMap.put(keysArray[i], keyToIndexMap[i]);

    }

  }

  /**
   * 加载keyToIndexMap，即索引值和行号的关系

   */

  private void loadIndexMap() {

    //assert _gd != null:"data不能为null";

    int indexSize = keysArray.length;

    //每个索引对应一个hashMap,该hashMap记录rs中该索引的值和行号的对应关系

    keyToIndexMap = newHashMapArray(indexSize);

    int row = 0;

    try {

      for (int i = 0; i < _gd.getRowCount(); i++) {

        for (int j = 0; j < indexSize; j++) {

          String indexValue = _gd.getString(i, keysArray[j]);

          keyToIndexMap[j].put(indexValue, new Integer(row));

        }

        row++;

      }

    }
    catch (GDSetException ex) {

      ex.printStackTrace();

    }

  }

  /**
   * 是否命中索引

   * @param index

   * @return

   */

  public boolean hitIndex(String index) {

    if (keysMap.get(index) == null) {

      return false;

    }

    return true;

  }

  /**
   * 根据索引得到命中的数据

   * @param index

   * @return

   * null:没有命中

   */

  public GDSet getDataByIndex(String index, String value) {

    int row = 0;

    try {

      if ( (row = getRowByIndex(index, value)) != -1) {

        return _gd.getSubGDSet(row, row + 1);

      }

    }
    catch (GDSetException ex) {

      ex.printStackTrace();

    }

    return null;

  }

  /**
   * 根据索引得到命中的行号

   * @param index

   * @return

   */

  private int getRowByIndex(String index, String value) {

    HashMap map = (HashMap) keysMap.get(index);

    if (map == null) {

      return -1;

    }

    Integer row = (Integer) map.get(value);

    if (row == null) {

      return -1;

    }

    return row.intValue();

  }

  /**
   * 生成HashMap对象数组及初始化

   * @param size

   * @return

   */

  private HashMap[] newHashMapArray(int size) {

    HashMap[] keyToIndexMap = new HashMap[size];

    for (int i = 0; i < keyToIndexMap.length; i++) {

      keyToIndexMap[i] = new HashMap();

    }

    return keyToIndexMap;

  }

}
