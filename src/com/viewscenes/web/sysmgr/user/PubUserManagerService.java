package com.viewscenes.web.sysmgr.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Element;
import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbException;
import com.viewscenes.web.sysmgr.user.PubUserManagerServiceDao;
import com.viewscenes.web.sysmgr.user.PubFuncBean;
import com.viewscenes.web.sysmgr.user.PubRoleBean;
import com.viewscenes.web.sysmgr.user.PubUserBean;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.MD5;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class PubUserManagerService {

	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:获取角色列表，取得指定角色请传入 ID,如果添加出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getRoleList(String roleId){
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			GDSet set = PubUserManagerServiceDao.getRoleSet(roleId);
			
			for(int i = 0 ;i < set.getRowCount(); i++){
				PubRoleBean roleBean = new PubRoleBean();
				roleBean.setRole_id(set.getString(i, "role_id"));
				roleBean.setName(set.getString(i, "name"));
				roleBean.setDescription(set.getString(i, "description"));
				roleBean.setPriority(set.getString(i, "priority"));
				list.add(roleBean);
			}
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:获取角色列表出错,"+e.getMessage(), "");
			list.add(ex);
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:获取角色列表出错,"+e.getMessage(), "");
			list.add(ex);
		}
		
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:添加角色，如果添加出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> addRole(PubRoleBean pubRoleBean){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			
			
			String roleName = pubRoleBean.getName();
			
			boolean has = PubUserManagerServiceDao.isExistRole(pubRoleBean);
			
			if (has){
				EXEException ex =  new EXEException("", "error:该角色名称已存在", "");
				list.add(ex);
				return list;
			}
			PubUserManagerServiceDao.addRole(pubRoleBean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:添加角色对象["+pubRoleBean.toString()+"]出错,"+e.getMessage(), "");
			list.add(ex);
		}
		
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:修改角色信息，如果修改出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> updateRole(PubRoleBean pubRoleBean){
		ArrayList<Object> list =  new ArrayList<Object>();
		
		try {
			
			boolean has = PubUserManagerServiceDao.isExistRole(pubRoleBean);
			
			if (has){
				EXEException ex =  new EXEException("", "error:该角色名称已存在", "");
				list.add(ex);
				return list;
			}
			
			PubUserManagerServiceDao.updateRole(pubRoleBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:修改角色对象["+pubRoleBean.toString()+"]出错,"+e.getMessage(), "");
			list.add(ex);
		}
		
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:删除角色信息,如果删除出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> delRoleById(String roleId){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			PubUserManagerServiceDao.delRoleById(roleId);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:删除角色对象ID="+roleId+"出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:根据角色ID取得用户及角色信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getUserListByRoleId(String roleId){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			GDSet set = PubUserManagerServiceDao.getUserSetByRoleId(roleId);
			
			for(int i = 0 ;i < set.getRowCount();i++){
				PubUserBean userBean = new PubUserBean();
				userBean.setUserId(set.getString(i, "user_id"));
				userBean.setUserCode(set.getString(i, "user_code"));
				userBean.setUserName(set.getString(i, "user_name"));
				userBean.setSex(set.getString(i, "sex"));
				userBean.setTelephone(set.getString(i, "telephone"));
				userBean.setAddress(set.getString(i, "address"));
				userBean.setEmail(set.getString(i, "email"));
//				userBean.setStart_time(set.getString(i, "start_time"));
//				userBean.setEnd_time(set.getString(i, "end_time"));
				userBean.setDescription(set.getString(i, "description"));
				userBean.setAge(set.getString(i, "age"));
				userBean.setMobilephone(set.getString(i, "mobilephone"));
				userBean.setPost(set.getString(i, "post"));
				userBean.setRoleId(set.getString(i, "role_id"));
				userBean.setRoleName(set.getString(i, "roleName"));
				userBean.setPriority(set.getString(i, "priority"));
				
				list.add(userBean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:获取角色对象ID="+roleId+"的用户信息出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:根据角色ID取得角色权限信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getFuncListByRoleId(String roleId){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			
			GDSet set = PubUserManagerServiceDao.getFuncSetByRoleId(roleId);
			for(int i = 0 ;i < set.getRowCount();i++){
				PubFuncBean pubFuncBean = new PubFuncBean();
				pubFuncBean.setFunc_id(set.getString(i, "OPERATION_ID"));
//				pubFuncBean.setCode(set.getString(i, "code"));
				pubFuncBean.setName(set.getString(i, "OPERATION_name"));
//				pubFuncBean.setLevels(set.getString(i, "levels"));
//				pubFuncBean.setOrders(set.getString(i, "orders"));
				pubFuncBean.setParent_func_id(set.getString(i, "PARENT_OPERATION_ID"));
//				pubFuncBean.setShow_flag(set.getString(i, "showFlag"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:获取角色对象ID="+roleId+"的权限信息出错,"+e.getMessage(), "");
			list.add(ex);
		} 
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:添加用户信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public Object addUser(PubUserBean pubUserBean){
		
		String newid = "";
		try {
			
			boolean has = PubUserManagerServiceDao.isExistUser(pubUserBean);
			
			if (has){
				EXEException ex =  new EXEException("", "error:该用户名称已存在", "");
//				list.add(ex);
				return ex;
			}
			
			int[] res = PubUserManagerServiceDao.addUser(pubUserBean);
			newid = res[0]+"";
		} catch (Exception e) {
			LogTool.fatal(e);
			EXEException ex =  new EXEException("", "error:添加用户["+pubUserBean.toString()+"]出错,"+e.getMessage(), "");
//			list.add(ex);
			return ex;
		}
		return newid;
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
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			
			String userName = pubUserBean.getUserName();
			
			boolean has = PubUserManagerServiceDao.isExistUser(pubUserBean);
			
			if (has){
				EXEException ex =  new EXEException("", "error:该用户名称已存在", "");
				list.add(ex);
				return list;
			}
			
			PubUserManagerServiceDao.updateUser(pubUserBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:修改用户["+pubUserBean.toString()+"]出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:删除用户
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> delUserById(String userId){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			PubUserManagerServiceDao.delUserById(userId);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:删除用户ID=["+userId+"]出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:为用户添加模块
	 * <p>author-date:张文 2012-8-19
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> addUserCust(String user_id,List<String> funcList){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			PubUserManagerServiceDao.addUserCust(user_id,funcList);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:为用户ID=["+user_id+"]授权出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	/**
	 * 默认为用户添加模块（角色所有权限）
	 * @param user_id
	 * @param role_id
	 * @return
	 */
	public ArrayList<Object> addUserCustByRoleId(String user_id,String role_id){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			GDSet set = PubUserManagerServiceDao.getFuncSetByRoleId(role_id);
			List<String> funcList=new ArrayList<String>();
			for(int i = 0 ;i < set.getRowCount();i++){
				funcList.add(set.getString(i, "OPERATION_ID"));
			}
			addUserCust(user_id, funcList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:为用户ID=["+user_id+"]授权出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:获取用户权限树
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getCustTreeByUserId(String user_id,String ...roleId){
		ArrayList<Object> list =  new ArrayList<Object>();
		
		try {
			String treeXML = PubUserManagerServiceDao.getCustTreeByUserId(user_id,roleId);
			list.add(treeXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			EXEException ex =  new EXEException("", "error:获取角色ID="+user_id+"权限列表出错,"+e.getMessage(), "");
			list.add(ex);
		}
		
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:为角色授权
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> grantFuncByRoleId(String roleId,List<String> funcList){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			PubUserManagerServiceDao.grantFuncByRoleId(roleId,funcList);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:为角色ID=["+roleId+"]授权出错,"+e.getMessage(), "");
			list.add(ex);
		}
		return list;
	}
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.framework.service.pub
	 * <p>explain:获取角色权限树
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public ArrayList<Object> getFuncTreeByRoleId(String roleId){
		ArrayList<Object> list =  new ArrayList<Object>();
		
		try {
			String treeXML = PubUserManagerServiceDao.getFuncTreeByRoleId(roleId);
			list.add(treeXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			EXEException ex =  new EXEException("", "error:获取角色ID="+roleId+"权限列表出错,"+e.getMessage(), "");
			list.add(ex);
		}
		
		return list;
	}

	/**
	 * <p>class/function:com.viewscenes.framework.service.pub/login
	 * <p>explain:用户登录验证
	 * <p>时间：2012-3-1
	 * <p>作者：wxb
	 * @param obj
	 * @return
	 */
	public Object login(ASObject obj) {
		// TODO Auto-generated method stub
		
		String userName = (String)obj.get("userName");
		String password = (String)obj.get("password");
		
		try {
			
			PubUserBean user = new PubUserBean();
			
			GDSet gd = PubUserManagerServiceDao.getUserByUserName(userName);
			
			if(gd.getRowCount() <=0 ){
				return new EXEException("","用户不存在","");
			}else{
				
				
				
				String pw = gd.getString(0, "user_password");
				
				MD5 md5 = new MD5();
				String md5OfPw = md5.getMD5ofStr(password);
				
				if(md5OfPw.equals(pw)){
					user.setUserId(gd.getString(0, "user_id"));
					user.setUserCode(gd.getString(0, "user_code"));
					user.setUserName(gd.getString(0, "user_name"));
					user.setUserPassword(gd.getString(0, "user_password"));
					user.setAge(gd.getString(0, "age"));
					user.setSex(gd.getString(0, "sex"));
					user.setMobilephone(gd.getString(0, "mobilephone"));
					user.setTelephone(gd.getString(0, "telephone"));
					user.setAddress(gd.getString(0, "address"));
					user.setPost(gd.getString(0, "post"));
					user.setDescription(gd.getString(0, "description"));
					user.setEmail(gd.getString(0, "email"));
					user.setPriority(gd.getString(0, "priority"));
					user.setRoleId(gd.getString(0, "role_id"));
					user.setRoleName(gd.getString(0, "role_name"));
					
				}else{
					return new EXEException("","密码错误","");
				}
			}
			
			
			return user;
			
		} catch (DbException e) {
			LogTool.fatal(e);
			return new EXEException("", e.getMessage(), "");
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("", "验证用户数据过程中出错", "");
		}
		
	}

}
