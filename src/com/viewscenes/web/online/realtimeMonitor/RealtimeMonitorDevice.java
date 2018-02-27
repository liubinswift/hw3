package com.viewscenes.web.online.realtimeMonitor;

import java.util.ArrayList;
import java.util.Date;

import org.jdom.Element;
import org.jmask.web.controller.EXEException;

import com.viewscenes.cache.mon_cache.MonCacheAccessor;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.exception.DeviceException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgEquipmentStatusRealtimeQueryCmd;
import com.viewscenes.device.radio.MsgEquipmentStatusRealtimeQueryRes;
import com.viewscenes.device.radio.MsgQualityRealtimeQueryCmd;
import com.viewscenes.device.radio.MsgQualityRealtimeQueryRes;
import com.viewscenes.device.radio.MsgSpectrumRealtimeScanCmd;
import com.viewscenes.device.radio.MsgSpectrumRealtimeScanRes;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.Operation_log;
import com.viewscenes.sys.Security;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.Daoutil.SiteVersionUtil;

import flex.messaging.io.amf.ASObject;

/**
 * 实时监测设备通信方法
 * 
 * @author Administrator
 * 
 */
public class RealtimeMonitorDevice {

    public final String EQU_CACHE_PREFIX = "equ_real_status_";
	public Object getQualityRealtimeQuery(ASObject asobj) {

		String user_id = (String) asobj.get("userId");
		String code = (String) asobj.get("code");
		String receiverCode = (String) asobj.get("receiverCode");
		String band = (String) asobj.get("band");
		String freq = (String) asobj.get("freq");
		String intervalTime = (String) asobj.get("intervalTime");
		String expires = (String) asobj.get("expireTime");
		String action = (String) asobj.get("action");

		intervalTime = StringTool.getTimeString(Integer.parseInt(intervalTime));
		expires = StringTool.getTimeString(Integer.parseInt(expires));
		String levelV8 = (String) asobj.get("levelV8"); // 载波电平 1
		String sNLevelV8 = (String) asobj.get("sNLevelV8");
		String fmModulationV8 = (String) asobj.get("fmModulationV8"); // 调制度 2
		String sNFmModulationV8 = (String) asobj.get("sNFmModulationV8");

		String fmModulationMaxV8 = (String) asobj.get("fmModulationMaxV8"); // 调制度最大值
																			// 5
		String sNFmModulationMaxV8 = (String) asobj.get("sNFmModulationMaxV8");
		String amModulationV8 = (String) asobj.get("amModulationV8");
		; // 调幅度 3
		String sNAmModulationV8 = (String) asobj.get("sNAmModulationV8");
		String offsetV8 = (String) asobj.get("offsetV8"); // 频偏 6
		String sNOffsetV8 = (String) asobj.get("sNOffsetV8");

		String bandWidthV8 = (String) asobj.get("bandWidthV8"); // 带宽 8
		String sNBandWidthV8 = (String) asobj.get("sNBandWidthV8");
		String liyushaV5 = (String) asobj.get("liyushaV5"); // 李育沙图 7
		String sNLiyushaV5 = (String) asobj.get("sNLiyushaV5");

		String typeCondition = (String) asobj.get("typeCondition");

		String[] retStr = new String[6];
		StringBuffer cmdMessage = new StringBuffer(); // 错误信息
		String cmdResult = action;// "stop"; // 画图命令
		String stopURL = null; // 停机地址
		String checkTime = null; // 返回的数据开始时间
		String clientStartTime = ""; // 客户端applet的自定义起画时间
		String realReceiCode = ""; // 实际工作接收机
		String headendID = SiteVersionUtil.getSiteHeadId(code);

		// 删除
		try {
			StringBuilder delSql = new StringBuilder(
					"delete from radio_quality_realtime_tab where");
			delSql.append(" head_id = " + headendID + " and ");
			delSql.append("type_id in(" + typeCondition + ") ");
			DbComponent.exeUpdate(delSql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

		String err = "";
		String result = "";

		Security security = new Security();
		long msgPrio = 0;
		String priority = "0";
		if (user_id != null) {
			try {
				msgPrio = security.getMessagePriority(user_id, 3, 2, 0);
				priority = new Long(msgPrio).toString();
			} catch (Exception ex1) {
				err = ex1.getMessage();
			}
		}

		/**
		 * 转换大小写。
		 */
		code = SiteVersionUtil.getSiteOriCode(code);

		int siteVer = Integer.parseInt(SiteVersionUtil.getSiteVerStr(code)
				.substring(1));
		// int siteVer
		// =Integer.parseInt(InnerDevice.instance(code+frontier).getType().getVersion());
		/**
		 * 定义变量
		 */
		String url = "";
		String recordId = "";

		try {
			// 生成发送消息对象
			MsgQualityRealtimeQueryCmd cmd = new MsgQualityRealtimeQueryCmd();
			// 生成返回消息对象
			MsgQualityRealtimeQueryRes res = new MsgQualityRealtimeQueryRes();

			// 设置发送消息数据
			cmd.setPriority(priority);
			cmd.setDestCode(code);

			ArrayList qualityIndex = new ArrayList();

			switch (siteVer) {

			case 8:

				if (bandWidthV8 != null && !bandWidthV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q28 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							bandWidthV8, "BandWidth", sNBandWidthV8);
					qualityIndex.add(q28);
				}

				if (levelV8 != null && !levelV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q21 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							levelV8, "Level", sNLevelV8);
					qualityIndex.add(q21);
				}

				if (fmModulationV8 != null
						&& !fmModulationV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q22 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							fmModulationV8, "FM-Modulation", sNFmModulationV8);
					qualityIndex.add(q22);
				}

				if (amModulationV8 != null
						&& !amModulationV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q23 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							amModulationV8, "AM-Modulation", sNAmModulationV8);
					qualityIndex.add(q23);
				}

				if (fmModulationMaxV8 != null
						&& !fmModulationMaxV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q25 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							fmModulationMaxV8, "FM-ModulationMax",
							sNFmModulationMaxV8);
					qualityIndex.add(q25);
				}

