package com.viewscenes.logic.autoupmess2db.MessProcess.v8;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.dao.database.DbComponent.DbQuickExeSQL;
import com.viewscenes.logic.autoupmess2db.Exception.NoRecordException;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.IUpMsgProcessor;
import com.viewscenes.logic.autoupmess2db.common.GDHead;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;
import com.viewscenes.web.common.Common;
/**
 * 广播设备报警数据入库
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioEquipmentAlarmHistoryReport implements IUpMsgProcessor {
    private String srcCode;
    public RadioEquipmentAlarmHistoryReport() {
    }

    public void processUpMsg(Element root) throws SQLException,
        UpMess2DBException, GDSetException, DbException, UtilException,
    	NoRecordException {
    	java.util.Date start = Calendar.getInstance().getTime();
    	
        getQualityAlarmGDSet(root);
        
        java.util.Date end = Calendar.getInstance().getTime();
        LogTool.debug("设备报警数数据上报入库花时：" + (end.getTime() - start.getTime()));
    }

    /**
     * 得到广播设备报警上报数据
     * @param root
     * @return GDSet
     * @throws GDSetException
     * @throws SQLException
     * @throws DbException
     * @throws UpMess2DBException
     */
    private synchronized void getQualityAlarmGDSet(Element root) throws
      GDSetException, SQLException, NoRecordException, DbException, UpMess2DBException {
        
        this.srcCode = root.getAttributeValue("SrcCode");
        String sitetype = SiteVersionUtil.getSiteType(srcCode);
        ArrayList<EquipmentAlarmHistoryReportBean> list = new ArrayList<EquipmentAlarmHistoryReportBean>();
        if(sitetype.equals("103")){//边境站
            list = getDataFromRootFrontier(root);
        }
        else{
            
    //      int version = Integer.parseInt(root.getAttributeValue("Version"));
    //      switch (version) {
    //      case 8:
                list = getDataFromRootV8(root);
    //        break;
      //
    //      default:
    //        break;
    //      }
        }
//        String queryCenterId = "select c.center_id from res_center_tab c,res_headend_tab h " +
//        		"where c.center_id=h.center_id and h.is_delete=0 and h.code='"+srcCode+"'";
//        GDSet gdc = DbComponent.Query(queryCenterId);
//        String centerID = "";
//        if (gdc.getRowCount()>0) {
//            centerID = gdc.getString(0, "center_id");
//        }
        try {
            setRadioGD(list, srcCode);
        } catch (Exception e) {
            LogTool.fatal("autoup2mess",e);
            throw new UpMess2DBException("",e);
        }
        
    
//        DbComponent.Insert(insertGd, null);
//        DbComponent.Update(updGd);
   
    }

    /**
     * 设置广播设备报警GDSet的数据
     * RADIO_EQUIPMENT_ALARM_TAB
     * 02_QualityRealtimeReport.xml
     * @param ele
     * @param gd               报警插入
     * @param gd1              恢复报警更新
     * @param headid
     * @throws GDSetException
     */
    private void setRadioGD(ArrayList<EquipmentAlarmHistoryReportBean> list,
			String head_code) throws Exception,
			UpMess2DBException {

    	String sql =
			  "insert into radio_equ_alarm_tab(alarm_id, equ_code,origin_alarmid,alarm_datetime,is_resume,type,description,reason," +
			  "outputlinelevel,inputlinelevel,linefrequency,batterylevel,upsstatus,head_code,head_id) " +
			  "values(RADIO_ALARM_SEQ.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	 DbComponent db = new DbComponent();
 		DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
 		prepExeSQL.getConnect();
        for (int x = 0; x < list.size(); x++) {
            EquipmentAlarmHistoryReportBean bean = list.get(x);
            String headid = Common.getHeadendByCode(head_code, "AB").getHead_id();
		    String equcode = bean.getEqucode(); // 获得equcode
			String alarmid = bean.getAlarmid(); // 获得alarmid
			String mode = bean.getMode();
			String type = bean.getType();
			String desc = bean.getDesc();
			String reason = bean.getReason();
			String checkdatetime = bean.getCheckdatetime();
//			checkData(equcode,alarmid,"centerID",centerID);
		    checkData(equcode,alarmid,"equcode",equcode);
		    checkData(equcode,alarmid,"alarmid",alarmid);
		    checkData(equcode,alarmid,"mode",mode);
		    checkData(equcode,alarmid,"type",type);
//			if (reason == null||reason.equals("")){
//                throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode+";alarmid=" + alarmid + "广播设备报警上报数据缺少必要元素:reason="+reason);
//            }
		    checkData(equcode,alarmid,"checkdatetime",checkdatetime);
			String outputlinelevel = bean.getOutputlinelevel();
			String inputlinelevel = bean.getInputlinelevel();
			String linefrequency = bean.getLinefrequency();
			String batterylevel = bean.getBatterylevel();
			String upsstatus = bean.getUpsstatus();
			if (type.equals("1")) {
		        checkData(equcode,alarmid,"outputlinelevel",outputlinelevel);
		        checkData(equcode,alarmid,"inputlinelevel",inputlinelevel);
		        checkData(equcode,alarmid,"linefrequency",linefrequency);
		        checkData(equcode,alarmid,"batterylevel",batterylevel);
		        checkData(equcode,alarmid,"upsstatus",upsstatus);
			}

			// 检查报警是否已经入库(对于恢复报警，检查是否已经恢复)
			StringBuffer alarm_id_buf = new StringBuffer("");
			boolean isExist = checkAlarmExist(alarmid, head_code,
					alarm_id_buf);
			String alarm_id = alarm_id_buf.toString();

			if (!isExist) { // 报警不存在时才入库
				//if (mode.equalsIgnoreCase("0")) { // 报警
//					if (mode.equals("0")) {
//					    gd.setString(k, "alarm_datetime", checkdatetime);
//                    } else {
//                        gd.setString(k, "resume_datetime", checkdatetime);
//                    }

					prepExeSQL.setString(1, equcode);
					prepExeSQL.setString(2, alarmid);
					prepExeSQL.setString(3, checkdatetime);
					prepExeSQL.setString(4, mode);
					prepExeSQL.setString(5, type);
					prepExeSQL.setString(6, desc);
					prepExeSQL.setString(7, reason);
					prepExeSQL.setString(8, outputlinelevel);
					prepExeSQL.setString(9, inputlinelevel);
					prepExeSQL.setString(10, linefrequency);
					prepExeSQL.setString(11, batterylevel);
					prepExeSQL.setString(12, upsstatus);
					prepExeSQL.setString(13, head_code);
					prepExeSQL.setString(14, headid);
					prepExeSQL.exeSQL();

			} else {// 已经存在库中那么修改，如果是报警修改报警时间，如果是解除报警修改
                String sqlUpdate = "";
				if (mode.equalsIgnoreCase("1")) {

					sqlUpdate = "update radio_equ_alarm_tab set is_resume=1, resume_datetime=to_date('"
							+ checkdatetime
							+ "', 'YYYY-MM-DD HH24:MI:SS') where alarm_id = "
							+ alarmid
							+ " and head_code = '"
							+ head_code
							+ "'";

				}
				if (mode.equalsIgnoreCase("0")) {
					sqlUpdate = "update radio_equ_alarm_tab set alarm_datetime=to_date('"
							+ checkdatetime
							+ "', 'YYYY-MM-DD HH24:MI:SS') " +
							" ,resume_datetime = '' where alarm_id = "
							+ alarmid
							+ " and head_code = '"
							+ head_code
							+ "'";
				}
				DbComponent.exeUpdate(sqlUpdate);
            }
		}
	}

    /**
     * 检查报警是否已经入库；恢复报警是否已经恢复
     * @param alarmid      报警ID
     * @param mode      0－报警，1－恢复报警
     * @param alarm_id     如果找到原报警，则返回报警ID
     * @return
     * @throws UpMess2DBException
     */
