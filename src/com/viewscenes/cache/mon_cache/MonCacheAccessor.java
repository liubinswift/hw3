package com.viewscenes.cache.mon_cache;

import java.util.*;

import com.viewscenes.cache.cache_pool.*;
import java.util.HashMap;

/**
 *
 * <p>Title: ʵʱ����ģ�黺�������</p>
 * <p>Description: Ϊʵʱ�����ṩ�Ļ�����ʽӿڣ��ڴ˲�����ṩ�������ơ�</p>
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
   * ʵʱ����������ȡ�Ķ���ӿ�:���ݻ����־���ϴ�����־��������ȡ��־��ȡ���ݡ�
   * ע�ⷵ��HashMap������Ȼ�������飬���ڲ����ݽ���¡һ�㣬�������޸�ʱҪע��.
   * ��ò����ڲ���α�֤��������ʹ��һ��Ҫ�޸���������ʱ����Ҫ��¡�����޸ġ�
   *
   * @param cacherSign String  �����־,��ò�ҪΪ�ա�
   * ���Ϊ�գ����ؽ������Ϊ�գ�
   * Ҳ���ܷ���addʱΪ��null��־��ֵ(���addû�йܵĻ�)��
   * @param lastRowSign Object �ϴΣ�������������־
   * @throws Exception
   * @return HashMap[] ��ͳһ����>�����ݣ���Ҫ=��
   */
  public static HashMap[] getCacheData(String cacherSign, Object lastRowSign) throws
      Exception {
    synchronized (syncObj) {
      //������ȡ�����벢�أ�Ҫ��������š�
      MonCacheData cacheData = (MonCacheData) DataCachePool.getCacheData(
	  monCacheSignHead + cacherSign);

      if (cacheData == null) {
	return null;
      }

      //��ȡ���ء�
      HashMap[] retData = cacheData.getData(lastRowSign);
      /**
       * ���������Ѿ�ǳ���¡����ʱ����ȷ��һ������һ���¡��
       */
      for (int di = 0; retData != null && di < retData.length; di++) {
	retData[di] = (HashMap) retData[di].clone();
      }
      return retData;
    }
  }

  /**
   * ʵʱ�����ϱ�������Ľӿ�
   * @param cacherSign String �����־��
   * @param rowData HashMap[] Ҫ��ӵ����ݼ�.
   * @param signColName Object  ������ֵ�����ơ� ����Ϊ�ա�
   * @param signColClass Class  ������ֵ�������͡� ����Ϊ�գ�����֧��compareTo������
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
      //�����޸ı��벢����
      MonCacheData cacheData = (MonCacheData) DataCachePool.getCacheData(
	  monCacheSignHead + cacherSign);

      if (cacheData == null) {
	cacheData = new MonCacheData(defaultMaxRow, signColName, signColClass);
      }

      //���������޸ġ�
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
