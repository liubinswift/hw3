package com.viewscenes.bean.spectrum;

public class SpectrumStatBean {
	private String id = "";
	private String head_code = "";//前端CODE
	private String station_id = "";//发射台ID
	private String station_name = "";//发射台
	private String language_id = "";//语言ID
	private String language_name = "";//语言
	private String country = "";//国家
	private String effect = "";//效果
	private String mark = "";//分数
	private String freq = "";//频率
	private String playtime = "";//播音时段
	private String headtype = "";//101采集点，102遥控站
	
	private String is_play = "";//0无播音，1有播音
	private String input_datetime = "";//录入日期
	private String head_name = "";//前端名称,去掉AB用于频谱统计时使用.
	public SpectrumStatBean() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHead_code() {
		return head_code;
	}
	public void setHead_code(String head_code) {
		this.head_code = head_code;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}
	public String getLanguage_name() {
		return language_name;
	}
	public void setLanguage_name(String language_name) {
		this.language_name = language_name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getPlaytime() {
		return playtime;
	}
	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}
	public String getHeadtype() {
		return headtype;
	}
	public void setHeadtype(String headtype) {
		this.headtype = headtype;
	}
	public String getIs_play() {
		return is_play;
	}
	public void setIs_play(String is_play) {
		this.is_play = is_play;
	}
	public String getInput_datetime() {
		return input_datetime;
	}
	public void setInput_datetime(String input_datetime) {
		this.input_datetime = input_datetime;
	}
	public String getHead_name() {
		return head_name;
	}
	public void setHead_name(String head_name) {
		this.head_name = head_name;
	}
}
