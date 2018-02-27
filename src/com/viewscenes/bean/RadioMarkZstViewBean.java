package com.viewscenes.bean;

/**
 * 打分表对象类
 * 
 * @author thinkpad
 * 
 */
public class RadioMarkZstViewBean {

	private String mark_id = "";
	private String mark_user = "";
	private String mark_datetime = "";
	private String head_code = "";
	private String equ_code = "";
	private String frequency = "";
	private String runplan_id = "";
	private String counti = "";
	private String counto = "";
	private String counts = "";
	private String description = "";
	private String mark_type = "";
	private String edit_user = "";
	private String unit = "";
	private String mark_file_url = "";
	private String file_name = "";
	private String file_length = "";
	private String record_start_time = "";
	private String record_end_time = "";
	private String station_id = "";
	private String station_name = "";
	private String headname = "";
//	private String language_name = "";
	private String play_time = "";
	private String task_id = "";
	private String task_name = "";
	private String level_value = "";
	private String fm_value = "";
	private String am_value = "";
	private String offset_value = "";
	private String remark = "";

	// 下面是语音识别回来的结果数据
	private String asr_type = "";
	private String result_type = "";
	private String status = "";
	private String wavelen = "";
	private String musicratio = "";
	private String noiseratio = "";
	private String speechlen = "";
	private String totalcm = "";
	private String audibilityscore = "";
	private String audibilityconfidence = "";
	private String channelname = "";
	private String channelnameconfidence = "";
	private String programname = "";
	private String programnameconfidence = "";
	private String languagename1 = "";
	private String languagename2 = "";
	private String languagename3 = "";
	private String languagename4 = "";
	private String languagename5 = "";
	private String languageconfidence1 = "";
	private String languageconfidence2 = "";
	private String languageconfidence3 = "";
	private String languageconfidence4 = "";
	private String languageconfidence5 = "";

	public String getMark_id() {
		return mark_id;
	}

	public void setMark_id(String markId) {
		mark_id = markId;
	}

	public String getMark_user() {
		return mark_user;
	}

	public void setMark_user(String markUser) {
		mark_user = markUser;
	}

	public String getMark_datetime() {
		return mark_datetime;
	}

	public void setMark_datetime(String markDatetime) {
		mark_datetime = markDatetime;
	}

	public String getHead_code() {
		return head_code;
	}

