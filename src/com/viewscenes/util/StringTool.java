package com.viewscenes.util;



import java.text.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.*;
import org.jdom.output.XMLOutputter;

import com.viewscenes.bean.BaseBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;

import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.translator.ASTranslator;

import java.io.*;

/**

 *

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class StringTool {

  public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public final static String DATE_FORMAT2 = "yyyyMMddHHmmss";
  public final static String TIME_FORMAT = "HH:mm:ss";

  public StringTool() {

  }


	/**
	 * 为SQL增加分页功能
	 * 
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public static String pageSql(String sql, int start, int end) {
		String s = "select * from (select rownum ora_rc,tab.* from (" + sql
				+ ") tab )" + " where ora_rc between " + start + " and " + end;
		return s;
	}
	/**
     * 将GDSet装换为对应的bean集合
     * @detail  
     * @method  
     * @param newgd
     * @return
     * @throws Exception 
     * @return  ArrayList  
     * @author  zhaoyahui
     * @version 2013-1-11 上午11:10:42
     */
	public static ArrayList converGDSetToBeanList(GDSet newgd,String classPath) throws Exception{
		ArrayList list = new ArrayList();
		ArrayList resList = new ArrayList();
		try {
			
			int colCount = newgd.getColumnCount();
			String[] colArr = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				colArr[i] = newgd.getColumnName(i);
			}

			int rowCount = newgd.getRowCount();
			for (int j = 0; j < rowCount; j++) {
				ASObject rowObj = new ASObject();
				for (int k = 0; k < colArr.length; k++) {
					rowObj.put(colArr[k], newgd.getString(j, colArr[k]));
				}
				list.add(rowObj);
			}
			
			resList = StringTool.convertFlexToJavaList(list, classPath);
			if(resList == null){
				return new ArrayList();
			}
		} catch (Exception e) {
			LogTool.fatal(e);
			throw new Exception("查询结果转化bean异常");
		}
		
		return resList;
	}
	/**
	 * 返回不分页时查询的总数
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public static String pageSqlCount(String sql) {
		String s = "select count(*) count from ("+sql+") tab ";
		return s;
	}

	/**
	 * 分页查询
	 * @detail  
	 * @method  
	 * @param sql 要执行的sql语句
	 * @param obj 对应flex传过来的object 或bean
	 * @return 
	 * @return  List  包含2个元素 resultList：查询结果的列表，里面存放ASObject，键为列名
	 * 							  resultTotal： 查询的全部总数
	 * @author  zhaoyahui
	 * @version 2012-7-30 上午09:41:10
	 */
	public static ASObject pageQuerySql(String sql,Object obj) throws Exception{
		int startRow = 0;
		int endRow = 0;
		if(obj instanceof BaseBean){
			BaseBean objnew1 = (BaseBean)obj;
			startRow = objnew1.getStartRow();
			endRow = objnew1.getEndRow();
		} else if(obj instanceof ASObject){
			ASObject objnew2 = (ASObject)obj;
			startRow = (Integer)objnew2.get("startRow");
			endRow = (Integer)objnew2.get("endRow");
		}
		String sqlPage = StringTool.pageSql(sql, startRow, endRow);
		String sqlPageTotal = StringTool.pageSqlCount(sql);
		ASObject objRes = new ASObject();
		ArrayList list = new ArrayList();
		GDSet pageGD = DbComponent.Query(sqlPage);
		GDSet coutn = DbComponent.Query(sqlPageTotal);
		
		int colCount = pageGD.getColumnCount();
	    String[] colArr = new String[colCount];
	    for (int i = 0; i < colCount; i++) {
	        colArr[i] = pageGD.getColumnName(i);
	    }
	    
	    int rowCount = pageGD.getRowCount();
	    for (int j = 0; j < rowCount; j++) {
	        ASObject rowObj = new ASObject();
	        for (int k = 0; k < colArr.length; k++) {
	            rowObj.put(colArr[k], pageGD.getString(j,colArr[k]));
	        }
	        list.add(rowObj);
	    }

		objRes.put("resultList", list);
		objRes.put("resultTotal", coutn.getString(0, "count"));
		return  objRes;
	}
  /**

   * 按照分隔符将字符串切分为字符串数组

   * 如果输入字符串为null或"",返回长度为0的字符串数组

   * @param str 源字符串

   * @param token 分隔符

   * @return 字符串数组

   */

  public static String[] parseTokenString(String str, String token) {

    if ( (str == null) || (str.length() == 0)) {

      return new String[0];

    }

    StringTokenizer tk = new StringTokenizer(str, token);

    String[] strArray = new String[tk.countTokens()];

    int count = 0;

    while (tk.hasMoreTokens()) {

      strArray[count] = tk.nextToken().trim();

      count++;

    }

    return strArray;

  }



  /**

   * 说明: 以后可以使用工具类中的对应函数

   * 把Date的时间格式转换成字符串形式

   * @param d Date时间格式(null表示当天时间)

   * @return 时间字串('YYYY-MM-DD HH:MM:SS')

   * @throws UtilException 若格式化失败抛出异常

   */

  public static String Date2String(java.util.Date d) {

    if (d == null) {

      d = new Date();

    }

    SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

    String s = df.format(d);

    return s;

  }

  public static String date2String(Date strDate) throws Exception {
	    if(strDate==null){
	      strDate = new Date();
	    }
	    String dateStr = null;
	    /**
	     * 先要验证时间格式正确。以后再扩充。
	     */
	    try {
	      SimpleDateFormat d = new SimpleDateFormat(DATE_FORMAT);
	      dateStr = d.format(strDate);
	    }
	    catch (Exception ex) {
	      throw new Exception("时间转字符格式有误：" + ex.getMessage(), ex);
	    }
	    return dateStr;
	 }

  /**

   * 说明: 以后可以使用工具类中的对应函数

   * 把Date的时间格式转换成字符串形式

   * @param d Date时间格式(null表示当天时间)

   * @return 时间字串('HH:MM:SS')

   * @throws UtilException

   */

  public static String Time2String(java.util.Date d) throws UtilException {

    if (d == null) {

      d = new Date();

    }

    SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);

    String s = df.format(d);

    return s;

  }


  /**
           * 将给定的字符串解析，并返回根节点
           * @param msg
           * @return
           */
