package com.viewscenes.device.api;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgDefaultRes;
import com.viewscenes.device.radio.MsgEquipmentAlarmHistoryQueryCmd;
import com.viewscenes.device.radio.MsgEquipmentAlarmHistoryQueryRes;
import com.viewscenes.device.radio.MsgEquipmentAlarmParamSetCmd;
import com.viewscenes.device.radio.MsgEquipmentInitParamSetCmd;
import com.viewscenes.device.radio.MsgEquipmentLogHistoryQueryCmd;
import com.viewscenes.device.radio.MsgEquipmentLogHistoryQueryRes;
import com.viewscenes.device.radio.MsgEquipmentStatusRealtimeQueryCmd;
import com.viewscenes.device.radio.MsgEquipmentStatusRealtimeQueryRes;

public class EquipmentAPI {
   /**
    * 设备报警回收接口
    * @param code
    * @param startTime
    * @param endTime
    * @param alarmType
    * @param priority
    * @return
    * @throws DeviceNotExistException
    * @throws DeviceFilterException
    * @throws DeviceProcessException
    * @throws DeviceTimedOutException
    * @throws DeviceReportException
    */
	public static Collection msgEquipmentAlarmHistoryQueryCmd(String code,
			String startTime, String endTime, String alarmType, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {

		// 生成发送消息对象
		MsgEquipmentAlarmHistoryQueryCmd cmd = new MsgEquipmentAlarmHistoryQueryCmd();
		// 生成返回消息对象
		MsgEquipmentAlarmHistoryQueryRes res = new MsgEquipmentAlarmHistoryQueryRes();

		// 设置发送消息数据
		cmd.setDestCode(code);

		cmd.setPriority(priority);

		cmd.setEquipmentAlarmQueryParams(startTime, endTime, alarmType);

		// 生成发送处理器
		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getEquipmentAlarms();
	}

	public static void msgEquipmentAlarmParamSetCmd(String code,
			String amparam, String fmparam, String offsetparam,
			String equparam, String voiceparam,
			String inputLineLevelUpThreshold1Gd,
			String inputLineLevelDownThreshold1Gd, String length1Gd,
			String priority) throws DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException, DeviceNotExistException {
		// 生成发送消息对象
		MsgEquipmentAlarmParamSetCmd cmd = new MsgEquipmentAlarmParamSetCmd();

		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();

		cmd.setDestCode(code);
		cmd.setAmCardParam(amparam);
		cmd.setFmCardParam(fmparam);
		cmd.setOffsetParam(offsetparam);
		cmd.setEquParam(equparam);
		//cmd.setYsParam(ysparam);
		//cmd.setDsCardParam(dsparam);
		cmd.setVoiceCardParam(voiceparam);

		cmd.setLevelParam("", "", inputLineLevelUpThreshold1Gd,
				inputLineLevelDownThreshold1Gd, "", length1Gd);
		cmd.setPriority(priority);

		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);

	}

	public static void msgEquipmentInitParamSetCmd(String code,
			Vector initParams, Vector centerParams, Vector loginfoParams,
			Map systemParams, String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgEquipmentInitParamSetCmd cmd = new MsgEquipmentInitParamSetCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();
		cmd.setEquipmentInitParams(initParams);

		cmd.setSystemEquInitParams(code, centerParams, loginfoParams,
				systemParams);
		cmd.setDestCode(code);

		cmd.setPriority(priority);

		// 生成发送处理器
		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果
		client.execute(cmd, res);
	}
/**
 * 设备日志回收接口
 * @param code
 * @param startDatetime
 * @param endDatetime
 * @param logType
 * @param priority
 * @return
 * @throws DeviceNotExistException
 * @throws DeviceFilterException
 * @throws DeviceProcessException
 * @throws DeviceTimedOutException
 * @throws DeviceReportException
 */
	public static Collection msgEquipmentLogHistoryQueryCmd(String code,
			String startDatetime, String endDatetime, String logType,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgEquipmentLogHistoryQueryCmd cmd = new MsgEquipmentLogHistoryQueryCmd();
		// 生成返回消息对象
		MsgEquipmentLogHistoryQueryRes res = new MsgEquipmentLogHistoryQueryRes();

		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setParams(startDatetime, endDatetime, logType);

		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果

		client.execute(cmd, res);

		return res.getEquipmentLogs();
	}

	public static Collection msgEquipmentStatusRealtimeQueryCmd(String code,
			String reportInterval, String sampleInterval, String expireTime,
			String action, String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		MsgEquipmentStatusRealtimeQueryCmd cmd = new MsgEquipmentStatusRealtimeQueryCmd();
		// 生成返回消息对象
		MsgEquipmentStatusRealtimeQueryRes res = new MsgEquipmentStatusRealtimeQueryRes();
		// 设置发送消息数据
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setAttrs(reportInterval, sampleInterval, expireTime, action);
		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getEquStatus();
	}
}
