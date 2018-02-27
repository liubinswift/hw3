package com.viewscenes.web.Daoutil;

import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Map.*;

import org.jdom.*;
import org.jdom.input.*;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.web.Daoutil.ModuleException;
import com.viewscenes.web.Daoutil.SiteRunplanUtil;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSetException;


/**
 * <p>Title: 站点版本检查。</p>
 * 注意本类jsp页面中使用比较多。耦合性高，但时间紧，程序架构差，没有整理。
 * 使用jsp页面有：实时监听、实时监测等Jsp页面。
 * <p>Description: 站点版本号的提取:
 * 可以从数据库中提取，与资源管理有关。
 * 也可以用deviceconfig.xml中的值。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author lixuefeng
 * @version 1.0
 */

public class SiteVersionUtil {
  private static Hashtable DEFAULT_PARAM_IDS = null;

//  private static HashMap SITECODE_VERTYPES = null;
  private static String[][] CENTER_LIST = null;
  private static HashMap CENTER_IDNAMES = null;
  private static HashMap CENTER_IDCODES = null;

//  private static HashMap SITEID_CODES = null;
  private static int siteInfoSize = 7;

  public SiteVersionUtil() {
  }

  /**
   * 站点信息内存加载类。
   */
//  public static synchronized void loadSiteInfo() {
//    SITECODE_VERTYPES = new HashMap();
//    SITEID_CODES = new HashMap();
//    String[] value = null; //0 version, 1 type_id, 2 shortname, 3 center_id ---->大写的code
//    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
//    //大写代码与详细信息的对照。
//    String taskCode =
//	"select upper(code) code,version,type_id,shortname,center_id,head_id,code site_code," +
//	"manufacturer,ip,is_online  from res_headend_tab where is_delete=0 ";
//    ResultSet resultCode = null;
//    //大写代码与详细信息的对照。
//    try {
//      resultCode = db.exeQuery(taskCode);
//      while (resultCode.next()) {
//	value = new String[siteInfoSize];
//	value[0] = resultCode.getString("version");
//	value[1] = resultCode.getString("type_id");
//	value[2] = resultCode.getString("shortname");
//	value[3] = resultCode.getString("center_id");
//	value[4] = resultCode.getString("head_id");
//	value[5] = resultCode.getString("site_code");
//	value[6] = resultCode.getString("manufacturer");
//	value[7] = resultCode.getString("ip");
//	value[8] = resultCode.getString("is_online");
//	SITECODE_VERTYPES.put(resultCode.getString("code"), value);
//
//	String headId = resultCode.getString("head_id");
//	String siteCode = resultCode.getString("site_code");
//	SITEID_CODES.put(headId, siteCode);
//      }
//      System.out.println("站点映射缓存数据加载成功！");
//    }
//    catch (Exception ex2) {
//      System.out.println("查询站点信息出错！");
//      ex2.printStackTrace();
//    }
//    finally {
//      if (resultCode != null) {
//	try {
//	  resultCode.close();
//	}
//	catch (Exception ex) {
//	}
//      }
//    }
//  }

  /**
   * 初始化参数id映射内存加载类。
   */
  public static synchronized void loadDefaulParamId() {
    if (DEFAULT_PARAM_IDS == null) {
      DEFAULT_PARAM_IDS = new Hashtable();
    }

    String infoHead = null;

    String task =
        "select *  from RADIO_EQU_INIT_CONFIG_TAB";
    GDSet result = null;
    try {
      result = DbComponent.Query(task);
     for(int i=0;i<result.getRowCount();i++) {
        //数据结构定义必不为空。
        String version = result.getString(i,"VERSION");
        if (version != null) {
          String[] vers = version.split(",");
          if (vers != null) {
            for (int vi = 0; vi < vers.length; vi++) {
              infoHead = result.getString(i,"CENTER_ID") + "_" +
                  vers[vi] + "_" + result.getString(i,"HEAD_TYPE_ID") + "_" +
                  result.getString(i,"MANUFACTURER");
              DEFAULT_PARAM_IDS.put(infoHead,
                                    result.getString(i,"PARAM_ID"));
            }
          }
        }
      }
    }
    catch (Exception ex2) {
      System.out.println("查询设备初始配置信息出错！");
      ex2.printStackTrace();
    }


  }

