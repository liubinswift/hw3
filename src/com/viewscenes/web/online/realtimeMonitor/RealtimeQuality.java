package com.viewscenes.web.online.realtimeMonitor;

import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioSpectrumHistoryReport;
import com.viewscenes.pub.GDSet;
import com.viewscenes.bean.device.SpectrumHistoryReportBean;
import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.cache.mon_cache.MonCacheAccessor;
import com.viewscenes.dao.DAOCondition;
import com.viewscenes.dao.DAOOperator;
import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.business.ReceiverListGetUtil;
import com.viewscenes.util.business.SiteRunplanUtil;
import com.viewscenes.util.business.SiteVersionUtil;
import com.viewscenes.util.business.runplan.RunPlan;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.runplan.gjt_runplan.GJTRunplan;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jmask.web.controller.EXEException;

import com.viewscenes.pub.GDSetException;
import com.viewscenes.dao.database.DbException;

import flex.messaging.io.amf.ASObject;

/**
 * <p>
 * Title: 实时指标
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 *
 * <p>
 * Company: Viewscenes
 * </p>
 *
 * @author zhaoyahui
 * @version 1.0
 */
public class RealtimeQuality {

    public final String EQU_CACHE_PREFIX = "equ_real_status_";
    
    public RealtimeQuality() {
    }

