package com.viewscenes.web.sysmgr.user;

public class PubFuncBean {


	private String func_id; 
	private String code; 
	private String name; 
	private String orders; 
	private String levels;
	private String parent_func_id; 
	private String show_flag;
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "func_id="+func_id+";code="+code+";name="+name+";orders="+orders+";levels="+levels+";parent_func_id="+parent_func_id+";show_flag="+show_flag;
	}
	
	public String getFunc_id() {
		return func_id;
	}
	public void setFunc_id(String funcId) {
		func_id = funcId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getParent_func_id() {
		return parent_func_id;
	}
	public void setParent_func_id(String parentFuncId) {
		parent_func_id = parentFuncId;
	}
	public String getShow_flag() {
		return show_flag;
	}
	public void setShow_flag(String showFlag) {
		show_flag = showFlag;
	}
	
}
