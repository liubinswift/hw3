package com.viewscenes.cache.util;

import java.lang.reflect.*;

/**
 *
 * <p>Title: 缓存池模块要用到的工具类。</p>
 * <p>Description: 提供关键索引列值比较，数组删除等方法。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class CacheUtil {
  private static Class thisClass = CacheUtil.class;

  public CacheUtil() {
  }

  /**
   * 从指定数组中删除相应数据。
   * @param needDeledArray Object[]
   * @param delPos int
   * @param delNums int
   * @return int 实际删除的数据量
   */
  public static void delArrayData(Object[] needDeledArray, int delPos,
				  int delNums) {
    if (needDeledArray == null) {
      return;
    }

    if (delPos >= needDeledArray.length) {
      return;
    }

    delNums = Math.min(delNums, needDeledArray.length - delPos);

    /**
     * 移动后续数据到删除位。
     */
    int movePos = delPos + delNums;
    int moveNums = needDeledArray.length - movePos;
    if (moveNums > 0) {
      System.arraycopy(needDeledArray, movePos, needDeledArray, delPos,
		       moveNums);
    }

    /**
     * 释放后续内存数据。
     */
    for (int delI = delPos + moveNums; delI < needDeledArray.length; delI++) {
      needDeledArray[delI] = null; //Let gc to do works.
    }
  }

  /**
   * 比较关键索引列值的方法。利用关键索引列值类型，反射其compareTo方法来取得结果。
   * 已经不用了，转到数据类本身了。
   * @param obj1 Object
   * @param obj2 Object
   * @throws Exception
   * @return int 正obj1大，0等，负obj1小。
   */
  public static int compare(Object obj1, Object obj2, Class objectClass) throws
      Exception {
    String methodMessage = thisClass.getName() +
	":compare(Object,Object,signColClass)";
    int compResult = 0;
    try {
      Method compareTo = objectClass.getMethod("compareTo",
					       new Class[] {objectClass});
      compResult = ( (Integer) compareTo.invoke(obj1, new Object[] {obj2})).
	  intValue();
    }
    catch (NoSuchMethodException ex) {
      throw new Exception(methodMessage + "出错：" + objectClass.getName() +
			  "不支持compareTo(thisClassObject)方法!", ex);
    }
    catch (IllegalArgumentException ex) {
      throw new Exception(methodMessage + "出错：缓存关键值字段不是" + objectClass.getName() +
			  "类型的对象!", ex);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(methodMessage + "出错：" + ex.getMessage());
    }
    return compResult;
  }

}
