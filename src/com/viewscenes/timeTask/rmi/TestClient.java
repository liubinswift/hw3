package com.viewscenes.timeTask.rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TestClient {

	  public static void main(String args[]){ 
	        try { 
	        	
	        	InetAddress addr = InetAddress.getLocalHost();
	        	String ip=addr.getHostAddress();//获得本机IP
	        	String address=addr.getHostName();//获得本机名称
	        	
	        	System.out.println(ip); 
	            //在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法 
	            IClient client =(IClient) Naming.lookup("rmi://localhost:8888/Client"); 
//	            
	        } catch (NotBoundException e) { 
	            e.printStackTrace(); 
	        } catch (MalformedURLException e) { 
	            e.printStackTrace(); 
	        } catch (RemoteException e) { 
	            e.printStackTrace();   
	        } catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    } 
}
