package com.viewscenes.task;

import java.io.IOException;

import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.util.UtilException;

/**
 * 45秒录音线程
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
			//出现异常，一般的原因是有其他用户抢占了该站点的收听，
			//或用户自已改换其它频率播放而没有先停止当前频率的播放
			//去掉对该录音的句柄保存 
			RealRecordManager.stop45SecRecord(urlCmdBean);
		}
	}

	
	/**
	 *  为外部提供的当前线程是否在录音
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public boolean isStop(){
		return client != null ?client.isStop():false;
	}
	
	/**
	 * 主动停止录音
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public void stopRecord(){
		if (client != null)
			client.stop45SecRecord();
	}
}
