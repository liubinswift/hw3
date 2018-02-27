package com.viewscenes.sys;

import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.util.StringTool;

/**
 * 单独提出来的修改用户模块,页面注销按钮下
 * 因为有可能涉及到其他修改项,所以我把类名定义为Update_User
 * zsx 20111207
 * @author Administrator
 *
 */
public class Update_User {

	
	/**
	 * 修改用户密码,Admin用户是不能修改的
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
			return StringTool.getXmlErrorMessage("新密码和确认密码不同,请确认修改!");
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
		result = "成功修改用户:" + user_name + "的密码!";
		return StringTool.getXmlRightMessage(result);
	}
}
