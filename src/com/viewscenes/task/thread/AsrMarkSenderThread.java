package com.viewscenes.task.thread;

import java.util.ArrayList;
import java.util.Date;

import org.jfree.util.Log;

import com.viewscenes.axis.asr.ASRClient;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.timeTask.AsrMarkCollectTask;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

/**
 * 语音识别发送器
 * 与AsrMarkCollectTask类，配合使用
 * @see AsrMarkCollectTask
 * @author thinkpad
 *
 */
public class AsrMarkSenderThread extends Thread{

	private ASRClient client = new ASRClient();
	private  final static int  sleeptime = 10*1000;
	private final static int SENDCOUNT = 20;
	public AsrMarkSenderThread(){
		
		LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统线程启动...");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true){
			ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();
			while(AsrMarkCollectTask.getAsrTaskQueue().getQuerySize()>0){
				FileRetrieveResult result = AsrMarkCollectTask.getAsrTaskQueue().get();
				list.add(result);
				//每次默认10条发送一次
				if (list.size()>SENDCOUNT){
					client.exucuteTask(list);
					//发送完成重置list
					list = new ArrayList<FileRetrieveResult>();
				}
				LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统 准备发送:"+list.size()+"条录音文件.");
	        }
			try {
				sleep(sleeptime);
				//最后把剩余不足十条的发送出去
				if (list.size() >0){
					client.exucuteTask(list);
					LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统 准备发送:"+list.size()+"条录音文件.");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch blockk
				e.printStackTrace();
			}
			
		}
	
		
	}

	
}
