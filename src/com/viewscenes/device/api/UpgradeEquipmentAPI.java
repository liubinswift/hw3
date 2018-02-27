package com.viewscenes.device.api;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgDefaultRes;
import com.viewscenes.device.radio.MsgUpgradeEquipmentCmd;

public class UpgradeEquipmentAPI {
	public static void msgQualityAlarmParamSetCmd(String code, String url,
			String username, String password,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgUpgradeEquipmentCmd cmd = new MsgUpgradeEquipmentCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();
        cmd.setParams(url, username, password);
       
		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);


		// 生成发送处理器

		MessageClient client = new MessageClient();

		// 发送消息，并获得返回结果

		client.execute(cmd, res);

	}
}
