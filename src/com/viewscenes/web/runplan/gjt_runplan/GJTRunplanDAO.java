package com.viewscenes.web.runplan.gjt_runplan;


import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.sysmgr.dicManager.HeadendTab;

import flex.messaging.io.amf.ASObject;
/**
 * ��������̨����ͼ��
 * @author ������
 * @date 2012/07/19
 */
public class GJTRunplanDAO {
	
	public ASObject queryRunplan(GJTRunplanBean bean) throws Exception{
		String runplan_id = bean.getRunplan_id();   //����ͼid
		String runplan_type_id = bean.getRunplan_type_id(); //����ͼ����id
		String station_id = bean.getStation_id();    //����̨id
		String station_name = bean.getStation_name(); //����̨
		String sentcity_id = bean.getSentcity_id();  //�������id
		String transmiter_no = bean.getTransmiter_no();  //�������
		String freq =bean.getFreq();      //Ƶ��
		String antenna = bean.getAntenna(); //���ߺ�
		String antennatype = bean.getAntennatype();//���߳�ʽ
		String direction = bean.getDirection(); //����
		String language_id = bean.getLanguage_id(); //����id
		String power = bean.getPower();  //����
		String program_type_id = bean.getProgram_type_id(); //��Ŀ����id
		String service_area = bean.getService_area(); //������
		String ciraf = bean.getCiraf(); // CIRAF��
		String satellite_channel = bean.getSatellite_channel();//��������ͨ��
		String start_time = bean.getStart_time(); //������ʼʱ��
		String end_time = bean.getEnd_time();   //��������ʱ��
		String mon_area = bean.getMon_area();    //ң��վ�ղ�
		String rest_datetime = bean.getRest_datetime(); // //��Ϣ����
		String rest_time = bean.getRest_time(); //��Ϣʱ��
		String valid_start_time = bean.getValid_start_time(); //������
		String valid_end_time = bean.getValid_end_time();  //ͣ����
		String flag = bean.getFlag();//��־λ
		String season_id = bean.getSeason_id();//���ڴ���
		String headCodes="('"+HeadendTab.getHeadCodesNOAB().replaceAll(",", "','")+"')";
		StringBuffer sqlbuffer = null ;
		if(flag.equalsIgnoreCase("unit"))
		{
			sqlbuffer =new StringBuffer("select distinct zrt.station_name,zrt.freq,zrt.start_time,zrt.end_time,zrt.xg_mon_area,zrt.mon_area,zrt.redisseminators,zrt.antenna, zlt.language_name as language ") ;	
		}else
		{
			sqlbuffer =new StringBuffer("select zrt.*,zlt.language_name as language") ;
		}
		sqlbuffer.append(" from zres_runplan_tab zrt,zdic_language_tab zlt ");
		sqlbuffer.append(" where zrt.is_delete=0  and zrt.language_id=zlt.language_id(+) ");
		if(runplan_id!=null&&!runplan_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.runplan_id='"+runplan_id+"'");
		}
		if(runplan_type_id!=null && !runplan_type_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.runplan_type_id='"+runplan_type_id+"'");
		}
//		if(station_id!=null && !station_id.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.station_id='"+station_id+"'");
//		}
		
		if(station_name!=null && !station_name.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.station_name='"+station_name+"'");
		}
		if(freq!=null && !freq.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.freq='"+freq+"'");
		}
		if(language_id!=null && !language_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.language_id='"+language_id+"'");
		}
//		if(start_time!=null && !start_time.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.start_time=to_char('"+start_time+"')");
//		}
//		if(end_time!=null && !end_time.equalsIgnoreCase("")){
//			sqlbuffer.append(" and zrt.end_time=to_char('"+end_time+"')");
//		}
		if(mon_area!=null && !mon_area.equalsIgnoreCase("")){
			sqlbuffer.append(" and (zrt.mon_area like '%"+mon_area+"%' or zrt.xg_mon_area like '%"+mon_area+"%')");
		}
		if(valid_start_time!=null && !valid_start_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and store_datetime>=to_date('"+valid_start_time+"','yyyy-MM-dd HH24:MI:SS')");
			
		}
		if(valid_end_time!=null && !valid_end_time.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.store_datetime<=to_date('"+valid_end_time+"','yyyy-MM-dd HH24:MI:SS')");
			
		}
		if(season_id!=null && !season_id.equalsIgnoreCase("")){
			sqlbuffer.append(" and zrt.season_id='"+season_id+"'");
		}
		if(flag.equalsIgnoreCase("unit")){
			sqlbuffer.append(" and (('2000-01-01 '||to_char(sysdate,'hh24:mi:ss') between '2000-01-01 ' || zrt.start_time || ':00' and '2000-01-01 ' || zrt.end_time || ':00') ");
			sqlbuffer.append(" or  (  '2000-01-01 ' || to_char(sysdate, 'hh24:mi:ss') between  '2000-01-01 ' || zrt.start_time || ':00'  and '2000-01-02 ' || zrt.end_time || ':00' and zrt.end_time<zrt.start_time  ))");

			sqlbuffer.append(" and zrt.xg_mon_area is not null and zrt.valid_end_time>sysdate  ");
		}
		sqlbuffer.append(" and ( zrt.xg_mon_area in ").append(headCodes);
		sqlbuffer.append(" or (zrt.xg_mon_area is null and zrt.mon_area in ").append(headCodes).append("))");
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

	
	/**
	 * ����ͼ������
	 * @param bean
	 * @return
	 * @throws DbException
	 */
	public String addRunplan(GJTRunplanBean bean,String date ) throws DbException{
		String res="��������ͼ�ɹ�!";
		String runplan_id = bean.getRunplan_id()==null?"":bean.getRunplan_id(); //����ͼid
		String station_name = bean.getStation_name()==null?"":bean.getStation_name(); //����̨
		String station_id = bean.getStation_id()==null?"":bean.getStation_id();//����̨id
		String freq =bean.getFreq()==null?"":bean.getFreq();      //Ƶ��
		String language_id = bean.getLanguage_id()==null?"":bean.getLanguage_id(); //����id
		String start_time = bean.getStart_time()==null?"":bean.getStart_time(); //������ʼʱ��
		String end_time = bean.getEnd_time()==null?"":bean.getEnd_time();   //��������ʱ��
		String mon_area =  bean.getMon_area()==null?"":bean.getMon_area();    //�����ղ�վ��
		String xg_mon_area = bean.getXg_mon_area()==null?"":bean.getXg_mon_area();//Ч���ղ�վ��
		String valid_start_time = bean.getValid_start_time()==null?"":bean.getValid_start_time(); //������
		String valid_end_time = bean.getValid_end_time()==null?"":bean.getValid_end_time();  //ͣ����
		String season_id = bean.getSeason_id()==null?"":bean.getSeason_id();//���ڴ���
		String satellite_channel = bean.getSatellite_channel()==null?"":bean.getSatellite_channel();//ͨ��
		String weekday = bean.getWeekday()==null?"":bean.getWeekday();//������
		String store_datetime = bean.getStore_datetime()==null?"":bean.getStore_datetime();//����ʱ��
		StringBuffer addsql = new StringBuffer("insert into zres_runplan_tab (runplan_id,station_name,station_id,freq,");
		             addsql.append("language_id,start_time,end_time,valid_start_time,valid_end_time,mon_area,xg_mon_area,runplan_type_id,is_delete,season_id,satellite_channel,weekday,store_datetime) ");
		             addsql.append(" values('"+runplan_id+"','"+station_name+"','"+station_id+"','"+freq+"',");
		             addsql.append("'"+language_id+"','"+start_time+"','"+end_time+"',");
		             addsql.append("'"+valid_start_time+"','"+valid_end_time+"','"+mon_area+"','"+xg_mon_area+"',1,0,'"+season_id+"','"+satellite_channel+"','"+weekday+"','"+store_datetime+"' )");
		 DbComponent.exeUpdate(addsql.toString());
		return res;
	}
}
