package com.viewscenes.timeTask.rmi;

import java.io.Serializable;
import java.util.Date;

public class RmiMsgObj implements Serializable {
	
	//��ǰ�����ķ�����ip
	private String ip;
	
	//��ǰ�����ķ������˿�
	private int port ;
	
	//��ǰ�����ķ����������ȼ���
	private int priority;
	
	//��ǰ״̬���Ƿ�����
	private String status;
	
	//�������ʱ��
	private String lastRuntime;
	
	//��ǰ�����ķ���������
	private RmiMsgObj curWorkObj;
	
	
	public RmiMsgObj getCurWorkObj() {
		return curWorkObj;
	}
	public void setCurWorkObj(RmiMsgObj curWorkObj) {
		this.curWorkObj = curWorkObj;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getLastRuntime() {
		return lastRuntime;
	}
	public void setLastRuntime(String lastRuntime) {
		this.lastRuntime = lastRuntime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
