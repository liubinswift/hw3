package com.viewscenes.cache.mon_cache;

import java.io.*;
import java.util.*;

import java.lang.reflect.*;
import com.viewscenes.cache.util.CacheUtil;

/**
 *
 * <p>Title: ������ϸ��Ϣ���� </p>
 * <p>Description: ���ڷ�װʵʱ���ݼ����������飬���ṩ��Ӧ��ά���ӿڡ�</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: novel_tongfang</p>
 * @author lixuefeng
 * @version 1.0
 */
public class MonCacheData
    implements Serializable {
  private int maxRow = 600; //�������������.
  private HashMap[] dataCache = null; //���ݻ���
  private int dataSize = 0; //ʵ����������

  private Object signColName = null; //��������
  private Method compareToMethod = null; // �����������͵�comareTo������

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
   * ���ݱ�־��ȡ��Ӧ�������ݣ���������־����
   * @param lastRowSign Object
   * @param getNums int
   * @return HashMap[]
   */
  public HashMap[] getData(Object lastRowSign) throws Exception {
    //����������⴦�������ݣ���־��Ϊ��ʱ����󻹴�ȣ�
    if (dataSize <= 0 ||
	(lastRowSign != null && compare(lastRowSign, dataSize - 1) >= 0)) {
      return null;
    }

    //ȫ��������⴦��(��־Ϊ�գ�����С��С)
    if (lastRowSign == null || compare(lastRowSign, 0) < 0) {
      HashMap[] retData = new HashMap[dataSize];
      System.arraycopy(dataCache, 0, retData, 0, dataSize);
      return retData;
    }

    /**
     * ���ö��ֲ��ң��ҵ��ʹӺ��濪ʼ�᣻
     * �Ҳ����ʹ�����λ��ʼ���ء�
     */
    int bottomPos = 0;
    int upPos = dataSize - 1;
    int midPos = 0; //�м�λ������¼���շ���λ��

    while (true) {
      midPos = (bottomPos + upPos) / 2;

      int compResult = compare(lastRowSign, midPos);
      if (compResult == 0) {
	//midPos+1����Ҫ�����ݵĿ�ʼ��
	//��Ϊǰ��ķ��ش������Ա�Ȼ��dataSize-1��
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
	//������Ϊ���ؿ�ʼ��
	midPos = bottomPos;
	break;
      }
    }

    //��Ϊǰ�����󷵻ش�����ʱmoveNums�ش���0.
    int moveNums = dataSize - midPos;
    HashMap[] retData = new HashMap[moveNums];
    System.arraycopy(dataCache, midPos, retData, 0, moveNums);
    return retData;
  }

  /**
   * ��������ݡ�Ĭ�����Ѿ��ź���������ݡ�
   * @param rowData HashMap[] Ҫ��ӵ������ݼ������ź���ġ�
   */
  public void addData(HashMap[] newData) throws Exception {
    /**
     * ������ռ䲻����ʱ��
     */
    if (newData.length + dataSize > maxRow) {
      //���1/4����
      int delLength = Math.max(maxRow / 4, newData.length);
      //��ֹ��������������
      delLength = Math.min(delLength, dataSize);
      CacheUtil.delArrayData(dataCache, 0, delLength);
      dataSize -= delLength;
    }

    /**
     * ����������:
     * �������ϱ�ʱ��������ظ��ϱ��������
     * �����������ʱ���ȴӻ����в鵽�ȵ�һ������С�£���ģ�
     * ֱ���ҵ�С��ͣ��ɾ�����������С�
     */
    //����ɾ���ظ�λ��
    int delDuPos = dataSize - 1;
    Object newMinSign = newData[0].get(signColName);

    for (delDuPos = dataSize - 1; delDuPos >= 0; delDuPos--) {
      //����������С��С��������
      if (compare(newMinSign, delDuPos) > 0) {
	break;
      }
    }

    if(delDuPos<0){//add by zhaoyahui 2010/3/15
        delDuPos=0;
    }
    //ɾ��ԭ���ظ��ľ�����.
    if (delDuPos < dataSize - 1) {
      CacheUtil.delArrayData(dataCache, delDuPos, dataSize - delDuPos);
      dataSize -= dataSize - delDuPos;
    }

    /**
     * ���������ݡ�
     */
    int newStart = Math.max(0, newData.length - maxRow);
    System.arraycopy(newData, newStart, dataCache, dataSize,
		     newData.length - newStart);
    dataSize += newData.length - newStart;
  }

  /**
   * ����Ƚ����򻯡�
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
