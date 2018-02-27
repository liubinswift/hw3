package com.viewscenes.timeTask.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IClient extends Remote{

	//设置当前工作服务器对象
	public void setCurWork(RmiMsgObj rmiMsgObj) throws RemoteException;
	
	//获取当前工作服务器对象
	public RmiMsgObj getCurWork() throws RemoteException;
	
	//删除服务器对象
	public boolean delWork(RmiMsgObj rmiMsgObj) throws RemoteException;
	
	//获取所有工作服务器对象列表
	public ArrayList<RmiMsgObj> getWorkList() throws RemoteException;
	
	//获取当前工作服务器对象的工作状态
	public boolean getCurWorkState() throws RemoteException;
	
	public int say() throws RemoteException;
}
