package com.viewscenes.task;

import java.util.HashMap;

import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.task.RecordRadio45SecThread;
import com.viewscenes.task.RecordRadioNormalThread;
import com.viewscenes.util.StringTool;


/**
 * ʵʱ��Ƶ¼��������
 * 
 * @author thinkpad
 *
 */
public class RealRecordManager {

	/**
	 * ʵʱ¼���̱߳������
	 */
	private static HashMap<String,RecordRadioNormalThread> realRecordNormalMap = new HashMap<String,RecordRadioNormalThread>();
	
	/**
	 * 45��ʵʱ¼���̱߳������
	 * <headcode,RecordRadio45Thread>
	 */
	private static HashMap<String,RecordRadio45SecThread> realRecord45SecMap = new HashMap<String,RecordRadio45SecThread>();
	
	
	
	/**
	 * ��ʼ45��¼���߳�
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-30
	 * @param:
	 * @return:
	 */
	public static void start45RecRecord(RealtimeUrlCmdBean realTimeUrlCmdBean,String ip,int port){
		
		//û�й�¼��
		if (realRecord45SecMap.get(realTimeUrlCmdBean.getCode()) == null){
			RecordRadio45SecThread clientThread = new RecordRadio45SecThread(realTimeUrlCmdBean,ip, port);
			new Thread(clientThread).start();
			realRecord45SecMap.put(realTimeUrlCmdBean.getCode(), clientThread);
		}
		//һ�����ջ�ͬһʱ���������Ƶ����һ���ģ������ظ�¼��
		else{
			RecordRadio45SecThread clientThread = realRecord45SecMap.get(realTimeUrlCmdBean.getCode());
			if (clientThread.isStop() == true){
				
				//ͣ�����ڵ��߳�
				remove45SecThread(realTimeUrlCmdBean.getCode());
				clientThread = null;
				
				//����һ���µ��߳�
				RecordRadio45SecThread new_clientThread = new RecordRadio45SecThread(realTimeUrlCmdBean,ip, port);
				new Thread(new_clientThread).start();
				realRecord45SecMap.put(realTimeUrlCmdBean.getCode(), new_clientThread);
			}
		}
		
	}
	
	
	/**
	 * ��ʼ�û��ֶ�¼���߳�
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-30
	 * @param:
	 * @return:
	 */
	public static void startNormalRecord(RealtimeUrlCmdBean realTimeUrlCmdBean,String ip,int port){
		
		String sDate = StringTool.generateRecordDateTimeByDate(null);
		
		//@�������ʱ��,��¼������滻�������Ľ���ʱ��
		String fileName = realTimeUrlCmdBean.getCode()+"_0_"+sDate+"_@_"+realTimeUrlCmdBean.getFreq()+"_"+realTimeUrlCmdBean.getEquCode()+"_"+ip+"_"+port+".mp3";
		
		//��վ�㲻����¼��
		if (realRecordNormalMap.get(realTimeUrlCmdBean.getCode()) == null){
			RecordRadioNormalThread clientThread = new RecordRadioNormalThread(fileName,realTimeUrlCmdBean,ip, port);
				
			new Thread(clientThread).start();
				
			//�����վ���¼������
			realRecordNormalMap.put(realTimeUrlCmdBean.getCode(), clientThread);
				
		//��վ����¼��,���ڶ����·�¼������	
		}else{
			RecordRadioNormalThread clientThread = realRecordNormalMap.get(realTimeUrlCmdBean.getCode());
			//¼��������ֹͣ,���¿�ʼ¼��
			if (clientThread.isStop() == true){
				removeNormalThread(realTimeUrlCmdBean.getCode());
				clientThread = null;
			
				RecordRadioNormalThread new_clientThread = new RecordRadioNormalThread(fileName,realTimeUrlCmdBean,ip, port);
				new Thread(new_clientThread).start();
				//�����վ���¼������
				realRecordNormalMap.put(realTimeUrlCmdBean.getCode(), new_clientThread);
			}
		}
	}
	
	/**
	 * ֹͣ45��¼���߳�,�û�ֹͣʵʱ����ʱ����
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-30
	 * @param:
	 * @return:
	 */
	public static void stop45SecRecord(RealtimeUrlCmdBean realTimeUrlCmdBean){
		//==========================ֹͣ45��¼���߳�===========================
		if (realRecord45SecMap.get(realTimeUrlCmdBean.getCode()) != null){
			
			RecordRadio45SecThread clientThread = realRecord45SecMap.get(realTimeUrlCmdBean.getCode());
			if (clientThread.isStop() ==  false){
				clientThread.stopRecord();
			}
			//ɾ��¼���߳�
			remove45SecThread(realTimeUrlCmdBean.getCode());
		}
	}
	
	
	/**
	 * ֹͣ�û��ֶ�¼���߳�
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-30
	 * @param:
	 * @return:
	 */
	public static String stopNormalRecord(RealtimeUrlCmdBean realTimeUrlCmdBean){
		//=========================ֹͣ�û��ֶ�¼���߳�=========================
		if (realRecordNormalMap.get(realTimeUrlCmdBean.getCode()) != null){
			
			RecordRadioNormalThread clientThread = realRecordNormalMap.get(realTimeUrlCmdBean.getCode());
			if (clientThread.isStop() ==  false){
				String msg =  clientThread.stopRecord();
				//ɾ��¼���߳�
				removeNormalThread(realTimeUrlCmdBean.getCode());
				return msg;
			}else{
				return "¼����Ŀʧ��,��¼������Ƶ�����ж�";
			}
			
			
		}
		return "";
	}
	
	
	/**
	 * ɾ��������45��¼��MAP�е��߳̾��
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-30
	 * @param:
	 * @return:
	 */
	private static void remove45SecThread(String headCode){
		realRecord45SecMap.remove(headCode);
	}

	/**
	 * ɾ���������û��ֶ�¼��MAP�е��߳̾��
	 * <p>class/function:com.viewscenes.web.online
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-30
	 * @param:
	 * @return:
	 */
	private static void removeNormalThread(String headCode){
		realRecordNormalMap.remove(headCode);
	}
}
