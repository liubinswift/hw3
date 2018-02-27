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
		// ���ɷ�����Ϣ����
		MsgQualityAlarmHistoryQueryCmd cmd = new MsgQualityAlarmHistoryQueryCmd();

		// ���ɷ�����Ϣ����
		MsgQualityAlarmHistoryQueryRes res = new MsgQualityAlarmHistoryQueryRes();

		// ���÷�����Ϣ����
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		// //���վ��İ汾
		cmd.setParams(equcode, freq, band, startDate, endDate);
		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��
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
		// ���ɷ�����Ϣ����
		MsgQualityAlarmParamSetCmd cmd = new MsgQualityAlarmParamSetCmd();
		// ���ɷ�����Ϣ����
		MsgDefaultRes res = MsgDefaultRes.instance();

		// ���÷�����Ϣ����
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

		// ���ɷ��ʹ�����

		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);

	}
  /**
   * ָ�����ݻ����·��ӿ�
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
		// ���ɷ�����Ϣ����
		MsgQualityHistoryQueryRes res = new MsgQualityHistoryQueryRes();

		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);
		cmd.setParamV8V7V6(equcode, taskid, freq, band, startDate, endDate,
				sampleNumber, unit);

		cmd.setQueryTypes(queryTypes);
		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��
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
		// ���ɷ�����Ϣ����
		MsgQualityRealtimeQueryRes res = new MsgQualityRealtimeQueryRes();
		// ���÷�����Ϣ����
		cmd.setDestCode(code);

		cmd.setQueryTypes(queryTypes);

		cmd.setPriority(priority);
		// if (stop == null || stop.equalsIgnoreCase("false"))
		cmd.setParams(equcode, freq, band, intVal, expVal, stop);

		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);

		return res.getResult();
	}

	public static void msgQualityReportTaskSetCmd(String code,
			ArrayList taskList, String priority)
			throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		// ���ɷ�����Ϣ����
		MsgQualityReportTaskSetCmd cmd = new MsgQualityReportTaskSetCmd();
		// ���ɷ�����Ϣ����
		MsgDefaultRes res = MsgDefaultRes.instance();

		cmd.setDestCode(code);
		cmd.setList(taskList);
		
		cmd.setPriority(priority);
		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);
	}
	
	

}
