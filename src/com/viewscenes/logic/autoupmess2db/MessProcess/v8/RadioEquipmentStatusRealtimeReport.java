package com.viewscenes.logic.autoupmess2db.MessProcess.v8;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

import com.viewscenes.cache.mon_cache.MonCacheAccessor;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.logic.autoupmess2db.Exception.NoRecordException;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.IUpMsgProcessor;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;

/**
 * <p>Title: �㲥�豸״̬ʵʱ��������ϱ��ӿڡ�</p>
 * <p>Description: ���������豸״̬�ϱ���Ϣ</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */
public class RadioEquipmentStatusRealtimeReport implements IUpMsgProcessor {
	private String srcCode;

	public RadioEquipmentStatusRealtimeReport() {
	}

	private static final String cacheSignPrefix = "equ_real_status_";

	public void processUpMsg(Element root) throws SQLException,
			UpMess2DBException, GDSetException, DbException, UtilException,
			NoRecordException {
		java.util.Date start = Calendar.getInstance().getTime();

		String headid = SiteVersionUtil.getSiteHeadId(root
				.getAttributeValue("SrcCode"));
		this.srcCode = root.getAttributeValue("SrcCode");//ǰ��վ��code
		String sitetype = SiteVersionUtil.getSiteType(srcCode);
		ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>> list = new ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>>();
		try {
			if (sitetype.equals("103")) {//�߾�վ
				//list = getDataFromRootFrontier(root);
				data2CacheFrontier(root);
			} else {
				//    int version = Integer.parseInt(root.getAttributeValue("Version"));
				//    switch (version) {
				//	  case 8:
				//list = getDataFromRootV8(root);
				data2CacheV8(root);
				//		break;
				//
				//	  default:
				//		break;
				//	  }
			}
		} catch (Exception e) {
			LogTool.info("autoup2mess","�豸״̬ʵʱ��������ϱ��ӿڴ���"+e.getMessage());
		}
//		ArrayList<EquipmentStatusRrealtimeReportBean> equipmentList = list
//				.get(0);
//		ArrayList<EquipmentStatusRrealtimeReportBean> equipmentParamList = list
//				.get(1);
//		data2Db(headid, equipmentList, equipmentParamList);
//
//		java.util.Date end = Calendar.getInstance().getTime();
//		LogTool.debug("�豸ʵʱ״̬�����ϱ���⻨ʱ��" + (end.getTime() - start.getTime()));
	}

