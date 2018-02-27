package com.viewscenes.task;

import java.io.File;
import java.io.IOException;

import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.UtilException;

/**
 * 
 * 用户手动录音线程
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
			//录音文件名命名规则：站点代号_任务号_录音开始日期时间_录音结束日期时间_频率_接收机代号.mp3
			client.startNormalRecord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
//			//录音出现异常，结束录音并入库
//			RealRecordManager.stopNormalRecord(urlCmdBean);
			/**
			 * 2012.08.31开会决定
			 * 当用户手动录音是被中断,该录音文件抛弃,不保存
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
	 *  为外部提供的当前线程是否在录音
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public boolean isStop(){
		return client.isStop();
	}
	
	/**
	 * 主动停止录音
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public String stopRecord(){
		
		return client.stopNormalRecord();
		
	}
}
