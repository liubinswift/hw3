package com.viewscenes.task;

import java.io.IOException;

import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.util.UtilException;

/**
 * 45��¼���߳�
 * @author thinkpad
 *
 */
public class RecordRadio45SecThread implements Runnable {

	
	private RecordRadioClient  client = null;
	private String ip;
	private int port;
	private RealtimeUrlCmdBean urlCmdBean = null;
	public RecordRadio45SecThread(RealtimeUrlCmdBean urlCmdBean,String ip,int port){
		this.urlCmdBean = urlCmdBean;
		this.ip = ip;
		this.port = port;
	}
	
	public void run() {
		client = new RecordRadioClient(null,urlCmdBean,ip,port);
		try {
			client.start45SecRecord();
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			RealRecordManager.stop45SecRecord(urlCmdBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//�����쳣��һ���ԭ�����������û���ռ�˸�վ���������
			//���û����ѸĻ�����Ƶ�ʲ��Ŷ�û����ֹͣ��ǰƵ�ʵĲ���
			//ȥ���Ը�¼���ľ������ 
			RealRecordManager.stop45SecRecord(urlCmdBean);
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
		return client != null ?client.isStop():false;
	}
	
	/**
	 * ����ֹͣ¼��
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 */
	public void stopRecord(){
		if (client != null)
			client.stop45SecRecord();
	}
}
