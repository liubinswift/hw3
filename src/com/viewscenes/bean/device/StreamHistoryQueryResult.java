package com.viewscenes.bean.device;

import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.bean.task.Task;

/**
 * 录音历史收测数据查询结果类
 * 
 * @author thinkpad
 * 
 */
public class StreamHistoryQueryResult {

	private String recordId;
	private String taskId;
	private String freq;
	private String band;
	private String equCode;
	private String startDateTime;
	private String endDateTime;
	private String url;
	private String fileName;
	private String size;
	
	
	/**
	 *接口新增加的返回结果值
	 *expireDays 在接口文档中没有该项，现注释掉
	 *2012-08-10 
	 */
	private String level;	//电平
	private String fmModulation;//调制度
	private String amModulation;//调幅度
	private String offset;		//频偏

	
	private Task task;
	private RunplanBean runplanBean;
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getEquCode() {
		return equCode;
	}

	public void setEquCode(String equCode) {
		this.equCode = equCode;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFmModulatio() {
		return fmModulation;
	}

	public void setFmModulatio(String fmModulation) {
		this.fmModulation = fmModulation;
	}

	public String getAmModulation() {
		return amModulation;
	}

	public void setAmModulation(String amModulation) {
		this.amModulation = amModulation;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getFmModulation() {
		return fmModulation;
	}

	public void setFmModulation(String fmModulation) {
		this.fmModulation = fmModulation;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public RunplanBean getRunplanBean() {
		return runplanBean;
	}

	public void setRunplanBean(RunplanBean runplanBean) {
		this.runplanBean = runplanBean;
	}

	
}
