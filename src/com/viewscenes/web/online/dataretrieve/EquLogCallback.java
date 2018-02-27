package com.viewscenes.web.online.dataretrieve;

import java.util.Collection;
import java.util.Iterator;

import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.EquipmentAPI;
import com.viewscenes.device.radio.MsgEquipmentLogHistoryQueryRes;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;
/**
 * �豸��־������
 * @author leeo
 * @date 2012/08/09
 */
public class EquLogCallback {
    /**
     * �����豸��־���սӿڣ������������
     * @param obj
     * @return
     */
	public Object equLogCallback(ASObject obj){
		String res="�豸��־���ճɹ�";
		String user_id = (String) obj.get("user_id");
		String priority = (String) obj.get("priority");
		String code = (String) obj.get("code");
		String startDateTime = (String) obj.get("starttime");
		String endDateTime = (String) obj.get("endtime");
		String logType="";
		Security security = new Security();
	    long msgPrio = 0;
	    if (user_id != null) {
	         try {
	             msgPrio = security.getMessagePriority(user_id, 2, 4, 0);
	             priority = new Long(msgPrio).toString();
	         } catch (Exception ex1) {
	        	 res=ex1.getMessage();
	         }
	    }
	    try {
			Collection resutList=EquipmentAPI.msgEquipmentLogHistoryQueryCmd(code, startDateTime, endDateTime, logType, priority);
			String insertSql = "insert into radio_equ_log_tab(log_id,log_datetime,description,remark,type_id,head_code) "
			  		+"values(RADIO_DATA_RECOVERY_SEQ.nextval,?,?,?,?,?)";
		    DbComponent db = new DbComponent();
		    DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(insertSql);
		    prepExeSQL.getConnect();
		    
			if(resutList!=null){
				try {
					//��ɾ����ǰʱ�ε�����
					String sql = "delete from radio_equ_log_tab where head_code='"
							+ code
							+ "' and  log_datetime<= " + "to_date('" + endDateTime + "','YYYY-MM-DD HH24:MI:SS') "
							+ " and log_datetime>= " + "to_date('" + startDateTime + "','YYYY-MM-DD HH24:MI:SS')";

                    DbComponent.exeUpdate(sql);
				} catch (DbException ex) {
					ex.printStackTrace();
					return "ɾ���豸��־����"+ex.getMessage();
				}
				//�������յ��������
				Iterator iter = resutList.iterator();
				while(iter.hasNext()){
					MsgEquipmentLogHistoryQueryRes.EquipmentLog log = (MsgEquipmentLogHistoryQueryRes.EquipmentLog) iter.next();
			        prepExeSQL.setString(1, log.getDateTime());
			        prepExeSQL.setString(2, log.getDesc() + "\\" + log.getName());
			        prepExeSQL.setString(3, log.getDesc());
			       if(!StringTool.isNumeric(log.getName())){
				     prepExeSQL.setString(4, "");
			       } else  prepExeSQL.setString(4, log.getName());
			       prepExeSQL.setString(5, code);
			       prepExeSQL.exeSQL();
				}
				
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 if(e.getMessage().equals("�������������󣬺�̨�Զ����յ����ݿ��У��뵽���ݲ�ѯ�в�ѯ�ϱ�������!"))
             {
                     /**
                   * ɾ��ͬ��ʱ��ε���������֤���ظ���,��ΪҪ
                   */
                 String sql = "delete from radio_equ_log_tab where head_code='"
                                + code
                                + "' and  log_datetime<= " + "to_date('" + endDateTime + "','YYYY-MM-DD HH24:MI:SS') "
                                + " and log_datetime>= " + "to_date('" + startDateTime + "','YYYY-MM-DD HH24:MI:SS')";

                  try {
					DbComponent.exeUpdate(sql);
				} catch (DbException e1) {
					e1.printStackTrace();
					return e.getMessage();
				}
             }
			 res= e.getMessage();
			
		} 
	    
		return res;
	}
	/**
	 * ��ѯ���������豸��־����
	 * @param obj
	 * @return
	 */
	public Object queryLogData(ASObject obj){
		ASObject resObj= new ASObject();
		String code = (String) obj.get("code");
		String startDateTime = (String) obj.get("starttime");
		String endDateTime = (String) obj.get("endtime");
		StringBuffer sql = new StringBuffer("select equ.log_id,equ.head_code,LOG_DATETIME,tt.type,t.category,equ.remark,equ.description from radio_equ_log_tab  equ,dic_equ_log_type_tab tt,dic_equ_category_tab t ");
		sql.append(" where equ.type_id=tt.type_id(+) and tt.category_id=t.category_id(+)");
		if(code.length()>0)
            sql.append(" and equ.head_code='"+code+"' ");

        if(startDateTime.length()>0)
            sql.append(" and equ.log_datetime  >=to_date('" +startDateTime+ "','yyyy-MM-dd HH24:MI:SS') ");

        if(endDateTime.length()>0)
            sql.append(" and equ.log_datetime  <=to_date('" +endDateTime+ "','yyyy-MM-dd HH24:MI:SS') ");
        sql.append(" order by log_datetime desc ");
        try {
			resObj=StringTool.pageQuerySql(sql.toString(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","�豸��־�������ݲ�ѯ�쳣��"+e.getMessage(),"");
		}
		return resObj;
	}
}
