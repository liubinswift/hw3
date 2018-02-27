package  org.jmask.web.controller.user;

import java.util.HashMap;

public class user {
	public String id;
	public user(String id){
		super();
		this.id=id;
	}
	public long lastTime=0 ;
	private HashMap<String,Object> result=new HashMap<String,Object>();
	synchronized public void setResult(String mid,Object ret){
		result.put(mid, ret);
	}
	synchronized public HashMap<String, Object> getResult(){
//		java.util.Date sDate = new java.util.Date();
//		lastTime = sDate.getTime();
		HashMap<String, Object> ret=result;
		result=new HashMap<String,Object>();
		return ret;
	}
}
