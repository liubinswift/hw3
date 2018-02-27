package org.jmask.web.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import org.jdom.*;
import java.lang.reflect.Method;
import com.viewscenes.util.ObjectQueue;
import com.viewscenes.sys.userlog.UserLog;

public class JMaskServlet extends HttpServlet {
    public JMaskServlet() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Default content type
        static final private String CONTENT_TYPE = "text/xml; charset=GB2312";

        //Default request charactor code
        static final private String REQUEST_TYPE = "UTF-8";

        public static ObjectQueue userLogQueue = new ObjectQueue("用户日志",10000,200);

    //Initialize global variables
    public void init() throws ServletException {
        com.viewscenes.sys.Startup.initService();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding(REQUEST_TYPE);
        response.setContentType(CONTENT_TYPE);
        //get module name
        HttpSession session  = request.getSession();
        BufferedReader br = request.getReader();
        String ip = request.getRemoteHost();
       try{
        StringBuffer msg = new StringBuffer();
        String line;
        while ( (line = br.readLine()) != null) {
          msg.append(line+"\r");
        }
       if (msg.length() == 0) {
          return;
        }
       Element root = StringTool.getXMLRoot(msg.toString());
       String seqId=root.getChildText("seqId"); 
        LogTool.debug("logMsg",msg.toString());
        String ret = processMessage(msg.toString(),ip,session);
        ret=ret.replace("<Msg", "<Msg seqId=\""+seqId+"\"");
        if(msg.toString().indexOf("com.viewscenes.web.RunState")>-1 && 
                msg.toString().indexOf("getAllWebsite")>-1){//查询站点状态的返回结果暂时不记录到文件，太多了
            System.out.println(ret.toString());
            LogTool.debug("logMsg","<msg type=\"com.viewscenes.web.RunState\" function=\"getAllWebsite\">这个方法的查询结果不记录到文件了，在后台直接输出");
        } else{
            LogTool.debug("logMsg",ret.toString());
        }
        response.setContentType("text/xml");
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.println(ret);
        out.flush();
      }
      catch(Exception e){
        LogTool.warning(e);
      }
    }

    public String processMessage(String msg,String ip,HttpSession session){

        Element root = null;
        String ret = "";
        java.util.Date sDate = new java.util.Date();
        long sTime = sDate.getTime();
        //parse xml config file;
        try {
            StringReader read = new StringReader(msg);
            org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);
            Document doc = builder.build(read);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }
        String type = root.getAttributeValue("type");
        String func = root.getAttributeValue("function");
        Object result = new Object();
        //reflection机制
        try{
            Class cla = Class.forName(type);
            Object obj = cla.newInstance();
            if(!func.equals("login")){
                java.lang.reflect.Method method = (java.lang.reflect.Method)
                                                  cla.getMethod(func,
                        new Class[] {String.class});
                result = method.invoke(obj, new Object[] {msg});
            }else{
                session.setAttribute("user_ip",ip);
                java.lang.reflect.Method method = (java.lang.reflect.Method)
                                                  cla.getMethod(func,
                        new Class[] {String.class,HttpSession.class});
                result = method.invoke(obj, new Object[] {msg,session});
            }
            //LogTool.debug("logMsg",result.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        java.util.Date eDate = new java.util.Date();
        long eTime = eDate.getTime();
        long functionRunTime = eTime - sTime;
       // System.out.println(result);
        return (String)result;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String type = request.getParameter("type");
        String func = request.getParameter("function");
        if ((type==null)||(func==null)){
            response.setContentType("text/xml; charset=UTF-8");
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println("<HTML><BODY>JMaskServlet is Running!</BODY></HTML>");
            out.flush();
            return;
        }
        Object result = new Object();
        //reflection机制
        try{
            Class cla = Class.forName(type);
            Object obj = cla.newInstance();
             if(func.equals("getGraphData")||func.equals("getScanData")){
                java.lang.reflect.Method method = (java.lang.reflect.Method)
                cla.getMethod(func,
                new Class[] {HttpServletRequest.class});
                result = method.invoke(obj, new Object[] {request});
            }else if((!func.equals("getTVFalshXml"))&&(!func.equals("getRadioFalshXml"))){
                java.lang.reflect.Method method = (java.lang.reflect.Method)
                                                  cla.getMethod(func,
                        new Class[] {String.class});
                result = method.invoke(obj, new Object[] {""});
            }else {
                java.lang.reflect.Method method = (java.lang.reflect.Method)
                                                  cla.getMethod(func,
                        new Class[] {String.class,HttpSession.class});
                result = method.invoke(obj, new Object[] {"",request.getSession()});
            }
          // System.out.println(result);
        response.setContentType("text/xml; charset=UTF-8");
        byte[] buffer = ((String)result).getBytes("UTF-8");
        response.getOutputStream().write(buffer);
        response.getOutputStream().flush();

        }catch(Exception ex){
            ex.printStackTrace();
        }

        //doProcess(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doProcess(request,response);
    }

    //Clean up resources
    public void destroy() {
    }
    private void jbInit() throws Exception {
    }
}
