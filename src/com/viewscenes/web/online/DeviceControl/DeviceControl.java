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
* 项目名称：HW   
* 类名称：EquipmentControl   
* 类描述：   设备控制类
* 创建人：刘斌
* 创建时间：Aug 8, 2012 4:55:53 PM   
* 修改人：刘斌
* 修改时间：Aug 8, 2012 4:55:53 PM   
* 修改备注：   
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

* @Description: TODO(重启机器)

* @param @param obj
* @param @return    设定文件

* @author  刘斌

* @return Object    返回类型

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
		         * 调用设备接口
		         */
		         }
			catch (Exception ex) {
				
				// 日志操作
                                try {
                                    String description = "设备重启失败，站点" + stationName + "";
                                    Operation_log.writeOperLog(user_id, "200802", description);
                                } catch (Exception ex1) {
                                    ex1.printStackTrace();
                                    
                                    return  "记录操作日志出错：，站点" + stationName + ",原因："+ex.getMessage();
                                }
                                return  "设备重启失败，站点" + stationName + ",原因："+ex.getMessage();
			}
			 
              try {
            	  String description = "设备重启失败，站点" + stationName + "";
				Operation_log.writeOperLog(user_id, "200802", description);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 return  "记录操作日志出错：，站点" + stationName + ",原因："+e.getMessage();
                 
                    
			}
		return "下发重启命令成功，站点代码"+code;
    }

 /**
  * ************************************************
 
 * @Title: setTime
 
 * @Description: TODO(计算机授时)
 
 * @param @param obj
 * @param @return    设定文件
 
 * @author  刘斌
 
 * @return Object    返回类型
 
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
         * 调用设备接口
         */

        try {
            // 设置发送消息数据
        	ProgramCommandAPI.setEquipmentTime(code, priority);
            }

			catch (DeviceException ex) {
			
				// 日志操作
				String description1 = "计算机授时失败，站点" + stationName + ":"+ex.getMessage() ;
                         try {
                                   Operation_log.writeOperLog(user_id, "200804", description1);
                               } catch (Exception ex1) {
                                   ex1.printStackTrace();
                                   return  "记录操作日志出错：，站点" + stationName + ",原因："+ex1.getMessage();
                               }

                               return "站点"+stationName+"计算机授时失败，原因："+ex.getMessage();
			}
	


                // 日志操作
                        String description1 = "计算机授时成功，站点" + stationName  ;
                 try {
                           Operation_log.writeOperLog(user_id, "200804", description1);
                       } catch (Exception ex1) {
                           ex1.printStackTrace();
                           return  "记录操作日志出错：，站点" + stationName + ",原因："+ex1.getMessage();
                           
                       }

		return "站点"+stationName+"计算机授时成功";
    }
/**
 * ************************************************

* @Title: queryEquipmentVersion

* @Description: TODO(版本查询)

* @param @param obj
* @param @return    设定文件

* @author  刘斌

* @return Object    返回类型

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
         * 调用设备接口
         */

        try {
            // 设置发送消息数据
        	Collection resutList =ProgramCommandAPI.getEquipmentVersion(code, priority);
        	// 解析返回结果
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
		
				// 日志操作
				String description1 = "程序版本查询失败，站点" + stationName + ex.getMessage();
                                           try {
                                                          Operation_log.writeOperLog(user_id, "200803", description1);
                                                      } catch (Exception ex1) {
                                                    	  return  "记录操作日志出错：，站点" + stationName + ",原因："+ex1.getMessage();            
                                }
                          return "程序版本查询失败，站点" + stationName + ex.getMessage();
			}
		

                // 日志操作
                                String description1 = "程序版本查询成功，站点" + stationName  ;
                                           try {
                                                          Operation_log.writeOperLog(user_id, "200803", description1);
                                                } catch (Exception ex1) {
                                                          ex1.printStackTrace();
                                                          return  "记录操作日志出错：，站点" + stationName + ",原因："+ex1.getMessage();
                                                          
                                                }

		
		return responseStr;
    }
  /**
   * ************************************************
  
  * @Title: aliveQuery
  
  * @Description: TODO(软件存活)
  
  * @param @param obj
  * @param @return    设定文件
  
  * @author  刘斌
  
  * @return Object    返回类型
  
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
        * 调用设备接口
        */
                try{
                // 解析返回结果
                Collection resutList = ProgramCommandAPI.queryAlive(code, priority);
           

           String aliveValue = "";
           Iterator iter = resutList.iterator();
           while (iter.hasNext()) {
                       MsgProgramCommandRes.AliveInfo aliveInfo = (MsgProgramCommandRes.AliveInfo)iter.next();
                       aliveValue = aliveInfo.getValue();
                   	if(aliveValue.equals("0")){
                   		responseStr = "软件状态:监测软件正常";
					}else if(aliveValue.equals("1")){
						responseStr = "软件状态:监测软件无法收测";
					}
					

           }
            
         
           }catch(DeviceException ex) {
              
                 // 日志操作
                 String description1 = "计算机监测软件存活查询失败，站点" + stationName + ex.getMessage() ;
                                try {
                                    Operation_log.writeOperLog(user_id, "200805", description1);
                                } catch (Exception ex1) {
                                    ex1.printStackTrace();
                                    return  "记录操作日志出错：，站点" + stationName + ",原因："+ex1.getMessage();
                                    
                                    
                                }
                  return  "计算机监测软件存活查询失败，站点" + stationName + ex.getMessage() ;
               
         }
         // 日志操作
                String description1 = "计算机监测软件存活查询成功，站点" + stationName  ;
                               try {
                                   Operation_log.writeOperLog(user_id, "200805", description1);
                               } catch (Exception ex1) {
                                   ex1.printStackTrace();//36699551
                                   return  "记录操作日志出错：，站点" + stationName + ",原因："+ex1.getMessage();
                                   
                                   
                                }
               return responseStr;
   }


   /**
    * ************************************************
   
   * @Title: UpgradeEquipment
   
   * @Description: TODO(站点升级)
   
   * @param @param obj
   * @param @return    设定文件
   
   * @author  刘斌
   
   * @return Object    返回类型
   
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
         * 调用设备接口
         */
                 try{
                 // 解析返回结果
                  UpgradeEquipmentAPI.msgQualityAlarmParamSetCmd(code, url, username, password, priority);

               }catch(DeviceException ex) {
               
     
                   return  "站点升级失败，站点" + stationName + ex.getMessage() ;
                
          }
        
                return "站点升级成功！";
    }
   
    

    

   

}
