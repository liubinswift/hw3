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

/**
 * �㲥ָ�걨����ʷ��ѯ���������ϱ��ӿ�
 * <p> Title: </p>
 * <p>  Description:  </p>
 * <p> Copyright: Copyright (c) 2009 </p>
 * <p>  Company: </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RadioQualityAlarmHistoryReport implements IUpMsgProcessor {
    private String srcCode;
	public RadioQualityAlarmHistoryReport() {
	}

	public void processUpMsg(Element root) throws SQLException,
			UpMess2DBException, GDSetException, DbException, UtilException,
			NoRecordException {
		java.util.Date start = Calendar.getInstance().getTime();
		QualityAlarmGDSet2Db(root);
		java.util.Date end = Calendar.getInstance().getTime();
		long period = end.getTime() - start.getTime();
		LogTool.debug("ָ�걨����ʷ��ѯ������⻨ʱ��" + period);
		System.out.println("ָ�걨����ʷ��ѯ������⻨ʱ��" + period);
	}

	/**
	 * �㲥ָ�걨���ϱ��������
	 * (radio_quality_alarm_tab)
	 * @param root
	 * @return GDSet
	 * @throws GDSetException
	 * @throws SQLException
	 * @throws DbException
	 * @throws UpMess2DBException
	 */
	private synchronized void QualityAlarmGDSet2Db(Element root)
			throws GDSetException, SQLException, NoRecordException,
			DbException, UpMess2DBException {
		this.srcCode = root.getAttributeValue("SrcCode");
//		Vector updateV = new Vector();
	    String sitetype = SiteVersionUtil.getSiteType(srcCode);
		ArrayList<QualityAlarmHistoryReportBean> list = new ArrayList<QualityAlarmHistoryReportBean>();
		 if(sitetype.equals("103")){//�߾�վ
	        //��
	    }
	    else{
//	    int version = Integer.parseInt(root.getAttributeValue("Version"));
//	    switch (version) {
//		  case 8:
			list = getDataFromRootV8(root);
//			break;
//
//		  default:
//			break;
//		  }
	    }
//		 String queryCenterId = "select c.center_id from res_center_tab c,res_headend_tab h " +
//         "where c.center_id=h.center_id and h.is_delete=0 and h.code='"+srcCode+"'";
//         GDSet gdc = DbComponent.Query(queryCenterId);
//         String centerID = "";
//         if (gdc.getRowCount()>0) {
//             centerID = gdc.getString(0, "center_id");
//         }
         
		try {
            setRadioGD(list, srcCode);
        } catch (Exception e) {
            LogTool.fatal("autoup2mess",e);
            throw new UpMess2DBException("",e);
        }
		
		
//		try {
//			DbComponent.Insert(gd, null); // ���뱨��
//			// ���±������ָ�������
//			String[] updateSql = new String[updateV.size()];
//			Iterator i = updateV.iterator();
//			int k = 0;
//			while (i.hasNext()) {
//				updateSql[k] = (String) i.next();
//				k++;
//			}
//			DbComponent.exeBatch(updateSql);
//		} catch (DbException ex) {
//			throw new UpMess2DBException("", ex);
//		}
		
	}

	/**
	 * ���ù㲥ָ�걨��GDSet������ v8 2
	 * 
	 * @param ele
	 * @param gd ��������
	 * @param gd1 �ָ���������
	 * @param headid
	 * @throws GDSetException
	 */
	private void setRadioGD(ArrayList<QualityAlarmHistoryReportBean> list, String head_code) throws GDSetException, UpMess2DBException,Exception {
		 String sql =
			  "insert into radio_quality_alarm_tab(alarm_id, equ_code,origin_alarmid,frequency,type,description,reason,e_level," +
			  "band,fm_modulation,am_modulation,attenuation,head_code,alarm_datetime,is_resume,resume_datetime) " +
			  "values(RADIO_ALARM_SEQ.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		 DbComponent db = new DbComponent();
		DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
		prepExeSQL.getConnect();

		try {
			for (int x = 0; x < list.size(); x++) {
				QualityAlarmHistoryReportBean bean = (QualityAlarmHistoryReportBean) list
						.get(x);

				String equcode = bean.getEqu_code(); // ���equcode
				String alarmid = bean.getAlarmid(); // ���alarmid
				String mode = bean.getIs_resume();
				String band = bean.getBand();
				String freq = bean.getFrequency();
				String type = bean.getType();
				String desc = bean.getDescription();
				String reason = bean.getReason();
				String alarm_datetime = bean.getAlarm_datetime();
				String resume_datetime = bean.getResume_datetime();
				// checkData(equcode,alarmid,"centerID",centerID);
				checkData(equcode, alarmid, "equcode", equcode);
				checkData(equcode, alarmid, "alarmid", alarmid);
				checkData(equcode, alarmid, "mode", mode);
				checkData(equcode, alarmid, "band", band);
				checkData(equcode, alarmid, "freq", freq);
				checkData(equcode, alarmid, "type", type);
				// if (reason == null||reason.equals("")) {
				// throw new UpMess2DBException("srcCode="+srcCode+";equcode=" +
				// equcode + ";alarmid=" + alarmid +
				// "ָ�걨���ϱ�����ȱ�ٱ�Ҫ����:reason="+reason);
				// }
				if (mode.equals("0")) {
					checkData(equcode, alarmid, "alarm_datetime",
							alarm_datetime);
				}
				if (mode.equals("1")) {
					checkData(equcode, alarmid, "resume_datetime",
							resume_datetime);
				}
				String level = bean.getE_level();
				String fm_modulation = bean.getFm_modulation();
				String am_modulation = bean.getAm_modulation();
				String attenuation = bean.getAttenuation();

				// �޸�ָ�걨����⣬������ݿ���û��������ô��ӣ����mode��0��ôis_resume��0�����mode��1��ôis_resume��1��
				// ������ݿ�����������ô�޸ģ����mode��0���������mode��1��ô�޸ġ�

				// 1.��ⱨ�������Ƿ����
				StringBuffer alarm_id_buf = new StringBuffer("");

				boolean isExist = false;
				try {
					isExist = checkAlarmExist1(alarmid, head_code, alarm_id_buf);
				} catch (UpMess2DBException ex) {
					throw new UpMess2DBException("", ex);
				}
				String alarm_id = alarm_id_buf.toString();

				// 2.����
				if (!isExist) { // �����������ô���
					prepExeSQL.setString(1, equcode);
					prepExeSQL.setString(2, alarmid);
					prepExeSQL.setString(3, freq);
					prepExeSQL.setString(4, type);
					prepExeSQL.setString(5, desc);
					prepExeSQL.setString(6, reason);
					prepExeSQL.setString(7, level);
					prepExeSQL.setString(8, band);
					prepExeSQL.setString(9, fm_modulation);
					prepExeSQL.setString(10, am_modulation);
					prepExeSQL.setString(11, attenuation);
					prepExeSQL.setString(12, head_code);
					prepExeSQL.setString(13, alarm_datetime);
					prepExeSQL.setString(14, mode);
					prepExeSQL.setString(15, resume_datetime);
					prepExeSQL.exeSQL();

				} else { // �Ѿ����ڿ�����ô�޸ģ�����Ǳ����޸ı���ʱ�䣬����ǽ�������޸�
					String sqlUpdate = "";
					if (mode.equalsIgnoreCase("1")) {

						sqlUpdate = "update radio_quality_alarm_tab set is_resume=1, resume_datetime=to_date('"
								+ resume_datetime
								+ "', 'YYYY-MM-DD HH24:MI:SS') where origin_alarmid = "
								+ alarmid
								+ " and head_code = '"
								+ head_code
								+ "'";

					}
					if (mode.equalsIgnoreCase("0")) {
						sqlUpdate = "update radio_quality_alarm_tab set alarm_datetime=to_date('"
								+ alarm_datetime
								+ "', 'YYYY-MM-DD HH24:MI:SS') where origin_alarmid = "
								+ alarmid
								+ " and head_code = '"
								+ head_code
								+ "'";
					}
					DbComponent.exeUpdate(sqlUpdate);
				}
			}
		} catch (Exception ex) {
			throw new UpMess2DBException("�����ϱ��쳣��", ex);
		} finally {
			prepExeSQL.closeConnect();
		}
	}

	boolean checkAlarmExist1(String alarmid, String head_code,
			StringBuffer alarm_id) throws UpMess2DBException {
		String sql;
		sql = "select alarm_id from radio_quality_alarm_tab where origin_alarmid="
				+ alarmid + " and head_code='" + head_code + "'";
		try {
			GDSet gs = DbComponent.Query(sql);
			if (gs.getRowCount()>0) {
				alarm_id.append(gs.getString(0,"alarm_id"));
				LogTool.debug("ָ�걨���ظ���id=" + alarmid);
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
	 * getDataFromRootV8
	 * ��root�е����ݽ�����ArrayList��(v8��)
	 * @param root
	 * @return ����QualityAlarmHistoryReportBean�ļ���
	 */
	public ArrayList<QualityAlarmHistoryReportBean> getDataFromRootV8(Element root) {
	    ArrayList<QualityAlarmHistoryReportBean> list = new ArrayList<QualityAlarmHistoryReportBean>();
	    Element eleReport = root.getChild("qualityalarmhistoryreport");
	    List list1 = eleReport.getChildren(); // ���QualityAlarmԪ��
		for (int x = 0; x < list1.size(); x++) {
			Element ele = (Element) list1.get(x);
			QualityAlarmHistoryReportBean bean = new QualityAlarmHistoryReportBean(ele);
			
			list.add(bean);				
		}
		return list;
	}
	
	/**
	   * ������������� ȱ���׳��쳣
	   * @param equcode
	   * @param alarmid
	   * @param data ������
	   * @param value ����ֵ
	   * @throws UpMess2DBException
	   */
	  private void checkData(String equcode,String alarmid,String data,String value) throws UpMess2DBException{
	      if(value==null||value.equals("")){
	          throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode + ";alarmid=" + alarmid + "ָ�걨���ϱ�����ȱ�ٱ�Ҫ����:"+data+"="+value);
	      }
	  }
	  
	  public void setHeadendStatus(String headendcode){
		  String sql = "select count(*) from radio_quality_alarm_tab where head_code='"+headendcode+"' and is_resume = 0 and is_handle = 0 "
		  			+ " union all"
		  			+ " select  count(*) from radio_equ_alarm_tab where head_code='RstestA' and is_resume = 0 and is_handle = 0";
		  //GDSet gdSet aa= DbComponent.Query(sql);
	  }
}

/*
 * ָ�걨���ϱ�������
 */
class QualityAlarmHistoryReportBean
{
	private String equ_code;
	private String alarmid;
	private String frequency;
	private String type;
	private String description;
	private String reason;
	private String band;
	private String head_code;
	private String is_resume;
	private String alarm_datetime;
	private String resume_datetime;
	
	private String e_level;
	private String am_modulation;
	private String fm_modulation;
	private String attenuation;

	public QualityAlarmHistoryReportBean(Element attrs) {
		this.equ_code = attrs.getAttributeValue("equcode"); // ���equcode
		this.alarmid = attrs.getAttributeValue("alarmid"); // ���alarmid
		this.is_resume = attrs.getAttributeValue("mode");
		this.band = attrs.getAttributeValue("band");
		this.frequency = attrs.getAttributeValue("freq");
		this.type = attrs.getAttributeValue("type");
		this.description = attrs.getAttributeValue("desc");
		this.reason = attrs.getAttributeValue("reason");
		if(this.is_resume.equals("0")){
			this.alarm_datetime = attrs.getAttributeValue("checkdatetime");
		}
		else{
			this.resume_datetime = attrs.getAttributeValue("checkdatetime");
		}
		
		List listAlarmParam = attrs.getChildren(); // ���AlarmParamԪ��
		for (int i = 0; i < listAlarmParam.size(); i++) {
			Element param = (Element)listAlarmParam.get(i);
			String name = param.getAttributeValue("name"); // ���name
			String value = param.getAttributeValue("value"); // ���value
			if (name.equalsIgnoreCase("attenuation")) {
				this.attenuation = value;
			} else if (name.equalsIgnoreCase("level")) {
				this.e_level = value;
			} else if (name.equalsIgnoreCase("fm-modulation")) {
				this.fm_modulation = value;
			} else if (name.equalsIgnoreCase("am-modulation")) {
				this.am_modulation = value;
			}
		}
	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getHead_code() {
		return head_code;
	}

	public void setHead_code(String head_code) {
		this.head_code = head_code;
	}

	public String getIs_resume() {
		return is_resume;
	}

	public void setIs_resume(String is_resume) {
		this.is_resume = is_resume;
	}

	public String getAlarm_datetime() {
		return alarm_datetime;
	}

	public void setAlarm_datetime(String alarm_datetime) {
		this.alarm_datetime = alarm_datetime;
	}

	public String getResume_datetime() {
		return resume_datetime;
	}

	public void setResume_datetime(String resume_datetime) {
		this.resume_datetime = resume_datetime;
	}

	public String getE_level() {
		return e_level;
	}

	public void setE_level(String e_level) {
		this.e_level = e_level;
	}

	public String getAm_modulation() {
		return am_modulation;
	}

	public void setAm_modulation(String am_modulation) {
		this.am_modulation = am_modulation;
	}

	public String getFm_modulation() {
		return fm_modulation;
	}

	public void setFm_modulation(String fm_modulation) {
		this.fm_modulation = fm_modulation;
	}

	public String getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(String attenuation) {
		this.attenuation = attenuation;
	}

	public String getAlarmid() {
		return alarmid;
	}

	public void setAlarmid(String alarmid) {
		this.alarmid = alarmid;
	}
}