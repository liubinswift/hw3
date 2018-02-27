package com.viewscenes.util;

import java.util.*;

import javax.servlet.http.*;



import com.viewscenes.dao.database.*;

import com.viewscenes.device.exception.*;

import com.viewscenes.pub.*;

import com.viewscenes.util.*;




public class MiscTool {

  LogTool lt = new LogTool();

  public MiscTool() {}



  public void setBaseInfo(HttpServletRequest request, String operationName,

                          String operationResult,

                          String operationInfo) {

    request.setAttribute("operation_name", operationName);

    request.setAttribute("operation_result", operationResult);

    request.setAttribute("operation_info", operationInfo);



  } //end.

  public static void setBaseInfo2(HttpServletRequest request, String operationName,

                          String operationResult,

                          String operationInfo) {

    request.setAttribute("operation_name", operationName);

    request.setAttribute("operation_result", operationResult);

    request.setAttribute("operation_info", operationInfo);



  } //end.





  public Date parseDate(String year, String month, String day, String hour,

                        String minute, String second) {



    if (year == null || month == null || day == null || hour == null

    || minute == null || second == null) {

      return null;

    }

    if (year.equals("") || month.equals("") || day.equals("")

    || hour.equals("") || minute.equals("") || second.equals("")) {

      return null;

    }



    int syear = Integer.parseInt(year);

    int smonth = Integer.parseInt(month);

    int sday = Integer.parseInt(day);

    int shour = Integer.parseInt(hour);

    int sminute = Integer.parseInt(minute);

    int ssecond = Integer.parseInt(second);

    Calendar c = Calendar.getInstance();

    c.set(syear, smonth - 1, sday, shour, sminute, ssecond);

    return c.getTime();



  }



  /**工具函数,用来把形如"s1,s2,s3,...,sn"的串转成Long对象的vector

   *

   * @param s:[输入]形如"s1,s2,s3,...,sn"的串

   * @return Long对象的Vector容器

   */

  public Vector parseString2LongVector(String s) {

    if (s == null)

      return null;

    String s1 = new String(s);

    Vector v = new Vector();

    int index = s1.indexOf(',');

    if (index < 0) {

      long ch_id = Long.parseLong(s);

      Long och_id = new Long(ch_id);

      v.add(och_id);

      return v;

    }



    for (; ; ) {

      index = s1.indexOf(',');

      if (index < 0) {

        long l = Long.parseLong(s1);

        Long ol = new Long(l);

        v.add(ol);

        return v;

      }

      String ts = s1.substring(0, index);

      long ch_id = Long.parseLong(ts);

      Long och_id = new Long(ch_id);

      v.add(och_id);

      s1 = s1.substring(index + 1);

    }

  }



  /**工具函数,用来把形如"s1,s2,s3,...,sn"的串转成串的vector

   *

   * @param s:[输入]形如"s1,s2,s3,...,sn"的串

   * @return String的Vector容器

   */

  public Vector parseString2StringVector(String s) {

    if (s == null)

      return null;

    String s1 = new String(s);

    Vector v = new Vector();

    int index = s1.indexOf(',');

    if (index < 0) {

      v.add(s);

      return v;

    }



    for (; ; ) {

      index = s1.indexOf(',');

      if (index < 0) {



        v.add(s1);

        return v;

      }

      String ts = s1.substring(0, index);

      v.add(ts);

      s1 = s1.substring(index + 1);

    }

  } //function end





  /**工具函数,用来把形如"s1,s2,s3,...,sn"的串转成串的vector

   *

   * @param s:[输入]形如"s1,s2,s3,...,sn"的串

   * @return String的Vector容器

   */

  static public Vector parseString2StringVector2(String s) {

    if (s == null)

      return null;

    String s1 = new String(s);

    Vector v = new Vector();

    int index = s1.indexOf(',');

    if (index < 0) {

      v.add(s);

      return v;

    }



    for (; ; ) {

      index = s1.indexOf(',');

      if (index < 0) {



        v.add(s1);

        return v;

      }

      String ts = s1.substring(0, index);

      v.add(ts);

      s1 = s1.substring(index + 1);

    }

  } //function end





  /**工具函数,用来把形如"s1,s2,s3,...,sn"的串转成Float对象的vector

   *

   * @param s:[输入]形如"s1,s2,s3,...,sn"的串

   * @return Float对象的Vector容器

   */



  public Vector parseString2FloatVector(String s) {

    if (s == null || s.equalsIgnoreCase(""))

      return null;

    String s1 = new String(s);

    Vector v = new Vector();



    int index = s1.indexOf(',');

    if (index < 0) {

      float ch_id = Float.parseFloat(s);

      Float och_id = new Float(ch_id);

      v.add(och_id);

      return v;

    }



    for (; ; ) {

      index = s1.indexOf(',');

      if (index < 0) {

        float f = Float.parseFloat(s1);

        Float of = new Float(f);

        v.add(of);

        return v;

      }

      String ts = s1.substring(0, index);

      float ch_id = Float.parseFloat(ts);

      Float och_id = new Float(ch_id);

      v.add(och_id);

      s1 = s1.substring(index + 1);

    }

  }



