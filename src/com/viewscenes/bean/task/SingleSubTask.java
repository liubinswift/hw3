package com.viewscenes.bean.task;
/**
 * *************************************   
*    
* 项目名称：HW   
* 类名称：SingleSubTask   
* 类描述：   
* 创建人：刘斌
* 创建时间：2012-7-19 下午03:00:53   
* 修改人：刘斌
* 修改时间：2012-7-19 下午03:00:53   
* 修改备注：   
* @version    
*    
***************************************
 */
public class SingleSubTask {

	public String getTime_id() {
		return time_id;
	}
	public void setTime_id(String time_id) {
		this.time_id = time_id;
	}
	public String getReportmode() {
		return reportmode;
	}
	public void setReportmode(String reportmode) {
		this.reportmode = reportmode;
	}
	public String getReportinterval() {
		return reportinterval;
	}
	public void setReportinterval(String reportinterval) {
		this.reportinterval = reportinterval;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	public String getStartdatetime() {
		return startdatetime;
	}
	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}
	public String getEnddatetime() {
		return enddatetime;
	}
	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}
	public String getExpiredays() {
		return expiredays;
	}
	public void setExpiredays(String expiredays) {
		this.expiredays = expiredays;
	}
	public String getSub_task_id() {
		return sub_task_id;
	}
	public void setSub_task_id(String sub_task_id) {
		this.sub_task_id = sub_task_id;
	}
	public SingleSubTask() {
		// TODO Auto-generated constructor stub
	}
	private String time_id;
	private String reportmode;
	private String reportinterval;
	private String reporttime;
	private String startdatetime;
	private String enddatetime;
	private String expiredays;
	private String sub_task_id;
}
