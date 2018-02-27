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
 * �㲥�豸��ʷ��־��ѯ���������ϱ��ӿ�
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
      String siteCode = root.getAttributeValue("SrcCode");//ǰ��վ��code
      String sitetype = SiteVersionUtil.getSiteType(siteCode);
      ArrayList<EquipmentLogBean> list = new ArrayList<EquipmentLogBean>();
      if(sitetype.equals("103")){//�߾�վ
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
      LogTool.debug("�豸��ʷ��־��ѯ���������ϱ����ʱ�䣺" + period);
      System.out.println("�豸��ʷ��־��ѯ���������ϱ����ʱ�䣺" + period);
  }

  /**
   * (radio_equ_log_tab)
   * ����preparedStatement��ָ���������
   * @throws UpMess2DBException
   */
  private void data2Db(ArrayList<EquipmentLogBean> list) throws UpMess2DBException {
	// ָ�����sql���
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
		  throw new UpMess2DBException("srcCode="+headcode+"�豸��ʷ��־��ѯ�ϱ�����ȱ�ٱ�Ҫ����:datetime="+datetime);
		}
		if (name == null||name.equals("")) {
	          throw new UpMess2DBException("srcCode="+headcode+"�豸��ʷ��־��ѯ�ϱ�����ȱ�ٱ�Ҫ����:name="+name);
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
      throw new UpMess2DBException("�㲥�豸��ʷ��־��ѯ���������ϱ��ӿ��쳣��", ex);
    }
    finally{
    	 prepExeSQL.closeConnect();
    }
  }

  /**
   * getDataFromRootV8
   * ��root�е����ݽ�����ArrayList��(v8��)
   * @param root
   * @return ����EquipmentLogBean�ļ���
   */
  private ArrayList<EquipmentLogBean> getDataFromRootV8(Element root) {

		ArrayList<EquipmentLogBean> list = new ArrayList<EquipmentLogBean>();
		// ����й�����
		Element ele = null;
		// ���EquipmentLogHistoryQueryRԪ��
		ele = root.getChild("equipmentloghistoryqueryr");
                if(ele==null)
                {
                    ele = root.getChild("EquipmentLogHistoryQueryR");

                }

		String headCode = root.getAttributeValue("SrcCode");

		List list_logitem = ele.getChildren(); // ���logitemԪ��
		for (int i = 0; i < list_logitem.size(); i++) {
			Element sub = (Element) list_logitem.get(i);
			EquipmentLogBean eLog = new EquipmentLogBean(sub, headCode);
			list.add(eLog);
		}

		return list;
	}

    /**
     * getDataFromRootFrontier
     * ��root�е����ݽ�����ArrayList��(�߾���)
     * @param root
     * @return ����EquipmentLogBean�ļ���
     */
    private ArrayList<EquipmentLogBean> getDataFromRootFrontier(Element root) {
      ArrayList<EquipmentLogBean> list = new ArrayList<EquipmentLogBean>();
      // ����й�����
      Element ele = null;
      // ���EquipmentLogHistoryQueryRԪ��
      ele = root.getChild("equipmentloghistoryqueryr");

      String headCode = root.getAttributeValue("SrcCode");

      List list_logitem = ele.getChildren(); // ���logitemԪ��
      for (int i = 0; i < list_logitem.size(); i++) {
          Element sub = (Element) list_logitem.get(i);
          EquipmentLogBean eLog = new EquipmentLogBean(sub, headCode);
          list.add(eLog);
      }

      return list;
   }
}

/*
 * �豸��ʷ��־��ѯ������
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
