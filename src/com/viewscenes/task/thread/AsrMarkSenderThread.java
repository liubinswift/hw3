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
 * ����ʶ������
 * ��AsrMarkCollectTask�࣬���ʹ��
 * @see AsrMarkCollectTask
 * @author thinkpad
 *
 */
public class AsrMarkSenderThread extends Thread{

	private ASRClient client = new ASRClient();
	private  final static int  sleeptime = 10*1000;
	private final static int SENDCOUNT = 20;
	public AsrMarkSenderThread(){
		
		LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ�����¼���ļ�������ʶ��ϵͳ�߳�����...");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true){
			ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();
			while(AsrMarkCollectTask.getAsrTaskQueue().getQuerySize()>0){
				FileRetrieveResult result = AsrMarkCollectTask.getAsrTaskQueue().get();
				list.add(result);
				//ÿ��Ĭ��10������һ��
				if (list.size()>SENDCOUNT){
					client.exucuteTask(list);
					//�����������list
					list = new ArrayList<FileRetrieveResult>();
				}
				LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ�����¼���ļ�������ʶ��ϵͳ ׼������:"+list.size()+"��¼���ļ�.");
	        }
			try {
				sleep(sleeptime);
				//����ʣ�಻��ʮ���ķ��ͳ�ȥ
				if (list.size() >0){
					client.exucuteTask(list);
					LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ�����¼���ļ�������ʶ��ϵͳ ׼������:"+list.size()+"��¼���ļ�.");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch blockk
				e.printStackTrace();
			}
			
		}
	
		
	}

	
}
