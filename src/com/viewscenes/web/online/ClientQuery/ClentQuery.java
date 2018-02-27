package com.viewscenes.web.online.ClientQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jdom.Attribute;
import org.jmask.web.controller.EXEException;


import com.viewscenes.bean.ClientInfo;
import com.viewscenes.device.api.StreamAPI;
import com.viewscenes.device.radio.MsgStreamRealtimeClientQueryRes;
import com.viewscenes.device.radio.MsgStreamRealtimeClientStopCmd;
import com.viewscenes.sys.Operation_log;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.Daoutil.SiteVersionUtil;

import flex.messaging.io.amf.ASObject;

public class ClentQuery {
	  /**
     * �ͻ�������Ϣ��ѯ����
     * @param msg String
     * @return String
     */
public Object  ClientInfoQuery(ASObject obj) {

   ArrayList retlist=new ArrayList();
       String code = (String)obj.get("stationCode");
       String stationName=(String)obj.get("stationName");
       String user_id=(String)obj.get("userid");
       
       Security security = new Security();
       String priority="1";
       long msgPrio = 0;
                 if (user_id != null) {
                     try {
                         msgPrio = security.getMessagePriority(user_id, 3, 0, 0);
                         priority = new Long(msgPrio).toString();
                     } catch (Exception ex1) {
                     }
              }


       try {
      
    	   Collection coll=StreamAPI.msgStreamRealtimeClientQueryCmd(code, "", "Radio", priority);
          
              Iterator it=coll.iterator();
              while(it.hasNext())
              {
                MsgStreamRealtimeClientQueryRes.StreamRealtime stream=(MsgStreamRealtimeClientQueryRes.StreamRealtime) it.next();
                /**
                 *  this.equCode = equCode;
                      this.url = url;
                      this.freq = freq;
                      this.bps = bps;
                      this.Type=type;
                      this.clientInfo

                 */
                //�����������Ҫ���,�����������������.
               

                      Collection clent=stream.getClientInfo();
                      Iterator cl=clent.iterator();
                              while(cl.hasNext())
                              {
                              	 ClientInfo  info=new ClientInfo();
                              	 info.setHeadname(stationName);
                              	 info.setBps(stream.getBps());
                              	 info.setCode(code);
                              	 info.setEquCode(stream.getEquCode());
                              	 info.setFreq(stream.getFreq());
                              	 info.setUrl(stream.getURL());
                              	 info.setUser_id(user_id);
                              	  MsgStreamRealtimeClientQueryRes.ClientInfo clients=(MsgStreamRealtimeClientQueryRes.ClientInfo)cl.next();
                                  
                              	 info.setIp(clients.getIP() );

                                 retlist.add(info);
                              }
              }

          } catch (Exception ex) {
                  //��־����
        try {
          String description = "�ͻ������Ӳ�ѯʧ�ܣ�" + ex.getMessage() + ",վ�����" + code + "";

          Operation_log.writeOperLog(user_id, "200301", description);
          
        }
        
        catch (Exception ex1) {
          ex1.printStackTrace();
          return new EXEException("","��¼������־����"+ ex.getMessage(),null);
        }
               
    return new EXEException("", ex.getMessage(),null);
   }
   //��־����
   try {
       String description = "�ͻ������Ӳ�ѯ�ɹ�,վ�����" + code + "";
       Operation_log.writeOperLog(user_id, "200301", description);
   } catch (Exception ex1) {
       ex1.printStackTrace();
       return new EXEException("","��¼������־����"+ ex1.getMessage(),null);
   }
          return retlist;


}
/**
* �ͻ�������Ϣ�ж�����
* @param headCode
* @return
*/
public Object  StreamRealtimeClientStop(ClientInfo info) {

  String result="";

  String code = info.getCode();
  String equCode =info.getEquCode();
  String ip =info.getIp();
  String user_id=info.getUser_id();

  String priority="";


    Security security = new Security();
         long msgPrio = 0;
                   if (user_id != null) {
                       try {
                           msgPrio = security.getMessagePriority(user_id, 3, 0, 0);
                           priority = new Long(msgPrio).toString();
                       } catch (Exception ex1) {
                       }
              }

  try {
	  MsgStreamRealtimeClientStopCmd.ClientInfo clientinfo=new MsgStreamRealtimeClientStopCmd.ClientInfo(equCode,ip,"");
      Collection clients=new ArrayList();
      clients.add(clientinfo);
    StreamAPI.msgStreamRealtimeClientStopCmd(code, clients, priority);

     } catch (Exception ex) {
             //��־����
        try {
            String description = "�ͻ��������ж�ʧ��,վ�����" + code + ",�ж�ip:"+ip;

            Operation_log.writeOperLog(user_id, "200302", description);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            return new EXEException("","��¼������־����"+ ex1.getMessage(),null);
        }
        return new EXEException("", ex.getMessage(),null);

     }
     //��־����
 try {
     String description = "�ͻ��������жϳɹ�,վ�����" + code + ",�ж�ip:"+ip;

     Operation_log.writeOperLog(user_id, "200302", description);
 } catch (Exception ex1) {
     ex1.printStackTrace();
     return new EXEException("","��¼������־����"+ ex1.getMessage(),null);
 }
 return "�жϳɹ���";


}
}
