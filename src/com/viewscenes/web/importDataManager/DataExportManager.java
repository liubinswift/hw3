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
 * ��Ŀ���ƣ�hwdata �����ƣ�DataImportManager �������� �����ֶ������������ݡ� �����ˣ����� ����ʱ�䣺2013-1-8
 * ����03:04:05 �޸��ˣ����� �޸�ʱ�䣺2013-1-8 ����03:04:05 �޸ı�ע��
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
	 * @Description: TODO(������Ч�ڲ�ѯ֪ͨ)
	 * @param @param NotificationBean
	 * @param @return �趨�ļ�
	 * @author ����
	 * @return Object ��������
	 * @throws
	 * 
	 ************************************************ 
	 */
	public Object getFileList(ImportFileBean obj) {
		ASObject objRes = new ASObject();
		String datetime = obj.getDate();// ��Ч��ʼʱ��
		ArrayList<ImportFileBean> list = new ArrayList();
		
		// 2013-12-13
		String path = SystemConfig.getVideo_location();
		String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
		  if (runat.equals("0")){
			  path = SystemConfig.getLoc_Video_location();
		  }
		Calendar cal = Calendar.getInstance();// ʹ��������
		try {
			cal.setTime(StringDateUtil.getDateTime(datetime + " 00:00:00"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new EXEException("", "��ѯ�ļ�ʧ�ܣ�������������", null);
		}
		int year = cal.get(Calendar.YEAR);// �õ���
		int month = cal.get(Calendar.MONTH) + 1;// �õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
		int day = cal.get(Calendar.DAY_OF_MONTH);// �õ���
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
		  
		  String fileName = (String)obj.get("fileName");//��
		    String tableName = (String)obj.get("tableName");//��
	        String starttime = (String)obj.get("starttime");//��ʼʱ��
	        String endtime = (String)obj.get("endtime");//��ʼʱ��
	        String path = SystemConfig.getVideo_location();
	        String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
			  if (runat.equals("0")){
				  path = SystemConfig.getLoc_Video_location();
			  }
			Calendar cal = Calendar.getInstance();// ʹ��������
			int year = cal.get(Calendar.YEAR);// �õ���
			int month = cal.get(Calendar.MONTH) + 1;// �õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
			int day = cal.get(Calendar.DAY_OF_MONTH);// �õ���
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
						return "��ѡ����û�м�����ݣ�";
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
				
	        return "�������ݳɹ����뵽�̶��洢�Ͻ��в�����";
	  }
	  /**
		 * ����дString���ļ���,��ȡ׷��д�ķ�ʽ���÷����ڼ�¼��־��ʹ��
		 * 
		 * @param fileName
		 *            Ҫд����Ϣ���ļ���
		 * @param buffer
		 *            Ҫд���ļ�������
		 * @param off
		 *            ����д�����ʼλ��
		 * @param len
		 *            д��ĳ���
		 * @param bAppend
		 *            �Ƿ�׷��д
		 * @throws UtilException
		 *             д���ļ����������׳��쳣
		 */
		/**
		 * ����������ʽд���ļ�,��ȡ׷��д�ķ�ʽ,��д����ļ���С������
		 * 
		 * @param fileName
		 *            Ҫд����Ϣ���ļ���
		 * @param buffer
		 *            Ҫд���ļ�������
		 * @param off
		 *            ����д�����ʼλ��
		 * @param len
		 *            д��ĳ���
		 * @param bAppend
		 *            �Ƿ�׷��д
		 * @throws UtilException
		 *             д���ļ����������׳��쳣
		 */
		public static void writeFile(String fileName, byte[] buffer, int off,
				int len, boolean bAppend) throws UtilException {
			FileOutputStream fos = null;
			try {
				File file = new File(fileName);

				// ���ļ�����������Ҫ�ȴ����ļ�����Ŀ¼
				if (!file.exists()) {
					makeDirectory(file.getParent());
				}
				// д���ļ�
				fos = new FileOutputStream(fileName, bAppend);

				fos.write(buffer, off, len);
				
			} catch (IOException e) {
				throw new UtilException("д�ļ�����:" + e.getMessage(), e);
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (IOException ex) {
					throw new UtilException("�޷��ر�FileOutputStream"
							+ ex.getMessage(), ex);
				}
			}
		}
		/**
		 * �ݹ鴴���ļ�Ŀ¼
		 * 
		 * @param pathName
		 *            ����·������
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
