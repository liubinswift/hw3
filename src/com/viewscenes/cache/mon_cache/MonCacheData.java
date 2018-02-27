package com.viewscenes.cache.mon_cache;

import java.io.*;
import java.util.*;

import java.lang.reflect.*;
import com.viewscenes.cache.util.CacheUtil;

/**
 *
 * <p>Title: 缓存详细信息对象 </p>
 * <p>Description: 用于封装实时数据及其索引数组，并提供相应的维护接口。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: novel_tongfang</p>
 * @author lixuefeng
 * @version 1.0
 */
public class MonCacheData
    implements Serializable {
  private int maxRow = 600; //最大数据行限制.
  private HashMap[] dataCache = null; //数据缓存
  private int dataSize = 0; //实际数据容量

  private Object signColName = null; //主键列名
  private Method compareToMethod = null; // 主键数据类型的comareTo方法。

  /**
   *
   * @param maxRow int
   * @param signColName Object
   * @param signColClass Class
   * @throws Exception
   */
  public MonCacheData(int maxRow, Object signColName, Class signColClass) throws
      Exception {
    this.maxRow = maxRow;
    this.dataCache = new HashMap[maxRow];
    this.dataSize = 0;
    this.signColName = signColName;
    this.compareToMethod = signColClass.getMethod("compareTo",
						  new Class[] {signColClass});
  }

  /**
   * 根据标志提取相应量的数据（不包括标志）。
   * @param lastRowSign Object
   * @param getNums int
   * @return HashMap[]
   */
  public HashMap[] getData(Object lastRowSign) throws Exception {
    //返空情况特殊处理（无数据；标志不为空时比最大还大等）
    if (dataSize <= 0 ||
	(lastRowSign != null && compare(lastRowSign, dataSize - 1) >= 0)) {
      return null;
    }

    //全返情况特殊处理(标志为空；比最小还小)
    if (lastRowSign == null || compare(lastRowSign, 0) < 0) {
      HashMap[] retData = new HashMap[dataSize];
      System.arraycopy(dataCache, 0, retData, 0, dataSize);
      return retData;
    }

    /**
     * 利用二分查找，找到就从后面开始提；
     * 找不到就从跳出位开始返回。
     */
    int bottomPos = 0;
    int upPos = dataSize - 1;
    int midPos = 0; //中间位，并记录最终返回位。

    while (true) {
      midPos = (bottomPos + upPos) / 2;

      int compResult = compare(lastRowSign, midPos);
      if (compResult == 0) {
	//midPos+1就是要找数据的开始。
	//因为前面的返回处理，所以必然比dataSize-1大。
	midPos = midPos + 1;
	break;
      }

      if (compResult > 0) {
	bottomPos = midPos + 1;
      }
      else if (compResult < 0) {
	upPos = midPos - 1;
      }

      if (bottomPos >= upPos) {
	//大数即为返回开始。
	midPos = bottomPos;
	break;
      }
    }

    //因为前面的最大返回处理，此时moveNums必大于0.
    int moveNums = dataSize - midPos;
    HashMap[] retData = new HashMap[moveNums];
    System.arraycopy(dataCache, midPos, retData, 0, moveNums);
    return retData;
  }

  /**
   * 添加新数据。默认是已经排好序的新数据。
   * @param rowData HashMap[] 要添加的行数据集，已排好序的。
   */
  public void addData(HashMap[] newData) throws Exception {
    /**
     * 当缓存空间不够用时，
     */
    if (newData.length + dataSize > maxRow) {
      //清除1/4数据
      int delLength = Math.max(maxRow / 4, newData.length);
      //防止超过数据容量。
      delLength = Math.min(delLength, dataSize);
      CacheUtil.delArrayData(dataCache, 0, delLength);
      dataSize -= delLength;
    }

    /**
     * 插入新数据:
     * 可能在上报时，会出现重复上报的情况；
     * 所以添加数据时，先从缓存中查到比第一条（最小新）大的，
     * 直到找到小的停，删除后续的已有。
     */
    //计算删除重复位。
    int delDuPos = dataSize - 1;
    Object newMinSign = newData[0].get(signColName);

    for (delDuPos = dataSize - 1; delDuPos >= 0; delDuPos--) {
      //比新数据最小还小处跳出。
      if (compare(newMinSign, delDuPos) > 0) {
	break;
      }
    }

    if(delDuPos<0){//add by zhaoyahui 2010/3/15
        delDuPos=0;
    }
    //删除原来重复的旧数据.
    if (delDuPos < dataSize - 1) {
      CacheUtil.delArrayData(dataCache, delDuPos, dataSize - delDuPos);
      dataSize -= dataSize - delDuPos;
    }

    /**
     * 插入新数据。
     */
    int newStart = Math.max(0, newData.length - maxRow);
    System.arraycopy(newData, newStart, dataCache, dataSize,
		     newData.length - newStart);
    dataSize += newData.length - newStart;
  }

  /**
   * 对象比较语句简化。
   * @param obj1 Object
   * @param obj2 Object
   * @return int
   */
  private int compare(Object obj1, int obj2Pos) throws Exception {
    return ( (Integer) compareToMethod.invoke(obj1,
					      new Object[] {dataCache[obj2Pos].
					      get(signColName)})).intValue();
  }
}
