package com.viewscenes.timeTask.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IClient extends Remote{

	//���õ�ǰ��������������
	public void setCurWork(RmiMsgObj rmiMsgObj) throws RemoteException;
	
	//��ȡ��ǰ��������������
	public RmiMsgObj getCurWork() throws RemoteException;
	
	//ɾ������������
	public boolean delWork(RmiMsgObj rmiMsgObj) throws RemoteException;
	
	//��ȡ���й��������������б�
	public ArrayList<RmiMsgObj> getWorkList() throws RemoteException;
	
	//��ȡ��ǰ��������������Ĺ���״̬
	public boolean getCurWorkState() throws RemoteException;
	
	public int say() throws RemoteException;
}
