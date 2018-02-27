package com.viewscenes.web.device;

import java.sql.*;
import java.util.*;

import org.jdom.Attribute;
import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.business.RecordRunplanInfoUtil;


public class RecordMarkUtil {
  public RecordMarkUtil() {
  }

  /**
   * 打分入库功能类。
   * @param record_location String
   * @param mark_user String
   * @param edit_user String
   * @param mark_datetime String
   * @param head_code String
   * @param equ_code String
   * @param frequency String
   * @param runplan_id String
   * @param fidelity String
   * @param exfidelity String
   * @param interfere String
   * @param description String
   * @param mark_type String
   * @param unit String
   */
  public static void mark(String mark_file_name, String mark_user,
			  String mark_datetime, String head_code,
			  String equ_code, String frequency, String runplan_id,
			  String fidelity, String exfidelity, String interfere,
			  String description, String mark_type, String unit) throws
      Exception {
    /**
     * 根据录音文件，查找是否已经打分。
     */
    Connection conn = null;
    String mark_id = "";
    try {
      String locMarkFindSql =
	  " select mark_id from RADIO_MARK_ZST_TAB where mark_file_name ='"+mark_file_name+"'";
		GDSet gs = null;

		gs = DbComponent.Query(locMarkFindSql);
		if (gs.getRowCount()>0) {
		   mark_id = gs.getString(0,"mark_id");
		}
    }
    catch (DbException ex) {
      throw new Exception("数据库查询异常" + ex.getMessage(), ex);
    }

    /**
     * 插入或者更新打分
     */
    String markCol[] = {
	"mark_id", "mark_user", "edit_user", "mark_datetime", "head_code",
	"equ_code", "frequency", "runplan_id", "fidelity", "exfidelity",
	"interfere", "description", "mark_type", "unit", "mark_file_name"};
    String markValue[] = {
	mark_id, mark_user, mark_user, mark_datetime, head_code,
	equ_code, frequency, runplan_id, fidelity, exfidelity,
	interfere, description, mark_type, unit, mark_file_name};

    GDSet markSet = new GDSet("radio_mark_zst_tab", markCol);
    markSet.addRow(markValue);

    if (mark_id.equalsIgnoreCase("")) {
        DbComponent.Insert(markSet, new long[1]);
    }
    else {
    	DbComponent.Update(markSet);
    }
  }

  /**
   * 打分操作 *
   * @param msg
   * * MARK_ID,MARK_USER,MARK_DATETIME,HEAD_CODE,EQU_CODE,FREQUENCY,RUNPLAN_ID,
   * FIDELITY,EXFIDELITY,INTERFERE,DESCRIPTION,MARK_TYPE,EDIT_USER,
   * UNIT,RECORD_LOCATION
   * @return id 行号
   */
  public static String evaluateCommit(String msg)throws Exception{
  	String responseStr;
      ArrayList<Element> list = new ArrayList<Element>();
      String err = "";

      Element root = StringTool.getXMLRoot(msg);
      //String userId = root.getChildText("user_id");
      String userName = root.getChildText("param_user_name");
      String id = root.getChildText("param_id");
        String frequency = root.getChildText("param_frequency");
        String head_code = root.getChildText("param_headendCode");

        /**
         * 时间转单元.
         */
        String record_startdatetime = (String) root.getChildText(
  	  "param_record_startdatetime");
        String record_enddatetime = (String) root.getChildText(
  	  "param_record_enddatetime");
        String unit = record_startdatetime.substring(11, 13) + ":00:00";

        String runplan_id = root.getChildText("param_runplan_id");
        String task_id = root.getChildText("param_task_id");
        String station_name = root.getChildText("param_stationName"); // 发射台名称
        String program_name = root.getChildText("param_program_name"); // 节目名称

        /**
         * 重新弥补一下运行图信息。
         */
	    if (runplan_id == null || runplan_id.trim().length() <= 0) {
	    	String[] runInfo = new RecordRunplanInfoUtil().getRunplanInfo(null,
	  	    task_id, head_code, frequency, record_startdatetime,
	  	    record_enddatetime);
		  	station_name = runInfo[0];
		  	program_name = runInfo[1];
		  	runplan_id = runInfo[3];
        }

        /**
         * 录音文件转名称。
         */
        String record_loc = ( (String) root.getChildText("param_record_location"));
        String mark_file_name = record_loc.substring(
  	  record_loc.lastIndexOf("/") + 1, record_loc.lastIndexOf("."));

        String fidelity = root.getChildText("param_fidelity"); // 播出效果
        String interfere[] = root.getChildText("param_interfere").split(","); // 干扰类型
        String exfidelity = root.getChildText("param_expfidelity"); // 实验效果
        if (exfidelity == null) {
      	  exfidelity = "";
        }

        String mark_type = root.getChildText("param_markType");

        //用户信息
        String mark_user = userName;
        if (mark_user == null) {
      	  mark_user = "";
        }

        String interfereStr = "";
        if (interfere != null) {
	    	for (int i = 0; i < interfere.length; i++) {
	    	  interfereStr += interfere[i];
	    	}
        }

        if (fidelity != null) {
	    	// 实验效果字符串
	    	String markstr = fidelity;
	    	if (exfidelity.length() > 0 || interfereStr.length() > 0) {
	    	  markstr += "/" + exfidelity + interfereStr;
	    	}

	    	String equ_code = "";
	    	//打分入库
	    	mark(mark_file_name, mark_user, record_enddatetime, head_code,
	    	     equ_code, frequency, runplan_id, fidelity, exfidelity,
	    	     interfereStr, markstr, mark_type, unit);
        }
      return id;
  }

  public static void main(String[] args) {

  }
}
