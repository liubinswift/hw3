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
import com.viewscenes.device.radio.MsgOffsetHistoryQueryCmd;
import com.viewscenes.device.radio.MsgOffsetHistoryQueryRes;
import com.viewscenes.device.radio.MsgOffsetTaskSetCmd;

public class OffsetAPI {
/**
 * 频偏数据回收接口
 * @param code
 * @param equcode
 * @param freq
 * @param band
 * @param taskId
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
	public static MsgOffsetHistoryQueryRes.Result msgOffsetHistoryQueryCmd(String code,
			String equcode, String freq, String band, String taskId,
			String startDate, String endDate, String sampleNumber, String unit,String priority) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgOffsetHistoryQueryCmd cmd = new MsgOffsetHistoryQueryCmd();
		// 生成返回消息对象
		MsgOffsetHistoryQueryRes res = new MsgOffsetHistoryQueryRes();

		// 设置发送消息数据
		cmd.setPriority(priority);
		cmd.setDestCode(code);

		cmd.setParamV8V7V6(equcode, taskId, freq, band, startDate,
				endDate, sampleNumber, unit);

		// 生成发送处理器

		MessageClient client = new MessageClient();

		client.execute(cmd, res);
		
		return res.getResult();
	}
	
	public static void msgOffsetTaskSetCmd(String code,ArrayList list,String priority) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException{
		MsgOffsetTaskSetCmd cmd = new MsgOffsetTaskSetCmd();
		MsgDefaultRes res = new MsgDefaultRes();
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		
		cmd.setList(list);
		MessageClient client = new MessageClient();
		client.execute(cmd, res);
	}
}
