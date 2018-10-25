package com.viewscenes.device.framework;



import javax.servlet.*;

import javax.servlet.http.*;

import java.io.*;

import java.util.*;

import java.net.URLDecoder;

import com.viewscenes.util.LogTool;



/**

 * �����豸��Ϣ��servlet��������ܻ���Ҫ���ɷֳɹ㲥�����ߣ����ֵȶ��servlet

 * @version 1.0

 */

public class DeviceServlet

    extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



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



  private void doWork(HttpServletRequest request) throws IOException {

    request.setCharacterEncoding("GB2312");
    BufferedReader br = request.getReader();


   try{
    StringBuffer msg = new StringBuffer();

    String line;

    while ( (line = br.readLine()) != null&&line.length()>0) {

      msg.append(line);

    }
    if (msg.length() == 0) {
      return;
    }
//    LogTool.info("devicelog",msg.toString());
    MessageServer.setMsg2Queue(msg.toString());
//   MessageServer.execute(msg.toString());
  }
  catch(Exception e){
	br.close();
    LogTool.warning(e);
  }
  }
}
