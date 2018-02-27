/* Generated by Together */



package com.viewscenes.device.util;



import java.net.*;

import java.text.*;

import java.util.*;



import org.jdom.*;

import org.jdom.input.*;

import com.viewscenes.util.*;
import com.viewscenes.sys.SystemConfig;



/**

 * 消息实用工具

 * @version 1.0

 */

public class MsgUtil {



  /**

   * 得到唯一的消息ID

   * @return 当前可以使用的消息ID

   */

  public static synchronized long getCurMsgID() {
    currMsgID++;
    if (currMsgID>999999)
      currMsgID = 0;
    return serverCode+currMsgID;
  }



  /**

   * 将当前时间转换为"yyyy-MM-dd HH:mm:ss"字符串用于"DateTime"缺省值

   * @return 当前时间的"yyyy-MM-dd HH:mm:ss"字符串形式

   */

  public static String getCurDateTime() {

    return dateFormatter.format(Calendar.getInstance().getTime());

  }



  /**

   * 获得本地中心编码

   * @return 本地中心编码，即消息的SrcCode

   */

  public static String getSrcCode() {

    return srcCode;

  }



  /**

   * 获得消息与超时的对应关系

   * @param msgName 消息名称

   * @return 超时时间，单位为秒

   */

  public static int getMsgTimedOut(String msgName) {

    Object time = msgTimedOut.get(msgName);

    if (time == null) {

      time = msgTimedOut.get("default");

    }

    return Integer.parseInt(time.toString());

  }



  /**

   * 外部的类调用该函数获得自己的配置项

   * @param itemName 自己配置项元素的名称

   * @return 配置项元素，不为null

   */

  public static Element getConfigItem(String itemName) {

    Element e = root.getChild(itemName);

    //assert e != null:itemName + "没有在配置文件中配置";

    return e;

  }



  public static String getFwURL(String dstCode){

    return (String)fwURLs.get(dstCode);

  }



  private MsgUtil() {}



  private static long currMsgID;
  private static long serverCode;

  private static void makeCurrMsgID() {

    try {
      String msgcode =
      com.viewscenes.sys.SystemSynchronizer.getLocalMsgCode();
      serverCode = Long.parseLong(msgcode+"000000");
    }
    catch (NumberFormatException ex) {
      serverCode = 10000000;
    }
    currMsgID = System.currentTimeMillis() & 0x0000ffff;
  }

  private static String srcCode;

  private static void makeSrcCode() {

    srcCode = getConfigItem("center").getAttributeValue("srccode");

  }
  static String proxyCode;
  static boolean bUseProxy;
  static HashMap proxyMsgTypeMap;
  private static void makeProxy() {

    Element proxy = getConfigItem("proxy");
    if (proxy==null){
      proxyCode = "";
      bUseProxy = false;
      proxyMsgTypeMap = new HashMap();
      return;
    }
    proxyCode = proxy.getAttributeValue("code");
    String sProxy = proxy.getAttributeValue("enable");
    if (sProxy==null){
      bUseProxy = false;
      return;
    }
    if (sProxy.equalsIgnoreCase("true")){
      bUseProxy = true;
    }
    List typeList = getConfigItem("proxy").getChildren("messagetype");
    proxyMsgTypeMap = new HashMap();
    for (int i=0;i<typeList.size();i++){
      Element type = (Element)typeList.get(i);
      proxyMsgTypeMap.put(type.getText(),type.getText());
    }
  }

  public static String getProxyCode(){
    return proxyCode;
  }

  public static boolean useProxy(){
    return bUseProxy;
  }

  public static boolean useProxy(String msgType){
    if (!bUseProxy)
      return false;
    String type = (String)proxyMsgTypeMap.get(msgType);
    if (type!=null)
      return true;
    else
      return false;
  }

  private static Map msgTimedOut;

  private static void makeMsgTimedOut() {

    List children = getConfigItem("msgtimedout").getChildren();

    msgTimedOut = new HashMap(children.size());

    for (Iterator it = children.iterator(); it.hasNext(); ) {

      Element child = (Element) it.next();

      msgTimedOut.put(child.getName(), child.getText());

    }

    if (!msgTimedOut.containsKey("default")) {

      msgTimedOut.put("default", "30");

    }

  }



  private static Map fwURLs;

  private static void makeFwURLs(){

    List children = MsgUtil.getConfigItem("msgfw").getChildren();

    fwURLs = new HashMap(children.size());

    for (Iterator it = children.iterator(); it.hasNext(); ) {

      Element child = (Element) it.next();

      fwURLs.put(child.getAttributeValue("code"), child.getAttributeValue("url"));

    }

  }




  private static Element root;

  private static SimpleDateFormat dateFormatter;

  static {

    try {

      URL configFile = MsgUtil.class.getClassLoader().getResource("deviceconfig.xml");

      root = new SAXBuilder(false).build(configFile).getRootElement();

    } catch (Exception ex) {

      LogTool.fatal("devicelog", ex + "加载配置文件出错"); //for runtime

    }

    makeSrcCode();

    makeProxy();

    makeMsgTimedOut();

    makeCurrMsgID();

    makeFwURLs();

    dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SystemConfig.localSystemCode = MsgUtil.getSrcCode();
  }

}
