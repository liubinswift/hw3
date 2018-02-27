package com.viewscenes.cache.util;

import java.lang.reflect.*;

/**
 *
 * <p>Title: �����ģ��Ҫ�õ��Ĺ����ࡣ</p>
 * <p>Description: �ṩ�ؼ�������ֵ�Ƚϣ�����ɾ���ȷ�����</p>
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
   * ��ָ��������ɾ����Ӧ���ݡ�
   * @param needDeledArray Object[]
   * @param delPos int
   * @param delNums int
   * @return int ʵ��ɾ����������
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
     * �ƶ��������ݵ�ɾ��λ��
     */
    int movePos = delPos + delNums;
    int moveNums = needDeledArray.length - movePos;
    if (moveNums > 0) {
      System.arraycopy(needDeledArray, movePos, needDeledArray, delPos,
		       moveNums);
    }

    /**
     * �ͷź����ڴ����ݡ�
     */
    for (int delI = delPos + moveNums; delI < needDeledArray.length; delI++) {
      needDeledArray[delI] = null; //Let gc to do works.
    }
  }

  /**
   * �ȽϹؼ�������ֵ�ķ��������ùؼ�������ֵ���ͣ�������compareTo������ȡ�ý����
   * �Ѿ������ˣ�ת�������౾���ˡ�
   * @param obj1 Object
   * @param obj2 Object
   * @throws Exception
   * @return int ��obj1��0�ȣ���obj1С��
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
      throw new Exception(methodMessage + "����" + objectClass.getName() +
			  "��֧��compareTo(thisClassObject)����!", ex);
    }
    catch (IllegalArgumentException ex) {
      throw new Exception(methodMessage + "��������ؼ�ֵ�ֶβ���" + objectClass.getName() +
			  "���͵Ķ���!", ex);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(methodMessage + "����" + ex.getMessage());
    }
    return compResult;
  }

}
