package com.viewscenes.web.sysmgr.recordRules;

import java.text.ParseException;
import java.util.ArrayList;

import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.sysmgr.dicManager.DicDao;
import com.viewscenes.web.sysmgr.dicManager.DicService;

import flex.messaging.io.amf.ASObject;

public class RecordRules {

	
	  public Object getRules(ASObject obj){
	    	return getRulesend(obj);
	    }
	  
	  public Object getRulesend(ASObject obj){
		    ASObject resObj;
		    
		    String shortname = (String)obj.get("shortname");
//		    String site = (String)obj.get("site");
		    String code = (String)obj.get("code");

		    
		    
	        String sql = "select * from res_headend_monitor_rule_tab t where 1=1 ";
	
	        if(shortname.length()>0)
	        	sql += " and t.shortname  like('%"+shortname+"%')";
//	        
	        if(code.length()>0){
	        	sql += " and t.head_code like('%"+code.toUpperCase()+"%')";

	        }
//	        if(site.length()>0)
//	        	sql += " and r.site like('%"+site+"%')";
	            
	        
	        sql +=" order by t.head_code desc ";
	        try {
				resObj = StringTool.pageQuerySql(sql, obj);
			} catch (Exception e) {
				LogTool.fatal(e);
				return new EXEException("",e.getMessage(),"");
			}
	    	
	    	return resObj;
	}
	  
	  
	  public Object modifyRules(ASObject asObj) throws ParseException, DbException, GDSetException{
			
			
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
	    	     String head_code = (String)resultStat.get("head_code");
	    	     String shortname = (String)resultStat.get("shortname");
	    	     String id = (String)resultStat.get("id");
	             String effect_sleep_time = (String)resultStat.get("effect_sleep_time");
	             String effect_record_length = (String)resultStat.get("effect_record_length");
	             String effect_bps = (String)resultStat.get("effect_bps");
	             String quality_sleep_time = (String)resultStat.get("quality_sleep_time");
	             String quality_record_length = (String)resultStat.get("quality_record_length");
	             String quality_bps = (String)resultStat.get("quality_bps");
	             String val = getLanguageNextVal();
	   
	                 insertSql = "insert into res_headend_monitor_rule_tab(id,head_code,shortname, effect_sleep_time,effect_record_length,effect_bps," +
	                 		"quality_sleep_time,quality_record_length,quality_bps) " +
	            				 "values('"+val+"','"+head_code+"','"+shortname+"','"+effect_sleep_time+"','"+effect_record_length+"','"+effect_bps+"'," +
	            				 		"'"+quality_sleep_time+"','"+quality_record_length+"','"+quality_bps+"')";
	          
	         		
	            System.out.println("insertSql="+insertSql);
	            try{
	         	 DbComponent.exeUpdate(insertSql);
	         
	            }
	            catch (DbException e) {
	    			e.printStackTrace();
	    			if(e.getMessage().indexOf("违反唯一约束条件")>-1){
	    				return new EXEException("","站点规则已存在，请重新选择站点！",null);
	    				
	    			}
	    			return new EXEException("",e.getMessage(),null);
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    			return new EXEException("",e.getMessage(),null);
	    		}
	             iNum++;
	         }
	       
	       for(int i=0;i<updateIdList.size();i++){
	    	   
	    	   ASObject resultStat = (ASObject)updateIdList.get(i);
	    	     String head_code = (String)resultStat.get("head_code");
	    	     String shortname = (String)resultStat.get("shortname");
	    	     String id = (String)resultStat.get("id");
	             String effect_sleep_time = (String)resultStat.get("effect_sleep_time");
	             String effect_record_length = (String)resultStat.get("effect_record_length");
	             String effect_bps = (String)resultStat.get("effect_bps");
	             String quality_sleep_time = (String)resultStat.get("quality_sleep_time");
	             String quality_record_length = (String)resultStat.get("quality_record_length");
	             String quality_bps = (String)resultStat.get("quality_bps");
	           
	           updateSql = " update  res_headend_monitor_rule_tab set head_code='"+head_code+"',shortname='"+shortname+"',effect_sleep_time='"+effect_sleep_time+"'," +
	           		"effect_record_length='"+effect_record_length+"',effect_bps='"+effect_bps+"',quality_sleep_time='"+quality_sleep_time+"'," +
	           				"quality_record_length='"+quality_record_length+"',quality_bps='"+quality_bps+"' where id='"+id+"'";

	           
	           try{
	           	 DbComponent.exeUpdate(updateSql);
	           
	              }
	              catch (DbException e) {
	      			e.printStackTrace();
	      			return new EXEException("",e.getMessage(),null);
	      		}
	      		catch (Exception e) {
	      			e.printStackTrace();
	      			return new EXEException("",e.getMessage(),null);
	      		}
	           uNum++;
	       }
	       
	       ArrayList resultList=new ArrayList();
	       resultList.add(iNum);
	       resultList.add(uNum);
	       return resultList;
		}
	  
		private static String getLanguageNextVal() throws DbException, GDSetException{
			
			String sql = " select SEC_SEQ.nextval val from dual ";
			
			
			GDSet set = DbComponent.Query(sql);
			
			String val = set.getString(0, "val");
			
			return val;
		}
		
		 public Object deleteRules(ASObject asObj){
		    
				String dellist=(String)asObj.get("dellist");
				
				try{
					System.out.println("dellist="+dellist);
					deletelist(dellist);
					
				 }catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new EXEException("","后台错误!",null);
				}
				 return " ";
			}
		 
		 public boolean deletelist(String dellist) throws Exception {
				String sql ="";
				boolean df=false;
				
				sql = "delete from res_headend_monitor_rule_tab where id in("+dellist+")";
				
				df=DbComponent.exeUpdate(sql);
				return df;
			}
}
