/*
 * Created on 2007-2-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.viewscenes.logic.autoupmess2db.MessProcess;

import java.sql.*;
import java.util.*;

import org.jdom.*;

import com.viewscenes.dao.database.*;
import com.viewscenes.logic.autoupmess2db.Exception.*;
import com.viewscenes.logic.autoupmess2db.common.*;
import com.viewscenes.pub.*;
import com.viewscenes.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskSchedulerAlarm2DB  implements IUpMsgProcessor {

        Element _root = null;

          public void processUpMsg(Element root) throws SQLException, UpMess2DBException,
              GDSetException, DbException, UtilException, NoRecordException {

            _root = root;
            RadioTaskToDB(root);
          }


private void RadioTaskToDB(Element root) throws UpMess2DBException,GDSetException, DbException, UtilException,NoRecordException {

  Element e = root.getChild("RecordTaskAlarmReport");

  Collection tasks = e.getChildren();

  for (Iterator tasksit = tasks.iterator(); tasksit.hasNext(); ) {

        Element TaskAlarmElement = (Element) tasksit.next();

        if(TaskAlarmElement.getName().equalsIgnoreCase("RecordTaskAlarm")){
                Collection RecordTaskAlarm= TaskAlarmElement.getChildren();

                for(Iterator tasks_1 = RecordTaskAlarm.iterator();tasks_1.hasNext();){
                                Element resultElement = (Element) tasks_1.next();
                                String AlarmID= resultElement.getAttributeValue("AlarmID");
                                String EquCode =resultElement.getAttributeValue("EquCode");
                                String ChCode= resultElement.getAttributeValue("ChCode");
                                String Task_id =resultElement.getAttributeValue("TaskID");
                                String Desc= resultElement.getAttributeValue("Desc");
                                String Reason =resultElement.getAttributeValue("Reason");
                                String StartDateTime= resultElement.getAttributeValue("StartDateTime");
                                String EndDateTime =resultElement.getAttributeValue("EndDateTime");
                                String CheckDateTime= resultElement.getAttributeValue("CheckDateTime");
                                String ReportTime = getFormDate();

                                setGDSet(AlarmID,EquCode,ChCode,Task_id,Desc,Reason,StartDateTime,EndDateTime,CheckDateTime,ReportTime);

                        }


        }
  }
          }

          /**

           * 设置GDSet

           * @param alarm

           * @param headinfo

           * @param chInfo

           * @param gd

           */

private void  setGDSet(String  AlarmID, String EquCode,String ChCode, String Task_id,String Desc,String  Reason,
                                 String StartDateTime, String EndDateTime,String CheckDateTime,String ReportTime) throws

              GDSetException, UtilException, DbException {


            try {
                        GDSet gd = GDHead.getGdHead("alarm_task_radio_tab");

                        gd.addRow();
                        int k = gd.getRowCount() - 1;

                        gd.setString(k, "Alarm_id", AlarmID);
                        gd.setString(k, "Head_id", EquCode);
                        gd.setString(k, "Task_id", Task_id);
                        gd.setString(k, "Ch_code", ChCode);
                        gd.setString(k, "Report_time", ReportTime);

                        gd.setString(k, "Check_time", CheckDateTime);
                        gd.setString(k, "Start_datetime", StartDateTime);
                        gd.setString(k, "End_datetime", EndDateTime);
                        gd.setString(k, "Description", Desc);
                        gd.setString(k, "Reason", Reason);

                } catch (GDSetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                        LogTool.warning("修改数据库失败"+e.getMessage());

                }
          }

public String getFormDate(){
    Date now = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String tdate = df.format(now);
    System.out.println("Today is "+tdate);
    return tdate;
}

}
