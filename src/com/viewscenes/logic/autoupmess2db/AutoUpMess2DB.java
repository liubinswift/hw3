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

 * <p>Title: �����ϱ���Ϣ���</p>

 * <pre>Description: ��Ҫ����4���ӿ�: �����������</p>
 *                                �㲥�������
 *                                ��ȫ�������
 *                                ����ָ���ϱ����
 *
 * </pre>Copyright: Copyright (c) 2003</p>
 * <p>Company: ����ͬ�� ����Ʒ��</p>
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
    msgQueue = new ObjectQueue("�ϱ���Ϣ",MAX_MSG,MAX_MSG/2);
   // msgQueue.readObjectQueue();//��ȡ���л��ļ�
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
                      "AutoUpMess2DB:�豸�����msgΪ��");
      return;
    }
    msgQueue.add(msg);
  }

}

