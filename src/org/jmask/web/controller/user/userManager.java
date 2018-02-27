package  org.jmask.web.controller.user;

import java.util.HashMap;

public class userManager {
	private userManager(){
		super();
	}
	public  HashMap<String,user> dicUser=new HashMap<String,user>();
	public  boolean processFlag=false;
	private static userManager UM;
	public static userManager getInstance(){
		if(UM==null){
			UM=new userManager();
			userManagerListener UML=new userManagerListener(UM);
			UML.setDaemon(true);
			UML.start();
		}
		return UM;
	}
	public user getUser(String id){
		if(processFlag){
				synchronized(this){
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(dicUser.containsKey(id)){
			return dicUser.get(id);
		}else{
			return creatUser(id);
		}
	}
	synchronized public  user creatUser(String id){
		if(dicUser.containsKey(id)){
			return dicUser.get(id);
		}else{
			dicUser.put(id, new user(id));
			return dicUser.get(id);
		}
	}
}
