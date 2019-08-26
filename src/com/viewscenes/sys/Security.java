package com.viewscenes.sys;

import java.util.*;
import javax.mail.*;
import javax.servlet.http.*;
import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.DAOOperator;
import com.viewscenes.dao.DAOCondition;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.dao.cache.DAOCacheComponent;



//import ipworks.*;

public class Security {
  private String errMsg = null;
  public Security() {
  }

  public String getLastError() {
    return errMsg;
  }

  public String getItemValue(String name, Vector s) {
    String returnValue = null;
    if (s != null && name != null) {
      String nameItem;
      for (int i = 0; i < s.size() - 1; i += 2) {
        nameItem = (String) s.elementAt(i);
        if (nameItem.equalsIgnoreCase(name)) {
          returnValue = (String) s.elementAt(i + 1);
          break;
        }
      }
    }
    return returnValue;
  }

  public String parseSessionID(String sid) {
    String returnValue = sid;
    if (returnValue != null) {
      int pos = returnValue.indexOf("!");
      if (pos > 0) {
        returnValue = returnValue.substring(0, pos);
      }
    }
    return returnValue;
  }

  /**
   * 明文密码验证
   * @param code
   * @param pass
   * @return
   * @throws Exception
   */
  public String verifyUser(String code, String pass, String sessionID) throws
      Exception {
    String returnValue = "";
    if (code != null && pass != null) {
      sessionID = parseSessionID(sessionID);
      DAOCondition condition = new DAOCondition("sec_user_tab");
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      condition.addCondition("user_code", "VARCHAR", "=", code);
      GDSet result_set = d.Query("user_id,user_password", condition);
      if (result_set.getRowCount() > 0) {

        MD5 m = new MD5();
        String mdPass = m.getMD5ofStr(sessionID +
                                      result_set.getString(0, "user_password"));
        if (mdPass.equals(pass)) {
          returnValue = result_set.getString(0, "user_id");
          errMsg = null;
        }
        else {
          errMsg = "用户名/密码错误";
        }
      }
      else {
        errMsg = "用户名/密码错误";
      }
    }
    return returnValue;
  }

  /**
   * 登陆，md5加密密码
   * @param code
   * @param pass
   * @param sessionID
   * @param ip
   * @return
   * @throws Exception
   */
  public Vector login(String code, String pass, String sessionID, String ip) throws
      Exception {
    Vector returnValue = null;
    if (code != null && pass != null && sessionID != null && ip != null) {
      sessionID = parseSessionID(sessionID);
      DAOCondition condition = new DAOCondition("sec_user_tab");
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      condition.addCondition("user_code", "VARCHAR", "=", code);
      GDSet result_set = d.Query(
          "user_id,user_code,user_password,user_name,sex user_sex", condition);
      if (result_set.getRowCount() > 0) {
        MD5 m = new MD5();
        //System.out.println("radio_pass:"+result_set.getString(0, "user_password")+"sid:"+sessionID);
        String mdPass = m.getMD5ofStr(sessionID +
                                      result_set.getString(0, "user_password"));
        //System.out.println("库中="+mdPass);
        //System.out.println("输入="+pass);
        if (pass.equals(mdPass)) {
          returnValue = new Vector();
          for (int i = 0; i < result_set.getColumnCount(); i++) {
            if (!result_set.getColumnName(i).equalsIgnoreCase("user_password")) {
              returnValue.add(result_set.getColumnName(i));
              returnValue.add(result_set.getString(0, i));
            }
          }
          returnValue.add("user_ip");
          returnValue.add(ip);
          /*
            //get email pass
            DAOCondition scondition = new DAOCondition("sec_imail_tab");
            scondition.addCondition("userid", "VARCHAR", "=", result_set.getString(0, "user_code"));
            GDSet result_set1=d.Query("password",scondition);
            if(result_set1.getRowCount()>0){
            returnValue.add("mail_pass");
            returnValue.add(m.getMD5ofStr(result_set.getString(0,0)));
            }
           */

          String b[] = {
              "user_id", "user_ip"};
          String e[] = {
              result_set.getString(0, "user_id"), ip};
          GDSet set1 = new GDSet("sec_user_tab", b);
          set1.addRow(e);
          d.Update(set1);

          logout(sessionID);

          String curTime = StringTool.Date2String(java.util.Calendar.
                                                  getInstance().
                                                  getTime());

          String b1[] = {
              "session_id", "login_time", "login_ip", "user_id"};
          String e1[] = {
              sessionID, curTime, ip, result_set.getString(0, "user_id")};
          long[] key = new long[1];
          set1 = new GDSet("sec_online_user_tab", b1);
          set1.addRow(e1);
          d.Insert(set1, key);
          errMsg = null;
        }
        else {
          errMsg = "密码错误";
        }
      }
      else {
        errMsg = "用户名错误";
      }
    }
    LogTool.info("applog","用户登录code:"+code+";ip:"+ip);
    return returnValue;
  }

