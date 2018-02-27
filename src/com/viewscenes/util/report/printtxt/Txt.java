package com.viewscenes.util.report.printtxt;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.viewscenes.bean.BaseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.sys.SystemTableCache;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.web.importDataManager.DataExportManager;
import com.viewscenes.web.sysmgr.dicManager.DicService;

import flex.messaging.io.amf.ASObject;

public class Txt {

    public Txt(){
    	
	}
    public static void main(String[] args) throws UtilException
    {
    	String sql="select * from radio_mark_zst_view_tab t where t.mark_datetime>=to_date('2015-03-02 00:00:00','yyyy-mm-dd hh24:mi:ss') and t.mark_datetime<to_date('2015-03-02 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
    	Txt t=new Txt();
    	try {
			t.getTxt(sql,"radio_mark_zst_view_tab","�������");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @throws UtilException 
     * @throws GDSetException 
     * @throws DbException 
     * @throws IOException 
     * ************************************************
    
    * @Title: getTxt
    
    * @Description: TODO(���ݴ����sql��䣬�����ƣ��ļ����ƴ��������ļ�)
    
    * @param @param sql
    * @param @param tab    �趨�ļ�
    
    * @author  ����
    
    * @return void    ��������
    
    * @throws
    
    ************************************************
     */
    public  static void getTxt(String sql,String tab,String fileName) throws IOException, DbException, GDSetException, UtilException{
		 String path= SystemConfig.getLoc_Video_location();
			Calendar cal=Calendar.getInstance();//ʹ��������
			  int year=cal.get(Calendar.YEAR);//�õ���
			  int month=cal.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
			  int day=cal.get(Calendar.DAY_OF_MONTH);//�õ���
			  //fileName=  "d://video_location_down//"+"//"+year+"//"+month+"//"+day+"//"+fileName;
			  fileName=  path+"//"+year+"//"+month+"//"+day+"//"+fileName+".txt";
			  File f=new File(fileName);
			  if(f.exists()&&f.length()>0)//����ļ�����˵���Ѿ����������ֱ�ӷ��ء�
			  {
				  return;
			  }
           GDSet  set=DbComponent.Query(sql);

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
				String insertleft="insert into "+tab+"("+insertColums +") values('";
				
				for(int k=0;k<columns.length;k++)
				{
					insertleft+=set.getString(i, columns[k])+"','";
				}
				allinsert=insertleft.substring(0,insertleft.length()-2)+")\n";
				
				//if(i%10==0||i==set.getRowCount())
				//{
					byte[] buffer = allinsert.getBytes("UTF-8");
					
					DataExportManager.writeFile(fileName, buffer, 0, buffer.length, true);
					allinsert="";
				//}
				
			}
		
	    
	
		
	
	
	}
	
   

}

