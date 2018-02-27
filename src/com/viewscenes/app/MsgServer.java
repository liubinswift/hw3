package com.viewscenes.app;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * ����HTTP������Ϣ�ķ����ʵ�֣���ʱ���ڶ�ֱ��̨�ϱ���վ�㱨����ϵͳ���к�ά���������ܱ��ձ�����
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: viewscenes</p>
 * @author ����
 * @version 1.0
 */
public class MsgServer
    extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=GBK";

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
	   doDefault(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    doWork(request);
    doDefault(request, response);
  }

  private void doDefault(HttpServletRequest request,

          HttpServletResponse response) throws IOException {

			response.setContentType("text/html");
			
			PrintWriter out = new PrintWriter(response.getOutputStream());
			
			out.println("<HTML><BODY>OK</BODY></HTML>");
			
			out.flush();

}

  /**
   * �����յ�����Ϣ
   * @param request
   * @throws IOException
   */
  private void doWork(HttpServletRequest request) throws IOException {
    BufferedReader br = request.getReader();
    StringBuffer msg = new StringBuffer();
    String line;
    /**
     * ������Ϣ
     */
    while ( (line = br.readLine()) != null) {
      msg.append(new String(line.getBytes("ISO-8859-1"), "GBK"));
    }
    if (msg.length() == 0) {
      return;
    }
      System.out.println(msg.toString());
    /**
     * ����
     */
    storeDb sdb = new storeDb();
    sdb.store(msg.toString());

  }

}