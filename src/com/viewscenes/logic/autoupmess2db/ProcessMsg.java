package com.viewscenes.logic.autoupmess2db;



import com.viewscenes.logic.autoupmess2db.Exception.*;
import com.viewscenes.logic.autoupmess2db.MessProcess.*;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.HeadendStateChange;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioEquipmentAlarmHistoryReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioEquipmentLogHistoryQuery;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioEquipmentStatusRealtimeReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioIpChange;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioOffsetHistoryReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioQualityAlarmHistoryReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioQualityHistoryReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioQualityRealtimeReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioReceiverControlReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioSpectrumHistoryReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioSpectrumRealtimeReport;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioStreamHistoryQuery;
import com.viewscenes.util.LogTool;
import com.viewscenes.device.framework.IMessage;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.framework.MessageServer;

import java.io.*;
import java.util.*;

import org.jdom.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ProcessMsg extends Thread {

    private Map _handleMap = new HashMap();

  public ProcessMsg() {
//	  _handleMap.put("radioupqualityalarmhistoryreport", new RadioQualityAlarmHistoryReport()); 		//广播指标报警历史查询返回主动上报接口
//	  _handleMap.put("radioupequipmentloghistoryqueryr", new RadioEquipmentLogHistoryQuery());			//广播设备历史日志查询返回主动上报
//	  _handleMap.put("radioupequipmentstatusrealtimereport", new RadioEquipmentStatusRealtimeReport());	//广播设备状态实时结果主动上报接口
//
//
//      _handleMap.put("radioupqualityhistoryreport", new RadioQualityHistoryReport());					//广播指标历史查询返回主动上报接口
//	  _handleMap.put("radioupqualityrealtimereport", new RadioQualityRealtimeReport());					//广播指标实时主动上报接口
//      _handleMap.put("radioupspectrumhistoryreport", new RadioSpectrumHistoryReport());					//广播频谱历史查询返回主动上报接口
//      _handleMap.put("radioupspectrumrealtimereport", new RadioSpectrumRealtimeReport());				//广播频谱实时主动上报接口
//      _handleMap.put("radioupreceivercontrolreport", new RadioReceiverControlReport());					//广播接收机控制主动上报接口
//
//
//      _handleMap.put("radioupequipmentalarmhistoryreport", new RadioEquipmentAlarmHistoryReport());	    //广播设备报警数据主动上报接口
//      _handleMap.put("radioupoffsethistoryreport", new RadioOffsetHistoryReport());	    				//广播频偏历史查询返回数据主动上报接口

	  _handleMap.put("qualityalarmhistoryreport", new RadioQualityAlarmHistoryReport()); 		//广播指标报警历史查询返回主动上报接口
	  _handleMap.put("equipmentloghistoryqueryr", new RadioEquipmentLogHistoryQuery());			//广播设备历史日志查询返回主动上报
	  _handleMap.put("equipmentstatusrealtimereport", new RadioEquipmentStatusRealtimeReport());	//广播设备状态实时结果主动上报接口


      _handleMap.put("qualityhistoryreport", new RadioQualityHistoryReport());					//广播指标历史查询返回主动上报接口
	  _handleMap.put("qualityrealtimereport", new RadioQualityRealtimeReport());					//广播指标实时主动上报接口
      _handleMap.put("spectrumhistoryreport", new RadioSpectrumHistoryReport());					//广播频谱历史查询返回主动上报接口
      _handleMap.put("spectrumrealtimereport", new RadioSpectrumRealtimeReport());				//广播频谱实时主动上报接口
      _handleMap.put("receivercontrolreport", new RadioReceiverControlReport());					//广播接收机控制主动上报接口


      _handleMap.put("equipmentalarmhistoryreport", new RadioEquipmentAlarmHistoryReport());	    //广播设备报警数据主动上报接口
      _handleMap.put("offsethistoryreport", new RadioOffsetHistoryReport());	    				//广播频偏历史查询返回数据主动上报接口
     
      _handleMap.put("ipchange", new RadioIpChange());	    
      _handleMap.put("fileretriever", new RadioStreamHistoryQuery());	
      
      _handleMap.put("headendstatechange", new HeadendStateChange());
    
  }

  public void run(){
    while(true){
          IMessage msg = (IMessage)AutoUpMess2DB.msgQueue.get();
          try {
            if (msg != null) {
              while (MessageServer.msgQueue.getQuerySize()>0){
                sleep(100);
              }
              processMsg(msg);
              sleep(100);
            }
          }
          catch(Exception ex){
            LogTool.warning(ex);
          }
        }
  }


  public static void main(String[] args) {

}
  public void test() throws Exception {
      BufferedReader reader = null;
      String path = "C:\\zyh\\test\\RadioEquipmentStatusRealtimeReport.xml";
      String path1 = "C:\\zyh\\test\\RadioEquipmentStatusRealtimeReport1111.xml";
      String path2 = "C:\\zyh\\test\\RadioEquipmentStatusRealtimeReport2222.xml";
      String msg = "";
      String msg1 = "";
      String msg2 = "";
      try {
          reader = new BufferedReader(new FileReader(path));
          String line;
          while ((line = reader.readLine()) != null) {
              msg += line;
          }
          reader.close();
          ProcessMsg process = new ProcessMsg();
          MessageServer.setMsg2Queue(msg);

          reader = new BufferedReader(new FileReader(path1));
          while ((line = reader.readLine()) != null) {
              msg1 += line;
          }
          reader.close();
          MessageServer.setMsg2Queue(msg1);
          reader = new BufferedReader(new FileReader(path2));
          while ((line = reader.readLine()) != null) {
              msg2 += line;
          }
          reader.close();
          MessageServer.setMsg2Queue(msg2);
      } catch (Exception e) {
          // TODO: handle exception
          e.printStackTrace();
      }
}
  public  void processMsg(IMessage msg){
    if(msg== null)
      return ;
    MessageElement header = msg.getHeader(); // 获得消息头
    MessageElement body = msg.getBody(); // 获得消息体
    //LogTool.info("devicelog", msg.getMessage());
    try {

      Map map = header.getAttributes();
//      String name = (map.get("Type") + body.getName()).toLowerCase();
      //map.get("Type") 边境时：radio为广播；tv为电视；common为公共
      String name = body.getName().toLowerCase();
      if(name.equals("fileretriever")){
    	  LogTool.info("autoupRecord", msg.getMessage());  
      }
      IUpMsgProcessor handle = (IUpMsgProcessor) _handleMap.get(name);


      if (handle == null) {
        LogTool.warning("Can't find proper handler for up message!\r\n"+msg.getMessage());
        return;
      }
      handle.processUpMsg(parseXml(msg.getMessage()));
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      LogTool.warning("autoup2mess",
                      "AutoUpMess2DB:" + ex.getMessage());
    }
  }



  /**
   * 解析XML串,获得指定的节点
   *
   * @param message ： 要解析的xml串,不可以空
   *
   * @return: 一个Element的节点
   */

  private static Element parseXml(String message) throws UpMess2DBException {

    try {

      //初步解析得到Document

      StringReader read = new StringReader(message);

      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);

      Document doc = builder.build(read);

      //得到根节点LIT
      return doc.getRootElement();
    }
    catch (Exception e) {
      throw new UpMess2DBException("解析上行消息错误", e);
    }
  }

}