  /**
   * 站点信息提取类。
   * @param siteCode String
   * @return String[]
   */
  public static String[] getSiteInfoByCode(String siteCode) {

    //大写代码与详细信息的对照。
    String taskCode =
        "select version,type_id,shortname,head_id,code site_code," +
        "manufacturer,ip  from res_headend_tab where is_delete=0 and upper(code) ='" +
        siteCode.toUpperCase() + "' ";
    GDSet resultCode = null;
    //大写代码与详细信息的对照。
    try {
      resultCode = DbComponent.Query(taskCode);
      if (resultCode.getRowCount()>0) {
        String[] siteInfo = new String[siteInfoSize];
        siteInfo[0] = resultCode.getString(0,"version");
        siteInfo[1] = resultCode.getString(0,"type_id");
        siteInfo[2] = resultCode.getString(0,"shortname");
        siteInfo[3] = resultCode.getString(0,"head_id");
        siteInfo[4] = resultCode.getString(0,"site_code");
        siteInfo[5] = resultCode.getString(0,"manufacturer");
        siteInfo[6] = resultCode.getString(0,"ip");
        return siteInfo;
      }
    }
    catch (Exception ex2) {
      System.out.println("查询站点信息出错！");
      ex2.printStackTrace();
    }

    return new String[siteInfoSize];
  }

  /**
   * 根据站点代码得到版本号。整数 5 6 7。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static int getSiteVersion(String siteCode) {
    int siteVer = 5; //4,5,6,7代表相应版本号。
    if (siteCode != null) {
      String version = getSiteInfoByCode(siteCode)[0];
      if (version != null) {
        siteVer = new Integer(version.substring(1)).intValue();
      }
    }
    return siteVer;
  }

  /**
   * 根据站点代码得到版本号。整数 5 6 7。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static Integer getSiteVerInteger(String siteCode) {
    Integer siteVer = new Integer(5); //4,5,6,7代表相应版本号。
    if (siteCode != null) {
      String version = getSiteInfoByCode(siteCode)[0];
      if (version != null) {
        siteVer = new Integer(version.substring(1));
      }
    }
    return siteVer;
  }

  /**
   * 根据站点代码得到版本号。V5 V6 V7。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static String getSiteVerStr(String siteCode) {
    String siteVer = "V5";
    if (siteCode != null) {
      siteVer = getSiteInfoByCode(siteCode)[0];
    }
    if (siteVer == null) {
      siteVer = "V5";
    }
    return siteVer.toUpperCase();
  }

  /**
   * 根据站点代码得到 站点类型 101采集点 102 遥控站。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static String getSiteType(String siteCode) {
    String siteType = "";
    if (siteCode != null) {
      siteType = getSiteInfoByCode(siteCode)[1];
    }
    if (siteType == null) {
      siteType = "";
    }
    return siteType;
  }

  /**
   * 根据站点代码得到  站点名称。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static String getSiteName(String siteCode) {
    String siteName = "";
    if (siteCode != null) {
      siteName = getSiteInfoByCode(siteCode)[2];
    }
    if (siteName == null) {
      siteName = "";
    }
    return siteName;
  }

  /**
   * 根据站点代码得到  站点所属台。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
//  public static String getSiteCenId(String siteCode) {
//    String siteCenId = "100";
//    if (siteCode != null) {
//      siteCenId = getSiteInfoByCode(siteCode)[3];
//    }
//    if (siteCenId == null) {
//      siteCenId = "100";
//    }
//    return siteCenId;
//  }

  /**
   * 根据站点代码得到  站点Id。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static String getSiteHeadId(String siteCode) {
    String siteId = "";
    if (siteCode != null) {
      siteId = getSiteInfoByCode(siteCode)[3];
    }
    if (siteId == null) {
      siteId = "";
    }
    return siteId;
  }

  /**
   * 根据站点代码输入 得到  数据库中站点原代码。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static String getSiteOriCode(String siteCode) {
    String siteOriCode = "";
    if (siteCode != null) {
      siteOriCode = getSiteInfoByCode(siteCode)[4];
    }
    if (siteOriCode == null) {
      siteOriCode = "";
    }
    return siteOriCode;
  }

  /**
   * 根据站点代码输入 得到  数据库中站点厂商。
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  public static String getSiteManu(String siteCode) {
    String siteManu = "";
    if (siteCode != null) {
      siteManu = getSiteInfoByCode(siteCode)[5];
    }
    if (siteManu == null) {
      siteManu = "";
    }
    return siteManu;
  }

  /**
   * 根据台id与站点版本从equ_init_parm.properties提取默认参数id。
   * @param centerId String
   * @param siteCode String
   * @return String
   */
  public static String getDefaultParamId(String centerId, String siteCode) {
    String paramId = "0";
    if (DEFAULT_PARAM_IDS == null) {
      loadDefaulParamId();
    }
    if (DEFAULT_PARAM_IDS != null) {
      if (siteCode != null && siteCode.length() > 0) {
        String[] siteInfo = getSiteInfoByCode(siteCode);
        paramId = (String) DEFAULT_PARAM_IDS.get(centerId + "_" + siteInfo[0] +
                                                 "_" + siteInfo[1] + "_" +
                                                 siteInfo[6]);
      }
    }
    return paramId;
  }

