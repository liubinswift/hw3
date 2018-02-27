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
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;

/**
 * 广播指标实时主动上报接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioQualityRealtimeReport implements IUpMsgProcessor {
    private String srcCode;
	private String equcode;
	private String headid;
	private String sitetype = "";
  //private static final String cacheSignPrefix = "quality_real_";

  public RadioQualityRealtimeReport() {
  }

  public void processUpMsg(Element root) throws SQLException,
	UpMess2DBException, GDSetException, DbException, UtilException,
	NoRecordException {
    java.util.Date start = Calendar.getInstance().getTime();
    this.srcCode = root.getAttributeValue("SrcCode");//前端站点code
    this.sitetype = SiteVersionUtil.getSiteType(srcCode);
    ArrayList<QualityRealtimeReportBean> list = new ArrayList<QualityRealtimeReportBean>();
//    int version = Integer.parseInt(root.getAttributeValue("Version"));
//    switch (version) {
//	  case 8:
		list = getDataFromRootV8(root);
//		break;
//
//	  default:
//		break;
//	  }
    data2Db(list);
    java.util.Date end = Calendar.getInstance().getTime();
    long period = end.getTime() - start.getTime();

    System.out.println("指标实时数据上报入库花时：" + period);
  }

  /**
   * *广播实时指标数据上报(radio_quality_realtime_tab)
   * 利用preparedStatement将实时指标数据入库
   * @throws UpMess2DBException
   */
  private void data2Db(ArrayList<QualityRealtimeReportBean> list) throws UpMess2DBException {
	// 实时状态入库sql语句
      String sql =
	  "insert into radio_quality_realtime_tab(realtime_id, equ_code, frequency, " +
	  "check_datetime, value1, value2, type_id,band,description, head_id) values(RADIO_REALTIME_SEQ.nextval, '" +
	  equcode + "', ?, ?, ?, ?, ?,?,?, '" + headid + "')";
      DbComponent db = new DbComponent();
      DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
      prepExeSQL.getConnect();
    try {
        checkData(equcode, "equcode", equcode);
      for (int i = 0; i < list.size(); i++) {
    	  QualityRealtimeReportBean bean = (QualityRealtimeReportBean) list.get(i);
		// 获得checkdatetime
		String checkdatetime = bean.getScandatetime();
		// 获得freq
		String freq = bean.getFreq();
		// 获得band
		String band = bean.getBand();
		// 获得type
        String type = bean.getType();
        String value1 = bean.getValue1();
        String value2 = bean.getValue2();
	    checkData(equcode, "checkdatetime", checkdatetime);
        checkData(equcode, "freq", freq);
        checkData(equcode, "type", type);
		checkData(equcode, "type="+type+",value1", value1);
	    checkData(equcode, ";band", band);
	    if(type.equals("5")){
	        checkData(equcode, "type="+type+";value2", value2);
	    }

		prepExeSQL.setString(1, freq);
		prepExeSQL.setString(2, checkdatetime);
		prepExeSQL.setString(3, value1);
		prepExeSQL.setString(4, value2);
		prepExeSQL.setString(5, type);
		prepExeSQL.setString(6, band);
		prepExeSQL.setString(7, bean.getDesc());
		prepExeSQL.exeSQL();

		}

    }
    catch (Exception ex) {
    	throw new UpMess2DBException("异常：", ex);
    }
    finally{
    	 prepExeSQL.closeConnect();
    }
  }

  /**
   * getDataFromRootV8
   * 将root中的数据解析到ArrayList中(v8版)
   * @param root
   * @return 包含QualityRealtimeReportBean的集合
   */
  public ArrayList<QualityRealtimeReportBean> getDataFromRootV8(Element root) {
	  ArrayList<QualityRealtimeReportBean> list = new ArrayList<QualityRealtimeReportBean>();
	// 获得有关数据
      Element ele = null;
      ele = root.getChild("qualityrealtimereport");
      this.equcode = ele.getAttributeValue("equcode"); // 获得equcode
      this.headid = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
      String versionStr = SiteVersionUtil.getSiteVerStr(root.getAttributeValue("SrcCode"));
      List list_quality = ele.getChildren(); // 获得Quality元素
      for (int i = 0; i < list_quality.size(); i++) {
		Element sub = (Element) list_quality.get(i);
		// 获得checkdatetime
		String checkdatetime = sub.getAttributeValue("checkdatetime");
		// 获得freq
		String freq = sub.getAttributeValue("freq");
		// 获得band
		String band = sub.getAttributeValue("band");

		List sublist = sub.getChildren(); // 获得QualityIndex元素
//		for (int j = 0; j < sublist.size(); j++) {
//		  Element scanResult = (Element) sublist.get(j);
//		  QualityRealtimeReportBean bean = new QualityRealtimeReportBean(scanResult,this.sitetype);
//		  bean.setEqu_code(equcode);
//		  bean.setHead_id(headid);
//		  bean.setFreq(freq);
//		  bean.setScandatetime(checkdatetime);
//		  bean.setBand(band);
//		  list.add(bean);
//		}
                String liyushaX = "";
                String liyushaY = "";
                boolean isV5Type2 = false;//是否是V5的全频段最大值
                String value1V5Type2 = "";
                String descV5Type2 = "";
                for (int j = 0; j < sublist.size(); j++) {
                  Element scanResult = (Element) sublist.get(j);
                  QualityRealtimeReportBean beanV7 = null;
                  if(scanResult.getAttributeValue("type").equals("7")){
                      beanV7=new QualityRealtimeReportBean(scanResult, this.sitetype);
                      if(j==sublist.size()-1){
                          beanV7.setEqu_code(equcode);
                          beanV7.setHead_id(headid);
                          beanV7.setFreq(freq);
                          beanV7.setScandatetime(checkdatetime);
                          beanV7.setBand(band);
                          liyushaX += scanResult.getAttributeValue("x-value");
                          liyushaY += scanResult.getAttributeValue("y-value");
                          beanV7.setValue1(liyushaX);
                          beanV7.setValue2(liyushaY);
                          list.add(beanV7);
                      } else{
                          liyushaX += scanResult.getAttributeValue("x-value")+",";
                          liyushaY += scanResult.getAttributeValue("y-value")+",";
                      }
                  } else{
                      if ("V5".equalsIgnoreCase(versionStr) && 
                              scanResult.getAttributeValue("type").equals("2")) {
                          isV5Type2 = true;
                          descV5Type2 = scanResult.getAttributeValue("desc");
                          value1V5Type2 += scanResult.getAttributeValue("value")+",";
                      } else{
                          QualityRealtimeReportBean bean = new QualityRealtimeReportBean(scanResult,
                                  this.sitetype);
                          bean.setEqu_code(equcode);
                          bean.setHead_id(headid);
                          bean.setFreq(freq);
                          bean.setScandatetime(checkdatetime);
                          bean.setBand(band);
                          list.add(bean);
                      }
                  }
                }
                if (isV5Type2) {
                    if (value1V5Type2.length()>0) {
                        value1V5Type2 = value1V5Type2.substring(0, value1V5Type2.length()-2);
                    }
                    QualityRealtimeReportBean bean = new QualityRealtimeReportBean();
                    bean.setType("2");
                    bean.setValue1(value1V5Type2);
                    bean.setValue2("");
                    bean.setDesc(descV5Type2);
                    bean.setEqu_code(equcode);
                    bean.setHead_id(headid);
                    bean.setFreq(freq);
                    bean.setScandatetime(checkdatetime);
                    bean.setBand(band);
                    list.add(bean);
                }

      }
	  return list;
  }

  /**
   * getDataFromRootFrontier
   * 将root中的数据解析到ArrayList中(边境版)
   * @param root
   * @return 包含QualityRealtimeReportBean的集合
   */
  public ArrayList<QualityRealtimeReportBean> getDataFromRootFrontier(Element root) {
      ArrayList<QualityRealtimeReportBean> list = new ArrayList<QualityRealtimeReportBean>();
    // 获得有关数据
      Element ele = null;
      ele = root.getChild("qualityrealtimereport");
      this.equcode = ele.getAttributeValue("equcode"); // 获得equcode
      this.headid = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
      List list_quality = ele.getChildren(); // 获得Quality元素
      for (int i = 0; i < list_quality.size(); i++) {
        Element sub = (Element) list_quality.get(i);
        // 获得checkdatetime
        String checkdatetime = sub.getAttributeValue("checkdatetime");
        // 获得freq
        String freq = sub.getAttributeValue("freq");
     // 获得band
        String band = "";
            // 获得band
            band = sub.getAttributeValue("band");
        List sublist = sub.getChildren(); // 获得QualityIndex元素
        for (int j = 0; j < sublist.size(); j++) {
          Element scanResult = (Element) sublist.get(j);
          QualityRealtimeReportBean bean = new QualityRealtimeReportBean(scanResult,this.sitetype);
          bean.setEqu_code(equcode);
          bean.setHead_id(headid);
          bean.setFreq(freq);
          bean.setScandatetime(checkdatetime);
          bean.setBand(band);
          list.add(bean);
        }
      }
      return list;
  }

  /**
   * 检查数据完整性 缺少抛出异常
   * @param equcode
   * @param task_id
   * @param data 数据名
   * @param value 数据值
   * @throws UpMess2DBException
   */
  private void checkData(String equcode,String data,String value) throws UpMess2DBException{
      if(value==null||value.equals("")){
          throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode + "时实指标上报数据缺少必要参数:"+data+"="+value);
      }
  }
}

