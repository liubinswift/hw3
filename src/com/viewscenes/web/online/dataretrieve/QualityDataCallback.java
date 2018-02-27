package com.viewscenes.web.online.dataretrieve;
/**
 * ָ�����ݻ�����
 * @author leeo
 * @date 2012/08/08
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.QualityAPI;
import com.viewscenes.device.radio.MsgQualityHistoryQueryCmd;
import com.viewscenes.device.radio.MsgQualityHistoryQueryRes;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class QualityDataCallback {
	
	public Object qualityDataCallback(ASObject obj){
		String res="ָ�����ݻ��ճɹ�";
		String user_id = (String) obj.get("user_id");//�û�id
		String priority = (String) obj.get("priority");//���ȼ�
		String head_id = (String) obj.get("head_id");
		String code = (String) obj.get("code");// ǰ��code
		String receiverCode = (String) obj.get("receiverCode");//���ջ�code
		String startDate = (String) obj.get("startDate");//��ʼʱ��
		String endDate = (String) obj.get("endDate");// ����ʱ��
		String band = (String) obj.get("band"); //����
		String freq = (String) obj.get("freq"); //Ƶ��
		String taskId = (String) obj.get("taskId");//����id
		String sampleNumber = (String) obj.get("sampleNumber");// ��λʱ���ϱ�����
		String unit = (String) obj.get("unit");//��λʱ��
		String check1 = (String) obj.get("check1"); //�ز���ƽ
		String check3 = (String) obj.get("check3"); //������
		String check5 = (String) obj.get("check5"); //���ƶ�
		String check8 = (String) obj.get("check8");//����
		String frontier = "radio";
		Security security = new Security();
	      long msgPrio = 0;
	      if (user_id != null) {
	          try {
	              msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
	              priority = new Long(msgPrio).toString();
	          } catch (Exception ex1) {
	             ex1.printStackTrace();
	              res=ex1.getMessage();

	          }
	      }
		ArrayList qualityIndex = new ArrayList();
		try {
			if (!check1.equalsIgnoreCase("null") && !check1.equalsIgnoreCase("")) {

	            MsgQualityHistoryQueryCmd.QueryTypes q21 = new MsgQualityHistoryQueryCmd.QueryTypes(check1,"Level");
	            qualityIndex.add(q21);
	        }

	        if (!check3.equalsIgnoreCase("null") && !check3.equalsIgnoreCase("")) {

	            MsgQualityHistoryQueryCmd.QueryTypes q23 = new MsgQualityHistoryQueryCmd.QueryTypes(check3,"AM-Modulation");
	            qualityIndex.add(q23);
	        }

	        if (!check5.equalsIgnoreCase("null") && !check5.equalsIgnoreCase("")) {

	            MsgQualityHistoryQueryCmd.QueryTypes q25 = new MsgQualityHistoryQueryCmd.QueryTypes(check5,"FM-ModulationMax");
	            qualityIndex.add(q25);
	        }
	        if(check8!=null&&!check8.equalsIgnoreCase("")){
				 MsgQualityHistoryQueryCmd.QueryTypes q27 = new MsgQualityHistoryQueryCmd.QueryTypes(check8,"BandWidth");
	            qualityIndex.add(q27);
			}
	        MsgQualityHistoryQueryRes.Result result= QualityAPI.msgQualityHistoryQueryCmd(code, receiverCode, freq, band, taskId, startDate, endDate, sampleNumber, unit, qualityIndex, priority);
	        /**
	         * �ӿ��·��ɹ����豸�˻��յ�������Ҫ�������ݿ�
	         */
	        if(result!=null){
	        	//����������ɾ��ԭ�л��յ�����
	        	StringBuffer delsql =new StringBuffer("");
	        	delsql.append("delete from radio_quality_result_tab where check_datetime <= TO_DATE('"+endDate+"','YYYY-MM-DD HH24:MI:SS') ");
	        	delsql.append(" and check_datetime >= TO_DATE('"+startDate+"','YYYY-MM-DD HH24:MI:SS') ");
	        	if(freq!=null&&!freq.equalsIgnoreCase("")){
	        		delsql.append(" and frequency='"+freq+"'");
	        	}
	        	delsql.append(" and head_id = '"+head_id+"'");
	        	DbComponent.exeUpdate(delsql.toString());
	        	//�������ص�����
	        	Collection resu = result.getQuality();
	        	String equ_code = result.getEquCode();
	   	        taskId = result.getTaskID();
	        	if(resu!=null){
	                Iterator i = resu.iterator();
	                while(i.hasNext()){
	                	MsgQualityHistoryQueryRes.Quality quality = (MsgQualityHistoryQueryRes.Quality) i.next();
	 	                String check_datetime = quality.getCheckTime();
	 	                String frequency = quality.getFreq();
	 	                band = quality.getBand();
	 	                Collection qualityindexs = quality.getQualityIndexs();
	 	                Iterator q = qualityindexs.iterator();
	 	                while(q.hasNext()){
	 	                      MsgQualityHistoryQueryRes.QualityIndex qindex = (MsgQualityHistoryQueryRes.QualityIndex) q.next();
	 	                      if(frontier == null) {
	 	                          String desc = qindex.getDesc();
	 	                      }
	 	                      String type = qindex.getType();
	 	                      String maxvalue ="";
	 	                      String minvalue ="";
	 	                      String value = "";
	 	                      if(type.equalsIgnoreCase("5")) {
	 	                          maxvalue = qindex.getMaxvalue();
	 	                          minvalue = qindex.getMinvalue();
	 	                      } else {
	 	                          value = qindex.getValue();
	 	                      }
	 	                      //�������
	 	                      StringBuffer addsql = new StringBuffer("insert into radio_quality_result_tab");
	 	                      addsql.append(" (result_id, equ_code, task_id, check_datetime, frequency, value1, value2, report_type, head_id, type, is_delete, store_datetime,  band) ");
                              addsql.append(" values (RADIO_DATA_RECOVERY_SEQ.nextval, '"+equ_code+"', '"+taskId+"',");
                              addsql.append(" to_date('"+check_datetime+"','YYYY-MM-DD HH24:MI:SS'), "+frequency+",");
                              if(type.equalsIgnoreCase("5")) {
                            	  addsql.append(minvalue+", ");
                            	  addsql.append(maxvalue+", ");
                              } else {
                            	  addsql.append(value+", ");
                            	  addsql.append("null, ");
                              }
                              addsql.append("1, ");
                              addsql.append(head_id+", ");
                              addsql.append(type+", ");
                              addsql.append("0, ");
                              addsql.append("sysdate ,");
                              addsql.append("'"+band+"') ");
                              DbComponent.exeUpdate(addsql.toString());
	 	                }
	                }
	               
	        	}
	        }
		} catch (Exception e) {
			 e.printStackTrace();
			 if(e.getMessage().equals("�������������󣬺�̨�Զ����յ����ݿ��У��뵽���ݲ�ѯ�в�ѯ�ϱ�������!")){
				 String sqlerr = " delete from radio_quality_result_tab where check_datetime <= TO_DATE('"+endDate+"','YYYY-MM-DD HH24:MI:SS') "
	              +" and check_datetime >= TO_DATE('"+startDate+"','YYYY-MM-DD HH24:MI:SS') "
	              +" and head_id = '"+head_id+"'";
	              try {
					DbComponent.exeUpdate(sqlerr);
				  } catch (DbException e1) {
					 e1.printStackTrace();
					return new EXEException("",e1.getMessage(),"");
				  }

			 }
			 res = e.getMessage();
			//return new EXEException("",e.getMessage(),"");
		} 
		return res;
	}
	/**
	 * ��ѯָ���������
	 * @param obj
	 * @return
	 */
	public Object queryQualityData(ASObject obj){
		ASObject resObj= new ASObject();
		String head_id = (String) obj.get("head_id");
		String equCode = (String) obj.get("receiverCode");
		String startDataTime = (String) obj.get("startDate");
		String endDataTime = (String) obj.get("endDate");
		String frequency = (String) obj.get("freq");
		String band = (String) obj.get("band");
		String type = (String) obj.get("type");
		StringBuffer sql = new StringBuffer("select t.equ_code,t.task_id,t.check_datetime,t.frequency," );
		sql.append("decode(t.type,1,'�ز���ƽ',3,'������',4,'˥��',5,'���ƶ����ֵ',8,'����') as type,");
		sql.append("t.value1,t.value2,t.band,t.description");
		sql.append(" from radio_quality_result_tab t ");
		sql.append(" where check_datetime >=TO_DATE('"+startDataTime+"', 'YYYY-MM-DD HH24:MI:SS')");
		sql.append(" and check_datetime <=TO_DATE('"+endDataTime+"', 'YYYY-MM-DD HH24:MI:SS') ");
		if(head_id!=null&&!head_id.equalsIgnoreCase("")){
			sql.append(" and head_id = " + head_id + " ");
		}
		if(equCode!=null&&!equCode.equalsIgnoreCase("")){
			sql.append(" and equ_code = '" + equCode + "' ");
		}
		if(band!=null&&!band.equalsIgnoreCase("")){
			sql.append(" and band = " + band + " ");
		}
		if(frequency!= null&&!frequency.equalsIgnoreCase("")) {
            sql.append(" and frequency = " + frequency + " ");
        }

        if(type!=null&&!type.equalsIgnoreCase("")) {
            sql.append(" and type in (" + type + ") ");
        }
        sql.append(" and is_delete = 0  order by frequency asc,check_datetime desc,type asc ");
        try {
			resObj=StringTool.pageQuerySql(sql.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","ָ�����ݲ�ѯ�쳣��"+e.getMessage(),"");
		}
		return resObj;
	}

}
