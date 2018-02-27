package com.viewscenes.task;

import java.util.HashMap;

import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.task.RecordRadio45SecThread;
import com.viewscenes.task.RecordRadioNormalThread;
import com.viewscenes.util.StringTool;


/**
 * 实时音频录音管理类
 * 
 * @author thinkpad
 *
 */
public class RealRecordManager {

	/**
	 * 实时录音线程保存对象
	 */
	private static HashMap<String,RecordRadioNormalThread> realRecordNormalMap = new HashMap<String,RecordRadioNormalThread>();
	
	/**
	 * 45秒实时录音线程保存对象
	 * <headcode,RecordRadio45Thread>
	 */
	private static HashMap<String,RecordRadio45SecThread> realRecord45SecMap = new HashMap<String,RecordRadio45SecThread>();
	
	
	
	/**
	 * 开始45秒录音线程
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-30
	 * @param:
	 * @return:
	 */
	public static void start45RecRecord(RealtimeUrlCmdBean realTimeUrlCmdBean,String ip,int port){
		
		//没有过录音
		if (realRecord45SecMap.get(realTimeUrlCmdBean.getCode()) == null){
			RecordRadio45SecThread clientThread = new RecordRadio45SecThread(realTimeUrlCmdBean,ip, port);
			new Thread(clientThread).start();
			realRecord45SecMap.put(realTimeUrlCmdBean.getCode(), clientThread);
		}
		//一个接收机同一时间输出的音频流是一样的，不进重复录音
		else{
			RecordRadio45SecThread clientThread = realRecord45SecMap.get(realTimeUrlCmdBean.getCode());
			if (clientThread.isStop() == true){
				
				//停掉现在的线程
				remove45SecThread(realTimeUrlCmdBean.getCode());
				clientThread = null;
				
				//开启一个新的线程
				RecordRadio45SecThread new_clientThread = new RecordRadio45SecThread(realTimeUrlCmdBean,ip, port);
				new Thread(new_clientThread).start();
				realRecord45SecMap.put(realTimeUrlCmdBean.getCode(), new_clientThread);
			}
		}
		
	}
	
	
	/**
	 * 开始用户手动录音线程
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-30
	 * @param:
	 * @return:
	 */
	public static void startNormalRecord(RealtimeUrlCmdBean realTimeUrlCmdBean,String ip,int port){
		
		String sDate = StringTool.generateRecordDateTimeByDate(null);
		
		//@代表结束时间,待录音完成替换成真正的结束时间
		String fileName = realTimeUrlCmdBean.getCode()+"_0_"+sDate+"_@_"+realTimeUrlCmdBean.getFreq()+"_"+realTimeUrlCmdBean.getEquCode()+"_"+ip+"_"+port+".mp3";
		
		//该站点不存在录音
		if (realRecordNormalMap.get(realTimeUrlCmdBean.getCode()) == null){
			RecordRadioNormalThread clientThread = new RecordRadioNormalThread(fileName,realTimeUrlCmdBean,ip, port);
				
			new Thread(clientThread).start();
				
			//保存该站点的录音操作
			realRecordNormalMap.put(realTimeUrlCmdBean.getCode(), clientThread);
				
		//该站点已录音,属于二次下发录音操作	
		}else{
			RecordRadioNormalThread clientThread = realRecordNormalMap.get(realTimeUrlCmdBean.getCode());
			//录音工作已停止,重新开始录音
			if (clientThread.isStop() == true){
				removeNormalThread(realTimeUrlCmdBean.getCode());
				clientThread = null;
			
				RecordRadioNormalThread new_clientThread = new RecordRadioNormalThread(fileName,realTimeUrlCmdBean,ip, port);
				new Thread(new_clientThread).start();
				//保存该站点的录音操作
				realRecordNormalMap.put(realTimeUrlCmdBean.getCode(), new_clientThread);
			}
		}
	}
	
	/**
	 * 停止45秒录音线程,用户停止实时监听时操作
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-30
	 * @param:
	 * @return:
	 */
	public static void stop45SecRecord(RealtimeUrlCmdBean realTimeUrlCmdBean){
		//==========================停止45秒录音线程===========================
		if (realRecord45SecMap.get(realTimeUrlCmdBean.getCode()) != null){
			
			RecordRadio45SecThread clientThread = realRecord45SecMap.get(realTimeUrlCmdBean.getCode());
			if (clientThread.isStop() ==  false){
				clientThread.stopRecord();
			}
			//删除录音线程
			remove45SecThread(realTimeUrlCmdBean.getCode());
		}
	}
	
	
	/**
	 * 停止用户手动录音线程
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-30
	 * @param:
	 * @return:
	 */
	public static String stopNormalRecord(RealtimeUrlCmdBean realTimeUrlCmdBean){
		//=========================停止用户手动录音线程=========================
		if (realRecordNormalMap.get(realTimeUrlCmdBean.getCode()) != null){
			
			RecordRadioNormalThread clientThread = realRecordNormalMap.get(realTimeUrlCmdBean.getCode());
			if (clientThread.isStop() ==  false){
				String msg =  clientThread.stopRecord();
				//删除录音线程
				removeNormalThread(realTimeUrlCmdBean.getCode());
				return msg;
			}else{
				return "录音节目失败,该录音的音频流被中断";
			}
			
			
		}
		return "";
	}
	
	
	/**
	 * 删除保存在45秒录音MAP中的线程句柄
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-30
	 * @param:
	 * @return:
	 */
	private static void remove45SecThread(String headCode){
		realRecord45SecMap.remove(headCode);
	}

	/**
	 * 删除保存在用户手动录音MAP中的线程句柄
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-30
	 * @param:
	 * @return:
	 */
	private static void removeNormalThread(String headCode){
		realRecordNormalMap.remove(headCode);
	}
}
