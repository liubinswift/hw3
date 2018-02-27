package com.viewscenes.timeTask.rmi;

import java.io.Serializable;
import java.util.Date;

public class RmiMsgObj implements Serializable {
	
	//当前工作的服务器ip
	private String ip;
	
	//当前工作的服务器端口
	private int port ;
	
	//当前工作的服务器的优先级别
	private int priority;
	
	//当前状态，是否运行
	private String status;
	
	//最后运行时间
	private String lastRuntime;
	
	//当前工作的服务器对象
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
