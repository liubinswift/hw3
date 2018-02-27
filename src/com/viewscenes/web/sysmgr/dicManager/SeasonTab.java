package com.viewscenes.web.sysmgr.dicManager;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;


import jxl.Sheet;
import jxl.Workbook;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ZdicSeasonBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;

import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;

public class SeasonTab {
	
	private static final ZdicSeasonBean bean = null;

	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:查询季节数据 ，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	
    public Object getSeason(ZdicSeasonBean bean){

    	return getSeasonlist(bean);
    }

    public Object getSeasonlist(ZdicSeasonBean bean){
	    ASObject resObj;
        String code=bean.getCode();
        String sql="select * from dic_season_tab where 1=1";
		if(code!=null&&!code.equalsIgnoreCase("")){
			sql+=" and code like '%"+code+"%'";
		}
		sql+=" order by is_now asc,end_time desc"; 
        try {
			resObj = StringTool.pageQuerySql(sql, bean);
		} catch (Exception e) {
			LogTool.fatal(e);
			return new EXEException("",e.getMessage(),"");
		}
    	
    	return resObj;
    }
    
    /**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:添加季节数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	 
    public ArrayList<Object>AddSeasonIs(ZdicSeasonBean bean){
		ArrayList<Object> list =  new ArrayList<Object>();
		try {
			
			
			String code = bean.getCode();
			
			boolean has = isExistCode(bean);
			
			if (has){
				EXEException ex =  new EXEException("", "error:该code已存在,请重新输入", "");
				list.add(ex);
				return list;
			}
			AddSeason(bean);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EXEException ex =  new EXEException("", "error:添加季节对象["+bean.toString()+"]出错,"+e.getMessage(), "");
			list.add(ex);
		}
		
		return list;
	}
    
	public static boolean isExistCode(ZdicSeasonBean SeasonBean) throws DbException, GDSetException{
			
			StringBuffer sql = new StringBuffer();
			
			sql.append(" select * from dic_season_tab t where t.code = '"+SeasonBean.getCode()+"' ");
			
			GDSet set = null;
			
			set = DbComponent.Query(sql.toString());
			
			
			if (set.getRowCount()>0){
				
				return true;
				
			}else{
				return false;
			}
			
		}
    
	public Object AddSeason(ZdicSeasonBean bean) throws Exception{
		     String insertSql ="";
		   
             String code = bean.getCode().toUpperCase();
             String start_time = bean.getStart_time();
             String end_time = bean.getEnd_time();
             String  is_now = bean.getIs_now();
             
             
            insertSql = "insert into dic_season_tab(code,start_time,end_time,is_now) " +
            				 "values('"+code+"','"+start_time+"','"+end_time+"','"+is_now+"')";
            String sql="select * from dic_season_tab where code='"+code+"'";
            String upSql= "update dic_season_tab set is_now = 1";
            try{
            	GDSet gd=DbComponent.Query(sql);
            	if(gd.getRowCount()>0){
            		System.out.println(gd.getString(0, "code"));
            		return new EXEException("","季节CODE已存在，请重新填写","");
            	}
            	if(is_now.equals("0")){
            		DbComponent.exeUpdate(upSql);
            	}
            	DbComponent.exeUpdate(insertSql);
            	
            }
            catch (DbException e) {
            	e.printStackTrace();
    			return  new EXEException("", ","+e.getMessage(), "");
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    			return new EXEException("","后台错误!",null);
    		}
            
    		return "";
	}
	
	
	  /**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:删除季节数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
    public Object deleteSeason(ASObject asObj){
    	
		String dellist=(String)asObj.get("dellist");
		
		try{
			System.out.println("dellist="+dellist);
//			delSeason(dellist);
			String sql = "delete from dic_season_tab where code in("+dellist+")";
			DbComponent.exeUpdate(sql);
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
		 return " ";
	}
    
    
    /**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:删除数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	
	public boolean delSeason(String dellist) throws Exception {
		String sql ="";
		boolean df=false;
		
		sql = "delete from dic_season_tab where code in("+dellist+")";
		
		df=DbComponent.exeUpdate(sql);
		return df;
	}
	
	
	/**
	 * 
	* <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:修改季节信息，如果修改出错会返回错误信息
	 * <p>author-date:谭长伟 2012-2-28
	 * @param:
	 * @return:
	 */
	public Object updateSeason(ZdicSeasonBean bean) throws Exception{
	     ArrayList<Object> list =  new ArrayList<Object>();
	     String updateSql ="";
	   
        String code = bean.getCode().toUpperCase();
        String start_time = bean.getStart_time();
        String end_time = bean.getEnd_time();
        String  is_now = bean.getIs_now();
        
        
        updateSql = "update  dic_season_tab set start_time ='"+start_time+"'," +
            		"end_time='"+end_time+"',is_now='"+is_now+"' where code = '"+code+"'";
     
       try{
    	 DbComponent.exeUpdate(updateSql);
    	 
       }
       catch (DbException e) {
       	e.printStackTrace();
			EXEException ex =  new EXEException("", ","+e.getMessage(), "");
			list.add(ex);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
       
		return list;
   }
	public	Object importExcel(ASObject obj){
		String file_name=(String)obj.get("file_name");
		HashMap<String,String> map=new HashMap<String, String>();
		
		try {
			String path="C:\\runplan\\"+file_name+"";
			File file=new File(path);
			Workbook book=Workbook.getWorkbook(file);
			Sheet sheet=book.getSheet(0);
			int rowNum = sheet.getRows();   //得到总行数  
			int colNum = sheet.getColumns();//得到总列数
			ArrayList<ASObject> list = new ArrayList<ASObject>();//存放运行图对象的list 
			for(int i=1;i<rowNum;i++){
				ASObject aso=new ASObject();
				
				for(int j=0;j<colNum;j++){
					switch (j) {
					case 0:
						aso.put("code", sheet.getCell(j,i).getContents());
						break;
					case 1:	
						
						aso.put("start_time", sheet.getCell(j,i).getContents());
						break;
					case 2:	
						aso.put("end_time", sheet.getCell(j,i).getContents());
						break;
				
					case 3:	
						if(sheet.getCell(j, i).getContents().equals("当前")){
							aso.put("is_now", "0");
						}else{
							aso.put("is_now", "1");
						}
						break;
					
					}
				}
				
				list.add(aso);
			}
			int num=list.size();
			for(int i=0;i<num;i++){
				ASObject asObj=list.get(i);
				ZdicSeasonBean bean = new ZdicSeasonBean();
				bean.setCode((String)asObj.get("code"));
				bean.setStart_time((String)asObj.get("start_time"));
				bean.setEnd_time((String)asObj.get("end_time"));
				bean.setIs_now((String)asObj.get("is_now"));
				String sql=" select * from dic_season_tab where code ='"+(String)asObj.get("code")+"'";
				GDSet set=DbComponent.Query(sql);
				if(set.getRowCount()>0){
					updateSeason(bean);		
				}else{
					AddSeason(bean);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","季节代号信息导入异常："+e.getMessage(),"");
		}
		
		return "导入季节代号信息成功";
	}
	
}
