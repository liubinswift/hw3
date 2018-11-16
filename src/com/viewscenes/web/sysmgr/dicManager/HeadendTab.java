package com.viewscenes.web.sysmgr.dicManager;


import java.util.ArrayList;
import java.util.HashMap;

import java.io.File;


import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;



import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.util.InnerDevice;

import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.pub.exception.AppException;
import com.viewscenes.web.sysmgr.dicManager.DicDao;
import com.viewscenes.web.sysmgr.dicManager.DicService;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.sys.TableInfoCache;

import flex.messaging.io.amf.ASObject;

public class HeadendTab {

	private static  HashMap<String, String> headMap ;
	static {
		headMap = new HashMap<String, String>();
		headMap.put("???_???_NI_V8", "b");
		headMap.put("???_???_713_V8", "d");
		headMap.put("???_???_FHD_V8", "d");
		headMap.put("????_???_VS_V8", "d");
		headMap.put("??_???_AMFT_V8", "d");
		headMap.put("??22?_???_V8", "d");
		headMap.put("??_???_TS_V8", "d");

		headMap.put("???_???_NI_V8", "d");
		headMap.put("???_???_713_V8", "d");
		headMap.put("???_???_FHD_V8", "d");
		headMap.put("????_???_VS_V8", "d");
		headMap.put("??22?_???_V8", "d");
		headMap.put("??_???_AMFT_V8", "d");
		headMap.put("??_???_TS_V8", "d");

	}

		/**
         *
         * <p>class/function:com.viewscenes.web.sysmgr.dicManager
         * <p>explain:查询前端数据 ，如果查询出错会返回错误信息
         * <p>author-date:张文 2012-7-26
         * @param:
         * @return:
         */
	
    public Object getHeadend(ASObject obj){
    	return new DicService().getHeadend(obj);
    }
    

	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:添加前端数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 * @throws DbException 
	 * @throws GDSetException 
	 */
	 
