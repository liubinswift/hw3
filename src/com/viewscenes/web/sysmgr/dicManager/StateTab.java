package com.viewscenes.web.sysmgr.dicManager;


import java.text.ParseException;

import java.util.ArrayList;


import org.jmask.web.controller.EXEException;


import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;

import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.web.sysmgr.dicManager.DicDao;
import com.viewscenes.web.sysmgr.dicManager.DicService;

import flex.messaging.io.amf.ASObject;

import com.viewscenes.bean.DicStateBean;


public class StateTab {
	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:提交更新大洲数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 * @throws GDSetException 
	 * @throws DbException 
	 */

	 
	public Object modifyState(ASObject asObj) throws ParseException, DbException, GDSetException{
		
		
		String insertSql ="";
		String updateSql ="";
		ArrayList insertIdList =  new ArrayList();
		ArrayList updateIdList =  new ArrayList();
		insertIdList=(ArrayList)asObj.get("insert");
		updateIdList=(ArrayList)asObj.get("update");
		int iNum = 0;
        int uNum = 0;
        
		System.out.println("insertIdList.size()="+insertIdList.size());
		System.out.println("updateIdList.size()="+updateIdList.size());
       for(int i=0;i<insertIdList.size();i++){
    	     ASObject resultStat = (ASObject)insertIdList.get(i);
    	     String state = (String)resultStat.get("state");
             String state_name = (String)resultStat.get("state_name");
             String val = getStateNextVal();
      
                 insertSql = "insert into dic_state_tab(state, state_name) " +
            				 "values('"+val+"','"+state_name+"')";
          
         		
            try{
         	 DbComponent.exeUpdate(insertSql);
//         	DicStateBean DicStateBean = new DicStateBean();
//         	DicStateBean.setState(val);
//         	DicStateBean.setState_name(state_name);
//  		  
//  		     TableInfoCache as =new TableInfoCache();
//  		     as.refreshTableMap("dic_state_tab",DicStateBean,DicStateBean.getState(),"1");
            }
            catch (DbException e) {
    			e.printStackTrace();
    			return new EXEException("","数据库查询错误!",null);
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    			return new EXEException("","后台错误!",null);
    		}
             iNum++;
         }
       
       for(int i=0;i<updateIdList.size();i++){
    	   
    	   ASObject resultStat = (ASObject)updateIdList.get(i);
    	   String state = (String)resultStat.get("state");
  	       String state_name = (String)resultStat.get("state_name");
        
        
           
           updateSql = " update  dic_state_tab set state_name='"+state_name+"' where state='"+state+"'";

           
           try{
           	 DbComponent.exeUpdate(updateSql);
           	DicStateBean DicStateBean = new DicStateBean();
         	DicStateBean.setState(state);
         	DicStateBean.setState_name(state_name);
  		  
  		     TableInfoCache as =new TableInfoCache();
  		     as.refreshTableMap("dic_state_tab",DicStateBean,DicStateBean.getState(),"2");
              }
              catch (DbException e) {
      			e.printStackTrace();
      			return new EXEException("","数据库查询错误!",null);
      		}
      		catch (Exception e) {
      			e.printStackTrace();
      			return new EXEException("","后台错误!",null);
      		}
           uNum++;
       }
       
       ArrayList resultList=new ArrayList();
       resultList.add(iNum);
       resultList.add(uNum);
       return resultList;
	}
	/*
	 * 
	 */
	public Object insertState(ASObject obj){
		ASObject resault=new ASObject();
		 
		String state_name=(String)obj.get("state_name");
		String start_time=(String)obj.get("start_time");
		String end_time=(String)obj.get("end_time");
		try {
			String val = getStateNextVal();
			String querySql="select * from dic_state_tab  where state_name='"+state_name+"'";
			GDSet gd=DbComponent.Query(querySql);
			if(gd.getRowCount()>0){
				return resault;
			}
			String sql= "insert into dic_state_tab(state, state_name,start_time,end_time) " +
			 "values('"+val+"','"+state_name+"',to_date('"+start_time+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+end_time+"','yyyy-mm-dd hh24:mi:ss'))";
			DbComponent.exeUpdate(sql);
			DicStateBean DicStateBean = new DicStateBean();
			DicStateBean.setState(val);
			DicStateBean.setState_name(state_name);
			DicStateBean.setStart_time(start_time);
			DicStateBean.setEnd_time(end_time);
			TableInfoCache as =new TableInfoCache();
			as.refreshTableMap("dic_state_tab",DicStateBean,DicStateBean.getState(),"1");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","数据库查询错误!",null);
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
		resault.put("success", "success");
		return resault;
	}
	public Object updateState(ASObject obj){
		ASObject resault=new ASObject();
		String state=(String)obj.get("state");
		String state_name=(String)obj.get("state_name");
		String start_time=(String)obj.get("start_time");
		String end_time=(String)obj.get("end_time");
		try {
			String querySql ="select * from dic_state_tab t where t.state_name='"+state_name+"' and t.state !=  "+state;
			GDSet gd=DbComponent.Query(querySql);
			if(gd.getRowCount()>0){
				resault.put("success", "");
				return resault;
			}
			String sql=" update dic_state_tab set state_name = '"+state_name+"' ,start_time = to_date('"+start_time+"','yyyy-mm-dd hh24:mi:ss'),end_time=to_date('"+end_time+"','yyyy-mm-dd hh24:mi:ss') where state="+state;
			DbComponent.exeUpdate(sql);
			DicStateBean stateBean=new DicStateBean();
			stateBean.setState(state);
			stateBean.setState_name(state_name);
			stateBean.setStart_time(start_time);
			stateBean.setEnd_time(end_time);
			TableInfoCache as =new TableInfoCache();
			as.refreshTableMap("dic_state_tab",stateBean,stateBean.getState(),"2");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
		resault.put("success", "success");
		return resault;		
	}
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:查询大洲数据 ，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	
    public Object getState(ASObject obj){
    	return new DicService().getState(obj);
    }
    /**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:删除大洲数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
    public Object deleteState(ASObject asObj){
    	DicDao asDao = new DicDao();
		String dellist=(String)asObj.get("dellist");
		try{
			asDao.deleteState(dellist);
			DicStateBean DicStateBean = new DicStateBean();
			String[] delArr = dellist.split(",");
			TableInfoCache as =new TableInfoCache();
			 for(int i=0;i<delArr.length;i++){
				 if(delArr[i].length()>3){
					 DicStateBean.setState(delArr[i].split("'")[1]);
					 
					 as.refreshTableMap("dic_state_tab",DicStateBean,DicStateBean.getState(),"3"); 
				 }
			 }
      
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("","后台错误!",null);
		}
		 return " ";
	}
    
    /**
	 * 
	 * <p>class/function:com.viewscenes.database.service.usermanager
	 * <p>explain:取得添加大洲的序列
	 * <p>author-date:谭长伟 2012-3-3
	 * @param:
	 * @return:
	 */
	private static String getStateNextVal() throws DbException, GDSetException{
		
		String sql = " select SEC_SEQ.nextval val from dual ";
		
		
		GDSet set = DbComponent.Query(sql);
		
		String val = set.getString(0, "val");
		
		return val;
	}
}