				if (offsetV8 != null && !offsetV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q26 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							offsetV8, "Offset", sNOffsetV8);
					qualityIndex.add(q26);
				}

				cmd.setParams(receiverCode, freq, band, intervalTime, expires,
						action);
				break;

			case 6:

			case 7:

				if (levelV8 != null && !levelV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q21 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							levelV8, "Level", sNLevelV8);
					qualityIndex.add(q21);
				}

				if (fmModulationV8 != null
						&& !fmModulationV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q22 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							fmModulationV8, "FM-Modulation", sNFmModulationV8);
					qualityIndex.add(q22);
				}

				if (amModulationV8 != null
						&& !amModulationV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q23 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							amModulationV8, "AM-Modulation", sNAmModulationV8);
					qualityIndex.add(q23);
				}

				if (fmModulationMaxV8 != null
						&& !fmModulationMaxV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q25 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							fmModulationMaxV8, "FM-ModulationMax",
							sNFmModulationMaxV8);
					qualityIndex.add(q25);
				}

				if (offsetV8 != null && !offsetV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q26 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							offsetV8, "Offset", sNOffsetV8);
					qualityIndex.add(q26);
				}

				cmd.setParams(receiverCode, freq, band, intervalTime, expires,
						action);
				break;

			case 5:

				if (levelV8 != null && !levelV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q21 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							levelV8, "Level", sNLevelV8);
					qualityIndex.add(q21);
				}

				if (fmModulationV8 != null
						&& !fmModulationV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q22 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							fmModulationV8, "FM-Modulation", sNFmModulationV8);
					qualityIndex.add(q22);
				}

				if (amModulationV8 != null
						&& !amModulationV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q23 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							amModulationV8, "AM-Modulation", sNAmModulationV8);
					qualityIndex.add(q23);
				}

				if (fmModulationMaxV8 != null
						&& !fmModulationMaxV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q25 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							fmModulationMaxV8, "FM-ModulationMax",
							sNFmModulationMaxV8);
					qualityIndex.add(q25);
				}

				if (offsetV8 != null && !offsetV8.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q26 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							offsetV8, "Offset", sNOffsetV8);
					qualityIndex.add(q26);
				}

				if (liyushaV5 != null && !liyushaV5.equalsIgnoreCase("")) {

					MsgQualityRealtimeQueryCmd.QueryTypes q27 = new MsgQualityRealtimeQueryCmd.QueryTypes(
							liyushaV5, "Offset-Lishayu", sNLiyushaV5);
					qualityIndex.add(q27);
				}
				cmd.setParams(receiverCode, freq, band, intervalTime, expires,
						action);
				break;

			case 1:

			}

			cmd.setParams(receiverCode, freq, band, intervalTime, expires,
					action);

			cmd.setQueryTypes(qualityIndex);

			// 生成发送处理器

			MessageClient client = new MessageClient();

			// 发送消息，并获得返回结果
			client.execute(cmd, res);
			if (action.equalsIgnoreCase("start")) {
				// cmdResult = "start";
				// //停止url
				// stopURL = "&headendcode=" + code + "&receivercode=" +
				// receiverCode + "&frequency=" + freq + "&data=" +
				// "&interval=" + intervalTime + "&expires=" + expires +
				// "&msgPrio=" +
				// "&stop=Stop";
				//
				// if (res.getQuality() != null) {
				// MsgQualityRealtimeQueryRes.Quality q[] =
				// (MsgQualityRealtimeQueryRes.Quality[])res.getQuality().toArray(new
				// MsgQualityRealtimeQueryRes.Quality[0]);//showLog(""+q.length);
				// if (q.length > 0) {
				//
				// // 获得实际的前端代码
				// if (q[0].getEquCode() != null && q[0].getEquCode().length() >
				// 0 && !receiverCode.equalsIgnoreCase(q[0].getEquCode())) {
				// realReceiCode = q[0].getEquCode();
				// // 获得数据开始时间
				// }
				// checkTime = q[0].getCheckTime();
				// if (checkTime != null && checkTime.length() > 0) {
				// checkTime = new
				// Long(StringTool.stringToDate(checkTime).getTime()).toString();
				// }
				// }
				// }
				/**
				 * 根据当前时间决定返回时间。循环100次查找，找到为至。
				 */
				// // DbComponent dbComponent = new DbComponent();
				GDSet gdSet = null;
				clientStartTime = res.getResult().getheadTime();
				if (clientStartTime == null || clientStartTime.equals("")) {
					clientStartTime = StringTool.Date2String(new Date());
				}
				// String taskTime =
				// "select to_char(check_datetime,'yyyyMMddHH24miss') check_datetime,equ_code from radio_quality_realtime_tab where head_id='"
				// +
				// headendID +
				// "' and  check_datetime in ( select max(check_datetime) from radio_quality_realtime_tab where head_id='"
				// +
				// headendID + "' ) and signaltype='"+signaltype+"'"+
				// " and check_datetime>=to_date('"+da+"','yyyy-mm-dd hh24:mi:ss')";
				String taskTime = "select to_char(check_datetime,'yyyyMMddHH24miss') check_datetime,equ_code from radio_quality_realtime_tab where head_id='"
						+ headendID
						+ "'  "
						+ " and check_datetime>=to_date('"
						+ clientStartTime + "','yyyy-mm-dd hh24:mi:ss')";
				try {
					String sqlString = "select to_char(to_date('"
							+ clientStartTime
							+ "','yyyy-mm-dd hh24:mi:ss'),'yyyyMMddHH24miss') check_datetimeRes  from dual";
					GDSet gd = DbComponent.Query(sqlString);
					clientStartTime = gd.getString(0, "check_datetimeRes");
					String sitytype = SiteVersionUtil.getSiteType(code);
					if (sitytype.equals("103")) {
						/**
						 * 睡1秒,共找5次或找到即结束。
						 */
						for (int ti = 0; ti < 35; ti++) {
							gdSet = DbComponent.Query(taskTime);
							/**
							 * 不知道，顶层applet如何使用，只能与页面使用一致。
							 */

							if (gdSet.getRowCount() > 0) {
								clientStartTime = gdSet.getString(0,
										"check_datetime");
								// String sitytype =
								// SiteVersionUtil.getSiteType(code);
								// if(sitytype.equals("103")){
								realReceiCode = gdSet.getString(0, "equ_code");
								// }

								if (realReceiCode == null) {
									realReceiCode = "";
								}
								break;
							}
							if (gdSet.getRowCount() == 0) {
								/**
								 * 睡1秒。
								 */
								try {
									Thread.currentThread().sleep(1000);
								} catch (InterruptedException ex) {
								}
								ti++;
							}
						}
					}

				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		} catch (Exception ex) {
			LogTool.fatal("查询实时指标错误："+ex);
			 try {
				 Operation_log.writeOperLog(user_id, "200201",
				 "查询实时指标错误,原因："+ex.getMessage());
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			return new EXEException("", ex.getMessage(), "");
		}

		 try {
			 Operation_log.writeOperLog(user_id, "200201", "查询实时指标成功！");
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		retStr[0] = cmdMessage.toString();
		retStr[1] = cmdResult;
		retStr[2] = stopURL;
		retStr[3] = checkTime; // 返回的数据开始时间
		retStr[4] = clientStartTime;
		retStr[5] = realReceiCode;
		ASObject resobj = new ASObject();
		resobj.put("clientStartTime", clientStartTime);
		resobj.put("realReceiCode", realReceiCode);
		LogTool.debug("2realReceiCode==" + realReceiCode
				+ "--clientStartTime==" + clientStartTime);
		return resobj;
	}

	/**
	 * 频谱实时查询
	 */
	public Object getSpectrumRealtimeQuery(ASObject asobj) {

		String user_id = (String) asobj.get("userId");
		String code = (String) asobj.get("code");
		String receiverCode = (String) asobj.get("receiverCode");
		String band = (String) asobj.get("band");
		String freq = (String) asobj.get("freq");
		String intervalTime = (String) asobj.get("intervalTime");
		String expires = (String) asobj.get("expireTime");
		String action = (String) asobj.get("action");
		String type = (String) asobj.get("type");
		String stepFreq = (String) asobj.get("freqStep");
		String startFreq = (String) asobj.get("startFreq");
		String endFreq = (String) asobj.get("endFreq");
		String priority = (String) asobj.get("priority");
		String freqNumber = (String) asobj.get("freqNumber");
		String headendtype_id = (String) asobj.get("headendtype_id");
		//String scan_count = (String) asobj.get("scan_count");
		intervalTime = StringTool.getTimeString(Integer.parseInt(intervalTime));
		//expires = StringTool.getTimeString(Integer.parseInt(expires));
		//expires = scan_count;
		String[] retStr = new String[6];
		StringBuffer cmdMessage = new StringBuffer(); // 错误信息
		String cmdResult = action;// "stop"; // 画图命令
		String stopURL = null; // 停机地址
		String checkTime = ""; // 返回的数据开始时间
		String realReceiCode = ""; // 实际工作接收机
		String headendID = SiteVersionUtil.getSiteHeadId(code);
		// 删除原记录
		StringBuilder delSql = new StringBuilder(
				"delete from radio_spectrum_realtime_tab where 1=1 ");
		delSql.append("and head_id = " + headendID + " ");
		DbComponent dbComponent = new DbComponent();
		try {
			dbComponent.exeUpdate(delSql.toString());
		} catch (DbException ex1) {
			ex1.printStackTrace();
		}

		String err = "";
		String result = "";

		Security security = new Security();
		long msgPrio = 0;
		if (user_id != null) {
			try {
				msgPrio = security.getMessagePriority(user_id, 3, 2, 0);
				priority = new Long(msgPrio).toString();
			} catch (Exception ex1) {
				err = ex1.getMessage();
			}
		}

		/**
		 * 转换大小写。
		 */
		code = SiteVersionUtil.getSiteOriCode(code);

		int siteVer = Integer.parseInt(SiteVersionUtil.getSiteVerStr(code)
				.substring(1));
		// int siteVer
		// =Integer.parseInt(InnerDevice.instance(code+frontier).getType().getVersion());
		/**
		 * 定义变量
		 */
		String url = "";
		String recordId = "";

		try {
			// 生成发送消息对象
			MsgSpectrumRealtimeScanCmd cmd = new MsgSpectrumRealtimeScanCmd();
			// 生成返回消息对象
			MsgSpectrumRealtimeScanRes res = new MsgSpectrumRealtimeScanRes();

			// 设置发送消息数据
			cmd.setPriority(priority);
			cmd.setDestCode(code);

			switch (siteVer) {

			case 8:
				cmd.setParamV8FrontierRadioV1(receiverCode, band, startFreq,
						endFreq, stepFreq, intervalTime, expires, freqNumber,
						action, freq, type);
				break;

			case 7:

			case 6:

			case 5:

			case 4:

			case 1:

			}

			// 生成发送处理器
			MessageClient client = new MessageClient();

			// 发送消息，并获得返回结果
			client.execute(cmd, res);

			// 解析返回结果
			// if (action.equalsIgnoreCase("start")) {
			// cmdResult = "start";
			// //停止用的url
			// stopURL = "&headendcode=" + code + "&receivercode=" +
			// receiverCode + "&band=" + band + "&stype=" + "0" +
			// "&interval=" + intervalTime + "&expires=" + expires + "&msgPrio="
			// +
			// priority + "&stop=Stop";
			//
			// stopURL += "&bfreq=" + startFreq + "&efreq=" + endFreq +
			// "&fstep=" + stepFreq;
			//
			// // 获得实际的前端代码
			// if (res.getEquCode() != null && res.getEquCode().length() > 0 &&
			// !receiverCode.equalsIgnoreCase(res.getEquCode())) {
			// realReceiCode = res.getEquCode();
			// }
			// // 获得数据开始时刻
			// if (res.getSpectrumScan() != null) {
			// MsgSpectrumRealtimeScanRes.SpectrumScan q[] =
			// (MsgSpectrumRealtimeScanRes.SpectrumScan[])
			// res.getSpectrumScan().toArray(new
			// MsgSpectrumRealtimeScanRes.
			// SpectrumScan[0]);
			// if (q.length > 0) {
			// checkTime = q[0].getScanTime();
			// if (checkTime != null && checkTime.length() > 0) {
			// checkTime = new Long(StringTool.stringToDate(checkTime).
			// getTime()).
			// toString();
			// }
			// }
			// }
			// }

			/*
			 * // 获得实际的前端代码 if (res.getEquCode() != null &&
			 * res.getEquCode().length() > 0 &&
			 * !receiverCode.equalsIgnoreCase(res.getEquCode())) {
			 * 
			 * String realReceiCode = res.getEquCode();
			 * System.out.println("realReceiCode = "+realReceiCode); }
			 * 
			 * Collection resu = res.getSpectrumScan();
			 * 
			 * if(resu != null) {
			 * 
			 * Iterator i = resu.iterator();
			 * 
			 * while(i.hasNext()) {
			 * 
			 * MsgSpectrumRealtimeScanRes.SpectrumScan spectrum =
			 * (MsgSpectrumRealtimeScanRes.SpectrumScan) i.next();
			 * 
			 * String check_datetime = spectrum.getScanTime();
			 * System.out.println("check_datetime = "+check_datetime);
			 * 
			 * Collection scanresult = spectrum.getScanResults(); if(scanresult
			 * != null) {
			 * 
			 * Iterator q = scanresult.iterator();
			 * 
			 * while (q.hasNext()) {
			 * 
			 * MsgSpectrumRealtimeScanRes.ScanResult scan =
			 * (MsgSpectrumRealtimeScanRes.ScanResult) q.next(); String
			 * frequency = scan.getFreq();
			 * System.out.println("scan.getFreq() = " + frequency); String
			 * e_level = scan.getLevel();
			 * System.out.println("scan.getLevel() = " + e_level); } } } }
			 */
			if (action.equalsIgnoreCase("Stop")) {
				String subsql = "";
				if (!receiverCode.equalsIgnoreCase("")) {
					subsql = " and equ_code='" + receiverCode + "' ";
				}
				// v8广播
				subsql += " and band = " + band;
				String sql = "delete from radio_spectrum_realtime_tab where head_id="
						+ headendID + subsql;
				DbComponent.exeUpdate(sql);
				return StringTool.getXmlRightMessage("停止实时频谱成功");
			} else {
				String clientStartTime = res.getResult().getheadTime();
				// System.out.println("spectrumBeginTime======"+clientStartTime);
				if (clientStartTime == null || clientStartTime.equals("")) {
					clientStartTime = StringTool.Date2String(new Date());
					checkTime = clientStartTime;
				}
				GDSet gdSet = null;
				String da = StringTool.Date2String(new Date());
				String taskTime = "select to_char(check_datetime,'yyyyMMddHH24miss') check_datetime,equ_code from radio_spectrum_realtime_tab where head_id='"
						+ headendID
						+ "' and  check_datetime in ( select max(check_datetime) from radio_spectrum_realtime_tab where head_id='"
						+ headendID
						+ "' ) "
						+ " and check_datetime>=to_date('"
						+ clientStartTime + "','yyyy-mm-dd hh24:mi:ss')";
				/**
				 * 睡1秒,共找5次或找到即结束。
				 */
//				if (headendtype_id.equals("103")) {
					for (int ti = 0; ti < 35; ti++) {
						gdSet = DbComponent.Query(taskTime);
						/**
						 * 不知道，顶层applet如何使用，只能与页面使用一致。
						 */

						if (gdSet.getRowCount() > 0) {
							checkTime = gdSet.getString(0, "check_datetime");
							realReceiCode = gdSet.getString(0, "equ_code");
							break;
						} else {
							/**
							 * 睡1秒。
							 */
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException ex) {
							}
							ti++;
						}
					}
//				}
				Element ele = new Element("datamessage");
				ele.addAttribute("clientStartTime", checkTime);
				ele.addAttribute("realReceiCode", realReceiCode);
				ArrayList list = new ArrayList();
				list.add(ele);

				ASObject resobj = new ASObject();
				resobj.put("clientStartTime", checkTime);
				resobj.put("realReceiCode", realReceiCode);
				try {
					 Operation_log.writeOperLog(user_id, "200202", "站点"+code+"实时频谱查询成功！");
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				return resobj;
			}
		} catch (Exception ex) {
			LogTool.fatal(ex);
			try {
				if (action.equalsIgnoreCase("Stop")) {
					String subsql = "";
					if (!receiverCode.equalsIgnoreCase("")) {
						subsql = " and equ_code='" + receiverCode + "' ";
					}
					// v8广播
					subsql += " and band = " + band;
					String sql = "delete from radio_spectrum_realtime_tab where head_id="
							+ headendID + subsql;
					DbComponent.exeUpdate(sql);
				}
				 Operation_log.writeOperLog(user_id, "200202", "站点"+code+"实时频谱"+action+"失败！");
			} catch (Exception ee) {
				LogTool.fatal(ee);
			}
			return new EXEException("", "实时频谱错误：" + ex.getMessage(), "");
		}

		// return result;

	}

	/**
	 * 设备实时状态查询
	 * @detail
	 * @method
	 * @param msg
	 * @return
	 * @return Object
	 * @author zhaoyahui
	 * @version 2012-8-10 上午10:27:28
	 */
	public Object UpEquipmentStatusRealtimeQuery(ASObject asobj) {
		String code = (String) asobj.get("code");
		String priority = "";
		String interval = (String) asobj.get("interval"); // 上报间隔
		String expires = (String) asobj.get("expires"); // 过期时间
		String action = ""; // 停止标志
		String sampleNumber = (String) asobj.get("sampleInterval");// 采样时间
		String deviceType = "";
		String userID = (String) asobj.get("userId");
		String stop = (String) asobj.get("stop"); // 停止标志
		String error = "";
		String headendID = "";
		String headType = "";
		if (code != null) {
			code = SiteVersionUtil.getSiteOriCode(code);
			headendID = SiteVersionUtil.getSiteHeadId(code);
			headType = SiteVersionUtil.getSiteType(code);
		} else {
			error = "站点代码不能为空!";
		}
		// 设置优先级
		long msgPrio = 1;

		if (userID != null) {
			Security security = new Security();
			try {
				msgPrio = security.getMessagePriority(userID, 3, 2, 0);
			} catch (Exception ex1) {
				error = "获得用户权限失败!";
			}
			priority = new Long(msgPrio).toString();
		}
		if (error != "") {
			return StringTool.getXmlErrorMessage(error);
		}
		if (headType.equals("103")) {
			deviceType = "common";

			// 边境需要此参数。
			if (stop.equals("1")) {
				action = "Start";
			} else {
				action = "Stop";
			}
		}

		String result = "";
		try {
			// 生成发送消息对象
			MsgEquipmentStatusRealtimeQueryCmd cmd = new MsgEquipmentStatusRealtimeQueryCmd();
			// 生成返回消息对象
			MsgEquipmentStatusRealtimeQueryRes res = new MsgEquipmentStatusRealtimeQueryRes();

			// 设置发送消息数据
			cmd.setDestCode(code + deviceType);
			cmd.setPriority(priority);
			// 设置缺省间隔与过期时间
			int intVal;
			if (interval != null) {
				intVal = new Integer(interval).intValue();
			} else {
				intVal = 5;
				interval = new Integer(intVal).toString();
			}

			int sampVal;
			if (sampleNumber != null) {
				sampVal = new Integer(sampleNumber).intValue();
			} else {
				sampVal = 1;
				sampleNumber = new Integer(sampVal).toString();
			}

			int expVal;
			if (expires == null) {
				expVal = intVal * 60;
				expires = new Integer(intVal).toString();
			} else {
				expVal = new Integer(expires).intValue() * 60;

			}
			if (stop != null && !stop.equalsIgnoreCase("1")) {
				expVal = 0;
			}
			cmd.setAttrs(StringTool.getTimeString(intVal), StringTool.getTimeString(sampVal),
					StringTool.getTimeString(expVal), action);

			// 生成发送处理器
			MessageClient client = new MessageClient();
			// 发送消息，并获得返回结果
			client.execute(cmd, res);
			if (stop == null || stop.equalsIgnoreCase("0")) {
				MonCacheAccessor.freeCacheData(EQU_CACHE_PREFIX + headendID);
			}
			// Collection resList = res.getEquStatus();
			// if (resList != null) {
			// MessageElement ele= res.returnBody();
			// ArrayList list = new ArrayList();
			// list.add(ele);
			// result = StringTool.getXmlStrFromList(list);
			// Collection coll = res.getEquStatus();
			// Iterator it = coll.iterator();
			// if(it.hasNext()){
			// MsgEquipmentStatusRealtimeQueryRes.EquStatus equs =
			// (MsgEquipmentStatusRealtimeQueryRes.EquStatus)it.next();
			// return StringTool.getXmlErrorMessage("res_checkdatetime",
			// equs.getCheckDateTime());
			// }
			// }
			String description = "获取实时站点" + code + "状态信息成功!";
			try {
				Operation_log.writeOperLog(userID, "200203", description);
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			if (res.getheadTime() != null || !res.getheadTime().equals("")) {
				ASObject resobj = new ASObject();
				LogTool.info("StationStatusStartTime======================="+ res.getheadTime());
				resobj.put("clientStartTime", res.getheadTime());
				return resobj;
			}
			
		} catch (DeviceException ex) {
			String description = "获取实时站点" + code + "状态信息失败!";
			try {
				Operation_log.writeOperLog(userID, "200203", description);
			} catch (Exception ex1) {
			}
			com.viewscenes.util.LogTool.debug("devicelog", ex);

			return new EXEException("", "获取实时站点错误：" + ex.getMessage(), "");

		}

		ASObject resobj = new ASObject();
		resobj.put("clientStartTime", StringTool.Date2String(new Date()));
		return resobj;
	}

	/**
	 * @detail
	 * @method
	 * @param args
	 * @return void
	 * @author zhaoyahui
	 * @version 2012-7-28 下午04:02:44
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
