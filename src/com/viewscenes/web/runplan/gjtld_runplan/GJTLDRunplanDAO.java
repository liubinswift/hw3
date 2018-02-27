package com.viewscenes.web.runplan.gjtld_runplan;

import com.viewscenes.bean.runplan.GJTLDRunplanBean;
import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class GJTLDRunplanDAO {

	public GJTLDRunplanDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public ASObject queryRunplan(GJTLDRunplanBean bean) throws Exception{
		GDSet gd=null;
		String runplan_id = bean.getRunplan_id();//运行图id
		String runplan_type_id = bean.getRunplan_type_id();//运行图类型id
		String sentcity_id = bean.getSentcity_id();//发射城市id
		String redisseminators = bean.getRedisseminators();//转播机构
		String freq =bean.getFreq();      //频率
		String direction = bean.getDirection(); //方向
		String band = bean.getBand(); //波段
		String program_id = bean.getProgram_id(); //节目id
		String language_id = bean.getLanguage_id();//语言id
		String power = bean.getPower();//功率
		String service_area = bean.getService_area(); //服务区
		String start_time = bean.getStart_time();
		String end_time = bean.getEnd_time();
		String local_time = bean.getLocal_time();
		String mon_area = bean.getMon_area();
		String rest_datetime = bean.getRest_datetime();
		String valid_start_time = bean.getValid_start_time();
		String valid_end_time = bean.getValid_end_time();
		String summer = bean.getSummer();
		String summer_starttime = bean.getSummer_starttime();
		String summer_endtime = bean.getSummer_endtime();
		String flag = bean.getFlag();
		StringBuffer sqlbuffer = null ;
		if(flag.equalsIgnoreCase("unit"))
		{
			sqlbuffer =new StringBuffer("select distinct zrt.station_name,zrt.freq,zrt.start_time,zrt.end_time,zrt.xg_mon_area,zrt.mon_area,zrt.redisseminators,zrt.antenna, zlt.language_name as language ") ;	
		}else
		{
			sqlbuffer =new StringBuffer("select zrt.*,zlt.language_name as language,rct.city as sentcity") ;
		}
		sqlbuffer.append(" from zres_runplan_tab zrt,zdic_language_tab zlt,res_city_tab rct  ");
		sqlbuffer.append(" where zrt.is_delete=0 and zrt.language_id=zlt.language_id  and zrt.sentcity_id=rct.id ");
		if(!runplan_id.equalsIgnoreCase("")&&runplan_id!=null){
			sqlbuffer.append(" and zrt.runplan_id='"+runplan_id+"'");
		}
		if(runplan_type_id!=null && !runplan_type_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.runplan_type_id='"+runplan_type_id+"'");
		}
		if(freq!=null && !freq.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.freq='"+freq+"'");
		}
//		if(band!=null && !band.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.band='"+band+"'");
//		}
//		if(direction!=null && !direction.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.direction='"+direction+"'");
//		}
		if(language_id!=null && !language_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.language_id="+language_id+"");
		}
//		if(start_time!=null && !start_time.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.start_time=to_char('"+start_time+"')");
//		}
//		if(end_time!=null && !end_time.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.end_time=to_char('"+end_time+"')");
//		}
//		if(local_time!=null && !local_time.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.local_time='"+local_time+"'");
//		}
		if(mon_area!=null && !mon_area.equalsIgnoreCase("")){
			sqlbuffer.append(" and (zrt.mon_area like '%"+mon_area+"%' or zrt.xg_mon_area like '%"+mon_area+"%')");
		}
//		if(rest_datetime!=null && !rest_datetime.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.rest_datetime=to_date('"+rest_datetime+"','yyyy-MM-dd HH24:MI:SS')");
//		}
		if(valid_start_time!=null && !valid_start_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.store_datetime>=to_date('"+valid_start_time+"','yyyy-MM-dd HH24:MI:SS')");
		}
		if(valid_end_time!=null && !valid_end_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.store_datetime<=to_date('"+valid_end_time+"','yyyy-MM-dd HH24:MI:SS')");
		}
		if(flag.equalsIgnoreCase("unit")){
			sqlbuffer.append(" and (('2000-01-01 '||to_char(sysdate,'hh24:mi:ss') between '2000-01-01 ' || zrt.start_time || ':00' and '2000-01-01 ' || zrt.end_time || ':00') ");
			sqlbuffer.append(" or  (  '2000-01-01 ' || to_char(sysdate, 'hh24:mi:ss') between  '2000-01-01 ' || zrt.start_time || ':00'  and '2000-01-02 ' || zrt.end_time || ':00' and zrt.end_time<zrt.start_time  ))");
            sqlbuffer.append(" and zrt.xg_mon_area is not null and zrt.valid_end_time>sysdate  ");
		}
		
		if(flag.equalsIgnoreCase("unit"))
		{
			sqlbuffer.append("   order by zrt.redisseminators,zrt.freq");	
		}else
		{
			sqlbuffer.append("   order by zrt.store_datetime desc,zrt.redisseminators,zrt.freq,zrt.start_time");
		}
//		if(summer!=null && !summer.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.rest_time=to_char('"+summer+"')");
//		}
//		if(summer_starttime!=null && !summer_starttime.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.summer_starttime=to_dater('"+summer_starttime+"','yyyy-MM-dd HH24:MI:SS')");
//		}
//		if(summer_endtime!=null && !summer_endtime.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.summer_endtime=to_dater('"+summer_endtime+"','yyyy-MM-dd HH24:MI:SS')");
//		}
//		
		ASObject resObj=StringTool.pageQuerySql(sqlbuffer.toString(), bean);
		return resObj;
	}
	
	/**
	 * 运行图添加入库
	 * @param bean
	 * @return
	 * @throws DbException
	 */
	public String addRunplan(GJTLDRunplanBean bean,String date) throws DbException{
		String res="导入运行图成功!";
		String runplan_id = bean.getRunplan_id()==null?"":bean.getRunplan_id(); //运行图id
		String launch_country = bean.getLaunch_country()==null?"":bean.getLaunch_country();//发射国家
		String sentcity_id = bean.getSentcity_id()==null?"":bean.getSentcity_id();//发射城市id
		String redisseminators = bean.getRedisseminators()==null?"":bean.getRedisseminators(); //转播机构
		String freq =bean.getFreq()==null?"":bean.getFreq();      //频率
		String language_id = bean.getLanguage_id()==null?"":bean.getLanguage_id(); //语言id
		String start_time = bean.getStart_time()==null?"":bean.getStart_time(); //播音开始时间
		String end_time = bean.getEnd_time()==null?"":bean.getEnd_time();   //播音结束时间
		String mon_area =  bean.getMon_area()==null?"":bean.getMon_area();    //质量收测站点
		String xg_mon_area = bean.getXg_mon_area()==null?"":bean.getXg_mon_area();//效果收测站点
		String valid_start_time = bean.getValid_start_time()==null?"":bean.getValid_start_time(); //启用期
		String valid_end_time = bean.getValid_end_time()==null?"":bean.getValid_end_time();  //停用期
		String weekday = bean.getWeekday()==null?"":bean.getWeekday();//周设置
		String summer = bean.getSummer()==null?"":bean.getSummer();//季节类型
		String store_datetime = bean.getStore_datetime()==null?"":bean.getStore_datetime();//导入时间
		StringBuffer addsql = new StringBuffer("insert into zres_runplan_tab (runplan_id,launch_country,sentcity_id,redisseminators,freq,");
		             addsql.append("language_id,start_time,end_time,valid_start_time,valid_end_time,mon_area,xg_mon_area,runplan_type_id,is_delete,weekday,summer,store_datetime) ");
		             addsql.append(" values('"+runplan_id+"','"+launch_country+"','"+sentcity_id+"','"+redisseminators+"','"+freq+"',");
		             addsql.append("'"+language_id+"','"+start_time+"','"+end_time+"',");
		             addsql.append("'"+valid_start_time+"','"+valid_end_time+"','"+mon_area+"','"+xg_mon_area+"',2,0,'"+weekday+"','"+summer+"','"+store_datetime+"')");
		 DbComponent.exeUpdate(addsql.toString());
		return res;
	}

}
