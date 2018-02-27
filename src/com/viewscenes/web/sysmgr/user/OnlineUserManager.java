package com.viewscenes.web.sysmgr.user;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import flex.messaging.FlexContext;
import flex.messaging.client.FlexClient;
import flex.messaging.io.amf.ASObject;





public class OnlineUserManager {
	private static ArrayList<Object> onlineUsers=new ArrayList<Object>();
	/**
	 * 用户添加到集合
	 * @param pubUser
	 * @param session
	 * @return
	 */
	public static Object addOnlineUser(PubUserBean pubUser){
		HttpServletRequest request=FlexContext.getHttpRequest();
		String ip=request.getRemoteHost();
		FlexClient clinet=FlexContext.getFlexClient();	
		OnlineUserBean onlineUser=setToOnlineUserBean(pubUser, ip);
		clinet.setAttribute(ip, onlineUser);
		if(onlineUsers.indexOf(onlineUser)==-1){
			onlineUsers.add(onlineUser);			
			HttpSession session=request.getSession();
			addUserToSession(session,onlineUser);
		}
		return pubUser;
	}
	/**
	 * 强制用户下线
	 * @param pubUser
	 * @param session
	 * @return
	 */
	public ArrayList<Object> removeUser(OnlineUserBean onlineUser){		
		String nowIp=FlexContext.getHttpRequest().getRemoteHost();
		Object obj=FlexContext.getFlexClient().getAttribute(nowIp);
		if(onlineUser.equals(obj)){
			ArrayList<Object> list=new ArrayList<Object>();
			list.add("message");
			return list;
		}
		onlineUsers.remove(onlineUser);
		return onlineUsers;
	} 
	/**
	 * 查询所有登录用户
	 * @param obj
	 * @param session
	 * @return
	 */
	public ArrayList<Object> getOnlineUsers(ASObject obj){
		
		String userCode=(String)obj.get("userCode");
		String userName=(String)obj.get("userName");
		ArrayList<Object> arrayList =new  ArrayList<Object>();
		for(Object user :onlineUsers){
			OnlineUserBean bean=(OnlineUserBean)user;
			boolean flag=true;
			if(userCode!=null&&userCode.length()!=0){
				if(bean.getUserCode().indexOf(userCode)==-1){
					flag=false;
				}
			}
			if(userName!=null&&userName.length()!=0){
				if(bean.getUserName().indexOf(userName)==-1){
					flag=false;
				}
			}
			if(bean.getIp()==null||bean.getIp().length()==0){
				flag=false;
			}
			if(flag){
				arrayList.add(bean);
			}
		}
		return arrayList;
	}
	private  static OnlineUserBean setToOnlineUserBean(PubUserBean bean,String ip){
		OnlineUserBean onlineUser =new OnlineUserBean();
		onlineUser.setAddress(bean.getAddress());
		onlineUser.setAge(bean.getAge());
		onlineUser.setDescription(bean.getDescription());
		onlineUser.setEmail(bean.getEmail());
		onlineUser.setIp(ip);
		onlineUser.setMobilephone(bean.getMobilephone());
		onlineUser.setPost(bean.getPost());
		onlineUser.setRoleId(bean.getRoleId());
		onlineUser.setRoleName(bean.getRoleName());
		onlineUser.setSex(bean.getSex());
		onlineUser.setTelephone(bean.getTelephone());
		onlineUser.setUserCode(bean.getUserCode());
		onlineUser.setUserId(bean.getUserId());
		onlineUser.setUserName(bean.getUserName());
		onlineUser.setUserPassword(bean.getUserPassword());
		return onlineUser;
	}
	public static boolean testLog(){		
		FlexClient client=FlexContext.getFlexClient();
		
		String ip=FlexContext.getHttpRequest().getRemoteHost();
		if(onlineUsers.indexOf(client.getAttribute(ip))==-1){
			return true;
		}
		return false;
	}
	private static void  addUserToSession(HttpSession session,OnlineUserBean onlineUser){
		ArrayList<OnlineUserBean> userList=null;
		userList=(ArrayList<OnlineUserBean>)session.getAttribute("userList");
		session.removeAttribute("userList");
		if(userList==null){
			userList=new ArrayList<OnlineUserBean>();
			userList.add(onlineUser);
		}else{
			if(userList.indexOf(onlineUser)==-1){				
				userList.add(onlineUser);
			}
		}
		session.setAttribute("userList", userList);
		removeUsersOnlyInSession(session);
	}
	private static void removeUsersOnlyInSession(HttpSession session){
		ArrayList<OnlineUserBean> userList=(ArrayList<OnlineUserBean>)session.getAttribute("userList");
		if(userList.size()==0){
			return ;
		}
		for(OnlineUserBean user: userList){
			if(!onlineUsers.contains(user)){
				userList.remove(user);
			}			
		}
	}
	public static void removeUsersInSession(HttpSession session){
		ArrayList<OnlineUserBean> userList=(ArrayList<OnlineUserBean>)session.getAttribute("userList");
		if(userList.size()==0){
			return ;
		}
		for(OnlineUserBean user:userList){
			if(onlineUsers.contains(user)){
				onlineUsers.remove(user);
			}
		}
	}
	public static Object logout(PubUserBean bean){
		HttpServletRequest request=FlexContext.getHttpRequest();
		String ip=request.getRemoteHost();
		FlexClient clinet=FlexContext.getFlexClient();	
		OnlineUserBean onlineUser=setToOnlineUserBean(bean, ip);
		clinet.removeAttribute(ip);
		if(onlineUsers.indexOf(onlineUser)!=-1){
			onlineUsers.remove(onlineUser);			
			HttpSession session=request.getSession();
			removeUserInSession(session,onlineUser);
		}
		
		return "";
	}
	private static void  removeUserInSession(HttpSession session,OnlineUserBean onlineUser){
		ArrayList<OnlineUserBean> userList=null;
		userList=(ArrayList<OnlineUserBean>)session.getAttribute("userList");
		session.removeAttribute("userList");
		if(userList==null){
			userList=new ArrayList<OnlineUserBean>();
			
		}else{
			if(userList.indexOf(onlineUser)!=-1){				
				userList.remove(onlineUser);
			}
		}
		session.setAttribute("userList", userList);
		removeUsersOnlyInSession(session);
	}
}
