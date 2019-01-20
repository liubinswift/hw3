package com.viewscenes.logic.autoupmess2db.MessProcess.v8;
/**
 *  * 站点ip上传
	<?xml version="1.0" encoding="GB2312" standalone="yes"?>
	<Msg Version="8" MsgID="1" Type="RadioUp" DateTime="2006-08-17 15:30:00" SrcCode="R61D01" DstCode="CBT01" Priority="-1">
	　　<Return Type="IpChange" Value="0" Desc="成功"/>
	  	<IpChange ip=”10.10.2.1”/>
	</Msg>
 */
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.utils.StringUtils;
import org.jdom.Element;

import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.pub.HeadOnlineStatusBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.util.InnerDevice;
import com.viewscenes.logic.autoupmess2db.Exception.NoRecordException;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.IUpMsgProcessor;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemCache;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.web.common.Common;

public class RadioIpChange implements IUpMsgProcessor {

	String headCode = "";

	public void processUpMsg(Element root) throws SQLException,
			UpMess2DBException, GDSetException, DbException, UtilException,
			NoRecordException {
		headCode = root.getAttributeValue("SrcCode");
		  //这里需要判断是否迁移后的站点，如果是迁移后的站点不需要修改对应的下发地址；
 	   ResHeadendBean resHeadend = Common.getHeadendBeanByCode(headCode);
 	   if(resHeadend!=null&&!("2").equals(resHeadend.getCom_protocol())) {
 		  IpChangeBean bean = getIpChangeBean(root);
 			HeadOnlineStatusBean onbean = SystemCache.onLineStatusMap.get(bean.getHeadCode());
 			String oldIp = "";
 			String oldPort = "";
 			if(onbean!=null){
 				oldIp = onbean.getIp();
 				oldPort = onbean.getPort();
 			}
 			updateHeadonLineStatusMap(bean);
 			if(bean.getIp() == null || bean.getIp().equals("")){
 				LogTool.info("autoup2mess","IP上报:ip为空");
 			} else{
 				if(bean.getIp().equals(oldIp) && bean.getPort().equals(oldPort)){
 					LogTool.info("autoup2mess","oldIp=="+oldIp+"&&bean.getIp()=="+bean.getIp()+"&&oldPort=="+oldPort+"&&bean.getPort()=="+bean.getPort());
 				} else{
 					try{
 						ipChangeToDb(bean);
 						bean.setIp(oldIp);
 						bean.setPort(oldPort);
 					} catch(UpMess2DBException ex){
 						throw ex;
 					}
 				}
 			}
 	   }
	}

	public IpChangeBean getIpChangeBean(Element root)  throws UpMess2DBException{
		IpChangeBean bean = new IpChangeBean();
		Element ipele = root.getChild("ipchange");
		String ips = ipele.getAttributeValue("ip"); // 获得ip
		String port = ipele.getAttributeValue("port"); // 获得端口
		if(ips!=null&&ips.length()>0)
		{
			String[]  ipsArray= ips.split(",");
			if(ipsArray.length ==2)
			{
				bean.setIp(ipsArray[0]);	
				bean.setNetIp(ipsArray[1]);
			}else if(ipsArray.length ==1)
			{
				bean.setIp(ipsArray[0]);	
			}
					
		}
		bean.setPort(port);
		bean.setHeadCode(headCode);
		return bean;
	}

