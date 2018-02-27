package com.viewscenes.timeTask;



import com.viewscenes.axis.asr.ASRClient;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;



/**
 * <p>Title:语音识别应用发送接口管理</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author not attributable
 * @version 1.0
 */
 
    public class AppManager extends Thread{

    	private  final static int  sleeptime = 10*1000;
		private final static int SENDCOUNT = 20;
        public AppManager() {

        }

        public void run(){
        	AsrMarkCollectTask asrMarkCollectTask = new AsrMarkCollectTask();
            while (true) {
				
            
				  try {

						 
						 InetAddress addr = InetAddress.getLocalHost();
					     String ip=addr.getHostAddress();//获得本机IP
					    
					 	String sql="  select * from sys_asr_run_config_tab t where t.status = 'true'   and (sysdate-t.lastruntime )*24*60<30 and ip!='"+ip+"'";
					 	 GDSet set= DbComponent.Query(sql); 
					     String queryIp="select * from sys_asr_run_config_tab where ip='"+ip+"'";
					     GDSet setquery=DbComponent.Query(queryIp);
						 if(set.getRowCount()>0)//说明已经有执行的app.
						 {
						     //有ip更新，没有的话就插入
						     if(setquery.getRowCount()>0)
						     {
						    	 String update="update  sys_asr_run_config_tab  t set status='false' ,t.lastruntime=sysdate where ip='"+ip+"'";
						    	 DbComponent.exeUpdate(update); 
						     }else
						     {
						    	 String insert="insert into sys_asr_run_config_tab(id,ip,status,lastruntime)" +
						    	 		" values(SEC_SEQ.Nextval,'"+ip+"','false',sysdate)";
						    	 DbComponent.exeUpdate(insert);  
						     }
						 }else
						 {
							  //启动录音文件打分收集线程
							 if(asrMarkCollectTask.getState()!=1)
							 {
							  asrMarkCollectTask.start();
							  
							 }
							  //启动录音文件语音识别发送线程
//							  AsrMarkSenderThread asrMarkSenderThread = new AsrMarkSenderThread();
//							  asrMarkSenderThread.setName("AsrMarkSenderThread");
//							  asrMarkSenderThread.start();
							ASRClient client = new ASRClient();
								
							ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();
							  while(AsrMarkCollectTask.getAsrTaskQueue().getQuerySize()>0){
									FileRetrieveResult result = AsrMarkCollectTask.getAsrTaskQueue().get();
									list.add(result);
									//每次默认20条发送一次
									if (list.size()>SENDCOUNT){
										client.exucuteTask(list);
										//发送完成重置list
										list = new ArrayList<FileRetrieveResult>();
									}
									LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统 准备发送:"+list.size()+"条录音文件.");
						        }
									//最后把剩余不足十条的发送出去
									if (list.size() >0){
										client.exucuteTask(list);
										LogTool.info("asr",StringTool.Date2String(new Date())+"信息：自动发送录音文件到语音识别系统 准备发送:"+list.size()+"条录音文件.");
									}
								
							  
							  
//							  RmiService service = new RmiService();
//							  service.start();
							//有ip更新，没有的话就插入
							     if(setquery.getRowCount()>0)
							     {
							    	 String update="update  sys_asr_run_config_tab  t set status='true' ,t.lastruntime=sysdate where ip='"+ip+"'";
							    	 DbComponent.exeUpdate(update); 
							     }else
							     {
							    	 String insert="insert into sys_asr_run_config_tab(id,ip,status,lastruntime)" +
							    	 		" values(SEC_SEQ.Nextval,'"+ip+"','true',sysdate)";
							    	 DbComponent.exeUpdate(insert);  
							     }
							     //其他的app要置为false
							     String  updateother="update sys_asr_run_config_tab set status='false' where ip!='"+ip+"'";
							     DbComponent.exeUpdate(updateother);  
						 }
						  
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					 try {
						Thread.sleep(1000*10*60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//三十分钟休息下。  
				
			}
        }
       public static void main(String[] args)
       {

			 InetAddress addr=null;
			try {
				addr = InetAddress.getLocalHost();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     String ip=addr.getHostAddress();//获得本机IP
		     System.out.println(ip);
       }
    

    }