/*
 * 广播实时指标数据类
 */
class QualityRealtimeReportBean
{
	private String equ_code;
	private String head_id;

	private String freq;
	private String scandatetime;
	private String band;

	private String value1;
	private String value2;
	private String type;
	private String desc;
	
	public QualityRealtimeReportBean(){
	    
	}
	/**
	 *
	 * @param attrs
	 * @param siteType 站点类型 101 102 103边境站
	 */
	public QualityRealtimeReportBean(Element attrs,String siteType) {
		this.type = attrs.getAttributeValue("type");
		    this.desc = attrs.getAttributeValue("desc");
		    if (type.equalsIgnoreCase("5")) { // 如果是调制度最大值，取两个值
		          value1 = attrs.getAttributeValue("min-value");
		          value2 = attrs.getAttributeValue("max-value");
	        }
                else if (type.equalsIgnoreCase("7")) { //李育沙图
                        value1 = attrs.getAttributeValue("x-value");
                        value2 = attrs.getAttributeValue("y-value");
                }
	        else { // 其余情况，取一个值
	          value1 = attrs.getAttributeValue("value");
	          value2 = "";
	        }

	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String head_id) {
		this.head_id = head_id;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getScandatetime() {
		return scandatetime;
	}

	public void setScandatetime(String scandatetime) {
		this.scandatetime = scandatetime;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
