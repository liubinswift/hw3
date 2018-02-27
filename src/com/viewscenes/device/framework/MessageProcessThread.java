package com.viewscenes.device.framework;

import com.viewscenes.util.LogTool;
import com.viewscenes.device.exception.DeviceException;


import java.io.*;
import java.util.*;

import org.jdom.*;


public class MessageProcessThread extends Thread {

  FilterManager fm = new FilterManager("up");
  ProcessorManager pm = new ProcessorManager("up");
  public MessageRegister mr = null;
  public static HashMap messageMap = new HashMap();

  public MessageProcessThread() {
  }

  public void run(){
    while(true){
          String msg = (String)MessageServer.msgQueue.get();
          try {
            if (msg != null) {
              processMsg(msg);
              yield();
            }
            else {
              sleep(100);
            }
          }
          catch (InterruptedException ex) {
            LogTool.debug(ex);
          }
          catch(Exception ex){
            LogTool.warning(ex);
          }
        }
  }

  /**

   * �����յ���Ϣ��Ĵ������
   * @param msg �ַ�����ʽ����Ϣ
   * @preconditions msg != null

   */

  public void processMsg(String msg) {
    try {

      IMessage msgFromDevice = new UpMessage(msg);
      msgFromDevice = fm.doFilter(msgFromDevice);
      /**
       *  ��¼�ϱ���־.
       *  author:����
       */
        String replyID=(String) msgFromDevice.getHeader().getAttributes().get("ReplyID");

     // com.viewscenes.util.LogTool.debug("logMsg",msgFromDevice.getMessage());
    //  SaveDownUpMessage save=new SaveDownUpMessage();
     // save.saveUpMsg(msgFromDevice);



   String url=getAutoReportFile(msg.toString());
   if (url != "" && url.length() != 0) {
       //���н�ѹд�뵽��־�У���ʽд����С�

          if(replyID.equals("-1"))
          {
             msg= com.viewscenes.device.util.Zip.unZip(url).toString();
            msgFromDevice = new UpMessage(msg);
            msgFromDevice = fm.doFilter(msgFromDevice);//��body�ڵ�ת��Сд
//           ProcessMsg processMsg=new ProcessMsg();
//           processMsg.processMsg(msgFromDevice);
//           this.destroy();
            MessageElement header = msgFromDevice.getHeader(); // �����Ϣͷ
            Map map = header.getAttributes();
            map.put("ReplyID", "-1");
          }

      }


      mr.notify(msgFromDevice);
      pm.doProcess(msgFromDevice);

    } catch (DeviceException ex) {

    } catch (Exception ex){
      LogTool.warning(ex);
      LogTool.warning(msg);
    }
  }
  public String getAutoReportFile(String msg)
{
    Element root = getXMLRoot(msg);
    String code="";
    try
    {
     Element node=   (Element) root.getChildren("AutoReportFile").get(0);
         code = node.getAttributeValue("URL");
    }catch(Exception e)
    {
        return "";
    }
    if(code!="")
    {
        return code;
    }else
    {
        return "";
    }
}
public Element getXMLRoot(String msg) {
  Element root = null;
  String ret = "";
  //parse xml config file;
  try {
      StringReader read = new StringReader(msg);
      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);
      Document doc = builder.build(read);
      root = doc.getRootElement();
  } catch (JDOMException ex) {
      ex.printStackTrace();
  }
  return root;
}



}
