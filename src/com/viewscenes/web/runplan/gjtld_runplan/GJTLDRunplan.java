package com.viewscenes.web.runplan.gjtld_runplan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.runplan.GJTLDRunplanBean;
import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.bean.task.Task;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.runplan.gjt_runplan.GJTRunplanDAO;
import com.viewscenes.web.task.TaskManager;

import flex.messaging.io.amf.ASObject;

/**
 * ����̨�������ͼ������
 * @author leeo
 * @date 2012/08/01
 */
public class GJTLDRunplan {

	public GJTLDRunplan() {
		// TODO Auto-generated constructor stub
	}

	public Object queryRunplan(GJTLDRunplanBean bean){
		GJTLDRunplanDAO gjtlddao = new GJTLDRunplanDAO();
		ASObject resObj;
		ArrayList result;
		try{
			 resObj = gjtlddao.queryRunplan(bean);
			 ArrayList list =(ArrayList) resObj.get("resultList") ;
			 String classpath = GJTLDRunplanBean.class.getName();
			 result = StringTool.convertFlexToJavaList(list, classpath);
			 resObj.put("resultList", result);
		}catch(Exception e){
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
		return resObj;
	}
	
	/**
	 * ����ͼ�·�
	 * @param list
	 * @return
	 */
	public Object sendRunplan( ArrayList list){
		String res="�·�����ͼ�ɹ�!";
		TaskManager tm = new TaskManager();
		try{
//			for(int i=0;i<list.size();i++){
//				Task task = new Task();
//				task = (Task) list.get(i);
//				tm.AddSyn_Task(task);
//			}
			tm.AddSyn_Task(list);
		}catch(Exception e){
			e.printStackTrace();
			return new EXEException("",e.getMessage(),"");
		}
		
		return res;
	}
	
	/**
	 * ����ͼ���빦��
	 * @param obj
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public Object importRunplan(ASObject obj) throws Exception{
		String res="����ͼ����ɹ�!";
		String date = StringTool.date2String(new Date());
		String file_name = (String) obj.get("file_name");//��Ҫ������ļ���
		String path="C:\\runplan\\"+file_name+"";
		String code = (String) obj.get("code");
		String redisseminators = (String) obj.get("redisseminators");
		String language_id = (String) obj.get("language_id");
		String freq = (String) obj.get("freq");
		String season = (String) obj.get("season");
		String runplanIds="";//��¼��Ҫ�������������ͼid
		if(!new File(path).exists()){
			res="�ļ�·��������,���ϴ����ٵ���!";
			return res;
		}
//		String sql="update  zres_runplan_tab t set t.is_delete=1 where t.runplan_type_id='2'";
//		if(season!=null&&!season.equalsIgnoreCase("")){
//			sql+=" and t.summer like '%"+season+"%' ";
//		}
//		if(file_name.indexOf("����")>0){
//			sql+=" and t.xg_mon_area is null and t.mon_area is not null ";
//			if(code!=null&&!code.equalsIgnoreCase("")){
//				sql+=" and t.mon_area like '%"+code+"%' ";
//			}
//		}
//		if(file_name.indexOf("Ч��")>0){
//			sql+=" and t.xg_mon_area is not null and t.mon_area is null ";
//			if(code!=null&&!code.equalsIgnoreCase("")){
//				sql+=" and t.xg_mon_area like '%"+code+"%' ";
//			}
//		}
//		
//		if(redisseminators!=null&&!redisseminators.equalsIgnoreCase("")){
//			sql+=" and t.redisseminators='"+redisseminators+"'";
//		}
//		if(language_id!=null&&!language_id.equalsIgnoreCase("")){
//			sql+=" and t.language_id='"+language_id+"'";
//		}
//		if(freq!=null&&!freq.equalsIgnoreCase("")){
//			sql+=" and t.freq='"+freq+"'";
//		}
//		DbComponent.exeUpdate(sql);
		GJTLDRunplanDAO gjtlddao = new GJTLDRunplanDAO();
		try{
			Workbook book = Workbook.getWorkbook(new File(path));
			Sheet sheet = book.getSheet(0);
			int rowNum = sheet.getRows();   //�õ�������  
			int colNum = sheet.getColumns();//�õ�������
			ArrayList list = new ArrayList();//�������ͼ�����list 
			for(int i=2;i<rowNum;i++){
				GJTLDRunplanBean bean = new GJTLDRunplanBean();
		      	  for(int j=0;j<colNum;j++){
		      		  if(j==0){//�������
		      			  bean.setSentcity_id(Common.getCityIDByName(sheet.getCell(j,i).getContents()));
		      		  }
		      		  if(j==1){//ת������
		      			 bean.setRedisseminators(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==2){//Ƶ��
		      			bean.setFreq(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==4){//����
		    			  bean.setLanguage(sheet.getCell(j,i).getContents());
		    			  bean.setLanguage_id(Common.getLanguageIDByName(sheet.getCell(j,i).getContents()));
		    		  }
		      		  if(j==5){
		      			  bean.setService_area(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==6){//��ʼʱ��
		    			  bean.setStart_time(sheet.getCell(j,i).getContents());
		    		  }
		      		  if(j==7){//����ʱ��
		    			  bean.setEnd_time(sheet.getCell(j,i).getContents());
		    		  }
		      		 
		      		  if(j==9){
		    			 bean.setValid_start_time(sheet.getCell(j,i).getContents());
		    		  }
		      		  if(j==10){
		    			  bean.setValid_end_time( sheet.getCell(j,i).getContents());
		    		  }
		      		  if(j==11){
		      			  bean.setSummer(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(file_name.indexOf("����")>0){
		      			 if(j==14){//�����ղ�վ��
		      				bean.setMon_area(sheet.getCell(j,i).getContents());
			    		  }
		      			
		      		  }
		      		 if(file_name.indexOf("Ч��")>0){
		      			 if(j==14){//Ч���ղ�վ��
		      			    bean.setXg_mon_area(sheet.getCell(j,i).getContents());
		      			 }
		      		 }
		      		 if(j==15){//��ע
		      				bean.setRemark(sheet.getCell(j,i).getContents());
			    	 }
		      		 if(j==16){//����ͼid
		      				bean.setRunplan_id(sheet.getCell(j,i).getContents());
		      				runplanIds+=sheet.getCell(j,i).getContents()+",";
			    	 }
		      		 if(j==17){//�������
		      				bean.setLaunch_country(sheet.getCell(j,i).getContents());
		      				
			    	 }
		      		 if(j==18){//������
		      				bean.setWeekday(sheet.getCell(j,i).getContents());
		      				
			    	 }
		      		if(j==19){//����ʱ��
	      				bean.setStore_datetime(sheet.getCell(j,i).getContents());
	      				
		    	     }
		      	  }
		      	  list.add(bean);
		        }
			   String[] ids = runplanIds.split(",");
			    if(ids.length>0){
			    	for(int m=0;m<ids.length;m++){
			    		deleteRunplan(ids[m]);
			    	}
			    }
		        for(int k=0;k<list.size();k++){
		        	gjtlddao.addRunplan((GJTLDRunplanBean)list.get(k),date);
		        }
		}catch(Exception e){
			e.printStackTrace();
			return new EXEException("","����ͼ�����쳣��"+e.getMessage(),"");
		}
		
		return res;
	}
	
	/**
	 * ɾ������ͼ
	 * @param ids
	 * @return
	 */
	public Object delRunplan(String ids){
		String message="ɾ������ͼ�ɹ�!";
		String sql="update zres_runplan_tab set is_delete=1 where runplan_type_id=2 and runplan_id in("+ids+")";
		if(ids.equalsIgnoreCase("all")){
			sql="update zres_runplan_tab set is_delete=1 where runplan_type_id=2 ";
		}
		try {
			DbComponent.exeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","ɾ������ͼ�쳣"+e.getMessage(),"");
		}
		return message;
	}
	/**
	 * ����ʱɾ������ͼ
	 * @param ids
	 * @return
	 */
	public void deleteRunplan(String ids){
		String sql="delete from zres_runplan_tab  where runplan_type_id=2 and runplan_id in("+ids+")";
		try {
			DbComponent.exeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
