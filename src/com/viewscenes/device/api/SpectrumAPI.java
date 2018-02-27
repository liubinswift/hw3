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
 * 频谱数据回收接口
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
		// 生成发送消息对象
		MsgSpectrumHistoryQueryCmd cmd = new MsgSpectrumHistoryQueryCmd();
		// 生成返回消息对象
		MsgSpectrumHistoryQueryRes res = new MsgSpectrumHistoryQueryRes();

		// 设置发送消息数据
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		cmd.setParamV8V7V6(equcode, taskid, startDate, endDate, sampleNumber,
				unit);

		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果
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
		// 生成发送消息对象
		MsgSpectrumRealtimeScanCmd cmd = new MsgSpectrumRealtimeScanCmd();
		// 生成返回消息对象
		MsgSpectrumRealtimeScanRes res = new MsgSpectrumRealtimeScanRes();

		// 设置发送消息数据
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		cmd.setParamV8FrontierRadioV1(equcode, band, startFreq, endFreq,
						stepFreq, intervalTime, expires, freqNumber, action,
						freq, type);
		// 生成发送处理器
		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getResult();
	}

	public static void msgSpectrumTaskSetCmd(String code, ArrayList list,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		MsgSpectrumTaskSetCmd cmd = new MsgSpectrumTaskSetCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();

		cmd.setList(list);
		cmd.setPriority(priority);

		cmd.setDestCode(code);
		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);
	}

	
}
