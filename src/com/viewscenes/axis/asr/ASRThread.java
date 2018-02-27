package com.viewscenes.axis.asr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import com.viewscenes.bean.ASRCmdBean;
import com.viewscenes.bean.ASRResBean;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.online.recplay.MonitorRecPlayService;

import flex.messaging.io.amf.ASObject;

public class ASRThread extends Observable implements Runnable{

	private ArrayList<FileRetrieveResult> list;
	
	public ASRThread(ArrayList<FileRetrieveResult> list){
		this.list = list;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		executeTask();
	}

	
	
	private void executeTask(){
		if (list !=null){
			for(int i=0;i<list.size();i++){
				
				ASRClient client  = new ASRClient();
				FileRetrieveResult rsrb = list.get(i);
				ASRCmdBean bean = new ASRCmdBean();
				
				if (!rsrb.getTaskId().equals("")){
					
					RunplanBean runBean = null;
					try {
						runBean = MonitorRecPlayService.getRunplanByTaskId(rsrb.getTaskId());
						if (runBean != null)
							bean.setCollectChannel(runBean.getSatellite_channel());
					} catch (GDSetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				ASObject headobj = new ASObject();
				headobj.put("headCode", rsrb.getHeadCode());
				ArrayList<ResHeadendBean> headList = (ArrayList<ResHeadendBean>) Common.getHeadendList(headobj);
				if (headList.size() > 0 ){
					ResHeadendBean headendBean = headList.get(0);
					//采集方式（录音方式：遥控站实时1、遥控站回传2、采集点实时3、采集点回传4）
					bean.setCollectMethod(headendBean.getType_id().equalsIgnoreCase("101")?"4":"2");
					
//					if(rsrb.getReveiceType().indexOf("NI")!=-1)
//					{
//						bean.setReceiverType("NI-1000");
//					}else if(rsrb.getReveiceType().indexOf("252A")!=-1)
//					{
//						bean.setReceiverType("BXM-252A");
//					}else if(rsrb.getReveiceType().indexOf("SDR")!=-1)
//					{
//						bean.setReceiverType("SF-RM");
//					}else 
//					{
//						bean.setReceiverType("NRD545");
//					}
					bean.setReceiverType(rsrb.getReveiceType());
					
				}
				bean.setFile(rsrb.getUrl());
				bean.setFileEndTime(rsrb.getEndDatetime());
				bean.setFileStartTime(rsrb.getStartDatetime());
				bean.setTaskEndTime(rsrb.getEndDatetime());
				bean.setTaskStartTime(rsrb.getStartDatetime());
				bean.setFreq(rsrb.getFreq());
				bean.setLanguage(rsrb.getLanguage());
				
				
				//发送到语音识别接口
				ASRResBean asrResBean = null;
				try {
					asrResBean = client.exucuteTask(bean);
					if (!asrResBean.getTaskStatus().equals("0000"))
						//将识别结果入库
						client.readyAsrResult2DB(rsrb, asrResBean);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
					
	//			Map markResultMap = new HashMap();
	//			markResultMap.put("rec", rsrb);
	//			markResultMap.put("mark", asrResBean);
	//			super.setChanged();
	//			notifyObservers(markResultMap);
				
				
			}
		}
	}
}
