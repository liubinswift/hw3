package com.viewscenes.web.daily.dataAnalyse;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.business.SiteVersionUtil;
import com.viewscenes.web.runplan.gjt_runplan.GJTRunplan;

import flex.messaging.io.amf.ASObject;

public class DataAnalyse {
	public Object queryHistoryQualityData(ASObject asobj){
		 ArrayList<Element> list = new ArrayList<Element>();
	        // Element root = StringTool.getXMLRoot(msg);
	        long startT = System.currentTimeMillis();
	        String dataType = null;
	        try {
	            dataType = (String) asobj.get("typeCondition");
	            String code = (String) asobj.get("code");
	            String headendID = (String) asobj.get("headendID");
	            String receiverCode = (String) asobj.get("receiverCode");// 接收机代码
	            String band = (String) asobj.get("band");
	            String frequency = (String) asobj.get("freq");// 频率
	            String interval = (String) asobj.get("intervalTime");// 上报间隔
	            String checktime = (String) asobj.get("checktime");// 检查时间 
	            String theStartTime = (String) asobj.get("startTime");//查询开始时间
	            String theEndTime = (String) asobj.get("endTime");//查询结束时间
	            String timespan = (String) asobj.get("timespan"); // 数据时长
//	            String frontier = (String) asobj.get("frontier");
	            if (receiverCode == null) {
	                receiverCode = "";
	            }


	            
	           


	            if (headendID != null && receiverCode != null && frequency != null
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
//	                int idt = new Integer(dataType).intValue();
	                String[] typeArr = dataType.split(",");
	                String subTypeSql = "";
	                dataType  = "";
	                for(int x=0;x<typeArr.length;x++){
//	                	if(typeArr[x].equals("2")){// 瞬时调制度只取一条记录
//	                		subTypeSql = " select realtime_id,value1,value2,check_datetime,type_id "
//				                        + " from radio_quality_realtime_tab where head_id="
//				                        + headendID
//				                        + " and frequency='"
//				                        + frequency
//				                        + "'"
//				                        + " and type_id=2"
//				                        + subsql
//				                        + " order by check_datetime,realtime_id";
//	                		subTypeSql = " union "+StringTool.processSql(subTypeSql, 0, 2);
//	                		continue;
//	                	}
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

	                
	                String sql = "select result_id,value1,value2,check_datetime,type "
                        + " from Radio_Quality_Result_Tab where head_id in("
                        + headendID
                        + ") and frequency='"
                        + frequency
                        + "'"
                        + " and type in("
                        + dataType
                        + ") and check_datetime>=to_date('"+theStartTime+"','yyyy-mm-dd hh24:mi:ss') "
                        + " and check_datetime<=to_date('"+theEndTime+"','yyyy-mm-dd hh24:mi:ss') "
                        + subsql
                        + " order by check_datetime,result_id";
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
//	                if (idt == 2) { // 瞬时调制度只取一条记录
//	                    sql = StringTool.processSql(sql, 0, 2);
//	                    result_set = DbComponent.Query(sql);
//	                } else {
//	                    result_set = DbComponent.Query(sql);
//	                }
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
	                 switch (Integer.parseInt(result_set.getString(i, "type"))) {
	                   case 1: // level
	                       if (value1Array.length > 0) {
		                	   e[0] = "10";
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
	                       if (value1Array.length > 0) {
		                	   e[0] = "10";
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
	                 if (value1Array.length > 0) {
	                	   e[0] = "10";
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
	                       if (value1Array.length > 0) {
		                	   e[0] = "10";
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
	                	   e[0] = "110";
	                       if (i > 0) {
	                           break;
	                       }
	                       if (value1Array.length > 0) {
	                           e[4] = e[5] = "瞬时调制度分布";
	                           value1Array = getFMData(value1Array, "",
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


	                }
	                // showLog("total has:" + result_set.getRowCount() + ", get " +
	                // i+", data num:"+graphDataSet.getRowCount());
	                //list.add(element);
	                long endT = System.currentTimeMillis();
	                System.out.println(new Timestamp(System.currentTimeMillis()) + "applet"
	                        + dataType + "提取数据时间差：" + (endT - startT));
//	                return "1<fen><xml>"+graphDataSet1.toString()+"</xml>#@#"+
//	                		"2<fen><xml>"+graphDataSet2.toString()+"</xml>#@#"+
//	                		"3<fen><xml>"+graphDataSet3.toString()+"</xml>#@#"+
//	                		"5<fen><xml>"+graphDataSet5.toString()+"</xml>#@#"+
//	                		"6<fen><xml>"+graphDataSet6.toString()+"</xml>#@#"+
//	                		"8<fen><xml>"+graphDataSet8.toString()+"</xml>";
	                System.out.println("<xml>1#fen#"+graphDataSet1.toString()+"#@#"+
            		"2#fen#"+graphDataSet2.toString()+"#@#"+
            		"3#fen#"+graphDataSet3.toString()+"#@#"+
            		"5#fen#"+graphDataSet5.toString()+"#@#"+
            		"6#fen#"+graphDataSet6.toString()+"#@#"+
            		"8#fen#"+graphDataSet8.toString()+"</xml>");
	                return "<xml>1#fen#"+graphDataSet1.toString()+"#@#"+
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
	            return new EXEException("",e.getMessage(),"");
	        }
	}
	
	/**
     * 查询频谱历史画图数据
     * @param msg
     * @return
     * @throws DbException
     * @throws GDSetException
     */
    public String querySpectrumData(ASObject asobj){


           String startTime=(String) asobj.get("startTime");
           String endTime = (String) asobj.get("endTime");
           String showMaxLevel=(String) asobj.get("showMaxLevel");//显示最大电平
           String band = (String) asobj.get("band");
           String receiveCode = (String) asobj.get("receiveCode");
           String startFreq = (String) asobj.get("startFreq");
           String endFreq = (String) asobj.get("endFreq");
           String headendID = (String) asobj.get("headendID");
           String spectrumType = (String) asobj.get("spectrumType");//频谱类型 0,1频谱任务数据；2实时频谱截图数据
           StringBuffer sql= new StringBuffer();

           String result;
           ArrayList list = new ArrayList();

           String sqllevel ="t.e_level as e_level";
           if(showMaxLevel.equals("1")){
               sqllevel = "max(t.e_level) as e_level";
           }
           //sql = "select SG_ID,	UPLOAD_TIME,REPORTTIME,RESUME_TIME,round(ABNORMALTIME,0) ABNORMALTIME,CH_NAME,HEAD_NAME,TYPE_NAME,et.name,SUBCENTER_HANDLE_TIME,dd_reason from RES_CABLE_SIGNAL_ALARM_TAB sa,res_cable_alarm_error_type_tab et where  SG_ID>0 and sa.ERROR_TYPE=et.type_id order by REPORTTIME" ;  //地点
           sql.append( "  select "+sqllevel+",t.frequency,t.band from radio_spectrum_result_tab t where t.is_delete =0   ") ;

           if(startTime.length()>0)
               sql.append(" and check_datetime >= '"+startTime+"'");

           if(endTime.length()>0)
               sql.append(" and check_datetime <= '"+endTime+"' ");

//                       if(station.length() > 0)
//                           sql.append(" and head_id in(select head_id from res_headend_tab where shortname='"+station+"')");  // name like '"+station+"') 修改为shortname查询 10/05/20

           if(headendID.length()>0)
               sql.append(" and head_id in( "+headendID+") ");

           if(receiveCode.length()>0)
               sql.append(" and  equ_code = '"+receiveCode+"'");

           if(band.length()>0)
               sql.append(" and band = '"+band+"' ");

           if(startFreq.length()>0)
               sql.append(" and frequency >= '"+startFreq+"' ");

           if(endFreq.length()>0)
               sql.append(" and frequency <= '"+endFreq+"' ");
           if(spectrumType!= null && endFreq.length()>0)
               sql.append(" and report_type in ("+spectrumType+") ");
           

           if(showMaxLevel.equals("1")){
               sql.append(" group by frequency,band ");
           } else {
               //sql.append(" order by ");
           }
           
           //sql.append("order by check_datetime desc");



           try {

               // 构造结果集
                  String e[] = { "index", "color", "x", "y", "description",
                          "desc", "url" };
                  GDSet graphDataSet = new GDSet("graphdata", e);
            GDSet gdSet = null;
            gdSet = DbComponent.Query(sql.toString());
            Document doc = StringTool.getXmlMsg();
            Element rootNode = doc.getRootElement();
      
            for (int i = 0; i < gdSet.getRowCount(); i++) {
                String resfrequency = "";
                String reslevel = "";
                if(gdSet.getString(i, "band")==null|| gdSet.getString(i, "band").equals("")){//边境电视
                	e[2] = gdSet.getString(i, "frequency");//MHz
                } else{
                	if (gdSet.getString(i, "band").equalsIgnoreCase("1")) {
                        e[2] = gdSet.getString(i, "frequency");
                    } else { // 短波，调频，单位转MHz
                        e[2] = new Float((new Float(gdSet.getString(i,
                                "frequency")).floatValue() / 1000)).toString();
                    }
                }
                e[3] = gdSet.getString(i, "e_level");
                graphDataSet.addRow(e);


            }
         
            return "<xml>"+graphDataSet.toString()+"</xml>";
        } catch (Exception e) {
            LogTool.fatal("查询频谱历史画图数据异常",e);
            return StringTool.getXmlErrorMessage("查询频谱历史画图数据异常："+e.getMessage());
        }

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
     * 通过前端CODE查询前端信息、接收机、任务freq
     *
     * @param
     * @return freq
     */
//    public Object getHeadendInfo(ASObject obj)  {
//        String responseStr;
//        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
//
//        String headendCode =(String)obj.get("headendCode"); // 站点code
//         boolean bVedio = false;
////        if (frontier != null && frontier.equalsIgnoreCase("tv")) {
////            //bVedio = true;
////        } else {
////            bVedio = false;
////        }
//        try {
//
//            // 站点代码不为空
//            if (headendCode != null && headendCode.length() > 0) {
//
//                // 查询前端信息
//                String headendName = SiteVersionUtil.getSiteName(headendCode);
//                String headendVersion = SiteVersionUtil
//                        .getSiteVerStr(headendCode);
//                String type_id = SiteVersionUtil.getSiteType(headendCode);
//
//
//                // 获取频率列表
//                Vector progVector = new Vector();
//                Calendar nowCal = Calendar.getInstance();
//                String nowDate = StringTool.Date2String(nowCal.getTime());
//                
//    			GJTRunplan gjtRunplan = new GJTRunplan();
//    			GJTRunplanBean bean = new GJTRunplanBean();
//    			bean.setStartRow(1);
//    			bean.setEndRow(100);
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
//					program.put("label",grlbean.getProgram_name()+"["+grlbean.getFreq()+"]");
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
//        		
//                
//                
//                
//              
//            } else {
//                return new EXEException("","未能找到指定站点","");
//            }
//        } catch (Exception ex) {
//            LogTool.fatal(ex);
//            return new EXEException("","通过前端CODE查询前端信息、接收机、任务freq错误:"+ ex.getMessage(),"");
//        }
//        return list;
//    }
}
