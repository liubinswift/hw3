package org.jmask.web.controller;
import org.jmask.web.controller.user.*;

import flex.messaging.util.MethodMatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class processThread extends Thread {
	public processThread(String mid,String cla,String fun,Object[] param,String CLID){
		super();
		this.mid=mid;
		this.cla=cla;
		this.fun=fun;
		this.CLID=CLID;
		this.param=param;
	}
	private String mid;
	private String CLID;
	private String cla;
	private String fun;
	private Object[] param;
	public void run(){
		Object ret=null;
		try {
    		Class<?> cl = Class.forName(cla);
    		Object obj = cl.newInstance();
    		Method method=null;
    		try{
    			MethodMatcher mm=new MethodMatcher();
        		method =mm.getMethod(cl, fun, Arrays.asList(param));
    		}catch(Exception e){
    			//反射方法或转换参数出错
    			ret=new EXEException("",e.getMessage(),null);
				e.printStackTrace();
    		}
    		if(method!=null){
    			ret=method.invoke(obj, param);
    		}
		} catch (SecurityException e) {
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//参数类型出错
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			//执行出错
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//找不到类
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		} catch (InstantiationException e) {
			//实例化出错
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		} catch(Exception e){
			ret=new EXEException("",e.getMessage(),null);
			e.printStackTrace();
		}
		userManager.getInstance().getUser(CLID).setResult(mid,ret);
	}
}
