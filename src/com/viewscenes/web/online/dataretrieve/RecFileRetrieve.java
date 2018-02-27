package com.viewscenes.web.online.dataretrieve;

import com.viewscenes.axis.asr.ASRClient;
import com.viewscenes.bean.device.FileQueryResult;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.bean.task.Task;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.device.api.FileAPI;
import com.viewscenes.device.exception.*;
import com.viewscenes.device.radio.MsgFileQueryCmd;
import com.viewscenes.device.radio.MsgFileQueryRes;
import com.viewscenes.device.radio.MsgFileRetrieveRes;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioStreamHistoryQuery;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.Security;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.online.recplay.MonitorRecPlayService;
import flex.messaging.io.amf.ASObject;
import org.jmask.web.controller.EXEException;

import java.util.ArrayList;

/**
 * 录音文件回收和重新上报
 *
 */
public class RecFileRetrieve {


    /**
     * 录音文件重新上报
     * <p>class/function:com.viewscenes.web.online.dataretrieve
     * <p>explain:
     * @param:
     * @return:
     */
    public Object streamFileReReport(ASObject obj){
        ArrayList list = new ArrayList();
        String userId = (String)obj.get("userId");
        String headCode = (String)obj.get("headCode");
        String startTime = (String)obj.get("startDatetime");
        String endTime = (String)obj.get("endDatetime");
        String priority = "";

        try {

            Security security = new Security();
            Long msgPrio=1L;
            try {
                msgPrio = security.getMessagePriority(userId, 2, 0, 0);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            priority = new Long(msgPrio).toString();
            FileAPI.msgStreamRecoveryReportCmdCmd(headCode, startTime, endTime, priority);
        } catch (DeviceNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new EXEException("", "[站点:" + headCode
                    + "]监测录音文件重新回传命令下发失败|" + e.getMessage(),"");
        } catch (DeviceFilterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new EXEException("", "[站点:" + headCode
                    + "]监测录音文件重新回传命令下发失败|" + e.getMessage(),"");
        } catch (DeviceProcessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DeviceTimedOutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new EXEException("", "[站点:" + headCode
                    + "]监测录音文件重新回传命令下发失败|" + e.getMessage(),"");
        } catch (DeviceReportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new EXEException("", "[站点:" + headCode
                    + "]监测录音文件重新回传命令下发失败|" + e.getMessage(),"");
        }
        return "监测录音文件重新回传命令下发成功！";
    }
	/**
	 * 录音文件查询
	 * <p>class/function:com.viewscenes.web.online.dataretrieve
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-6
	 * @param:
	 * @return:
	 */
	public Object msgFileQueryCmd(ASObject obj){
		ArrayList list = new ArrayList();
		String userId = (String)obj.get("userId");
		String headCode = (String)obj.get("headCode");
		String resultType = (String)obj.get("resultType");
		String startTime = (String)obj.get("startDatetime");
		String endTime = (String)obj.get("endDatetime");
		String requestTaskId = (String)obj.get("requestTaskId");
		String freq = (String)obj.get("freq");
		String band = (String)obj.get("band");
		String priority = (String)obj.get("priority");
		
		try {
			
			Security security = new Security();
			Long msgPrio=1L;
			try {
		        msgPrio = security.getMessagePriority(userId, 2, 0, 0);
		    } catch (Exception ex2) {
		           ex2.printStackTrace();
		    }
			priority = new Long(msgPrio).toString();
			
			ArrayList<MsgFileQueryCmd.Frequency> tmpList = new ArrayList<MsgFileQueryCmd.Frequency>();
			
		            
            MsgFileQueryCmd.Frequency freqBean = new MsgFileQueryCmd.Frequency(band, freq);
            tmpList.add(freqBean);
			
			list = (ArrayList)FileAPI.msgFileQueryCmd(headCode, resultType, startTime, endTime, requestTaskId, tmpList, priority);
			
			if (list != null){
				ArrayList newList = new ArrayList();
				for(int i=0;i<list.size();i++){
					MsgFileQueryRes.ResultInfo s = (MsgFileQueryRes.ResultInfo)list.get(i);
					FileQueryResult file = new FileQueryResult();
					file.setBand(s.getBand());
					file.setEndDateTime(s.getEndDateTime());
					file.setFileURL(s.getFileURL());
					file.setFreq(s.getFreq());
					file.setResultType(s.getResultType());
					file.setSize(s.getSize());
					file.setStartDateTime(s.getStartDateTime());
					file.setTaskID(s.getTaskID());
					newList.add(file);
				}
				list = newList;
			}
		} catch (DeviceNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件查询失败|" + e.getMessage(),"");
		} catch (DeviceFilterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件查询失败|" + e.getMessage(),"");
		} catch (DeviceProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeviceTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件查询失败|" + e.getMessage(),"");
		} catch (DeviceReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件查询失败|" + e.getMessage(),"");
		}
		
		if (list == null)
			list = new ArrayList();
		ASObject resultObj = new ASObject();
		
		resultObj.put("resultList", list);
		resultObj.put("resultTotal", list.size());
		return resultObj;
	}
	
	/**
	 * 录音文件回收，入库
	 * <p>class/function:com.viewscenes.web.online.dataretrieve
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-6
	 * @param:
	 * @return:
	 */
	public Object msgFileRetrieveCmd(ASObject obj){
		ArrayList list = new ArrayList();
		String userId = (String)obj.get("userId");
		String headCode = (String)obj.get("headCode");
		ArrayList _fileList = (ArrayList)obj.get("fileList");
		String priority = (String)obj.get("priority");
		
		try {
			ArrayList<String> fileNames = new ArrayList<String>(_fileList.size());
			for(int i=0;i<_fileList.size();i++){
				FileQueryResult result = (FileQueryResult)_fileList.get(i);
				fileNames.add(result.getFileURL());
			}
			
			Security security = new Security();
			Long msgPrio=1L;
			try {
		        msgPrio = security.getMessagePriority(userId, 2, 0, 0);
		    } catch (Exception ex2) {
		           ex2.printStackTrace();
		    }
			priority = new Long(msgPrio).toString();
			
			ArrayList<MsgFileRetrieveRes.FileRetrieve> fileList = (ArrayList<MsgFileRetrieveRes.FileRetrieve>)FileAPI.msgFileRetrieveCmd(headCode, fileNames, priority);
			
			if (fileList != null){
				ArrayList<FileRetrieveResult> newList = new ArrayList<FileRetrieveResult>();
				for(int i=0;i<fileList.size();i++){
					FileRetrieveResult fileRet = new FileRetrieveResult();
					MsgFileRetrieveRes.FileRetrieve result = fileList.get(i);
					String taskId = result.getTaskId();
					
					Task task =MonitorRecPlayService.getTaskById(taskId);
					if (task != null){
						String runplan_id = task.getRunplan_id();
						if (!runplan_id.equals("")){
							RunplanBean runplanBean = MonitorRecPlayService.getRunplanById(runplan_id);
							fileRet.setRunplanBean(runplanBean);
							fileRet.setRunplan_id(runplanBean.getRunplan_id());
						}
						fileRet.setTask(task);
					}
					
					
					
					
					fileRet.setFileName(result.getFileName());
					fileRet.setHeadCode(result.getHeadCode());
					fileRet.setTaskId(taskId);
					fileRet.setStartDatetime(StringTool.convertString2DateStr(result.getStartDatetime()));
					fileRet.setEndDatetime(StringTool.convertString2DateStr(result.getEndDatetime()));
					fileRet.setFreq(result.getFreq());
					fileRet.setEquCode(result.getEquCode());
					fileRet.setBand(result.getBand());
					fileRet.setLanguage(result.getLanguage());
					
					fileRet.setFileUrl(result.getFileUrl());// ftp url
					fileRet.setAmModulation(result.getAmModulation());
					fileRet.setFmModulation(result.getFmModulation());
					fileRet.setLevel(result.getLevel());
					fileRet.setOffset(result.getOffset());
					
					fileRet.setHead_id(Common.getHeadendBeanByCode(result.getHeadCode()).getHead_id());
//					String url = result.getFileUrl();
//					url = url.replaceAll("ftp:([\\S\\s]+?)upload/", SystemConfig.getVideoUrl());
					fileRet.setUrl(result.getFileUrl());;//Media url
					//录音类型为空时，默认为临时录音
					fileRet.setReport_type(result.getRecType().equals("")?"3":result.getRecType());
					fileRet.setIs_stored("0");
					fileRet.setMark_file_name(result.getFileName());
	//				RunplanBean runBean = MonitorRecPlayService.getRunplanByTaskId(result.getTaskId());
	//				if (runBean != null){
	//					
	//				}
					
					newList.add(fileRet);
				}
				
				list = newList;
				
				ArrayList<FileRetrieveResult> resultList = new ArrayList<FileRetrieveResult>();
				//返回入库成功的文件列表
				resultList = RadioStreamHistoryQuery.data2DbUpload(list);
				//发送给语音识别
				ASRClient client = new ASRClient();
				client.exucuteTask(resultList);
			}
		} catch (DeviceNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (DeviceFilterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (DeviceProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (DeviceTimedOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (DeviceReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		} catch (UpMess2DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new EXEException("", "[站点:" + headCode
					+ "]监测录音文件获取失败|" + e.getMessage(),"");
		}
		
		ASObject resultObj = new ASObject();
		
		if (list == null)
			list = new ArrayList();
		
		resultObj.put("resultList", list);
		resultObj.put("resultTotal", list.size());
		return resultObj;
	}
	
	
}
