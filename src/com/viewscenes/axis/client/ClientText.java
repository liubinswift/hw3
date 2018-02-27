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
					"<!-- �ϱ���Ϣ-->"+
					"<MessageReport>"+
					"	<!-- �ϱ�ʱ��-->"+
					"	<ReportTime>2012-8-8 08:08:08</ReportTime>"+
					"	<!-- �ϱ�����-->"+
					"	<ReportDescription>����1 ͨ�� ���ź�</ReportDescription>"+
					"</MessageReport>";
        
       String res =  (String)cInterface.getInfo("reportMessage", param);
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ����axis������ṩ����
     * @param method ������
     * @param param �������� Object ����
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