  /**
   * 设置用户密码
   * @param userID
   * @param password
   * @return
   * @throws java.lang.Exception
   */
  public long setPassword(String userID, String password) throws Exception {
    long returnValue = 0;
    if (userID != null && password != null) {
      String b[] = {
          "user_id", "user_password"};
      String e[] = {
          userID, password};
      GDSet set1 = new GDSet("sec_user_tab", b);
      set1.addRow(e);
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      int[] daoReturn = d.Update(set1);
      if (daoReturn[0] > 0) {
        returnValue = daoReturn[0];
        errMsg = null;
      }
      else {
        errMsg = "密码更新失败";
      }
    }
    else {
      errMsg = "用户名/密码错误";
    }
    return returnValue;
  }

  /**
   * 登出
   * @param sessionID
   * @return
   * @throws java.lang.Exception
   */
  public long logout(String sessionID) throws Exception {
    long returnValue = 0;
    if (sessionID != null) {
      sessionID = parseSessionID(sessionID);
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      String b[] = {
          "session_id"};
      String e[] = {
          sessionID};
      GDSet set1 = new GDSet("sec_online_user_tab", b);
      set1.addRow(e);
      d.Delete(set1); //条件删除
    }
    return returnValue;
  }

  /**
   * 踢人
   * @param onlineID
   * @return
   * @throws java.lang.Exception
   */
  public long kickUser(String onlineID) throws Exception {
    long returnValue = 0;
    if (onlineID != null) {
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      String b[] = {
          "online_id"};
      String e[] = {
          onlineID};
      GDSet set1 = new GDSet("sec_online_user_tab", b);
      set1.addRow(e);
      d.Delete(set1); //条件删除
    }
    return returnValue;
  }

  /**
   * 根据在线id查询用户
   * @param onlineID
   * @return
   * @throws java.lang.Exception
   */
  public Vector getUserInfoByOnlineID(String onlineID) throws Exception {
    Vector returnValue = null;
    if (onlineID != null) {
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      DAOCondition condition = new DAOCondition("sec_online_user");
      condition.addCondition("online_id", "VARCHAR", "=", onlineID);
      GDSet result_set = d.Query(
          "user_id,user_code,user_name,sex user_sex,login_ip,session_id,online_id",
          condition);
      if (result_set.getRowCount() > 0) {
        returnValue = new Vector();
        for (int i = 0; i < result_set.getColumnCount(); i++) {
          returnValue.add(result_set.getColumnName(i));
          returnValue.add(result_set.getString(0, i));
        }
        //returnValue.add("user_ip");
        //returnValue.add(ip);
      }
      else {
        errMsg = "未知用户";
      }
    }
    return returnValue;
  }

  /**
   * 根据在线id查询用户
   * @param onlineID
   * @return
   * @throws java.lang.Exception
   */
  public Vector getUserInfoBySessionID(String sessionID) throws Exception {
    Vector returnValue = null;
    if (sessionID != null) {
      sessionID = parseSessionID(sessionID);
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      DAOCondition condition = new DAOCondition("sec_online_user");
      condition.addCondition("session_id", "VARCHAR", "=", sessionID);
      GDSet result_set = d.Query(
          "user_id,user_code,user_name,sex user_sex,login_ip,session_id,online_id",
          condition);
      if (result_set.getRowCount() > 0) {
        returnValue = new Vector();
        for (int i = 0; i < result_set.getColumnCount(); i++) {
          returnValue.add(result_set.getColumnName(i));
          returnValue.add(result_set.getString(0, i));
        }
        //returnValue.add("user_ip");
        //returnValue.add(ip);
      }
      else {
        errMsg = "未知用户";
      }
    }
    return returnValue;
  }

