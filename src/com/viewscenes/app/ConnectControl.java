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
 * ���ſ���
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class ConnectControl    {
  static private Map headInfo = new HashMap(); // ����վ��code��ip��Ӧ��ϵ

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
      if (actionname.equalsIgnoreCase("connect")) { // ����
	return connectHost(request, response);
      }
      else if (actionname.equalsIgnoreCase("check")) { // ���״̬
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
   * ����վ�㲦������
   * @param request ���������host, ����|�����վ��CODE��ע����ܻ����ظ���վ�㣻
   *                              ���磺S57P19|S20P02|S57P19
   *                              ���ⱻѡ�е�վ���п����Ѿ�����
   * @param response
   * @throws ModuleException
   */
  private String connectHost(HttpServletRequest request,
			     HttpServletResponse response) throws
      Exception {
    String redirectURL = "/module/comm/connect_result.jsp";

    String host = request.getParameter("host");
    if (host == null) {
      throw new Exception("δ����host����");
    }

    /**
     * ����HOST
     */
    String newCode = parseSelectSite(host);
    Vector v = getSingleHost(newCode);

    if (headInfo == null) {
      throw new Exception("�޷�����head����");
    }

    /**
     * ����վ��
     */
    boolean flag = false;
    for (int i = 0; i < v.size(); i++) {
      String code = (String) v.get(i);
      String ip = (String) headInfo.get(code);
      if (ip == null) {
	System.out.println("����code�޷��ҵ�վ�㣺" + code);

      }
      try {
	String url = "http://" + ip;
	connect(url);
      }
      catch (Exception ex) { // �д����򷵻ش�����Ϣ
	request.setAttribute("error", ex.toString());
      }
    }
    return redirectURL;
  }

  /**
   * ����ҳ�洫��������ѡ��վ�������ܱ����ȡ��ѡ��ѡ��Ӧ��ȥ���ظ���վ�����
   * @param selectSite ҳ��ѡ�е�վ������ִ�����|�ָ���
   *              ���磺 S57P02|S57P05|S57P02����ʾS57P02��ѡ����ֱ�ȡ��ѡ��ʵ��ֻ��S57P05��ѡ��
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
      else { // ��newCode��ɾ��һ��code������ص�|
	if (newCode.indexOf("|") == -1) { // newCode����һ��code
	  newCode = "";
	}
	else {
	  if (pos == 0) { // ȥ����һ��code
	    String codeA = newCode.substring(0, pos);
	    String codeB = newCode.substring(pos + code.length() + 1,
					     newCode.length()); // +1��Ϊ��ȥ��|
	    newCode = codeA + codeB;
	  }
	  else { // ȥ���м��һ��code
	    String codeA = newCode.substring(0, pos - 1); // -1��Ϊ��ȥ��|
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
   * ����ϵ�HOST������
   * @param host ��|������ִ�
   * @return     ����õ�IP��ַ
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
   * ��ĳ��IP��������
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
   * ��headcode��ip�Ķ�Ӧ��ϵ���浽map��
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