	/**
	 * 修改ip入库
	 * 
	 * @detail
	 * @method
	 * @param bean
	 * @return void
	 * @author zhaoyahui
	 * @version 2012-10-19 下午05:26:34
	 */
	public void ipChangeToDb(IpChangeBean bean) throws UpMess2DBException {
		String url = "";
		try {
			String sql = "select url from res_headend_tab where is_delete=0 and upper(code)=upper('" + bean.getHeadCode() + "')";
			GDSet gd = DbComponent.Query(sql);
			if(gd.getRowCount()>0){
				url = gd.getString(0, "url");
			}
			String subSql = "";
			if(url != null && url.length()>0 && url.indexOf("http://")>-1 && url.indexOf("/")>-1){
				String ipport = url.substring(url.indexOf("http://")+7);
					if(ipport.indexOf("/")!=-1){
					  subSql = " , url='http://"+bean.getIp()+":"+bean.getPort()+ipport.substring(ipport.indexOf("/"),ipport.length())+"' "; 
					}else
					{
					  subSql = " , url='http://"+bean.getIp()+":"+bean.getPort()+"' "; 		
					}
				}
			sql = "update res_headend_tab set " +
					"ip = '" + bean.getNetIp() + "' " +
					subSql +
					" where upper(code)=upper('" + bean.getHeadCode() + "')";
			LogTool.info("autoup2mess","IP上报SQL=="+sql);
			DbComponent.exeUpdate(sql);
			InnerDevice.refreshInstance();
			TableInfoCache s = new TableInfoCache("res_headend_tab");
		} catch (Exception ex) {
			throw new UpMess2DBException("IP上报异常：code"+bean.getHeadCode()+"IP"+bean.getIp()+"port"+bean.getPort(), ex);
		}
	}
	public static void main(String[] args )
	{
		String url ="'http://5.38.175.128:9981/Receiver/Enter/";
		if(url != null && url.length()>0 && url.indexOf("http://")>-1 && url.indexOf("/")>-1){
			String ipport = url.substring(url.indexOf("http://")+7);
			String subSql = " , url='http://10.0.1.2:8080"+ipport.substring(ipport.indexOf("/"),ipport.length())+"' "; 
		    System.out.print(subSql);
		}
	}
	/**
	 * 更新站点是否在线缓存map
	 * @detail  
	 * @method  
	 * @throws UpMess2DBException 
	 * @return  void  
	 * @author  zhaoyahui
	 * @version 2013-3-22 下午01:53:18
	 */
	public void updateHeadonLineStatusMap(IpChangeBean bean) throws UpMess2DBException {
		try{
			HeadOnlineStatusBean onlineBean = SystemCache.onLineStatusMap.get(bean.getHeadCode());
			
			if(onlineBean == null){
				onlineBean = new HeadOnlineStatusBean();
				LogTool.fatal("onlineBean is null i new it==="+bean.getHeadCode());
				onlineBean.setIntervalReport("10");
				onlineBean.setCode(bean.getHeadCode());
			}
//			LogTool.fatal("updateHeadonLineStatusMap:code=="+onlineBean.getCode()+"&&getLastSaveTime=="+StringTool.date2String(onlineBean.getLastSaveTime()));
			onlineBean.setLastSaveTime(new Date());
			onlineBean.setIp(bean.getIp());
			onlineBean.setPort(bean.getPort());
			onlineBean.setFristCreate(false);
			/**
			 * 这里缺少把站点置为在线的操作.
			 * 修改人：刘斌
			 * 修改日期：2013-10-21
			 */
			if(onlineBean.getIs_online().equals("0"))//不在线
			{
				String updateOnlineString="update res_headend_tab set is_online=1 where  upper(code)=upper('" + bean.getHeadCode() + "')";
				DbComponent.exeUpdate(updateOnlineString);
			}
			
			SystemCache.onLineStatusMap.put(onlineBean.getCode(), onlineBean);
			
		} catch (Exception ex) {
			throw new UpMess2DBException("更新站点是否在线异常：code"+bean.getHeadCode()+"IP"+bean.getIp()+"port"+bean.getPort(), ex);
		}
	}
	
}
/**
 * 站点ip上传bean
 * 
 * @author Administrator
 * 
 */
class IpChangeBean {
	private String headCode = "";//code
	private String ip = "";//通信ip
	private String netIp = "";//网络Ip
	private String port = "";//端口

	public String getHeadCode() {
		return headCode;
	}

	public void setHeadCode(String headCode) {
		this.headCode = headCode;
	}

	public String getIp() {
		return ip;
	}

	public String getNetIp() {
		return netIp;
	}

	public void setNetIp(String netIp) {
		this.netIp = netIp;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}