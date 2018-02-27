package com.viewscenes.bean;

/**
 * 下发实时音频指令类
 * 
 * @author thinkpad
 * 
 */

public class RealtimeUrlCmdBean {

	private String userid;
	private String code;
	private String equCode;
	private String bps;
	private String encode;
//	private String flag;
	private String freq;
	private String band;
	private String action;
	private String priority;
	private String lastUrl;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEquCode() {
		return equCode;
	}

	public void setEquCode(String equCode) {
		this.equCode = equCode;
	}

	public String getBps() {
		return bps;
	}

	public void setBps(String bps) {
		this.bps = bps;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

//	public String getFlag() {
//		return flag;
//	}
//
//	public void setFlag(String flag) {
//		this.flag = flag;
//	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getLastUrl() {
		return lastUrl;
	}

	public void setLastUrl(String lastUrl) {
		this.lastUrl = lastUrl;
	}

}
