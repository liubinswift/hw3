package com.viewscenes.bean;
/**
 * *************************************   
*    
* 项目名称：HW   
* 类名称：ClientInfo   
* 类描述：  客户端连接bean 
* 创建人：刘斌
* 创建时间：Aug 8, 2012 2:50:41 PM   
* 修改人：刘斌
* 修改时间：Aug 8, 2012 2:50:41 PM   
* 修改备注：   
* @version    
*    
***************************************
 */
public class ClientInfo {
	private String headname;
	private String code;
	private String equCode;
	private String url;
	private String freq;
	private String bps;
	private String ip;
	private String user_id;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getHeadname() {
		return headname;
	}
	public void setHeadname(String headname) {
		this.headname = headname;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getBps() {
		return bps;
	}
	public void setBps(String bps) {
		this.bps = bps;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

}
