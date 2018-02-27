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

 * 三满指标入库

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class RadioQualityStatisticsMess2DB

    implements IUpMsgProcessor {

  Element _root = null;

  public void processUpMsg(Element root) throws SQLException,
      UpMess2DBException,
      GDSetException, DbException, UtilException, NoRecordException {

    _root = root;
    qualityStatistics2DB(root);
  }

  /**
   * <pre>
   * 三满指标入库
   *
   * 说明:
   *  对应设备XML : QualityStatisticsReport.xml
   *  对应数据库表 : mon_radio_quality_statistics_tab
   *
   * </pre>
   * @param upMessage: 上报的三满指标
   * @throws UpMess2DBException
   */

  private void qualityStatistics2DB(Element root) throws UpMess2DBException,

      GDSetException, DbException, UtilException, NoRecordException {

    //得到前端相关信息: head_id,head_name,center_id,center_name,parent_center_id;

    GDSet headinfo = Common.getHeadInfoByCode(root.getAttributeValue(

        "srccode"));

    Element e = root.getChild("radioqualitystatisticsreport");

    String StartDateTime = e.getAttributeValue("startdatetime");

    String EndDateTime = e.getAttributeValue("enddatetime");

    List list = e.getChildren();


    for (int i = 0; i < list.size(); i++) {

      try {

        Element channel = (Element) list.get(i);

        //获得报警频道的信息,返回ch_id,ch_name

        GDSet chInfo =

            Common.IsChanExist(root.getAttributeValue("srccode") +

                               channel.getAttributeValue("channelcode"));

        List QualityStatistics = channel.getChildren();

        for (int j = 0; j < QualityStatistics.size(); j++) {

          Element statistics = (Element) QualityStatistics.get(j);

          String type = statistics.getAttributeValue("type");

          List WorkTime = statistics.getChildren();

          for (int k = 0; k < WorkTime.size(); k++) {

            Element work = (Element) WorkTime.get(k);

            GDSet gd = setGDSet(work, headinfo, chInfo, StartDateTime,
                                EndDateTime, type);

            // 保存报警记录到mon_radio_quality_statistics_tab

            DbComponent.Insert(gd, null);
          }
        }

      }

      catch (NoRecordException ex) {

        Common.logElement(ex, _root);

      }

    }

  }

  /**
   * 设置GDSet

   * @param workinfo

   * @param headinfo

   * @param chInfo

   * @param gd

   */

  private GDSet setGDSet(Element workinfo, GDSet headinfo, GDSet chInfo,
                         String StartDateTime, String EndDateTime, String type) throws

      GDSetException, UtilException, DbException {

    GDSet gd = GDHead.getGdHead("task_radio_quality_stat_tab");

    gd.addRow();

    int k = gd.getRowCount() - 1;

    gd.setString(k, "StartDateTime", StartDateTime);

    gd.setString(k, "EndDateTime", EndDateTime);

    // 把3版的报警类型转换成4版的对应类型

    gd.setString(k, "TYPE_ID", type);

    gd.setString(k, "REPORTTIME", StringTool.Date2String(null));

    gd.setString(k, "worktime_index", workinfo.getAttributeValue("index"));

    gd.setString(k, "WorkStartDateTime",
                 workinfo.getAttributeValue("startdatetime"));

    gd.setString(k, "WorkEndDateTime", workinfo.getAttributeValue("enddatetime"));

    gd.setString(k, "HEAD_ID", headinfo.getString(0, "head_id"));

    //gd.setString(k, "CENTER_ID", headinfo.getString(0, "center_id"));

    gd.setString(k, "CH_ID", chInfo.getString(0, "ch_id"));

    //gd.setString(k, "HEAD_NAME", headinfo.getString(0, "head_name"));

    //gd.setString(k, "CENTER_NAME", headinfo.getString(0, "center_name"));

    //gd.setString(k, "CH_NAME", chInfo.getString(0, "ch_name"));

    //gd.setString(k, "PARENT_CENTER_ID",
    //             headinfo.getString(0, "parent_center_id"));

    gd.setString(k, "AverageValue", workinfo.getAttributeValue("averagevalue"));

    //gd.setString(k, "TYPE_NAME", getTypeName(type));

    return gd;

  }

  /**
   * 根据三满指标类型ID获得报警类型名

   * @param typeid

   * @return

   */

  private String getTypeName(String typeid) throws GDSetException, DbException {
   String sql = "select * from info_data_dict_tab where dict_type='"+typeid+"'";

   GDSet type = DbComponent.Query(sql);
    return type.getString(0, "item_name");

  }

}

