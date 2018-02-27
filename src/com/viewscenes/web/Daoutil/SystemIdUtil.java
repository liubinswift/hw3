package com.viewscenes.web.Daoutil;

import java.sql.*;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;



/**
 * 当前系统id判断类。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class SystemIdUtil {
 public static String CENTER_ID = null;
 //用来生成任务id时使用。
 public static String CENTER_ID_FORTASK = null;
  public static String CENTER_NAME=null;
  public static String CENTER_CODE=null;
  public SystemIdUtil() {
  }

  /**
   * 判断本前系统是否配置成数据中心系统。
   * @return boolean
   */
  public static boolean isCenter() {
    boolean isCenter = true;
    String locCenterId = getLocalCenterId();
    if (!"100".equalsIgnoreCase(locCenterId)) {
      isCenter = false;
    }
    return isCenter;
  }

  /**
   * center_id是100，运行在数据中心。
   * 其它表明是直属台系统。
   * @return String
   */
  public synchronized static String getLocalCenterId() {
    if (CENTER_ID == null) {

      String task =
          "select param_value from  sys_configuration_tab where param_name='local_center_id' ";
      GDSet result = null;
      try {
        result = DbComponent.Query(task);
        if (result.getRowCount()>0) {
          CENTER_ID = result.getString(0,"param_value");
        }
      }
      catch (Exception ex2) {
        System.out.println("查询系统所属台id信息出错！");
        ex2.printStackTrace();
      }

    }
    return CENTER_ID;
  }
  /**
   * 取得当前数据库连接的直属台名字
   * @return
   */
  public synchronized static String getLocalCenterName() {
      if (CENTER_NAME == null) {

        String task =
            "select name,center_id,code from res_center_tab t where t.center_id= (select param_value from  sys_configuration_tab where param_name='local_center_id') ";
        GDSet result = null;
        try {
          result = DbComponent.Query(task);
          if (result.getRowCount()>0) {
            CENTER_ID = result.getString(0,"center_id");
            if ("100".equalsIgnoreCase(CENTER_ID)) {
            	CENTER_ID_FORTASK="1000";
              }else
              {
            	  CENTER_ID_FORTASK=CENTER_ID;
              }
            CENTER_NAME = result.getString(0,"name");
            CENTER_CODE = result.getString(0,"code");
          }
        }
        catch (Exception ex2) {
          System.out.println("查询系统所属台id信息出错！");
          ex2.printStackTrace();
        }

      }
      return CENTER_NAME;
    }
  public static void main(String[] args) {
    SystemIdUtil systemIdUtil1 = new SystemIdUtil();
  }

}
