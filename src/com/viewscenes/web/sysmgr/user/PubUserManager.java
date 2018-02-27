package com.viewscenes.web.sysmgr.user;

import java.util.ArrayList;
import java.util.List;


import com.viewscenes.web.sysmgr.user.PubRoleBean;
import com.viewscenes.web.sysmgr.user.PubUserBean;
import com.viewscenes.web.sysmgr.user.PubUserManagerService;

import flex.messaging.io.amf.ASObject;

public class PubUserManager {

	private PubUserManagerService userManagerService = new PubUserManagerService();
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:获取角色列表，取得指定角色请传入 ID,如果添加出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getRoleList(ASObject obj){
		String roleId = (String)obj.get("roleId");
		return userManagerService.getRoleList(roleId);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:添加角色，如果添加出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> addRole(ASObject asobj){
		PubRoleBean pubRoleBean = new PubRoleBean();
		pubRoleBean.setName((String)asobj.get("name"));
		pubRoleBean.setPriority((String)asobj.get("priority"));
		pubRoleBean.setDescription((String)asobj.get("description"));
		return userManagerService.addRole(pubRoleBean);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:修改角色信息，如果修改出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> updateRole(ASObject obj){
		PubRoleBean pubRoleBean = new PubRoleBean();
		pubRoleBean.setRole_id((String)obj.get("role_id"));
		pubRoleBean.setName((String)obj.get("name"));
		pubRoleBean.setPriority((String)obj.get("priority"));
		pubRoleBean.setDescription((String)obj.get("description"));
		return userManagerService.updateRole(pubRoleBean);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:删除角色信息,如果删除出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> delRoleById(ASObject obj){
		String roleId = (String)obj.get("roleId");
		return userManagerService.delRoleById(roleId);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:根据角色ID取得用户及角色信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getUserListByRoleId(ASObject obj){
		String roleId = (String)obj.get("role_id");
		return userManagerService.getUserListByRoleId(roleId);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:根据角色ID取得角色权限信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getFuncListByRoleId(ASObject obj){
		String roleId = (String)obj.get("roleId");
		return userManagerService.getFuncListByRoleId(roleId);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:添加用户信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public Object addUser(ASObject asobj){
		PubUserBean pubUserBean = new PubUserBean();
		pubUserBean.setUserCode((String)asobj.get("userCode"));
		pubUserBean.setUserName((String)asobj.get("userName"));
		pubUserBean.setUserPassword((String)asobj.get("password"));
		pubUserBean.setSex((String)asobj.get("sex"));
		pubUserBean.setTelephone((String)asobj.get("telephone"));
		pubUserBean.setAddress((String)asobj.get("address"));
		pubUserBean.setEmail((String)asobj.get("email"));
		pubUserBean.setDescription((String)asobj.get("description"));
		pubUserBean.setPost((String)asobj.get("post"));
		pubUserBean.setTelephone((String)asobj.get("telephone"));
		pubUserBean.setAge((String)asobj.get("age"));
		pubUserBean.setMobilephone((String)asobj.get("mobilephone"));
		pubUserBean.setRoleId((String)asobj.get("role_id"));
		return userManagerService.addUser(pubUserBean);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:修改角色
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> updateUser(PubUserBean pubUserBean){
		
		return userManagerService.updateUser(pubUserBean);
		
		
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:删除用户
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> delUserById(ASObject obj){
		String userId = (String)obj.get("userId");
		return userManagerService.delUserById(userId);
	}
	
	

	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:为用户添加模块
	 * <p>author-date:张文 2012-8-19
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> addUserCust(ASObject asObj){
		String user_id = (String)asObj.get("user_id");
		List<ASObject> listObj = (List<ASObject>)asObj.get("list");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listObj.size(); i++) {
			ASObject as = (ASObject)listObj.get(i);
			list.add(as.get("id").toString());
		}
		return userManagerService.addUserCust(user_id,list);
	}
	/**
	 * 默认为用户添加模块（角色所有权限）
	 * @param asobj
	 * @return
	 */
	public ArrayList<Object> addUserCustByRoleId(ASObject asobj){
		String user_id=(String)asobj.get("user_id");
		String role_id=(String)asobj.get("role_id");
		return userManagerService.addUserCustByRoleId(user_id, role_id);
	}
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:获取用户模块权限树
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getCustTreeByUserId(ASObject obj){
		String user_id = (String)obj.get("user_id");
		String role_id=(String)obj.get("role_id");
		return userManagerService.getCustTreeByUserId(user_id,role_id);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:为角色授权
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> grantFuncByRoleId(ASObject asObj){
		String role_id = (String)asObj.get("role_id");
		List<ASObject> listObj = (List<ASObject>)asObj.get("list");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listObj.size(); i++) {
			ASObject as = (ASObject)listObj.get(i);
			list.add(as.get("id").toString());
		}
		return userManagerService.grantFuncByRoleId(role_id,list);
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:获取角色权限树
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getFuncTreeByRoleId(ASObject obj){
		String roleId = (String)obj.get("role_id");
		return userManagerService.getFuncTreeByRoleId(roleId);
	}
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.manager.appManagement.PubUserManager.java/login
	 * <p>explain:
	 * <p>时间：2012-3-6
	 * <p>作者：wxb
	 * @param obj
	 * @return
	 */
	public Object login(ASObject obj){
		return userManagerService.login(obj);
	}
	
}
