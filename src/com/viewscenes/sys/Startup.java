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
	   * 系统启动前先判断是中心系统还是直属台系统。
	   * 
	   */
	  TableInfoCache s = new TableInfoCache();

	  /**
	   * 加载系统相关路径配置信息
	   */
	  SystemConfig.loadConfig();
	  /**
	   * 根据日期code提前创建ftp文件夹
	   */
	  CreateFtpFolderThread ftp=new CreateFtpFolderThread();
	  ftp.start();
	
	  //系统运行位置0:外网的内网，1：外网
	  String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
	  if (runat.equals("0")){
		  /**
		   * 每天定时导出数据
		   */
		  AlarmClock alarm=new AlarmClock(00,05,00);
		  alarm.start();
         //语音识别  
		  AppManager m = new AppManager();
		  m.start();
		  
		  
	  } else if (runat.equals("1")){
		  /**
		   * 判断站点是否在线
		   */
		  CheakHeadOnLineThread online = new CheakHeadOnLineThread();
		  online.start();
	  }
  }
}
