package com.viewscenes.logic.autoupmess2db.MessProcess.v8;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom.Element;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.logic.autoupmess2db.Exception.NoRecordException;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.IUpMsgProcessor;
import com.viewscenes.logic.autoupmess2db.common.Common;
import com.viewscenes.logic.autoupmess2db.common.GDHead;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;

/**
 * 广播接收机控制主动上报接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioReceiverControlReport implements IUpMsgProcessor {
    private String srcCode;
  private String equcode;
  private String headid;
  private String url;
  public RadioReceiverControlReport() {
  }

  public void processUpMsg(Element root) throws SQLException, UpMess2DBException,
      GDSetException, DbException, UtilException, NoRecordException {
	  java.util.Date start = Calendar.getInstance().getTime();
	  this.srcCode = root.getAttributeValue("SrcCode");
	  ArrayList<ReceiverControlReportBean> list = new ArrayList<ReceiverControlReportBean>();
//	  int version = Integer.parseInt(root.getAttributeValue("Version"));
//	  switch (version) {
//	  case 8:
		list = getDataFromRootV8(root);
//		break;
//
//	  default:
//		break;
//	  }
	  
	  data2db(list);

	  java.util.Date end = Calendar.getInstance().getTime();
	  long period = end.getTime() - start.getTime();
	  LogTool.debug("接收机控制主动上报入库时间：" + period);
	  System.out.println("接收机控制主动上报入库时间：" + period);
  }

  /**
   * 得到接收机实时数据
   * RADIO_QUALITY_REALTIME_TAB
   * 02_QualityRealtimeReport.xml
   * @param ArrayList<ReceiverControlReportBean>
   * @throws GDSetException
   * @throws SQLException
   * @throws DbException
   * @throws UpMess2DBException
   */
  private void data2db(ArrayList<ReceiverControlReportBean> list) throws GDSetException,
      SQLException, NoRecordException,
      DbException, UpMess2DBException {
	  if(equcode==null||equcode.equals("")){
      	throw new UpMess2DBException("srcCode="+srcCode+";equcode="+this.equcode+"接收机实时数据上报缺少必要参数:equcode="+this.equcode);
      }
	  if(url==null||url.equals("")){
	      throw new UpMess2DBException("srcCode="+srcCode+";equcode="+this.equcode+"接收机实时数据上报缺少必要参数:url="+this.url);
	  }
    GDSet gd = GDHead.getGdHead("radio_receiver_realtime_tab");
    for (int i = 0; i < list.size(); i++) {
        ReceiverControlReportBean bean = list.get(i);
        String equcode = bean.getEqu_code();
        String url = bean.getUrl();
        String headid = bean.getHead_id();
        
        gd.addRow();
        gd.setString(i, "equ_code", equcode);
        gd.setString(i, "url", url);
        gd.setString(i, "param", bean.getParam());
        gd.setString(i, "check_datetime", bean.getCheck_datetime());
        gd.setString(i, "head_id", headid);

      }
      if(list.size()>0){
  	    // 删除原有所有数据
  	    String delSql = "delete from radio_receiver_realtime_tab where head_id='"+this.headid+"' and equ_code='"+this.equcode+"'";
  	    DbComponent.exeUpdate(delSql);
  	    DbComponent.Insert(gd,null);
      }
  }
  
  /**
   * 将root中的数据解析到ArrayList中(v8版)
   * @param root
   * @return 包含ReceiverControlReportBean的集合
   */
  private ArrayList<ReceiverControlReportBean> getDataFromRootV8(Element root) {
		ArrayList<ReceiverControlReportBean> list = new ArrayList<ReceiverControlReportBean>();
		String headid = SiteVersionUtil.getSiteHeadId(srcCode);
		//获得receivercontrolreport元素
	    Element ele = root.getChild("receivercontrolreport");
		String equcode = ele.getAttributeValue("equcode");  // 获得equcode
		String url = ele.getAttributeValue("url"); 			// 获得url
		String check_datetime = Common.getCurDateTime();    // 获得当前时间
		this.equcode=equcode;
		this.headid = headid;
		this.url = url;
		List list_param = ele.getChildren(); // 获得Param元素
		for (int i = 0; i < list_param.size(); i++) {
			Element sub = (Element) list_param.get(i);
			ReceiverControlReportBean bean = new ReceiverControlReportBean(sub);
			bean.setCheck_datetime(check_datetime);
			bean.setEqu_code(equcode);
			bean.setHead_id(headid);
			bean.setUrl(url);
			list.add(bean);
		}

		return list;
	}

}

/*
 * 接收机实时数据类
 */
class ReceiverControlReportBean
{
	private String equ_code;
	private String url;
	private String param;
	private String check_datetime;
	private String head_id;

	public ReceiverControlReportBean(Element attrs) {
		this.param = attrs.getAttributeValue("value");
	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getCheck_datetime() {
		return check_datetime;
	}

	public void setCheck_datetime(String check_datetime) {
		this.check_datetime = check_datetime;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String head_id) {
		this.head_id = head_id;
	}
	
}