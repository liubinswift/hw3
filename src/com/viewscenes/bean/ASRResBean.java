package com.viewscenes.bean;

/**
 * 语音识别接收返回结果对象类
 * 
 * @author thinkpad
 * 
 */
public class ASRResBean {
	// 固定1
	private String type;
	// 订单ID，即任务ID
	private String orderID;
	// 结果类型: BC573
	private String timePeriodType;
	// 停播、空播、话少
	private String status;
	// 执行状态
	private String taskStatus;
	// 错误描述
	private String errorMessage;
	// 录音文件名称
	private String file;
	// 文件开始时间
	private String startTime;
	// 文件结束时间
	private String endTime;

	//语音长度（秒）
	private String wavelen;
	
	//音乐比例
	private String musicratio;
	
	//噪声比例
	private String noiseratio;
	
	//话音长度（秒）
	private String speechlen;
	
	//总体置信度
	private String totalcm;

	// 可听度得分
	private String audibilityScore;
	// 可听度置信度
	private String audibilityConfidence;
	// 台名识别结果
	private String channelName;
	// 台名识别置信度
	private String channelNameConfidence;
	// 节目比对结果
	private String programName;
	// 节目比对置信度
	private String programNameConfidence;

	// 前5名语种识别结果及其置信度
	private String languageName1;
	private String languageName2;
	private String languageName3;
	private String languageName4;
	private String languageName5;
	private String languageConfidence1;
	private String languageConfidence2;
	private String languageConfidence3;
	private String languageConfidence4;
	private String languageConfidence5;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getTimePeriodType() {
		return timePeriodType;
	}

	public void setTimePeriodType(String timePeriodType) {
		this.timePeriodType = timePeriodType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAudibilityScore() {
		return audibilityScore;
	}

	public void setAudibilityScore(String audibilityScore) {
		this.audibilityScore = audibilityScore;
	}

	public String getAudibilityConfidence() {
		return audibilityConfidence;
	}

	public void setAudibilityConfidence(String audibilityConfidence) {
		this.audibilityConfidence = audibilityConfidence;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelNameConfidence() {
		return channelNameConfidence;
	}

	public void setChannelNameConfidence(String channelNameConfidence) {
		this.channelNameConfidence = channelNameConfidence;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramNameConfidence() {
		return programNameConfidence;
	}

	public void setProgramNameConfidence(String programNameConfidence) {
		this.programNameConfidence = programNameConfidence;
	}

	public String getLanguageName1() {
		return languageName1;
	}

	public void setLanguageName1(String languageName1) {
		this.languageName1 = languageName1;
	}

	public String getLanguageName2() {
		return languageName2;
	}

	public void setLanguageName2(String languageName2) {
		this.languageName2 = languageName2;
	}

	public String getLanguageName3() {
		return languageName3;
	}

	public void setLanguageName3(String languageName3) {
		this.languageName3 = languageName3;
	}

	public String getLanguageName4() {
		return languageName4;
	}

	public void setLanguageName4(String languageName4) {
		this.languageName4 = languageName4;
	}

	public String getLanguageName5() {
		return languageName5;
	}

	public void setLanguageName5(String languageName5) {
		this.languageName5 = languageName5;
	}

	public String getLanguageConfidence1() {
		return languageConfidence1;
	}

	public void setLanguageConfidence1(String languageConfidence1) {
		this.languageConfidence1 = languageConfidence1;
	}

	public String getLanguageConfidence2() {
		return languageConfidence2;
	}

	public void setLanguageConfidence2(String languageConfidence2) {
		this.languageConfidence2 = languageConfidence2;
	}

	public String getLanguageConfidence3() {
		return languageConfidence3;
	}

	public void setLanguageConfidence3(String languageConfidence3) {
		this.languageConfidence3 = languageConfidence3;
	}

	public String getLanguageConfidence4() {
		return languageConfidence4;
	}

	public void setLanguageConfidence4(String languageConfidence4) {
		this.languageConfidence4 = languageConfidence4;
	}

	public String getLanguageConfidence5() {
		return languageConfidence5;
	}

	public void setLanguageConfidence5(String languageConfidence5) {
		this.languageConfidence5 = languageConfidence5;
	}

	public String getWavelen() {
		return wavelen;
	}

	public void setWavelen(String wavelen) {
		this.wavelen = wavelen;
	}

	public String getMusicratio() {
		return musicratio;
	}

	public void setMusicratio(String musicratio) {
		this.musicratio = musicratio;
	}

	public String getNoiseratio() {
		return noiseratio;
	}

	public void setNoiseratio(String noiseratio) {
		this.noiseratio = noiseratio;
	}

	public String getSpeechlen() {
		return speechlen;
	}

	public void setSpeechlen(String speechlen) {
		this.speechlen = speechlen;
	}

	public String getTotalcm() {
		return totalcm;
	}

	public void setTotalcm(String totalcm) {
		this.totalcm = totalcm;
	}

	
}
