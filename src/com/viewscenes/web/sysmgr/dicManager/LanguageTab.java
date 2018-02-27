package com.viewscenes.web.sysmgr.dicManager;

import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.web.sysmgr.dicManager.DicDao;
import com.viewscenes.web.sysmgr.dicManager.DicService;

import org.jdom.Element;
import org.jmask.web.controller.EXEException;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.util.report.printexcel.JExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flex.messaging.io.amf.ASObject;
import com.viewscenes.bean.ZdicLanguageBean;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.report.printexcel.JExcel;
import com.viewscenes.util.report.printtxt.ReadWriteFile;
import com.viewscenes.util.report.printtxt.Txt;

public class LanguageTab {

	
	/**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:查询语言数据 ，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
	
    public Object getLanguage(ZdicLanguageBean bean){

    	return new DicService().getLanguage(bean);
    }
    
    
    /**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:提交更新语言数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
     * @throws GDSetException 
     * @throws DbException 
	 */

	 
	public Object modifyLanguage(ASObject asObj) throws ParseException, DbException, GDSetException{
		
		
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
    	     String language_id = (String)resultStat.get("language_id");
             String language_name = (String)resultStat.get("language_name");
             String val = getLanguageNextVal();
                 insertSql = "insert into zdic_language_tab(language_id, language_name) " +
            				 "values('"+val+"','"+language_name+"')";
          
         		
            System.out.println("insertSql="+insertSql);
            try{
         	 DbComponent.exeUpdate(insertSql);
         	ZdicLanguageBean ZdicLanguageBean = new ZdicLanguageBean();
         	ZdicLanguageBean.setLanguage_id(val);
         	ZdicLanguageBean.setLanguage_name(language_name);
  		  
  		     TableInfoCache as =new TableInfoCache();
  		     as.refreshTableMap("zdic_language_tab",ZdicLanguageBean,ZdicLanguageBean.getLanguage_id(),"1");
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
    	   String language_id = (String)resultStat.get("language_id");
  	       String language_name = (String)resultStat.get("language_name");
        
        
           
           updateSql = " update  zdic_language_tab set Language_name='"+language_name+"' where Language_id='"+language_id+"'";

           
           try{
           	 DbComponent.exeUpdate(updateSql);
           	 
           	ZdicLanguageBean ZdicLanguageBean = new ZdicLanguageBean();
         	ZdicLanguageBean.setLanguage_id(language_id);
         	ZdicLanguageBean.setLanguage_name(language_name);
  		  
  		     TableInfoCache as =new TableInfoCache();
  		     as.refreshTableMap("zdic_language_tab",ZdicLanguageBean,ZdicLanguageBean.getLanguage_id(),"2");
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
	
	
	 /**
	 * 
	 * <p>class/function:com.viewscenes.web.sysmgr.dicManager
	 * <p>explain:删除语言数据，如果添加出错会返回错误信息
	 * <p>author-date:张文 2012-7-26
	 * @param:
	 * @return:
	 */
    public Object deleteLanguage(ASObject asObj){
    	DicDao asDao = new DicDao();
		String dellist=(String)asObj.get("dellist");
		
		try{
			System.out.println("dellist="+dellist);
			asDao.deleteLanguage(dellist);
			ZdicLanguageBean ZdicLanguageBean = new ZdicLanguageBean();
			 TableInfoCache as =new TableInfoCache();
			String[] delArr = dellist.split(",");
			 for(int i=0;i<delArr.length;i++){
				if(delArr[i].length()>3){
					 ZdicLanguageBean.setLanguage_id(delArr[i].split("'")[1]);
					 System.out.println("aaa="+delArr[i].split("'")[1]);
					 as.refreshTableMap("zdic_language_tab",ZdicLanguageBean,ZdicLanguageBean.getLanguage_name(),"3"); 
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
	private static String getLanguageNextVal() throws DbException, GDSetException{
		
		String sql = " select SEC_SEQ.nextval val from dual ";
		
		
		GDSet set = DbComponent.Query(sql);
		
		String val = set.getString(0, "val");
		
		return val;
	}
	
	
	/**
	 * <p>class/function:com.viewscenes.framework.service.report.am
	 * <p>explain:导出语言报表(处理非模板Excel的情况)
	 * <p>author-date:常海涛 Feb 28, 2012
	 * @param:
	 * @return:
	 */
	public void getLanguageExcel(String msg, HttpServletRequest request,
			HttpServletResponse response){
		
		Element root = StringTool.getXMLRoot(msg);
		ZdicLanguageBean conditonbean = new ZdicLanguageBean();//定义条件bean
//		conditonbean.setHeadCode(root.getChildText("headCode"));
//		conditonbean.setChannel(root.getChildText("channel"));
//		conditonbean.setAlarmFatherType(root.getChildText("alarmFatherType"));
//		conditonbean.setAlarmSonType(root.getChildText("alarmSonType"));
//		conditonbean.setIsReview(root.getChildText("isReview"));
//		conditonbean.setStartTime(root.getChildText("startTime"));
//		conditonbean.setEndTime(root.getChildText("endTime"));
//		conditonbean.setAbnormal_time_type(root.getChildText("abnormal_time_type"));
//		conditonbean.setAbnormal_time(root.getChildText("abnormal_time"));
		
		
	    String fileName= "语言表";
	    
        try {
			
			  JExcel jExcel = new JExcel();
	          String downFileName=jExcel.openDocument();
	          jExcel.addData(0, 0, fileName, jExcel.repTitleFormat); //添加title  这个测试完后需要改
	          jExcel.getWorkSheet().setRowView(0, 1000);
	          jExcel.mergeCells(0,0,8,1);// 合并第一列第一行到第8列第一行的所有单元格	
			  jExcel.WorkBookGetSheet(0);
			  
			  int topCount = 2;
			  int column=0;
			
			    jExcel.addData(column++, topCount,"ID",jExcel.dateTITLEFormat);
				jExcel.addData(column++, topCount,"语言名称",jExcel.dateTITLEFormat);
			
				jExcel.getWorkSheet().setRowView(topCount, 500);
				
				jExcel.getWorkSheet().setColumnView(0, 20);
				jExcel.getWorkSheet().setColumnView(1, 20);
	
		
			
			ArrayList<ZdicLanguageBean> languageList = new ArrayList<ZdicLanguageBean>();
			HashMap obj=(HashMap)new DicService().getLanguage(conditonbean);
			if(obj.get("resultList") instanceof ArrayList){//如果返回正常的结果集
			  languageList=(ArrayList)obj.get("resultList");
			
			 for (int i = 0; i < languageList.size(); i++) {
				    ZdicLanguageBean resultbean = (ZdicLanguageBean)languageList.get(i);  //定义结果bean 并加入到结果list中
	            	jExcel.addData(0, i+topCount+1,resultbean.getLanguage_id(),jExcel.dateCellFormat);
	            	jExcel.addData(1, i+topCount+1,resultbean.getLanguage_name(),jExcel.dateCellFormat);
	            
	            	jExcel.getWorkSheet().setRowView(i+topCount+1, 500);	            	
             }
	    }
			jExcel.saveDocument();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Location", "Export.xls");
			response.setHeader("Expires", "0");
			response.setHeader("Content-Disposition", "filename="
					+ java.net.URLEncoder.encode(fileName, "UTF-8") + ".xls");
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(downFileName);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			outputStream = null;
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * <p>class/function:com.viewscenes.framework.service.report.am
	 * <p>explain:导出语言报表(处理非模板Excel的情况)
	 * <p>author-date:常海涛 Feb 28, 2012
	 * @param:
	 * @return:
	 * @throws UtilException 
	 */
//	public void getLanguageTxt(String msg, HttpServletRequest request,
//			HttpServletResponse response){
//		
//		String settabname="zdic_language_tab";
//		String setcolname ="";
//		String setvalue ="";
//		String sql="";
//		ZdicLanguageBean conditonbean = new ZdicLanguageBean();//定义条件bean
//		conditonbean.setStartRow(1);
//		conditonbean.setEndRow(400);
//		ArrayList<ASObject> languageList = new ArrayList<ASObject>();
//		HashMap obj=(HashMap)new DicService().getLanguage(conditonbean);
//		if(obj.get("resultList") instanceof ArrayList){//如果返回正常的结果集
//		  languageList=(ArrayList)obj.get("resultList");
//		
//			 for (int i = 0; i < languageList.size(); i++) {
//				 ASObject obj1= (ASObject)languageList.get(i);
//				 Iterator it = obj1.entrySet().iterator();
//				 String sKey="";
//				 String sValue="";
//				 setcolname ="";
//				 setvalue ="";
//				 while (it.hasNext()) {
//					  Map.Entry entry = (Map.Entry) it.next();
//					   sKey = (String) entry.getKey();
//					   sValue = (String) entry.getValue();
//					   if(sKey.equals("ora_rc")){
//						  
//					   }else{
//							 setcolname += sKey+',';
//							 setvalue += sValue+','; 
//						
//					   }
//				 }
//				 
//				 
//				 if(setcolname.equals("")){
//					 
//				 }else{
//					
//					setcolname = setcolname.substring(0,setcolname.length()-1);
//					setvalue = setvalue.substring(0,setvalue.length()-1);
//				   sql +="insert into "+settabname+"("+setcolname+") values("+setvalue+")"+ "\r\n";
//				 }
//          }
//			
//			 
//	    }
//    	 try {
//			ReadWriteFile.writeTxtFile(sql,"d:/test.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}
	
	
	public void getLanguageTxt(String msg, HttpServletRequest request,
			HttpServletResponse response) throws UtilException{
		String sql = "select * from zdic_language_tab where is_delete =0  order by language_name desc ";
		String tab = "zdic_language_tab";
		Txt Txtt = new Txt();
		 try {
			Txtt.getTxt(sql,tab,"语言");
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
}
