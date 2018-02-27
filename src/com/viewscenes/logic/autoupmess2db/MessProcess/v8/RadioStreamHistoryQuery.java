package com.viewscenes.logic.autoupmess2db.MessProcess.v8;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.viewscenes.axis.asr.ASRClient;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.logic.autoupmess2db.Exception.NoRecordException;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.IUpMsgProcessor;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.util.business.SiteVersionUtil;
/**
 * �㲥¼����ʷ��ѯ���������ϱ��ӿ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RadioStreamHistoryQuery implements IUpMsgProcessor {
	private String head_id = "";
	private String srcCode = "";
	private String sitetype = "";
  public RadioStreamHistoryQuery() {
  }

  public void processUpMsg(Element root) throws SQLException,
	UpMess2DBException, GDSetException, DbException, UtilException,
	NoRecordException {
	  try{
//	    java.util.Date start = Calendar.getInstance().getTime();
	    this.head_id = SiteVersionUtil.getSiteHeadId(root.getAttributeValue("SrcCode"));
	    this.srcCode = root.getAttributeValue("SrcCode");//ǰ��վ��code
	    this.sitetype = SiteVersionUtil.getSiteType(srcCode);
	    ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();
	    LogTool.debug("autoupRecord","RadioStreamHistoryQuery========================");
	    list = getDataFromRootV8(root);
	    ArrayList<FileRetrieveResult> listSucc = data2DbUpload(list);
	    LogTool.debug("autoupRecord","RadioStreamHistoryQuery========================insert over listSucc:"+listSucc.size());
	    //������ʶ��ӿڷ���¼���ļ���Ϣ���ڴ��
//	    LogTool.debug("autoupRecord","��ʼ������ʶ��ӿڷ���¼���ļ�");
//	    ASRClient client = new ASRClient();
//	    client.exucuteTask(listSucc);
//	    LogTool.debug("autoupRecord","������ʶ��ӿڷ���¼���ļ�����");
	    
	    
//	    java.util.Date end = Calendar.getInstance().getTime();
//	    long period = end.getTime() - start.getTime();
		  } catch(Exception e){
			  LogTool.fatal("autoupRecord",e);
	  }
//    LogTool.info("autoupRecord","¼����ʷ��ѯ�ϱ����ʱ�䣺" + period);
  }

  /**
   * �ֶ�¼��ʱʹ�ã�һ��ֻ��һ�����ݣ�
   * @detail  
   * @method  
   * @param list
   * @param srcCode
   * @return
   * @throws Exception 
   * @return  ArrayList<FileRetrieveResult>  
   * @author  zhaoyahui
   * @version 2012-8-29 ����11:12:20
   */
  public static ArrayList<FileRetrieveResult> data2Db(ArrayList<FileRetrieveResult> list,String... srcCode) throws UpMess2DBException {
	  ArrayList<FileRetrieveResult> resList = RadioStreamHistoryQuery.data2DbUpload(list);
	  if(resList.size() == 0){
		  throw new UpMess2DBException("¼���ļ�����쳣!");
	  }
	  return resList;
  }
  /**
   * �����ϱ�¼���ļ�ʹ��
   * (radio_stream_result_tab)
   * ����preparedStatement��ָ���������
   * @throws UpMess2DBException
   */
  public static ArrayList<FileRetrieveResult> data2DbUpload(ArrayList<FileRetrieveResult> list,String... srcCode) throws UpMess2DBException {
	  ArrayList<FileRetrieveResult> listsuccess = new ArrayList<FileRetrieveResult>();
    if(list!=null&&list.size()>0){
      String sql = "insert into radio_stream_result_tab(result_id,equ_code,task_id,band,frequency " +
      " ,offset,start_datetime,end_datetime,url,filesize,am_Modulation,filename,mark_file_name,fm_Modulation" +
      " ,is_stored, is_delete,head_id,store_datetime,level_value,report_type,language,station_name,receive_type,runplan_id) " +
      "values(RADIO_DATA_RECOVERY_SEQ.nextval,?,?,?,?,  ?,?,?,?,  ?,?,?,? ,?,'0','0',?,sysdate,?,?,?,?,?,?)";
      DbComponent db = new DbComponent();
      DbComponent.DbQuickExeSQL prepExeSQL = db.new DbQuickExeSQL(sql);
      prepExeSQL.getConnect();
      try {
 
    	String mainUrl = SystemConfig.getLocVideoUrl();
    	
    	if(mainUrl.lastIndexOf("/")!=(mainUrl.length()-1)){
    		mainUrl += "/";
    	}
        Iterator iter = list.iterator();
        
//        HashMap<String,String> trMap = new HashMap<String,String>();
//        for(int i=0;i<gd.getRowCount();i++){
//        	String taskid = gd.getString(i, "task_id");
//        	String runplanid = gd.getString(i, "runplan_id");
//        	if(runplanid == null){
//        		runplanid = "";
//        	}
//        	trMap.put(taskid, runplanid);
//        }
        while (iter.hasNext()) {
        	FileRetrieveResult bean = (FileRetrieveResult) iter.next();
        	String runplan_id = "";
            String equcode = bean.getEquCode();
            String task_id = bean.getTaskId();
            String band = bean.getBand();
            String freq = bean.getFreq();
            String filename = bean.getFileName();
            String headid = bean.getHead_id();
            String report_type = bean.getReport_type();
            String receive_type = bean.getReveiceType();
            if (report_type != null){
            	if (report_type.equals("")){
            		report_type = "3";
            	}else if (report_type.equals("����")){
            		report_type = "0";
            	}else if (report_type.equals("Ч��")){
            		report_type = "1";
            	}else if (report_type.equals("��ʱ")){
            		report_type = "3";
            	}else if (report_type.equals("ʵʱ")){
            		report_type = "2";
            	}
            }
            if(task_id==null||task_id.trim().length()==0)
              {
                  task_id="";
              } else{
            	  String runSql = "select task.runplan_id runplan_id from radio_unify_task_tab task "+
	      			" where task.task_id='"+task_id+"'  ";
	              GDSet gd = DbComponent.Query(runSql);
	              if(gd.getRowCount()>0 && gd.getString(0, "runplan_id") != null){
	            	  runplan_id = gd.getString(0, "runplan_id");
	              }
              }
            String srccode = "";
            if(srcCode.length>0){
            	srccode = srcCode[0];
            }
            
            filename = filename.endsWith(".zip") ?filename.replaceAll(".zip", ".mp3"):filename;
            bean.setFileName(filename);
            
            checkData(equcode,task_id,"headid",headid,srccode);
            checkData(equcode,task_id,"equcode",equcode,srccode);
            //checkData(equcode,task_id,"task_id",task_id);
            checkData(equcode,task_id,"band",band,srccode);
            checkData(equcode,task_id,"freq",freq,srccode);
            checkData(equcode,task_id,"filename",filename,srccode);
            checkData(equcode,task_id,"report_type",report_type,srccode);


            String start_datetime = bean.getStartDatetime();
            String end_datetime = bean.getEndDatetime();
            
            if (start_datetime.indexOf("-") == -1 || start_datetime.indexOf(":") == -1)
            	bean.setStartDatetime(StringTool.stringToStringDate(start_datetime));
            if (end_datetime.indexOf("-") == -1 || end_datetime.indexOf(":") == -1)
            	bean.setEndDatetime(StringTool.stringToStringDate(end_datetime));
           
            String url = bean.getFileUrl();
            String size = bean.getFilesize();
            String markFileName = url.substring(url.lastIndexOf("/") + 1);
            markFileName = markFileName.endsWith(".zip")?markFileName.replaceAll(".zip", ".mp3"):markFileName;
            if(url.indexOf("/upload/")!=-1)
            {
            	url = mainUrl + url.substring(url.indexOf("/upload/")+8);
            }
            
            url = url.endsWith(".zip") ? url.replaceAll(".zip", ".mp3"):url;
           
            bean.setUrl(url);
            checkData(equcode,task_id,"StartDateTime",start_datetime,srccode);
            checkData(equcode,task_id,"EndDateTime",end_datetime,srccode);
            checkData(equcode,task_id,"Url",url,srccode);


          prepExeSQL.setString(1, equcode);
          prepExeSQL.setString(2, task_id);
          prepExeSQL.setString(3, band);
          prepExeSQL.setString(4, freq);

          prepExeSQL.setString(5, bean.getOffset());
          prepExeSQL.setString(6, start_datetime);
          prepExeSQL.setString(7, end_datetime);
          prepExeSQL.setString(8, url);

          prepExeSQL.setString(9, size);
          prepExeSQL.setString(10, bean.getAmModulation());
          prepExeSQL.setString(11, filename);
          prepExeSQL.setString(12, markFileName);
          prepExeSQL.setString(13, bean.getFmModulation());
          prepExeSQL.setString(14, headid);
          prepExeSQL.setString(15, bean.getLevel());
          prepExeSQL.setString(16, report_type);
          //���������ԣ�����̨ 2012-09-24
          prepExeSQL.setString(17, bean.getLanguage());
          prepExeSQL.setString(18, bean.getStationName());
          prepExeSQL.setString(19, receive_type);
          prepExeSQL.setString(20, runplan_id);
          
          try{
        	  prepExeSQL.exeSQL();
        	  listsuccess.add(bean);
          }catch(Exception ex2){
        	  ex2.printStackTrace();
              LogTool.fatal("autoupRecord","�����ϱ��쳣1��"+ex2);
          }
        }
      }
      catch (Exception ex1) {
    	  ex1.printStackTrace();
        LogTool.fatal("autoupRecord","�����ϱ��쳣2��"+ex1);
      }
      finally {
          prepExeSQL.closeConnect();
      }
     
    }
    return listsuccess;

  }

  /**
   * getDataFromRootV8
   * ��root�е����ݽ�����ArrayList��(v8��)
   * @param root
   * @return ����FileRetrieveResult�ļ���
   */
  private ArrayList<FileRetrieveResult> getDataFromRootV8(Element root) {
	  ArrayList<FileRetrieveResult> list = new ArrayList<FileRetrieveResult>();

	  // ����й�����
      Element ele = null;
      ele = root.getChild("fileretriever");
//      String equ_code = ele.getAttributeValue("equcode");
//      String task_id = ele.getAttributeValue("taskid");

      List list_file = ele.getChildren(); // ���FileԪ���б�
      for (int i = 0; i < list_file.size(); i++) {
    	  Element fileEle = (Element) list_file.get(i);
          FileRetrieveResult bean = new FileRetrieveResult();
    	  bean.setMap(fileEle);
          bean.setHead_id(this.head_id);
    	  list.add(bean);
      }
	  return list;
  }

  /**
   * ������������� ȱ���׳��쳣
   * @param equcode
   * @param task_id
   * @param data ������
   * @param value ����ֵ
   * @throws UpMess2DBException
   */
  public static void checkData(String equcode,String task_id,String data,String value,String srcCode) throws UpMess2DBException{
      if(value==null||value.equals("")){
          throw new UpMess2DBException("srcCode="+srcCode+";equcode=" + equcode + ";task_id="+task_id+"¼����ʷ��ѯ�ϱ�����ȱ�ٱ�Ҫ����:"+data+"="+value);
      }
  }
}

