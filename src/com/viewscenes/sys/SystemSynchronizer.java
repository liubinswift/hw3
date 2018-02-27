package com.viewscenes.sys;

import java.util.*;
import javax.servlet.http.*;

import org.jdom.*;

import com.viewscenes.util.FileTool;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.XMLConfigFile;


public class SystemSynchronizer
    {

    public static HashMap serverMap = new HashMap();
    static List syncUrlList = null;
    static String localMsgCode = null;
    static String localIP = null;
    static String localName = null;
    static HashMap headUserInfoMap = new HashMap();
    static boolean bCheckHeadUser = false;
    static long checkHeadUserInterval = 5*60*1000;

    static long userValidTime = 60*60*100;

    public SystemSynchronizer() {
    }


    public static synchronized void initServerUrlList() {
        if (syncUrlList == null) {
            try {
                String configFilePath = XMLConfigFile.getConfigFilePath("appserver.xml");
                Element root = FileTool.loadXMLFile(configFilePath);
                Element local = root.getChild("localmsgcode");
                localMsgCode = local.getText();

                local = root.getChild("localip");
                if (local!=null)
                  localIP = local.getText();

                local = root.getChild("localname");
                if (local!=null)
                  localName = local.getText();

                local = root.getChild("onlineuser");
                if (local!=null){
                  String validtime = (String)local.getAttributeValue("validtime");

                  try {
                    userValidTime = Long.parseLong(validtime)*1000;
                  }
                  catch (NumberFormatException ex1) {
                    userValidTime = 60*60*1000;
                  }
                }

                local = root.getChild("checkheaduser");
                if (local!=null){
                  String enable = (String)local.getAttributeValue("enable");
                  String interval = (String)local.getAttributeValue("interval");

                    if ((enable!=null)&&(!enable.equals("0"))){
                      bCheckHeadUser = true;
                    }
                    else{
                      bCheckHeadUser = false;
                    }

                  try {
                    checkHeadUserInterval = Long.parseLong(interval)*1000;
                  }
                  catch (NumberFormatException ex1) {
                    checkHeadUserInterval = 5*60*1000;
                  }
                }

                List l = root.getChildren("server");
                syncUrlList = new LinkedList();
                serverMap = new HashMap();
                if (l != null) {
                    for (int i = 0; i < l.size(); i++) {
                        Element sub = (Element) l.get(i);
                        syncUrlList.add(sub.getAttributeValue("syncurl"));
                        serverMap.put(sub.getAttributeValue("code"), sub.getAttributeValue("url"));
                    }
                }
            }
            catch (Exception ex) {
                LogTool.warning(ex);
            }
        }
    }

    public static boolean getCheckHeadUser() {
        if (syncUrlList == null) {
            initServerUrlList();
        }
        return bCheckHeadUser;
    }

    public static long getCheckHeadUserInterval() {
        if (syncUrlList == null) {
            initServerUrlList();
        }
        return checkHeadUserInterval;
    }

    public static List getServerList() {
        if (syncUrlList == null) {
            initServerUrlList();
        }
        return syncUrlList;
    }

    public static String getLocalMsgCode() {
        if (localMsgCode == null) {
            initServerUrlList();
        }
        return localMsgCode;
    }

    public static String getUrl(String msgcode) {
        return (String) serverMap.get(msgcode);
    }

//    public String process(HttpServletRequest request, HttpServletResponse response) throws com.viewscenes.servlet.module.ModuleException, java.io.IOException, javax.servlet.ServletException {
//        String action = getStrParam(request, "action", "undefined");
//        if (action.equals("reloadcache")) {
//            reloadCache();
//            return null;
//        }
//        else if (action.equals("refreshcache")) {
//            return refreshAllCache(request, response);
//        }
//        else if (action.equalsIgnoreCase("reloadothercache")) {
//            return reloadOtherCache(request, response);
//        }
//        else if (action.equalsIgnoreCase("reloaddata")) {
//            return reloadData(request, response);
//        }
//        else if (action.equalsIgnoreCase("reloaddatamap")) {
//            return reloadDataMap(request, response);
//        }
//
//        else if (action.equalsIgnoreCase("onlineuser")) {
//            return onlineUser(request, response);
//        }
//        else if (action.equalsIgnoreCase("headuser")) {
//            return headUser(request, response);
//        }
//
//        ModuleException e = new ModuleException("模块没有定义动作" + action);
//        throw e;
//    }

//    public String onlineUser(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
//      ArrayList contextList = ControllerServlet.contextManager.getActiveContextList();
//      String serverName = request.getServerName();
//      long curTime = System.currentTimeMillis();
//
//      if (localIP==null){
//        initServerUrlList();
//      }
//
//      String[] columns = {"serverName","role_name","user_name","clientIP","module_name","action_name","login_time","last_access_time"};
//      try {
//        GDSet userInfoSet = new GDSet("userlist", columns);
//        for (int i=0;i<contextList.size();i++){
//          //生成用户列表GDSet
//          ServerContext context = (ServerContext)contextList.get(i);
//          String clientip = context.getClientIP();
//          long accessTime = context.getAccessTime();
//          if (clientip!=null&&!clientip.equals("")&&(curTime-accessTime<userValidTime)){
//            userInfoSet.addRow();
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "serverName", "");
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "role_name",
//                                  context.getRoleName());
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "user_name",
//                                  context.getUserName());
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "clientip",
//                                  context.getClientIP());
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "module_name",
//                                  context.getModuleName());
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "action_name",
//                                  context.getActionName());
//            Date d = new Date();
//            d.setTime(context.getLoginTime());
//            userInfoSet.setString(userInfoSet.getRowCount() - 1, "login_time",
//                                  StringTool.Date2String(d));
//            d.setTime(context.getAccessTime());
//            userInfoSet.setString(userInfoSet.getRowCount() - 1,
//                                  "last_access_time", StringTool.Date2String(d));
//          }
//        }
//      request.setAttribute("userinfo",userInfoSet);
//      }
//      catch (Exception ex) {
//        LogTool.debug(ex);
//      }
//      request.setAttribute("ip",localIP);
//      request.setAttribute("name",localName);
//      try {
//        String centerid = SystemCache.getSysConfig("local_center_id");
////        String centercode = TVServiceAPI.getCenterInfo(centerid,"code");
////        request.setAttribute("centercode", centercode);
//      }
//      catch (Exception ex) {
//        LogTool.debug(ex);
//      }
//      request.setAttribute("datetime",StringTool.Date2String(null));
//
//      return "/onlineuser.jsp";
//    }