    public Object test(ASObject obj){
    	String sql = "select * from sec_user_tab t";
    	ASObject resObj;
		try {
			resObj = StringTool.pageQuerySql(sql, obj);
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return resObj;
    }
    public Object test1(ArrayList list){
    	for (int i = 0; i < list.size(); i++) {
    		System.out.println(list.get(i).toString()+"***************");
//    		TestBean bean = (TestBean)list.get(i);
//    		System.out.println(bean.getCode()+"===============");
		}
    	
    	return null;
    }
    /**
     * 下发实时指标扫描命令
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-7-28 下午04:45:04
     */
    public Object getSpectrumRealtimeQuery(ASObject obj){
    	RealtimeMonitorDevice device = new RealtimeMonitorDevice();
    	Object resObj = device.getSpectrumRealtimeQuery(obj);
    	return resObj;
    }
    
    /**
     * 下发实时频谱扫描命令
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-7-28 下午04:45:04
     */
    public Object sendQualityRealtimeAction(ASObject obj){
    	RealtimeMonitorDevice device = new RealtimeMonitorDevice();
    	Object resObj = device.getQualityRealtimeQuery(obj);
    	return resObj;
    }
    
    /**
     * 通过前端CODE查询前端信息、接收机、任务freq
     *
     * @param
     * @return freq
     */
    public Object getHeadendInfo(ASObject obj)  {
        String responseStr;
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();

        String headendCode =(String)obj.get("headendCode"); // 站点code
         boolean bVedio = false;
//        if (frontier != null && frontier.equalsIgnoreCase("tv")) {
//            //bVedio = true;
//        } else {
//            bVedio = false;
//        }
        try {

            // 站点代码不为空
            if (headendCode != null && headendCode.length() > 0) {

                // 查询前端信息
//                String headendName = SiteVersionUtil.getSiteName(headendCode);
//                String headendVersion = SiteVersionUtil
//                        .getSiteVerStr(headendCode);
//                String type_id = SiteVersionUtil.getSiteType(headendCode);

                // 获取接收机列表
                ArrayList equList = new ArrayList();
                Object equObj = this.getEquList(headendCode);
                if(equObj instanceof ArrayList){ 
                    list.add((ArrayList)equObj);
                } else {
                	return equObj;
                }

                // 获取频率列表
//                Vector progVector = new Vector();
//                Calendar nowCal = Calendar.getInstance();
//                String nowDate = StringTool.Date2String(nowCal.getTime());
//                
//    			GJTRunplan gjtRunplan = new GJTRunplan();
//    			GJTRunplanBean bean = new GJTRunplanBean();
//    			bean.setStartRow(1);
//    			bean.setEndRow(100);
//    			if("102".equals(type_id)){
//    				headendCode = headendCode.substring(0,headendCode.length()-1);
//    			}
//    			bean.setMon_area(""+headendCode+"");
//    			ASObject resultObj = (ASObject)gjtRunplan.queryRunplan(bean);
//    			ArrayList proList = new ArrayList();
//				ASObject program1 = new ASObject();
//				program1.put("label","指定频率");
//				program1.put("data","");
//				proList.add(program1);
//				ArrayList rlist = (ArrayList)resultObj.get("resultList"); 
//				HashMap map = new HashMap();
//				for(int a=0;a<rlist.size();a++){
//					GJTRunplanBean grlbean = (GJTRunplanBean)rlist.get(a);
//					ASObject program = new ASObject();
////					program.put("label",(grlbean.getProgram_name()==null?"":grlbean.getProgram_name())+"["+grlbean.getFreq()+"]");
//					program.put("label",grlbean.getFreq());
//					program.put("data",grlbean.getFreq());
//					map.put(grlbean.getFreq(), program);
//        		}
//				
//				
//				Iterator iter = map.entrySet().iterator();
//				while (iter.hasNext()) {
//					Map.Entry entry = (Map.Entry) iter.next();
//					Object key = entry.getKey();
//					Object val = entry.getValue();
//					
//					proList.add(val);
//				}
//                list.add(proList);
        		
                
                
                
              
            } else {
                return new EXEException("","未能找到指定站点","");
            }
        } catch (Exception ex) {
            LogTool.fatal(ex);
            return new EXEException("","通过前端CODE查询前端信息、接收机、任务freq错误:"+ ex.getMessage(),"");
        }
        return list;
    }
    /**
     * 获取接收机列表
     * @detail  
     * @method  
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-10 上午10:04:15
     */
    public Object getEquList(String headendCode){
    	Common common = new Common();
    	Object equList = common.getEquListNew(headendCode);
    	
    	return equList;
    }
    /**
     * 获取图形数据
     *
     * @param
     * @return
     */
    public String getGraphData(HttpServletRequest request) throws DbException,
            GDSetException {
        ArrayList<Element> list = new ArrayList<Element>();
        // Element root = StringTool.getXMLRoot(msg);
        long startT = System.currentTimeMillis();
        String dataType = null;
        try {
//            dataType = (String) asobj.get("dataType");
//            String code = (String) asobj.get("code");
//            String receiverCode = (String) asobj.get("receiverCode");// 接收机代码
//            String band = (String) asobj.get("band");
//            String frequency = (String) asobj.get("freq");// 频率
//            String interval = (String) asobj.get("intervalTime");// 上报间隔
//            String checktime = (String) asobj.get("checktime");// 检查时间
//            String timespan = (String) asobj.get("timespan"); // 数据时长
//            String frontier = (String) asobj.get("frontier");
            dataType = request.getParameter("data");
            Vector param = parseRequest(request);
            String headendID = getParam(param, "headendID"); //前端ID
            String receiverCode = getParam(param, "receiverCode"); //接收机代码
            if (receiverCode == null) {
                receiverCode = "";
            }
            String frequency = getParam(param, "frequency"); //频率
            String headendType = getParam(param, "headendType"); //前端类型
            String band = getParam(param, "band"); // 波段
            if (headendID != null && !headendID.equals("") && dataType != null && 
            		dataType.equalsIgnoreCase("100")&&(band!=null&&!band.equals(""))) {
                return getScanData(request);
            }

            String interval = request.getParameter("interval"); //上报间隔
            String checktime = request.getParameter("checktime"); //检查时间
            String timespan = request.getParameter("timespan"); //数据时长

            
           


            if (headendID != null && !headendID.equals("") && receiverCode != null && frequency != null
                    && dataType != null) {
                long interValue = 5;
                if (interval != null) {
                    interValue = new Long(interval).longValue();
                }

                // 构造结果集
                String b[] = { "index", "color", "x", "y", "description",
                        "desc", "url","dataType" };
                GDSet graphDataSet1 = new GDSet("graphdata", b);
                GDSet graphDataSet2 = new GDSet("graphdata", b);
                GDSet graphDataSet3 = new GDSet("graphdata", b);
                GDSet graphDataSet5 = new GDSet("graphdata", b);
                GDSet graphDataSet6 = new GDSet("graphdata", b);
                GDSet graphDataSet8 = new GDSet("graphdata", b);
                String e[] = { "10", "", "0", "0", "", "", "","" };

                String subsql = "";
                if (!receiverCode.equalsIgnoreCase("")) {
                    subsql += " and equ_code='" + receiverCode + "' ";
                }
//                int idt = new Integer(dataType).intValue();
                String[] typeArr = dataType.split(",");
                String subTypeSql = "";
                dataType  = "";
                for(int x=0;x<typeArr.length;x++){
                	if(typeArr[x].equals("2")){// 瞬时调制度只取一条记录
                		subTypeSql = " select realtime_id,value1,value2,check_datetime,type_id "
			                        + " from radio_quality_realtime_tab where head_id="
			                        + headendID
			                        + " and frequency='"
			                        + frequency
			                        + "'"
			                        + " and type_id=2"
			                        + subsql;
                		subTypeSql = StringTool.processSql(subTypeSql, 0, 2).replace("select * from (select rownum ora_rc",
                				" select realtime_id,value1,value2,check_datetime,type_id from (select rownum ora_rc");
                		continue;
                	}
                	if(x < typeArr.length-1){
                		dataType += typeArr[x]+",";
                	} else{
                		dataType += typeArr[x];
                	}
                }
                int fmmaxid = -150;

                long newInterval = 0;
                String value1Array[];
                String value2Array[];
                int i, k;

                GDSet set1;
                String f[] = { "realtime_id" };
                String g[] = new String[1];
                String sql = "";
                if(subTypeSql.equals("")){
                	sql = "select realtime_id,value1,value2,check_datetime,type_id "
                        + " from radio_quality_realtime_tab where head_id="
                        + headendID
                        + " and frequency='"
                        + frequency
                        + "'"
                        + " and type_id in("
                        + dataType +")"
                        + subsql
                        + " order by check_datetime,realtime_id";
                } else{
                	sql = " select realtime_id,value1,value2,check_datetime,type_id from "
                		+" (select realtime_id,value1,value2,check_datetime,type_id "
                        + " from radio_quality_realtime_tab where head_id="
                        + headendID
                        + " and frequency='"
                        + frequency
                        + "'"
                        + " and type_id in("
                        + dataType +")"
                        + subsql
                        + " union "
                        + subTypeSql + ")"
                        + " order by check_datetime,realtime_id";
                }
                /*
                 * if (checktime != null) {
                 * condition.addCondition("check_datetime", "DATE", ">",
                 * StringTool.Date2String(new Date(new
                 * Long(checktime).longValue()))); }
                 */
                long checkTime = 0;
                if (checktime != null && checktime.length() > 0) {
                    checkTime = new Long(checktime).longValue();
                }

                GDSet result_set;
//                if (idt == 2) { // 瞬时调制度只取一条记录
//                    sql = StringTool.processSql(sql, 0, 2);
//                    result_set = DbComponent.Query(sql);
//                } else {
//                    result_set = DbComponent.Query(sql);
//                }
                result_set = DbComponent.Query(sql);
                long endTime = 0; // 截止时间
                long resultTime = 0; // 数据采样时间
                boolean randomData = false;

                for (i = 0; i < result_set.getRowCount(); i++) {
                    // 获取记录采样时间
                    randomData = false;
                    resultTime = StringTool.stringToDate(
                            result_set.getString(i, "check_datetime"))
                            .getTime();
                    if (resultTime < checkTime) {
                        randomData = true;
                    }
                    if (i == 0 && timespan != null) {
                        // 计算截至时间
                        endTime = resultTime + new Long(timespan).longValue();
                    }
                    // if (i > 0 && endTime > 0)
                    // break; //超过截至时间不返回
                    if (result_set.getRowCount() <= interValue + 1) { // 取时间间隔内的
                        if (resultTime >= endTime && endTime > 0) {
                            break;
                        }
                    } else { // 超过上报间隔，留最后几条
                        if (i >= result_set.getRowCount() - (interValue + 1)) {
                            break;
                        }
                    }
                    value1Array = result_set.getString(i, "value1").split(",");
                    value2Array = result_set.getString(i, "value2").split(",");
                    // showLog(result_set.getString(i,"check_datetime")+":"+result_set.getString(i,
                    // "value1")+" | "+result_set.getString(i, "value2"));
                 switch (Integer.parseInt(result_set.getString(i, "type_id"))) {
                   case 1: // level
                	   e[0] = "10";
                       if (value1Array.length > 0) {
                           e[4] = e[5] = "电平";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // 时间均分
                               e[2] = new Long(resultTime + k * newInterval)
                                       .toString();
                               e[3] = value1Array[k];
                               e[7] = "1";
                               graphDataSet1.addRow(e);
                           }
                       }
                       break;
                   case 3: // am modulation
                	   e[0] = "10";
                       if (value1Array.length > 0) {
                           e[4] = e[5] = "调幅度";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // 时间均分
                               e[2] = new Long(resultTime + k * newInterval)
                                       .toString();
                               e[3] = value1Array[k];
                               e[7] = "3";
                               graphDataSet3.addRow(e);
                           }
                       }
                       break;
                   case 8: // bandwidth
                	   e[0] = "10";
		                 if (value1Array.length > 0) {
		                     e[4] = e[5] = "带宽";
		                     newInterval = 1000 / value1Array.length;
		                     for (k = 0; k < value1Array.length; k++) {
		                         // 时间均分
		                         e[2] = new Long(resultTime + k * newInterval)
		                                 .toString();
		                         e[3] = value1Array[k];
		                         e[7] = "8";
		                         graphDataSet8.addRow(e);
		                     }
		                 }
		                 break;

                   case 6: // offset
                	   e[0] = "10";
                       if (value1Array.length > 0) {
                           e[4] = e[5] = "频偏";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // 时间均分
                               e[2] = new Long(resultTime + k * newInterval)
                                       .toString();
                               e[3] = value1Array[k];
                               e[7] = "6";
                               graphDataSet6.addRow(e);
                           }
                       }
                       break;
                   case 2: // fm modulation
                	   e[0] = "11";
                       if (i > 0) {
                           break;
                       }
                       if (value1Array.length > 0) {
                           e[4] = e[5] = "瞬时调制度分布";
                           value1Array = getFMData(value1Array, headendType,
                                   randomData);
                           for (k = 0; k < value1Array.length; k++) {
                               // 频点均分
                               e[2] = new Long(fmmaxid + k).toString();
                               e[3] = value1Array[k];
                               e[7] = "2";
                               graphDataSet2.addRow(e);
                           }
                       }
                       break;
                   case 5: // fm max
                	   e[0] = "10";
                       if (value2Array.length > 0) {
                           e[4] = e[5] = "最大调制度 正向";
                           newInterval = 1000 / value2Array.length;
                           for (k = 0; k < value2Array.length; k++) {
                               // 时间均分
                               e[2] = new Long(resultTime + k * newInterval)
                                       .toString();
                               e[3] = value2Array[k];
                               e[7] = "5";
                               graphDataSet5.addRow(e);
                           }
                       }
                       e[0] = "11";
                       // e[1] = "00ffff";
                       if (value1Array.length > 0) {
                           e[4] = e[5] = "最大调制度 反向";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // 时间均分
                               e[2] = new Long(resultTime + k * newInterval)
                                       .toString();
                               e[3] = value1Array[k];
                               e[7] = "5";
                               graphDataSet5.addRow(e);
                           }
                       }
                       break;
                  
                   }


                    // 删除已获取记录
                    if (i < result_set.getRowCount() - 1) {
                        DbComponent
                                .exeUpdate("delete from radio_quality_realtime_tab where realtime_id="
                                        + result_set
                                                .getString(i, "realtime_id"));
                    }
                }
                // showLog("total has:" + result_set.getRowCount() + ", get " +
                // i+", data num:"+graphDataSet.getRowCount());
                //list.add(element);
                long endT = System.currentTimeMillis();
                System.out.println(new Timestamp(System.currentTimeMillis()) + "applet"
                        + dataType + "提取数据时间差：" + (endT - startT));
                LogTool.debug("applog", "<xml>-1#fen#temp#@#1#fen#"+graphDataSet1.toString()+"#@#"+
                		"2#fen#"+graphDataSet2.toString()+"#@#"+
                		"3#fen#"+graphDataSet3.toString()+"#@#"+
                		"5#fen#"+graphDataSet5.toString()+"#@#"+
                		"6#fen#"+graphDataSet6.toString()+"#@#"+
                		"8#fen#"+graphDataSet8.toString()+"</xml>");
//                System.out.println("-1#fen#temp#@#1#fen#"+graphDataSet1.toString()+"#@#"+
//                		"2#fen#"+graphDataSet2.toString()+"#@#"+
//                		"3#fen#"+graphDataSet3.toString()+"#@#"+
//                		"5#fen#"+graphDataSet5.toString()+"#@#"+
//                		"6#fen#"+graphDataSet6.toString()+"#@#"+
//                		"8#fen#"+graphDataSet8.toString()+"</xml>");
                return "<xml>-1#fen#temp#@#1#fen#"+graphDataSet1.toString()+"#@#"+
                		"2#fen#"+graphDataSet2.toString()+"#@#"+
                		"3#fen#"+graphDataSet3.toString()+"#@#"+
                		"5#fen#"+graphDataSet5.toString()+"#@#"+
                		"6#fen#"+graphDataSet6.toString()+"#@#"+
                		"8#fen#"+graphDataSet8.toString()+"</xml>";
            } else {
                throw new Exception("参数错误或不足");
            }
        } catch (Exception e) {
            LogTool.fatal(e);
            return StringTool.getXmlErrorMessage("ErrorMessage", "服务器错误 - "
                    + e.toString());
        }

    }
