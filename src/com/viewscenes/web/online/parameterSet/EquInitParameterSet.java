package com.viewscenes.web.online.parameterSet;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.device.radio.MsgEquipmentInitParamSetCmd;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.Security;
import flex.messaging.io.amf.ASObject;
import com.viewscenes.device.api.EquipmentAPI;
/**
 * �豸��ʼ������������
 * @author leeo
 * @date 2012/07/26
 */
public class EquInitParameterSet {
	
	public Object EquipmentInitParamSetV8(ASObject obj){
		String result="";
		String head_id = (String) obj.get("head_id");  //վ��id
		String code = (String) obj.get("code");         //վ�����
		String type_id = (String) obj.get("type_id");   //վ������
		String PhoneMain = (String) obj.get("PhoneMain");   //վ����ѡ��������  ���е绰
		String LogNameMain = (String) obj.get("LogNameMain");   //�����û���
		String LogPasswordMain = (String) obj.get("LogPasswordMain");//��������
		String PhoneSlave = (String) obj.get("PhoneSlave"); //վ�㱸ע��������  ���е绰
		String LogNameSlave = (String) obj.get("LogNameSlave");  //վ�㱸ע��������  �����û���
		String LogPasswordSlave = (String) obj.get("LogPasswordSlave");//վ�㱸ע��������  ��������
		String nativelogpassword = (String) obj.get("nativelogpassword");//���в�������
		String BatteryLevelDownThreshold = (String) obj.get("BatteryLevelDownThreshold");//UPS��ص�ѹ����
		String TimeServer = (String) obj.get("TimeServer");  //ʱ�������
		String OffLineTime = (String) obj.get("OffLineTime"); //վ�����������Զ�����ʱ��
		String CompressSize = (String) obj.get("CompressSize");//��Ϣѹ��������ֵ
		String ShutdownDelayTime = (String) obj.get("ShutdownDelayTime");//UPS�ػ���ʱʱ��
		String CheckUPS = (String) obj.get("CheckUPS"); //�Ƿ����UPS״̬����ϵͳ���
		String LogExpireDays = (String) obj.get("LogExpireDays");//�豸��־/�������ݹ���ʱ��
		String StationStatusReportInterval = (String) obj.get("StationStatusReportInterval");//վ��״̬�Զ��ϱ���� (ע��2012.10.30�����Ľӿڽڵ�)
		ArrayList centerList = (ArrayList) obj.get("center");
		String R1 = (String) obj.get("R1");
		String R2 = (String) obj.get("R2");
		String R1Name= (String) obj.get("R1Name");
		String R1Code = (String) obj.get("R1Code");
		String R2Name = (String) obj.get("R2Name");
		String R2Code = (String) obj.get("R2Code");
	//	String type = SiteVersionUtil.getSiteType(code);
		String priority = "";  //���ȼ�
		String user_id=(String) obj.get("user_id");
		Security security = new Security();
		Long msgPrio=1L;
		try {
	        msgPrio = security.getMessagePriority(user_id, 2, 0, 0);
	    } catch (Exception ex2) {
	           ex2.printStackTrace();
	    }
		priority = new Long(msgPrio).toString();
		
		Vector params = new Vector();
		Vector centerParams = new Vector();
		if (R1Code != null && R1Code.trim().length() > 0) {
		    Vector R1_value = String2Vector(R1);
		    MsgEquipmentInitParamSetCmd.EquipmentInitParam eip1 = new
		       MsgEquipmentInitParamSetCmd.EquipmentInitParam(R1Name,
		          R1Code, R1_value);
		    params.add(eip1);
		 }
		 if (R2Code != null && R2Code.trim().length() > 0) {
		    Vector R2_value = String2Vector(R2);
		    MsgEquipmentInitParamSetCmd.EquipmentInitParam eip2 = new
		         MsgEquipmentInitParamSetCmd.EquipmentInitParam(R2Name,
		          R2Code, R2_value);
		    params.add(eip2);
		 }
		 for(int i=0;i<centerList.size();i++)
		 {
			  ASObject centerObj =(ASObject) centerList.get(i);

		       Map map11 = new HashMap();
		       map11.put("UpURL",centerObj.get("UpURL") );
		       map11.put("FTP", centerObj.get("FTP"));
		       map11.put("FTPPort", centerObj.get("FTPPort"));
		       map11.put("FTPUser", centerObj.get("FTPUser"));
		       map11.put("FTPPassword", centerObj.get("FTPPassword"));
		       map11.put("FTPPath", centerObj.get("FTPPath"));
		       MsgEquipmentInitParamSetCmd.Center center11 = new
		          MsgEquipmentInitParamSetCmd.Center(centerObj.get("SrcCode").toString(),"2", map11);//ϵͳ����2����ֱ��̨ϵͳ
		       centerParams.add(center11);
		 }
		 Map map21 = new HashMap();
	     map21.put("Phone", PhoneMain);
	     map21.put("LogName", LogNameMain);
	     map21.put("LogPassword", LogPasswordMain);
	     MsgEquipmentInitParamSetCmd.Loginfo loginfo21 = new
	          MsgEquipmentInitParamSetCmd.Loginfo("Main", map21);
	     Map map22 = new HashMap();
	     map22.put("Phone", PhoneSlave);
	     map22.put("LogName", LogNameSlave);
	     map22.put("LogPassword", LogPasswordSlave);
	     MsgEquipmentInitParamSetCmd.Loginfo loginfo22 = new
	          MsgEquipmentInitParamSetCmd.Loginfo("Slave", map22);
	     Map map23 = new HashMap();
	     map23.put("LogPassword", nativelogpassword);
	     MsgEquipmentInitParamSetCmd.Loginfo loginfo23 = new
	           MsgEquipmentInitParamSetCmd.Loginfo("Native", map23);
	     
	     Vector loginfoParams = new Vector();
	    
	     if(PhoneMain.length()!=0){
	    	loginfoParams.add(loginfo21);  
	     }
	     if(PhoneSlave.length()!=0){
	    	loginfoParams.add(loginfo22);  
	     }
	     if(nativelogpassword.length()!=0){
	    	loginfoParams.add(loginfo23);  
	     }
	     Map systemParams = new HashMap();
	     systemParams.put("TimeServer", TimeServer);
	     systemParams.put("BatteryLevelDownThreshold",
	                        BatteryLevelDownThreshold);
	     systemParams.put("ShutdownDelayTime", ShutdownDelayTime);
	     systemParams.put("CheckUPS", CheckUPS);
	     systemParams.put("OffLineTime", OffLineTime);
	     systemParams.put("CompressSize", CompressSize);
	     systemParams.put("LogExpireDays", LogExpireDays);
	     systemParams.put("StationStatusReportInterval", StationStatusReportInterval);
	     try {
			EquipmentAPI.msgEquipmentInitParamSetCmd(code, params, centerParams, loginfoParams, systemParams, priority);
			ASObject aso = this.getParamByHeadID(head_id);
			try{
				ASObject centerObj =(ASObject) centerList.get(0);
				
				if(aso.get("param_id")!=null){//����й���ʼ����������Ҫ��ɾ��ԭ�е����ݣ�Ȼ����������⡣
					String delsql = "delete from radio_equ_init_param_tab where head_id='"+head_id+"' and head_code='"+code+"'";
					DbComponent.exeUpdate(delsql);
				}
					StringBuffer insertsbf = new StringBuffer("insert into radio_equ_init_param_tab");
					insertsbf.append("(param_id,head_id,head_code,type_id,srccode,upurl,ftp,");
					insertsbf.append("ftpport,ftpuser,ftppassword,ftppath,phonemain,lognamemain,");
					insertsbf.append("logpasswordmain,phoneslave,lognameslave,logpasswordslave,");
					insertsbf.append("nativelogpassword,timeserver,batteryleveldownthreshold,");
					insertsbf.append("shutdowndelaytime,checkups,offlinetime,spectrumscansamplelength,");
					insertsbf.append("r1,r2,r3,r4,r5,r6,r7,r8,r9,compresssize,logexpiredays,stationstatusreportinterval)");
					insertsbf.append("values(RADIO_ALARM_SEQ.nextval,'"+head_id+"','"+code+"','"+type_id+"',");
					insertsbf.append("'"+centerObj.get("SrcCode")+"','"+centerObj.get("UpURL")+"','"+centerObj.get("FTP")+"',");
					insertsbf.append("'"+centerObj.get("FTPPort")+"','"+centerObj.get("FTPUser")+"','"+centerObj.get("FTPPassword")+"',");
					insertsbf.append("'"+centerObj.get("FTPPath")+"','"+LogNameMain+"','"+LogPasswordMain+"','"+LogPasswordMain+"',");
					insertsbf.append("'"+PhoneSlave+"','"+LogNameSlave+"','"+LogPasswordSlave+"','"+nativelogpassword+"','"+TimeServer+"',");
					insertsbf.append("'"+BatteryLevelDownThreshold+"','"+ShutdownDelayTime+"','"+CheckUPS+"','"+OffLineTime+"','',");
					insertsbf.append("'"+R1+"','"+R2+"','','','','','','','','"+CompressSize+"','"+LogExpireDays+"','"+StationStatusReportInterval+"')");
					DbComponent.exeUpdate(insertsbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				return new EXEException("",e.getMessage()+"�豸��ʼ���������õ��������ʧ�ܣ����Ǹò����´��豸�ɹ�","");
			}
			
			result="�豸��ʼ�������ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("",e.getMessage()+"�·��豸ʧ��","");
		} 
		return result ;
	}
	/**
	 * ����ǰ��id��code��ѯ��վ���Ƿ��г�ʼ�����Ĳ���ֵ
	 * @param head_id
	 * @param code
	 * @return
	 */
	public ASObject getParamByHeadID(String head_id){
		ASObject obj = new ASObject();
		String sql="select * from radio_equ_init_param_tab where head_id='"+head_id+"'";
		try {
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				for(int i=0;i<gd.getRowCount();i++){
					obj.put("param_id", gd.getString(i, "param_id"));
					obj.put("srccode", gd.getString(i, "srccode"));
					obj.put("upurl", gd.getString(i, "upurl"));
					obj.put("ftp", gd.getString(i, "ftp"));
					obj.put("ftpport", gd.getString(i, "ftpport"));
					obj.put("ftpuser", gd.getString(i, "ftpuser"));
					obj.put("fptpassword", gd.getString(i, "ftppassword"));
					obj.put("ftppath", gd.getString(i, "ftppath"));
					obj.put("phonemain", gd.getString(i, "phonemain"));
					obj.put("lognamemain", gd.getString(i, "lognamemain"));
					obj.put("logpasswordmain", gd.getString(i, "logpasswordmain"));
					obj.put("phoneslave", gd.getString(i, "phoneslave"));
					obj.put("loagnameslave", gd.getString(i, "lognameslave"));
					obj.put("logpasswordslave", gd.getString(i, "logpasswordslave"));
					obj.put("nativelogpassword", gd.getString(i, "nativelogpassword"));
					obj.put("timeserver", gd.getString(i, "timeserver"));
					obj.put("batteryleveldownthreshold", gd.getString(i, "batteryleveldownthreshold"));
					obj.put("shutdowndelaytime", gd.getString(i, "shutdowndelaytime"));
					obj.put("checkups", gd.getString(i, "checkups"));
					obj.put("offlinetime", gd.getString(i, "offlinetime"));
					obj.put("R1", gd.getString(i, "r1"));
					obj.put("R2", gd.getString(i, "r2"));
					obj.put("R3", gd.getString(i, "r3"));
					obj.put("R4", gd.getString(i, "r4"));
					obj.put("R5", gd.getString(i, "r5"));
					obj.put("R6", gd.getString(i, "r6"));
					obj.put("R7", gd.getString(i, "r7"));
					obj.put("R8", gd.getString(i, "r8"));
					obj.put("R9", gd.getString(i, "r9"));
					obj.put("compresssize", gd.getString(i, "compresssize"));
					obj.put("logexpiredays", gd.getString(i, "logexpiredays"));
					obj.put("stationstatusreportinterval", gd.getString(i, "stationstatusreportinterval"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return obj;
	}
	
	 private static Vector String2Vector(String R)
	 {
	     Vector vec=new Vector();
	     StringTokenizer token=new StringTokenizer(R,",");
	     while(token.hasMoreTokens())
	     {
	         vec.add(token.nextToken());
	     }
	     return vec;
	 }

}
