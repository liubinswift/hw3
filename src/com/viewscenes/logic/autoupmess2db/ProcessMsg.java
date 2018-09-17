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
//	  _handleMap.put("radioupqualityalarmhistoryreport", new RadioQualityAlarmHistoryReport()); 		//�㲥ָ�걨����ʷ��ѯ���������ϱ��ӿ�
//	  _handleMap.put("radioupequipmentloghistoryqueryr", new RadioEquipmentLogHistoryQuery());			//�㲥�豸��ʷ��־��ѯ���������ϱ�
//	  _handleMap.put("radioupequipmentstatusrealtimereport", new RadioEquipmentStatusRealtimeReport());	//�㲥�豸״̬ʵʱ��������ϱ��ӿ�
//
//
//      _handleMap.put("radioupqualityhistoryreport", new RadioQualityHistoryReport());					//�㲥ָ����ʷ��ѯ���������ϱ��ӿ�
//	  _handleMap.put("radioupqualityrealtimereport", new RadioQualityRealtimeReport());					//�㲥ָ��ʵʱ�����ϱ��ӿ�
//      _handleMap.put("radioupspectrumhistoryreport", new RadioSpectrumHistoryReport());					//�㲥Ƶ����ʷ��ѯ���������ϱ��ӿ�
//      _handleMap.put("radioupspectrumrealtimereport", new RadioSpectrumRealtimeReport());				//�㲥Ƶ��ʵʱ�����ϱ��ӿ�
//      _handleMap.put("radioupreceivercontrolreport", new RadioReceiverControlReport());					//�㲥���ջ����������ϱ��ӿ�
//
//
//      _handleMap.put("radioupequipmentalarmhistoryreport", new RadioEquipmentAlarmHistoryReport());	    //�㲥�豸�������������ϱ��ӿ�
//      _handleMap.put("radioupoffsethistoryreport", new RadioOffsetHistoryReport());	    				//�㲥Ƶƫ��ʷ��ѯ�������������ϱ��ӿ�

	  _handleMap.put("qualityalarmhistoryreport", new RadioQualityAlarmHistoryReport()); 		//�㲥ָ�걨����ʷ��ѯ���������ϱ��ӿ�
	  _handleMap.put("equipmentloghistoryqueryr", new RadioEquipmentLogHistoryQuery());			//�㲥�豸��ʷ��־��ѯ���������ϱ�
	  _handleMap.put("equipmentstatusrealtimereport", new RadioEquipmentStatusRealtimeReport());	//�㲥�豸״̬ʵʱ��������ϱ��ӿ�


      _handleMap.put("qualityhistoryreport", new RadioQualityHistoryReport());					//�㲥ָ����ʷ��ѯ���������ϱ��ӿ�
	  _handleMap.put("qualityrealtimereport", new RadioQualityRealtimeReport());					//�㲥ָ��ʵʱ�����ϱ��ӿ�
      _handleMap.put("spectrumhistoryreport", new RadioSpectrumHistoryReport());					//�㲥Ƶ����ʷ��ѯ���������ϱ��ӿ�
      _handleMap.put("spectrumrealtimereport", new RadioSpectrumRealtimeReport());				//�㲥Ƶ��ʵʱ�����ϱ��ӿ�
      _handleMap.put("receivercontrolreport", new RadioReceiverControlReport());					//�㲥���ջ����������ϱ��ӿ�


      _handleMap.put("equipmentalarmhistoryreport", new RadioEquipmentAlarmHistoryReport());	    //�㲥�豸�������������ϱ��ӿ�
      _handleMap.put("offsethistoryreport", new RadioOffsetHistoryReport());	    				//�㲥Ƶƫ��ʷ��ѯ�������������ϱ��ӿ�
     
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
    MessageElement header = msg.getHeader(); // �����Ϣͷ
    MessageElement body = msg.getBody(); // �����Ϣ��
    //LogTool.info("devicelog", msg.getMessage());
    try {

      Map map = header.getAttributes();
//      String name = (map.get("Type") + body.getName()).toLowerCase();
      //map.get("Type") �߾�ʱ��radioΪ�㲥��tvΪ���ӣ�commonΪ����
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
   * ����XML��,���ָ���Ľڵ�
   *
   * @param message �� Ҫ������xml��,�����Կ�
   *
   * @return: һ��Element�Ľڵ�
   */

  private static Element parseXml(String message) throws UpMess2DBException {

    try {

      //���������õ�Document

      StringReader read = new StringReader(message);

      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);

      Document doc = builder.build(read);

      //�õ����ڵ�LIT
      return doc.getRootElement();
    }
    catch (Exception e) {
      throw new UpMess2DBException("����������Ϣ����", e);
    }
  }

}
