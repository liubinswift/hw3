package com.viewscenes.device.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.viewscenes.device.exception.DeviceFilterException;
import com.viewscenes.device.exception.DeviceNotExistException;
import com.viewscenes.device.exception.DeviceProcessException;
import com.viewscenes.device.exception.DeviceReportException;
import com.viewscenes.device.exception.DeviceTimedOutException;
import com.viewscenes.device.framework.MessageClient;
import com.viewscenes.device.radio.MsgDefaultRes;
import com.viewscenes.device.radio.MsgQualityStreamTaskSetCmd;
import com.viewscenes.device.radio.MsgStreamHistoryQueryCmd;
import com.viewscenes.device.radio.MsgStreamHistoryQueryRes;
import com.viewscenes.device.radio.MsgStreamRealtimeClientQueryCmd;
import com.viewscenes.device.radio.MsgStreamRealtimeClientQueryRes;
import com.viewscenes.device.radio.MsgStreamRealtimeClientStopCmd;
import com.viewscenes.device.radio.MsgStreamRealtimeQueryCmd;
import com.viewscenes.device.radio.MsgStreamRealtimeQueryRes;
import com.viewscenes.device.radio.MsgStreamTaskSetCmd;

public class StreamAPI {
	/**
	 * 录音历史收测数据查询
	 * <p>class/function:com.viewscenes.device.api
	 * <p>explain: 2012-09-15 by tcw
	 * 该接口在海外监测中已删除,没有相关需求
	 * <p>author-date:谭长伟 2012-8-6
	 * @param:
	 * @return:
	 */
//	public static MsgStreamHistoryQueryRes.Result msgStreamHistoryQueryCmd(
//			String code, String equcode, String freq, String band,
//			String taskid, String startDatetime, String endDatetime,
//			String priority) throws DeviceNotExistException,
//			DeviceFilterException, DeviceProcessException,
//			DeviceTimedOutException, DeviceReportException {
//		MsgStreamHistoryQueryCmd cmd = new MsgStreamHistoryQueryCmd();
//		// 生成返回消息对象
//		MsgStreamHistoryQueryRes res = new MsgStreamHistoryQueryRes();
//
//		// 设置发送消息数据
//		cmd.setPriority(priority);
//		cmd.setDestCode(code);
//
//		cmd.setParams(equcode, taskid, freq, band, startDatetime, endDatetime);
//		MessageClient client = new MessageClient();
//
//		// 发送消息，并获得返回结果
//		client.execute(cmd, res);
//
//		return res.getResult();
//	}

	public static Collection msgStreamRealtimeClientQueryCmd(String code,
			String equcode, String queryType, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgStreamRealtimeClientQueryCmd cmd = new MsgStreamRealtimeClientQueryCmd();
		MsgStreamRealtimeClientQueryRes res = new MsgStreamRealtimeClientQueryRes();
		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setEequCode(equcode);
		cmd.setType(queryType);

		MessageClient client = new MessageClient();

		client.execute(cmd, res);
		return res.getStreamRealtime();
	}

	public static void msgStreamRealtimeClientStopCmd(String code,
			Collection clients, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {

		MsgStreamRealtimeClientStopCmd cmd = new MsgStreamRealtimeClientStopCmd();
		MsgDefaultRes res = new MsgDefaultRes();

		MessageClient client = new MessageClient();

		cmd.setDestCode(code);
		cmd.setPriority(priority);
		cmd.setClientInfos(clients);
		client.execute(cmd, res);
	}

	public static Map msgStreamRealtimeQueryCmd(String code,
			MsgStreamRealtimeQueryCmd.RealtimeStream[] reals, String priority)
			throws DeviceNotExistException, DeviceFilterException,
			DeviceProcessException, DeviceTimedOutException,
			DeviceReportException {
		MsgStreamRealtimeQueryCmd cmd = new MsgStreamRealtimeQueryCmd();
		// 生成返回消息对象
		MsgStreamRealtimeQueryRes res = new MsgStreamRealtimeQueryRes();
		// 设置发送消息数据
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		cmd.setEqus(Arrays.asList(reals));

		// 生成发送处理器
		MessageClient client = new MessageClient();
		
		// 发送消息，并获得返回结果
		client.execute(cmd, res);
		
		MsgStreamRealtimeQueryCmd.RealtimeStream s = reals[0];
		if (s.getAction().equalsIgnoreCase("stop"))
			return null;
		
		
		
		return res.getUrls();
	}

	public static void msgStreamTaskSetCmd(String code, ArrayList list,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgStreamTaskSetCmd cmd = new MsgStreamTaskSetCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();
		cmd.setPriority(priority);
		cmd.setDestCode(code);

		// 设置发送消息数据
		cmd.setList(list);
		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);

	}
	
	/**
	 * 指标录音任务
	 * <p>class/function:com.viewscenes.device.api
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-9-15
	 * @param:
	 * @return:
	 */
	public static void msgQualityStreamTaskSetCmd(String code, ArrayList qualityList,ArrayList streamList,
			String priority) throws DeviceNotExistException,
			DeviceFilterException, DeviceProcessException,
			DeviceTimedOutException, DeviceReportException {
		// 生成发送消息对象
		MsgQualityStreamTaskSetCmd cmd = new MsgQualityStreamTaskSetCmd();
		// 生成返回消息对象
		MsgDefaultRes res = MsgDefaultRes.instance();
		cmd.setPriority(priority);
		cmd.setDestCode(code);
		
		// 设置指标发送消息数据
		cmd.setQualityList(qualityList);
		
		// 设置录音发送消息数据
		cmd.setStreamList(streamList);
		
		// 生成发送处理器
		MessageClient client = new MessageClient();
		// 发送消息，并获得返回结果
		client.execute(cmd, res);
		
	}

}
