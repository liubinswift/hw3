package com.viewscenes.task;

import java.io.File;
import java.io.IOException;

import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.UtilException;

/**
 * 
 * �û��ֶ�¼���߳�
 * @author thinkpad
 *
 */
public class RecordRadioNormalThread implements Runnable {
	
	private RecordRadioClient  client = null;
	private String ip;
	private int port;
	private RealtimeUrlCmdBean urlCmdBean = null;
	private String normalFileName = null;
	
	
	public RecordRadioNormalThread(String fileName,RealtimeUrlCmdBean urlCmdBean,String ip,int port){
		this.normalFileName = fileName;
		this.urlCmdBean = urlCmdBean;
		this.ip = ip;
		this.port = port;
	}
	
	public void run() {
		client = new RecordRadioClient(normalFileName,urlCmdBean,ip,port);
		try {
			//¼���ļ�����������վ�����_�����_¼����ʼ����ʱ��_¼����������ʱ��_Ƶ��_���ջ�����.mp3
			client.startNormalRecord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
//			//¼�������쳣������¼�������
//			RealRecordManager.stopNormalRecord(urlCmdBean);
			/**
			 * 2012.08.31�������
			 * ���û��ֶ�¼���Ǳ��ж�,��¼���ļ�����,������
			 */
			client.setStop(true);
			File file = new File(SystemConfig.getVideoPath() + client.getUserRecDirs()+ File.separator + normalFileName);
			if (file.exists()){
				file.delete();
			}
			
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			RealRecordManager.stopNormalRecord(urlCmdBean);
			client.setStop(true);
			File file = new File(SystemConfig.getVideoPath() + client.getUserRecDirs()+ File.separator + normalFileName);
			if (file.exists()){
				file.delete();
			}
		}
	}

	
	/**
	 *  Ϊ�ⲿ�ṩ�ĵ�ǰ�߳��Ƿ���¼��
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 */
	public boolean isStop(){
		return client.isStop();
	}
	
	/**
	 * ����ֹͣ¼��
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 */
	public String stopRecord(){
		
		return client.stopNormalRecord();
		
	}
}
