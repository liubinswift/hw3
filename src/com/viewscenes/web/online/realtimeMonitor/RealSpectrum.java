package com.viewscenes.web.online.realtimeMonitor;

import java.util.ArrayList;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ResCityBean;
import com.viewscenes.bean.spectrum.SpectrumStatBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class RealSpectrum {
	/**
	 * 查询频谱信息表
	 * @detail  
	 * @method  
	 * @param obj
	 * @return 
	 * @return  Object  
	 * @author  zhaoyahui
	 * @version 2012-10-26 下午05:13:36
	 */
	public Object querySpectrumStat(SpectrumStatBean bean){
		String inputDate = bean.getInput_datetime();
		inputDate = inputDate.split(" ")[0];
    	String sql = "select * from radio_spectrum_stat_tab t where head_code='"+bean.getHead_code()+
    					"' and freq='"+bean.getFreq()+"'"+
    					" and playtime='"+bean.getPlaytime()+"'"+
    					" and to_char(input_datetime,'yyyy-mm-dd')='"+inputDate+"'";
    	ArrayList list = new ArrayList();
		try {
			GDSet gd = DbComponent.Query(sql);
			list = TableInfoCache.gdSetToPojoList(gd, SpectrumStatBean.class.getName());
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return list;
    }
	
	/**
	 * 修改频谱信息表
	 * @detail  
	 * @method  
	 * @param obj
	 * @return 
	 * @return  Object  
	 * @author  zhaoyahui
	 * @version 2012-10-26 下午05:17:05
	 */
	public Object updateSpectrumStat(SpectrumStatBean bean){
    	String sql = "update radio_spectrum_stat_tab set " +
	    			" station_id='"+bean.getStation_id()+"',"+
	    			" station_name='"+bean.getStation_name()+"',"+
	    			" language_id='"+bean.getLanguage_id()+"',"+
	    			" language_name='"+bean.getLanguage_name()+"',"+
	    			" country='"+bean.getCountry()+"',"+
	    			" effect='"+bean.getEffect()+"',"+
	    			" mark='"+bean.getMark()+"',"+
	    			" playtime='"+bean.getPlaytime()+"',"+
	    			" headtype='"+bean.getHeadtype()+"',"+
	    			" is_play='"+bean.getIs_play()+"',"+
	    			" head_name='"+bean.getHead_name()+"' "+
    				" where id="+bean.getId();
		try {
			DbComponent.exeUpdate(sql);
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return "修改成功";
    }
	
	/**
	 * 添加频谱信息表数据
	 * @detail  
	 * @method  
	 * @param bean
	 * @return 
	 * @return  Object  
	 * @author  zhaoyahui
	 * @version 2012-10-26 下午05:18:53
	 */
	public Object addSpectrumStat(SpectrumStatBean bean){
    	String sql = "insert into radio_spectrum_stat_tab(id,head_code,station_id,station_name,language_id,language_name,country," +
    			" effect,mark,freq,playtime,headtype,is_play,input_datetime,head_name) " +
    			" values(radio_realtime_seq.nextval,'"+bean.getHead_code()+"','"+bean.getStation_id()+"','"+bean.getStation_name()+
    			"','"+bean.getLanguage_id()+"','"+bean.getLanguage_name()+"','"+bean.getCountry()+"','"+
    			bean.getEffect()+"','"+bean.getMark()+"','"+bean.getFreq()+"','"+bean.getPlaytime()+"','"+bean.getHeadtype()+"','"+
    			bean.getIs_play()+"',sysdate,'"+bean.getHead_name()+"')";
		try {
			DbComponent.exeUpdate(sql);
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return "添加成功";
    }
	/**
	 * 
	 * @detail  
	 * @method  
	 * @param asobj
	 * @return 
	 * @return  Object  
	 * @author  zhaoyahui
	 * @version 2013-7-19 下午04:18:46
	 */
	public Object querySpectrumInfo(ASObject asobj){
		ArrayList list = new ArrayList();
		String freq = (String)asobj.get("freq");
		String freqnew = freq;
		String band = (String)asobj.get("band");
		if("0".equals(band)){//短波
			freqnew += ","+(Double.parseDouble(freq)-5);
			freqnew += ","+(Double.parseDouble(freq)+5);
		} else if("1".equals(band)){//中波
			freqnew += ","+(Double.parseDouble(freq)-9);
			freqnew += ","+(Double.parseDouble(freq)+9);
			freqnew += ","+(Double.parseDouble(freq)-10);
			freqnew += ","+(Double.parseDouble(freq)+10);
		} else if("2".equals(band)){//调频
			freqnew += ","+(Double.parseDouble(freq)-100);
			freqnew += ","+(Double.parseDouble(freq)+100);
		}
		String head_code = (String)asobj.get("head_code");
		String play_time = getPlayTime();
		
		String sql = "select freq,play_time,mark from radio_spectrum_stat_tab where freq in ("+freqnew+") and head_code='"+head_code+"' and play_time in ("+play_time+")"+
					" to_char(t.input_datetime,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') ";
		try {
			GDSet newgd = DbComponent.Query(sql);
			int rowCount = newgd.getRowCount();
			for (int j = 0; j < rowCount; j++) {
				ASObject rowObj = new ASObject();
				rowObj.put("freq", newgd.getString(j, "freq"));
				rowObj.put("play_time", newgd.getString(j, "play_time"));
				rowObj.put("mark", newgd.getString(j, "mark"));
				list.add(rowObj);
			}
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
		
		return list;
	}
	
	public String getPlayTime(){
		String playTimeAll = "'00:00-00:30','00:30-01:00','01:00-01:30','01:30-02:00','02:30-03:00','03:00-03:30','04:00-04:30','04:30-05:00'," +
				"'05:00-05:30','05:30-06:00','06:00-06:30','06:30-07:00','07:00-07:30','07:30-08:00','08:00-08:30','08:30-09:00','09:00-09:30','09:30-10:00'," +
				"'10:00-10:30','10:30-11:00','11:00-11:30','11:30-12:00','12:00-12:30','12:30-13:00','13:00-13:30','13:30-14:00','14:00-14:30','14:30-15:00'," +
				"'15:00-15:30','15:30-16:00','16:00-16:30','16:30-17:00','17:00-17:30','17:30-18:00','18:00-18:30','18:30-19:00','19:00-19:30','19:30-20:00'," +
				"'20:00-20:30','20:30-21:00','21:00-21:30','21:30-22:00','22:00-22:30','22:30-23:00','23:00-23:30','23:30-00:00'";
		return playTimeAll;
	}
	public static void main(String[] args) {
		RealSpectrum aa = new RealSpectrum();
		System.out.println(aa.getPlayTime());
	}
}
