package com.viewscenes.app;

import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.viewscenes.device.thirdparty.PostHttpThread;


/**
 * 基于HTTP发送消息的客户端实现
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class MsgClient {
	public MsgClient() {
	}

	/**
	 * 测试程序
	 * 
	 * @param parm1
	 * @param parm2
	 * @return
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 * @throws ModuleException
	 */
	public String process(HttpServletRequest parm1, HttpServletResponse parm2)
			throws javax.servlet.ServletException, java.io.IOException,
			Exception {
		/** @todo Implement this ModuleProcessor abstract method */
		sendMessage("", "");
		return "success.jsp";
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 * @throws ModuleException
	 */
	public void sendMessage(String url, String msg) throws Exception {
		// String url="http://10.10.2.12:8000/servlet/msgserver";
		connect(url, msg);
	}

	/**
	 * 与某个IP建立链接
	 * 
	 * @param ip
	 * @throws CommException
	 */
	public void connect(String url, String msg) throws Exception {
		try {
			URL url1 = new URL(url);
			int m_nPort = 80;
			if (url1.getPort() < 0) {
				m_nPort = 80;
			} else {
				m_nPort = url1.getPort();
			}
			new Socket(url1.getHost(), m_nPort);// 原来的方法捕捉不到异常，所以在这自己建立了个socket连接捕捉异常
			new PostHttpThread(url, msg);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("连接异常", ex);
		}
	}

	public String gettime() {
		String value = "2010-08-26 15:17:30.0";
		if (value != null) {
			try {
				// 2005-12-14 16:48:04.0
				value = value.substring(11, value.lastIndexOf("."));
				value = value.substring(0, value.lastIndexOf(":"));
				return value;
			} catch (Exception e) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 测试程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// TimeZone china =TimeZone.getTimeZone("GMT+08:00");
		// Calendar ca=Calendar.getInstance();
		// Date date = null;
		// try {
		// date=StringDateUtil.getDayTime("2010-8-26");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(date.getDate());
		// System.out.println(ca.get(Calendar.DAY_OF_MONTH));
		String value = "2010-08-26 15:17:30.0";

		// 2005-12-14 16:48:04.0
		value = value.substring(11, value.lastIndexOf("."));
		value = value.substring(0, value.lastIndexOf(":"));
		long yuyueTime = Timestamp.valueOf("2010-08-26 15:21:30").getTime();

		/*
		 * MsgClient client = new MsgClient(); try { String sql = "select * from
		 * radio_equ_alarm_tab";
		 * 
		 * GDSet result_set = null; try { result_set = DbComponent.Query(sql); }
		 * catch (DbException ex1) { ex1.printStackTrace(); } String msg =
		 * result_set.toString();
		 * 
		 * client.sendMessage("", msg); } catch (Exception ex) {
		 * ex.printStackTrace(); }
		 */
	}

}