package com.viewscenes.device.api;

import com.viewscenes.device.exception.*;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.*;

import java.util.Collection;

public class FileAPI {
    public static boolean msgStreamRecoveryReportCmdCmd(String code,
                                          String startTime, String endTime, String priority)
            throws DeviceNotExistException, DeviceFilterException,
            DeviceProcessException, DeviceTimedOutException,
            DeviceReportException {
        StreamRecoveryReportCmd cmd = new StreamRecoveryReportCmd();
        // ���ɷ�����Ϣ����
        MsgDefaultRes res = MsgDefaultRes.instance();
        cmd.setDestCode(code);
        cmd.setPriority(priority);
        cmd.setParams(code, startTime, endTime);
        MessageClient client = new MessageClient();
        // ������Ϣ������÷��ؽ��
        client.execute(cmd, res);
        return true;
    }
	public static Collection msgFileQueryCmd(String code, String resultType,
			String startTime, String endTime, String requestTaskId,
			Collection<MsgFileQueryCmd.Frequency> freqList, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgFileQueryCmd cmd = new MsgFileQueryCmd();
		// ���ɷ�����Ϣ����
		MsgFileQueryRes res = new MsgFileQueryRes();
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setParams(resultType, startTime, endTime, requestTaskId);
		cmd.setFrequencys(freqList);
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��
		client.execute(cmd, res);
		return res.getResultInfos();
	}

	public static Collection msgFileRetrieveCmd(String code,
			Collection fileNames, String priority)
			throws DeviceNotExistException, DeviceFilterException, DeviceProcessException, DeviceTimedOutException, DeviceReportException{
		MsgFileRetrieveCmd cmd = new MsgFileRetrieveCmd();
		// ���ɷ�����Ϣ����
		MsgFileRetrieveRes res = new MsgFileRetrieveRes();
		// ���÷�����Ϣ����
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setAttrs(fileNames);

		// ���ɷ��ʹ�����
		MessageClient client = new MessageClient();
		// ������Ϣ������÷��ؽ��

		client.execute(cmd, res);

		return res.getValues();
	}



}
