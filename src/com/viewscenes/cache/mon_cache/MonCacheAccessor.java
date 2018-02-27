package com.viewscenes.cache.mon_cache;

import java.util.*;

import com.viewscenes.cache.cache_pool.*;
import java.util.HashMap;

/**
 *
 * <p>Title: 实时监听模块缓存访问器</p>
 * <p>Description: 为实时监听提供的缓存访问接口，在此层次上提供并发控制。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: novel_tongfang</p>
 * @author lixuefeng
 * @version 1.0
 */
public class MonCacheAccessor {
  private static final String monCacheSignHead = "mon_";

  private static int defaultMaxRow = 15 * 60 * 1000 / 1000 + 1;

  private static Object syncObj = new Object();

  /**
   * 实时缓存数据提取的对外接口:根据缓存标志、上次最后标志／初次提取标志提取数据。
   * 注意返回HashMap数组虽然是新数组，但内部数据仅克隆一层，在外面修改时要注意.
   * 最好不管内部如何保证，在外面使用一旦要修改数据内容时，就要克隆后再修改。
   *
   * @param cacherSign String  缓存标志,最好不要为空。
   * 如果为空，返回结果可能为空，
   * 也可能返回add时为的null标志的值(如果add没有管的话)。
   * @param lastRowSign Object 上次／初次行索引标志
   * @throws Exception
   * @return HashMap[] （统一返回>的数据，不要=）
   */
  public static HashMap[] getCacheData(String cacherSign, Object lastRowSign) throws
      Exception {
    synchronized (syncObj) {
      //缓存提取，必须并必，要不互相干扰。
      MonCacheData cacheData = (MonCacheData) DataCachePool.getCacheData(
	  monCacheSignHead + cacherSign);

      if (cacheData == null) {
	return null;
      }

      //提取返回。
      HashMap[] retData = cacheData.getData(lastRowSign);
      /**
       * 返回数据已经浅层克隆，此时可再确保一下再深一层克隆。
       */
      for (int di = 0; retData != null && di < retData.length; di++) {
	retData[di] = (HashMap) retData[di].clone();
      }
      return retData;
    }
  }

  /**
   * 实时数据上报到缓存的接口
   * @param cacherSign String 缓存标志。
   * @param rowData HashMap[] 要添加的数据集.
   * @param signColName Object  主键列值对名称。 不能为空。
   * @param signColClass Class  主键列值数据类型。 不能为空；必须支持compareTo方法。
   * @throws Exception
   */
  public static void addCacheData(String cacherSign, HashMap[] rowData,
				  Object signColName, Class signColClass) throws
      Exception {
    if (cacherSign == null || cacherSign.length() <= 0) {
      return;
    }

    if (rowData == null || rowData.length <= 0) {
      return;
    }

    synchronized (syncObj) {
      //缓存修改必须并发。
      MonCacheData cacheData = (MonCacheData) DataCachePool.getCacheData(
	  monCacheSignHead + cacherSign);

      if (cacheData == null) {
	cacheData = new MonCacheData(defaultMaxRow, signColName, signColClass);
      }

      //缓存数据修改。
      cacheData.addData(rowData);
      DataCachePool.setCacheData(monCacheSignHead + cacherSign, cacheData);
    }
  }

  /**
   * @param cacherSign String
   */
  public static void freeCacheData(String cacherSign) {
    synchronized (syncObj) {
      DataCachePool.freeCacheData(monCacheSignHead + cacherSign);
    }
  }
}
