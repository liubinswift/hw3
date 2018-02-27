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
import com.viewscenes.util.LogTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;

/**
 * 广播频谱实时主动上报接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioSpectrumRealtimeReport implements IUpMsgProcessor {
    private String srcCode;
	private String equcode;
	private String headid;
	private String band;
    private String sitetype;//101 102 103边境站

    public RadioSpectrumRealtimeReport() {
    }

   /**
   * 将xml数据存入数据库
   * @throws SQLException
   * @throws UpMess2DBException
   * @throws GDSetException
   * @throws DbException
   * @throws UtilException
   * @throws NoRecordException
   */
    public void processUpMsg(Element root) throws SQLException,
            UpMess2DBException, GDSetException, DbException, UtilException,
            NoRecordException {
        java.util.Date start = Calendar.getInstance().getTime();
        this.srcCode = root.getAttributeValue("SrcCode");// 前端站点code
        this.headid = SiteVersionUtil.getSiteHeadId(srcCode);
        this.sitetype = SiteVersionUtil.getSiteType(srcCode);
        ArrayList<SpectrumRealtimeReportBean> list = new ArrayList<SpectrumRealtimeReportBean>();
            // int version =
            // Integer.parseInt(root.getAttributeValue("Version"));
            // switch (version) {
            // case 8:
            list = getDataFromRootV8(root);
            // break;
            //
            // default:
            // break;
            // }
        data2Db(list);
    
        java.util.Date end = Calendar.getInstance().getTime();
        long period = end.getTime() - start.getTime();
        LogTool.debug("频谱实时数据入库花时：" + period);
        System.out.println("频谱实时数据入库花时：" + period);
    }

  /**
   * (radio_spectrum_realtime_tab)
   * 利用preparedStatement将指标数据入库
   * @throws UpMess2DBException
   */
  private void data2Db(ArrayList<SpectrumRealtimeReportBean> list) throws UpMess2DBException {
	// 指标入库sql语句
      String sql =
	  "insert into radio_spectrum_realtime_tab(realtime_id, equ_code, band, " +
	  "check_datetime, frequency, e_level, head_id) " +
	  "values(RADIO_REALTIME_SEQ.nextval, '" + equcode + "', '" +
	  band + "', ?, ?, ?,'" + headid + "')";
      DbComponent db = new DbComponent();
      DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
      prepExeSQL.getConnect();
    try {
            checkData(equcode, "band", band);
      
      // 获得SpectrumScan元素
      for (int i = 0; i < list.size(); i++) {
		SpectrumRealtimeReportBean bean = (SpectrumRealtimeReportBean) list.get(i);
		String checkdatetime = bean.getScandatetime();
		checkData(equcode, "checkdatetime", checkdatetime);
		String freq = bean.getFreq();
		String level = bean.getLevel();
		checkData(equcode, "level", level);
		checkData(equcode, "freq", freq);
		prepExeSQL.setString(1, checkdatetime);
		prepExeSQL.setString(2, freq);
		prepExeSQL.setString(3, level);
		prepExeSQL.exeSQL();
      }
    }
    catch (Exception ex) {
      throw new UpMess2DBException("主动上报异常：", ex);
    }
    finally{
    	prepExeSQL.closeConnect();
    }
  }

  /**
   * getDataFromRootV8
   * 将root中的数据解析到ArrayList中(v8版)
   * @param root
   * @return 包含SpectrumRealtimeReportBean的集合
   */
  public ArrayList<SpectrumRealtimeReportBean> getDataFromRootV8(Element root) {
	  ArrayList<SpectrumRealtimeReportBean> list = new ArrayList<SpectrumRealtimeReportBean>();
	      // 获得有关数据
      Element ele = null;
      ele = root.getChild("spectrumrealtimereport");
      this.headid = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
      
      // 获得equcode
      this.equcode = ele.getAttributeValue("equcode");
      // 获得band
      this.band = ele.getAttributeValue("band");
      // 获得SpectrumScan元素
      List list_spec = ele.getChildren(); 
      for (int i = 0; i < list_spec.size(); i++) {
		Element sub = (Element) list_spec.get(i);
		String checkdatetime = sub.getAttributeValue("scandatetime");
		List sublist = sub.getChildren(); // 获得ScanResult元素
			for (int j = 0; j < sublist.size(); j++) {
			  Element scanResult = (Element) sublist.get(j);
			  SpectrumRealtimeReportBean bean = new SpectrumRealtimeReportBean(scanResult);
			  bean.setEqu_code(equcode);
			  bean.setHead_id(headid);
			  bean.setBand(band);
			  bean.setScandatetime(checkdatetime);
			  list.add(bean);
			}
	      }
	  return list;
  }
  
  /**
   * getDataFromRootFrontier
   * 将root中的数据解析到ArrayList中(边境版)
   * @param root
   * @return 包含SpectrumRealtimeReportBean的集合
   */
  public ArrayList<SpectrumRealtimeReportBean> getDataFromRootFrontier(Element root) {
      ArrayList<SpectrumRealtimeReportBean> list = new ArrayList<SpectrumRealtimeReportBean>();
          // 获得有关数据
      Element ele = null;
      ele = root.getChild("spectrumrealtimereport");
      this.headid = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
      
      // 获得equcode
      this.equcode = ele.getAttributeValue("equcode");
      // 获得band
          this.band = ele.getAttributeValue("band");
      // 获得SpectrumScan元素
      List list_spec = ele.getChildren(); 
      for (int i = 0; i < list_spec.size(); i++) {
        Element sub = (Element) list_spec.get(i);
        String checkdatetime = sub.getAttributeValue("scandatetime");
        List sublist = sub.getChildren(); // 获得ScanResult元素
        for (int j = 0; j < sublist.size(); j++) {
          Element scanResult = (Element) sublist.get(j);
          SpectrumRealtimeReportBean bean = new SpectrumRealtimeReportBean(scanResult);
          bean.setEqu_code(equcode);
          bean.setHead_id(headid);
          bean.setBand(band);
          bean.setScandatetime(checkdatetime);
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
          throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode + "实时频谱上报数据缺少必要参数:"+data+"="+value);
      }
  }
}

/*
 * 广播频谱实时查询数据类
 */
class SpectrumRealtimeReportBean
{
	private String equ_code;
	private String band;
	private String scandatetime;
	private String freq;
	private String level;
	private String head_id;

	public SpectrumRealtimeReportBean(Element attrs) {
		this.freq = attrs.getAttributeValue("freq");
		this.level = attrs.getAttributeValue("level");
	}

	public String getEqu_code() {
		return equ_code;
	}

	public void setEqu_code(String equ_code) {
		this.equ_code = equ_code;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getScandatetime() {
		return scandatetime;
	}

	public void setScandatetime(String scandatetime) {
		this.scandatetime = scandatetime;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String head_id) {
		this.head_id = head_id;
	}
}