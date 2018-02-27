package org.jmask.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Vector;

public class SessionHelper {

	private static Vector<HttpSession> sessions = new Vector<HttpSession>();

	public static HttpSession getSession(HttpServletRequest request){

		HttpSession session = request.getSession();

		if (!(sessions.indexOf(session) > -1)){

			sessions.add(session);
		}

		return session;

	}

	public static void addSession(HttpSession session){

		if (!(sessions.indexOf(session) > -1)){

			sessions.add(session);
		}

	}

	public  static void destroySession(HttpServletRequest request){

		HttpSession session = request.getSession();

		if (sessions.indexOf(session) > -1){

			sessions.remove(session);
		}

		session.invalidate();

	}

	public static void destroySession(HttpSession session){

		if (sessions.indexOf(session) > -1){

			sessions.remove(session);
		}

		session.invalidate();

	}

	public static void destroySessions(){

		for( int i = 0; i < sessions.size(); i++){

			destroySession(sessions.get(i));

		}

	}

	public static Boolean isSessionExists(HttpSession session){

		if (sessions.indexOf(session) > -1)

			return true;

		return false;

	}

}
