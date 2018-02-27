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
	        	String ip=addr.getHostAddress();//��ñ���IP
	        	String address=addr.getHostName();//��ñ�������
	        	
	        	System.out.println(ip); 
	            //��RMI����ע����в�������ΪRHello�Ķ��󣬲��������ϵķ��� 
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
