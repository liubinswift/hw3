package com.viewscenes.web.runplan.wgt_runplan;

import com.viewscenes.bean.runplan.WGTRunplanBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

/**
 * 操作国际台运行图类
 * @author 王福祥
 * @date 2012/08/01
 */
public class WGTRunplanDAO {

	public WGTRunplanDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public ASObject queryRunplan(WGTRunplanBean bean) throws Exception{
		
		String runplan_id = bean.getRunplan_id();   //运行图id
		String runplan_type_id = bean.getRunplan_type_id(); //运行图类型id
		String broadcast_station = bean.getBroadcast_station(); //播音电台
		String broadcast_country = bean.getBroadcast_country();//播音国家
		String launch_country = bean.getLaunch_country(); //发射国家
		String sentcity_id = bean.getSentcity_id();  //发射城市id
		String station_id = bean.getStation_id();    //发射台id
		String station_name = bean.getStation_name(); //发射台名称
		String transmiter_no = bean.getTransmiter_no();  //发射机号
		String freq =bean.getFreq();      //频率
		String antenna = bean.getAntenna(); //天线号
		String antennatype = bean.getAntennatype();//天线程式
		String direction = bean.getDirection(); //方向
		String language_id = bean.getLanguage_id(); //语言id
		String power = bean.getPower();  //功率 
		String service_area = bean.getService_area(); //服务区
		String ciraf = bean.getCiraf(); // CIRAF区
		String start_time = bean.getStart_time(); //播音开始时间
		String end_time = bean.getEnd_time();   //播音结束时间
		String mon_area = bean.getMon_area();    //遥控站收测
		String rest_datetime = bean.getRest_datetime(); // //休息日期
		String rest_time = bean.getRest_time(); //休息时间
		String valid_start_time = bean.getValid_start_time(); //启用期
		String valid_end_time = bean.getValid_end_time();  //停用期
		String flag = bean.getFlag();//标志位
	    StringBuffer sqlbuffer =null ;
	    
		if(flag.equalsIgnoreCase("unit"))
		{
			sqlbuffer =new StringBuffer(" select distinct zrt.station_name,zrt.freq,zrt.start_time,zrt.end_time,zrt.xg_mon_area,zrt.mon_area,zrt.redisseminators,zrt.antenna,zlt.language_name,rct.city  ") ;	
		}else
		{
			sqlbuffer =new StringBuffer("select zrt.*,zlt.language_name as language,rct.city as sentcity ") ;
		}
		sqlbuffer.append(" from zres_runplan_tab zrt,zdic_language_tab zlt,res_city_tab rct ");
		sqlbuffer.append(" where zrt.is_delete=0  and zrt.language_id=zlt.language_id and zrt.sentcity_id=rct.id ");
		if(!runplan_id.equalsIgnoreCase("")&&runplan_id!=null){
			sqlbuffer.append(" and zrt.runplan_id='"+runplan_id+"'");
		}
		if(runplan_type_id!=null && !runplan_type_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.runplan_type_id='"+runplan_type_id+"'");
		}
		if(broadcast_station!=null&&!broadcast_station.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.broadcast_station='"+broadcast_station+"'");
		}
		if(broadcast_country!=null&&!broadcast_country.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.broadcast_country='"+broadcast_country+"'");
		}
		if(launch_country!=null&&!launch_country.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.launch_country='"+launch_country+"'");
		}
		if(station_id!=null && !station_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.station_id='"+station_id+"'");
		}
		if(station_name!=null && !station_name.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.station_name='"+station_name+"'");
		}
		if(sentcity_id!=null && !sentcity_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.sentcity_id='"+sentcity_id+"'");
		}
		if(transmiter_no!=null && !transmiter_no.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.transmiter_no='"+transmiter_no+"'");
		}
		if(freq!=null && !freq.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.freq='"+freq+"'");
		}
		if(antenna!=null && !antenna.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.antenna='"+antenna+"'");
		}
		if(antennatype!=null && !antennatype.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.antennatype='"+antennatype+"'");
		}
		if(direction!=null && !direction.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.direction='"+direction+"'");
		}
		if(language_id!=null && !language_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.language_id="+language_id+"");
		}
		if(power!=null &&!power.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.power="+power+"");
		}
		if(service_area!=null && !service_area.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.service_area='"+service_area+"'");
		}
		if(ciraf!=null && !ciraf.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.ciraf='"+ciraf+"'");
		}
		if(start_time!=null && !start_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.start_time=to_char('"+start_time+"')");
		}
		if(end_time!=null && !end_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.end_time=to_char('"+end_time+"')");
		}
		if(mon_area!=null && !mon_area.equalsIgnoreCase("")){
			sqlbuffer.append(" and (zrt.mon_area like '%"+mon_area+"%' or zrt.xg_mon_area like '%"+mon_area+"%')");
		}
		if(rest_datetime!=null && !rest_datetime.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.rest_datetime=to_date('"+rest_datetime+"','yyyy-MM-dd HH24:MI:SS')");
		}
		if(rest_time!=null && !rest_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.rest_time=to_char('"+rest_time+"')");
		}
		if(valid_start_time!=null && !valid_start_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.valid_start_time=to_dater('"+valid_start_time+"','yyyy-MM-dd HH24:MI:SS')");
		}
		if(valid_end_time!=null && !valid_end_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.valid_end_time=to_dater('"+valid_end_time+"','yyyy-MM-dd HH24:MI:SS')");
		}
		if(flag.equalsIgnoreCase("unit")){
			sqlbuffer.append(" and (('2000-01-01 '||to_char(sysdate,'hh24:mi:ss') between '2000-01-01 ' || zrt.start_time || ':00' and '2000-01-01 ' || zrt.end_time || ':00') ");
			sqlbuffer.append(" or  (  '2000-01-01 ' || to_char(sysdate, 'hh24:mi:ss') between  '2000-01-01 ' || zrt.start_time || ':00'  and '2000-01-02 ' || zrt.end_time || ':00' and zrt.end_time<zrt.start_time  ))");
         }
		if(flag.equalsIgnoreCase("unit"))
		{
			sqlbuffer.append("   order by zrt.station_name,zrt.freq");	
		}else
		{
			sqlbuffer.append("   order by zrt.store_datetime desc,zrt.station_name,zrt.freq");
		}
		ASObject resObj=StringTool.pageQuerySql(sqlbuffer.toString(), bean);
		return resObj;
	}

}
