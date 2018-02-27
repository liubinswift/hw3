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
 * 广播设备历史日志查询返回主动上报接口
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioEquipmentLogHistoryQuery implements IUpMsgProcessor {
  public RadioEquipmentLogHistoryQuery() {
  }

  public void processUpMsg(Element root) throws SQLException,
	UpMess2DBException, GDSetException, DbException, UtilException, NoRecordException {
      java.util.Date start = Calendar.getInstance().getTime();
      String siteCode = root.getAttributeValue("SrcCode");//前端站点code
      String sitetype = SiteVersionUtil.getSiteType(siteCode);
      ArrayList<EquipmentLogBean> list = new ArrayList<EquipmentLogBean>();
      if(sitetype.equals("103")){//边境站
          list = getDataFromRootFrontier(root);
      }
      else{
    //    int version = Integer.parseInt(root.getAttributeValue("Version"));
    //    switch (version) {
    //	case 8:
          list = getDataFromRootV8(root);
    //		break;
    //
    //	default:
    //		break;
    //	}
      }
      data2Db(list);

      java.util.Date end = Calendar.getInstance().getTime();
      long period = end.getTime() - start.getTime();
      LogTool.debug("设备历史日志查询返回主动上报入库时间：" + period);
      System.out.println("设备历史日志查询返回主动上报入库时间：" + period);
  }

  /**
   * (radio_equ_log_tab)
   * 利用preparedStatement将指标数据入库
   * @throws UpMess2DBException
   */
  private void data2Db(ArrayList<EquipmentLogBean> list) throws UpMess2DBException {
	// 指标入库sql语句
      String sql =
	  "insert into radio_equ_log_tab(log_id, log_datetime,description,remark,type_id,head_code) " +
	  "values(RADIO_DATA_RECOVERY_SEQ.nextval, ?,?,?,?,?)";
      DbComponent db = new DbComponent();
      DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
      prepExeSQL.getConnect();
    try {
      for (int i = 0; i < list.size(); i++) {
    	  EquipmentLogBean eLog = (EquipmentLogBean)list.get(i);
    	  String datetime = eLog.getDateTime();
    	  String name = eLog.getName();
    	  String headcode = eLog.getHeadCode();
		if (datetime == null||datetime.equals("")) {
		  throw new UpMess2DBException("srcCode="+headcode+"设备历史日志查询上报数据缺少必要参数:datetime="+datetime);
		}
		if (name == null||name.equals("")) {
	          throw new UpMess2DBException("srcCode="+headcode+"设备历史日志查询上报数据缺少必要参数:name="+name);
	        }
		  prepExeSQL.setString(1, datetime);
		  prepExeSQL.setString(2, eLog.getDesc() + "\\" + name);
		  prepExeSQL.setString(3, eLog.getRemark());
		  prepExeSQL.setString(4, eLog.getTypeId());
		  prepExeSQL.setString(5, eLog.getHeadCode());
		  prepExeSQL.exeSQL();
		}
    }
    catch (Exception ex) {
      throw new UpMess2DBException("广播设备历史日志查询返回主动上报接口异常：", ex);
    }
    finally{
    	 prepExeSQL.closeConnect();
    }
  }

  /**
   * getDataFromRootV8
   * 将root中的数据解析到ArrayList中(v8版)
   * @param root
   * @return 包含EquipmentLogBean的集合
   */
  private ArrayList<EquipmentLogBean> getDataFromRootV8(Element root) {

		ArrayList<EquipmentLogBean> list = new ArrayList<EquipmentLogBean>();
		// 获得有关数据
		Element ele = null;
		// 获得EquipmentLogHistoryQueryR元素
		ele = root.getChild("equipmentloghistoryqueryr");
                if(ele==null)
                {
                    ele = root.getChild("EquipmentLogHistoryQueryR");

                }

		String headCode = root.getAttributeValue("SrcCode");

		List list_logitem = ele.getChildren(); // 获得logitem元素
		for (int i = 0; i < list_logitem.size(); i++) {
			Element sub = (Element) list_logitem.get(i);
			EquipmentLogBean eLog = new EquipmentLogBean(sub, headCode);
			list.add(eLog);
		}

		return list;
	}

    /**
     * getDataFromRootFrontier
     * 将root中的数据解析到ArrayList中(边境版)
     * @param root
     * @return 包含EquipmentLogBean的集合
     */
    private ArrayList<EquipmentLogBean> getDataFromRootFrontier(Element root) {
      ArrayList<EquipmentLogBean> list = new ArrayList<EquipmentLogBean>();
      // 获得有关数据
      Element ele = null;
      // 获得EquipmentLogHistoryQueryR元素
      ele = root.getChild("equipmentloghistoryqueryr");

      String headCode = root.getAttributeValue("SrcCode");

      List list_logitem = ele.getChildren(); // 获得logitem元素
      for (int i = 0; i < list_logitem.size(); i++) {
          Element sub = (Element) list_logitem.get(i);
          EquipmentLogBean eLog = new EquipmentLogBean(sub, headCode);
          list.add(eLog);
      }

      return list;
   }
}

/*
 * 设备历史日志查询数据类
 */
class EquipmentLogBean
{
  private String dateTime;
  private String name;
  private String desc;
  private String headCode;
  private String remark;
  private String typeId;

  public EquipmentLogBean(Element attrs,String headCode) {
	this.headCode=headCode;
    this.dateTime = attrs.getAttributeValue("datetime");
    this.name = attrs.getAttributeValue("name");
    this.desc = attrs.getAttributeValue("desc");
    this.typeId = attrs.getAttributeValue("name");
  }

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHeadCode() {
		return headCode;
	}

	public void setHeadCode(String headCode) {
		this.headCode = headCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