	/**
	 * xml to cache��V8�棩
	 * �����ݽ��ܵ������С�HashMap[]���顣
	 */
	private void data2CacheV8(Element root) throws Exception {
		HashMap[] newData = null;
		// ����й�����
		String headCode = root.getAttributeValue("SrcCode");
		String headId = SiteVersionUtil.getSiteHeadId(headCode);
		if (headId == null || headId.length() <= 0) {
			LogTool.warning("�Ҳ���" + headCode + "���վ����룬�������ϣ�");
			return;
		}

		/**
		 * �����ϱ����ݡ�
		 */
		//��ȡEquipmentRealtimeStatusԪ��
		Element ele = root.getChild("equipmentstatusrealtimereport");
		List timeList = ele.getChildren();
		//��ʼ����
		if (timeList.size() > 0) {
			newData = new HashMap[timeList.size()];
		}
		for (int dataI = 0; dataI < timeList.size(); dataI++) {
			Element timeEqu = (Element) timeList.get(dataI);
			//���checkdatetime
			String checkdatetime = timeEqu.getAttributeValue("checkdatetime");
			if (checkdatetime == null) {
				checkData(checkdatetime, "scandatetime", checkdatetime);
			}
			java.util.Date dateCheckTime = StringTool.stringToDate(checkdatetime);
			//���EquipmentԪ��
			List equlist = timeEqu.getChildren();
			HashMap newDatum = new HashMap();
			newDatum.put("head_id", headId);
			newDatum.put("head_code", headCode);
			newDatum.put("check_datetime", dateCheckTime);
			for (int datumJ = 0; datumJ < equlist.size(); datumJ++) {
				Element scanResult = (Element) equlist.get(datumJ);
				String type = scanResult.getAttributeValue("type");
				if (type == null) {
					checkData(checkdatetime, "type", type);
				}
				String equCode = scanResult.getAttributeValue("equcode");
				if (equCode == null) {
					checkData(checkdatetime, "equCode", equCode);
				}
				//��������
				String statusValue = null;
				//�����ͼ���Ϣ��
//				if (type.equalsIgnoreCase("1")) {
//					statusValue = "1|" + type + "|";
//				} else if (type.equalsIgnoreCase("2")) {
//					statusValue = "2|" + equCode + "|";
//				} else {
//					statusValue = "3|" + type + "|";
//				}
				statusValue = type+"|" + equCode + "|";
				//����״̬
				String equStatus = scanResult.getAttributeValue("equstatus");
				if (equStatus == null) {
					checkData(checkdatetime, "equStatus", equStatus);
				}
				String desc = scanResult.getAttributeValue("desc");
				if (desc == null) {
					desc = "";
				}

				statusValue += equStatus + ":" + desc + "|";

				// ��ȡUPS״̬����ջ�״̬����ϸ������
				if (type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2")) {
					List paramlist = scanResult.getChildren();
					for (int paramI = 0; paramI < paramlist.size(); paramI++) {
						Element param = (Element) paramlist.get(paramI);
						String paramValue = param.getAttributeValue(
								"parammetertype").toLowerCase()
								+ ":" + param.getAttributeValue("value") + ";";
						statusValue += paramValue;
					}
				}
				System.out.println(statusValue);
				newDatum.put(datumJ+"", statusValue);
			}
			newData[dataI] = newDatum;
		}
		/**
		 * �ϱ������뻺�档
		 */
		if (newData != null && newData.length > 0) {
			try {
				MonCacheAccessor.addCacheData(cacheSignPrefix + headId,
						newData, "check_datetime", java.util.Date.class);
			} catch (Exception ex) {
			    ex.printStackTrace();
				LogTool.warning("վ��" + headCode + "ʵʱ״̬���ݽ��ܳ����쳣��" + ex);
			}
		}
	}