  /**
   * 根据用户id查询用户
   * @param userID
   * @return
   * @throws java.lang.Exception
   */
  public Vector getUserInfo(String userID) throws Exception {
    Vector returnValue = null;
    if (userID != null) {
      DAOCondition condition = new DAOCondition("sec_user_tab");
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      condition.addCondition("user_id", "VARCHAR", "=", userID);
      GDSet result_set = d.Query(
          "user_id,user_code,user_name,sex user_sex,user_ip", condition);
      if (result_set.getRowCount() > 0) {
        returnValue = new Vector();
        for (int i = 0; i < result_set.getColumnCount(); i++) {
          returnValue.add(result_set.getColumnName(i));
          returnValue.add(result_set.getString(0, i));
        }
        //returnValue.add("user_ip");
        //returnValue.add(ip);
        errMsg = null;
      }
      else {
        errMsg = "未知用户";
      }
    }
    return returnValue;
  }

  /**
   * 获取用户最大角色优先级
   * @param userID
   * @return
   * @throws java.lang.Exception
   */
  public long getUserPriority(String userID) throws Exception {
    long returnValue = 1;
    String sql = "select priority from sec_role_tab t,sec_user_role_rel_tab t1 where t1.user_id='"+userID+"' and t.role_id=t1.role_id" ;   
    if (userID != null) {
//      DAOCondition condition = new DAOCondition("sec_role_tab");
//      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
//      DAOCondition scondition = new DAOCondition("sec_user_role_rel_tab");
//      scondition.addCondition("user_id", "VARCHAR", "=", userID);
//      condition.addCondition("role_id", "VARCHAR", "in", scondition.toString());
//      condition.addCondition("priority", "", "order by", "desc");
      GDSet result_set =DbComponent.Query(sql);
      if (result_set.getRowCount() > 0) {
        returnValue = new Long(result_set.getString(0, "priority")).longValue();
      }
    }
    return returnValue;
  }
  /**
   * 获取用户最大角色优先级
   * @param userID
   * @return
   * @throws java.lang.Exception
   */
  public long getUserPriorityByUserName(String user_name) throws Exception {
    long returnValue = 1;
    if (user_name != null) {
    	 String sql = "select t.priority from sec_role_tab t,sec_user_role_rel_tab t1,sec_user_tab tt where tt.user_name='"+user_name+"' and tt.user_id=t1.user_id and t.role_id=t1.role_id " ;   
    	 GDSet result_set =DbComponent.Query(sql);   
      if (result_set.getRowCount() > 0) {
        returnValue = new Long(result_set.getString(0, "priority")).longValue();
      }
    }
    return returnValue;
  }
  /**
   * 获取消息优先级
   * @param userID          用户ID
   * @param messageType     消息类型 0:其他,1:日常,2:临时,3:实时
   * @param actionType      操作类型 1:录音,2:扫频,3:频偏,4:收测
   * @param msgPrio         自定义优先级 0-9
   * @return                消息优先级
   * @throws java.lang.Exception
   */
  public long getMessagePriority(String userID, long messageType,
                                 long actionType, long msgPrio) throws
      Exception {
    long returnValue = 0;
    long rolePrio = getUserPriority(userID);
    long centerType = 0;
    String sql = "select param_value from sys_configuration_tab where param_name='center_type' ";

    GDSet result_set = DbComponent.Query(sql);
    
    if (result_set.getRowCount() > 0) {
      if (result_set.getString(0, "param_value").length() > 0) {
        centerType = new Long(result_set.getString(0, "param_value")).longValue();
      }
    }

    if (msgPrio > 9) {
      msgPrio = 9;
    }
    if (rolePrio > 99) {
      rolePrio = 99;
    }
    if (actionType > 9) {
      actionType = 9;

    }
    returnValue = msgPrio;
    returnValue += rolePrio * 10;

    switch ( (int) messageType) {
      case 0:
        break;
      case 1:
        if (centerType == 0) {
          returnValue += (10 + actionType) * 1000;
        }
        else {
          returnValue += (20 + actionType) * 1000;
        }
        break;
      case 2:
        if (centerType == 0) {
          returnValue += (30 + actionType) * 1000;
        }
        else {
          returnValue += (40 + actionType) * 1000;
        }
        break;
      case 3:
        if (centerType == 0) {
          returnValue += (50) * 1000;
        }
        else {
          returnValue += (51) * 1000;
        }
        break;
    }
    return returnValue;
  }
  /**
   * 获取消息优先级
   * @param userID          用户ID
   * @param messageType     消息类型 0:其他,1:日常,2:临时,3:实时
   * @param actionType      操作类型 1:录音,2:扫频,3:频偏,4:收测
   * @param msgPrio         自定义优先级 0-9
   * @return                消息优先级
   * @throws java.lang.Exception
   */
  public long getMessagePriorityByUserName(String userName, long messageType,
                                 long actionType, long msgPrio) throws
      Exception {
    long returnValue = 0;
    long rolePrio = getUserPriorityByUserName(userName);
    long centerType = 0;
    String sql = "select param_value from sys_configuration_tab where param_name='center_type' ";

    GDSet result_set = DbComponent.Query(sql);
    if (result_set.getRowCount() > 0) {
      if (result_set.getString(0, "param_value").length() > 0) {
        centerType = new Long(result_set.getString(0, "param_value")).longValue();
      }
    }

    if (msgPrio > 9) {
      msgPrio = 9;
    }
    if (rolePrio > 99) {
      rolePrio = 99;
    }
    if (actionType > 9) {
      actionType = 9;

    }
    returnValue = msgPrio;
    returnValue += rolePrio * 10;

    switch ( (int) messageType) {
      case 0:
        break;
      case 1:
        if (centerType == 0) {
          returnValue += (10 + actionType) * 1000;
        }
        else {
          returnValue += (20 + actionType) * 1000;
        }
        break;
      case 2:
        if (centerType == 0) {
          returnValue += (30 + actionType) * 1000;
        }
        else {
          returnValue += (40 + actionType) * 1000;
        }
        break;
      case 3:
        if (centerType == 0) {
          returnValue += (50) * 1000;
        }
        else {
          returnValue += (51) * 1000;
        }
        break;
    }
    return returnValue;
  }
  /**
   * 检查用户是否拥有某角色的权限
   * @param roleID
   * @return
   * @throws java.lang.Exception
   */
  public boolean checkUserHasRole(String userID, String roleID) throws
      Exception {
    boolean returnValue = false;
    if (userID != null && roleID != null) {
      DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
      String strSql =
          "select role_id from  sec_user_role_rel_tab where role_id=" + roleID +
          " and user_id=" + userID;
      GDSet result_set = db.Query(strSql);
      if (result_set.getRowCount() > 0) {
        returnValue = true;
      }
      errMsg = null;
    }
    return returnValue;
  }