//    boolean checkAlarmExist(String alarmid, String mode, String head_code,
//			  StringBuffer alarm_id) throws UpMess2DBException {
//        String sql = "";
//        try {
//            if (mode.equalsIgnoreCase("0")) { // 报警，检查是否已有报警
//                sql = "select alarm_id from radio_equ_alarm_tab where origin_alarmid="
//                        + alarmid
//                        + " and is_resume=0 and head_code='"
//                        + head_code + "'";
//
//                GDSet gd = DbComponent.Query(sql);
//                if (gd.getRowCount() > 0) {
//                    LogTool.debug("设备报警重复。id=" + alarmid);
//                    return true;
//                } else {
//                    return false;
//                }
//            } else if (mode.equalsIgnoreCase("1")) { // 恢复报警，检查是否已有报警并且未恢复
//                sql = "select alarm_id,is_resume from radio_equ_alarm_tab where origin_alarmid="
//                        + alarmid
//                        + " and head_code='"
//                        + head_code + "'";
//
//                GDSet gd1 = DbComponent.Query(sql);
//                if (gd1.getRowCount() > 0) {
//                    String isResume = gd1.getString(0, "is_resume");
//                    alarm_id.append(gd1.getString(0, "alarm_id"));
//                    if (isResume.equalsIgnoreCase("1")) {
//                        LogTool.debug("设备报警恢复重复。id=" + alarm_id);
//                        return true;
//                    } else {
//                        return false;
//                    }
//                } else {
//                    return true;
//                }
//            } else {
//                throw new UpMess2DBException("报警mode不正确。 mode=" + mode
//                        + "||head_code=" + head_code);
//            }
//        } catch (DbException ex) {
//            throw new UpMess2DBException("", ex);
//        } catch (GDSetException ex) {
//            throw new UpMess2DBException("", ex);
//        }
//  }
  
    /**
     * 检查报警是否已经入库；恢复报警是否已经恢复
     * @param alarmid      报警ID
     * @param alarm_id     如果找到原报警，则返回报警ID
     * @return
     * @throws UpMess2DBException
     */
    boolean checkAlarmExist(String alarmid, String head_code,
            StringBuffer alarm_id) throws UpMess2DBException {
        String sql;
        sql = "select alarm_id from radio_equ_alarm_tab where origin_alarmid="
                + alarmid + " and head_code='" + head_code + "'";
        try {
            GDSet gs = DbComponent.Query(sql);
            if (gs.getRowCount()>0) {
                alarm_id.append(gs.getString(0,"alarm_id"));
                LogTool.debug("指标报警重复。id=" + alarmid);
                return true;
            } else {
                return false;
            }
        } catch (DbException ex) {
            throw new UpMess2DBException("", ex);
        } catch (GDSetException ex) {
            throw new UpMess2DBException("", ex);
        }
        // return false;
    }
   /**
	* 将root中的数据解析到ArrayList中(v8版)
	* @param root 根元素
	* @return 包含EquipmentAlarmHistoryReportBean的集合
	*/
	private ArrayList<EquipmentAlarmHistoryReportBean> getDataFromRootV8(Element root ) throws UpMess2DBException {
		Element equele = root.getChild("equipmentalarmhistoryreport");
		ArrayList<EquipmentAlarmHistoryReportBean> list = new ArrayList<EquipmentAlarmHistoryReportBean>();
		List list_equAlarm = equele.getChildren(); // 获得EquipmentAlarm元素
		for (int x = 0; x < list_equAlarm.size(); x++) {
		    Element ele = (Element) list_equAlarm.get(x);
		    EquipmentAlarmHistoryReportBean bean = new EquipmentAlarmHistoryReportBean(ele);
		    list.add(bean);
	    }
		return list;
	}
	
	/**
     * 将root中的数据解析到ArrayList中(边境版)
     * @param root 根元素
     * @return 包含EquipmentAlarmHistoryReportBean的集合
     */
    private ArrayList<EquipmentAlarmHistoryReportBean> getDataFromRootFrontier(Element root ) throws UpMess2DBException {
        Element equele = root.getChild("equipmentalarmhistoryreport");
	    ArrayList<EquipmentAlarmHistoryReportBean> list = new ArrayList<EquipmentAlarmHistoryReportBean>();
        List list_equAlarm = equele.getChildren(); // 获得EquipmentAlarm元素
        for (int x = 0; x < list_equAlarm.size(); x++) {
            Element ele = (Element) list_equAlarm.get(x);
            EquipmentAlarmHistoryReportBean bean = new EquipmentAlarmHistoryReportBean(ele);
            list.add(bean);
        }
        return list;
    }
    
    /**
     * 检查数据完整性 缺少抛出异常
     * @param equcode
     * @param alarmid
     * @param data 数据名
     * @param value 数据值
     * @throws UpMess2DBException
     */
    private void checkData(String equcode,String alarmid,String data,String value) throws UpMess2DBException{
        if(value==null||value.equals("")){
            throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode+";alarmid=" + alarmid + "广播设备报警上报数据缺少必要元素:"+data+"="+value);
        }
    }
}
/*
 * 设备设备报警数据类
 */
