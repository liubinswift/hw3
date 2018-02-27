package com.viewscenes.device.api;

import java.util.Collection;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgReceiverControlCmd;
import com.viewscenes.device.radio.MsgReceiverControlRes;

public class ReceiverAPI {
	
	public static MsgReceiverControlRes.Result msgReceiverControlCmd(String code, String equcode,
			String action, String expireTime, Collection ctlCmd, String priority)
			throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException{
		MsgReceiverControlCmd cmd = new MsgReceiverControlCmd();
		MsgReceiverControlRes res = new MsgReceiverControlRes();
		
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setAttrs(equcode, action, expireTime, ctlCmd);

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);
		
		return res.getResult();
	}
}
