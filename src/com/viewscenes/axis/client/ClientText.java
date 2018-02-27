package com.viewscenes.axis.client;

import java.net.URL;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class ClientText {

    private String targetEendPoint = "http://192.168.1.151:8080/hw/services/MessageReportWS";
    Call call = null;
    
	public ClientText() throws Exception{
        Service service = new Service();
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(new URL(targetEendPoint));
    }
    public static void main(String[] args) {
    	
    	try{
    	ClientText cInterface = new ClientText();
        Object[] param = new Object[]{};
        
        param = new Object[1];
        param[0] ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
					"<!-- 上报信息-->"+
					"<MessageReport>"+
					"	<!-- 上报时间-->"+
					"	<ReportTime>2012-8-8 08:08:08</ReportTime>"+
					"	<!-- 上报内容-->"+
					"	<ReportDescription>国际1 通道 无信号</ReportDescription>"+
					"</MessageReport>";
        
       String res =  (String)cInterface.getInfo("reportMessage", param);
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 调用axis服务端提供方法
     * @param method 方法名
     * @param param 方法参数 Object 数组
     * @return
     * @throws Exception
     */
    public Object getInfo(String method,Object[] param) throws Exception{
        call.setOperationName(method);
        Object result = call.invoke(param);
        System.out.println(result);
            
        return result;
    }
}