//    public String headUser(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
//      request.setAttribute("headusermap",headUserInfoMap);
//
//      request.setAttribute("ip",localIP);
//      request.setAttribute("name",localName);
//      try {
//        String centerid = SystemCache.getSysConfig("local_center_id");
////        String centercode = TVServiceAPI.getCenterInfo(centerid,"code");
////        request.setAttribute("centercode", centercode);
//      }
//      catch (Exception ex) {
//        LogTool.debug(ex);
//      }
//      request.setAttribute("datetime",StringTool.Date2String(null));
//
//      return "/headuser.jsp";
//    }


//    public String refreshAllCache(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
//        if (syncUrlList == null) {
//            initServerUrlList();
//        }
//        for (int i = 0; i < syncUrlList.size(); i++) {
//            String url = (String) syncUrlList.get(i);
//            String text = "&action=reloadcache";
//            new HttpGetThread(url + text);
//        }
//        reloadCache();
//        String message = "刷新系统缓存完毕！";
//        request.setAttribute("message", message);
//        return "/public/alertmsg.jsp";
//    }


    /**
     * 刷新分中心
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ModuleException
     * @return String
     * @version 2004/03/03
     */
    /*
    public String reloadOtherCache(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
        List l = null;
        try {
            String configFilePath = XMLConfigFile.getConfigFilePath("appserver.xml");
            Element root = FileTool.loadXMLFile(configFilePath);
            Element local = root.getChild("localmsgcode");

            local= root.getChild("subserver");
            l=local.getChildren("item");
        }
        catch (UtilException ex) {

        }

        String text = "&action=reloadcache";
        for (int i = 0; i < l.size(); i++) {
            Element  ele = (Element) l.get(i);
            String url=ele.getAttributeValue("url");
            new HttpGetThread(url + text);
        }
        //reloadCache();
        String message = "刷新分中心缓存完毕！";
        request.setAttribute("message", message);
        return "/public/alertmsg.jsp";
    }
*/