class EquipmentAlarmHistoryReportBean
{
	String equcode = "";
    String alarmid = "";
    String mode = "";
    String type = "";
    String desc = "";
    String reason = "";
    String checkdatetime = "";
    
    String outputlinelevel = "";
    String inputlinelevel = "";
    String linefrequency = "";
    String batterylevel = "";
    String upsstatus = "";

	//构造EquipmentAlarm
	public EquipmentAlarmHistoryReportBean(Element attrs) {
		this.equcode = attrs.getAttributeValue("equcode"); // 获得equcode
		this.alarmid = attrs.getAttributeValue("alarmid"); // 获得alarmid
		this.mode = attrs.getAttributeValue("mode");
		this.type = attrs.getAttributeValue("type");
		this.desc = attrs.getAttributeValue("desc");
		this.reason = attrs.getAttributeValue("reason");
		this.checkdatetime = attrs.getAttributeValue("checkdatetime");
		
		List list = attrs.getChildren(); // 获得Param元素
		for (int i = 0; i < list.size(); i++) {
			Element sub = (Element) list.get(i);
			String name = sub.getAttributeValue("name"); // 获得name
			String value = sub.getAttributeValue("value"); // 获得value
			if (name.equalsIgnoreCase("outputlinelevel")) {
				this.outputlinelevel = value;
			} else if (name.equalsIgnoreCase("inputlinelevel")) {
				this.inputlinelevel = value;
			} else if (name.equalsIgnoreCase("linefrequency")) {
				this.linefrequency = value;
			} else if (name.equalsIgnoreCase("batterylevel")) {
				this.batterylevel = value;
			} else if (name.equalsIgnoreCase("upsstatus")) {
				this.upsstatus = value;
			}
		}
	}

