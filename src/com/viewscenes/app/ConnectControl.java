package com.viewscenes.app;

import java.util.*;


import javax.servlet.http.*;


import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.thirdparty.PostHttpThread;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;



/**
 * 拨号控制
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class ConnectControl    {
  static private Map headInfo = new HashMap(); // 保存站点code与ip对应关系

  public ConnectControl() {
    try {
      getHeadInfo();
    }
    catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception
       {
    String actionname = request.getParameter("actionname");

    if (actionname != null) {
      if (actionname.equalsIgnoreCase("connect")) { // 拨号
	return connectHost(request, response);
      }
      else if (actionname.equalsIgnoreCase("check")) { // 检查状态
	CommServer server = new CommServer();
	try {
	  server.startCheck();
	  return "/module/comm/success.jsp";
	}
	catch (Exception ex) {
	  ex.printStackTrace();
	}
      }
    }

    return "";
  }

  /**
   * 与多个站点拨号链接
   * @param request 输入参数：host, 是以|分离的站点CODE。注意可能会有重复的站点；
   *                              例如：S57P19|S20P02|S57P19
   *                              另外被选中的站点有可能已经在线
   * @param response
   * @throws ModuleException
   */
  private String connectHost(HttpServletRequest request,
			     HttpServletResponse response) throws
      Exception {
    String redirectURL = "/module/comm/connect_result.jsp";

    String host = request.getParameter("host");
    if (host == null) {
      throw new Exception("未输入host参数");
    }

    /**
     * 分离HOST
     */
    String newCode = parseSelectSite(host);
    Vector v = getSingleHost(newCode);

    if (headInfo == null) {
      throw new Exception("无法加载head数据");
    }

    /**
     * 链接站点
     */
    boolean flag = false;
    for (int i = 0; i < v.size(); i++) {
      String code = (String) v.get(i);
      String ip = (String) headInfo.get(code);
      if (ip == null) {
	System.out.println("根据code无法找到站点：" + code);

      }
      try {
	String url = "http://" + ip;
	connect(url);
      }
      catch (Exception ex) { // 有错误则返回错误信息
	request.setAttribute("error", ex.toString());
      }
    }
    return redirectURL;
  }

  /**
   * 由于页面传递来到的选择站点代码可能被多次取消选择、选择，应该去掉重复的站点代码
   * @param selectSite 页面选中的站点代号字串，以|分隔。
   *              例如： S57P02|S57P05|S57P02。表示S57P02被选择后又被取消选择；实际只有S57P05被选择
   * @return
   * @throws ModuleException
   */
  private String parseSelectSite(String selectSite) throws Exception {
    String newCode = "";

    java.util.StringTokenizer st = new StringTokenizer(selectSite, "|");
    while (st.hasMoreTokens()) {
      String code = st.nextToken();
      int pos = newCode.indexOf(code);
      if (pos == -1) {
	if (newCode.equalsIgnoreCase("")) {
	  newCode = code;
	}
	else {
	  newCode = newCode + "|" + code;
	}
      }
      else { // 从newCode中删除一个code，及相关的|
	if (newCode.indexOf("|") == -1) { // newCode仅有一个code
	  newCode = "";
	}
	else {
	  if (pos == 0) { // 去掉第一个code
	    String codeA = newCode.substring(0, pos);
	    String codeB = newCode.substring(pos + code.length() + 1,
					     newCode.length()); // +1是为了去掉|
	    newCode = codeA + codeB;
	  }
	  else { // 去掉中间的一个code
	    String codeA = newCode.substring(0, pos - 1); // -1是为了去掉|
	    String codeB = newCode.substring(pos + code.length(),
					     newCode.length());
	    newCode = codeA + codeB;
	  }
	}
      }
    }
    return newCode;
  }

  /**
   * 将组合的HOST串分离
   * @param host 以|分离的字串
   * @return     分离好的IP地址
   * @throws ModuleException
   */
  private Vector getSingleHost(String host) throws Exception {
    Vector v = new Vector();

    java.util.StringTokenizer st = new StringTokenizer(host, "|");
    while (st.hasMoreTokens()) {
      String single = st.nextToken();
      v.add(single);
    }
    return v;
  }

  /**
   * 与某个IP建立链接
   * @param ip
   * @throws CommException
   */
  public void connect(String url) throws Exception {
//    String url1 = "http://192.168.6.30";
    try {
      new PostHttpThread(url, "");
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception("", ex);
    }
  }

  /**
   * 将headcode与ip的对应关系保存到map中
   */
  private void getHeadInfo() throws Exception {
    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
    try {
      GDSet set = db.Query("select ip, code from res_headend_tab where is_delete=0 ");
      for (int i = 0; i < set.getRowCount(); i++) {
	headInfo.put(set.getString(i, "code"), set.getString(i, "ip"));
      }
    }
    catch (DbException ex) {
      throw new Exception("", ex);
    }
    catch (GDSetException ex) {
      throw new Exception("", ex);
    }
  }

}
