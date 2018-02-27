package com.viewscenes.web.online.DeviceControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import org.jdom.Element;
import org.jdom.Attribute;
import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.device.api.ProgramCommandAPI;
import com.viewscenes.device.api.StreamAPI;
import com.viewscenes.device.api.UpgradeEquipmentAPI;
import com.viewscenes.device.exception.DeviceException;
import com.viewscenes.device.radio.MsgProgramCommandRes;

import com.viewscenes.sys.Operation_log;
import com.viewscenes.sys.Security;

import flex.messaging.io.amf.ASObject;


/**
 * *************************************   
*    
* ��Ŀ���ƣ�HW   
* �����ƣ�EquipmentControl   
* ��������   �豸������
* �����ˣ�����
* ����ʱ�䣺Aug 8, 2012 4:55:53 PM   
* �޸��ˣ�����
* �޸�ʱ�䣺Aug 8, 2012 4:55:53 PM   
* �޸ı�ע��   
* @version    
*    
***************************************
 */
public class DeviceControl {
    public DeviceControl() {
    }
/**
 * ************************************************

* @Title: reStartEquipment

* @Description: TODO(��������)

* @param @param obj
* @param @return    �趨�ļ�

* @author  ����

* @return Object    ��������

* @throws

************************************************
 */
    public Object reStartEquipment(ASObject obj) {
    	String responseStr;
        ArrayList list = new ArrayList();

        String code = (String)obj.get("stationCode");
        String stationName=(String)obj.get("stationName");
        String user_id=(String)obj.get("userid");

        String priority = "";

       long msgPrio = 0;
                Security security = new Security();
                if (user_id != null) {
                    try {
                        msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
                       priority = new Long(msgPrio).toString();
                    } catch (Exception ex1) {

                    }
             }
		  try{
		                ProgramCommandAPI.reStartEquipment(code, priority);
		        /**
		         * �����豸�ӿ�
		         */
		         }
			catch (Exception ex) {
				
				// ��־����
                                try {
                                    String description = "�豸����ʧ�ܣ�վ��" + stationName + "";
                                    Operation_log.writeOperLog(user_id, "200802", description);
                                } catch (Exception ex1) {
                                    ex1.printStackTrace();
                                    
                                    return  "��¼������־������վ��" + stationName + ",ԭ��"+ex.getMessage();
                                }
                                return  "�豸����ʧ�ܣ�վ��" + stationName + ",ԭ��"+ex.getMessage();
			}
			 
              try {
            	  String description = "�豸����ʧ�ܣ�վ��" + stationName + "";
				Operation_log.writeOperLog(user_id, "200802", description);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 return  "��¼������־������վ��" + stationName + ",ԭ��"+e.getMessage();
                 
                    
			}
		return "�·���������ɹ���վ�����"+code;
    }

