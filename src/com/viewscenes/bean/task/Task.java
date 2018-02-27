package com.viewscenes.bean.task;

import java.util.ArrayList;

import com.viewscenes.bean.BaseBean;

/**
 * *************************************   
*    
* 项目名称：HW   
* 类名称：Task   
* 类描述：   
* 创建人：刘斌
* 创建时间：2012-7-19 下午02:56:24   
* 修改人：刘斌
* 修改时间：2012-7-19 下午02:56:24   
* 修改备注：   
* @version    
*    
***************************************
 */
public class Task extends BaseBean {
	private String task_id;
	private String equ_code;
	private String head_code;
	private String shortname;
	private String freq;
	private String validDate;//有效期开始时间和结束时间的联合。
	private String start;
	private String end;//有效期开始时间和结束时间的联合。
	private String is_delete;
	private String valid_startdatetime;
	private String valid_enddatetime;
	private String check_level;
	private String  check_fm_modulation;
	private String  check_am_modulation;
	private String check_fm_modulation_max;
	private String check_offset;
	private String  check_bandwidth;
	private String unit;
	private String  samples_number;
	private String is_send;
	private String is_delaysend;
	private String delaysend_datetime;
	private String recordlength;
	private String expiredays;
	private String sleeptime;
	private String send_times;
	private String note;
	private String runplan_id;
	private String batch_no;
	private String is_active;
	private String  priority;
	private String  quality_sleeptime;
	private String  stream_sleeptime;
	private String  offset_sleeptime;
	private String  spectrum_sleeptime;
	private String is_temporary;
	private String send_datetime;
	private String bps;
	private String  task_type;
	private String create_user;
	private String send_user;
	private String authentic_status;
	private String authentic_user;
	private String check_alarm;
	private String head_type_id ="";//站点类型 101采集点 102遥控站
	private String record_type = "";//运行图录音任务类型 质量 录音
	private String play_time = "";//关联运行图的播音时间
	private String station_name = "";//发射台 (其实子任务表中的字段，方便页面查询显示，这里也放了)
	private String language = "";//语言 (其实子任务表中的字段，方便页面查询显示，这里也放了)
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPlay_time() {
		return play_time;
	}
	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	public String getHead_type_id() {
		return head_type_id;
	}
	public void setHead_type_id(String head_type_id) {
		this.head_type_id = head_type_id;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	

	public String getCheck_alarm() {
		return check_alarm;
	}
	public void setCheck_alarm(String check_alarm) {
		this.check_alarm = check_alarm;
	}
	private ArrayList<Subtask> subtask;

	public String getQuality_sleeptime() {
		return quality_sleeptime;
	}
	public void setQuality_sleeptime(String quality_sleeptime) {
		this.quality_sleeptime = quality_sleeptime;
	}
	public String getStream_sleeptime() {
		return stream_sleeptime;
	}
	public void setStream_sleeptime(String stream_sleeptime) {
		this.stream_sleeptime = stream_sleeptime;
	}
	public String getOffset_sleeptime() {
		return offset_sleeptime;
	}
	public void setOffset_sleeptime(String offset_sleeptime) {
		this.offset_sleeptime = offset_sleeptime;
	}
	public String getSpectrum_sleeptime() {
		return spectrum_sleeptime;
	}
	public void setSpectrum_sleeptime(String spectrum_sleeptime) {
		this.spectrum_sleeptime = spectrum_sleeptime;
	}

	  public ArrayList<Subtask> getSubtask() {
		return subtask;
	}
	public void setSubtask(ArrayList<Subtask> subtask) {
		this.subtask = subtask;
	}

	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getEqu_code() {
		return equ_code;
	}
	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}
	public String getHead_code() {
		return head_code;
	}
	public void setHead_code(String head_code) {
		this.head_code = head_code;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	public String getValid_startdatetime() {
		return valid_startdatetime;
	}
	public void setValid_startdatetime(String valid_startdatetime) {
		this.valid_startdatetime = valid_startdatetime;
	}
	public String getValid_enddatetime() {
		return valid_enddatetime;
	}
	public void setValid_enddatetime(String valid_enddatetime) {
		this.valid_enddatetime = valid_enddatetime;
	}
	public String getCheck_level() {
		return check_level;
	}
	public void setCheck_level(String check_level) {
		this.check_level = check_level;
	}
	public String getCheck_fm_modulation() {
		return check_fm_modulation;
	}
	public void setCheck_fm_modulation(String check_fm_modulation) {
		this.check_fm_modulation = check_fm_modulation;
	}
	public String getCheck_am_modulation() {
		return check_am_modulation;
	}
	public void setCheck_am_modulation(String check_am_modulation) {
		this.check_am_modulation = check_am_modulation;
	}
	public String getCheck_fm_modulation_max() {
		return check_fm_modulation_max;
	}
	public void setCheck_fm_modulation_max(String check_fm_modulation_max) {
		this.check_fm_modulation_max = check_fm_modulation_max;
	}
	public String getCheck_offset() {
		return check_offset;
	}
	public void setCheck_offset(String check_offset) {
		this.check_offset = check_offset;
	}
	public String getCheck_bandwidth() {
		return check_bandwidth;
	}
	public void setCheck_bandwidth(String check_bandwidth) {
		this.check_bandwidth = check_bandwidth;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSamples_number() {
		return samples_number;
	}
	public void setSamples_number(String samples_number) {
		this.samples_number = samples_number;
	}
	public String getIs_send() {
		return is_send;
	}
	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}
	public String getIs_delaysend() {
		return is_delaysend;
	}
	public void setIs_delaysend(String is_delaysend) {
		this.is_delaysend = is_delaysend;
	}
	public String getDelaysend_datetime() {
		return delaysend_datetime;
	}
	public void setDelaysend_datetime(String delaysend_datetime) {
		this.delaysend_datetime = delaysend_datetime;
	}
	public String getRecordlength() {
		return recordlength;
	}
	public void setRecordlength(String recordlength) {
		this.recordlength = recordlength;
	}
	public String getExpiredays() {
		return expiredays;
	}
	public void setExpiredays(String expiredays) {
		this.expiredays = expiredays;
	}
	public String getSleeptime() {
		return sleeptime;
	}
	public void setSleeptime(String sleeptime) {
		this.sleeptime = sleeptime;
	}
	public String getSend_times() {
		return send_times;
	}
	public void setSend_times(String send_times) {
		this.send_times = send_times;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRunplan_id() {
		return runplan_id;
	}
	public void setRunplan_id(String runplan_id) {
		this.runplan_id = runplan_id;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getIs_temporary() {
		return is_temporary;
	}
	public void setIs_temporary(String is_temporary) {
		this.is_temporary = is_temporary;
	}
	public String getSend_datetime() {
		return send_datetime;
	}
	public void setSend_datetime(String send_datetime) {
		this.send_datetime = send_datetime;
	}
	public String getBps() {
		return bps;
	}
	public void setBps(String bps) {
		this.bps = bps;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getSend_user() {
		return send_user;
	}
	public void setSend_user(String send_user) {
		this.send_user = send_user;
	}
	public String getAuthentic_status() {
		return authentic_status;
	}
	public void setAuthentic_status(String authentic_status) {
		this.authentic_status = authentic_status;
	}
	public String getAuthentic_user() {
		return authentic_user;
	}
	public void setAuthentic_user(String authentic_user) {
		this.authentic_user = authentic_user;
	}

  
}
