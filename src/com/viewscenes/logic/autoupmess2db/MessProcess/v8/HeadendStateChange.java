package com.viewscenes.logic.autoupmess2db.MessProcess.v8;
/**
 *  * 站点状态修改
	<?xml version="1.0" encoding="GB2312" standalone="yes"?>
	<Msg Version="8" MsgID="1" Type="RadioUp" DateTime="2006-08-17 15:30:00" SrcCode="R61D01" DstCode="CBT01" Priority="-1">
	　　<Return Type="headendStateChange" Value="0" Desc="成功"/>
	  	<online code=”10.10.2.1”/>
	  	<offline code=”10.10.2.1”/>
	</Msg>
 */
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Element;

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
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;

public class HeadendStateChange implements IUpMsgProcessor {



	public void processUpMsg(Element root) throws SQLException,
			UpMess2DBException, GDSetException, DbException, UtilException,
			NoRecordException {
		String clientCode = root.getAttributeValue("SrcCode");
		if("headendUpClient".equals(clientCode))
		{
			HeadendStateBean bean = getHeadendStateBean(root);
			updateHeadonLineStatusMap(bean);
		}
	}

	public HeadendStateBean getHeadendStateBean(Element root)  throws UpMess2DBException{
		HeadendStateBean bean = new HeadendStateBean();
		Element ipele = root.getChild("headendStateChange");
		String online = ipele.getAttributeValue("online"); 
		String offline = ipele.getAttributeValue("offline"); 
		bean.setOffline(offline);
		bean.setOnline(online);
		return bean;
	}


	public static void main(String[] args )
	{
		String regex="//(.*?):(.*?)";
		Pattern p=Pattern.compile(regex);
		String t="http://10.1.2.20:8080/enter/";
		Matcher m=p.matcher(t);
		while(m.find()){
			System.out.println(m.group(1));
			System.out.println(m.group(2));
		}
		
		String reg = "((\\d+\\.){3}\\d+)\\:(\\d+)";
		String url = "http://10.1.2.20:8080/enter/";
		String ip = url.replaceAll(reg, "$1");
		String port = url.replaceAll(reg, "$3");
		System.out.println(ip+":"+port);
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
	public void updateHeadonLineStatusMap(HeadendStateBean bean) throws UpMess2DBException {
		try{
			if(bean!=null) {
				String[] onlineHeadCodes= bean.getOffline().split(",");
				String[] offlineHeadCodes= bean.getOffline().split(",");
				for(String code :onlineHeadCodes) {
					updateHeadState(code,"1");
				}
				for(String code :offlineHeadCodes) {
					updateHeadState(code,"0");
				}
			}
			
		} catch (Exception ex) {
			throw new UpMess2DBException("更新站点是否在线异常：code", ex);
		}
	}

	private void updateHeadState(String code, String is_online) throws DbException {
		
		HeadOnlineStatusBean onlineBean = SystemCache.onLineStatusMap.get(code);
		
		if(onlineBean == null){
			onlineBean = new HeadOnlineStatusBean();
			onlineBean.setIntervalReport("10");
			onlineBean.setCode(code);
		}
        onlineBean.setLastSaveTime(new Date());
		onlineBean.setFristCreate(false);
		onlineBean.setIs_online(is_online);
		if(onlineBean.getIs_online().equals("1"))
		{
			String updateOnlineString="update res_headend_tab set is_online= 1 where  upper(code)=upper('" + code + "')";
			DbComponent.exeUpdate(updateOnlineString);
		}else {
			String updateOnlineString="update res_headend_tab set is_online= 0 where  upper(code)=upper('" + code+ "')";
			DbComponent.exeUpdate(updateOnlineString);
		}
		
		SystemCache.onLineStatusMap.put(onlineBean.getCode(), onlineBean);
	}
	
}
/**
 * 站点状态bean
 * 
 * @author Administrator
 * 
 */
class HeadendStateBean {
	private String online = "";//在线code
	private String offline = "";//离线code
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getOffline() {
		return offline;
	}
	public void setOffline(String offline) {
		this.offline = offline;
	}

	

}