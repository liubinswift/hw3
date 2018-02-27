package com.viewscenes.app.thread;


import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemCache;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.common.Common;

import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.pub.HeadOnlineStatusBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;

import flex.messaging.io.amf.ASObject;



import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

import org.jdom.Element;

/**
 * 检测站点是否在线状态
 * @author Administrator
 *
 */
 
    public class CheakHeadOnLineThread extends Thread{

        public CheakHeadOnLineThread() {

        }

        public void run(){
            while (true) {
				try {
					  this.checkOnline();
				} catch (Exception e) {
					LogTool.fatal(e);
				}
				try {
					Thread.sleep(240000);//给240秒
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
        /**
         * 从数据库得到全部站点是否在线信息存放到缓存map中
         * @param msg String
         * @return String
         */
        public void getAllOnlineInfo() {

        	LogTool.info("********************************getAllOnlineInfo()");
				String sql = "select h.code, h.is_online, decode(p.stationstatusreportinterval,null,10,p.stationstatusreportinterval) stationstatusreportinterval " +
					  " from radio_equ_init_param_tab p, res_headend_tab h " +
					  " where p.head_id(+) = h.head_id " +
					  " and h.is_delete = 0 ";
				try {
					GDSet gd = DbComponent.Query(sql);
					for(int i=0;i<gd.getRowCount();i++)
					{

						String code = gd.getString(i , "code");
						HeadOnlineStatusBean onlineBean = SystemCache.onLineStatusMap.get(code);
						if(onlineBean == null){
							onlineBean = new HeadOnlineStatusBean();
						}
						onlineBean.setCode(code);
						onlineBean.setIs_online(gd.getString(i , "is_online"));
						String inter = gd.getString(i , "stationstatusreportinterval");
						if(inter == null || inter.equals("")){
							inter = "10";
						}
						onlineBean.setIntervalReport(inter);
						SystemCache.onLineStatusMap.put(code, onlineBean);
					}
				} catch (Exception e) {
					LogTool.fatal(e);
				}
			
        }
    
        /**
         * 检测站点是否在线
         * 
         * @detail  
         * @method   
         * @return  void  
         * @author  zhaoyahui
         * @version 2013-3-22 下午03:39:54
         */
        public void checkOnline() throws Exception{
        	LogTool.info("+++++++++++++++++++++++++++++++++++++checkOnline()");
        	for(String o:SystemCache.onLineStatusMap.keySet()){
//            	String code = o; 		// Map的键
				HeadOnlineStatusBean onBean = SystemCache.onLineStatusMap.get(o);// Map的值
				long begin = onBean.getLastSaveTime().getTime();
				long end = new Date().getTime();
				String sql = "";
				try {
					
//					LogTool.fatal("code=="+onBean.getCode()+"%%online=="+onBean.getIs_online()+"%%begin=="+StringTool.date2String(onBean.getLastSaveTime())+"%%(end-begin)/1000=="+(end-begin)/1000+"%%inert=="+Integer.parseInt(onBean.getIntervalReport())*60*1.3);
					//大于上报时间*1.3后发现还没有更新状态就判断为不在线了
					if(onBean.getIs_online().equals("1") && (end-begin)/1000>=Integer.parseInt(onBean.getIntervalReport())*60*1.1){
						sql = "update res_headend_tab set is_online='0' where code='"+onBean.getCode()+"'";
						DbComponent.exeUpdate(sql);
						onBean.setIs_online("0");
					} else if(!onBean.isFristCreate() && onBean.getIs_online().equals("0") && (end-begin)/1000<Integer.parseInt(onBean.getIntervalReport())*60*1.1){
						sql = "update res_headend_tab set is_online='1' where code='"+onBean.getCode()+"'";
						DbComponent.exeUpdate(sql);
						onBean.setIs_online("1");
					} else{
//						LogTool.fatal("检测站点是否在线error");
					}
				} catch (DbException e) {
					LogTool.fatal(e);
//					throw new Exception("检测站点是否在线异常"+e.getMessage());
				}
			}
        }
    }

