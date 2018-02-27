package com.viewscenes.sys;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.web.common.Common;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.database.*;

import flex.messaging.io.amf.ASObject;

public class SystemConfig {

    public static String localSystemCode = "900000X20";

    private static HashMap<String,String> paramConfigMap = new HashMap<String,String>();
    
    public static String runat = "0";
    
    public static void loadConfig(){
    	
    	String sql = "select * from sys_configuration_tab t";
    	GDSet set = null;
    	try {
			 set = DbComponent.Query(sql);
			 runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
			 for(int i=0;i<set.getRowCount();i++){
				 String name = set.getString(i, "param_name");
				 String value = set.getString(i, "param_value");
				 paramConfigMap.put(name, value);
				 
			 }
			
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    //45秒录音文件存放路径
    public static String getRecord45SecPath(){
    	return paramConfigMap.get("record_45sec_path");
    }
  //ftp目录
    public static String getVideo_location(){
		return paramConfigMap.get("video_location");
    }
    
    public static String getLoc_Video_location(){
		return paramConfigMap.get("loc_video_location");
    }
    
    //45秒录音文件放播的URL
    public static String getRecord45SecUrl(){
    	return paramConfigMap.get("record_45sec_url");
    } 
    //录音文件播放地址
    public static String getVideoUrl(){
	return paramConfigMap.get("video_url");
    }
    
    public static String getLocVideoUrl(){
		return paramConfigMap.get("loc_video_url");
    }
    //上报地址
    public static String getUploadDataUrl(){
    	return paramConfigMap.get("upload_data_url");
    }
    
    //录音文件存放路径
    public static String getVideoPath(){
		return paramConfigMap.get("video_location");
    }

    public static String getLocVideoPath(){
		return paramConfigMap.get("loc_video_location");
    }
    
    //ftp根路径
    public static String getFtpUrl(){
    	return paramConfigMap.get("ftp");
    }
    
}