 /**
  * ************************************************
 
 * @Title: setTime
 
 * @Description: TODO(�������ʱ)
 
 * @param @param obj
 * @param @return    �趨�ļ�
 
 * @author  ����
 
 * @return Object    ��������
 
 * @throws
 
 ************************************************
  */
    public Object setTime(ASObject obj) {
    	

        String code = (String)obj.get("stationCode");
        String stationName=(String)obj.get("stationName");
        String user_id=(String)obj.get("userid");

        String priority = "";

 

     long msgPrio = 0;
              Security security = new Security();
              if (user_id != null) {
                  try {
                      msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
                     priority = new Long(msgPrio).toString();
                  } catch (Exception ex1) {

                  }
        }

  
  
        /**
         * �����豸�ӿ�
         */

        try {
            // ���÷�����Ϣ����
        	ProgramCommandAPI.setEquipmentTime(code, priority);
            }

			catch (DeviceException ex) {
			
				// ��־����
				String description1 = "�������ʱʧ�ܣ�վ��" + stationName + ":"+ex.getMessage() ;
                         try {
                                   Operation_log.writeOperLog(user_id, "200804", description1);
                               } catch (Exception ex1) {
                                   ex1.printStackTrace();
                                   return  "��¼������־������վ��" + stationName + ",ԭ��"+ex1.getMessage();
                               }

                               return "վ��"+stationName+"�������ʱʧ�ܣ�ԭ��"+ex.getMessage();
			}
	


                // ��־����
                        String description1 = "�������ʱ�ɹ���վ��" + stationName  ;
                 try {
                           Operation_log.writeOperLog(user_id, "200804", description1);
                       } catch (Exception ex1) {
                           ex1.printStackTrace();
                           return  "��¼������־������վ��" + stationName + ",ԭ��"+ex1.getMessage();
                           
                       }

		return "վ��"+stationName+"�������ʱ�ɹ�";
    }
/**
 * ************************************************

* @Title: queryEquipmentVersion

* @Description: TODO(�汾��ѯ)

* @param @param obj
* @param @return    �趨�ļ�

* @author  ����

* @return Object    ��������

* @throws

************************************************
 */
    public Object queryEquipmentVersion(ASObject obj) {
    	String responseStr="";
    	   String code = (String)obj.get("stationCode");
           String stationName=(String)obj.get("stationName");
           String user_id=(String)obj.get("userid");
        String priority = "";


      long msgPrio = 0;
            Security security = new Security();
            if (user_id != null) {
                try {
                    msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
                   priority = new Long(msgPrio).toString();
                } catch (Exception ex1) {

                }
        }


        /**
         * �����豸�ӿ�
         */

        try {
            // ���÷�����Ϣ����
        	Collection resutList =ProgramCommandAPI.getEquipmentVersion(code, priority);
        	// �������ؽ��
    		String company = "";
    	    String name = "";
    	    String version = "";
    	    String aliveValue = "";
    	    Iterator iter = resutList.iterator();
    	    int num = resutList.size();
    	    while (iter.hasNext()) {
    	    	if(num==1){
    	    		MsgProgramCommandRes.ProgramInfo programInfo = (MsgProgramCommandRes.ProgramInfo)iter.next();
    	    		company = programInfo.getCompany();
    	    		name = programInfo.getName();
    	    		version = programInfo.getVersion();
    	    	}
    	    	else if(num==2){
    	    		MsgProgramCommandRes.AliveInfo aliveInfo = (MsgProgramCommandRes.AliveInfo)iter.next();
    	    		aliveValue = aliveInfo.getValue();
    	    	}
    	    }
    		Element ele = new Element("info");
    		ele.addAttribute(new Attribute("company", company));
    		ele.addAttribute(new Attribute("name", name));
    		ele.addAttribute(new Attribute("version", version));
    		ele.addAttribute(new Attribute("aliveValue", aliveValue));
    		responseStr+=company+name+version+aliveValue;
    	
            }

			catch (DeviceException ex) {
		
				// ��־����
				String description1 = "����汾��ѯʧ�ܣ�վ��" + stationName + ex.getMessage();
                                           try {
                                                          Operation_log.writeOperLog(user_id, "200803", description1);
                                                      } catch (Exception ex1) {
                                                    	  return  "��¼������־������վ��" + stationName + ",ԭ��"+ex1.getMessage();            
                                }
                          return "����汾��ѯʧ�ܣ�վ��" + stationName + ex.getMessage();
			}
		

                // ��־����
                                String description1 = "����汾��ѯ�ɹ���վ��" + stationName  ;
                                           try {
                                                          Operation_log.writeOperLog(user_id, "200803", description1);
                                                } catch (Exception ex1) {
                                                          ex1.printStackTrace();
                                                          return  "��¼������־������վ��" + stationName + ",ԭ��"+ex1.getMessage();
                                                          
                                                }

		
		return responseStr;
    }
  /**
   * ************************************************
  
  * @Title: aliveQuery
  
  * @Description: TODO(������)
  
  * @param @param obj
  * @param @return    �趨�ļ�
  
  * @author  ����
  
  * @return Object    ��������
  
  * @throws
  
  ************************************************
   */
   public Object aliveQuery(ASObject obj) {
       String responseStr="";
	   String code = (String)obj.get("stationCode");
       String stationName=(String)obj.get("stationName");
       String user_id=(String)obj.get("userid");

       String priority = "";


       long msgPrio = 0;
                Security security = new Security();
                if (user_id != null) {
                    try {
                        msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
                       priority = new Long(msgPrio).toString();
                    } catch (Exception ex1) {

                    }
        }

       /**
        * �����豸�ӿ�
        */
                try{
                // �������ؽ��
                Collection resutList = ProgramCommandAPI.queryAlive(code, priority);
           

           String aliveValue = "";
           Iterator iter = resutList.iterator();
           while (iter.hasNext()) {
                       MsgProgramCommandRes.AliveInfo aliveInfo = (MsgProgramCommandRes.AliveInfo)iter.next();
                       aliveValue = aliveInfo.getValue();
                   	if(aliveValue.equals("0")){
                   		responseStr = "���״̬:����������";
					}else if(aliveValue.equals("1")){
						responseStr = "���״̬:�������޷��ղ�";
					}
					

           }
            
         
           }catch(DeviceException ex) {
              
                 // ��־����
                 String description1 = "���������������ѯʧ�ܣ�վ��" + stationName + ex.getMessage() ;
                                try {
                                    Operation_log.writeOperLog(user_id, "200805", description1);
                                } catch (Exception ex1) {
                                    ex1.printStackTrace();
                                    return  "��¼������־������վ��" + stationName + ",ԭ��"+ex1.getMessage();
                                    
                                    
                                }
                  return  "���������������ѯʧ�ܣ�վ��" + stationName + ex.getMessage() ;
               
         }
         // ��־����
                String description1 = "���������������ѯ�ɹ���վ��" + stationName  ;
                               try {
                                   Operation_log.writeOperLog(user_id, "200805", description1);
                               } catch (Exception ex1) {
                                   ex1.printStackTrace();//36699551
                                   return  "��¼������־������վ��" + stationName + ",ԭ��"+ex1.getMessage();
                                   
                                   
                                }
               return responseStr;
   }


   /**
    * ************************************************
   
   * @Title: UpgradeEquipment
   
   * @Description: TODO(վ������)
   
   * @param @param obj
   * @param @return    �趨�ļ�
   
   * @author  ����
   
   * @return Object    ��������
   
   * @throws
   
   ************************************************
    */
    public Object UpgradeEquipment(ASObject obj) {
        String responseStr="";
 	   String code = (String)obj.get("stationCode");
        String stationName=(String)obj.get("stationName");
        String user_id=(String)obj.get("userid");
        String url=(String)obj.get("url");
        String username=(String)obj.get("username");
        String password=(String)obj.get("password");

        String priority = "";


        long msgPrio = 0;
                 Security security = new Security();
                 if (user_id != null) {
                     try {
                         msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
                        priority = new Long(msgPrio).toString();
                     } catch (Exception ex1) {

                     }
         }

        /**
         * �����豸�ӿ�
         */
                 try{
                 // �������ؽ��
                  UpgradeEquipmentAPI.msgQualityAlarmParamSetCmd(code, url, username, password, priority);

               }catch(DeviceException ex) {
               
     
                   return  "վ������ʧ�ܣ�վ��" + stationName + ex.getMessage() ;
                
          }
        
                return "վ�������ɹ���";
    }
   
    

    

   

}