	/**
	 * xml to cache���߾��棩
	 * �����ݽ��ܵ������С�HashMap[]���顣
	 */
	private void data2CacheFrontier(Element root) throws Exception {
		HashMap[] newData = null;
		// ����й�����
		String headCode = root.getAttributeValue("SrcCode");
		String headId = SiteVersionUtil.getSiteHeadId(headCode);
		if (headId == null || headId.length() <= 0) {
			LogTool.warning("�Ҳ���" + headCode + "���վ����룬�������ϣ�");
			return;
		}

		/**
		 * �����ϱ����ݡ�
		 */
		//��ȡEquipmentRealtimeStatusԪ��
		Element ele = root.getChild("equipmentstatusrealtimereport");
		List timeList = ele.getChildren();
		//��ʼ����
		if (timeList.size() > 0) {
			newData = new HashMap[timeList.size()];
		}
		for (int dataI = 0; dataI < timeList.size(); dataI++) {
			Element timeEqu = (Element) timeList.get(dataI);
			//���checkdatetime
			String checkdatetime = timeEqu.getAttributeValue("checkdatetime");
			if (checkdatetime == null) {
				checkData(checkdatetime, "scandatetime", checkdatetime);
			}
			java.util.Date dateCheckTime = StringTool.stringToDate(checkdatetime);
			//���EquipmentԪ��
			List equlist = timeEqu.getChildren();
			HashMap newDatum = new HashMap();
			newDatum.put("head_id", headId);
			newDatum.put("head_code", headCode);
			newDatum.put("check_datetime", dateCheckTime);
			for (int datumJ = 0; datumJ < equlist.size(); datumJ++) {
				Element scanResult = (Element) equlist.get(datumJ);
				String type = scanResult.getAttributeValue("type");
				if (type == null) {
					checkData(checkdatetime, "type", type);
				}
				String equCode = scanResult.getAttributeValue("equcode");
				if (equCode == null) {
					checkData(checkdatetime, "equCode", equCode);
				}
				//��������
				String statusValue = null;
				//�����ͼ���Ϣ��
//				if (type.equalsIgnoreCase("1")) {
//					statusValue = "1|" + type + "|";
//				} else if (type.equalsIgnoreCase("2")) {
//					statusValue = "2|" + equCode + "|";
//				} else {
//					statusValue = "3|" + type + "|";
//				}
				statusValue = type + "|"+ equCode + "|";
				//����״̬
				String equStatus = scanResult.getAttributeValue("equstatus");
				if (equStatus == null) {
					checkData(checkdatetime, "equStatus", equStatus);
				}
				String desc = scanResult.getAttributeValue("desc");
				if (desc == null) {
					desc = "";
				}

				statusValue += equStatus + ":" + desc + "|";

				if (type.equalsIgnoreCase("1")
						|| type.equalsIgnoreCase("2") // 1UPS״̬ 2���ջ�״̬
						|| type.equalsIgnoreCase("11")// 11���ӽ��ջ�
						|| type.equalsIgnoreCase("21")) { // 11���ӽ��ջ� 21���ػ�ϵͳ
					List paramlist = scanResult.getChildren();
					for (int paramI = 0; paramI < paramlist.size(); paramI++) {
						Element param = (Element) paramlist.get(paramI);
						String paramValue = param.getAttributeValue(
								"parammetertype").toLowerCase()
								+ ":" + param.getAttributeValue("value") + ";";
						statusValue += paramValue;
					}
				}
				newDatum.put(datumJ+"", statusValue);
			}
			newData[dataI] = newDatum;
		}
		/**
		 * ��������ݡ�
		 */
		//      if (newData != null && newData.length > 0) {
		//        int dataLength = newData.length;
		//        System.out.println("ȥ��ǰ��������:"+dataLength);
		//        for (int nullI = 0; nullI < dataLength; nullI++) {
		//  	if (newData[nullI] == null) {
		//  	  dataLength--;
		//  	  System.arraycopy(newData, nullI + 1, newData, nullI,
		//  			   dataLength - nullI - 1);
		//  	}
		//        }
		//        System.out.println("ȥ�պ���������:"+dataLength);
		//        if (dataLength < newData.length) {
		//  	HashMap[] tempData = new HashMap[dataLength];
		//  	System.arraycopy(newData, 0, tempData, 0, dataLength);
		//  	newData = tempData;
		//        }
		//      }
		/**
		 * �ϱ������뻺�档
		 */
		if (newData != null && newData.length > 0) {
			try {
				MonCacheAccessor.addCacheData(cacheSignPrefix + headId,
						newData, "check_datetime", java.util.Date.class);
			} catch (Exception ex) {
			    ex.printStackTrace();
				LogTool.warning("վ��" + headCode + "ʵʱ״̬���ݽ��ܳ����쳣��" + ex);
				
			}
		}
	}
	