  /**
   * 获取授权操作信息
   * @param roleID
   * @return
   * @throws java.lang.Exception
   */
  public GDSet getOperationList(String userID) throws Exception {
    GDSet returnValue = null;
    if (userID != null) {
      DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
      //String strSql = "select a.* from  sec_operation_tab a, SEC_ROLE_OPERATION_REL_TAB b, sec_user_role_rel_tab c where b.role_id = c.role_id and a.operation_id = b.operation_id and c.user_id=" + userID;
      //##begin: modify by zch
      String strSql = "select a.operation_id,a.operation_name,a.operation_description,a.type,a.parent_operation_id,a.description" +
          " from  sec_operation_tab a, SEC_ROLE_OPERATION_REL_TAB b, sec_user_role_rel_tab c" +
          " where b.role_id = c.role_id and a.operation_id = b.operation_id and c.user_id=" +
          userID +
          " group by a.operation_id,a.operation_name,a.operation_description,a.type,a.parent_operation_id,a.description";
      //##end: modify by zch
      returnValue = db.Query(strSql);
      errMsg = null;
    }
    return returnValue;
  }

  /**
   * 返回指定用户对指定操作的权限值
   * @param userID
   * @param operationID
   * @return
   * @throws java.lang.Exception
   */
  public int getOperationLevel(String userID, String operationID) throws
      Exception {
    int returnValue = 0;
    Vector opVector = getOperationInfo(userID);
    if (opVector != null) {
      String levelStr = getItemValue(operationID, opVector);
      if (levelStr != null && levelStr.length() > 0) {
        returnValue = new Integer(levelStr).intValue();
      }
    }
    return returnValue;
  }

