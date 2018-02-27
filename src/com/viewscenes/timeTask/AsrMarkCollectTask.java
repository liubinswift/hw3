package com.viewscenes.timeTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.ObjectQueue;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.daily.queryData.RecFileQuery;

/**
 * 自动收集需要向语音识别系统发送录音文件的任务类
 * 与AsrMarkSenderThread语音识别发送器配合使用
 * 
 * 本类负责收集AsrMarkSenderThread负责发送
 * @author thinkpad
 * 
 */
public class AsrMarkCollectTask {
	private final static Scheduler scheduler = new Scheduler();// 调度器
	private final int hourOfDay, minute, second;// 每天触发的时间点
	private final int maxProecessFileCount = 800;		//每次录音文件最大处理量
	private final int interval = 10;				//执行收集录音文件的时间间隔

	
	// 语音识别发送队列
	private static ObjectQueue<FileRetrieveResult> asrTaskQueue = new ObjectQueue<FileRetrieveResult>("语音识别发送",10000, 2000);

	@SuppressWarnings("deprecation")
	public AsrMarkCollectTask() {
		Date d = new Date();
		this.hourOfDay = d.getHours();
		this.minute = d.getMinutes();
		this.second = d.getSeconds();
		LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动收集语音识别录音文件线程启动,收集时间间隔("+interval+"分钟)...");
	}

	public void start() {
		scheduler.schedule(new SchedulerTask() {
			public void run() {
				
				//对列不为空，不再进行收集
				if (asrTaskQueue.getQuerySize()==0){
				
					//获取未打分的录音文件列表
					ArrayList<FileRetrieveResult> list = getRecordList(maxProecessFileCount);
					
					LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动收集语音识别录音文件线程 录音文件数量:"+list.size());
					//将未打分的录音文件放到队列里
					for(int i=0;i<list.size();i++){
						FileRetrieveResult result = list.get(i);
						
						String fileName = result.getFileName();
						String[] names = fileName.split("_");
						String languageName = names.length>6?names[6]:"";
						result.setLanguage(languageName);
						
						asrTaskQueue.add(result);
					}
				}else{
					LogTool.info("asr",StringTool.Date2String(new Date())+"警告：语音识别发送 队列中还有" + asrTaskQueue.getQuerySize() + "条未处理消息。");
				}
			}

			
			/**
			 * 调用获取未打分的录音文件列表方法
			 * <p>class/function:com.viewscenes.timeTask
			 * <p>explain:
			 * <p>author-date:谭长伟 2013-4-9
			 * @param:
			 * @return:
			 */
			private ArrayList<FileRetrieveResult> getRecordList(int maxCount){
				return RecFileQuery.getNotMarkRecordFileList(maxCount);
			}
		}, new DailyIterator(Calendar.MINUTE, interval, hourOfDay, minute, second));// 通过迭代器模式迭代遍历得到后面一系列的时间点
	}

	
	
	
	public static ObjectQueue<FileRetrieveResult> getAsrTaskQueue() {
		return asrTaskQueue;
	}
	
	//运行状态0:未运行，1：运行，2：已停止
	public static int getState(){
		return scheduler.getState();
	}

	public static void main(String[] args) {
//		Date d = new Date();
//		System.out.println(StringTool.Date2String(d));
//		System.out.println(d.getHours());
//		System.out.println();
//		System.out.println(d.getSeconds());
		
		String s = "OAF07A/OAF07A_12143_20121226162759_20121226163000_95800_R1_保_501_效果_NI-1000.mp3";
		System.out.println(s.split("_")[6]);
	}
}
