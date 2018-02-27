package com.viewscenes.web.importDataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ImportFileBean;
import com.viewscenes.dao.XmlReader;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.web.Daoutil.StringDateUtil;
import flex.messaging.io.amf.ASObject;

/**
 * *************************************
 * 
 * 项目名称：hwdata 类名称：DataImportManager 类描述： 用于手动导入外网数据。 创建人：刘斌 创建时间：2013-1-8
 * 下午03:04:05 修改人：刘斌 修改时间：2013-1-8 下午03:04:05 修改备注：
 * 
 * @version
 * 
 *************************************** 
 */
public class DataExportManager {

	/**
	 * ************************************************
	 * 
	 * @Title: getLogList
	 * @Description: TODO(根据有效期查询通知)
	 * @param @param NotificationBean
	 * @param @return 设定文件
	 * @author 刘斌
	 * @return Object 返回类型
	 * @throws
	 * 
	 ************************************************ 
	 */
	public Object getFileList(ImportFileBean obj) {
		ASObject objRes = new ASObject();
		String datetime = obj.getDate();// 有效开始时间
		ArrayList<ImportFileBean> list = new ArrayList();
		
		// 2013-12-13
		String path = SystemConfig.getVideo_location();
		String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
		  if (runat.equals("0")){
			  path = SystemConfig.getLoc_Video_location();
		  }
		Calendar cal = Calendar.getInstance();// 使用日历类
		try {
			cal.setTime(StringDateUtil.getDateTime(datetime + " 00:00:00"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new EXEException("", "查询文件失败，传入日期有误！", null);
		}
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		// fileName=
		// "d://video_location_down//"+"//"+year+"//"+month+"//"+day+"//"+fileName;
		path = path + "//" + year + "//" + month + "//" + day;

		File pathfile = new File(path);
		if(pathfile.exists())
		{
		File[] filelist = pathfile.listFiles();
		ArrayList<File> txtlist = new ArrayList<File>();
		for (int i = 0; i < filelist.length; i++) {
			if (!filelist[i].isDirectory()
					&& filelist[i].getName().endsWith(".txt")) {
				txtlist.add(filelist[i]);
			}
		}
	
		for (int i = 0; i < txtlist.size(); i++) {
			File txtfile = (File) txtlist.get(i);
			ImportFileBean file = new ImportFileBean();
			file.setDate(datetime);
			file.setFile_path(txtfile.getAbsolutePath());
			file.setFile_name(txtfile.getName());
			file.setFile_size(txtfile.length() / 1024 + "");
			list.add(file);
		}
		
		}
		objRes.put("resultList", list);
		objRes.put("resultTotal", list.size());
		return objRes;
	}

	/*
	 * 
	 */
	  public static Object exporttData(ASObject obj)
	  {
		  ASObject objRes = new ASObject();
		  
		  String fileName = (String)obj.get("fileName");//。
		    String tableName = (String)obj.get("tableName");//。
	        String starttime = (String)obj.get("starttime");//开始时间
	        String endtime = (String)obj.get("endtime");//开始时间
	        String path = SystemConfig.getVideo_location();
	        String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
			  if (runat.equals("0")){
				  path = SystemConfig.getLoc_Video_location();
			  }
			Calendar cal = Calendar.getInstance();// 使用日历类
			int year = cal.get(Calendar.YEAR);// 得到年
			int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
			int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
			path = path + "" + year + "\\" + month + "\\" + day+"\\"+fileName+"("+starttime+"~"+endtime+").txt";
			
			path=path.replaceAll(":", "").replace("-", "");
			LogTool.fatal("path=="+path);
			
			
			String returnstr="";
			
				try {
					
					String sql="";
					if(tableName.equals("radio_mark_zst_view_tab"))
					{
						sql="select * from radio_mark_zst_view_tab t where t.mark_datetime>=to_date('"+starttime+"','yyyy-mm-dd hh24:mi:ss') and t.mark_datetime<to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') ";
					}else if(tableName.equals("radio_stream_result_tab"))
					{
						sql="select * from radio_stream_result_tab t where store_datetime>=to_date('"+starttime+"','yyyy-mm-dd hh24:mi:ss') and store_datetime<to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') ";
							
					}else if(tableName.equals("radio_spectrum_stat_tab"))
					{
						sql="select * from radio_spectrum_stat_tab t where input_datetime>=to_date('"+starttime+"','yyyy-mm-dd hh24:mi:ss') and input_datetime<to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') ";
							
					}else if(tableName.equals("radio_equ_alarm_tab"))
					{
						sql="select * from radio_equ_alarm_tab t where alarm_datetime>=to_date('"+starttime+"','yyyy-mm-dd hh24:mi:ss') and alarm_datetime<to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') ";
							
					}
					else if(tableName.equals("radio_abnormal_tab"))
					{
						sql="select * from radio_abnormal_tab t where abnormal_date>=to_date('"+starttime+"','yyyy-mm-dd hh24:mi:ss') and abnormal_date<to_date('"+endtime+"','yyyy-mm-dd hh24:mi:ss') ";
							
					}
					GDSet set =DbComponent.Query(sql);
					if(set.getRowCount()==0)
					{
						return "所选日期没有监测数据！";
					}
					String[] columns=set.getAllColumnName();
					String insertColums="";
					String allinsert="";
					for(int k=0;k<columns.length;k++)
					{
						insertColums+=columns[k]+",";
					}
					insertColums=insertColums.substring(0,insertColums.length()-1);
					
					for(int i=0;i<set.getRowCount();i++)
					{
						String insertleft="insert into "+tableName+"("+insertColums +") values('";
						
						for(int k=0;k<columns.length;k++)
						{
							insertleft+=set.getString(i, columns[k])+"','";
						}
						allinsert=insertleft.substring(0,insertleft.length()-2)+")\n";
						
						//if(i%10==0||i==set.getRowCount())
						//{
							byte[] buffer = allinsert.getBytes("UTF-8");
							
							writeFile(path, buffer, 0, buffer.length, true);
							allinsert="";
						//}
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				} 
				
	        return "导出数据成功，请到固定存储上进行操作！";
	  }
	  /**
		 * 快速写String到文件中,采取追加写的方式，该方法在记录日志中使用
		 * 
		 * @param fileName
		 *            要写入信息的文件名
		 * @param buffer
		 *            要写入文件的内容
		 * @param off
		 *            数组写入的起始位置
		 * @param len
		 *            写入的长度
		 * @param bAppend
		 *            是否追加写
		 * @throws UtilException
		 *             写入文件发生错误抛出异常
		 */
		/**
		 * 快速以流方式写到文件,采取追加写的方式,对写入的文件大小无限制
		 * 
		 * @param fileName
		 *            要写入信息的文件名
		 * @param buffer
		 *            要写入文件的内容
		 * @param off
		 *            数组写入的起始位置
		 * @param len
		 *            写入的长度
		 * @param bAppend
		 *            是否追加写
		 * @throws UtilException
		 *             写入文件发生错误抛出异常
		 */
		public static void writeFile(String fileName, byte[] buffer, int off,
				int len, boolean bAppend) throws UtilException {
			FileOutputStream fos = null;
			try {
				File file = new File(fileName);

				// 若文件不存在则需要先创建文件所在目录
				if (!file.exists()) {
					makeDirectory(file.getParent());
				}
				// 写入文件
				fos = new FileOutputStream(fileName, bAppend);

				fos.write(buffer, off, len);
				
			} catch (IOException e) {
				throw new UtilException("写文件错误:" + e.getMessage(), e);
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (IOException ex) {
					throw new UtilException("无法关闭FileOutputStream"
							+ ex.getMessage(), ex);
				}
			}
		}
		/**
		 * 递归创建文件目录
		 * 
		 * @param pathName
		 *            完整路径名称
		 */
		private static void makeDirectory(String pathName) {
			if (pathName == null)
				return;
			File file = new File(pathName);
			if (!file.exists()) {
				if (file.getParent() != null)
					makeDirectory(file.getParent());
				file.mkdirs();
			}
		}
}