public static Element getXMLRoot(String msg) {

                  Element root = null;

                  try {

                          StringReader read = new StringReader(msg);
                        
                          org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);

                          Document doc = builder.build(read);

                          root = doc.getRootElement();

                      } catch (JDOMException ex) {

                          ex.printStackTrace();

                      }

                      return root;

}



  /**

   * 将String转为Date

   * @param strDate 时间字符串

   * @return 日期

   */

  public static Date stringToDate(String strDate) {

    try {

      SimpleDateFormat d = new SimpleDateFormat(DATE_FORMAT);

      ParsePosition pos = new ParsePosition(0);

      Date currentTime = d.parse(strDate, pos);

      return currentTime;

    }

    catch (Exception e) {

      e.printStackTrace();

    }

    return null;

  }

  /**

   * 将String转为Date

   * @param strDate 时间字符串

   * @return 日期

   */

  public static String stringToStringDate(String strDate) {

    try {

      SimpleDateFormat d = new SimpleDateFormat(DATE_FORMAT2);

      ParsePosition pos = new ParsePosition(0);

      Date currentTime = d.parse(strDate, pos);

      return StringTool.date2String(currentTime);

    }

    catch (Exception e) {

      e.printStackTrace();

    }

    return null;

  }

  /**

   * 将原字符串中的子串替换为新的子串

   * 方法返回新字符串，不改变原字符串

   * @param src 原字符串

   * @param oldStr 待替换的字符串

   * @param newStr 替换的字符串

   * @return 目标字符串

   */

  public static String replace(String src, String oldStr, String newStr){

    if ((src==null)||(oldStr==null)||(newStr==null)) return "";

    StringBuffer buffer = new StringBuffer();

    int indexStart = 0;

    int indexEnd = src.indexOf(oldStr);

    while (indexEnd!=-1){

      buffer.append(src.substring(indexStart,indexEnd));

      buffer.append(newStr);

      indexStart = indexEnd+oldStr.length();

      indexEnd = src.indexOf(oldStr,indexStart);

    }

    buffer.append(src.substring(indexStart));

    return buffer.toString();

  }

  /**

   * 将字符串格式化为可在Javascript中用alert函数显示的格式

   * @param msg 原字符串

   * @return 格式化后的字符串

   */

  public static String formatAlertMsg(String msg){

    msg = msg.replace('\r',' ');

    msg = msg.replace('\n',' ');

    return msg;

  }
  /**
   * 字符串转换为整数
   * @param str String
   * @return int
   */
  public static int stringToInt(String str){

      if(isNumeric(str)){
          return Integer.parseInt(str);
      }

      return 0;

  }

  /**
   * 判断字符串是否未数字字符串
   * @param str String
   * @return boolean
   */
  public static boolean isNumeric(String str){

      if ("".equals(str)){

          return false;
      }
      if(str.indexOf(".")>-1) str=str.replace(".", "");
      Pattern pattern = Pattern.compile("[0-9]*");

      Matcher isNum = pattern.matcher(str);

      if(!isNum.matches()){
          return false;
      }

      return true;
  }
  public static Document getXmlMsg(){
      Element root = new Element("Msg");
      root.addAttribute("return", "1");
      Document doc = new Document(root);
      return doc;
  }
  /**
   * ChangCode
   * 转换中心号码
   * 作用：转换中心代码
   * 例如：11000N01转换为：11000N00001
   */
  public static String ChangCode(String scode){

	  int position= scode.lastIndexOf("N");

	  String tmp_s1= scode.substring(0,position+1);

	  String tmp_s2=scode.substring(position+1,scode.length());

	  tmp_s1=tmp_s1+"000";

	  scode=tmp_s1+tmp_s2;

	  return scode;

  }

  /**
          * 为SQL增加分页功能
          * @param sql
          * @param start
          * @param end
          * @return
          */
  public static String processSql(String sql, int start, int end) {
      String s = "select * from (select rownum ora_rc,tab.* from ("
                 + sql + ") tab )"
                 + " where ora_rc between " + start + " and " + end;
      return s;
  }
    /**
     * 返回分页sql和带总数element节点
     * @param root 包含开始、终止行的xml根节点
     * @param sql 要查询的sql
     * @return ArrayList 包含2个元素，第一个分页的sql语句，第二个带总数的element节点
     * @throws Exception
     */
    public static ArrayList<Object> pageQuerySql(Element root,String sql) throws Exception{
        String startRow = root.getChild("startRow").getText(); //开始取数据的行数,如果是第一次，则值为1
        String endRow = root.getChild("endRow").getText();
        int start = Integer.parseInt(startRow);
        int end = Integer.parseInt(endRow);
        ArrayList<Object> reslist = new ArrayList<Object>();
        ArrayList<Element> list = new ArrayList<Element>();
        reslist.add(StringTool.processSql(sql, start, end));

        String countSql = "select count(*) as count from ("+sql+")";
        GDSet coutn = DbComponent.Query(countSql);

        String count = coutn.getString(0, "count");
        Element ele_count = new Element("totalCount");
        ele_count.addAttribute("count", count);

        reslist.add(ele_count);
        return reslist;
    }
    
