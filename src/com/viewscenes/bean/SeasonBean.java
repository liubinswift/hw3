package com.viewscenes.bean;

public class SeasonBean extends BaseBean {

	private String code;
	private String start_time;
	private String end_time;
	private String is_now;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getIs_now() {
		return is_now;
	}
	public void setIs_now(String is_now) {
		this.is_now = is_now;
	}
}
