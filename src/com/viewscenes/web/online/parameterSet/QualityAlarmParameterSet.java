package com.viewscenes.web.online.parameterSet;

import org.jdom.Document;
import org.jdom.Element;
import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.DAOOperator;
import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.device.api.QualityAPI;
import com.viewscenes.device.radio.MsgQualityAlarmParamSetCmd;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class QualityAlarmParameterSet {

	public QualityAlarmParameterSet() {
		// TODO Auto-generated constructor stub
	}
	
	public Object qualityAlarmSet(ASObject obj){
		String result="";
		String user_id = (String) obj.get("user_id");  //�û�id
		String priority = (String) obj.get("priority"); //�û����ȼ�
		String head_id = (String) obj.get("head_id"); //վ��id
		String code = (String) obj.get("code");     //վ�����
		String type_id = (String) obj.get("type_id");
		String equCode = (String) obj.get("equCode"); //���ջ�code
		String band = (String) obj.get("band");  //����
		String freq = (String) obj.get("freq");  //Ƶ��
		String DownThreshold1Level = (String) obj.get("DownThreshold1Level");//��ƽ����
		String AbnormityLength1Level = (String) obj.get("AbnormityLength1Level"); //��ƽ����ʱ��
		//���ƶȱ�������
		String SampleLength2FM = (String) obj.get("SampleLength2FM");//���ƶ�ͳ�Ʊ����õ�λʱ��;
		String DownThreshold2FM = (String) obj.get("DownThreshold2FM");//������Ƶ�Ƶ�����޾���ֵ
		String UpThreshold2FM = (String) obj.get("UpThreshold2FM");//�����Ƶ�Ƶ�����޾���ֵ
		String UpAbnormityRate2FM = (String) obj.get("UpAbnormityRate2FM");//�����ưٷֱ�
		String DownAbnormityRate2FM = (String) obj.get("DownAbnormityRate2FM");//������ưٷֱ�
		String AbnormityLength2FM = (String) obj.get("AbnormityLength2FM");//����ʱ��
		//������ָ�걨������
		String SampleLength3AM = (String) obj.get("SampleLength3AM");//������ͳ�Ʊ����õ�λʱ��
		String DownThreshold3AM = (String) obj.get("DownThreshold3AM");//������ͳ�Ʋ�������Ȱٷֱ�
		String UpThreshold3AM = (String) obj.get("UpThreshold3AM");//�жϹ����������ްٷֱ�
		String UpAbnormityRate3AM = (String) obj.get("UpAbnormityRate3AM");//ͳ�ƹ������Ȱٷֱ�
		String DownAbnormityRate3AM = (String) obj.get("DownAbnormityRate3AM");//ͳ�Ʋ�������Ȱٷֱ�
		String AbnormityLength3AM = (String) obj.get("AbnormityLength3AM");//����ʱ��
		//˥��״̬����
		String Attenuation4Attenuation =  (String) obj.get("Attenuation4Attenuation");//˥��״̬
		try{
			 Long msgPrio=1L;
		     Security security = new Security();
		     msgPrio = security.getMessagePriority(user_id, 0, 0, 0);
		     priority = new Long(msgPrio).toString();
		     
			 MsgQualityAlarmParamSetCmd.FmParam fmParam = new MsgQualityAlarmParamSetCmd.FmParam();
			 fmParam.setFm_abnormityLength(AbnormityLength2FM);
			 fmParam.setFm_downAbnormityRate(DownAbnormityRate2FM);
			 fmParam.setFm_downThreshold(DownThreshold2FM);
			 fmParam.setFm_sampleLength(SampleLength2FM);
			 fmParam.setFm_upAbnormityRate(UpAbnormityRate2FM);
			 fmParam.setFm_upThreshold(UpThreshold2FM);
			 MsgQualityAlarmParamSetCmd.AmParam amParam = new MsgQualityAlarmParamSetCmd.AmParam();
			 amParam.setAbnormityLength(AbnormityLength3AM);
			 amParam.setDownAbnormityRate(DownAbnormityRate3AM);
			 amParam.setDownThreshold(DownThreshold3AM);
			 amParam.setSampleLength(SampleLength3AM);
			 amParam.setUpAbnormityRate(UpAbnormityRate3AM);
			 amParam.setUpThreshold(UpThreshold3AM);
			 QualityAPI.msgQualityAlarmParamSetCmd(code, equCode, freq, band, Attenuation4Attenuation, fmParam, amParam, DownThreshold1Level, AbnormityLength1Level, priority);
			
			 //�������õĲ������
			 if(equCode.equalsIgnoreCase("")){
					equCode="All";
				}
				if(band.equalsIgnoreCase("")){
					band="9";
				}
				if(freq.equalsIgnoreCase("")){
					freq="0";
				}
				String delSql =
			           "delete from radio_quality_alarm_param_tab where head_id=" +
			           head_id +" ";   
			    if(!equCode.equalsIgnoreCase("All")){
			    	delSql = delSql+" and equ_code='"+equCode+"'";
			    }
			    if(!band.equalsIgnoreCase("9")){
			    	delSql = delSql+" and band='"+band+"'";
			    }
			    if(!freq.equalsIgnoreCase("0")){
			    	delSql = delSql+" and frequency='"+freq+"'";
			    }
				
				 DbComponent.exeUpdate(delSql);
				
					StringBuffer insertsql = new StringBuffer("insert into radio_quality_alarm_param_tab");
					insertsql.append("(param_id,head_id,equ_code,band,");
					insertsql.append("frequency,DownThreshold1Level,AbnormityLength1Level,");
					insertsql.append("SampleLength2FM,DownThreshold2FM,UpThreshold2FM,UpAbnormityRate2FM,");
					insertsql.append("DownAbnormityRate2FM,AbnormityLength2FM,SampleLength3AM,DownThreshold3AM,");
					insertsql.append("UpThreshold3AM,UpAbnormityRate3AM,DownAbnormityRate3AM,AbnormityLength3AM,Attenuation4Attenuation,head_type_id)");
					insertsql.append(" values(RADIO_ALARM_SEQ.nextval,'"+head_id+"','"+equCode+"','"+band+"',");
					insertsql.append("'"+freq+"','"+DownThreshold1Level+"','"+AbnormityLength1Level+"',");
					insertsql.append("'"+SampleLength2FM+"','"+DownThreshold2FM+"','"+UpThreshold2FM+"','"+UpAbnormityRate2FM+"',");
					insertsql.append("'"+DownAbnormityRate2FM+"','"+AbnormityLength2FM+"','"+SampleLength3AM+"','"+DownThreshold3AM+"',");
					insertsql.append("'"+UpThreshold3AM+"','"+UpAbnormityRate3AM+"','"+DownAbnormityRate3AM+"','"+AbnormityLength3AM+"','"+Attenuation4Attenuation+"','"+type_id+"')");
                    DbComponent.exeUpdate(insertsql.toString());
                    result="ָ�걨���������óɹ�";  
		}catch(Exception e){
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
		return result;
	}
	
	public Object getParamByHeadID(ASObject obj){
		ASObject robj = new ASObject();
		String head_id = (String) obj.get("head_id");
		String type_id = (String) obj.get("type_id");
		String equCode = (String) obj.get("equCode");
		String band = (String) obj.get("band");
		String freq = (String) obj.get("freq");
		try{
			String sql = "select * from radio_quality_alarm_param_tab where 1=1 and head_id='"+head_id+"'";
	    	if(!equCode.equalsIgnoreCase("")){
	    		sql = sql+" and equ_code='"+equCode+"'";
	    	}else{
	    		sql = sql+" and equ_code='All'";
	    	}
	    	if(!band.equalsIgnoreCase("")){
	    		sql = sql+" and band='"+band+"'";
	    	}else{
	    		sql = sql+" and band='9'";
	    	}
	    	if(!freq.equalsIgnoreCase("")){
	    		sql = sql+" and frequency='"+freq+"'";
	    	}else{
	    		sql = sql+" and frequency='0'";
	    	}
	    	GDSet gd= DbComponent.Query(sql);
	    	String sql1="select * from radio_quality_alarm_param_tab where 1=1 and head_id='"+head_id+"'";
	        if( gd.getRowCount()<=0){
	         	if(equCode.equalsIgnoreCase("")&&band.equalsIgnoreCase("")&&freq.equalsIgnoreCase("")){
	         		sql1="select * from radio_quality_alarm_param_tab where  head_type_id='"+type_id+"'";
	         	}else{
	         		if(equCode.equalsIgnoreCase("")){
	         			if(!band.equalsIgnoreCase("")&&freq.equalsIgnoreCase("")){
	         				sql1=sql1+" and equ_code='All' and band='9' and frequency=0 ";
	         			}
	         			if(!band.equalsIgnoreCase("")&&!freq.equalsIgnoreCase("")){
	         				sql1=sql1+" and equ_code='All' and band='"+band+"' and frequency=0 ";
	         			}
	         			if(band.equalsIgnoreCase("")&&freq.equalsIgnoreCase("")){
	         				sql1=sql1+" and equ_code='All' and band='9' and frequency=0 ";
	         			}
	         		}
	         		else{
	         			if(!band.equalsIgnoreCase("")&&freq.equalsIgnoreCase("")){
	         				sql1=sql1+" and equ_code='"+equCode+"' and band='9' and frequency=0 ";
	         			}
	         			if(!band.equalsIgnoreCase("")&&!freq.equalsIgnoreCase("")){
	         				sql1=sql1+" and equ_code='"+equCode+"' and band='"+band+"' and frequency=0 ";
	         			}
	         			if(band.equalsIgnoreCase("")&&freq.equalsIgnoreCase("")){
	         				sql1=sql1+" and equ_code='"+equCode+"' and band='9' and frequency=0 ";
	         			}
	         		}
	         	}
	         	gd= DbComponent.Query(sql1);
	         }
	        if(gd.getRowCount()>0){
	        	for(int i =0;i<gd.getRowCount();i++){
	        		robj.put("param_id", gd.getString(i, "param_id"));
	        		robj.put("band", gd.getString(i, "band"));
	        		robj.put("frequency", gd.getString(i, "frequency"));
	        		//��ƽ����
	        		robj.put("downthreshold1level", gd.getString(i, "downthreshold1level"));
	        		robj.put("abnormitylength1level", gd.getString(i, "abnormitylength1level"));
	        		//���ƶȲ���
	        		robj.put("samplelength2fm", gd.getString(i, "samplelength2fm"));
	        		robj.put("downthreshold2fm", gd.getString(i, "downthreshold2fm"));
	        		robj.put("upthreshold2fm", gd.getString(i, "upthreshold2fm"));
	        		robj.put("downabnormityrate2fm", gd.getString(i, "downabnormityrate2fm"));
	        		robj.put("upabnormityrate2fm", gd.getString(i, "upabnormityrate2fm"));
	        		robj.put("abnormitylength2fm", gd.getString(i, "abnormitylength2fm"));
	        		//�����Ȳ���
	        		robj.put("samplelength3am", gd.getString(i, "samplelength3am"));
	        		robj.put("downthreshold3am", gd.getString(i, "downthreshold3am"));
	        		robj.put("upthreshold3am", gd.getString(i, "upthreshold3am"));
	        		robj.put("upabnormityrate3am", gd.getString(i, "upabnormityrate3am"));
	        		robj.put("downabnormityrate3am", gd.getString(i, "downabnormityrate3am"));
	        		robj.put("abnormitylength3am", gd.getString(i, "downabnormityrate3am"));
	        		//˥��״̬
	        		robj.put("attenuation4attenuation", gd.getString(i, "attenuation4attenuation"));
	        		
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
		
		return robj;
	}

}