  /**
   * 提取站点  对应 运行图频率数组。
   * @param siteCode String
   * @param band String
   * @throws ModuleException
   * @return String[]
   */
  public static String[] getSiteFreqs(String siteCode) throws
      ModuleException {
    String[] siteFreqs = null;
    if (siteCode != null && siteCode.length() > 0) {
      StringBuffer sql = new StringBuffer(" SELECT distinct frequency ")
          .append("  FROM zres_runplan_view tab where 1=1 ")
          .append(SiteRunplanUtil.getRunplanSqlByCode(siteCode))
          .append(" and VALID_END_TIME >=sysdate and ")
          .append(" is_predefine =0 AND is_confirm =1  ")
          .append(" and is_delete = 0  and timespan_type_id=1 ")
          .append("  order by frequency ");

      GDSet result = null;
      try {
        result = DbComponent.Query(sql.toString());

        int rowCount = result.getRowCount();
        if (rowCount > 0) {
          siteFreqs = new String[rowCount];
          for (int i = 0; i < rowCount; i++) {
            String freq = result.getString(i, "frequency");
            siteFreqs[i] = freq;
          }
        }
      }
      catch (DbException ex2) {
        System.out.println("查询频率信息出错！");
        ex2.printStackTrace();
      }
      catch (GDSetException ex) {
        throw new ModuleException("查询频率信息出错！", ex);
      }


    }
    return siteFreqs;
  }

  /**
   *
   * @param siteCode
   * @return
   * @throws java.lang.Exception
   */
  private static int getSiteVersion_lxfplay(String siteCode) throws
      Exception {
    int siteVer = 0; //4,5,6,7代表相应版本号。
    URL deviceConfig = SiteVersionUtil.class.getClassLoader().getResource(
        "deviceconfig.xml");
    SAXBuilder builder = new SAXBuilder(false);
    Document doc = builder.build(deviceConfig);
    Element root = doc.getRootElement();
    Element e = root.getChild("innerdevice");
    List devlist = e.getChildren();
    for (int i = 0; i < devlist.size(); i++) {
      Element sub = (Element) devlist.get(i);
      String tem = sub.getAttributeValue("code");
      if (tem.equalsIgnoreCase(siteCode)) {
        String siteType = sub.getAttributeValue("type");
        if (siteType.equalsIgnoreCase("radio7")) {
          siteVer = 7;
          return siteVer;
        }
        else if (siteType.equalsIgnoreCase("radio6")) {
          siteVer = 6;
          return siteVer;
        }
      }
    }
    return siteVer;
  }