	/**
	 * (radio_equ_status_realtime_tab)(radio_equ_status_param_tab)
	 * ����preparedStatement��ʵʱ״̬�������
	 * @throws UpMess2DBException
	 **/
	private void data2Db(String headid,
			ArrayList<EquipmentStatusRrealtimeReportBean> equipmentList,
			ArrayList<EquipmentStatusRrealtimeReportBean> equipmentParamList)
			throws UpMess2DBException {
		// ʵʱ״̬���sql���
		String sql1 = "insert into radio_equ_status_realtime_tab(realtime_id, type, equ_code, "
				+ "equstatus, description, check_datetime, head_id) values(RADIO_REALTIME_SEQ.nextval, "
				+ "?, ?, ?, ?, ?, " + headid + ")";
		String sql2 = "insert into radio_equ_status_param_tab(param_id, param, value, equ_code, "
				+ "check_datetime, head_id) values(RADIO_REALTIME_SEQ.nextval, ?, ?, ?, ?, "
				+ headid + ")";

		DbComponent db1 = new DbComponent();
		DbComponent.DbQuickExeSQL prepExeSQL1 = db1.new DbQuickExeSQL(sql1);
		prepExeSQL1.getConnect();
		DbComponent db2 = new DbComponent();
		DbComponent.DbQuickExeSQL prepExeSQL2 = db2.new DbQuickExeSQL(sql2);
		prepExeSQL2.getConnect();
		try {
			for (int i = 0; i < equipmentList.size(); i++) {
				EquipmentStatusRrealtimeReportBean bean = equipmentList.get(i);
				String type = bean.getType();
				String equcode = bean.getEqu_code();
				String equstatus = bean.getEqustatus();
				String check_datetime = bean.getCheck_datetime();
				checkData(check_datetime, "check_datetime", check_datetime);
				checkData(check_datetime, "type", type);
				checkData(check_datetime, "equcode", equcode);
				checkData(check_datetime, "equstatus", equstatus);

				prepExeSQL1.setString(1, type);
				prepExeSQL1.setString(2, equcode);
				prepExeSQL1.setString(3, equstatus);
				prepExeSQL1.setString(4, check_datetime);
				prepExeSQL1.setString(5, bean.getCheck_datetime());
				prepExeSQL1.exeSQL();
			}

			for (int j = 0; j < equipmentParamList.size(); j++) {
				EquipmentStatusRrealtimeReportBean bean = equipmentParamList
						.get(j);
				String parammeterType = bean.getParam();
				String value = bean.getValue();
				String check_datetime = bean.getCheck_datetime();
				checkData(check_datetime, "parammeterType", parammeterType);
				checkData(check_datetime, "value", value);
				prepExeSQL2.setString(1, parammeterType);
				prepExeSQL2.setString(2, value);
				prepExeSQL2.setString(3, bean.getEqu_code());
				prepExeSQL2.setString(4, check_datetime);
				prepExeSQL2.exeSQL();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prepExeSQL1.closeConnect();
			prepExeSQL2.closeConnect();
		}
	}

	/**
	 * getDataFromRootV8
	 * ��root�е����ݽ�����ArrayList��(v8��)
	 * @param root ��Ԫ��
	 * @return ����EquipmentStatusRrealtimeReportBean�ļ���
	 */
	private ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>> getDataFromRootV8(
			Element root) throws UpMess2DBException {
		ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>> list = new ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>>();
		ArrayList<EquipmentStatusRrealtimeReportBean> equipmentList = new ArrayList<EquipmentStatusRrealtimeReportBean>();
		ArrayList<EquipmentStatusRrealtimeReportBean> equipmentParamList = new ArrayList<EquipmentStatusRrealtimeReportBean>();

		// ����й�����
		Element ele = root.getChild("equipmentstatusrealtimereport");
		List list_equ = ele.getChildren(); // ���EquipmentRealtimeStatusԪ��
		for (int i = 0; i < list_equ.size(); i++) {
			Element sub = (Element) list_equ.get(i);
			// ���checkdatetime
			String checkdatetime = sub.getAttributeValue("checkdatetime");
			if (checkdatetime == null) {
				throw new UpMess2DBException("�豸״̬ʵʱ����ϱ�scandatetimeΪnull");
			}
			List sublist = sub.getChildren(); // ���EquipmentԪ��
			for (int j = 0; j < sublist.size(); j++) {
				Element scanResult = (Element) sublist.get(j);
				String equcode = scanResult.getAttributeValue("equcode");
				String type = scanResult.getAttributeValue("type");
				EquipmentStatusRrealtimeReportBean bean = new EquipmentStatusRrealtimeReportBean(
						scanResult);
				bean.setCheck_datetime(checkdatetime);
				equipmentList.add(bean);
				// ��ȡ��ϸ����
				if (type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2")) { // UPS״̬����ջ�״̬
					List paramlist = scanResult.getChildren(); // ���ParameterԪ��
					for (int l = 0; l < paramlist.size(); l++) {
						Element param = (Element) paramlist.get(l);
						EquipmentStatusRrealtimeReportBean beanParam = new EquipmentStatusRrealtimeReportBean(
								param, equcode, checkdatetime);
						equipmentParamList.add(beanParam);
					}
				}
			}
		}
		list.add(equipmentList);
		list.add(equipmentParamList);
		return list;
	}

	/**
	 * getDataFromRootFrontier
	 * ��root�е����ݽ�����ArrayList��(�߾����)
	 * @param root ��Ԫ��
	 * @return ����EquipmentStatusRrealtimeReportBean�ļ���
	 */
	private ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>> getDataFromRootFrontier(
			Element root) throws UpMess2DBException {
		ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>> list = new ArrayList<ArrayList<EquipmentStatusRrealtimeReportBean>>();
		ArrayList<EquipmentStatusRrealtimeReportBean> equipmentList = new ArrayList<EquipmentStatusRrealtimeReportBean>();
		ArrayList<EquipmentStatusRrealtimeReportBean> equipmentParamList = new ArrayList<EquipmentStatusRrealtimeReportBean>();

		// ����й�����
		Element ele = root.getChild("equipmentstatusrealtimereport");
		List list_equ = ele.getChildren(); // ���EquipmentRealtimeStatusԪ��
		for (int i = 0; i < list_equ.size(); i++) {
			Element sub = (Element) list_equ.get(i);
			// ���checkdatetime
			String checkdatetime = sub.getAttributeValue("checkdatetime");
			if (checkdatetime == null) {
				throw new UpMess2DBException("�豸״̬ʵʱ����ϱ�scandatetimeΪnull");
			}
			List sublist = sub.getChildren(); // ���EquipmentԪ��
			for (int j = 0; j < sublist.size(); j++) {
				Element scanResult = (Element) sublist.get(j);
				String equcode = scanResult.getAttributeValue("equcode");
				String type = scanResult.getAttributeValue("type");
				EquipmentStatusRrealtimeReportBean bean = new EquipmentStatusRrealtimeReportBean(
						scanResult);
				bean.setCheck_datetime(checkdatetime);
				equipmentList.add(bean);
				// ��ȡ��ϸ����
				if (type.equalsIgnoreCase("1")
						|| type.equalsIgnoreCase("2")   // 1UPS״̬ 2���ջ�״̬
						|| type.equalsIgnoreCase("21")) { // 11���ӽ��ջ� 21���ػ�ϵͳ
					List paramlist = scanResult.getChildren(); // ���ParameterԪ��
					for (int l = 0; l < paramlist.size(); l++) {
						Element param = (Element) paramlist.get(l);
						EquipmentStatusRrealtimeReportBean beanParam = new EquipmentStatusRrealtimeReportBean(
								param, equcode, checkdatetime);
						equipmentParamList.add(beanParam);
					}
				}
			}
		}
		list.add(equipmentList);
		list.add(equipmentParamList);
		return list;
	}

	/**
	 * ������������� ȱ���׳��쳣
	 * @param equcode
	 * @param task_id
	 * @param data ������
	 * @param value ����ֵ
	 * @throws UpMess2DBException
	 */
	private void checkData(String check_datetime, String data, String value)
			throws UpMess2DBException {
		if (value == null || value.equals("")) {
			throw new UpMess2DBException("srcCode=" + srcCode
					+ ";check_datetime=" + check_datetime + "�豸״̬ʵʱ����ϱ�ȱ�ٱ�Ҫ����:"
					+ data + "=" + value);
		}
	}
}

/*
 * �豸״̬ʵʱ����ϱ�������
 */
class EquipmentStatusRrealtimeReportBean {
	private String type;
	private String equstatus;
	private String description;

	private String equ_code;
	private String check_datetime;

	private String param;
	private String value;

	//����Equipment
	public EquipmentStatusRrealtimeReportBean(Element attrs) {
		this.type = attrs.getAttributeValue("type");
		this.equ_code = attrs.getAttributeValue("equcode");
		this.description = attrs.getAttributeValue("desc");
		this.equstatus = attrs.getAttributeValue("equstatus");
	}

	//����EquipmentarParam
	public EquipmentStatusRrealtimeReportBean(Element attrs, String equcode,
			String check_datetime) {
		this.param = attrs.getAttributeValue("parammetertype");
		this.value = attrs.getAttributeValue("value");
		this.equ_code = equcode;
		this.check_datetime = check_datetime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEqustatus() {
		return equstatus;
	}

	public void setEqustatus(String equstatus) {
		this.equstatus = equstatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getCheck_datetime() {
		return check_datetime;
	}

	public void setCheck_datetime(String check_datetime) {
		this.check_datetime = check_datetime;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}