  public void setDetailInfo(HttpServletRequest request, String headId,

                            String chId,

                            String videoUrl,

                            String playerType, String streamType, String freq,

                            String chName) {



    String detail = "<input type=hidden name=ch_id value=" + chId + " ><br>";

    detail += "<input type=hidden name=head_id value=" + headId + "><br>";

    detail += "<input type=hidden name=video_url value=" + videoUrl + " ><br>";

    detail += "<input type=hidden name=player_type value=" + playerType +

        "><br>";

    detail += "<input type=hidden name=stream_type value=" + streamType +

        "><br>";

    detail += "<input type=hidden name=freq value=" + freq + "><br>";

    detail += "<input type=hidden name=ch_name value=" + chName + " ><br>";

    request.setAttribute("operation_detail", detail);



  } //function end.



  public void setDetailInfo2(HttpServletRequest request, String headId,

                            String chId,

                            String videoUrl,

                            String playerType,

                            String streamType,

                            String freq,

                            String chName,

                            String band

                            ) {



    String detail = "<input type=hidden name=ch_id value=" + chId + " ><br>";

    detail += "<input type=hidden name=head_id value=" + headId + "><br>";

    detail += "<input type=hidden name=video_url value=" + videoUrl + " ><br>";

    detail += "<input type=hidden name=player_type value=" + playerType +

        "><br>";

    detail += "<input type=hidden name=stream_type value=" + streamType +

        "><br>";

    detail += "<input type=hidden name=freq value=" + freq + "><br>";

    detail += "<input type=hidden name=ch_name value=" + chName + " ><br>";

    detail += "<input type=hidden name=band value=" + band + " ><br>";

    request.setAttribute("operation_detail", detail);



  } //function end.




/*
  public static void showLog(String log) {

    LogTool.info("tv_log", log);

  } //function end.



  public static void showDebug(String log) {

    LogTool.debug("tv_log", log);

  } //function end.

*/

  public static void showLog(HttpServletRequest request) {

    Enumeration e = request.getParameterNames();

    String log = "";

    while (e.hasMoreElements()) {

      String name = (String) e.nextElement();

      String value = request.getParameter(name);

      log = log + name + "=" + value + " ,";

    }

    LogTool.info("tv_log", log);

  } //function end.




/*
  public static void showLog(Exception e) {

    LogTool.info("tv_log", e);

  } //function end.
*/

/*
  public static void Assert(boolean cond,String msg) throws ModuleException {

  //assert(msg!=null):"msg=null";

   if(cond){

     return;

   }

    ModuleException e = new ModuleException(msg);

    throw e;

  } //function end.

*/

  public static String getCodeDesc(String code) {

    //assert (code != null):"code=null";

    String desc = "未知错误代码=" + code;

    if (code.equalsIgnoreCase("1")) {

      desc = "没有查询到数据";

    }

    else if (code.equalsIgnoreCase("2")) {

      desc = "没有权限";

    }

    else if (code.equalsIgnoreCase("3")) {

      desc = "内部错误";

    }

    else if (code.equalsIgnoreCase("4")) {

      desc = "指定的分中心未找到";

    }

    else if (code.equalsIgnoreCase("5")) {

      desc = "用户名密码错误";

    }

    else if (code.equalsIgnoreCase("6")) {

      desc = "资源不足";

    }

    return desc;

  } //function end.

 public static String handleExceptions(Exception e){

   //assert(e!=null):"//assert(e!=null)";

   String msg ="";

   if(e instanceof DeviceTimedOutException){

     msg="设备异常:超时";

   }

   else if (e instanceof GDSetException){

     msg= "GDSet异常:"+e.getMessage();

   }

   else if( e instanceof DbException){

        msg= "Db异常:"+e.getMessage();

   }

   else{

     msg = e.getMessage();

   }

   return msg;

 }



 public static void handleExceptions(Throwable e,HttpServletRequest request,

 String opName){

   //assert(e!=null):"//assert(e!=null)";

   ////assert(!(request!=null&&opName==null))

   //:"request和opName必须同时为null或同时有效";

   //assert(request!=null):"//assert(request!=null)";

   //assert(opName!=null):"//assert(opName!=null)";

   String msg ="";

   if(e instanceof DeviceTimedOutException){

     msg="设备异常:超时";

   }

   else if( e instanceof DeviceException){

        msg= "设备异常:"+e.getMessage();

   }

   else if (e instanceof GDSetException){

     msg= "GDSet异常:"+e.getMessage();

   }

   else if( e instanceof DbException){

        msg= "Db异常:"+e.getMessage();

   }

   else if( e instanceof Exception){

     msg = e.getMessage();

   }

   else if(e instanceof Error){

     msg = e.getMessage();

   }

   if(msg==null){

     msg=e.getClass().toString();



   }

  setBaseInfo2(request,opName,"失败",msg);

  if( e instanceof Exception){

  LogTool.debug((Exception)e);

  }

  if(e instanceof Error){

    e.printStackTrace();

  }

  LogTool.debug(opName+":失败");

 }//function



 public static String  validAssign(String paramName ,String paramValue){

   //assert(paramValue!=null):"validAssign:paramValue=null.";

   String param=paramName;

   if(paramName==null){

   param  =  paramValue;

   }

   try{

     Long.parseLong(paramName);

   }catch(Exception e){

     param=  paramValue;

   }

   return param;

 }//function end.



 public static String getParamValue(String paramName){

//       String configFilePath = XMLConfigFile.getConfigFilePath("paramsconfig.xml");
//
//       if (configFilePath==null){
//
//         return null;
//
//       }
//
//   ParamConfig  pc = new ParamConfig(configFilePath);

//  return pc.getParamValue(paramName);
	 return null;

 }//function end.








} //class end.

