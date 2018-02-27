package com.viewscenes.device.api;

import java.util.Collection;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgDefaultRes;
import com.viewscenes.device.radio.MsgTaskDeleteCmd;
import com.viewscenes.device.radio.MsgTaskStatusQueryCmd;
import com.viewscenes.device.radio.MsgTaskStatusQueryRes;

public class TaskAPI {
	public static boolean msgTaskDeleteCmd(String code, String srcCode,
			String freq, String band, String valid_startdatetime,
			String valid_enddatetime, String date, String taskType,
			String priority,String task_id) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		
		// 生成发送消息对象
		MsgTaskDeleteCmd cmd = new MsgTaskDeleteCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();

		cmd.setPriority(priority);

		cmd.setDestCode(code);
		cmd.setAttrs(band, freq,task_id, valid_startdatetime, valid_enddatetime,
				srcCode, date, taskType);
		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);
		return true;
	}

	public static Collection msgTaskStatusQueryCmd(String code,
			Collection params, String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		MsgTaskStatusQueryCmd cmd = new MsgTaskStatusQueryCmd();
		// 生成返回消息对象
		MsgTaskStatusQueryRes res = new MsgTaskStatusQueryRes();
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setAttrs(params);
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);
		
		return res.getTaskStatus();
	}
}
