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
    * �豸�������սӿ�
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

		// ���ɷ�����Ϣ����
		MsgEquipmentAlarmHistoryQueryCmd cmd = new MsgEquipmentAlarmHistoryQueryCmd();
		// ���ɷ�����Ϣ����
		MsgEquipmentAlarmHistoryQueryRes res = new MsgEquipmentAlarmHistoryQueryRes();

		// ���÷�����Ϣ����
		cmd.setDestCode(code);

		cmd.setPriority(priority);

		cmd.setEquipmentAlarmQueryParams(startTime, endTime, alarmType);

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��
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
		// ���ɷ�����Ϣ����
		MsgEquipmentAlarmParamSetCmd cmd = new MsgEquipmentAlarmParamSetCmd();

		// ���ɷ�����Ϣ����
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

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);

	}

	public static void msgEquipmentInitParamSetCmd(String code,
			Vector initParams, Vector centerParams, Vector loginfoParams,
			Map systemParams, String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// ���ɷ�����Ϣ����
		MsgEquipmentInitParamSetCmd cmd = new MsgEquipmentInitParamSetCmd();
		// ���ɷ�����Ϣ����
		MsgDefaultRes res = MsgDefaultRes.instance();
		cmd.setEquipmentInitParams(initParams);

		cmd.setSystemEquInitParams(code, centerParams, loginfoParams,
				systemParams);
		cmd.setDestCode(code);

		cmd.setPriority(priority);

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);
	}
/**
 * �豸��־���սӿ�
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
		// ���ɷ�����Ϣ����
		MsgEquipmentLogHistoryQueryCmd cmd = new MsgEquipmentLogHistoryQueryCmd();
		// ���ɷ�����Ϣ����
		MsgEquipmentLogHistoryQueryRes res = new MsgEquipmentLogHistoryQueryRes();

		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setParams(startDatetime, endDatetime, logType);

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);

		return res.getEquipmentLogs();
	}

	public static Collection msgEquipmentStatusRealtimeQueryCmd(String code,
			String reportInterval, String sampleInterval, String expireTime,
			String action, String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		MsgEquipmentStatusRealtimeQueryCmd cmd = new MsgEquipmentStatusRealtimeQueryCmd();
		// ���ɷ�����Ϣ����
		MsgEquipmentStatusRealtimeQueryRes res = new MsgEquipmentStatusRealtimeQueryRes();
		// ���÷�����Ϣ����
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setAttrs(reportInterval, sampleInterval, expireTime, action);
		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);

		return res.getEquStatus();
	}
}
