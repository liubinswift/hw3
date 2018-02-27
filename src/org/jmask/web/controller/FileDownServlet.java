package org.jmask.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.Element;
import org.jmask.web.util.ClassRegister;
import org.jmask.web.util.SessionHelper;

import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;


/**
 *
 * <p>Title: 文件下载入口</p>
 *
 * <p>Description: 入口类根据用户的请求中的执行方法来执行反射调用，并完成下载或其它相关如生成EXCEL的操作</p>
 *
 * <p>该类的基类为javax.servlet.http.HttpServlet。</p>
 *
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author not attributable
 * @version 1.0
 */

public class FileDownServlet extends HttpServlet {

	static final private String CONTENT_TYPE = "text/xml; charset=GB2312";

	static final private String REQUEST_TYPE = "UTF-8";

        /**
         * 无参数的构造方法，调用基类的构造函数
         */
        public FileDownServlet() {

		super();
	}


        /**
         * 基类方法的重构。调用基类destroy()方法并执行其它所有相关的清除操作
         */
        public void destroy() {

		super.destroy();

	}


        public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
                    doPost(request,response);
	}

        /**
         * doPost方法
         * 该方法用于接受用户以POST方法访问时的请求。页面以POST方式向服务器提交数据是系统支持的业务访问方式。
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @throws ServletException
         * @throws IOException
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

                processMessage(request, response);

	}




        /**
         * 根据给定的XML消息，对给定的方法执行反射调用。
         * @param msg String XML消息
         * @param session HttpSession session
         * @return String 调用结果XML消息
         */
        public void processMessage(HttpServletRequest request,HttpServletResponse response){
            response.setCharacterEncoding("GBK");
            String msg="";
            try {
                        msg = new String( request.getParameter("msg").getBytes("8859_1"), "UTF-8").trim();
                    } catch (UnsupportedEncodingException ex1) {
        }

            if (msg == null)
                return;

            LogTool.debug(msg);

            Element root = StringTool.getXMLRoot(msg);

            String className = root.getAttributeValue("type");

	    String func = root.getAttributeValue("function");

            String log = root.getAttributeValue("log"); //记录操作日志

            HttpSession session  = SessionHelper.getSession(request);

	    String[] paramTypeArr = ClassRegister.getMethodParaType(className, func);

            int paramTypeArrLength = paramTypeArr.length;


            Class cla = null;

            Object obj = null;

            Method method = null;

            try {

                cla = Class.forName(className);

                obj = cla.newInstance();

                if(paramTypeArrLength == 0 || paramTypeArr == null){//方法无参数

                    method = (Method)cla.getMethod(func,new Class[] {});

                    method.invoke(obj, new Object[] {});

                }else if(paramTypeArrLength == 1
                         && (paramTypeArr[0].equals("java.lang.String"))){

                    method = (Method)cla.getMethod(func,new Class[] {String.class});

                    method.invoke(obj, new Object[] {msg});

                } else if (paramTypeArrLength == 2
                           && (paramTypeArr[0].equals("java.lang.String"))
                           && (paramTypeArr[1].equals("javax.servlet.http.HttpSession"))){

                    method = (Method)cla.getMethod(func,new Class[] {String.class, HttpSession.class});

                    method.invoke(obj, new Object[] {msg, session});

                } else if (paramTypeArrLength == 3
                           && (paramTypeArr[0].equals("java.lang.String"))
                           && (paramTypeArr[1].equals("javax.servlet.http.HttpServletRequest"))
                           && (paramTypeArr[2].equals("javax.servlet.http.HttpServletResponse"))){

                    method = (Method)cla.getMethod(func,new Class[] {String.class,HttpServletRequest.class,HttpServletResponse.class});

                    method.invoke(obj, new Object[] {msg, request,response});

                } else if (paramTypeArrLength == 4
                           && (paramTypeArr[0].equals("java.lang.String"))
                           && (paramTypeArr[1].equals("javax.servlet.http.HttpServletRequest"))
                           && (paramTypeArr[2].equals("javax.servlet.http.HttpServletResponse"))
                           && (paramTypeArr[3].equals("javax.servlet.http.HttpSession"))){

                    method = (Method)cla.getMethod(func,new Class[] {String.class,HttpServletRequest.class,HttpServletResponse.class,HttpSession.class});

                    method.invoke(obj, new Object[] {msg, request,response,session});

                }

                String result="<?xml version=\"1.0\" encoding=\"GB2312\"?><Msg return=\"1\"></Msg>";

                //日志记录操作 用户功能操作日志
//            if (log!= null  && !log.equals("")){
//
//                String ip = new com.viewscenes.util.WebCacheBypass(request).getRemoteAddr();
//
//                String logSql = UserLog.insertUserLog(msg,result, ip,session);
//                if (!logSql.equalsIgnoreCase(""))
//                        JMaskServlet.userLogQueue.add(logSql);
//            }


                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    response.setCharacterEncoding("GBK");
                } catch (IOException ex) {
                }
                out.println("<script language='javascript'>;");
                out.println("alert('Success!')");
                out.println("</script>");
            } catch (Exception e) {
            	e.printStackTrace();
                LogTool.warning("FileDownSerlet:processMessage()::" + e.getClass().getName() + "--" + e.toString() );
                System.out.println(e.getClass());
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                } catch (IOException ex) {
                }
                out.println("<script language='javascript'>;");
                out.println("alert('对不起,您的下载出现异常!')");
                out.println("window.history.back()");
                out.println("</script>");

            }

        }


        public void init() throws ServletException {
	}

}
