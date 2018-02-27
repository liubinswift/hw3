/**
 * 
 */
package com.viewscenes.axis.service;

import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;


/**
 * @author Administrator
 *
 */
public class MessageReportServer {
	/**
	 * 报警上报信息入口
	 * @detail  
	 * @method  
	 * @param messageXml
	 * <?xml version="1.0" encoding="UTF-8"?>
		<!-- 上报信息-->
		<MessageReport>
			<!-- 上报时间-->
			<ReportTime>2012-8-8 08:08:08</ReportTime>
			<!-- 上报内容-->
			<ReportDescription>国际1 通道 无信号</ReportDescription>
		</MessageReport>
	 * @return
	 * @throws Exception 
	 * @return  String  
	 * @author  zhaoyahui
	 * @version 2012-8-27 下午05:03:11
	 */
    public static String reportMessage(String messageXml) {
    	  Element root = StringTool.getXMLRoot(messageXml);
    	  String reportTime = root.getChildText("ReportTime");
    	  String reportDescription = root.getChildText("ReportDescription");
    	  
    	  String sql = "insert into radio_message_report_tab(id,report_time,report_description) " +
    	  		"values(radio_alarm_seq.nextval,'"+reportTime+"','"+reportDescription+"')";
    	  try {
			DbComponent.exeUpdate(sql);
		} catch (DbException e) {
			LogTool.fatal(e);
			return "error:"+e.getMessage();
		}
    	  return "success:上报成功";
        
    }
    
   
}
