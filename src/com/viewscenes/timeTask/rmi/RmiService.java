package com.viewscenes.timeTask.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiService {

	
	public void start(){
		try {
			IClient client = new RmiClient();
			
			LocateRegistry.createRegistry(8888); 

			Naming.bind("rmi://localhost:8888/Client",client); 
			
			 System.out.println(">>>>>INFO:远程client对象绑定成功！"); 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
		
	}
}
