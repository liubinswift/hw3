package com.viewscenes.device.api;

import java.util.Collection;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgProgramCommandCmd;
import com.viewscenes.device.radio.MsgProgramCommandRes;

public class ProgramCommandAPI {

	public static Collection queryAlive(String code, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// 生成返回消息对象
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setAliveQuery("AliveQuery"); // 监测软件存活查询。

		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果

		client.execute(cmd, res);
		// 解析返回结果
		return res.getProgramInfos();
	}

	public static Collection getEquipmentVersion(String code, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// 生成返回消息对象
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);
		cmd.setAllAttr("", null, null, null);

		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);

		return res.getProgramInfos();
	}

	public static void reStartEquipment(String code, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// 生成返回消息对象
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setCommandName("Reset"); // 重启命令。

		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果

		client.execute(cmd, res);
	}

	public static void setEquipmentTime(String code, String priority) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// 生成返回消息对象
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// 设置发送消息数据
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setCommandName(1); // 计算机授时命令。

		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果

		client.execute(cmd, res);
	}
}
