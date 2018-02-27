package com.viewscenes.bean.runplan;

import com.viewscenes.bean.BaseBean;

/**
 * 国际台运行图类
 * @author 王福祥
 * @date 2012/07/18
 */
public class GJTRunplanBean extends BaseBean  {

	public GJTRunplanBean() {
		// TODO Auto-generated constructor stub
	}
	private String runplan_id;         //运行图id
	private String runplan_type_id;    //运行图类型id
	private String station_id;         //发射台id
	private String station_name;       //发射台名称
	private String sentcity_id;        //发射城市id
	private String sentcity;           //发射城市名称
	private String transmiter_no;     //发射机号
	private String freq;              //频率
	private String antenna;           //天线号
	private String antennatype;       //天线程式
	private String direction;         //方向
	private String program_id;        //节目id
	private String program_name;      // 节目名称
	private String language_id;       //语言id
	private String language;          //语言名称
	private String power;             //功率
	private String program_type_id;   //节目类型id
	private String program_type;      //节目类型
	private String service_area;      //服务区
	private String ciraf;             // CIRAF区
	private String satellite_channel; //国际卫星通道
	private String start_time;        //播音开始时间
	private String end_time;          //播音结束时间
	private String mon_area;          //遥控站收测
	private String rest_datetime;     //休息日期
	private String rest_time;         //休息时间
	private String valid_start_time;  //启用期
	private String valid_end_time;    //停用期
	private String remark;            //备注
	private String xg_mon_area;     //效果收测站点
	private String flag = "";            //标志位 unit:代表查询当前单元的运行图；all:代表查询全部运行图
	private String season_id="";//季节代号
	private String weekday="";//周设置
	private String store_datetime="";//导入日期
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
	public String getSeason_id() {
		return season_id;
	}
	public void setSeason_id(String season_id) {
		this.season_id = season_id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getXg_mon_area() {
		return xg_mon_area;
	}
	public void setXg_mon_area(String xg_mon_area) {
		this.xg_mon_area = xg_mon_area;
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
	public String getTransmiter_no() {
		return transmiter_no;
	}
	public void setTransmiter_no(String transmiter_no) {
		this.transmiter_no = transmiter_no;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getAntenna() {
		return antenna;
	}
	public void setAntenna(String antenna) {
		this.antenna = antenna;
	}
	public String getAntennatype() {
		return antennatype;
	}
	public void setAntennatype(String antennatype) {
		this.antennatype = antennatype;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
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
	public String getProgram_type_id() {
		return program_type_id;
	}
	public void setProgram_type_id(String program_type_id) {
		this.program_type_id = program_type_id;
	}
	public String getProgram_type() {
		return program_type;
	}
	public void setProgram_type(String program_type) {
		this.program_type = program_type;
	}
	public String getService_area() {
		return service_area;
	}
	public void setService_area(String service_area) {
		this.service_area = service_area;
	}
	public String getCiraf() {
		return ciraf;
	}
	public void setCiraf(String ciraf) {
		this.ciraf = ciraf;
	}
	public String getSatellite_channel() {
		return satellite_channel;
	}
	public void setSatellite_channel(String satellite_channel) {
		this.satellite_channel = satellite_channel;
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
	public String getRest_time() {
		return rest_time;
	}
	public void setRest_time(String rest_time) {
		this.rest_time = rest_time;
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
	

}