/**
 * ************************************************

* @Title: convertFlexToJavaList

* @Description: TODO(前台javabean传到后台需要转换方法才能使用。)

* @param @param arrayCollection
* @param @param classPath
* @param @return    设定文件

* @author  刘斌

* @return List    返回类型

* @throws

************************************************
 */
    public static ArrayList convertFlexToJavaList(ArrayList arrayCollection, String classPath){ 
    	ArrayList dataList = null; 
    try { 
    if (arrayCollection == null || classPath == null || classPath.length() == 0) { 
    return null; 
    } 
    dataList = new ArrayList(); 
    ASObject asObject = null; 
    ASTranslator asTranslator = null; 
    Class cls = Class.forName(classPath); //反射，可以转换成任意一个JAVA BEAN 

    for(int i=0; i<arrayCollection.size(); i++){ 
    asObject = (ASObject) arrayCollection.get(i); 
    asObject.setType(classPath); 
    asTranslator = new ASTranslator(); 

    dataList.add(asTranslator.convert(asObject, cls.getClass())); 
    } 
    } catch (Exception e) { 
    e.printStackTrace(); 
    } 
    return dataList; 
    } 


  /**
   * 根据传入list封装为返回xml字符串
   * @author zhaoyahui
   * @date 2009/12/01
   * @param list 包含Element的ArrayList
   * @return 传到前台字符串
   */
  public static String getXmlStrFromList(ArrayList<Element> list){
	  Document doc = StringTool.getXmlMsg();
      Element rootNode = doc.getRootElement();
      rootNode.setChildren(list);

      XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
      StringWriter sw = new StringWriter();

      try {

          out.output(doc, sw);

      } catch (IOException ex2) {
          ex2.printStackTrace();
          return getXmlErrorMessage("error",ex2.getMessage());
      }

	  return sw.toString();
  }

  /**
   * 返回报错xml字符串
   * @author zhaoyahui
   * @date 2009/12/01
   * @param message 报错信息
   * @return 传到前台字符串
   *
   */
  public static String getXmlErrorMessage(String eleStr,String message){
	  Document doc = StringTool.getXmlMsg();
      Element rootNode = doc.getRootElement();
      ArrayList<Element> list = new ArrayList<Element>();
      Element errorNode = new Element(eleStr);//ErrorMessage error
      errorNode.addAttribute(eleStr,message);
      list.add(errorNode);
      rootNode.setChildren(list);
	  XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

      StringWriter sw = new StringWriter();

      try {

          out.output(doc, sw);

      } catch (IOException ex2) {

          ex2.printStackTrace();
      }

	  return sw.toString();
  }

  /**
   * 返回报错xml字符串
   * @author zhaoyahui
   * @date 2009/12/01
   * @param message 报错信息
   * @return 传到前台字符串
   *
   */
  public static String getXmlErrorMessage(String message){
	  Document doc = StringTool.getXmlMsg();
      Element rootNode = doc.getRootElement();
      ArrayList<Element> list = new ArrayList<Element>();
      Element errorNode = new Element("error");//ErrorMessage error
      errorNode.setText(message);
      list.add(errorNode);
      rootNode.setChildren(list);
	  XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

      StringWriter sw = new StringWriter();

      try {

          out.output(doc, sw);

      } catch (IOException ex2) {

          ex2.printStackTrace();
      }

	  return sw.toString();
  }
  /**
   * 返回操作成功的字符串信息
   * @author 王福祥
   * @date 2010/05/10
   * @param message
   * @return
   */
  public static String getXmlRightMessage(String message){
	  Document doc = StringTool.getXmlMsg();
      Element rootNode = doc.getRootElement();
      ArrayList<Element> list = new ArrayList<Element>();
      Element rightNode = new Element("right");
      rightNode.setText(message);
      list.add(rightNode);
      rootNode.setChildren(list);
	  XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

      StringWriter sw = new StringWriter();

      try {

          out.output(doc, sw);

      } catch (IOException ex2) {

          ex2.printStackTrace();
      }

	  return sw.toString();
  }
  
  /**
  * 返回操作成功xml字符串
  * @author 刘斌
  * @date 2009/12/01
  * @param message 操作成功信息
  * @return 传到前台字符串
  *
  */
 public static String getXmlRightMessage(){
     Document doc = StringTool.getXmlMsg();
         XMLOutputter out = new XMLOutputter();
         out.setEncoding("GB2312");
         StringWriter sw = new StringWriter();
        try {
            out.output(doc, sw);
        } catch (IOException ex) {
        }
        return sw.toString();

 }
 /**
  * 从查询结果中拼接返回到前台的xml字符串
  * @author zhaoyahui
  * @date 2010/02/04
  * @param GDSet 查询结果
  * @return 传到前台字符串
  */
 public static String getXmlFromGdset(GDSet gd) throws Exception{
     ArrayList<Element> list=new ArrayList<Element>();
    int colCount = gd.getColumnCount();
    String[] colArr = new String[colCount];
    list = new ArrayList<Element>();
    for (int i = 0; i < colCount; i++) {
        colArr[i] = gd.getColumnName(i);
    }
    
    int rowCount = gd.getRowCount();
    for (int j = 0; j < rowCount; j++) {
        Element ele = new Element("info");
        for (int k = 0; k < colArr.length; k++) {
            ele.addAttribute("res_" + colArr[k], gd.getString(j,colArr[k]));
        }
        list.add(ele);
    }
    return StringTool.getXmlStrFromList(list);
 }
 
 /**
  * 从查询结果中拼接xml的Element对象
  * @author zhaoyahui
  * @date 2010/02/04
  * @param GDSet 查询结果
  * @param eleTag 返回element对象名 
  * @return Element对象
  */
 public static Element getElementFromGdset(GDSet gd,String eleTag) throws Exception{
    int colCount = gd.getColumnCount();
    String[] colArr = new String[colCount];
    for (int i = 0; i < colCount; i++) {
        colArr[i] = gd.getColumnName(i);
    }
    
    int rowCount = gd.getRowCount();
    Element element = new Element(eleTag);
    for (int j = 0; j < rowCount; j++) {
        Element ele = new Element("info");
        for (int k = 0; k < colArr.length; k++) {
            ele.addAttribute("res_" + colArr[k], gd.getString(j,colArr[k]));
        }
        element.addContent(ele);
    }
    return element;
 }
 
 /**
  * 获取消息字符串
  * @param a
  * @return
  */
 public static String getTimeString(int a) {
   String returnValue = "";
   int h, m, s;
   s = a % 60;
   m = a / 60;
   h = m / 60;
   m = m % 60;
   h = h % 24;

   //showLog(a+" - "+h+":"+m+":"+s);

   if (h < 10) {
     returnValue += "0" + h + ":";
   }
   else {
     returnValue += h + ":";
   }
   if (m < 10) {
     returnValue += "0" + m + ":";
   }
   else {
     returnValue += m + ":";
   }
   if (s < 10) {
     returnValue += "0" + s;
   }
   else {
     returnValue += s;

   }
   return returnValue;
}
 /**
  * 王福祥
  * 比较两个时间差以小时为单位保留3为小数
  * @param time1
  * @param time2
  * @return
  */
 public static String compareTime(String time1,String time2){
	 String hours ="";
	 DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	 DecimalFormat format = new DecimalFormat("#0.000");
	 try {
		Date date1 = df.parse(time1);
		Date date2 = df.parse(time2);
		double d=(date2.getTime()-date1.getTime());//转换成毫秒数
		String s=format.format(d/1000);//换算成秒保留3位有效小数
		double d1=Double.parseDouble(s);//再将String转换成Double类型
		hours = format.format(d1/3600);//秒换算成小时保留3位有效小数。
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return hours;
 }
 
 /**
  * 从配置表中获得center_id
  */
 public static String getCenter_id(){
	String center_id="";   
	String sql="select param_value as center_id from sys_configuration_tab t where  t.param_name='local_center_id'";
	GDSet gd=null;
	try {
		gd = DbComponent.Query(sql);
		center_id=gd.getString(0, "center_id");
	} catch (Exception e) {
		e.printStackTrace();
	}
	 return center_id;  
 }
 
 
 /**
	 * 根据给定的格式为(yyyy-mm-dd)日期计算前一天的时间
	 * @param date
	 * @return
	 */
	public static String getLastDate(String date){
		String yesterday="";
		
		int year =0; 
		
		int month =0;
		
		int day = 0;
		
		year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		
		month = Integer.parseInt(date.substring(date.indexOf("-")+1, date.lastIndexOf("-")));
		
		day = Integer.parseInt(date.substring(date.lastIndexOf("-")+1));
		
		day = day-1;
		if(day==0){
			month=month-1;
			if(month==0){
				month=12;
				day=31;
				year=year-1;
			}
			else{
				switch(month){
				  case 1:
					day=31;
					break;
				  case 3:
					day=31;
					break;
				  case 5:
					day=31;
					break;
				  case 7:
					day=31;
					break;
				  case 8:
					day=31;
					break;
				  case 10:
					day=31;
					break;
				  case 12:
					day=31;
					break;
				  case 4:
					day=30;
					break;
				  case 6:
					day=30;
					break;
				  case 9:
					day=30;
					break;
				  case 11:
					day=30;
					break;
				  case 2:
					if(year%4==0&&year%100!=0||year%400==0){
						day=29;
					}
					else day=28;
				}
			}
		}
		String s_month="";
		String s_day="";
		if(month<10) s_month="0"+String.valueOf(month);
		else s_month=String.valueOf(month);
		if(day<10) s_day="0"+String.valueOf(day);
		else s_day=String.valueOf(day);
		yesterday=String.valueOf(year)+"-"+s_month+"-"+s_day;
		return yesterday;
	}
	
	
	/**
	 * 从URL地址中解析出IP地址和端口号
	 * 返回格式：192.168.100.1:8080
	 * <p>class/function:com.viewscenes.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-27
	 * @param:
	 * @return:
	 */
	public static String getIpAndPortByUrl(String url){
		if (url == null || url.equals(""))
			return url;
		Pattern p1  = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)");
		Matcher matcher1 = p1.matcher(url);
		matcher1.find();
		return matcher1.group(0);
	}
	
	/**
	 * 把时间转换成录音文件的时间格式,如2012-07-27 15:55:08生成20120727155508
	 * <p>class/function:com.viewscenes.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-27
	 * @param:
	 * @return:
	 */
	public static String generateRecordDateTimeByDate(Date date){
		if (date ==null)
			date = new Date();
		String sDate = Date2String(date);
		sDate = sDate.replaceAll(" |:|-", "");
		return sDate;
	}
	
	
	public static Date addHours(int hours){
		return addHours(new Date(),hours);
	}
	
	public static Date addHours(Date d,int hours){
		if (d == null)
			d = new Date();
		long l = d.getTime();
		l = l + hours * 60 * 60 * 1000;
		d.setTime(l);
		return d;
	}
	/**
	 * 加小时方法
	 * <p>class/function:com.viewscenes.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-21
	 * @param:
	 * @return:
	 */
	public static Date addHours(String date,int hours){
		Date d = stringToDate(date);
		return addHours(d,hours);
	}
	
	/**
	 * 将20120727155508转换成2012-07-27 15:55:08
	 * <p>class/function:com.viewscenes.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-23
	 * @param:
	 * @return:
	 */
	public static String convertString2DateStr(String date){
		if (date ==null)
			return date;
		date = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12);
		return date;
	}
	
	
	public static void main(String[] args) throws Exception{
		String path="d:\\video_location_down";
	
		Calendar cal=Calendar.getInstance();//使用日历类
		  int year=cal.get(Calendar.YEAR);//得到年
		  int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		  int day=cal.get(Calendar.DAY_OF_MONTH);//得到天

		   File file=new File(path);
		   if(!file.exists())
			   file.mkdir();
		   path=path+"\\"+year;
		   file=new File(path);
		   if(!file.exists())
			   file.mkdir();
		   path=path+"\\"+month;
		   file=new File(path);
		   if(!file.exists())
			   file.mkdir();
		   path=path+"\\"+day;
		   file=new File(path);
		   if(!file.exists())
			   file.mkdir();
	
			   
	}
	
	public static String setDefaultValue(String value){
		  if (value == null)
			  return "";
		  else
			  return value;
	 }

}
