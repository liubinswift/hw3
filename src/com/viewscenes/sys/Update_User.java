package com.viewscenes.sys;

import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.util.StringTool;

/**
 * ������������޸��û�ģ��,ҳ��ע����ť��
 * ��Ϊ�п����漰�������޸���,�����Ұ���������ΪUpdate_User
 * zsx 20111207
 * @author Administrator
 *
 */
public class Update_User {

	
	/**
	 * �޸��û�����,Admin�û��ǲ����޸ĵ�
	 * @param msg
	 * @return
	 */
	public String update_Password(String msg){
		String result = null;
		Element root = StringTool.getXMLRoot(msg);
		String user_id = root.getChildText("user_id");
		String user_name = root.getChildText("user_name");
		String user_code = root.getChildText("user_code");
//		String basePassword = root.getChildText("basePassword");
		String newPassword = root.getChildText("newPassword");
		String newPassword1 = root.getChildText("newPassword1");
		
		if(!newPassword.equals(newPassword1)){
			return StringTool.getXmlErrorMessage("�������ȷ�����벻ͬ,��ȷ���޸�!");
		}
		
		
		MD5 md5 = new MD5();
		String param_userPass = md5.getMD5ofStr(newPassword);
        
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("  update sec_user_tab set  \n");
		sbsql.append("  user_password='"+param_userPass+"'  \n");
		sbsql.append("  where user_id='"+user_id+"'  \n");
		
		try {
			DbComponent.exeUpdate(sbsql.toString());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = "�ɹ��޸��û�:" + user_name + "������!";
		return StringTool.getXmlRightMessage(result);
	}
}
