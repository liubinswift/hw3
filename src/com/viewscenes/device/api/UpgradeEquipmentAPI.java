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
		// ���ɷ�����Ϣ����
		MsgUpgradeEquipmentCmd cmd = new MsgUpgradeEquipmentCmd();
		// ���ɷ�����Ϣ����
		MsgDefaultRes res = MsgDefaultRes.instance();
        cmd.setParams(url, username, password);
       
		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);


		// ���ɷ��ʹ�����

		MessageClient client = new MessageClient();

		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);

	}
}
