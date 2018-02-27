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

   * 控制收到消息后的处理过程
   * @param msg 字符串形式的消息
   * @preconditions msg != null

   */

  public void processMsg(String msg) {
    try {

      IMessage msgFromDevice = new UpMessage(msg);
      msgFromDevice = fm.doFilter(msgFromDevice);
      /**
       *  记录上报日志.
       *  author:刘斌
       */
        String replyID=(String) msgFromDevice.getHeader().getAttributes().get("ReplyID");

     // com.viewscenes.util.LogTool.debug("logMsg",msgFromDevice.getMessage());
    //  SaveDownUpMessage save=new SaveDownUpMessage();
     // save.saveUpMsg(msgFromDevice);



   String url=getAutoReportFile(msg.toString());
   if (url != "" && url.length() != 0) {
       //进行解压写入到日志中，正式写入库中。

          if(replyID.equals("-1"))
          {
             msg= com.viewscenes.device.util.Zip.unZip(url).toString();
            msgFromDevice = new UpMessage(msg);
            msgFromDevice = fm.doFilter(msgFromDevice);//将body节点转换小写
//           ProcessMsg processMsg=new ProcessMsg();
//           processMsg.processMsg(msgFromDevice);
//           this.destroy();
            MessageElement header = msgFromDevice.getHeader(); // 获得消息头
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
