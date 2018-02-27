package com.viewscenes.sys;



//import com.novel_tongfang.mon.framework.task.TaskManager;
import com.viewscenes.app.CreateFtpFolderThread;
import com.viewscenes.app.thread.CheakHeadOnLineThread;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.sys.SystemTableCache;
import com.viewscenes.task.thread.AsrMarkSenderThread;
import com.viewscenes.timeTask.AlarmClock;
import com.viewscenes.timeTask.AppManager;
import com.viewscenes.timeTask.AsrMarkCollectTask;
import com.viewscenes.timeTask.rmi.AsrRemoteManager;
import com.viewscenes.timeTask.rmi.RmiService;
public class Startup {
  public static void main(String[] args) {
    initService();
  }

  public static void initService(){
	  /**
	   * ϵͳ����ǰ���ж�������ϵͳ����ֱ��̨ϵͳ��
	   * 
	   */
	  TableInfoCache s = new TableInfoCache();

	  /**
	   * ����ϵͳ���·��������Ϣ
	   */
	  SystemConfig.loadConfig();
	  /**
	   * ��������code��ǰ����ftp�ļ���
	   */
	  CreateFtpFolderThread ftp=new CreateFtpFolderThread();
	  ftp.start();
	
	  //ϵͳ����λ��0:������������1������
	  String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
	  if (runat.equals("0")){
		  /**
		   * ÿ�춨ʱ��������
		   */
		  AlarmClock alarm=new AlarmClock(00,05,00);
		  alarm.start();
         //����ʶ��  
		  AppManager m = new AppManager();
		  m.start();
		  
		  
	  } else if (runat.equals("1")){
		  /**
		   * �ж�վ���Ƿ�����
		   */
		  CheakHeadOnLineThread online = new CheakHeadOnLineThread();
		  online.start();
	  }
  }
}