  /**
   * 获取授权操作信息
   * @param roleID
   * @return
   * @throws java.lang.Exception
   */
  public Vector getOperationInfo(String userID) throws Exception {
    Vector returnValue = null;
    if (userID != null) {
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);

      //查询用户拥有的角色
      DAOCondition scondition = new DAOCondition("sec_user_role_rel_tab");
      scondition.addCondition("user_id", "VARCHAR", "=", userID);
      //查询符合的最大权限
      DAOCondition condition = new DAOCondition("sec_role_operation");
      condition.addCondition("role_id", "NUMBER", "in", scondition.toString());
      //降序排列,将高级别的派在前面
      condition.addCondition("operation_id,levelvalue", "", "order by", "desc");

      GDSet result_set = d.Query("operation_id,levelvalue", condition);
      String lastOpID = "";
      for (int i = 0; i < result_set.getRowCount(); i++) {
        if (returnValue == null) {
          returnValue = new Vector();
          //只获取最大的一条记录
        }
        if (!lastOpID.equalsIgnoreCase(result_set.getString(i, "operation_id"))) {
          lastOpID = result_set.getString(i, "operation_id");
          for (int j = 0; j < result_set.getColumnCount(); j++) {
            //returnValue.add(result_set.getColumnName(i));
            returnValue.add(result_set.getString(i, j));
          }
        }
      }
      errMsg = null;
    }
    return returnValue;
  }

  /**
   * 根据用户id返回授权的模块url Vector
   * @param userID
   * @return
   * @throws java.lang.Exception
   */
  public Vector getModuleInfo(String userID) throws Exception {
    Vector returnValue = null;
    if (userID != null) {
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      //查询用户拥有的角色
      DAOCondition sucondition = new DAOCondition("sec_user_role_rel_tab");
      sucondition.addCondition("user_id", "VARCHAR", "=", userID);
      //查询符合角色的所有操作
      DAOCondition scondition = new DAOCondition("sec_role_operation_rel_tab");
      scondition.addCondition("role_id", "VARCHAR", "in", sucondition.toString());
      //查询符合操作的所有模块
      DAOCondition condition = new DAOCondition("sec_module_tab");
      condition.addCondition("operation_id", "NUMBER", "in",
                             scondition.toString());
      condition.addCondition("module_url", "", "group by", "");

      GDSet result_set = d.Query("module_url", condition);
      for (int i = 0; i < result_set.getRowCount(); i++) {
        if (returnValue == null) {
          returnValue = new Vector();
        }
        returnValue.add(result_set.getString(i, 0));
      }
      errMsg = null;
    }
    return returnValue;
  }

  /**
   * 检查邮件
   * @param userCode
   * @param mailServer
   * @return
   * @throws java.lang.Exception
   */
  public long checkNewMail(String userCode, String mailServer) throws Exception {
    long returnValue = 0;
    if (userCode != null && mailServer != null) {
      DAOCondition condition = new DAOCondition("sec_imail_tab");
      condition.addCondition("userid", "VARCHAR", "=", userCode);
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      GDSet result_set = d.Query("userid,password", condition);
      if (result_set.getRowCount() > 0) {
        String code = result_set.getString(0, "userid");
        String pass = result_set.getString(0, "password");
        returnValue = checkMailServer(code, pass, mailServer);
      }
    }
    return returnValue;
  }

  public long checkMailServer(String code, String pass, String mailServer) throws
      Exception {
    long returnValue = 0;
    if (code != null && pass != null && mailServer != null) {
      try {
        Store store = null;
        Folder folder = null;
        // -- Get hold of the default session --
        Properties props = System.getProperties();
        Session session = Session.getDefaultInstance(props, null);

        // -- Get hold of a POP3 message store, and connect to it --
        store = session.getStore("imap");
        store.connect(mailServer, code, pass);

        // -- Try to get hold of the default folder --
        folder = store.getDefaultFolder();
        //if (folder == null) throw new Exception("No default folder");
        //System.out.print("\nget default folder:"+folder.getName());

        // -- ...and its INBOX --
        Folder[] allFolders = folder.list();
        //if (folder == null) throw new Exception("No POP3 INBOX");
        for (int i = 0; i < allFolders.length; i++) {
          String name = allFolders[i].getName();
          //System.out.print("\nget sub folder:"+name);
          if (allFolders[i].HOLDS_MESSAGES == Folder.HOLDS_MESSAGES &&
              !name.equals("Public Folders")) {
            allFolders[i].open(Folder.READ_ONLY);
            /*System.out.print(" " + allFolders[i].getMessageCount() + " : " +
               allFolders[i].getNewMessageCount() + " :" +
               allFolders[i].getUnreadMessageCount());*/
            if (allFolders[i].getUnreadMessageCount() > 0) {
              returnValue += allFolders[i].getUnreadMessageCount();
            }
            allFolders[i].close(false);
          }
        }
        store.close();
        System.out.print("\ncheck unread mail using imap4 for " + code + "@" +
                         mailServer + " ,total Unread Mail:" + returnValue +
                         "\n");

        /*
              Pop pop1 = new Pop();
              pop1.setAction(Pop.popDisconnect);
              pop1.setTimeout(10);
              pop1.setMailServer(mailServer);
              pop1.setUser(code);
              pop1.setPassword(pass);
              pop1.setAction(Pop.popConnect);
              returnValue = pop1.getMessageCount();
              pop1.setAction(Pop.popDisconnect);
              System.out.print("Connect Pop server:"+mailServer+" for User:"+code+" ,total Mail:"+returnValue+"\n");
         */
      }
      //catch (IPWorksException ipwe) {
      //System.out.print(ipwe.getMessage());
      //}
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return returnValue;
  }

  public long reloadCache() {
    long returnValue = 0;
    DAOCacheComponent.load();
    return returnValue;
  }

  /**
   * 模块入口检查，检查Sessin是否过期，或者用户是否有权限访问这个模块
   * @param HttpServletRequest 进入模块时的request
   * @param String  该模块的operation_id
   * @return int -1 : 没有登录  0 ：该用户没有权限访问这个模块  level_value：这个用户对应的级别数值
   */
  public int getUserOpLevel(HttpServletRequest request, String operation_id) throws
      Exception {
    //默认为 用户未登陆或身份失效
    int level_value = -1;

    HttpSession session = request.getSession();
    Vector userInfo = (Vector) session.getAttribute("userinfo");
    Vector opInfo = (Vector) session.getAttribute("opinfo");
    if (userInfo != null) {
      String userID = getItemValue("user_id", userInfo);
      request.setAttribute("userid", getItemValue("user_id", userInfo));
      request.setAttribute("username", getItemValue("user_name", userInfo));
      String temp = getItemValue(operation_id, opInfo);
      level_value = 0;
      if (temp != null && !temp.equalsIgnoreCase("")) {
        level_value = new Integer(temp).intValue();
      }
    }
    return level_value;
  }

  /**
   * 操作入口检查，检查用户是否有权限访问这个模块
   * @ param int 当前用户的level_value
   * @ param String  该操作的detail_id
   * @ return boolean true ：通过   false：禁止该操作
   */
  public boolean checkOpDetail(int level_value, String detail_id) throws
      Exception {
    boolean isAuthorized = false;
    if (level_value > 0 && detail_id != null) {
      //获得该操作的level_value
      int operation_lv = getLevelValueByDetailID(detail_id);

      if (operation_lv >= 0) {
        //两者进行比较
        if (level_value >= operation_lv) {
          //通过
          isAuthorized = true;
        }
        else {
          //禁止
          isAuthorized = false;
        }
      }
    }
    return isAuthorized;
  }

  /**
   * 根据detail_id 获取该操作的level_value值
   * @param String detail_id
   * @return int 该操作的level_value值
   * @throws Exception
   */
  public int getLevelValueByDetailID(String detail_id) throws Exception {
    int level_value = -1;
    if (detail_id != null) {
      DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
      DAOCondition condition = new DAOCondition("sec_operation_detail_tab");
      condition.addCondition("detail_id", "number", "=", detail_id);
      GDSet result_set = d.Query("levelvalue", condition);
      if (result_set.getRowCount() > 0) {
        level_value = new Integer(result_set.getString(0, "levelvalue")).
            intValue();
      }
    }
    return level_value;
  }

}
