package com.viewscenes.bean;

/**
 * 语音识别发送命令对象类
 * 
 * @author thinkpad
 * 
 */
public class ASRCmdBean {
	// 服务类型：WorkFlow;TaskStatus
	private String type = "TaskStatus";
	// 任务流类型:BC573
	private String wfType = "BC573";
	// 录音文件名称
	private String file;
	// 文件的录音开始时间
	private String fileStartTime;
	// 文件的录音结束时间
	private String fileEndTime;
	// 待分析的有效时间段的开始时间
	private String taskStartTime;
	// 待分析的有效时间段的结束时间
	private String taskEndTime;
	//频率
	private String freq;
	//语言
	private String language;
	// 采集方式（录音方式：遥控站实时1、遥控站回传2、采集点实时3、采集点回传4）
	private String collectMethod;
	// 参考源通道（阿拉伯数字，从1，2……21）
	private String collectChannel;

	
	/**************************************************
	 *      2012-11-5 新增加接口内容
	 * 		谭长伟
	 ************************************************/
	//接收机类型(545/713/NI1000)
	private String receiverType;
	
	//业务系统ID号(字符串，除“_”外)
	private String taskId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWfType() {
		return wfType;
	}

	public void setWfType(String wfType) {
		this.wfType = wfType;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileStartTime() {
		return fileStartTime;
	}

	public void setFileStartTime(String fileStartTime) {
		this.fileStartTime = fileStartTime;
	}

	public String getFileEndTime() {
		return fileEndTime;
	}

	public void setFileEndTime(String fileEndTime) {
		this.fileEndTime = fileEndTime;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public String getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	
	
	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getCollectChannel() {
		return collectChannel;
	}

	public void setCollectChannel(String collectChannel) {
		this.collectChannel = collectChannel;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
}
