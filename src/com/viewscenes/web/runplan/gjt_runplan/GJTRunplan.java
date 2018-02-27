package com.viewscenes.web.runplan.gjt_runplan;
/**
 * ����̨����ͼ������
 * @author leeo
 * @date 2012/08/01
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.runplan.GJTRunplanBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.task.TaskManager;

import flex.messaging.io.amf.ASObject;

public class GJTRunplan {
	
	public GJTRunplan(){
		
	}
    
	public Object queryRunplan(GJTRunplanBean bean){
		GJTRunplanDAO grldao = new GJTRunplanDAO();
		ASObject resObj;
		ArrayList result;
		try {
			 resObj = grldao.queryRunplan(bean);
			 ArrayList list =(ArrayList) resObj.get("resultList") ;//ȡ������Ľ����
			 String classpath = GJTRunplanBean.class.getName();
			 result = StringTool.convertFlexToJavaList(list, classpath);//����������ת����bean���ٷŻؽ�����С�
			 resObj.put("resultList", result);
		} catch (Exception e) {
			e.printStackTrace();
			EXEException exe = new EXEException("", 
					e.getMessage(), "");
			return exe ;
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
		String storeTime = StringTool.date2String(new Date());
		String res="����ͼ����ɹ�!";
		String file_name = (String) obj.get("file_name");//��Ҫ������ļ���
		String season = (String) obj.get("season");
		String code = (String) obj.get("code");
		String station_id = (String) obj.get("station_id");
		String language_id = (String) obj.get("language_id");
		String freq = (String) obj.get("freq");
		String path="C:\\runplan\\"+file_name+"";
		String runplanIds="";//��¼��Ҫ�������������ͼid
		if(!new File(path).exists()){
			res="�ļ�·��������,���ϴ����ٵ���!";
			return res;
		}
		
		GJTRunplanDAO grldao = new GJTRunplanDAO();
		try{
//			String sql=" update zres_runplan_tab t set t.is_delete=1 where t.runplan_type_id='1'";
//			if(season!=null&&!season.equalsIgnoreCase("")){
//				sql+=" and t.season_id='"+season+"'";
//			}
//			if(file_name.indexOf("����")>0){
//				sql+=" and t.xg_mon_area is null and t.mon_area is not null ";
//				if(code!=null&&!code.equalsIgnoreCase("")){
//					sql+=" and t.mon_area like '%"+code+"%' ";
//				}
//			}
//			if(file_name.indexOf("Ч��")>0){
//				sql+=" and t.xg_mon_area is not null and t.mon_area is null ";
//				if(code!=null&&!code.equalsIgnoreCase("")){
//					sql+=" and t.xg_mon_area like '%"+code+"%' ";
//				}
//			}
//			
//			if(station_id!=null&&!station_id.equalsIgnoreCase("")){
//				sql+=" and t.station_id='"+station_id+"'";
//			}
//			if(language_id!=null&&!language_id.equalsIgnoreCase("")){
//				sql+=" and t.language_id='"+language_id+"'";
//			}
//			if(freq!=null&&!freq.equalsIgnoreCase("")){
//				sql+=" and t.freq='"+freq+"'";
//			}
//			DbComponent.exeUpdate(sql);
			Workbook book = Workbook.getWorkbook(new File(path));
			Sheet sheet = book.getSheet(0);
			int rowNum = sheet.getRows();   //�õ�������  
			int colNum = sheet.getColumns();//�õ�������
			ArrayList list = new ArrayList();//�������ͼ�����list 
			for(int i=2;i<rowNum;i++){
				GJTRunplanBean bean = new GJTRunplanBean();
		      	  for(int j=0;j<colNum;j++){
		      		  if(j==0){//����̨
		      			 bean.setStation_name(sheet.getCell(j,i).getContents());
		      			 //bean.setStation_id(Common.getStationIDByName(sheet.getCell(j,i).getContents()));
		      		  }
		      		  if(j==1){
		      			bean.setTransmiter_no(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==2){//Ƶ��
		      			bean.setFreq(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==6){//����
		    			  bean.setLanguage(sheet.getCell(j,i).getContents());
		    			  bean.setLanguage_id(Common.getLanguageIDByName(sheet.getCell(j,i).getContents()));
		    		  }
		      		  if(j==7){
		      			  bean.setPower(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==8){
		      			  bean.setProgram_type(sheet.getCell(j,i).getContents());
		      		  }
		      		  if(j==9){//��ʼʱ��
		    			  bean.setStart_time(sheet.getCell(j,i).getContents());
		    		  }
		      		  if(j==10){//����ʱ��
		    			  bean.setEnd_time(sheet.getCell(j,i).getContents());
		    		  }
		      		 
		      		  if(j==11){
		    			 bean.setValid_start_time(sheet.getCell(j,i).getContents());
		    		  }
		      		  if(j==12){
		    			  bean.setValid_end_time( sheet.getCell(j,i).getContents());
		    		  }
		      		 
		      		  if(j==14){//�����ղ�վ��
		      			 if(file_name.indexOf("����")>0){
		      				 bean.setMon_area(sheet.getCell(j,i).getContents());
			      		  }else bean.setXg_mon_area(sheet.getCell(j,i).getContents());
		    		  }
		      		  if(j==15){
		      			  bean.setStation_id(sheet.getCell(j,i).getContents());//����̨id
		      		  }
		      		if(j==16){
		      			  bean.setRunplan_id(sheet.getCell(j,i).getContents());//����ͼid
		      			 runplanIds+=sheet.getCell(j,i).getContents()+",";
		      		  }
		      		if(j==17){
		      			  bean.setSeason_id(sheet.getCell(j,i).getContents());//���ڴ���
		      		  }
		      		if(j==18){
		      			  bean.setSatellite_channel(sheet.getCell(j,i).getContents());//ͨ��
		      		  }
		      		if(j==19){
		      			  bean.setWeekday(sheet.getCell(j,i).getContents());//������
		      		  }
		      		if(j==20){
		      			 bean.setStore_datetime(sheet.getCell(j,i).getContents());//��������
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
					grldao.addRunplan((GJTRunplanBean)list.get(k),storeTime);
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
		String sql="update zres_runplan_tab set is_delete=1  where runplan_type_id=1 and runplan_id in("+ids+")";
		if(ids.equalsIgnoreCase("all")){
			sql="update  zres_runplan_tab set is_delete=1 where runplan_type_id=1";
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
	 * ɾ������ͼ
	 * @param ids
	 * @return
	 */
	public void deleteRunplan(String ids){
	
		String sql="delete from zres_runplan_tab  where runplan_type_id=1 and runplan_id in("+ids+")";
		try {
			DbComponent.exeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			//return new EXEException("","ɾ������ͼ�쳣"+e.getMessage(),"");
		}
		//return message;
	}
}
