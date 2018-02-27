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
 * Title: ʵʱָ��
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
     * �·�ʵʱָ��ɨ������
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-7-28 ����04:45:04
     */
    public Object getSpectrumRealtimeQuery(ASObject obj){
    	RealtimeMonitorDevice device = new RealtimeMonitorDevice();
    	Object resObj = device.getSpectrumRealtimeQuery(obj);
    	return resObj;
    }
    
    /**
     * �·�ʵʱƵ��ɨ������
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-7-28 ����04:45:04
     */
    public Object sendQualityRealtimeAction(ASObject obj){
    	RealtimeMonitorDevice device = new RealtimeMonitorDevice();
    	Object resObj = device.getQualityRealtimeQuery(obj);
    	return resObj;
    }
    
    /**
     * ͨ��ǰ��CODE��ѯǰ����Ϣ�����ջ�������freq
     *
     * @param
     * @return freq
     */
    public Object getHeadendInfo(ASObject obj)  {
        String responseStr;
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();

        String headendCode =(String)obj.get("headendCode"); // վ��code
         boolean bVedio = false;
//        if (frontier != null && frontier.equalsIgnoreCase("tv")) {
//            //bVedio = true;
//        } else {
//            bVedio = false;
//        }
        try {

            // վ����벻Ϊ��
            if (headendCode != null && headendCode.length() > 0) {

                // ��ѯǰ����Ϣ
//                String headendName = SiteVersionUtil.getSiteName(headendCode);
//                String headendVersion = SiteVersionUtil
//                        .getSiteVerStr(headendCode);
//                String type_id = SiteVersionUtil.getSiteType(headendCode);

                // ��ȡ���ջ��б�
                ArrayList equList = new ArrayList();
                Object equObj = this.getEquList(headendCode);
                if(equObj instanceof ArrayList){ 
                    list.add((ArrayList)equObj);
                } else {
                	return equObj;
                }

                // ��ȡƵ���б�
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
//				program1.put("label","ָ��Ƶ��");
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
                return new EXEException("","δ���ҵ�ָ��վ��","");
            }
        } catch (Exception ex) {
            LogTool.fatal(ex);
            return new EXEException("","ͨ��ǰ��CODE��ѯǰ����Ϣ�����ջ�������freq����:"+ ex.getMessage(),"");
        }
        return list;
    }
    /**
     * ��ȡ���ջ��б�
     * @detail  
     * @method  
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-10 ����10:04:15
     */
    public Object getEquList(String headendCode){
    	Common common = new Common();
    	Object equList = common.getEquListNew(headendCode);
    	
    	return equList;
    }
    /**
     * ��ȡͼ������
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
//            String receiverCode = (String) asobj.get("receiverCode");// ���ջ�����
//            String band = (String) asobj.get("band");
//            String frequency = (String) asobj.get("freq");// Ƶ��
//            String interval = (String) asobj.get("intervalTime");// �ϱ����
//            String checktime = (String) asobj.get("checktime");// ���ʱ��
//            String timespan = (String) asobj.get("timespan"); // ����ʱ��
//            String frontier = (String) asobj.get("frontier");
            dataType = request.getParameter("data");
            Vector param = parseRequest(request);
            String headendID = getParam(param, "headendID"); //ǰ��ID
            String receiverCode = getParam(param, "receiverCode"); //���ջ�����
            if (receiverCode == null) {
                receiverCode = "";
            }
            String frequency = getParam(param, "frequency"); //Ƶ��
            String headendType = getParam(param, "headendType"); //ǰ������
            String band = getParam(param, "band"); // ����
            if (headendID != null && !headendID.equals("") && dataType != null && 
            		dataType.equalsIgnoreCase("100")&&(band!=null&&!band.equals(""))) {
                return getScanData(request);
            }

            String interval = request.getParameter("interval"); //�ϱ����
            String checktime = request.getParameter("checktime"); //���ʱ��
            String timespan = request.getParameter("timespan"); //����ʱ��

            
           


            if (headendID != null && !headendID.equals("") && receiverCode != null && frequency != null
                    && dataType != null) {
                long interValue = 5;
                if (interval != null) {
                    interValue = new Long(interval).longValue();
                }

                // ��������
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
                	if(typeArr[x].equals("2")){// ˲ʱ���ƶ�ֻȡһ����¼
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
//                if (idt == 2) { // ˲ʱ���ƶ�ֻȡһ����¼
//                    sql = StringTool.processSql(sql, 0, 2);
//                    result_set = DbComponent.Query(sql);
//                } else {
//                    result_set = DbComponent.Query(sql);
//                }
                result_set = DbComponent.Query(sql);
                long endTime = 0; // ��ֹʱ��
                long resultTime = 0; // ���ݲ���ʱ��
                boolean randomData = false;

                for (i = 0; i < result_set.getRowCount(); i++) {
                    // ��ȡ��¼����ʱ��
                    randomData = false;
                    resultTime = StringTool.stringToDate(
                            result_set.getString(i, "check_datetime"))
                            .getTime();
                    if (resultTime < checkTime) {
                        randomData = true;
                    }
                    if (i == 0 && timespan != null) {
                        // �������ʱ��
                        endTime = resultTime + new Long(timespan).longValue();
                    }
                    // if (i > 0 && endTime > 0)
                    // break; //��������ʱ�䲻����
                    if (result_set.getRowCount() <= interValue + 1) { // ȡʱ�����ڵ�
                        if (resultTime >= endTime && endTime > 0) {
                            break;
                        }
                    } else { // �����ϱ�������������
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
                           e[4] = e[5] = "��ƽ";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // ʱ�����
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
                           e[4] = e[5] = "������";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // ʱ�����
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
		                     e[4] = e[5] = "����";
		                     newInterval = 1000 / value1Array.length;
		                     for (k = 0; k < value1Array.length; k++) {
		                         // ʱ�����
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
                           e[4] = e[5] = "Ƶƫ";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // ʱ�����
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
                           e[4] = e[5] = "˲ʱ���ƶȷֲ�";
                           value1Array = getFMData(value1Array, headendType,
                                   randomData);
                           for (k = 0; k < value1Array.length; k++) {
                               // Ƶ�����
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
                           e[4] = e[5] = "�����ƶ� ����";
                           newInterval = 1000 / value2Array.length;
                           for (k = 0; k < value2Array.length; k++) {
                               // ʱ�����
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
                           e[4] = e[5] = "�����ƶ� ����";
                           newInterval = 1000 / value1Array.length;
                           for (k = 0; k < value1Array.length; k++) {
                               // ʱ�����
                               e[2] = new Long(resultTime + k * newInterval)
                                       .toString();
                               e[3] = value1Array[k];
                               e[7] = "5";
                               graphDataSet5.addRow(e);
                           }
                       }
                       break;
                  
                   }


                    // ɾ���ѻ�ȡ��¼
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
                        + dataType + "��ȡ����ʱ��" + (endT - startT));
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
                throw new Exception("�����������");
            }
        } catch (Exception e) {
            LogTool.fatal(e);
            return StringTool.getXmlErrorMessage("ErrorMessage", "���������� - "
                    + e.toString());
        }

    }
public  static void main(String[] args)
{
	String str="10$$1403071757000$38.1$��ƽ$��ƽ$$1$#@#2#fen#";
	String str2="10$$1403071756000$40$����$����$$8$";
	String bandvalue=str2.substring(str2.indexOf("����")-8,str2.indexOf("����"));
	String levelvalue=str.substring(str.indexOf("��ƽ")-8,str.indexOf("��ƽ"));
	System.out.println(bandvalue.split("\\$")[1]);
	System.out.println(levelvalue.split("\\$")[1]);
	
}
    /**
     * ����˲ʱ���ƶ�
     *
     * @param val
     * @return
     */
    private String[] getFMData(String[] val, String type, boolean randomData) {
        String returnValue[] = new String[val.length];
        double rValue = 0;
        double tValue = 0; // ��ֵ
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
     * ��ȡƵ������
     *
     * @param request
     * @param opDetail
     * @return
     */
    public String getScanData(HttpServletRequest request) {
        long startT = System.currentTimeMillis();
        boolean returnValue = false;
        try {
         // ��������
            String b[] = { "index", "color", "x", "y", "description",
                    "desc", "url" };
            GDSet graphDataSet = new GDSet("graphdata", b);
            Vector param = parseRequest(request);
            String headendID = getParam(param, "headendID"); // ǰ��ID
            String receiverCode = getParam(param, "receiverCode"); // ���ջ����
            String band = getParam(param, "band"); // ����
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
                    // ��ȡ��¼ʱ��
                    resultTime = StringTool.stringToDate(
                            result_set.getString(i, "check_datetime"))
                            .getTime();
                    resultFreq = new Double(result_set.getString(i, "frequency"))
                            .longValue();
//                    if (i == 0) {
//                        // ��¼�ϴ�ʱ����Ƶ��
//                        lastFreq = resultFreq;
//                        lastTime = resultTime;
//                        // ���ý�ֹʱ��
//                    }
//                    // ѭ��һ����
//                    if (resultFreq <= lastFreq && resultTime > lastTime) {
//                        // showLog("delete old spec band" + resultFreq + ":" +
//                        // lastFreq);
//                        String subsql = "";
//                        if (!receiverCode.equalsIgnoreCase("")) {
//                            subsql = " and equ_code='" + receiverCode + "' ";
//                        }
//                        if (frontier.equals("radio")) {//�߾��㲥
//                        	subsql += " and signaltype='radioup' ";
//                        	subsql+= " and band = " + band;
//                        } else if (frontier.equals("tv")) {//�߾�����
//                        	subsql+= " and signaltype='tvup' ";
//                        	subsql += " and band  is null";
//                        } else {//v8�㲥
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
                    // ��¼�ϴ�ʱ����Ƶ��
                    lastFreq = resultFreq;
                    lastTime = resultTime;
                    if(result_set.getString(i, "band")==null|| result_set.getString(i, "band").equals("")){//�߾�����
                    	e[2] = result_set.getString(i, "frequency");//MHz
                    } else{
                    	if (result_set.getString(i, "band").equalsIgnoreCase("1")) {
	                        e[2] = result_set.getString(i, "frequency");
	                    } else { // �̲�����Ƶ����λתMHz
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
                        + "Ƶ��applet��ȡ����ʱ��" + (endT - startT));
                return "<xml>"+graphDataSet.toString()+"</xml>";
            } else {
                throw new Exception("��������");
            }
        } catch (Exception e) {
            LogTool.fatal("��ȡƵ�������쳣��"+e);
        }
       return "";
    }

    /**
     * ��ȡ�������
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
     * ��������
     *
     * @param request
     * @param getReceiver
     * @param opDetail
     * @return
     */
    private Vector parseRequest(HttpServletRequest request) throws Exception{
        Vector returnValue = new Vector();

        String headendID = request.getParameter("headendid"); // ǰ��ID
        String headendCode = request.getParameter("headendcode"); // ǰ�˱��
        /**
         * ��������Ϊ��ʱ���ȴ��ڴ���ת�������ݿ��е�ʵ��ֵ�� ���ִ�Сд���ܡ� lixuefeng2005-12-08
         */
        if (headendCode != null && headendCode.length() > 0) {
            headendCode = SiteVersionUtil.getSiteOriCode(headendCode);
            headendID = SiteVersionUtil.getSiteHeadId(headendCode);
            String receiverCode = request.getParameter("receivercode"); // ���ջ����
            String band = request.getParameter("band"); // ����
            String frequency = request.getParameter("frequency"); // Ƶ��
            String headendType = SiteVersionUtil.getSiteType(headendCode);
            String headendName = SiteVersionUtil.getSiteName(headendCode);
//            String headendMaker = SiteVersionUtil.getSiteManu(headendCode);
            if (receiverCode == null) {
                receiverCode = "";
            } 
//            else if (!receiverCode.equalsIgnoreCase("")) {
//                try {
//                    //��ѯ���õĽ��ջ����鲻����Ϊ�ա�
//                    String sql = "select code from dic_headend_receiver_tab where head_type_id="
//                            + headendType + " and code='" + receiverCode + "'";
//                    GDSet result_set = DbComponent.Query(sql);
//                    if (result_set.getRowCount() <= 0) {
//                        //��վ���Զ�ѡ����ջ�
//                        receiverCode = "";
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
            //�����֤������Ϣ
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
                //��Ƶ�ʼ��㲨��
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
            throw new Exception("warning:վ���������");
        }
        return returnValue;
    }

    /**
     * ��ѯ��������
     *
     * @param request
     * @param opDetail
     * @return
     */
    public Object getLevel(ASObject asobj) {
        String level = "";
        String data = "";
        try {

            String headendCode = (String) asobj.get("code"); // ǰ�˴���
            String headendID = (String) asobj.get("headendID"); // ǰ��ID
            String frequency = (String) asobj.get("freq"); // Ƶ��
            String band = (String) asobj.get("band"); // ����
            String receiverCode = (String) asobj.get("receiverCode"); //���ջ�
            data = (String) asobj.get("dataType"); // ͼ�����͡�

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
                 * ����ͼ�����ͣ�������ѯ���ֶΡ�
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
                if (data.equalsIgnoreCase("1")) {//��ƽ
                    lowLevelName = "downthreshold1level";
                } else if (data.equalsIgnoreCase("3")) {//������
                    lowLevelName = "downthreshold3am";
                    highLevelName = "upthreshold3am";
                } else if (data.equalsIgnoreCase("5")) {//���ƶ�
                    lowLevelName = "downthreshold2fm";
                    highLevelName = "upthreshold2fm";
                }
                if (lowLevelName.length() > 0 || highLevelName.length() > 0) {
                    GDSet set1;
                    /**
                     * ����headid�����ջ���frequency������Ӧ�Ĳ�����
                     */
                    if (frequency != null) {
                        if (receiverCode == null || receiverCode.length() <= 0) {
                            receiverCode = "All";
                        }
                        /**
                         * �Ȳ�ѯ�Լ���ֵ�����û�У����β�ѯ��ӦĬ��ֵ��
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
                        // �鲻������˲����µ�Ĭ��ֵ��
                        band=SiteRunplanUtil.getBandFromFreq(frequency);
                        if (set1 == null || set1.getRowCount() <= 0) {
                            sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                    + headendID
                                    + "'"
                                    + " and band='"
                                    + band + "'" + " and FREQUENCY='0'";
                            set1 = DbComponent.Query(sql);
                        }
                        // �鲻������˽��ջ����µ�Ĭ��ֵ��
                        if (set1 == null || set1.getRowCount() <= 0) {
                            sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                    + headendID
                                    + "'"
                                    + " and EQU_CODE='"
                                    + receiverCode + "'" + " and FREQUENCY='0'";
                            set1 = DbComponent.Query(sql);
                        }
                        // ��Ϊ�գ�������ջ��о���ֵ���ٲ�ѯ���ջ�ΪAllĬ��ʱ��Ĭ��ֵ��û�С�
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
                        //��Ϊ�գ���ѯĬ��ֵ��
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
                         // ��Ϊ�գ�������ջ��о���ֵ���ٲ�ѯ���ջ�ΪAllĬ��ʱ��Ĭ��ֵ��û�С�
                             sql = " select * from radio_quality_alarm_param_tab where head_id='"
                                     + headendID
                                     + "'"
                                     + " and EQU_CODE='All'"
                                     + " and FREQUENCY='0'";
                             set3 = DbComponent.Query(sql);
                         //��Ϊ�գ���ѯĬ��ֵ��
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
                throw new Exception("��������");
            }
        } catch (Exception e) {
            LogTool.fatal("��ѯ���������쳣��"+e);

            return new EXEException("","��ѯ���������쳣��"+ e.getMessage(),"");
        }
        ASObject resobj = new ASObject();
        resobj.put("dataType",data);
        resobj.put("level",level);
        return resobj;
    }

    /**
     * ���ñ�������
     *
     * @return
     */
    public Object setLevel(ASObject asobj) {
        try {
            String userID = (String) asobj.get("user_id");//
            String headendCode = (String) asobj.get("code");// ǰ�˴���
            String headendName = "";
            String headendID = ""; // ǰ�˴���
            String frequency = (String) asobj.get("freq"); // Ƶ��
            String band = (String) asobj.get("band"); // ����
            String receiverCode = (String) asobj.get("receiverCode"); // ���ջ�
            String data = (String) asobj.get("dataType"); // ͼ�����͡�
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
//                        cmdStr += "��ƽ���ͱ�������:" + lowLevel + " ";
//                    }
//                } else if (data.equalsIgnoreCase("3")
//                        || data.equalsIgnoreCase("5")) {
//                    lowLevelName = "downthreshold3am";
//                    if (lowLevel.length() > 0) {
//                        cmdStr += "�����ȹ��ͱ�������:" + lowLevel + " ";
//                    }
//                    highLevelName = "upthreshold3am";
//                    if (highLevel.length() > 0) {
//                        cmdStr += ",�����ȹ��߱�������:" + highLevel + " ";
//                    }
//                }
                if (data.equalsIgnoreCase("1")) {//��ƽ
                    lowLevelName = "downthreshold1level";
                    if (lowLevel.length() > 0) {
                        cmdStr += "��ƽ���ͱ�������:" + lowLevel + " ";
                    }
                } else if (data.equalsIgnoreCase("3")) {//������
                    lowLevelName = "downthreshold3am";
                    if (lowLevel.length() > 0) {
                        cmdStr += "�����ȹ��ͱ�������:" + lowLevel + " ";
                    }
                    highLevelName = "upthreshold3am";
                    if (highLevel.length() > 0) {
                        cmdStr += ",�����ȹ��߱�������:" + highLevel + " ";
                    }
                } else if (data.equalsIgnoreCase("5")) {//���ƶ�
                    lowLevelName = "downthreshold2fm";
                    highLevelName = "upthreshold2fm";
                    
                    lowLevelName = "downthreshold2fm";
                    if (lowLevel.length() > 0) {
                        cmdStr += "���ƶȹ��ͱ�������:" + lowLevel + " ";
                    }
                    highLevelName = "upthreshold2fm";
                    if (highLevel.length() > 0) {
                        cmdStr += ",���ƶȹ��߱�������:" + highLevel + " ";
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
//                        if (!data.equalsIgnoreCase("100")) { // ��Ƶ�ס�
//                            MonDataByDeviceToDB device = new MonDataByDeviceToDB();
//                          //�������ȼ�
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
                  //ɾ��ԭ��¼
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
               throw new Exception("��������");
            }
        } catch (Exception ex) {
            LogTool.fatal("���ñ�����������"+ex);

            return new EXEException("","���ñ��������쳣��"+ ex.getMessage(),"");
        }
        return "���óɹ�";
    }
    
    /**
     * �豸ʵʱ״̬��ѯ
     * @detail  
     * @method  
     * @param obj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-10 ����10:44:48
     */
    public Object UpEquipmentStatusRealtimeQuery(ASObject obj){
    	RealtimeMonitorDevice device = new RealtimeMonitorDevice();
    	Object resObj = device.UpEquipmentStatusRealtimeQuery(obj);
    	return resObj;
    }
    
    /**
     * ��ȡ�豸ʵʱ״̬
     * @detail  
     * @method  
     * @param msg
     * @return 
     * @return  String  
     * @author  zhaoyahui
     * @version 2012-8-10 ����10:44:53
     */
    public Object getEquDataFromCache(ASObject asobj){
        String result = "";
        ASObject asobjres = new ASObject();
        asobjres.put("last_get_time", "");
        asobjres.put("dataList", "");
        long startTime = System.currentTimeMillis();
        try {
            // ǰ��ID
            String headCode = (String) asobj.get("code");
            String last_get_time = (String) asobj.get("last_get_time");
            headCode = SiteVersionUtil.getSiteOriCode(headCode);
            String headId = SiteVersionUtil.getSiteHeadId(headCode);

            // �ϴ���ȡ���ݵ����ʱ�䡣
            // ȡ����ʱ��ҳ����ʾ���´���ȡʱ�䡣
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
                    // ����1������ݡ�
                    HashMap valueMap = (HashMap) (statusMap[getI].clone());
                    // ���Ϊ�գ�ȡ��һ��.
                    if (valueMap == null || valueMap.size() == 0) {
                        continue;
                    }

                    // ���ʱ��(����)Ϊ�գ�ȡ��һ���������´���ȡ������
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
                    //����ҵ����޸����ݡ�ע�����û�п�¡���޸Ļ������ݡ�
                    // session.setAttribute("last_get_time", newTime);
                   

                    break;
                }

            } else {
                return new EXEException("","��������:�Ҳ���ǰ��code-->"+ headCode,"");
            }
        } catch (Exception e) {
            return new EXEException("","���������� - " + e.getMessage(),"");
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("��ȡ�豸�ӻ��棬����ʱ��=" + (endTime - startTime));
        
//        Element ele = new Element("info");
//        ele.addAttribute(new Attribute("last_get_time", "2010-03-10 09:43:56"));
//        ele.addAttribute(new Attribute("value", "3|150000X04|Fail:��ⲻ���豸|#$#5|150000X05|Work:|#$#2|R5|Idle:|level:231;fm-modulation:57;am-modulation:75;band:1;freq:828;offset:-2;attenuation:2;bandwidth:5;tasktype:�ղ�����;#$#1|110000R0101|Work:����|outputlinelevel:232;inputlinelevel:231;linerrequency:53.3;batterylevel:36.2;upsstatus:255;#$#4|150000X04|Fail:��ⲻ���豸|#$#6|150000X09|Work:|#$#2|R7|Work:����|level:231;fm-modulation:57;am-modulation:75;band:1;freq:828;offset:-2;attenuation:2;bandwidth:5;tasktype:�ղ�����;"));
//        list.add(ele);
        return asobjres;
    }
    /**
     * Ƶ�����ݽ�ͼ
     * @detail  ���浽Ƶ��������ʷ����
     * @method  
     * @param asobj
     * @return 
     * @return  Object  
     * @author  zhaoyahui
     * @version 2012-8-25 ����04:25:10
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
            return new EXEException("","Ƶ�����ݽ�ͼ�쳣��"+ e.getMessage(),"");
		}
    	return "Ƶ�����ݽ�ͼ�ɹ�";
    }
}
