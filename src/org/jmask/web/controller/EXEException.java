package org.jmask.web.controller;

public class EXEException {
	public EXEException(String id,String message,Object data){
		this.excId=id;
		this.message=message;
		this.data=data;
	}
	public String excId;
	public String message;
	public Object data;
}