  private static void LoadCenters() {
    if (CENTER_LIST == null || CENTER_IDNAMES == null || CENTER_IDCODES == null) {

      GDSet data1 = null;
      try {
          String sql=" select center_id,name,code from res_center_tab  where center_id!=100 order by center_id asc";

        data1 = DbComponent.Query(sql);
        int rowCount1 = data1.getRowCount();
        if (rowCount1 >= 0) {
          CENTER_LIST = new String[2][rowCount1];
          CENTER_IDNAMES = new HashMap();
          CENTER_IDCODES = new HashMap();
          for (int i = 0; i < rowCount1; i++) {
            try {
              CENTER_LIST[0][i] = data1.getString(i, "center_id");
              CENTER_LIST[1][i] = data1.getString(i, "name");
              CENTER_IDNAMES.put(data1.getString(i, "center_id"),
                                 data1.getString(i, "name"));
              CENTER_IDCODES.put(data1.getString(i, "center_id"),
                                 data1.getString(i, "code"));
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * 查找中心列表. 位置0固定放中心的，如果不取中心的从1开始。
   * @param
   * @return
   */
  public static String[][] get_center_list() {
    LoadCenters();
    return CENTER_LIST;
  }

  /** add by kim 2006-3-21
   * 数据库的查询结果转到Hashtable[]中存放，方便操作
   * @param
   * @return
   */
  public static HashMap[] ResultSet2Hashtable(ResultSet ret) throws
      SQLException {
    HashMap[] hb = null;
    /********未完成编码 kim***************/
    if (ret != null) {
      ResultSetMetaData md = ret.getMetaData();
      int nColumnCount = md.getColumnCount();
      if (nColumnCount <= 0) {
        return null;
      }
      String[] szColnmnName = new String[nColumnCount];
      for (int it = 0; it < nColumnCount; it++) {
        szColnmnName[it] = md.getColumnName(it + 1);
      }
      int row = 0;
      ret.beforeFirst();
      while (ret.next()) {
        row++;
      }
      ret.first();
      if (row <= 0) {
        return null;
      }
      hb = new HashMap[row];
      for (int i = 0; i < row; i++) {
        hb[i] = new HashMap();
        for (int k = 0; k < szColnmnName.length; k++) {
          String stmp = ret.getString(szColnmnName[k]);
          if (stmp == null) {
            stmp = "";
          }
          hb[i].put(szColnmnName[k], stmp);
        }
        ret.next();
      }
    }
    return hb;
  }

  public static String getCenNameById(String center_id) {
    LoadCenters();
    String center_name = "";
    if (CENTER_IDNAMES != null && center_id != null &&
        center_id.length() > 0) {
      center_name = (String) CENTER_IDNAMES.get(center_id);
    }
    return center_name;
  }

  public static String getCenCodeById(String center_id) {
    LoadCenters();
    String center_code = "";
    if (CENTER_IDCODES != null && center_id != null &&
        center_id.length() > 0) {
      center_code = (String) CENTER_IDCODES.get(center_id);
    }
    return center_code;
  }

  /**
   * 站点id_code信息内存加载类。
   */
  public static String getHeadCodeById(String headId) {

    //大写代码与详细信息的对照。
    String taskCode =
        "select code from res_headend_tab where is_delete=0 and head_id =" + headId;
    GDSet resultCode = null;
    try {
      resultCode = DbComponent.Query(taskCode);
      if (resultCode.getRowCount()>0) {
        return resultCode.getString(0,"code");
      }
    }
    catch (Exception ex2) {
      System.out.println("查询站点信息出错！");
      ex2.printStackTrace();
    }

    return "";
  }

}