	public void setHead_code(String headCode) {
		head_code = headCode;
	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equCode) {
		equ_code = equCode;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRunplan_id() {
		return runplan_id;
	}

	public void setRunplan_id(String runplanId) {
		runplan_id = runplanId;
	}

	public String getCounti() {
		return counti;
	}

	public void setCounti(String counti) {
		this.counti = counti;
	}

	public String getCounto() {
		return counto;
	}

	public void setCounto(String counto) {
		this.counto = counto;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMark_type() {
		return mark_type;
	}

	public void setMark_type(String markType) {
		mark_type = markType;
	}

	public String getEdit_user() {
		return edit_user;
	}

	public void setEdit_user(String editUser) {
		edit_user = editUser;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMark_file_url() {
		return mark_file_url;
	}

	public void setMark_file_url(String markFilePath) {
		mark_file_url = markFilePath;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String fileName) {
		file_name = fileName;
	}

	public String getFile_length() {
		return file_length;
	}

	public void setFile_length(String fileLength) {
		file_length = fileLength;
	}

	public String getRecord_start_time() {
		return record_start_time;
	}

	public void setRecord_start_time(String recordStartTime) {
		if (recordStartTime.length() == 19)
			recordStartTime = recordStartTime.substring(11,recordStartTime.length()); 
		record_start_time = recordStartTime;
	}

	public String getRecord_end_time() {
		return record_end_time;
	}

	public void setRecord_end_time(String recordEndTime) {
		if (recordEndTime.length() == 19)
			recordEndTime = recordEndTime.substring(11,recordEndTime.length()); 
		record_end_time = recordEndTime;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String stationId) {
		station_id = stationId;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String stationName) {
		station_name = stationName;
	}

	public String getHeadname() {
		return headname;
	}

	public void setHeadname(String headname) {
		this.headname = headname;
	}

//	public String getLanguage_name() {
//		return language_name;
//	}
//
//	public void setLanguage_name(String languageName) {
//		language_name = languageName;
//	}

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String playTime) {
		play_time = playTime;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String taskId) {
		task_id = taskId;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String taskName) {
		task_name = taskName;
	}

	public String getLevel_value() {
		return level_value;
	}

	public void setLevel_value(String levelValue) {
		level_value = levelValue;
	}

	public String getFm_value() {
		return fm_value;
	}

	public void setFm_value(String fmValue) {
		fm_value = fmValue;
	}

	public String getAm_value() {
		return am_value;
	}

	public void setAm_value(String amValue) {
		am_value = amValue;
	}

	public String getOffset_value() {
		return offset_value;
	}

	public void setOffset_value(String offsetValue) {
		offset_value = offsetValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAsr_type() {
		return asr_type;
	}

	public void setAsr_type(String asrType) {
		asr_type = asrType;
	}

	public String getResult_type() {
		return result_type;
	}

	public void setResult_type(String resultType) {
		result_type = resultType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAudibilityscore() {
		return audibilityscore;
	}

	public void setAudibilityscore(String audibilityscore) {
		this.audibilityscore = audibilityscore;
	}

	public String getAudibilityconfidence() {
		return audibilityconfidence;
	}

	public void setAudibilityconfidence(String audibilityconfidence) {
		this.audibilityconfidence = audibilityconfidence;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getChannelnameconfidence() {
		return channelnameconfidence;
	}

	public void setChannelnameconfidence(String channelnameconfidence) {
		this.channelnameconfidence = channelnameconfidence;
	}

	public String getProgramname() {
		return programname;
	}

	public void setProgramname(String programname) {
		this.programname = programname;
	}

	public String getProgramnameconfidence() {
		return programnameconfidence;
	}

	public void setProgramnameconfidence(String programnameconfidence) {
		this.programnameconfidence = programnameconfidence;
	}

	public String getLanguagename1() {
		return languagename1;
	}

	public void setLanguagename1(String languagename1) {
		this.languagename1 = languagename1;
	}

	public String getLanguagename2() {
		return languagename2;
	}

	public void setLanguagename2(String languagename2) {
		this.languagename2 = languagename2;
	}

	public String getLanguagename3() {
		return languagename3;
	}

	public void setLanguagename3(String languagename3) {
		this.languagename3 = languagename3;
	}

	public String getLanguagename4() {
		return languagename4;
	}

	public void setLanguagename4(String languagename4) {
		this.languagename4 = languagename4;
	}

	public String getLanguagename5() {
		return languagename5;
	}

	public void setLanguagename5(String languagename5) {
		this.languagename5 = languagename5;
	}

	public String getLanguageconfidence1() {
		return languageconfidence1;
	}

	public void setLanguageconfidence1(String languageconfidence1) {
		this.languageconfidence1 = languageconfidence1;
	}

	public String getLanguageconfidence2() {
		return languageconfidence2;
	}

	public void setLanguageconfidence2(String languageconfidence2) {
		this.languageconfidence2 = languageconfidence2;
	}

	public String getLanguageconfidence3() {
		return languageconfidence3;
	}

	public void setLanguageconfidence3(String languageconfidence3) {
		this.languageconfidence3 = languageconfidence3;
	}

	public String getLanguageconfidence4() {
		return languageconfidence4;
	}

	public void setLanguageconfidence4(String languageconfidence4) {
		this.languageconfidence4 = languageconfidence4;
	}

	public String getLanguageconfidence5() {
		return languageconfidence5;
	}

	public void setLanguageconfidence5(String languageconfidence5) {
		this.languageconfidence5 = languageconfidence5;
	}

}
