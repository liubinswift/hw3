package com.viewscenes.logic.autoupmess2db.MessProcess;



import java.sql.*;
import java.util.*;

import org.jdom.*;
import com.viewscenes.dao.*;
import com.viewscenes.dao.database.*;
import com.viewscenes.logic.autoupmess2db.Exception.*;
import com.viewscenes.logic.autoupmess2db.common.*;
import com.viewscenes.pub.*;
import com.viewscenes.util.*;



/**

 * 质量指标上报

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class TvSignalUpMess2DB

    implements IUpMsgProcessor {

  Element _root = null;

  static final Map _typeMap = new HashMap(); //类型的map

  static {

    _typeMap.put("1", "value1"); // 图像载波

    _typeMap.put("2", "value2"); // 伴音载波

    _typeMap.put("3", "value3"); // 图像伴音比

    _typeMap.put("4", "value4"); // 载噪比

    _typeMap.put("5", "value5"); // 频偏

    _typeMap.put("6", "value6"); // 斜率

  }



  public void processUpMsg(Element root) throws SQLException, UpMess2DBException,

      GDSetException, DbException, UtilException, NoRecordException {

    _root = root;

    signalUp2DB(root);

  }



  /**

   * <pre>

   * 质量指标上报入库

   *

   * 说明:

   *   对应设备XML : QualityIndexReport.xml

   *   对应数据库表 : mon_signal_tab

   *   1. 根据报警的频道code检查报警的频道是否存在,如果不存在,则此条报警不作处理

   *   2. 把上报的指标按顺序直接存入到数据库中

   * </pre>

   * @param root

   * @throws SQLException

   * @throws DbException

   * @throws UpMess2DBException

   */

  private void signalUp2DB(Element root) throws SQLException, DbException,

      UpMess2DBException, GDSetException, UtilException, NoRecordException {


    String headCode = root.getAttributeValue("SrcCode");
    //String headid = TVServiceAPI.getHeadInfoByCode(headCode,"head_id");

//    if (headid==null){
//      LogTool.warning("数据库中找不到code为"+headCode+"的前端");
//      return;
//    }

    Element ele = root.getChild("qualityreport");
    setSignal2DB(ele);

  }

  /**
   * 设置质量指标的数据
   * @param ele
   * @param gd
   * @param headid
   * @throws GDSetException
   */

  private void setSignal2DB(Element ele) throws

      GDSetException, UtilException, DbException {

    List list = ele.getChildren();


    for (int i = 0; i < list.size(); i++) {

        Element sub = (Element) list.get(i);

        String chCode =  sub.getAttributeValue("chcode");

        if(chCode==null){
            chCode =  sub.getAttributeValue("channelcode");
        }

        String taskid =  sub.getAttributeValue("taskid");

        String val1 = "0";
        String val2 = "0";
        String val3 = "0";
        String val4 = "0";
        String val5 = "0";
        String val6 = "0";

        List sublist = sub.getChildren();

        for (int j = 0; j < sublist.size(); j++) {

          Element scanResult = (Element) sublist.get(j);

          String value = scanResult.getAttributeValue("value"); // 指标值

          String type = (String) _typeMap.get(scanResult.getAttributeValue(
              "type"));

            if(type.equals("value1")){
                val1 = value.equals("") ? "0" : value;
            }else if(type.equals("value2")){
                val2 = value.equals("") ? "0" : value;
            }else if(type.equals("value3")){
                val3 = value.equals("") ? "0" : value;
            }else if(type.equals("value4")){
                val4 = value.equals("") ? "0" : value;
            }else if(type.equals("value5")){
                val5 = value.equals("") ? "0" : value;
            }else if(type.equals("value6")){
                val6 = value.equals("") ? "0" : value;
            }
        }
        String sql = "insert into task_tv_quality_data_tab (task_id,ch_code,"
                     +"check_time,value1,value2,value3,value4,value5,value6) "
                     +" values("+taskid+",'"+chCode+"',to_date('"+sub.getAttributeValue("checktime")
                     +"','yyyy-MM-dd HH24:MI:SS'),"+val1+","+val2+","+val3+","+val4+","+val5+","+val6+")";
        DbComponent.exeUpdate(sql);
    }
  }
}
