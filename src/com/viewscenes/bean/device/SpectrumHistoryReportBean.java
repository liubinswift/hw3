package com.viewscenes.bean.device;

import org.jdom.Element;

/*
 * 频谱历史查询返回数据类
 */
public class SpectrumHistoryReportBean
{
	private String equ_code;
	private String head_id;
	private String task_id;

	private String scandatetime;
	private String band;//边境版电视不存在
	private String freq;
	private String level;
	private String report_type;//上报类型：0手动回收，1主动上报，2实时频谱数据截图

	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	private String spectrumid;//边境版存在

	public SpectrumHistoryReportBean() {
		
	}
	public SpectrumHistoryReportBean(Element attrs) {
		this.band = attrs.getAttributeValue("band");
		this.freq = attrs.getAttributeValue("freq");
		this.level = attrs.getAttributeValue("level");
		this.spectrumid = "";
	}
	/**
	 * 边境版构造器
	 * @param attrs
	 * @param signalType 区分广播、电视上报 radioup 广播 tvup电视
	 */
	public SpectrumHistoryReportBean(Element attrs,String signalType) {
	    if(signalType.equals("radioup")){
	        this.band = attrs.getAttributeValue("band");
	    }
	    else if(signalType.equals("tvup")){
	        this.band = "";
        }

        this.freq = attrs.getAttributeValue("freq");
        this.level = attrs.getAttributeValue("level");
    }

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String head_id) {
		this.head_id = head_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getScandatetime() {
		return scandatetime;
	}

	public void setScandatetime(String scandatetime) {
		this.scandatetime = scandatetime;
	}

    public String getSpectrumid() {
        return spectrumid;
    }

    public void setSpectrumid(String spectrumid) {
        this.spectrumid = spectrumid;
    }
}