//    /**
//     * 刷新分中心
//     *
//     * @param request HttpServletRequest
//     * @param response HttpServletResponse
//     * @throws ModuleException
//     * @return String
//     * @version 2004/03/03
//     */
//    public String reloadOtherCache(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
//
//      String tianjin = getStrParam(request,"tianjin","");
//      String hebei = getStrParam(request,"hebei","");
//      String shanxi = getStrParam(request,"shanxi","");
//      String neimeng = getStrParam(request,"neimeng","");
//      String liaoning = getStrParam(request,"liaoning","");
//      String jilin = getStrParam(request,"jilin","");
//      String heilongjiang = getStrParam(request,"heilongjiang","");
//      String shanghai = getStrParam(request,"shanghai","");
//      String jiangsu = getStrParam(request,"jiangsu","");
//      String zhejiang = getStrParam(request,"zhejiang","");
//
//      String anhui = getStrParam(request,"anhui","");
//      String fujian = getStrParam(request,"fujian","");
//      String jiangxi = getStrParam(request,"jiangxi","");
//      String shandong = getStrParam(request,"shandong","");
//      String henan = getStrParam(request,"henan","");
//      String hubei = getStrParam(request,"hubei","");
//      String hunan = getStrParam(request,"hunan","");
//      String guangdong = getStrParam(request,"guangdong","");
//      String guangxi = getStrParam(request,"guangxi","");
//      String hainan = getStrParam(request,"hainan","");
//
//      String chongqing = getStrParam(request,"chongqing","");
//      String sichuan = getStrParam(request,"sichuan","");
//      String guizhou = getStrParam(request,"guizhou","");
//      String yunnan = getStrParam(request,"yunnan","");
//      String shanxi1 = getStrParam(request,"shanxi1","");
//      String gansu = getStrParam(request,"gansu","");
//      String qinghai = getStrParam(request,"qinghai","");
//      String ningxia = getStrParam(request,"ningxia","");
//      String xinjiang = getStrParam(request,"xinjiang","");
//
//      String text = "&action=reloadcache";
//      if(tianjin!=null){
//        new HttpGetThread(tianjin + text);
//      }
//      if(hebei!=null){
//        new HttpGetThread(hebei + text);
//      }
//      if(shanxi!=null){
//        new HttpGetThread(shanxi + text);
//      }
//      if(neimeng!=null){
//        new HttpGetThread(neimeng + text);
//      }
//      if(liaoning!=null){
//        new HttpGetThread(liaoning + text);
//      }
//      if(jilin!=null){
//        new HttpGetThread(jilin + text);
//      }
//      if(heilongjiang!=null){
//        new HttpGetThread(heilongjiang + text);
//      }
//      if(shanghai!=null){
//        new HttpGetThread(shanghai + text);
//      }
//      if(jiangsu!=null){
//        new HttpGetThread(jiangsu + text);
//      }
//      if(zhejiang!=null){
//        new HttpGetThread(zhejiang + text);
//      }
//      if(anhui!=null){
//        new HttpGetThread(anhui + text);
//      }
//      if(fujian!=null){
//        new HttpGetThread(fujian + text);
//      }
//      if(jiangxi!=null){
//        new HttpGetThread(jiangxi + text);
//      }
//      if(shandong!=null){
//        new HttpGetThread(shandong + text);
//      }
//      if(henan!=null){
//        new HttpGetThread(henan + text);
//      }
//      if(hubei!=null){
//        new HttpGetThread(hubei + text);
//      }
//      if(hunan!=null){
//        new HttpGetThread(hunan + text);
//      }
//      if(guangdong!=null){
//        new HttpGetThread(guangdong + text);
//      }
//      if(guangxi!=null){
//        new HttpGetThread(guangxi + text);
//      }
//      if(hainan!=null){
//        new HttpGetThread(hainan + text);
//      }
//      if(chongqing!=null){
//        new HttpGetThread(chongqing + text);
//      }
//      if(sichuan!=null){
//        new HttpGetThread(sichuan + text);
//      }
//      if(guizhou!=null){
//        new HttpGetThread(guizhou + text);
//      }
//      if(yunnan!=null){
//        new HttpGetThread(yunnan + text);
//      }
//      if(shanxi1!=null){
//        new HttpGetThread(shanxi1 + text);
//      }
//      if(gansu!=null){
//        new HttpGetThread(gansu + text);
//      }
//      if(qinghai!=null){
//        new HttpGetThread(qinghai + text);
//      }
//      if(ningxia!=null){
//        new HttpGetThread(ningxia + text);
//      }
//      if(xinjiang!=null){
//        new HttpGetThread(xinjiang + text);
//      }
//
//
//        String message = "刷新分中心缓存完毕！";
//        request.setAttribute("message", message);
//        return "/public/alertmsg.jsp";
//    }