public  static void main(String[] args)
{
	String str="10$$1403071757000$38.1$电平$电平$$1$#@#2#fen#";
	String str2="10$$1403071756000$40$带宽$带宽$$8$";
	String bandvalue=str2.substring(str2.indexOf("带宽")-8,str2.indexOf("带宽"));
	String levelvalue=str.substring(str.indexOf("电平")-8,str.indexOf("电平"));
	System.out.println(bandvalue.split("\\$")[1]);
	System.out.println(levelvalue.split("\\$")[1]);
	
}
    /**
     * 计算瞬时调制度
     *
     * @param val
     * @return
     */
    private String[] getFMData(String[] val, String type, boolean randomData) {
        String returnValue[] = new String[val.length];
        double rValue = 0;
        double tValue = 0; // 总值
        double dValue, nValue;
        for (int i = 0; i < val.length; i++) {
            if (val[i].length() > 0) {
                tValue += new Double(val[i]).doubleValue();
            }
        }
        for (int i = 0; i < val.length; i++) {
            if (val[i].length() > 0 && !val[i].equalsIgnoreCase("0")) {
                rValue = new Double(val[i]).doubleValue();
                dValue = rValue / tValue;
                nValue = Math.log(dValue) / Math.log(10);
                if (randomData) {
                    double rnum = (Math.random() - 0.5) / 10 + 1;
                    nValue *= rnum;
                }
                nValue = ((long) (nValue * 100)) / 10.0;
                returnValue[i] = new Double(nValue).toString();
            } else {
                returnValue[i] = new Double(Double.NEGATIVE_INFINITY)
                        .toString();
            }
        }
        return returnValue;
    }

    /**
     * 获取频谱数据
     *
     * @param request
     * @param opDetail
     * @return
     */
    public String getScanData(HttpServletRequest request) {
        long startT = System.currentTimeMillis();
        boolean returnValue = false;
        try {
         // 构造结果集
            String b[] = { "index", "color", "x", "y", "description",
                    "desc", "url" };
            GDSet graphDataSet = new GDSet("graphdata", b);
            Vector param = parseRequest(request);
            String headendID = getParam(param, "headendID"); // 前端ID
            String receiverCode = getParam(param, "receiverCode"); // 接收机编号
            String band = getParam(param, "band"); // 波段
            if (headendID != null && receiverCode != null) {

                String e[] = { "10", "00FF00", "0", "0", "", "", "" };

                GDSet set1;
                String f[] = { "realtime_id" };
                String g[] = new String[1];

                String receiveSqlWhere = "";
                if (!receiverCode.equalsIgnoreCase("")) {
                    receiveSqlWhere = " and equ_code='" + receiverCode + "'";
                }
                receiveSqlWhere+= " and band = " + band;
                String maxSql = "select decode(max(realtime_id),'',0,max(realtime_id))  maxid from radio_spectrum_realtime_tab";
                GDSet maxSet = DbComponent.Query(maxSql);
                String maxRealId = maxSet.getString(0, "maxid");
                String querySql = " select realtime_id,band,frequency,e_level,check_datetime from "
                        + " radio_spectrum_realtime_tab where head_id="
                        + headendID

                        + receiveSqlWhere
                        + " and realtime_id<="+maxRealId
                        + " order by check_datetime,frequency ";

                GDSet result_set = DbComponent.Query(querySql);
                int i;

                long resultTime = 0;
                double resultFreq = 0;
                double lastFreq = 0;
                long lastTime = 0;

                for (i = 0; i < result_set.getRowCount(); i++) {
                    // 获取记录时间
                    resultTime = StringTool.stringToDate(
                            result_set.getString(i, "check_datetime"))
                            .getTime();
                    resultFreq = new Double(result_set.getString(i, "frequency"))
                            .longValue();
//                    if (i == 0) {
//                        // 记录上次时间与频率
//                        lastFreq = resultFreq;
//                        lastTime = resultTime;
//                        // 设置截止时间
//                    }
//                    // 循环一周了
//                    if (resultFreq <= lastFreq && resultTime > lastTime) {
//                        // showLog("delete old spec band" + resultFreq + ":" +
//                        // lastFreq);
//                        String subsql = "";
//                        if (!receiverCode.equalsIgnoreCase("")) {
//                            subsql = " and equ_code='" + receiverCode + "' ";
//                        }
//                        if (frontier.equals("radio")) {//边境广播
//                        	subsql += " and signaltype='radioup' ";
//                        	subsql+= " and band = " + band;
//                        } else if (frontier.equals("tv")) {//边境电视
//                        	subsql+= " and signaltype='tvup' ";
//                        	subsql += " and band  is null";
//                        } else {//v8广播
//                        	subsql+= " and band = " + band;
//                        }
//                        String sql = "delete from radio_spectrum_realtime_tab where head_id="
//                                + headendID
//                                + subsql
//                                + " and check_datetime<=to_date('"
//                                + result_set.getString(i, "check_datetime")
//                                + "','yyyy-mm-dd hh24:mi:ss')";
//                        DbComponent.exeUpdate(sql);
//                        graphDataSet.clearAllRow();
//                        // break;
//                    }
                    // 记录上次时间与频率
                    lastFreq = resultFreq;
                    lastTime = resultTime;
                    if(result_set.getString(i, "band")==null|| result_set.getString(i, "band").equals("")){//边境电视
                    	e[2] = result_set.getString(i, "frequency");//MHz
                    } else{
                    	if (result_set.getString(i, "band").equalsIgnoreCase("1")) {
	                        e[2] = result_set.getString(i, "frequency");
	                    } else { // 短波，调频，单位转MHz
	                        e[2] = new Float((new Float(result_set.getString(i,
	                                "frequency")).floatValue() / 1000)).toString();
	                    }
                    }
                    e[3] = result_set.getString(i, "e_level");
                    graphDataSet.addRow(e);
                }

                if (result_set.getRowCount()>0) {
                    String subsql = "";
                    if (!receiverCode.equalsIgnoreCase("")) {
                        subsql = " and equ_code='" + receiverCode + "' ";
                    }
                    subsql+= " and band = " + band;
                    String sql = "delete from radio_spectrum_realtime_tab where head_id="
                          + headendID
                          + subsql
                          + " and realtime_id<="+maxRealId;
                    DbComponent.exeUpdate(sql);
//                  graphDataSet.clearAllRow();
                }

                long endT = System.currentTimeMillis();
                System.out.println(new Timestamp(System.currentTimeMillis())
                        + "频谱applet提取数据时间差：" + (endT - startT));
                return "<xml>"+graphDataSet.toString()+"</xml>";
            } else {
                throw new Exception("参数不足");
            }
        } catch (Exception e) {
            LogTool.fatal("获取频谱数据异常："+e);
        }
       return "";
    }

    /**
     * 获取输入参数
     *
     * @param v
     * @param name
     * @return
     */
    private String getParam(Vector v, String name) {
        for (int i = 0; i < v.size(); i += 2) {
            if (v.get(i).toString().equalsIgnoreCase(name)) {
                return v.get(i + 1).toString();
            }
        }
        return null;
    }

    /**
     * 解析请求
     *
     * @param request
     * @param getReceiver
     * @param opDetail
     * @return
     */
    private Vector parseRequest(HttpServletRequest request) throws Exception{
        Vector returnValue = new Vector();

        String headendID = request.getParameter("headendid"); // 前端ID
        String headendCode = request.getParameter("headendcode"); // 前端编号
        /**
         * 当参数不为空时，先从内存中转换成数据库中的实际值。 区分大小写功能。 lixuefeng2005-12-08
         */
        if (headendCode != null && headendCode.length() > 0) {
            headendCode = SiteVersionUtil.getSiteOriCode(headendCode);
            headendID = SiteVersionUtil.getSiteHeadId(headendCode);
            String receiverCode = request.getParameter("receivercode"); // 接收机编号
            String band = request.getParameter("band"); // 波段
            String frequency = request.getParameter("frequency"); // 频率
            String headendType = SiteVersionUtil.getSiteType(headendCode);
            String headendName = SiteVersionUtil.getSiteName(headendCode);
//            String headendMaker = SiteVersionUtil.getSiteManu(headendCode);
            if (receiverCode == null) {
                receiverCode = "";
            } 
//            else if (!receiverCode.equalsIgnoreCase("")) {
//                try {
//                    //查询可用的接收机，查不到仍为空。
//                    String sql = "select code from dic_headend_receiver_tab where head_type_id="
//                            + headendType + " and code='" + receiverCode + "'";
//                    GDSet result_set = DbComponent.Query(sql);
//                    if (result_set.getRowCount() <= 0) {
//                        //由站点自动选择接收机
//                        receiverCode = "";
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
            //添加验证过的信息
            returnValue.add("headendID");
            returnValue.add(headendID);
            returnValue.add("headendCode");
            returnValue.add(headendCode);
            returnValue.add("headendType");
            returnValue.add(headendType);
            returnValue.add("headendName");
            returnValue.add(headendName);
            returnValue.add("receiverCode");
            returnValue.add(receiverCode);
            if (band != null && band.length() > 0) {
                returnValue.add("band");
                returnValue.add(band);
            }
            if (frequency != null && frequency.length() > 0) {
                //由频率计算波段
                returnValue.add("frequency");
                returnValue.add(frequency);
                if (band == null) {
                    float fFreq = new Float(frequency).floatValue();
                    if (fFreq < 2000) {
                        band = "1";
                    } else if (fFreq < 30000) {
                        band = "0";
                    } else {
                        band = "2";
                    }
                    returnValue.add("band");
                    returnValue.add(band);
                }
            }
        } else {
            throw new Exception("warning:站点代码有误！");
        }
        return returnValue;
    }

    /**
     * 查询报警参数
     *
     * @param request
     * @param opDetail
     * @return
     */
    public Object getLevel(ASObject asobj) {
        String level = "";
        String data = "";
        try {

            String headendCode = (String) asobj.get("code"); // 前端代码
            String headendID = (String) asobj.get("headendID"); // 前端ID
            String frequency = (String) asobj.get("freq"); // 频率
            String band = (String) asobj.get("band"); // 波段
            String receiverCode = (String) asobj.get("receiverCode"); //接收机
            data = (String) asobj.get("dataType"); // 图表类型。

            if (headendCode != null && headendCode.length() > 0 && data != null) {
                // send to equ
                if (band == null) {
                    band = "";
                }
                if (receiverCode == null) {
                    receiverCode = "";

                }

                String lowLevelName = "";
                String highLevelName = "";
                /**
                 * 根据图表类型，决定查询的字段。
                 */
//                if (data.equalsIgnoreCase("1") || data.equalsIgnoreCase("4")) {
//                    lowLevelName = "downthreshold1level";
//                } else if (data.equalsIgnoreCase("3")) {
//                    lowLevelName = "downthreshold3am";
//                    highLevelName = "upthreshold3am";
//                } else if (data.equalsIgnoreCase("5")) {
//                    lowLevelName = "downthreshold2fm";
//                    highLevelName = "upthreshold2fm";
//                }
                if (data.equalsIgnoreCase("1")) {//电平
                    lowLevelName = "downthreshold1level";
                } else if (data.equalsIgnoreCase("3")) {//调幅度
                    lowLevelName = "downthreshold3am";
                    highLevelName = "upthreshold3am";
                } else if (data.equalsIgnoreCase("5")) {//调制度
                    lowLevelName = "downthreshold2fm";
                    highLevelName = "upthreshold2fm";
                }
                if (lowLevelName.length() > 0 || highLevelName.length() > 0) {
                    GDSet set1;
                    /**
                     * 根据headid、接收机、frequency查找相应的参数。
                     */
                    if (frequency != null) {
                        if (receiverCode == null || receiverCode.length() <= 0) {
                            receiverCode = "All";
                        }
                        /**
                         * 先查询自己的值。如果没有，依次查询相应默认值。
                         */
                        String sql = "select * from radio_quality_alarm_param_tab where head_id='"
                                + headendID
                                + "'"
                                + " and EQU_CODE='"
                                + receiverCode
                                + "'"
                                + " and FREQUENCY='"
                                + frequency + "'";
                        set1 = DbComponent.Query(sql);
                        // 查不到，查此波段下的默认值。
                        band=SiteRunplanUtil.getBandFromFreq(frequency);
                        if (set1 == null || set1.getRowCount() <= 0) {
                            sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                    + headendID
                                    + "'"
                                    + " and band='"
                                    + band + "'" + " and FREQUENCY='0'";
                            set1 = DbComponent.Query(sql);
                        }
                        // 查不到，查此接收机号下的默认值。
                        if (set1 == null || set1.getRowCount() <= 0) {
                            sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                    + headendID
                                    + "'"
                                    + " and EQU_CODE='"
                                    + receiverCode + "'" + " and FREQUENCY='0'";
                            set1 = DbComponent.Query(sql);
                        }
                        // 仍为空，如果接收机有具体值，再查询接收机为All默认时的默认值有没有。
                        if (set1 == null || set1.getRowCount() <= 0) {
                            if (!receiverCode.equalsIgnoreCase("All")) {
                                sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                        + headendID
                                        + "'"
                                        + " and EQU_CODE='All'"
                                        + " and FREQUENCY='0'";
                                set1 = DbComponent.Query(sql);
                            }
                        }
                        //仍为空，查询默认值。
                        if (set1 == null || set1.getRowCount() <= 0) {
                            sql = " select * from radio_quality_alarm_param_tab where param_id='1'";
                            set1 = DbComponent.Query(sql);
                        }
                        if (set1 != null && set1.getRowCount() > 0) {
                            if (lowLevelName.length() > 0) {
                                level = set1.getString(0, lowLevelName);
                            }
                            if (highLevelName.length() > 0) {
                                level += ":" + set1.getString(0, highLevelName);
                            }

                        }
                    } else{
                    	GDSet set3 = null;
                    	 String sql = "";
                         // 仍为空，如果接收机有具体值，再查询接收机为All默认时的默认值有没有。
                             sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                     + headendID
                                     + "'"
                                     + " and EQU_CODE='All'"
                                     + " and FREQUENCY='0'";
                             set3 = DbComponent.Query(sql);
                         //仍为空，查询默认值。
                         if (set3 == null || set3.getRowCount() <= 0) {
                             sql = " select * from radio_quality_alarm_param_tab where param_id='1'";
                             set3 = DbComponent.Query(sql);
                         }
                         if (set3 != null && set3.getRowCount() > 0) {
                             if (lowLevelName.length() > 0) {
                                 level = set3.getString(0, lowLevelName);
                             }
                             if (highLevelName.length() > 0) {
                                 level += ":" + set3.getString(0, highLevelName);
                             }

                         }
                    }
                }
            } else {
                throw new Exception("参数不足");
            }
        } catch (Exception e) {
            LogTool.fatal("查询报警参数异常："+e);

            return new EXEException("","查询报警参数异常："+ e.getMessage(),"");
        }
        ASObject resobj = new ASObject();
        resobj.put("dataType",data);
        resobj.put("level",level);
        return resobj;
    }

    /**
     * 设置报警参数
     *
     * @return
     */
    public Object setLevel(ASObject asobj) {
        try {
            String userID = (String) asobj.get("user_id");//
            String headendCode = (String) asobj.get("code");// 前端代码
            String headendName = "";
            String headendID = ""; // 前端代码
            String frequency = (String) asobj.get("freq"); // 频率
            String band = (String) asobj.get("band"); // 波段
            String receiverCode = (String) asobj.get("receiverCode"); // 接收机
            String data = (String) asobj.get("dataType"); // 图表类型。
            String lowLevel = (String) asobj.get("lowlevel"); //
            String highLevel = (String) asobj.get("highlevel"); //
            if (headendCode != null) {
                headendCode = SiteVersionUtil.getSiteOriCode(headendCode);
                headendID = SiteVersionUtil.getSiteHeadId(headendCode);
            }

            if (headendCode != null && data != null
                    && (lowLevel != null || highLevel != null)) {
                // send to equ
                if (band == null) {
                    band = "";
                }
                if (receiverCode == null) {
                    receiverCode = "";
                }
                String cmdStr = "";
                String lowLevelName = "";
                String highLevelName = "";
//                if (data.equalsIgnoreCase("1") || data.equalsIgnoreCase("4")) {
//                    lowLevelName = "downthreshold1level";
//                    if (lowLevel.length() > 0) {
//                        cmdStr += "电平过低报警门限:" + lowLevel + " ";
//                    }
//                } else if (data.equalsIgnoreCase("3")
//                        || data.equalsIgnoreCase("5")) {
//                    lowLevelName = "downthreshold3am";
//                    if (lowLevel.length() > 0) {
//                        cmdStr += "调幅度过低报警门限:" + lowLevel + " ";
//                    }
//                    highLevelName = "upthreshold3am";
//                    if (highLevel.length() > 0) {
//                        cmdStr += ",调幅度过高报警门限:" + highLevel + " ";
//                    }
//                }
                if (data.equalsIgnoreCase("1")) {//电平
                    lowLevelName = "downthreshold1level";
                    if (lowLevel.length() > 0) {
                        cmdStr += "电平过低报警门限:" + lowLevel + " ";
                    }
                } else if (data.equalsIgnoreCase("3")) {//调幅度
                    lowLevelName = "downthreshold3am";
                    if (lowLevel.length() > 0) {
                        cmdStr += "调幅度过低报警门限:" + lowLevel + " ";
                    }
                    highLevelName = "upthreshold3am";
                    if (highLevel.length() > 0) {
                        cmdStr += ",调幅度过高报警门限:" + highLevel + " ";
                    }
                } else if (data.equalsIgnoreCase("5")) {//调制度
                    lowLevelName = "downthreshold2fm";
                    highLevelName = "upthreshold2fm";
                    
                    lowLevelName = "downthreshold2fm";
                    if (lowLevel.length() > 0) {
                        cmdStr += "调制度过低报警门限:" + lowLevel + " ";
                    }
                    highLevelName = "upthreshold2fm";
                    if (highLevel.length() > 0) {
                        cmdStr += ",调制度过高报警门限:" + highLevel + " ";
                    }
                }
                
                if (frequency != null) {
                    cmdStr += frequency + " kHz " + cmdStr;
                }

                if (lowLevelName.length() > 0 || highLevelName.length() > 0) {
                    GDSet set1;
                    if (frequency != null) {
                        set1 = RunPlan.getAlarmInfo(headendID, receiverCode,
                                frequency);
                    } else {
                        set1 = RunPlan.getAlarmInfo(headendID, receiverCode,
                                band, "", null);
                    }
                    if (set1 != null && set1.getRowCount() > 0) {
                        // showLog(set1.toString());
                        if (lowLevelName.length() > 0 && lowLevel != null) {
                            set1.setString(0, lowLevelName, lowLevel);
                        }
                        if (highLevelName.length() > 0 && highLevel != null) {
                            set1.setString(0, highLevelName, highLevel);

                        }
//                        if (!data.equalsIgnoreCase("100")) { // 非频谱。
//                            MonDataByDeviceToDB device = new MonDataByDeviceToDB();
//                          //设置优先级
//                            long msgPrio = 1;
//                            if (userID != null) {
//                              Security security = new Security();
//                              msgPrio = security.getMessagePriority(userID, 0, 4, 0);
//                            }
//                            String center_id = SiteVersionUtil.getSiteCenId(headendCode);
//                            msg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
//                                "<msg>"+
//                                "<code>"+headendCode+"</code>"+
//                                "<equCode>"+receiverCode+"</equCode>"+
//                                "<band>"+band+"</band>"+
//                                "<freq>"+frequency+"</freq>"+
//                                "<priority>"+new Long(msgPrio).toString()+"</priority>"+
//                                "<headId>"+headendID+"</headId>"+
//
//                                "<DownThreshold1Level>"+set1.getString(0,"downthreshold1level")+"</DownThreshold1Level>"+
//                                "<AbnormityLength1Level>"+set1.getString(0, "abnormitylength1level")+"</AbnormityLength1Level>"+
//
//                                "<SampleLength2FM>"+set1.getString(0, "samplelength2fm")+"</SampleLength2FM>"+
//                                "<DownThreshold2FM>"+set1.getString(0, "downthreshold2fm")+"</DownThreshold2FM>"+
//                                "<UpThreshold2FM>"+set1.getString(0, "upthreshold2fm")+"</UpThreshold2FM>"+
//                                "<UpAbnormityRate2FM>"+set1.getString(0, "upabnormityrate2fm")+"</UpAbnormityRate2FM>"+
//                                "<DownAbnormityRate2FM>"+set1.getString(0, "downabnormityrate2fm")+"</DownAbnormityRate2FM>"+
//                                "<AbnormityLength2FM>"+ set1.getString(0, "abnormitylength2fm")+"</AbnormityLength2FM>"+
//
//                                "<SampleLength3AM>"+set1.getString(0, "samplelength3am")+"</SampleLength3AM>"+
//                                "<DownThreshold3AM>"+set1.getString(0, "downthreshold3am")+"</DownThreshold3AM>"+
//                                "<UpThreshold3AM>"+set1.getString(0, "upthreshold3am")+"</UpThreshold3AM>"+
//                                "<UpAbnormityRate3AM>"+set1.getString(0, "upabnormityrate3am")+"</UpAbnormityRate3AM>"+
//                                "<DownAbnormityRate3AM>"+set1.getString(0, "downabnormityrate3am")+"</DownAbnormityRate3AM>"+
//                                "<AbnormityLength3AM>"+set1.getString(0, "abnormitylength3am")+"</AbnormityLength3AM>"+
//
//                                "<Attenuation4Attenuation>1</Attenuation4Attenuation>"+
//                                "<center_id>"+center_id+"</center_id>"+
//                                "</msg>";
//
//                            return device.getQualityAlarmSet(msg);
                        		return "";
                            //end send msg
//                        }

                    }
                  //删除原记录
            	    DAOOperator d = (DAOOperator) DaoFactory.create(
            		DaoFactory.
            		DAO_OBJECT);
            	    DAOCondition condition = new DAOCondition(
            		"radio_quality_alarm_param_tab");
            	    condition.addCondition("head_id", "NUMBER", "=",
            				   headendID);
            	    if (receiverCode.length() > 0) {
            	      condition.addCondition("equ_code", "VARCHAR", "=",
            				     receiverCode);
            	    }
            	    else {
            	      receiverCode = "All";
            	    }
            	    if (frequency != null && frequency.length() > 0) {
            	      condition.addCondition("frequency", "NUMBER", "=",
            				     frequency);
            	    }
            	    else {
            	      frequency = "";
            	    }
            	    if (band.length() > 0) {
            	      condition.addCondition("band", "NUMBER", "=", band);
            	    }
            	    else {
            	      band = "9";
            	    }
            	    d.DeleteX(condition);

            	    int[] daoReturn;
            	    set1.setString(0, "param_id", "");
            	    set1.setString(0, "head_id", headendID);
            	    set1.setString(0, "equ_code", receiverCode);
            	    set1.setString(0, "band", band);
            	    set1.setString(0, "frequency", frequency);
            	    long key[] = new long[1];
            	    daoReturn = d.Insert(set1, key);
                } else {
                    
                    
                }
            } else {
               throw new Exception("参数不足");
            }
        } catch (Exception ex) {
            LogTool.fatal("设置报警参数错误："+ex);

            return new EXEException("","设置报警参数异常："+ ex.getMessage(),"");
        }
        return "设置成功";
    }
    
    /**
     * 设备实时状态查询
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-10 上午10:44:48
     */
    public Object UpEquipmentStatusRealtimeQuery(ASObject obj){
    	RealtimeMonitorDevice device = new RealtimeMonitorDevice();
    	Object resObj = device.UpEquipmentStatusRealtimeQuery(obj);
    	return resObj;
    }
    
    /**
     * 获取设备实时状态
     * @detail  
     * @method  
     * @param msg
     * @return 
     * @return  String  
     * @author  zhaoyahui
     * @version 2012-8-10 上午10:44:53
     */
    public Object getEquDataFromCache(ASObject asobj){
        String result = "";
        ASObject asobjres = new ASObject();
        asobjres.put("last_get_time", "");
        asobjres.put("dataList", "");
        long startTime = System.currentTimeMillis();
        try {
            // 前端ID
            String headCode = (String) asobj.get("code");
            String last_get_time = (String) asobj.get("last_get_time");
            headCode = SiteVersionUtil.getSiteOriCode(headCode);
            String headId = SiteVersionUtil.getSiteHeadId(headCode);

            // 上次提取数据的最大时间。
            // 取不到时，页面显示与下次提取时间。
            java.util.Date lastTime = null;
            if (last_get_time != null && !last_get_time.equals("")) {
                lastTime = StringTool.stringToDate(last_get_time);
            }else
            {
                lastTime= new Date();
            }
            if (headId != null) {
                HashMap[] statusMap = MonCacheAccessor.getCacheData(
                        EQU_CACHE_PREFIX + headId, lastTime);
                for (int getI = 0; statusMap != null && statusMap.length > 0
                        && getI < statusMap.length; getI++) {
                    // 就用1秒的数据。
                    HashMap valueMap = (HashMap) (statusMap[getI].clone());
                    // 如果为空，取下一条.
                    if (valueMap == null || valueMap.size() == 0) {
                        continue;
                    }

                    // 如果时间(主键)为空，取下一条。否则下次仍取当条。
                    java.util.Date newTime = (java.util.Date) valueMap.get("check_datetime");
                    valueMap.remove("check_datetime");
                    if (newTime == null
                            || (lastTime != null && !newTime.after(lastTime))) {
                        continue;
                    }
//                    Element ele = new Element("info");
                    asobjres.put("last_get_time", StringTool.Date2String(newTime));
//                    ele.addAttribute(new Attribute("last_get_time", StringTool
//                            .Date2String(newTime)));
                    String temp = "";

                    if (!valueMap.isEmpty()) {
                        valueMap.remove("head_id");
                        valueMap.remove("head_code");
                        Set key = valueMap.keySet();
                        Iterator keyIt = key.iterator();
                        if (keyIt.hasNext()) {
                            temp = (String) valueMap.get((String) keyIt.next());
                        }

                        while (keyIt.hasNext()) {
                            temp += "#$#"+ (String) valueMap.get((String) keyIt.next());
                        }
                    }
                    asobjres.put("dataList", temp);
//                    ele.addAttribute(new Attribute("value", temp));
//                    list.add(ele);
                    //如果找到了修改内容。注意如果没有克隆会修改缓存内容。
                    // session.setAttribute("last_get_time", newTime);
                   

                    break;
                }

            } else {
                return new EXEException("","参数不足:找不到前端code-->"+ headCode,"");
            }
        } catch (Exception e) {
            return new EXEException("","服务器错误 - " + e.getMessage(),"");
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("提取设备从缓存，花费时间=" + (endTime - startTime));
        
//        Element ele = new Element("info");
//        ele.addAttribute(new Attribute("last_get_time", "2010-03-10 09:43:56"));
//        ele.addAttribute(new Attribute("value", "3|150000X04|Fail:检测不到设备|#$#5|150000X05|Work:|#$#2|R5|Idle:|level:231;fm-modulation:57;am-modulation:75;band:1;freq:828;offset:-2;attenuation:2;bandwidth:5;tasktype:收测任务;#$#1|110000R0101|Work:工作|outputlinelevel:232;inputlinelevel:231;linerrequency:53.3;batterylevel:36.2;upsstatus:255;#$#4|150000X04|Fail:检测不到设备|#$#6|150000X09|Work:|#$#2|R7|Work:工作|level:231;fm-modulation:57;am-modulation:75;band:1;freq:828;offset:-2;attenuation:2;bandwidth:5;tasktype:收测任务;"));
//        list.add(ele);
        return asobjres;
    }
    /**
     * 频谱数据截图
     * @detail  保存到频谱数据历史表中
     * @method  
     * @param asobj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-25 下午04:25:10
     */
    public Object savePicture(ASObject asobj){
		ArrayList spectrumArr = (ArrayList) asobj.get("spectrumArr");
		String headcode = (String) asobj.get("headcode");
		String head_id = (String) asobj.get("head_id");
		String receiverCode = (String) asobj.get("receiverCode");
		String band = (String) asobj.get("band");
		String store_datetime = (String) asobj.get("store_datetime");
		ArrayList list = new ArrayList();
		for(int i=0;i<spectrumArr.size();i++){
			SpectrumHistoryReportBean bean = new SpectrumHistoryReportBean();
			ASObject obj = (ASObject)spectrumArr.get(i);
			String freq = (String) obj.get("freq");
			String spectrum = (String) obj.get("spectrum");
			bean.setBand(band);
			bean.setEqu_code(receiverCode);
			bean.setHead_id(head_id);
			bean.setScandatetime(store_datetime);
			bean.setFreq(freq);
			bean.setLevel(spectrum);
			bean.setReport_type("2");
			bean.setHead_id(head_id);
			list.add(bean);
		}
		RadioSpectrumHistoryReport report = new RadioSpectrumHistoryReport();
		try {
			report.data2Db(list);
		} catch (UpMess2DBException e) {
			LogTool.fatal(e);
            return new EXEException("","频谱数据截图异常："+ e.getMessage(),"");
		}
    	return "频谱数据截图成功";
    }
}
