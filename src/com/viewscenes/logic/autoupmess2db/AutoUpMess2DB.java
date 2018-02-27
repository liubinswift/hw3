package com.viewscenes.logic.autoupmess2db;

import java.io.*;
import java.util.*;

import org.jdom.*;

import com.viewscenes.util.LogTool;
import com.viewscenes.util.ObjectQueue;
import java.sql.*;
import com.viewscenes.dao.database.*;
import com.viewscenes.device.framework.IMessage;
import com.viewscenes.device.framework.IMessageListener;
import com.viewscenes.pub.*;
import com.viewscenes.util.*;



/**

 * <p>Title: 主动上报消息入库</p>

 * <pre>Description: 主要包含4个接口: 质量报警入库</p>
 *                                广播报警入库
 *                                安全报警入库
 *                                技术指标上报入库
 *
 * </pre>Copyright: Copyright (c) 2003</p>
 * <p>Company: 永新同方 监测产品部</p>
 * @author not attributable
 * @version 1.0

 */


public class AutoUpMess2DB

    implements IMessageListener {

  static AutoUpMess2DB mess2db = null;
  static int MAX_PROCESSOR = 1;
  static int MAX_MSG = 10000;
  static ProcessMsg[] processors = null;
  public static ObjectQueue msgQueue = null;

  public AutoUpMess2DB getInstance(){
    if (mess2db==null)
      mess2db = new AutoUpMess2DB();
    return mess2db;
  }

  public AutoUpMess2DB() {
    loadConfig();
    msgQueue = new ObjectQueue("上报消息",MAX_MSG,MAX_MSG/2);
   // msgQueue.readObjectQueue();//读取序列化文件
    initProcessor();
  }
  public static void loadConfig(){

    String configFilePath = XMLConfigFile.getConfigFilePath("appserver.xml");
    Element root = null;
    try {
      root = FileTool.loadXMLFile(configFilePath);
    }
    catch (UtilException ex) {
      LogTool.warning(ex);
    }

    Element upmessage = root.getChild("up_message_processor");
    if (upmessage!=null){
       String processor = upmessage.getAttributeValue("max_processor");
      try {
        MAX_PROCESSOR = 1;//Integer.parseInt(processor);
      }
      catch (NumberFormatException ex1) {
      }

      String msg = upmessage.getAttributeValue("max_msg");
     try {
       MAX_MSG = Integer.parseInt(msg);
     }
     catch (NumberFormatException ex1) {
     }

    }

  }

  public static synchronized void initProcessor(){

    if (processors==null){
      processors = new ProcessMsg[MAX_PROCESSOR];
      for (int i = 0; i < MAX_PROCESSOR; i++) {
        processors[i] = new ProcessMsg();
        processors[i].setPriority(Thread.MIN_PRIORITY);
        processors[i].start();
      }
    }
  }

  public void onMessage(IMessage msg) {
    if (msg == null) {
      LogTool.warning("autoup2mess",
                      "AutoUpMess2DB:设备传入的msg为空");
      return;
    }
    msgQueue.add(msg);
  }

}