	public String getEqucode() {
		return equcode;
	}

	public void setEqucode(String equcode) {
		this.equcode = equcode;
	}

	public String getAlarmid() {
		return alarmid;
	}

	public void setAlarmid(String alarmid) {
		this.alarmid = alarmid;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCheckdatetime() {
		return checkdatetime;
	}

	public void setCheckdatetime(String checkdatetime) {
		this.checkdatetime = checkdatetime;
	}

	public String getOutputlinelevel() {
		return outputlinelevel;
	}

	public void setOutputlinelevel(String outputlinelevel) {
		this.outputlinelevel = outputlinelevel;
	}

	public String getInputlinelevel() {
		return inputlinelevel;
	}

	public void setInputlinelevel(String inputlinelevel) {
		this.inputlinelevel = inputlinelevel;
	}

	public String getLinefrequency() {
		return linefrequency;
	}

	public void setLinefrequency(String linefrequency) {
		this.linefrequency = linefrequency;
	}

	public String getBatterylevel() {
		return batterylevel;
	}

	public void setBatterylevel(String batterylevel) {
		this.batterylevel = batterylevel;
	}

	public String getUpsstatus() {
		return upsstatus;
	}

	public void setUpsstatus(String upsstatus) {
		this.upsstatus = upsstatus;
	}
}