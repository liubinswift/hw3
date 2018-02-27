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
		// ���ɷ�����Ϣ����
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setAliveQuery("AliveQuery"); // ����������ѯ��

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);
		// �������ؽ��
		return res.getProgramInfos();
	}

	public static Collection getEquipmentVersion(String code, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// ���ɷ�����Ϣ����
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);
		cmd.setAllAttr("", null, null, null);

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);

		return res.getProgramInfos();
	}

	public static void reStartEquipment(String code, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// ���ɷ�����Ϣ����
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setCommandName("Reset"); // �������

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);
	}

	public static void setEquipmentTime(String code, String priority) throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException {
		// ���ɷ�����Ϣ����
		MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();
		// ���ɷ�����Ϣ����
		MsgProgramCommandRes res = new MsgProgramCommandRes();
		// ���÷�����Ϣ����
		cmd.setPriority(priority);

		cmd.setDestCode(code);

		cmd.setCommandName(1); // �������ʱ���

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);
	}
}
