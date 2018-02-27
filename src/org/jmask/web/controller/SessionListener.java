package org.jmask.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.viewscenes.web.sysmgr.user.OnlineUserBean;
import com.viewscenes.web.sysmgr.user.OnlineUserManager;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session=arg0.getSession();
		session.setMaxInactiveInterval(60*60*24);
		ArrayList<OnlineUserBean> userList=new ArrayList<OnlineUserBean>();
		session.setAttribute("userList", userList);

	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session=arg0.getSession();
		OnlineUserManager.removeUsersInSession(session);
	}

}
