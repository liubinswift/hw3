package com.viewscenes.device.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgDefaultRes;
import com.viewscenes.device.radio.MsgQualityAlarmHistoryQueryCmd;
import com.viewscenes.device.radio.MsgQualityAlarmHistoryQueryRes;
import com.viewscenes.device.radio.MsgQualityAlarmParamSetCmd;
import com.viewscenes.device.radio.MsgQualityHistoryQueryCmd;
import com.viewscenes.device.radio.MsgQualityHistoryQueryRes;
import com.viewscenes.device.radio.MsgQualityRealtimeQueryCmd;
import com.viewscenes.device.radio.MsgQualityRealtimeQueryRes;
import com.viewscenes.device.radio.MsgQualityReportTaskSetCmd;
import com.viewscenes.device.radio.MsgReceiverControlCmd;
import com.viewscenes.device.radio.MsgReceiverControlRes;

public class QualityAPI {

	public static MsgQualityAlarmHistoryQueryRes.Result msgQualityAlarmHistoryQueryCmd(String code,
			String equcode, String freq, String band, String startDate,
			String endDate, String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgQualityAlarmHistoryQueryCmd cmd = new MsgQualityAlarmHistoryQueryCmd();

		// 生成返回消息对象
		MsgQualityAlarmHistoryQueryRes res = new MsgQualityAlarmHistoryQueryRes();

		// 设置发送消息数据
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		// //获得站点的版本
		cmd.setParams(equcode, freq, band, startDate, endDate);
		// 生成发送处理器
		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getResult();
	}

	public static void msgQualityAlarmParamSetCmd(String code, String equCode,
			String freq, String band, String attenuation,
			MsgQualityAlarmParamSetCmd.FmParam fmParam,
			MsgQualityAlarmParamSetCmd.AmParam amParam,
			String level_downThreshold, String level_abnormityLength,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgQualityAlarmParamSetCmd cmd = new MsgQualityAlarmParamSetCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();

		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		if (band.equalsIgnoreCase("9")) {
			cmd.setParams(equCode, freq, "");
		} else {
			cmd.setParams(equCode, freq, band);
		}

		cmd.setLevelParam(level_downThreshold, level_abnormityLength);

		if (band.equalsIgnoreCase("2")) {

			cmd.setFMModulationParam(fmParam.getFm_sampleLength(), fmParam
					.getFm_downThreshold(), fmParam.getFm_upThreshold(),
					fmParam.getFm_upAbnormityRate(), fmParam
							.getFm_downAbnormityRate(), fmParam
							.getFm_abnormityLength());
		}

		if ((band.equalsIgnoreCase("1")) || (band.equalsIgnoreCase("0"))) {

			cmd.setAMModulationParam(amParam.getSampleLength(), amParam
					.getDownThreshold(), amParam.getUpThreshold(), amParam
					.getUpAbnormityRate(), amParam.getDownAbnormityRate(),
					amParam.getAbnormityLength());
		}

		if (band.equalsIgnoreCase("") && (freq.equalsIgnoreCase(""))) {

			cmd.setFMModulationParam(fmParam.getFm_sampleLength(), fmParam
					.getFm_downThreshold(), fmParam.getFm_upThreshold(),
					fmParam.getFm_upAbnormityRate(), fmParam
							.getFm_downAbnormityRate(), fmParam
							.getFm_abnormityLength());

			cmd.setAMModulationParam(amParam.getSampleLength(), amParam
					.getDownThreshold(), amParam.getUpThreshold(), amParam
					.getUpAbnormityRate(), amParam.getDownAbnormityRate(),
					amParam.getAbnormityLength());
		}

		cmd.setAttenuationParam(attenuation);

		// 生成发送处理器

		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果

		client.execute(cmd, res);

	}
  /**
   * 指标数据回收下发接口
   * @param code
   * @param equcode
   * @param freq
   * @param band
   * @param taskid
   * @param startDate
   * @param endDate
   * @param sampleNumber
   * @param unit
   * @param queryTypes
   * @param priority
   * @return
   * @throws DeviceNotExistException
   * @throws DeviceFilterException
   * @throws DeviceProcessException
   * @throws DeviceTimedOutException
   * @throws DeviceReportException
   */
	public static MsgQualityHistoryQueryRes.Result msgQualityHistoryQueryCmd(String code,
			String equcode, String freq, String band, String taskid,
			String startDate, String endDate, String sampleNumber, String unit,
			ArrayList queryTypes, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgQualityHistoryQueryCmd cmd = new MsgQualityHistoryQueryCmd();
		// 生成返回消息对象
		MsgQualityHistoryQueryRes res = new MsgQualityHistoryQueryRes();

		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);
		cmd.setParamV8V7V6(equcode, taskid, freq, band, startDate, endDate,
				sampleNumber, unit);

		cmd.setQueryTypes(queryTypes);
		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getResult();
	}

	public static MsgQualityRealtimeQueryRes.Result msgQualityRealtimeQueryCmd(String code,
			String equcode, String freq, String band, String intVal,
			String expVal, String stop, ArrayList queryTypes, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgQualityRealtimeQueryCmd cmd = new MsgQualityRealtimeQueryCmd();
		// 生成返回消息对象
		MsgQualityRealtimeQueryRes res = new MsgQualityRealtimeQueryRes();
		// 设置发送消息数据
		cmd.setDestCode(code);

		cmd.setQueryTypes(queryTypes);

		cmd.setPriority(priority);
		// if (stop == null || stop.equalsIgnoreCase("false"))
		cmd.setParams(equcode, freq, band, intVal, expVal, stop);

		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getResult();
	}

	public static void msgQualityReportTaskSetCmd(String code,
			ArrayList taskList, String priority)
			throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgQualityReportTaskSetCmd cmd = new MsgQualityReportTaskSetCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();

		cmd.setDestCode(code);
		cmd.setList(taskList);
		
		cmd.setPriority(priority);
		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);
	}
	
	

}
