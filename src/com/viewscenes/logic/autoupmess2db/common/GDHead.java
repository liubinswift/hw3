package com.viewscenes.logic.autoupmess2db.common;



import java.util.*;

import com.viewscenes.pub.*;



/**

 * �õ�ָ�����GDSet���б���ṹ

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class GDHead {

  private final static Map _colMap = new HashMap(); //�б�������ͱ����Ĺ�ϵmap

  //�㲥����������ʷ��ѯ���б���  RADIO_QUALITY_ALARM_TAB
  private final static String[] radioqualityalarmhistoryCol = {
      "equ_code", "origin_alarmid", "frequency", "type", "description",
      "reason", "e_level", "band",
      "am_modulation","fm_modulation", "attenuation", "head_code", "is_resume",
      "alarm_datetime","resume_datetime"};

  private final static String[] receivercontrolreportCol = {
      "equ_code","url","param","check_datetime","head_id"};//���ջ������ϱ�����(����)
  
  private final static String[] RADIO_EQU_ALARM_TAB_COL = {
      "HEAD_CODE", "EQU_CODE", "ORIGIN_ALARMID", "ALARM_DATETIME",
      "RESUME_DATETIME",
      "IS_RESUME", "TYPE", "DESCRIPTION", "REASON", "OUTPUTLINELEVEL",
      "INPUTLINELEVEL", "LINEFREQUENCY", "BATTERYLEVEL", "UPSSTATUS","CENTER_ID"
  }; // �豸�����ϱ����ݣ����룩

  private final static String[] RADIO_EQU_ALARM_TAB_COL1 = {
      "ALARM_ID", "RESUME_DATETIME", "IS_RESUME"
  }; // �豸�����ϱ����ݣ��޸ģ�
  static {

    _colMap.put("radio_quality_alarm_tab", radioqualityalarmhistoryCol);
    _colMap.put("radio_receiver_realtime_tab", receivercontrolreportCol);
    _colMap.put("RADIO_EQU_ALARM_TAB", RADIO_EQU_ALARM_TAB_COL);
    _colMap.put("RADIO_EQU_ALARM_TAB1", RADIO_EQU_ALARM_TAB_COL1);
  }



  /**

   * �õ����ú��б����GDSet

   * @param tab  ����

   * @return   GDSet

   * @throws GDSetException

   */

  public static GDSet getGdHead(String tab) throws GDSetException {

    String[] col = (String[]) _colMap.get(tab);

    return new GDSet(tab, col);

  }

  public static GDSet getGdHead(String tab, String real_name) throws
  GDSetException {
	String[] col = (String[]) _colMap.get(tab);
	return new GDSet(real_name, col);
}

}
