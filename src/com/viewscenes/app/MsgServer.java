package com.viewscenes.app;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 基于HTTP发送消息的服务端实现，暂时用于对直属台上报的站点报警、系统运行和维护报警的周报日报服务！
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: viewscenes</p>
 * @author 刘斌
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
   * 处理收到的消息
   * @param request
   * @throws IOException
   */
  private void doWork(HttpServletRequest request) throws IOException {
    BufferedReader br = request.getReader();
    StringBuffer msg = new StringBuffer();
    String line;
    /**
     * 接收消息
     */
    while ( (line = br.readLine()) != null) {
      msg.append(new String(line.getBytes("ISO-8859-1"), "GBK"));
    }
    if (msg.length() == 0) {
      return;
    }
      System.out.println(msg.toString());
    /**
     * 处理
     */
    storeDb sdb = new storeDb();
    sdb.store(msg.toString());

  }

}