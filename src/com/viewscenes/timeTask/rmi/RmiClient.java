package com.viewscenes.timeTask.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.viewscenes.timeTask.AsrMarkCollectTask;

public class RmiClient  extends UnicastRemoteObject implements IClient{

	private static RmiMsgObj curRmiMsgObj;	//当前正在工作的服务器对象
	
	private static ArrayList<RmiMsgObj> rmiMsgObjList = new ArrayList<RmiMsgObj>();
	
	
	private static int test=0;
	
	private static final long serialVersionUID = 1L;
	
	protected RmiClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean delWork(RmiMsgObj rmiMsgObj) throws RemoteException {
		// TODO Auto-generated method stub
		return rmiMsgObjList.remove(rmiMsgObj);
	}

	@Override
	public RmiMsgObj getCurWork() throws RemoteException {
		// TODO Auto-generated method stub
		return curRmiMsgObj;
	}

	@Override
	public ArrayList<RmiMsgObj> getWorkList() throws RemoteException {
		// TODO Auto-generated method stub
		return rmiMsgObjList;
	}

	@Override
	public void setCurWork(RmiMsgObj rmiMsgObj) throws RemoteException {
		// TODO Auto-generated method stub
		curRmiMsgObj = rmiMsgObj;
	}

//	@Override
//	public void setWork(RmiMsgObj rmiMsgObj) throws RemoteException {
//		// TODO Auto-generated method stub
//		
//		if (!rmiMsgObjList.contains(rmiMsgObj))
//			rmiMsgObjList.add(rmiMsgObj);
//		
//	}


	@Override
	public int say() throws RemoteException {
		// TODO Auto-generated method stub
		return test++;
	}


	@Override
	public boolean getCurWorkState() throws RemoteException {
		// TODO Auto-generated method stub
		
		boolean sender = false;	//发送状态
		boolean collect = false;//收集状态	
		
		//查看发送线程是否还活着
		Thread[] threads = new Thread[Thread.activeCount()];
		int n = Thread.enumerate(threads);
		for(int i = 0; i < n; i++){
			if (threads [i].getName().equals("AsrMarkSenderThread")){
				sender = true;
			}
		}
		
		//查看收集对象是否还活着
		collect = AsrMarkCollectTask.getState() ==1;
		return sender && collect;
	}
	
	

}
