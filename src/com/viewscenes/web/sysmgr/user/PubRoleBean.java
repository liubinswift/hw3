package com.viewscenes.web.sysmgr.user;

public class PubRoleBean {
private String role_id;
	
	private String name;
	
	private String description;
	
	private String priority;

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "role_id="+role_id+";name="+name+";description="+description+";priority="+priority;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String roleId) {
		role_id = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	
	

}
