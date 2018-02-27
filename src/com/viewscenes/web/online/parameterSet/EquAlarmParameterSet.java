package com.viewscenes.web.online.parameterSet;

import java.util.ArrayList;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.DAOOperator;
import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.EquipmentAPI;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.Security;

import flex.messaging.io.amf.ASObject;
/**
 * �豸�����������ó�ʼ����
 * @author leeo
 * @date 2012/07/26
 */
public class EquAlarmParameterSet {

	public EquAlarmParameterSet() {
		// TODO Auto-generated constructor stub
	}
	
	public Object EquipmentAlarmParamSet(ASObject obj){
		String result="";
		String code = (String) obj.get("code"); //վ�����
		String head_id = (String) obj.get("head_id");
		String type_id = (String) obj.get("type_id");
		String AbnormityLength1Gd =(String) obj.get("AbnormityLength1Gd"); //�����쳣����ʱ��
		String InputLineLevelDownThreshold1Gd = (String) obj.get("InputLineLevelDownThreshold1Gd");//����ѹ����
		String InputLineLevelUpThreshold1Gd = (String) obj.get("InputLineLevelUpThreshold1Gd");//����ѹ����
		String AbnormityLength2Jsh = (String) obj.get("AbnormityLength2Jsh");//���ջ��쳣����ʱ��
		String AbnormityLength3Tzh = (String) obj.get("AbnormityLength3Tzh"); //���ƶȿ��쳣����ʱ��
		String AbnormityLength4Tf = (String) obj.get("AbnormityLength4Tf");//�����ȿ��쳣����ʱ��
		String AbnormityLength5Yy = (String) obj.get("AbnormityLength5Yy"); //����ѹ�����쳣����ʱ��
		String AbnormityLength6Pp = (String) obj.get("AbnormityLength6Pp");  //Ƶƫ���쳣����ʱ��
		String user_id=(String) obj.get("user_id");
		String priority = (String) obj.get("priority");
		Long msgPrio=1L;
	    Security security = new Security();
	    try {
	       msgPrio = security.getMessagePriority(user_id, 0, 0, 0);
	    } catch (Exception ex2) {
	          ex2.printStackTrace();
	    }
	    priority = new Long(msgPrio).toString();
	    try {
			EquipmentAPI.msgEquipmentAlarmParamSetCmd(code, AbnormityLength4Tf, AbnormityLength3Tzh,
					AbnormityLength6Pp, AbnormityLength2Jsh,
					AbnormityLength5Yy, InputLineLevelUpThreshold1Gd, 
					InputLineLevelDownThreshold1Gd, AbnormityLength1Gd, priority);
			//�·��ӿڳɹ����豸�����������
			ASObject temp = this.getParamByHeadID(head_id);
			if(temp.get("param_id")!=null&&!temp.get("param_id").toString().equalsIgnoreCase("")){//����в���ֵ����ɾ��ԭ���Ĳ���
				String delsqp = "delete from radio_equ_alarm_param_tab where head_id="+head_id+"";
				DbComponent.exeUpdate(delsqp);
			}
			//�������õĲ������
			StringBuffer insertsql = new StringBuffer("insert into radio_equ_alarm_param_tab");
			insertsql.append("(param_id,head_id,InputLineLevelDownThreshold1Gd,InputLineLevelUpThreshold1Gd,");
			insertsql.append("AbnormityLength1Gd,AbnormityLength2Jsh,AbnormityLength3Tzh,");
			insertsql.append("AbnormityLength4Tf,AbnormityLength5Yy,AbnormityLength6Pp,head_type_id)");
			insertsql.append("values(RADIO_ALARM_SEQ.nextval,'"+head_id+"','"+InputLineLevelDownThreshold1Gd+"','"+InputLineLevelUpThreshold1Gd+"',");
			insertsql.append("'"+AbnormityLength1Gd+"','"+AbnormityLength2Jsh+"','"+AbnormityLength3Tzh+"',");
			insertsql.append("'"+AbnormityLength4Tf+"','"+AbnormityLength5Yy+"','"+AbnormityLength6Pp+"','"+type_id+"')");
			DbComponent.exeUpdate(insertsql.toString());
            result="�豸�����������óɹ�";
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		} 
		
		return result;
	}
	/**
	 * ����վ��id��ѯ��վ���Ƿ����豸������ʼ������
	 * @param headID
	 * @return
	 */
	public ASObject getParamByHeadID(String headID){
		String sql = "select * from radio_equ_alarm_param_tab where head_id="+headID+"";
		GDSet gd=null;
		ASObject obj = new ASObject();
		try {
			 gd = DbComponent.Query(sql);
			 if(gd.getRowCount()>0){
				 for(int i=0;i<gd.getRowCount();i++){
					obj.put("param_id", gd.getString(i, "param_id"));
					obj.put("head_id",  gd.getString(i, "HEAD_ID"));    //վ��id
					obj.put("HEAD_TYPE_ID", gd.getString(i, "HEAD_TYPE_ID"));      //վ������
					obj.put("ABNORMITYLENGTH1GD", gd.getString(i, "ABNORMITYLENGTH1GD")); //�����쳣����ʱ��
					obj.put("INPUTLINELEVELUPTHRESHOLD1GD", gd.getString(i, "INPUTLINELEVELUPTHRESHOLD1GD")); //�����ѹ����
					obj.put("INPUTLINELEVELDOWNTHRESHOLD1GD", gd.getString(i, "INPUTLINELEVELDOWNTHRESHOLD1GD"));//�����ѹ����
					obj.put("ABNORMITYLENGTH2JSH", gd.getString(i, "ABNORMITYLENGTH2JSH"));//���ջ��쳣����ʱ��
					obj.put("ABNORMITYLENGTH3TZH", gd.getString(i, "ABNORMITYLENGTH3TZH"));//���ƶȿ��쳣����ʱ��
					obj.put("ABNORMITYLENGTH4TF", gd.getString(i, "ABNORMITYLENGTH4TF"));  //�����ȿ��쳣����ʱ��
					obj.put("ABNORMITYLENGTH5YY", gd.getString(i, "ABNORMITYLENGTH5YY"));  //����ѹ�����쳣����ʱ��
					obj.put("ABNORMITYLENGTH6PP", gd.getString(i, "ABNORMITYLENGTH6PP"));  //Ƶƫ���쳣ʱ�䳤��
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
