package com.viewscenes.timeTask.rmi;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jdom.Element;

import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.task.thread.AsrMarkSenderThread;
import com.viewscenes.timeTask.AsrMarkCollectTask;
import com.viewscenes.timeTask.DailyIterator;
import com.viewscenes.timeTask.Scheduler;
import com.viewscenes.timeTask.SchedulerTask;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

public class AsrRemoteManager {
	static String runat = "0";
	static String localIp = "localhost";
	static ArrayList<RmiMsgObj> rmiMsgObjList = new ArrayList<RmiMsgObj>();
	static{
		
		//获得本机IP
		try {
			InetAddress addr = InetAddress.getLocalHost();
			localIp = addr.getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		
		//系统运行位置0:外网的内网，1：外网
//		Element runConfigE  = XmlReader.getConfigItem("RunConfig");
//		runat = runConfigE.getAttributeValue("runat");
		
		
//		String sql = " select * from sys_asr_run_config_tab t ";
//		try {
//			if (runat.equals("0")){
//				GDSet set = DbComponent.Query(sql);
//				//初始化，加载所有部署服务器的信息
//				for(int i=0;i<set.getRowCount();i++){
//					RmiMsgObj rmiMsgObj = new RmiMsgObj();
//					String ip = set.getString(i, "ip");
//					int port = Integer.parseInt(set.getString(i, "port"));
//					int priority = Integer.parseInt(set.getString(i, "priority"));
//					String lastRuntime = set.getString(i, "lastruntime");
//					String status = set.getString(i, "status");
//					
//					
//					rmiMsgObj.setIp(ip);
//					rmiMsgObj.setPort(port);
//					rmiMsgObj.setPriority(priority);
//					rmiMsgObj.setLastRuntime(lastRuntime);
//					rmiMsgObj.setStatus(status);
//					rmiMsgObjList.add(rmiMsgObj);
//				}
//				
//			}
//		} catch (DbException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (GDSetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	private final Scheduler scheduler = new Scheduler();// 调度器
	private final int hourOfDay, minute, second;		// 每天触发的时间点
	private final int interval = 2;						//执行远程监控管理间隔时间


	@SuppressWarnings("deprecation")
	public AsrRemoteManager() {
		Date d = new Date();
		this.hourOfDay = d.getHours();
		this.minute = d.getMinutes();
		this.second = d.getSeconds();
	}

	public void start() {
		scheduler.schedule(new SchedulerTask() {
			public void run() {
				String sql = " select ID, IP, PORT, PRIORITY, STATUS, LASTRUNTIME from sys_asr_run_config_tab t for update ";
				
				try {
					Connection con = DbComponent.getDB();
					con.setAutoCommit(false);
					java.sql.Statement  st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			                  ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = st.executeQuery(sql);
					
					while(rs.next()){
						IClient client = null;
						
						RmiMsgObj rmiMsgObj = new RmiMsgObj();
						String ip = rs.getString("ip");
						int port = Integer.parseInt(rs.getString("port"));
						int priority = Integer.parseInt(rs.getString("priority"));
						String lastRuntime = rs.getString("lastruntime");
						String status = rs.getString("status");
						rmiMsgObj.setIp(ip);
						rmiMsgObj.setPort(port);
						rmiMsgObj.setPriority(priority);
						rmiMsgObj.setLastRuntime(lastRuntime);
						rmiMsgObj.setStatus(status);
						
						if (rmiMsgObj.getStatus().equals("1")){
							
							boolean isRun = true;
							try {
								client =(IClient) Naming.lookup("rmi://"+rmiMsgObj.getIp()+":"+rmiMsgObj.getPort()+"/Client");
								isRun = client.getCurWorkState();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							if (!isRun || client==null){
								rs.updateString("status", "0");
								rs.updateRow();
							}else{
								try {
									client.setCurWork(rmiMsgObj);

									LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统运行在:"+client.getCurWork().getIp());
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
						
						if (rmiMsgObj.getIp().equals(localIp) && !rmiMsgObj.getStatus().equals("1")){
							rs.updateString("status", "1");
							rs.updateRow();
							
							
							
						  //启动录音文件打分收集线程
						  AsrMarkCollectTask asrMarkCollectTask = new AsrMarkCollectTask();
						  asrMarkCollectTask.start();
						  //启动录音文件语音识别发送线程
						  AsrMarkSenderThread asrMarkSenderThread = new AsrMarkSenderThread();
						  asrMarkSenderThread.setName("AsrMarkSenderThread");
						  asrMarkSenderThread.start();
						  
						  client =(IClient) Naming.lookup("rmi://"+rs.getString("ip")+":"+rs.getString("port")+"/Client");
						  
						  client.setCurWork(rmiMsgObj);
						  LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统运行在:"+client.getCurWork().getIp());
						}
						
					}

					
					con.setAutoCommit(true);
					con.commit();
					
					if (rs!=null)
						rs.close();
					if (st!=null)
						st.close();
					if (con!=null)
						con.close();
					
					
				} catch (DbException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
					
					
					
			}
		}, new DailyIterator(Calendar.MINUTE, interval, hourOfDay, minute, second));// 通过迭代器模式迭代遍历得到后面一系列的时间点
	}
	
	
	
	
}