//    public String reloadData(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
//      String dataName = this.getStrParam(request,"dataname","0");
//      SystemCache.loadData(dataName);
//      return null;
//    }
//
//    public String reloadDataMap(HttpServletRequest request, HttpServletResponse response) throws ModuleException {
//      String dataName = this.getStrParam(request,"dataname","0");
//      SystemCache.loadDataMap(dataName);
//      return null;
//    }
//
//    public static void updateData(String dataName){
//      if (syncUrlList == null) {
//          initServerUrlList();
//      }
//      for (int i = 0; i < syncUrlList.size(); i++) {
//          String url = (String) syncUrlList.get(i);
//          String text = "&action=reloaddata&dataname="+dataName;
//          new HttpGetThread(url + text);
//      }
//    }
//
//    public static void updateDataMap(String dataName){
//      if (syncUrlList == null) {
//          initServerUrlList();
//      }
//      for (int i = 0; i < syncUrlList.size(); i++) {
//          String url = (String) syncUrlList.get(i);
//          String text = "&action=reloaddatamap&dataname="+dataName;
//          new HttpGetThread(url + text);
//      }
//    }
//    public void reloadCache() {
//        try {
//            SystemCache.reLoadCacheData();
////            TVServiceAPI.reInitService();
////            DTVServiceAPI.reInitService();
////            DAOCacheComponent.load();
////            SystemLogin.reInitService();
////            NavigateTree.reInitService();
////            RunPlanFilter.reInitService();
////            com.novel_tongfang.mon.logic.autoupmess2db.common.Common.loadHeadInfo();
////            com.novel_tongfang.mon.logic.autoupmess2db.common.Common.loadAnalogInfo();
////            com.novel_tongfang.mon.logic.autoupmess2db.common.Common.loadRadioInfo();
//        }
////        catch (DbException ex) {
////            LogTool.debug(ex);
////        }
//        catch (Exception ex) {
//            LogTool.debug(ex);
//        }
//    }


//    public static void setHeadUserInfo(String headid, GDSet clientInfo){
//      headUserInfoMap.put(headid,clientInfo);
//    }
//    public static void main(String[] args) {
//        String url = "http://localhost:8000/controller?modulename=synchronizer&action=registmessage&msgid=1&url=http://localhost:8000/xmlReceiver/upload";
//        new HttpGetThread(url);
//    }
}
