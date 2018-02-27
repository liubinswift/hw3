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
 * �Զ��ռ���Ҫ������ʶ��ϵͳ����¼���ļ���������
 * ��AsrMarkSenderThread����ʶ���������ʹ��
 * 
 * ���ฺ���ռ�AsrMarkSenderThread������
 * @author thinkpad
 * 
 */
public class AsrMarkCollectTask {
	private final static Scheduler scheduler = new Scheduler();// ������
	private final int hourOfDay, minute, second;// ÿ�촥����ʱ���
	private final int maxProecessFileCount = 800;		//ÿ��¼���ļ��������
	private final int interval = 10;				//ִ���ռ�¼���ļ���ʱ����

	
	// ����ʶ���Ͷ���
	private static ObjectQueue<FileRetrieveResult> asrTaskQueue = new ObjectQueue<FileRetrieveResult>("����ʶ����",10000, 2000);

	@SuppressWarnings("deprecation")
	public AsrMarkCollectTask() {
		Date d = new Date();
		this.hourOfDay = d.getHours();
		this.minute = d.getMinutes();
		this.second = d.getSeconds();
		LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ��ռ�����ʶ��¼���ļ��߳�����,�ռ�ʱ����("+interval+"����)...");
	}

	public void start() {
		scheduler.schedule(new SchedulerTask() {
			public void run() {
				
				//���в�Ϊ�գ����ٽ����ռ�
				if (asrTaskQueue.getQuerySize()==0){
				
					//��ȡδ��ֵ�¼���ļ��б�
					ArrayList<FileRetrieveResult> list = getRecordList(maxProecessFileCount);
					
					LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ��ռ�����ʶ��¼���ļ��߳� ¼���ļ�����:"+list.size());
					//��δ��ֵ�¼���ļ��ŵ�������
					for(int i=0;i<list.size();i++){
						FileRetrieveResult result = list.get(i);
						
						String fileName = result.getFileName();
						String[] names = fileName.split("_");
						String languageName = names.length>6?names[6]:"";
						result.setLanguage(languageName);
						
						asrTaskQueue.add(result);
					}
				}else{
					LogTool.info("asr",StringTool.Date2String(new Date())+"���棺����ʶ���� �����л���" + asrTaskQueue.getQuerySize() + "��δ������Ϣ��");
				}
			}

			
			/**
			 * ���û�ȡδ��ֵ�¼���ļ��б���
			 * <p>class/function:com.viewscenes.timeTask
			 * <p>explain:
			 * <p>author-date:̷��ΰ 2013-4-9
			 * @param:
			 * @return:
			 */
			private ArrayList<FileRetrieveResult> getRecordList(int maxCount){
				return RecFileQuery.getNotMarkRecordFileList(maxCount);
			}
		}, new DailyIterator(Calendar.MINUTE, interval, hourOfDay, minute, second));// ͨ��������ģʽ���������õ�����һϵ�е�ʱ���
	}

	
	
	
	public static ObjectQueue<FileRetrieveResult> getAsrTaskQueue() {
		return asrTaskQueue;
	}
	
	//����״̬0:δ���У�1�����У�2����ֹͣ
	public static int getState(){
		return scheduler.getState();
	}

	public static void main(String[] args) {
//		Date d = new Date();
//		System.out.println(StringTool.Date2String(d));
//		System.out.println(d.getHours());
//		System.out.println();
//		System.out.println(d.getSeconds());
		
		String s = "OAF07A/OAF07A_12143_20121226162759_20121226163000_95800_R1_��_501_Ч��_NI-1000.mp3";
		System.out.println(s.split("_")[6]);
	}
}
