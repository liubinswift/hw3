package com.viewscenes.bean.pub;

import java.util.Date;


/**
 * 存放站点上报的在线状态
 * @author Administrator
 *
 */
public class HeadOnlineStatusBean {
	private String code = "";
	private String is_online = "0";//1在线 0不在线
	private Date lastSaveTime = new Date();//站点最后一次上报状态的时间
	private String intervalReport = "";//站点上报时间间隔
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
