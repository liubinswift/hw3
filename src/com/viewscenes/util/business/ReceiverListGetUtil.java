package com.viewscenes.util.business;

import java.util.*;
import java.sql.*;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;


/**
 * <p>Title:接收机列表提取。 </p>
 * <p>Description: 根据站点类型（采集点还是遥控站），
 * 站点代码（如果有的话，提取相应版本号），音频还是视频</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ReceiverListGetUtil {
  private static int VIDEO_AUDIO_SEP = 200;

  private static Hashtable RECEIVER_CODES = null;
  private static Hashtable RECEIVER_NAMES = null;

  public ReceiverListGetUtil() {
  }

  public static String getCodesByCodeIds(String codeIds) {
    String[] codeIdArray = codeIds.split(",");
    String codes = "";
    for (int i = 0; i < codeIdArray.length; i++) {
      codes += getCodeByCodeId(codeIdArray[i]) + ",";
    }
    return codes.substring(0, codes.length() - 1);
  }

  /**
   * 提取
   * @param codeId String
   * @return String
   */
  public static String getCodeByCodeId(String codeId) {
    if (RECEIVER_CODES == null || RECEIVER_CODES.size() == 0) {
      loadResource();
    }
    if (RECEIVER_CODES != null) {
      return (String) RECEIVER_CODES.get(codeId);
    }
    return null;
  }

  public static String getCodeNamesByNameIds(String nameIds) {
    String[] nameIdArray = nameIds.split(",");
    String names = "";
    for (int i = 0; i < nameIdArray.length; i++) {
      names += getCodeNameByNameId(nameIdArray[i]) + ",";
    }
    return names.substring(0, names.length() - 1);
  }

  /**
   *
   * @param nameId String
   * @return String
   */
  public static String getCodeNameByNameId(String nameId) {
	  
	  if (RECEIVER_NAMES == null || RECEIVER_NAMES.size() == 0) {
      loadResource();
    }
    if (RECEIVER_NAMES != null) {
      return (String) RECEIVER_NAMES.get(nameId);
    }
    return null;
  }

  /**
   * 强制提取接收机名称资源
   */
  public synchronized static void loadResource() {
    RECEIVER_CODES = new Hashtable();
    RECEIVER_NAMES = new Hashtable();


    GDSet codeSt = null;
    GDSet nameSt = null;
    String codeSql =
        "select code_id,CODE from dic_headend_receiver_code_tab ";
    String nameSql =
        "select name_id,name from dic_headend_receiver_name_tab ";
    try {

      codeSt = DbComponent.Query(codeSql);
      nameSt = DbComponent.Query(nameSql);


     for(int i=0;i<codeSt.getRowCount();i++) {
        RECEIVER_CODES.put(codeSt.getString(i,"code_id"), codeSt.getString(i,"CODE"));
      }

        for(int i=0;i<nameSt.getRowCount();i++) {
        RECEIVER_NAMES.put(nameSt.getString(i,"name_id"), nameSt.getString(i,"name"));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }


  /**
   * 提取站点配置文件。
   * @param field String
   * @param headManu String
   * @param headType String
   * @param headVersion String
   * @throws Exception
   * @return String
   */
  public static String getConfigByHeadend(String field, String headManu,
                                          String headType, String headVersion) throws
      Exception {
    String errorMessage = "站点信息：厂商=" + headManu + ",版本=" + headVersion +
        ",站点类型=" + headType;
    //提取配置文件接收机列表。
    String configSql =
        " select " + field + " from DIC_HEADEND_MANUFACTURER_TAB " +
        " where MANUFACTURER_NAME = '" + headManu +
        "' and VERSION = '" + headVersion + "' and HEADEND_TYPE ='" + headType+"' ";

    try {
      GDSet rs = DbComponent.Query(configSql);
      int num = rs.getRowCount();
      if (num <= 0) {
        throw new Exception("提取不到相关配置！");
      }
      else {
        if (num > 1) {
          System.out.println("出现重复配置！");
        }
        return rs.getString(0, 0);
      }
    }
    catch (Exception ex) {
      throw new Exception(errorMessage + "提取站点特性(接收机、电压下限等)配置时出现异常：" +
                          ex.getMessage());
    }
  }

  /**
   * 提取接收机列表的基列。
   * @param headManu String
   * @param headType String
   * @param headVersion String
   * @param isRadio boolean
   * @throws Exception
   */
  public static Vector getReceiverBaseList(String headManu, String headType,
                                           String headVersion, boolean isRadio) throws
      Exception {
    String codeIds = getConfigByHeadend("RECEIVER_CODE_LIST", headManu,
                                        headType, headVersion);

    String nameIds = getConfigByHeadend("RECEIVER_NAME_LIST", headManu,
                                        headType, headVersion);
    Vector recVec = new Vector();
    String[] codeArray = codeIds.split(",");
    String[] nameArray = nameIds.split(",");
    for (int i = 0; i < codeArray.length; i++) {
      //如果取接收机,不能大于要求值,不满足条件返回.
//      if (isRadio && new Integer(codeArray[i]).intValue() >= VIDEO_AUDIO_SEP) {
//        continue;
//      }
//      //如果取摄像头，不能小于要求值,不满足条件返回。
//      else if (!isRadio &&
//               new Integer(codeArray[i]).intValue() < VIDEO_AUDIO_SEP) {
//        continue;
//      }
      String code = getCodeByCodeId(codeArray[i]);
      recVec.add(code);
      recVec.add(code + ":" + getCodeNameByNameId(nameArray[i]));
    }
    return recVec;
  }

  /**
   * 提取接收机列表，加自动选择。
   * @param headManu String
   * @param headType String
   * @param headVersion String
   * @param isRadio boolean
   * @throws Exception
   * @return Vector
   */
  public static Vector getReceiverList(String headManu, String headType,
                                       String headVersion, boolean isRadio) throws
      Exception {
    Vector receiverVector = new Vector();
    receiverVector.add("");
    receiverVector.add("自动选择");
    Vector baseVec = getReceiverBaseList(headManu, headType, headVersion,
                                         isRadio);
    if (baseVec != null && baseVec.size() > 0) {
      receiverVector.addAll(baseVec);
    }
    else {
      throw new Exception("站点未配置相应接收机！");
    }
    return receiverVector;
  }

  public static Vector getReceiverList(String headCode, boolean isRadio) throws
      Exception {
    Vector receiverVector = new Vector();
    receiverVector.add("");
    receiverVector.add("自动选择");
    String headManu = SiteVersionUtil.getSiteManu(headCode);
    String headType = SiteVersionUtil.getSiteType(headCode);
    String headVer = SiteVersionUtil.getSiteVerStr(headCode);
    Vector baseVec = getReceiverBaseList(headManu, headType, headVer,
                                         isRadio);
    if (baseVec != null && baseVec.size() > 0) {
      receiverVector.addAll(baseVec);
    }
    else {
      throw new Exception("站点未配置相应接收机！");
    }
    return receiverVector;
  }

  /**
   * 提取接收机列表，加所有。
   * @param headManu String
   * @param headType String
   * @param headVersion String
   * @param isRadio boolean
   * @throws Exception
   * @return Vector
   */
  public static Vector getReceiverAllList(String headManu, String headType,
                                          String headVersion, boolean isRadio) throws
      Exception {
    //获取接收机列表
    Vector receiverVector = new Vector();
    receiverVector.add("all");
    receiverVector.add("所有");
    Vector baseVec = getReceiverBaseList(headManu, headType, headVersion,
                                         isRadio);
    if (baseVec != null && baseVec.size() > 0) {
      receiverVector.addAll(baseVec);
    }
    else {
      throw new Exception("站点未配置相应接收机！");
    }
    return receiverVector;
  }

  public static String[][] getReceiverParamsArray(String receiverCode) {
    //接收机代码与类型映射。
    if (receiverCode == null) {
      receiverCode = "R1";
    }
    String receiType = "2";
    if (receiverCode.equalsIgnoreCase("R1")) {
      receiType = "1";
    }
    else if (receiverCode.equalsIgnoreCase("R1")) {
      receiType = "3";
    }

    String[][] words = {
        {
        ""}
        , {
        "无"}
    };

    try {
      String sql="select type ,word from dic_receiver_control_word_tab  where type='"+receiType+"' order by word asc";


      GDSet dataTypes = DbComponent.Query(sql);
      int rowTypes = dataTypes.getRowCount();
      if (rowTypes > 0) {
        words = new String[2][rowTypes];
      }
      for (int rti = 0; rti < rowTypes; rti++) {
        String word = dataTypes.getString(rti, "word");
        words[0][rti] = word;
        words[1][rti] = word;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return words;
  }

  public static String[][] getReceiveSortArray(String headCode,
                                               boolean isRadio) throws
      Exception {
    String headManu = SiteVersionUtil.getSiteManu(headCode);
    String headType = SiteVersionUtil.getSiteType(headCode);
    String headVer = SiteVersionUtil.getSiteVerStr(headCode);
    return getReceiveSortArray(headManu, headType, headVer, isRadio);
  }

  /**
   * 根据站点id提取  接收机代码与type名称的排序数组。
   * @param headendid
   * @return Object[] 0为站点类型，1为接收机Code与名称映射表。
   */
  public static String[][] getReceiveSortArray(String headManu,
                                               String headType,
                                               String headVersion,
                                               boolean isRadio) throws
      Exception {

    String[][] recArray = null;
    Vector receiVec = getReceiverBaseList(headManu, headType, headVersion,
                                          isRadio);
    if (receiVec != null && receiVec.size() > 0) {
      recArray = new String[9][2];
      for (int i = 0; i < receiVec.size(); i += 2) {
        String key = (String) receiVec.get(i);
        String keyValue = (String) receiVec.get(i + 1);
        if (keyValue != null && keyValue.length() > 0) {
          if (key.equalsIgnoreCase("R1")) {
            recArray[0][0] = key;
            recArray[0][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R2")) {
            recArray[1][0] = key;
            recArray[1][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R3")) {
            recArray[2][0] = key;
            recArray[2][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R4")) {
            recArray[3][0] = key;
            recArray[3][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R5")) {
            recArray[4][0] = key;
            recArray[4][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R6")) {
            recArray[5][0] = key;
            recArray[5][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R7")) {
            recArray[6][0] = key;
            recArray[6][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R8")) {
            recArray[7][0] = key;
            recArray[7][1] = keyValue;
          }
          else if (key.equalsIgnoreCase("R9")) {
            recArray[8][0] = key;
            recArray[8][1] = keyValue;
          }
        }
      }
    }
    else {
      throw new Exception("没有配置相应接收机");
    }
    return recArray;
  }

  /**
   * 根据站点id提取  接收机代码与type名称的映射表。
   * @param headendid
   * @return Object[] 0为站点类型，1为接收机Code与名称映射表。
   */
  public static HashMap getReceiveCTMap(String headCode, boolean isRadio) throws
      Exception {
    String headManu = SiteVersionUtil.getSiteManu(headCode);
    String headType = SiteVersionUtil.getSiteType(headCode);
    String headVer = SiteVersionUtil.getSiteVerStr(headCode);
    return getReceiveCTMap(headManu, headType, headVer, isRadio);
  }

  /**
   * 根据站点id提取  接收机代码与type名称的映射表。
   * @param headendid
   * @return Object[] 0为站点类型，1为接收机Code与名称映射表。
   */
  public static HashMap getReceiveCTMap(String headManu, String headType,
                                        String headVersion,
                                        boolean isRadio) throws
      Exception {
    String codeIds = getConfigByHeadend("RECEIVER_CODE_LIST", headManu,
                                        headType, headVersion);
    String nameIds = getConfigByHeadend("RECEIVER_NAME_LIST", headManu,
                                        headType, headVersion);

    HashMap recMap = new HashMap();
    String[] codeArray = codeIds.split(",");
    String[] nameArray = nameIds.split(",");
    for (int i = 0; i < codeArray.length; i++) {
      //如果取接收机,不能大于要求值,不满足条件返回.
      if (isRadio && new Integer(codeArray[i]).intValue() >= VIDEO_AUDIO_SEP) {
        continue;
      }
      //如果取摄像头，不能小于要求值,不满足条件返回。
      else if (!isRadio &&
               new Integer(codeArray[i]).intValue() < VIDEO_AUDIO_SEP) {
        continue;
      }

      String code = getCodeByCodeId(codeArray[i]);
      recMap.put(code, code + ":" + getCodeNameByNameId(nameArray[i]));
    }
    return recMap;
  }
}
