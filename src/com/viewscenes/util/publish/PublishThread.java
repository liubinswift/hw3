package com.viewscenes.util.publish;





import com.viewscenes.dao.*;

import com.viewscenes.dao.database.*;

import com.viewscenes.pub.*;

import com.viewscenes.util.*;



public  class PublishThread extends Thread {

  String publishPath = null;

  int interval = 30000;

  boolean bRun = true;

  public PublishThread() {

  }



  public void run(){



    while(bRun){



      try {

        GDSet head_set = loadHeadAlarmCount();

        GDSet center_set = loadCenterAlarmCount();

        String content = getAlarmContent(head_set, center_set);

        FileTool.updateFile(publishPath, content);

      }

      catch (UtilException ex) {

        LogTool.debug(ex);

      }



      try {

        sleep(interval);

      }

      catch (InterruptedException ex1) {

      }

    }

  }



  public void setInterval(int interval){

    this.interval = interval;

  }



  public void setPublishPath(String publishPath){

    this.publishPath = publishPath;

  }



  public void setRun(boolean run){

    bRun = run;

  }



  public static String getAlarmContent(GDSet head_set, GDSet center_set){

    StringBuffer buffer = new StringBuffer();

    buffer.append("<html>\r\n");

    buffer.append("<head>\r\n");

    buffer.append("<script>\r\n");

    buffer.append("var headAlarmCounts = new Array();\r\n");

    buffer.append("var centerAlarmCounts = new Array();\r\n");

    for (int i=0;i<head_set.getRowCount();i++){

      try {

        buffer.append("var headAlarm = new Object();\r\n");

        buffer.append("headAlarm.head_id='");

        buffer.append(head_set.getString(i, "head_id"));

        buffer.append("';\r\n");

        buffer.append("headAlarm.gid='");

        buffer.append(head_set.getString(i, "gid"));

        buffer.append("';\r\n");

        buffer.append("headAlarm.name='");

        buffer.append(head_set.getString(i, "name"));

        buffer.append("';\r\n");

        buffer.append("headAlarm.signal_alarm_count='");

        buffer.append(head_set.getString(i, "signal_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("headAlarm.security_alarm_count='");

        buffer.append(head_set.getString(i, "security_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("headAlarm.radio_alarm_count='");

        buffer.append(head_set.getString(i, "radio_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("headAlarm.total_alarm_count='");

        buffer.append(head_set.getString(i, "total_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("headAlarmCounts[headAlarmCounts.length] = headAlarm;\r\n");

      }

      catch (GDSetException ex) {

      }

    }



    for (int i=0;i<center_set.getRowCount();i++){

      try {

        buffer.append("var centerAlarm = new Object();\r\n");

        buffer.append("centerAlarm.center_id='");

        buffer.append(center_set.getString(i, "center_id"));

        buffer.append("';\r\n");

        buffer.append("centerAlarm.gid='");

        buffer.append(center_set.getString(i, "gid"));

        buffer.append("';\r\n");

        buffer.append("centerAlarm.name='");

        buffer.append(center_set.getString(i, "name"));

        buffer.append("';\r\n");

        buffer.append("centerAlarm.signal_alarm_count='");

        buffer.append(center_set.getString(i, "signal_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("centerAlarm.security_alarm_count='");

        buffer.append(center_set.getString(i, "security_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("centerAlarm.radio_alarm_count='");

        buffer.append(center_set.getString(i, "radio_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("centerAlarm.total_alarm_count='");

        buffer.append(center_set.getString(i, "total_alarm_count"));

        buffer.append("';\r\n");

        buffer.append("centerAlarmCounts[centerAlarmCounts.length] = centerAlarm;\r\n");

      }

      catch (GDSetException ex) {

      }

    }

    buffer.append("function bodyInit(){\r\n");

    buffer.append("    if (parent.refreshNavigation)\r\n");

    buffer.append("        parent.refreshNavigation();\r\n");

    buffer.append("}\r\n");

    buffer.append("</script>\r\n");

    buffer.append("</head>");

    buffer.append("<body>");

    buffer.append("<body onload=bodyInit()>");

    buffer.append("</html>");

    return buffer.toString();

  }



  public static GDSet loadHeadAlarmCount(){

    String sql = "select head_id,name,gid from mon_headend_tab"

        +" order by head_id";

    GDSet headSet = loadDataBySql(sql);

    sql = "select count(*) as count,head_id from mon_signal_alarm_tab"

        +" where isresumed=0 group by head_id order by head_id";

    GDSet headSignalSet = loadDataBySql(sql);

    sql = "select count(*) as count,head_id from mon_abnormity_tab"

        +" where isresumed=0 group by head_id order by head_id";

    GDSet headSecuritySet = loadDataBySql(sql);

    sql = "select count(*) as count,head_id from mon_radio_alarm_tab"

        +" where isresumed=0 group by head_id order by head_id";

    GDSet headRadioSet = loadDataBySql(sql);

    headSet.addColumn("signal_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    headSet.addColumn("security_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    headSet.addColumn("radio_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    headSet.addColumn("total_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    int signalCount = 0;

    int securityCount = 0;

    int radioCount = 0;

    for (int i=0;i<headSet.getRowCount();i++){

      try {

        String head_id = headSet.getString(i, "head_id");

        if ((signalCount<headSignalSet.getRowCount())

          &&(head_id.equals(headSignalSet.getString(signalCount,"head_id")))){

              headSet.setString(i,"signal_alarm_count",headSignalSet.getString(signalCount,"count"));

              signalCount++;

        }

       else{

              headSet.setString(i,"signal_alarm_count","0");

        }

        if ((securityCount<headSecuritySet.getRowCount())

           &&(head_id.equals(headSecuritySet.getString(securityCount,"head_id")))){

              headSet.setString(i,"security_alarm_count",headSecuritySet.getString(securityCount,"count"));

              securityCount++;

          }

        else{

              headSet.setString(i,"security_alarm_count","0");

        }

        if ((radioCount<headRadioSet.getRowCount())

           &&(head_id.equals(headRadioSet.getString(radioCount,"head_id")))){

              headSet.setString(i,"radio_alarm_count",headRadioSet.getString(radioCount,"count"));

              radioCount++;

          }

        else{

              headSet.setString(i,"radio_alarm_count","0");

        }

        long lSignalCount = Long.parseLong(headSet.getString(i,"signal_alarm_count"));

        long lSecurityCount = Long.parseLong(headSet.getString(i,"security_alarm_count"));

        long lRadioCount = Long.parseLong(headSet.getString(i,"radio_alarm_count"));

        long totalCount = lSignalCount + lSecurityCount + lRadioCount;

        headSet.setString(i,"total_alarm_count",String.valueOf(totalCount));

      }

      catch (GDSetException ex) {

        ex.printStackTrace();

      }

    }

    return headSet;

  }



  public static GDSet loadCenterAlarmCount(){

    String sql = "select center_id,name,gid from mon_center_tab"

        +" order by center_id";

    GDSet centerSet = loadDataBySql(sql);

    sql = "select count(*) as count,center_id from mon_signal_alarm_tab"

        +" where isresumed=0 group by center_id order by center_id";

    GDSet centerSignalSet = loadDataBySql(sql);

    sql = "select count(*) as count,center_id from mon_abnormity_tab"

        +" where isresumed=0 group by center_id order by center_id";

    GDSet centerSecuritySet = loadDataBySql(sql);

    sql = "select count(*) as count,center_id from mon_radio_alarm_tab"

        +" where isresumed=0 group by center_id order by center_id";

    GDSet centerRadioSet = loadDataBySql(sql);

    centerSet.addColumn("signal_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    centerSet.addColumn("security_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    centerSet.addColumn("radio_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    centerSet.addColumn("total_alarm_count","signal_alarm_count",Column.COLUMN_TYPE_NUMBER);

    int signalCount = 0;

    int securityCount = 0;

    int radioCount = 0;

    for (int i=0;i<centerSet.getRowCount();i++){

      try {

        String center_id = centerSet.getString(i, "center_id");

        if ((signalCount<centerSignalSet.getRowCount())

          &&(center_id.equals(centerSignalSet.getString(signalCount,"center_id")))){

              centerSet.setString(i,"signal_alarm_count",centerSignalSet.getString(signalCount,"count"));

              signalCount++;

        }

       else{

              centerSet.setString(i,"signal_alarm_count","0");

        }

        if ((securityCount<centerSecuritySet.getRowCount())

           &&(center_id.equals(centerSecuritySet.getString(securityCount,"center_id")))){

              centerSet.setString(i,"security_alarm_count",centerSecuritySet.getString(securityCount,"count"));

              securityCount++;

          }

        else{

              centerSet.setString(i,"security_alarm_count","0");

        }

        if ((radioCount<centerRadioSet.getRowCount())

           &&(center_id.equals(centerRadioSet.getString(radioCount,"center_id")))){

              centerSet.setString(i,"radio_alarm_count",centerRadioSet.getString(radioCount,"center_id"));

              radioCount++;

          }

        else{

              centerSet.setString(i,"radio_alarm_count","0");

        }

        long lSignalCount = Long.parseLong(centerSet.getString(i,"signal_alarm_count"));

        long lSecurityCount = Long.parseLong(centerSet.getString(i,"security_alarm_count"));

        long lRadioCount = Long.parseLong(centerSet.getString(i,"radio_alarm_count"));

        long totalCount = lSignalCount + lSecurityCount + lRadioCount;

        centerSet.setString(i,"total_alarm_count",String.valueOf(totalCount));

      }

      catch (GDSetException ex) {

        LogTool.debug(ex);

      }

    }

    return centerSet;

  }





  public static GDSet loadDataBySql(String sql) {

  GDSet dataset = null;

  try {

//    DaoFactory factory = new DaoFactory();
//
//    DbComponent db = (DbComponent) factory.create(DaoFactory.DB_OBJECT);

    dataset = DbComponent.Query(sql);

  }

  catch (DbException ex) {

    LogTool.debug(ex);

  }

  if (dataset==null)

      dataset = new GDSet("");

  return dataset;

}



public static void main(String[] args) {

  GDSet head_set = loadHeadAlarmCount();

  GDSet center_set = loadCenterAlarmCount();

  String content =getAlarmContent(head_set,center_set);

  try {

    FileTool.updateFile("c:\\alarmcount.htm", content);

  }

  catch (UtilException ex) {

    ex.printStackTrace();

  }

}



}
