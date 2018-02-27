package com.viewscenes.bean.runplan;

import com.viewscenes.bean.BaseBean;

public class GJTLDRunplanBean extends BaseBean {

	public GJTLDRunplanBean() {
		// TODO Auto-generated constructor stub
	}
	private String runplan_id;        //����ͼid
	private String runplan_type_id;   //����ͼ����id
	private String sentcity_id;       //�������id
	private String sentcity;          //�����������
	private String redisseminators;   //ת������
	private String freq;              //Ƶ��
	private String band;              //����
	private String direction;         //����
	private String program_id;        //��Ŀid
	private String program_name;      // ��Ŀ����
	private String language_id;       //����id
	private String language;          //��������
	private String power;             //����
	private String service_area;      //������
	private String start_time;       //������ʼʱ��
	private String end_time;         //��������ʱ��
	private String local_time ;       //����ʱ��
	private String mon_area;         //�����ղ�վ��
	private String xg_mon_area;      //Ч���ղ�վ��
	private String weekday;//������
	private String store_datetime;//����ʱ��
	public String getStore_datetime() {
		return store_datetime;
	}
	public void setStore_datetime(String store_datetime) {
		this.store_datetime = store_datetime;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getXg_mon_area() {
		return xg_mon_area;
	}
	public void setXg_mon_area(String xg_mon_area) {
		this.xg_mon_area = xg_mon_area;
	}
	
	private String rest_datetime;    //��Ϣ����
	private String valid_start_time; //������
	private String valid_end_time;  //ͣ����
	private String remark;           //��ע
	private String summer;           // ����ʱ
	private String summer_starttime; //����ʱ����ʱ��
	private String summer_endtime;   //����ʱͣ��ʱ��
	private String flag;            //��־λ unit:�����ѯ��ǰ��Ԫ������ͼ��all:�����ѯȫ������ͼ
	private String launch_country;    //�������
	
	public String getLaunch_country() {
		return launch_country;
	}
	public void setLaunch_country(String launch_country) {
		this.launch_country = launch_country;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRunplan_id() {
		return runplan_id;
	}
	public void setRunplan_id(String runplan_id) {
		this.runplan_id = runplan_id;
	}
	public String getRunplan_type_id() {
		return runplan_type_id;
	}
	public void setRunplan_type_id(String runplan_type_id) {
		this.runplan_type_id = runplan_type_id;
	}
	public String getSentcity_id() {
		return sentcity_id;
	}
	public void setSentcity_id(String sentcity_id) {
		this.sentcity_id = sentcity_id;
	}
	public String getSentcity() {
		return sentcity;
	}
	public void setSentcity(String sentcity) {
		this.sentcity = sentcity;
	}
	public String getRedisseminators() {
		return redisseminators;
	}
	public void setRedisseminators(String redisseminators) {
		this.redisseminators = redisseminators;
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String program_id) {
		this.program_id = program_id;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getService_area() {
		return service_area;
	}
	public void setService_area(String service_area) {
		this.service_area = service_area;
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
	public String getLocal_time() {
		return local_time;
	}
	public void setLocal_time(String local_time) {
		this.local_time = local_time;
	}
	public String getMon_area() {
		return mon_area;
	}
	public void setMon_area(String mon_area) {
		this.mon_area = mon_area;
	}
	public String getRest_datetime() {
		return rest_datetime;
	}
	public void setRest_datetime(String rest_datetime) {
		this.rest_datetime = rest_datetime;
	}
	public String getValid_start_time() {
		return valid_start_time;
	}
	public void setValid_start_time(String valid_start_time) {
		this.valid_start_time = valid_start_time;
	}
	public String getValid_end_time() {
		return valid_end_time;
	}
	public void setValid_end_time(String valid_end_time) {
		this.valid_end_time = valid_end_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSummer() {
		return summer;
	}
	public void setSummer(String summer) {
		this.summer = summer;
	}
	public String getSummer_starttime() {
		return summer_starttime;
	}
	public void setSummer_starttime(String summer_starttime) {
		this.summer_starttime = summer_starttime;
	}
	public String getSummer_endtime() {
		return summer_endtime;
	}
	public void setSummer_endtime(String summer_endtime) {
		this.summer_endtime = summer_endtime;
	}
}
