package com.viewscenes.bean.pub;

import java.util.Date;


/**
 * ���վ���ϱ�������״̬
 * @author Administrator
 *
 */
public class HeadOnlineStatusBean {
	private String code = "";
	private String is_online = "0";//1���� 0������
	private Date lastSaveTime = new Date();//վ�����һ���ϱ�״̬��ʱ��
	private String intervalReport = "";//վ���ϱ�ʱ����
	private String ip = "";
	private String port = "";
	private boolean fristCreate = true;
	public boolean isFristCreate() {
		return fristCreate;
	}
	public void setFristCreate(boolean fristCreate) {
		this.fristCreate = fristCreate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIs_online() {
		return is_online;
	}
	public void setIs_online(String is_online) {
		this.is_online = is_online;
	}
	public Date getLastSaveTime() {
		return lastSaveTime;
	}
	public void setLastSaveTime(Date lastSaveTime) {
		this.lastSaveTime = lastSaveTime;
	}
	public String getIntervalReport() {
		return intervalReport;
	}
	public void setIntervalReport(String intervalReport) {
		this.intervalReport = intervalReport;
	}
}
