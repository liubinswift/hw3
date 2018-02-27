package com.viewscenes.device.api;

import java.util.ArrayList;
import java.util.Collection;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgDefaultRes;
import com.viewscenes.device.radio.MsgSpectrumHistoryQueryCmd;
import com.viewscenes.device.radio.MsgSpectrumHistoryQueryRes;
import com.viewscenes.device.radio.MsgSpectrumRealtimeScanCmd;
import com.viewscenes.device.radio.MsgSpectrumRealtimeScanRes;
import com.viewscenes.device.radio.MsgSpectrumTaskSetCmd;
import com.viewscenes.device.radio.MsgStreamHistoryQueryCmd;
import com.viewscenes.device.radio.MsgStreamHistoryQueryRes;

public class SpectrumAPI {
/**
 * Ƶ�����ݻ��սӿ�
 * @param code
 * @param equcode
 * @param taskid
 * @param startDate
 * @param endDate
 * @param sampleNumber
 * @param unit
 * @param priority
 * @return
 * @throws DeviceNotExistException
 * @throws DeviceFilterException
 * @throws DeviceProcessException
 * @throws DeviceTimedOutException
 * @throws DeviceReportException
 */
	public static MsgSpectrumHistoryQueryRes.Result msgSpectrumHistoryQueryCmd(String code,
			String equcode, String taskid, String startDate, String endDate,
			String sampleNumber, String unit, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		// ���ɷ�����Ϣ����
		MsgSpectrumHistoryQueryCmd cmd = new MsgSpectrumHistoryQueryCmd();
		// ���ɷ�����Ϣ����
		MsgSpectrumHistoryQueryRes res = new MsgSpectrumHistoryQueryRes();

		// ���÷�����Ϣ����
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		cmd.setParamV8V7V6(equcode, taskid, startDate, endDate, sampleNumber,
				unit);

		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);

		return res.getResult();
	}

	public static MsgSpectrumRealtimeScanRes.Result msgSpectrumRealtimeScanCmd(String code,
			String equcode, String freq, String action, String type,
			String band, String startFreq, String endFreq, String stepFreq,
			String freqNumber, String intervalTime, String expires,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// ���ɷ�����Ϣ����
		MsgSpectrumRealtimeScanCmd cmd = new MsgSpectrumRealtimeScanCmd();
		// ���ɷ�����Ϣ����
		MsgSpectrumRealtimeScanRes res = new MsgSpectrumRealtimeScanRes();

		// ���÷�����Ϣ����
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		cmd.setParamV8FrontierRadioV1(equcode, band, startFreq, endFreq,
						stepFreq, intervalTime, expires, freqNumber, action,
						freq, type);
		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);

		return res.getResult();
	}

	public static void msgSpectrumTaskSetCmd(String code, ArrayList list,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		MsgSpectrumTaskSetCmd cmd = new MsgSpectrumTaskSetCmd();
		// ���ɷ�����Ϣ����
		MsgDefaultRes res = MsgDefaultRes.instance();

		cmd.setList(list);
		cmd.setPriority(priority);

		cmd.setDestCode(code);
		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);
	}

	
}
