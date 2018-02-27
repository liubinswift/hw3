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
 * <p>Title:����ʶ��Ӧ�÷��ͽӿڹ���</p>
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
					     String ip=addr.getHostAddress();//��ñ���IP
					    
					 	String sql="  select * from sys_asr_run_config_tab t where t.status = 'true'   and (sysdate-t.lastruntime )*24*60<30 and ip!='"+ip+"'";
					 	 GDSet set= DbComponent.Query(sql); 
					     String queryIp="select * from sys_asr_run_config_tab where ip='"+ip+"'";
					     GDSet setquery=DbComponent.Query(queryIp);
						 if(set.getRowCount()>0)//˵���Ѿ���ִ�е�app.
						 {
						     //��ip���£�û�еĻ��Ͳ���
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
							  //����¼���ļ�����ռ��߳�
							 if(asrMarkCollectTask.getState()!=1)
							 {
							  asrMarkCollectTask.start();
							  
							 }
							  //����¼���ļ�����ʶ�����߳�
//							  AsrMarkSenderThread asrMarkSenderThread = new AsrMarkSenderThread();
//							  asrMarkSenderThread.setName("AsrMarkSenderThread");
//							  asrMarkSenderThread.start();
							ASRClient client = new ASRClient();
								
							ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();
							  while(AsrMarkCollectTask.getAsrTaskQueue().getQuerySize()>0){
									FileRetrieveResult result = AsrMarkCollectTask.getAsrTaskQueue().get();
									list.add(result);
									//ÿ��Ĭ��20������һ��
									if (list.size()>SENDCOUNT){
										client.exucuteTask(list);
										//�����������list
										list = new ArrayList<FileRetrieveResult>();
									}
									LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ�����¼���ļ�������ʶ��ϵͳ ׼������:"+list.size()+"��¼���ļ�.");
						        }
									//����ʣ�಻��ʮ���ķ��ͳ�ȥ
									if (list.size() >0){
										client.exucuteTask(list);
										LogTool.info("asr",StringTool.Date2String(new Date())+"��Ϣ���Զ�����¼���ļ�������ʶ��ϵͳ ׼������:"+list.size()+"��¼���ļ�.");
									}
								
							  
							  
//							  RmiService service = new RmiService();
//							  service.start();
							//��ip���£�û�еĻ��Ͳ���
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
							     //������appҪ��Ϊfalse
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
					}//��ʮ������Ϣ�¡�  
				
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
		     String ip=addr.getHostAddress();//��ñ���IP
		     System.out.println(ip);
       }
    

    }