	public Object AddHead(ASObject asObj) throws DbException, GDSetException{
		ArrayList<Object> list = new ArrayList<Object>();
		String insertSql ="";
		String head_id = reString((String)asObj.get("id"));
		 String code = reString((String)asObj.get("code"));
         String shortname = reString((String)asObj.get("shortname"));
         String com_id = reString((String)asObj.get("com_id"));
         String com_protocol = reString((String)asObj.get("com_protocol"));
         String ip = reString((String)asObj.get("ip"));
         String longitude = reString((String)asObj.get("longitude"));
         String latitude = reString((String)asObj.get("latitude"));
         String comphone = reString((String)asObj.get("comphone"));
         String site = reString((String)asObj.get("site"));
         String address = reString((String)asObj.get("address"));
         String site_status = reString((String)asObj.get("site_status"));
         String com_status = reString((String)asObj.get("com_status"));
         String fault_status = reString((String)asObj.get("fault_status"));
         String station_name = reString((String)asObj.get("station_name"));
         String descript = reString((String)asObj.get("descript"));
         String state = reString((String)asObj.get("state")); 
       
         String country = reString((String)asObj.get("country"));
         String version = reString((String)asObj.get("version"));
         String occur_time = reString((String)asObj.get("occur_time"));
         String altitude = reString((String)asObj.get("altitude"));
         String summertime= reString((String)asObj.get("summertime"));
         
//         String summertime_end = reString((String)asObj.get("summertime_end"));
         String ciraf = reString((String)asObj.get("ciraf"));
         String person = reString((String)asObj.get("person"));
         String person_phone = reString((String)asObj.get("person_phone"));
         String principal = reString((String)asObj.get("principal"));
         String principal_phone = reString((String)asObj.get("principal_phone"));
         String time_diff = reString((String)asObj.get("time_diff"));
         String default_language = reString((String)asObj.get("default_language"));
         String x = reString((String)asObj.get("x"));

         String y = reString((String)asObj.get("y"));
         String url = reString((String)asObj.get("url"));
         String is_delete = (String)asObj.get("is_delete");
      
         String type_id = (String)asObj.get("type_id");
         String is_online = (String)asObj.get("is_online");
         String manufacturer = reString((String)asObj.get("manufacturer"));
         String post = reString((String)asObj.get("post"));
         String service_name = reString((String)asObj.get("service_name"));
         
             insertSql = "insert into res_headend_tab(head_id,code,com_id,com_protocol,ip,longitude,latitude,comphone,site,address,site_status,com_status,fault_status," +
             		"station_name,descript,state,country,version,occur_time,altitude,summertime,ciraf,person," +
             		"person_phone,principal,principal_phone,time_diff,default_language,x,y,url,is_delete,type_id,is_online,manufacturer,shortname,post,service_name) " +
        				 "values("+head_id+",'"+code.toUpperCase()+"','"+com_id+"','"+com_protocol+"','"+ip+"','"+longitude+"','"+latitude+"','"+comphone+"'," +
        				 		"'"+site+"','"+address+"','"+site_status+"','"+com_status+"','"+fault_status+"','"+station_name+"','"+descript+"','"+state+"'," +
        				 		"'"+country+"','"+version+"',to_date('"+occur_time+"','yyyy-mm-dd hh24:mi:ss'),'"+altitude+"'," +
        				 		"'"+summertime+"','"+ciraf+"','"+person+"'," +
        				 		"'"+person_phone+"','"+principal+"','"+principal_phone+"','"+time_diff+"','"+default_language+"','"+x+"','"+y+"','"+url+"'," +
        				 				"'"+0+"','"+type_id+"','"+is_online+"','"+manufacturer+"','"+shortname+"','"+post+"','"+service_name+"')";


		try {
			DbComponent.exeUpdate(insertSql.toString());
			ResHeadendBean ResHeadendBean = getHeadendByCode(code);
			TableInfoCache as = new TableInfoCache();
			as.refreshTableMap("res_headend_tab", ResHeadendBean,
					ResHeadendBean.getCode(), "1");
			InnerDevice.refreshInstance();

		} catch (DbException e) {
			e.printStackTrace();
			EXEException ex = new EXEException("", "," + e.getMessage(), "");
			list.add(ex);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("", "后台错误!", null);
		}

		return list;
	}
	
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:更新前端数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	 
	public Object updateHead(ASObject asObj) throws DbException{
		ArrayList<Object> list = new ArrayList<Object>();
		String updateSql = "";
		String head_id = (String) asObj.get("id");
		String url = (String) asObj.get("url");
        String code = reString((String)asObj.get("code"));
//         String shortname = reString((String)asObj.get("shortname"));
//         String com_id = reString((String)asObj.get("com_id"));
         String com_protocol = reString((String)asObj.get("com_protocol"));
        String ip = reString((String)asObj.get("ip"));
         String longitude = reString((String)asObj.get("longitude"));
         String latitude = reString((String)asObj.get("latitude"));
//         String comphone = reString((String)asObj.get("comphone"));
        String site = reString((String)asObj.get("site"));
//         String address = reString((String)asObj.get("address"));
//         String site_status = reString((String)asObj.get("site"));
//         String com_status = reString((String)asObj.get("com_status"));
//         String fault_status = reString((String)asObj.get("fault_status"));
//         String station_name = reString((String)asObj.get("station_name"));
//         String descript = reString((String)asObj.get("descript"));
//         String state = reString((String)asObj.get("state")); 
//         if(state!="")
//             Integer.valueOf(state);
//         String country = reString((String)asObj.get("country"));
//         String version = reString((String)asObj.get("version"));
//         String occur_time = reString((String)asObj.get("occur_time"));
//         String altitude = reString((String)asObj.get("altitude"));
//         String summertime = reString((String)asObj.get("summertime"));
//         
////         String summertime = reString((String)asObj.get("summertime_end"));
//         String ciraf = reString((String)asObj.get("ciraf"));
//         String person = reString((String)asObj.get("person"));
//         String person_phone = reString((String)asObj.get("person_phone"));
//         String principal = reString((String)asObj.get("principal"));
         String principal_phone = reString((String)asObj.get("principal_phone"));
        // String time_diff = reString((String)asObj.get("time_diff"));
         String default_language = reString((String)asObj.get("default_language"));
        // String is_delete = reString((String)asObj.get("is_delete"));
//         if(is_delete!="")
//             Integer.valueOf(is_delete);
//         String type_id = reString((String)asObj.get("type_id"));
//         if(type_id!="")
//             Integer.valueOf(type_id);
//         String is_online = reString((String)asObj.get("is_online"));
//         if(is_online!="")
//             Integer.valueOf(is_online);
         String manufacturer = reString((String)asObj.get("manufacturer"));
//         String post = reString((String)asObj.get("post"));
//         String service_name = reString((String)asObj.get("service_name"));
		/**
		 * ?????????????????
		 */
		if(!("2").equals(com_protocol)){
			String defaultUrl =  headMap.get(manufacturer);
			if(defaultUrl!=null){
				url= defaultUrl;
			}

		}
			updateSql = "update res_headend_tab set  ip='"+ip+"',principal_phone='"+principal_phone
         +"',default_language='"+default_language+"',code='"+code+"',site='"+site+"',url='"
         +url+"',manufacturer='"+manufacturer+"',longitude='"+longitude+"',com_protocol='"+com_protocol+"',latitude='"+latitude+"' where head_id ="+head_id;
		try {
			DbComponent.exeUpdate(updateSql);
			ResHeadendBean ResHeadendBean = getHeadendByCode(code);
			TableInfoCache as = new TableInfoCache();
			as.refreshTableMap("res_headend_tab", ResHeadendBean,
					ResHeadendBean.getCode(), "2");
			InnerDevice.refreshInstance();

		} catch (DbException e) {
			e.printStackTrace();
			EXEException ex = new EXEException("", "," + e.getMessage(), "");
			list.add(ex);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("", "后台错误!", null);
		}

		return list;
	}
	public String getInsertSql(ASObject asObj){
		  String insertSql ="";
		     
 	    
          String code = reString((String)asObj.get("code"));
          String shortname = reString((String)asObj.get("shortname"));
          String com_id = reString((String)asObj.get("com_id"));
          String com_protocol = reString((String)asObj.get("com_protocol"));
          String ip = reString((String)asObj.get("ip"));
          String longitude = reString((String)asObj.get("longitude"));
          String latitude = reString((String)asObj.get("latitude"));
          String comphone = reString((String)asObj.get("comphone"));
          String site = reString((String)asObj.get("site"));
          String address = reString((String)asObj.get("address"));
          String site_status = reString((String)asObj.get("site_status"));
          String com_status = reString((String)asObj.get("com_status"));
          String fault_status = reString((String)asObj.get("fault_status"));
          String station_name = reString((String)asObj.get("station_name"));
          String descript = reString((String)asObj.get("descript"));
          String state = reString((String)asObj.get("state")); 
        
          String country = reString((String)asObj.get("country"));
          String version = reString((String)asObj.get("version"));
          String occur_time = reString((String)asObj.get("occur_time"));
          String altitude = reString((String)asObj.get("altitude"));
          String summertime_begin = reString((String)asObj.get("summertime_begin"));
          
          String summertime_end = reString((String)asObj.get("summertime_end"));
          String ciraf = reString((String)asObj.get("ciraf"));
          String person = reString((String)asObj.get("person"));
          String person_phone = reString((String)asObj.get("person_phone"));
          String principal = reString((String)asObj.get("principal"));
          String principal_phone = reString((String)asObj.get("principal_phone"));
          String time_diff = reString((String)asObj.get("time_diff"));
          String default_language = reString((String)asObj.get("default_language"));
          String x = reString((String)asObj.get("x"));

          String y = reString((String)asObj.get("y"));
          String url = reString((String)asObj.get("url"));
          String is_delete = (String)asObj.get("is_delete");
       
          String type_id = (String)asObj.get("type_id");
          String is_online = (String)asObj.get("is_online");
          String manufacturer = reString((String)asObj.get("manufacturer"));
          String post = reString((String)asObj.get("post"));
          String service_name = reString((String)asObj.get("service_name"));
          
              insertSql = "insert into res_headend_tab(head_id,code,com_id,com_protocol,ip,longitude,latitude,comphone,site,address,site_status,com_status,fault_status," +
              		"station_name,descript,state,country,version,occur_time,altitude,summertime_begin,summertime_end,ciraf,person," +
              		"person_phone,principal,principal_phone,time_diff,default_language,x,y,url,is_delete,type_id,is_online,manufacturer,shortname,post,service_name) " +
         				 "values(RES_RESOURSE_SEQ.nextval,'"+code.toUpperCase()+"','"+com_id+"','"+com_protocol+"','"+ip+"','"+longitude+"','"+latitude+"','"+comphone+"'," +
         				 		"'"+site+"','"+address+"','"+site_status+"','"+com_status+"','"+fault_status+"','"+station_name+"','"+descript+"','"+state+"'," +
         				 		"'"+country+"','"+version+"',to_date('"+occur_time+"','yyyy-mm-dd hh24:mi:ss'),'"+altitude+"'," +
         				 		"to_date('"+summertime_begin+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+summertime_end+"','yyyy-mm-dd hh24:mi:ss'),'"+ciraf+"','"+person+"'," +
         				 		"'"+person_phone+"','"+principal+"','"+principal_phone+"','"+time_diff+"','"+default_language+"','"+x+"','"+y+"','"+url+"'," +
         				 				"'"+is_delete+"','"+type_id+"','"+is_online+"','"+manufacturer+"','"+shortname+"','"+post+"','"+service_name+"')";
          return insertSql;
	}
	public String getUpdateSql(ASObject asObj){
		 String updateSql ="";
         String code = reString((String)asObj.get("code"));
         String shortname = reString((String)asObj.get("shortname"));
         String com_id = reString((String)asObj.get("com_id"));
         String com_protocol = reString((String)asObj.get("com_protocol"));
         String ip = reString((String)asObj.get("ip"));
         String longitude = reString((String)asObj.get("longitude"));
         String latitude = reString((String)asObj.get("latitude"));
         String comphone = reString((String)asObj.get("comphone"));
         String site = reString((String)asObj.get("site"));
         String address = reString((String)asObj.get("address"));
         String site_status = reString((String)asObj.get("site_status"));
         String com_status = reString((String)asObj.get("com_status"));
         String fault_status = reString((String)asObj.get("fault_status"));
         String station_name = reString((String)asObj.get("station_name"));
         String descript = reString((String)asObj.get("descript"));
         String state = reString((String)asObj.get("state")); 
         if(state!="")
             Integer.valueOf(state);
         String country = reString((String)asObj.get("country"));
         String version = reString((String)asObj.get("version"));
         String occur_time = reString((String)asObj.get("occur_time"));
         String altitude = reString((String)asObj.get("altitude"));
         String summertime_begin = reString((String)asObj.get("summertime_begin"));
         
         String summertime_end = reString((String)asObj.get("summertime_end"));
         String ciraf = reString((String)asObj.get("ciraf"));
         String person = reString((String)asObj.get("person"));
         String person_phone = reString((String)asObj.get("person_phone"));
         String principal = reString((String)asObj.get("principal"));
         String principal_phone = reString((String)asObj.get("principal_phone"));
         String time_diff = reString((String)asObj.get("time_diff"));
         String default_language = reString((String)asObj.get("default_language"));
         String is_delete = reString((String)asObj.get("is_delete"));
         if(is_delete!="")
             Integer.valueOf(is_delete);
         String type_id = reString((String)asObj.get("type_id"));
         if(type_id!="")
             Integer.valueOf(type_id);
         String is_online = reString((String)asObj.get("is_online"));
         if(is_online!="")
             Integer.valueOf(is_online);
         String manufacturer = reString((String)asObj.get("manufacturer"));
         String post = reString((String)asObj.get("post"));
         String service_name = reString((String)asObj.get("service_name"));
                  
         updateSql = "update res_headend_tab set shortname='"+shortname+"',com_id='"+com_id+"',com_protocol='"+com_protocol+"', ip='"+ip+"', longitude='"+longitude+"'," +
          		"latitude='"+latitude+"',comphone='"+comphone+"',site='"+site+"',address='"+address+"',site_status='"+site_status+"',com_status='"+com_status+"'," +
          		"fault_status='"+fault_status+"',station_name='"+station_name+"',descript='"+descript+"',state='"+state+"',country='"+country+"',version='"+version+"'," +
          		"occur_time=to_date('"+occur_time+"','yyyy-mm-dd hh24:mi:ss'),altitude='"+altitude+"',summertime_begin=to_date('"+summertime_begin+"','yyyy-mm-dd hh24:mi:ss')," +
          		"summertime_end=to_date('"+summertime_end+"','yyyy-mm-dd hh24:mi:ss'),ciraf='"+ciraf+"',person='"+person+"',person_phone='"+person_phone+"'," +
          		"principal='"+principal+"',principal_phone='"+principal_phone+"',time_diff='"+time_diff+"',default_language='"+default_language+"'," +
          		"is_delete='"+is_delete+"',type_id='"+type_id+"',is_online='"+is_online+"',manufacturer='"+manufacturer+"'," +
          				"post='"+post+"',service_name='"+service_name+"' where code='"+code+"'";
         return updateSql;
	}
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:删除前端数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
    public Object deleteHead(ASObject asObj){
    	DicDao asDao = new DicDao();
		String dellist=(String)asObj.get("dellist");
		
		try{
			System.out.println("dellist="+dellist);
			asDao.deleteHead(dellist);
			 ResHeadendBean ResHeadendBean = new ResHeadendBean();
			 TableInfoCache as =new TableInfoCache();
			String[] delArr = dellist.split(",");
			 for(int i=0;i<delArr.length;i++){
				
				 ResHeadendBean.setCode(delArr[i].split("'")[1]);
				 System.out.println("aaa="+delArr[i].split("'")[1]);
				 as.refreshTableMap("res_headend_tab",ResHeadendBean,ResHeadendBean.getCode(),"3"); 
			 }
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
		 return " ";
	}
    
    
    /**
	 * 
	 * <p>class/function:com.viewscenes.database.service.usermanager
	 * <p>explain:取得添加前端的序列
	 * <p>author-date:谭长伟 2012-3-3
	 * @param:
	 * @return:
	 */
//	private static String getHeadNextVal() throws DbException, GDSetException{
//		
//		String sql = " select RES_RESOURSE_SEQ.nextval val from dual ";
//		
//		GDSet set = DbComponent.Query(sql);
//		
//		String val = set.getString(0, "val");
//		
//		return val;
//	}
	public	Object importExcel(ASObject obj){
//		ArrayList<String> codes=getHeadendCodes();
		String file_name=(String)obj.get("file_name");
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("亚洲","0");
		map.put("欧洲","1");
		map.put("非洲","2");
		map.put("北美洲","3");
		map.put("南美洲","4");
		map.put("大洋洲","5");
		map.put("南极洲","6");
		try {
			String path="C:\\runplan\\"+file_name+"";
			File file=new File(path);
			Workbook book=Workbook.getWorkbook(file);
			Sheet sheet=book.getSheet(0);
			int rowNum = sheet.getRows();   //得到总行数  
			int colNum = sheet.getColumns();//得到总列数
			ArrayList<ASObject> list = new ArrayList<ASObject>();//存放运行图对象的list 
			for(int i=1;i<rowNum;i++){
				ASObject aso=new ASObject();
				StringBuffer sf=new StringBuffer();
				for(int j=0;j<colNum;j++){
					switch (j) {
					case 0:
						aso.put("code", sheet.getCell(j,i).getContents());
						break;
					case 1:	
						aso.put("shortname", sheet.getCell(j,i).getContents());
						aso.put("site", sheet.getCell(j,i).getContents());
						break;
					case 2:	
						aso.put("country", sheet.getCell(j,i).getContents());
						break;
					case 3:
						aso.put("state", map.get(sheet.getCell(j,i).getContents()));
						break;
					case 4:	
						aso.put("service_name", sheet.getCell(j,i).getContents());
						break;
					case 5:	
						aso.put("address", sheet.getCell(j,i).getContents());
						break;
					case 6:	
						aso.put("longitude", sheet.getCell(j,i).getContents());
						break;
					case 7:	
						aso.put("latitude", sheet.getCell(j,i).getContents());
						break;
					case 8:	
						aso.put("altitude", sheet.getCell(j,i).getContents());
						break;
					case 9:	
						aso.put("x", sheet.getCell(j,i).getContents());
						break;
					case 10:
						aso.put("y", sheet.getCell(j,i).getContents());
						break;
					case 11:
						aso.put("default_language", sheet.getCell(j,i).getContents());
						break;
					case 12:
						aso.put("ciraf", sheet.getCell(j,i).getContents());
						break;
					case 13:
						aso.put("time_diff", sheet.getCell(j,i).getContents());
						break;
					case 14:	
						aso.put("comphone", sheet.getCell(j,i).getContents());
						break;
					case 15:	
						aso.put("person", sheet.getCell(j,i).getContents());
						break;
					case 16:	
						aso.put("person_phone", sheet.getCell(j,i).getContents());
						break;
					case 17:	
						aso.put("principal", sheet.getCell(j,i).getContents());
						break;
					case 18:	
						aso.put("post", sheet.getCell(j,i).getContents());
						break;
					case 19:	
						sf.append(sheet.getCell(j,i).getContents());
						break;
					case 20:	
						sf.append(",").append(sheet.getCell(j,i).getContents());
						break;
					case 21:	
						sf.append(",").append(sheet.getCell(j,i).getContents());
						break;
					case 22:	
						aso.put("url", sheet.getCell(j,i).getContents());
						break;
					case 23:	
						if(sheet.getCell(j, i).getContents().equals("宽带")){
							aso.put("com_id", "1");
						}else{
							aso.put("com_id", "2");
						}
						break;
					case 24:	
						aso.put("com_protocol", sheet.getCell(j,i).getContents());
						break;
					case 25:	
						aso.put("ip", sheet.getCell(j,i).getContents());
						break;
					case 26:
						if(sheet.getCell(j, i).getContents().equals("正常")){
							aso.put("site_status", "0");
						}else{
							aso.put("site_status", "1");
						}
						break;
//					case 27:	
//						aso.put("com_status", sheet.getCell(j,i).getContents());
//						break;
//					case 28:	
//						aso.put("fault_status", sheet.getCell(j,i).getContents());
//						break;
//					case 29:
//						if(sheet.getCell(j, i).getContents().equals("是")){
//							aso.put("is_delete", "1");
//						}else{
//							aso.put("is_delete", "0");
//						}
//						break;
					case 27:
						if(sheet.getCell(j, i).getContents().equals("遥控站")){
							aso.put("type_id", "102");
						}else{
							aso.put("type_id", "101");
						}
						break;
					case 28:
						if(sheet.getCell(j, i).getContents().equals("在线")){
							aso.put("is_online", "0");
						}else{
							aso.put("is_online", "1");
						}
						break;
					case 29:
						if(sheet.getCell(j, i).getContents().equals("否")){
							aso.put("summertime", "0");
						}else{
							aso.put("summertime", "1");
						}					
						break;
//					case 33:	
//						aso.put("summertime_end", sheet.getCell(j,i).getContents());
//						break;
					case 30:	
						aso.put("manufacturer", sheet.getCell(j,i).getContents());
						break;
					case 31:	
						aso.put("version", sheet.getCell(j,i).getContents());
						break;
					case 32:
						aso.put("descript", sheet.getCell(j,i).getContents());
						break;
					
					case 33:
						aso.put("id", sheet.getCell(j,i).getContents());
						break;
					}
				}
				aso.put("principal_phone", sf.toString());
				list.add(aso);
			}
			int num=list.size();
			for(int i=0;i<num;i++){
				ASObject asObj=list.get(i);
				String sql="select * from res_headend_tab where head_id ="+(String)asObj.get("id");
				GDSet set=DbComponent.Query(sql);
				if(set.getRowCount()>0){
					updateHead(asObj);		
				}else{
					AddHead(asObj);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","站点信息导入异常："+e.getMessage(),"");
		}
		
		return "导入站点信息成功";
	}
	private ResHeadendBean getHeadendByCode(String code){
		ResHeadendBean bean=new ResHeadendBean();
		String sql="select * from res_headend_tab where code='"+code+"'";
		try {
			GDSet gd=DbComponent.Query(sql);
			bean.setHead_id(gd.getString(0, "head_id"));
			bean.setCode(gd.getString(0, "code"));
			bean.setShortname(gd.getString(0, "shortname"));
			bean.setManufacturer(gd.getString(0, "manufacturer"));
			bean.setIs_online(gd.getString(0, "is_online"));
			bean.setType_id(gd.getString(0, "type_id"));
			bean.setUrl(gd.getString(0, "url"));
			bean.setState_name(gd.getString(0, "station_name"));
			bean.setCom_id(gd.getString(0, "com_id"));
			bean.setCom_protocol(gd.getString(0, "com_protocol"));
			bean.setIp(gd.getString(0, "ip"));
			bean.setLongitude(gd.getString(0, "longitude"));
			bean.setLatitude(gd.getString(0, "latitude"));
			bean.setComphone(gd.getString(0, "comphone"));
			bean.setSite(gd.getString(0, "site"));
			bean.setAddress(gd.getString(0, "address"));
			bean.setSite_status(gd.getString(0, "site_status"));
			bean.setCom_status(gd.getString(0, "com_status"));
			bean.setFault_status(gd.getString(0, "fault_status"));
			bean.setStation_name(gd.getString(0, "station_name"));
			bean.setDescript(gd.getString(0, "descript"));
			bean.setState(gd.getString(0, "state"));
			bean.setCountry(gd.getString(0, "country"));
			bean.setOccur_time(gd.getString(0, "occur_time"));
			bean.setAltitude(gd.getString(0, "altitude"));
			bean.setSummertime_begin(gd.getString(0, "summertime_begin"));
			bean.setSummertime_end(gd.getString(0, "summertime_end"));
			bean.setCiraf(gd.getString(0, "ciraf"));
			bean.setPerson(gd.getString(0, "person"));
			bean.setPerson_phone(gd.getString(0, "person_phone"));
			bean.setPrincipal(gd.getString(0, "principal"));
			bean.setPrincipal_phone(gd.getString(0, "principal_phone"));
			bean.setTime_diff(gd.getString(0, "time_diff"));
			bean.setIs_delete(gd.getString(0, "is_delete"));
			bean.setDefault_language(gd.getString(0, "default_language"));
			bean.setX(gd.getString(0, "x"));
			bean.setY(gd.getString(0, "y"));
			bean.setVersion(gd.getString(0, "version"));
			bean.setVersion(gd.getString(0, "post"));
			bean.setVersion(gd.getString(0, "service_name"));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	public static String getHeadCodesNOAB() throws Exception{
		String resault="";
		String sql="select distinct decode(upper(t.type_id||t.version), upper('102V8'), substr(t.code, 0, length(t.code) - 1), t.code)  from res_headend_tab t ";
		GDSet gd=DbComponent.Query(sql);
		int n=gd.getRowCount();
		if(n>0){
			for(int i=0;i<n;i++){
				if(i==n-1){
					resault+=gd.getString(i, 0);
				}else{
					resault+=gd.getString(i, 0)+",";
				}
			}
		}
		
		return resault;
	}
//	private ArrayList<String> getHeadendCodes(){
//		ArrayList<String> list=new ArrayList<String>();
//		String sql="select code from res_headend_tab order by code";
//		try {
//			GDSet gd=DbComponent.Query(sql);
//			int n=gd.getRowCount();
//			if(n>0){
//				for(int i=0;i<n;i++){
//					list.add(gd.getString(i, "code"));
//				}
//			}
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (GDSetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
	private String reString(String str){
		if(str==null||str.length()==0){
			return "";
		}
		return str;
	}
}
