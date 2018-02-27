package com.viewscenes.axis.asr;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import cn.com.pattek.pesb.client.PESBClient;

import com.viewscenes.bean.ASRCmdBean;
import com.viewscenes.bean.ASRResBean;
import com.viewscenes.bean.RadioMarkZstViewBean;
import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.LogTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.web.common.Common;
import com.viewscenes.web.online.onlineListen.MarkService;
import com.viewscenes.web.online.recplay.MonitorRecPlayService;

/**
 * �пƲ�ʱ ����ʶ��ӿڵ���
 * 
 * �ӿ�Ҫ��
 * jdk 1.6����
 * 
 * 
 * @author thinkpad
 * 
 */
public class ASRClient{
	public ASRClient() {

	}
	

//	public static Element getXMLRoot(String msg) {
//		Element root = null;
//		String ret = "";
//		// parse xml config file;
//		try {
//			StringReader read = new StringReader(msg);
//			org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(
//					false);
//			Document doc = builder.build(read);
//			root = doc.getRootElement();
//		} catch (JDOMException ex) {
//			ex.printStackTrace();
//		}
//		return root;
//	}

	
	/**
	 * ������¼���ļ���������ʶ��ϵͳ���д���
	 * @throws DbException 
	 * @throws GDSetException 
	 * 
	 */
	public  void exucuteTask(ArrayList<FileRetrieveResult> list) {
		//Ϊ����������������߳̽��д���
		ASRThread asrThread  = new ASRThread(list);
		Thread t = new Thread(asrThread);
		t.start();
	}
	
	/**
	 * ����ʶ��ӿ�
	 * @throws IOException 
	 */
	public   ASRResBean exucuteTask(ASRCmdBean asrCmdBean) throws IOException {
		ASRResBean asrResBean = new ASRResBean();
		String result = "";
		

		String xml = getCmdXml(asrCmdBean);

		String taskId = "";
		PESBClient pesbClient = new PESBClient();
		try {

//		    LogTool.info("asr","���͸�����ʶ��ϵͳXML:\n"+xml);
//		    String taskXml = pesbClient.exucuteTask(xml);
//		    if (taskXml.equals("-100")){
//		    	LogTool.info("asr","�޷����ӵ�����ʶ��ϵͳ,��������...\n"+xml);
//		    	asrResBean.setTaskStatus("0000");
//				asrResBean.setErrorMessage("�޷����ӵ�����ʶ��ϵͳ,��������...");
//		    	return asrResBean;
//		    }
//		    Element root = StringTool.getXMLRoot(taskXml);
//			taskId = root.getChild("task").getAttributeValue("id");
//			LogTool.info("asr","����ʶ��ϵͳ���صĴ�������ID="+taskId);
			 LogTool.info("asr","���͸�����ʶ��ϵͳXML:\n"+xml);
			 String taskXml = pesbClient.exucuteTask(xml);
		    LogTool.info("asr","����ʶ��ϵͳ������XML:\n"+taskXml);
			asrResBean = getResXml(taskXml);
		} catch (Exception e) {
			e.printStackTrace(); 
			asrResBean.setTaskStatus("0000");
			asrResBean.setErrorMessage("�޷����ӵ�����ʶ��ϵͳ,��������...");
			return asrResBean;
		}
		
		return asrResBean;

		

//		return asrResBean;
	}

	/**
	 *  ����ʶ��ӿڷ��Ͳ�������
	 * <p>class/function:com.viewscenes.axis
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-16
	 * @param:
	 * @return:
	 * @throws IOException 
	 */
	private   String getCmdXml(ASRCmdBean asrCmdBean) throws IOException {

		String result = "";

		ArrayList list = new ArrayList();

		Element tasks = new Element("tasks");

		tasks.addAttribute(new Attribute("type", asrCmdBean.getType()));

		tasks.addAttribute(new Attribute("wfType", asrCmdBean.getWfType()));

		Element task = new Element("task");

		task.addAttribute(new Attribute("id", ""));

		String filepath = asrCmdBean.getFile();
		if(filepath.indexOf("114.251.47.196")!=-1)
		{
			filepath = filepath.replace("http://114.251.47.196:80/video/",SystemConfig.getLoc_Video_location());
	
		}else
		{
		filepath = filepath.replace(SystemConfig.getLocVideoUrl(),SystemConfig.getLoc_Video_location());
		}
		Element e_file = new Element("file");
		e_file.addContent(filepath);

		Element e_fileStartTime = new Element("fileStartTime");
		e_fileStartTime.addContent(asrCmdBean.getFileStartTime());

		
		
		Element e_fileEndTime = new Element("fileEndTime");
		e_fileEndTime.addContent(asrCmdBean.getFileEndTime());

		Element e_taskStartTime = new Element("taskStartTime");
		e_taskStartTime.addContent(asrCmdBean.getTaskStartTime());

		Element e_taskEndTime = new Element("taskEndTime");
		e_taskEndTime.addContent(asrCmdBean.getTaskEndTime());

		Element e_freq = new Element("freq");
		e_freq.addContent(asrCmdBean.getFreq());
		
		Element e_language = new Element("language");
		e_language.addContent(asrCmdBean.getLanguage());
		
		Element e_collectMethod = new Element("collectMethod");
		e_collectMethod.addContent(asrCmdBean.getCollectMethod());

		Element e_collectChannel = new Element("collectChannel");
		if(asrCmdBean.getCollectChannel()!=null&&asrCmdBean.getCollectChannel().length()>0)
		
		   {
			    int channel=0;
				 if(Integer.parseInt(asrCmdBean.getCollectChannel())>20)
				 {
					 channel =Integer.parseInt(asrCmdBean.getCollectChannel())-20;
				 }else channel=Integer.parseInt(asrCmdBean.getCollectChannel());
				e_collectChannel.addContent(channel+"");
			}else 
			{
				e_collectChannel.addContent(asrCmdBean.getCollectChannel());
			}
		Element receiverType = new Element("receiverType");
	//	asrCmdBean.setReceiverType("NI");
		if(asrCmdBean.getReceiverType()!=null)
		{
				if(asrCmdBean.getReceiverType().indexOf("NI")!=-1)
				{
				
					receiverType.addContent("NI-1000");
				}else if(asrCmdBean.getReceiverType().indexOf("252A")!=-1)
				{
					
					receiverType.addContent("BXM-252A");
				}else if(asrCmdBean.getReceiverType().indexOf("SDR")!=-1)
				{
					
					receiverType.addContent("SF-RM");
					
				}else 
				{
					receiverType.addContent("NRD545");
				}
		}else {
			receiverType.addContent("");
		}
		task.addChild(e_file);

		task.addChild(e_fileStartTime);

		task.addChild(e_fileEndTime);

		task.addChild(e_taskStartTime);

		task.addChild(e_taskEndTime);

		task.addChild(e_freq);
		
		task.addChild(e_language);
		
		task.addChild(e_collectMethod);

		task.addChild(e_collectChannel);
		task.addChild(receiverType);

		list.add(task);

		Document doc = new Document(tasks);

		Element rootNode = doc.getRootElement();

		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();

	

			out.output(doc, sw);

	
		result = sw.toString();

//		System.out.println(result);

		return result;
	}

	/**
	 * ����ʶ�𷵻�XML��������
	 * <p>class/function:com.viewscenes.axis
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-16
	 * @param:
	 * @return:
	 */
	private   ASRResBean getResXml(String xml){
	
		System.out.println("---------------"+xml);
		ASRResBean asrResBean = new ASRResBean();
		Element root = StringTool.getXMLRoot(xml);
		String type = root.getChild("Type").getText();
		String orderId = root.getChild("OrderID").getText();
		String TaskStatus = root.getChild("TaskStatus").getText();
			
	
		asrResBean.setType(type);
		asrResBean.setOrderID(orderId);
		//String ErrorMessage= root.getChild("ErrorMessage").getText();
		if(!TaskStatus.equals("203"))
		{
			asrResBean.setTaskStatus("0000");//��Ϊ�д�����⡣
			
		}else 
		{
			Element subEl = root.getChild("TimePeriod");
            
			String timePeriod_type =subEl.getAttributeValue("type") ;
			asrResBean.setTimePeriodType(timePeriod_type);
			
			asrResBean.setTaskStatus(TaskStatus);
			String status = subEl.getChild("Status").getText();
			asrResBean.setStatus(status);
			String file = subEl.getChild("File").getText();
			asrResBean.setFile(file);
			String StartTime = subEl.getChild("StartTime").getText();
			asrResBean.setStartTime(StartTime);
			String EndTime = subEl.getChild("EndTime").getText();
			asrResBean.setEndTime(EndTime);
			String AudibilityScore = subEl.getChild("AudibilityScore").getText();
			asrResBean.setAudibilityScore(AudibilityScore);
			
			String MusicRatio = subEl.getChild("MusicRatio").getText();
			asrResBean.setMusicratio(MusicRatio);
			String NoiseRatio = subEl.getChild("NoiseRatio").getText();
			asrResBean.setNoiseratio(NoiseRatio);
			String SpeechLen = subEl.getChild("SpeechLen").getText();
			asrResBean.setSpeechlen(SpeechLen);
			String TotalCM = subEl.getChild("TotalCM").getText();
			asrResBean.setTotalcm(TotalCM);
			
			
			String AudibilityConfidence = subEl.getChild("AudibilityConfidence").getText();
			asrResBean.setAudibilityConfidence(AudibilityConfidence);
			String ChannelName = subEl.getChild("ChannelName").getText();
			asrResBean.setChannelName(ChannelName);
			String ChannelNameConfidence = subEl.getChild("ChannelNameConfidence").getText();
			asrResBean.setChannelNameConfidence(ChannelNameConfidence);
			String ProgramName = subEl.getChild("ProgramName").getText();
			asrResBean.setProgramName(ProgramName);
			String ProgramNameConfidence = subEl.getChild("ProgramNameConfidence").getText();
			asrResBean.setProgramNameConfidence(ProgramNameConfidence);
			String LanguageName1 = subEl.getChild("LanguageName1").getText();
			asrResBean.setLanguageName1(LanguageName1);
			String LanguageName2 = subEl.getChild("LanguageName2").getText();
			asrResBean.setLanguageName2(LanguageName2);
			String LanguageName3 = subEl.getChild("LanguageName3").getText();
			asrResBean.setLanguageName3(LanguageName3);
			String LanguageName4 = subEl.getChild("LanguageName4").getText();
			asrResBean.setLanguageName4(LanguageName4);
			String LanguageName5 = subEl.getChild("LanguageName5").getText();
			asrResBean.setLanguageName5(LanguageName5);
			String LanguageConfidence1 = subEl.getChild("LanguageConfidence1").getText();
			asrResBean.setLanguageConfidence1(LanguageConfidence1);
			String LanguageConfidence2 = subEl.getChild("LanguageConfidence2").getText();
			asrResBean.setLanguageConfidence2(LanguageConfidence2);
			String LanguageConfidence3 = subEl.getChild("LanguageConfidence3").getText();
			asrResBean.setLanguageConfidence3(LanguageConfidence3);
			String LanguageConfidence4 = subEl.getChild("LanguageConfidence4").getText();
			asrResBean.setLanguageConfidence4(LanguageConfidence4);
			String LanguageConfidence5 = subEl.getChild("LanguageConfidence5").getText();
			asrResBean.setLanguageConfidence5(LanguageConfidence5);
			
		}
		
		return asrResBean;
		
	}
	
	
	/**
	 * ȡ�ý��ջ�����
	 * <p>class/function:com.viewscenes.axis.asr
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-11-5
	 * @param:
	 * @return:
	 */
	public  String getReceiver(ResHeadendBean headendBean){
		String receiverType = "";
	
			if(headendBean.getType_id().equals("101"))
			{
				receiverType="SDR-1";
			}else
			{
				if(headendBean.getManufacturer().equals("ʥ����_ң��վ_NI_V8"))
				{
					receiverType="NI-1000";
				}else
				{
					receiverType="BXM-252A-1";
				}
			}
		
		
		if (receiverType.equals(""))
			receiverType = "NRD545";
		return receiverType;
	}
	
	
	/**
	 * ����ʶ�����ֺ�"׼��"��¼���ļ��Ĵ����Ϣ��������������
	 * <p>class/function:com.viewscenes.axis
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-8-20
	 * @param:
	 * @return:
	 */
	public   void  readyAsrResult2DB(FileRetrieveResult fileRetrieveResult,ASRResBean asrResBean){
		
		RadioMarkZstViewBean rmzvb = new RadioMarkZstViewBean();
		
		rmzvb.setMark_user("������");//�û�Ϊ������
		rmzvb.setHead_code(fileRetrieveResult.getHeadCode());
		rmzvb.setEqu_code(fileRetrieveResult.getEquCode());
		rmzvb.setFrequency(fileRetrieveResult.getFreq());
		try {
			RunplanBean runBean = MonitorRecPlayService.getRunplanByTaskId(fileRetrieveResult.getTaskId());
			String runplan_id = "";
			//������ͼ�й�ϵ��
			if (runBean != null){
				runplan_id = runBean.getRunplan_id();
				rmzvb.setStation_id(runBean.getStation_id());
				rmzvb.setStation_name(runBean.getStation_name());
				rmzvb.setPlay_time(runBean.getStart_time()+"-"+runBean.getEnd_time());
			}
			rmzvb.setRunplan_id(runplan_id);
		} catch (GDSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(asrResBean.getAudibilityScore().length()>0)
		{
			String[] sio = asrResBean.getAudibilityScore().split(",");
			if(sio.length==3)
			{
				//if(Integer.parseInt(asrResBean.getLanguageConfidence1())>=75)
				//{
				rmzvb.setCounts(sio[0]);
				rmzvb.setCounti(sio[1]);
				rmzvb.setCounto(sio[2]);
				//}else
				//{
				//	rmzvb.setCounto(sio[2]);
				//}
			}
		}
		
//		rmzvb.setLanguage_name(asrResBean.getLanguageName1());
		rmzvb.setDescription("");
		rmzvb.setMark_type("2");//������ͣ�¼�����
		rmzvb.setEdit_user("");
		
		//��ȡ��������������,��2013-03-29 13:49:51.000 ���д���
		String s = fileRetrieveResult.getStartDatetime();
		String e = fileRetrieveResult.getEndDatetime();
		
		
		s = (s.indexOf(".")>-1)?s.substring(0,s.indexOf(".")):s;
		e = (e.indexOf(".")>-1)?e.substring(0,e.indexOf(".")):e;
		
		String ee = fileRetrieveResult.getStartDatetime();
		ee = (ee.indexOf(".")>-1)?ee.substring(0,ee.indexOf(".")):ee;
		
		
		Date sdate =  StringTool.stringToDate(s);
		String shour = sdate.getHours()>10?sdate.getHours()+"":"0"+sdate.getHours();
		String sUnit =shour +":00:00";
		Date edate = StringTool.stringToDate(StringTool.Date2String(StringTool.addHours(ee,1)));
		String ehour = edate.getHours()>10?edate.getHours()+"":"0"+edate.getHours();
		String eUnit = ehour+":00:00";
		rmzvb.setUnit(sUnit + "-" + eUnit);
		
		String url = fileRetrieveResult.getUrl();
//		url = url.replaceAll("ftp://", SystemConfig.getVideoUrl());
		
		rmzvb.setMark_file_url(url.replaceAll("http:([\\S\\s]+?)video/", SystemConfig.getLocVideoUrl()));
		rmzvb.setFile_name(fileRetrieveResult.getFileName());
		rmzvb.setFile_length("");
		rmzvb.setRecord_start_time(s);
		rmzvb.setRecord_end_time(e);
		rmzvb.setTask_id(fileRetrieveResult.getTaskId());
		rmzvb.setTask_name("");
		//����վ�����Ƶ�ʱ�򲻴�ab����������ͳ�ơ�
		String headname = Common.getHeadendnameNOABByCode(fileRetrieveResult.getHeadCode());
		rmzvb.setHeadname(headname);
		rmzvb.setLevel_value(fileRetrieveResult.getLevel());
		rmzvb.setFm_value(fileRetrieveResult.getFmModulation());
		rmzvb.setAm_value(fileRetrieveResult.getAmModulation());
		rmzvb.setOffset_value(fileRetrieveResult.getOffset());
		rmzvb.setRemark("");
		
		rmzvb.setAsr_type(asrResBean.getType());
		rmzvb.setResult_type(asrResBean.getTimePeriodType());
		rmzvb.setStatus(asrResBean.getStatus());
		rmzvb.setWavelen(asrResBean.getWavelen());
		rmzvb.setMusicratio(asrResBean.getMusicratio());
		rmzvb.setNoiseratio(asrResBean.getNoiseratio());
		rmzvb.setSpeechlen(asrResBean.getSpeechlen());
		rmzvb.setTotalcm(asrResBean.getTotalcm());
		rmzvb.setAudibilityscore(asrResBean.getAudibilityScore());
		rmzvb.setAudibilityconfidence(asrResBean.getAudibilityConfidence());
		rmzvb.setChannelname(asrResBean.getChannelName());
		rmzvb.setChannelnameconfidence(asrResBean.getChannelNameConfidence());
		rmzvb.setProgramname(asrResBean.getProgramName());
		rmzvb.setProgramnameconfidence(asrResBean.getProgramNameConfidence());
		rmzvb.setLanguagename1(asrResBean.getLanguageName1());
		rmzvb.setLanguagename2(asrResBean.getLanguageName2());
		rmzvb.setLanguagename3(asrResBean.getLanguageName3());
		rmzvb.setLanguagename4(asrResBean.getLanguageName4());
		rmzvb.setLanguagename5(asrResBean.getLanguageName5());
		rmzvb.setLanguageconfidence1(asrResBean.getLanguageConfidence1());
		rmzvb.setLanguageconfidence2(asrResBean.getLanguageConfidence2());
		rmzvb.setLanguageconfidence3(asrResBean.getLanguageConfidence3());
		rmzvb.setLanguageconfidence4(asrResBean.getLanguageConfidence4());
		rmzvb.setLanguageconfidence5(asrResBean.getLanguageConfidence5());
		
		//
		MarkService.setMark(rmzvb);
	}
	
	
	public static void main(String[] args) {

		ASRClient clent = new ASRClient();

	
		
//		
//		
//		clent.getCmdXml("WorkFlow", "BC573", "file", "fileStartTime",
//				"fileEndTime", "taskStartTime", "taskEndTime", "collectMethod",
//				"collectChannel");
		
		
		//String xml = "";
//		= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//		
//		xml += " <tasks type=\"WorkFlow\" wfType=\"BC573\"> ";
//		xml += " <task id=\"\"> ";
//		xml += " <file>http://10.15.6.12:8000/video/OAF07A_42640_20120920142915_20120920143216_852_R1.mp3</file> ";
//		xml += " <fileStartTime>2012-09-20 14:29:15</fileStartTime> ";
//		xml += " <fileEndTime>2012-09-20 14:32:16</fileEndTime> ";
//		xml += " <taskStartTime>2012-09-20 14:29:15</taskStartTime> ";
//		xml += " <taskEndTime>2012-09-20 14:32:16</taskEndTime> ";
//		xml += " <freq>852</freq> ";
//		xml += " <language></language> ";
//		xml += " <collectMethod>2</collectMethod> ";
//		xml += " <collectChannel></collectChannel> ";
//		xml += " <receiverType>NI1000</receiverType> ";
//		xml += " <taskId>100</taskId> ";
//		xml += " </task> ";
//		xml += " </tasks> ";
		
//		PESBClient pesbClient = new PESBClient();
//		long l1 = System.currentTimeMillis();
//		
//		pesbClient.exucuteTask(xml);
//		
//		String result  =  pesbClient.queryResultByTaskId("100");
//		
//		long l2 = System.currentTimeMillis();
//		
//		System.out.println("result+"+(l2-l1));
//		
//		System.out.println("result+"+result);
		
//		xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//		xml += "<tasks type=\"TaskStatus\" wfType=\"BC573\">";
//		xml += "<task id=\"567\">";
//		
//		xml += "<status>201</status>";ASRCmdBean
//		xml += "</task>";
//		xml += "</tasks>";
//		Element root = StringTool.getXMLRoot(xml);
//		Element subEl =root.getChild("task").getAttributeValue("id");
//		System.out.println(subEl.getAttributeValue("id"));
		String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<TimePeriods><Type>11</Type><TaskID></TaskID><OrderID>88</OrderID>" +
				"<TaskStatus>203</TaskStatus><ErrorMessage> </ErrorMessage><TimePeriod type=\"BC573\">" +
				"<Status>�źŽϲ�</Status><File>ideo_location\2013/12/24/OOC03B/OOC03B_32223_20131224091849_20131224091949_1341_R2_��_ī����3CW����̨_����_SDR-2.mp3</File>" +
				"<StartTime>2013-12-24 09:18:49</StartTime><EndTime>2013-12-24 09:19:49</EndTime><WaveLen>60</WaveLen><MusicRatio>3</MusicRatio><NoiseRatio>22</NoiseRatio>" +
				"<SpeechLen>44</SpeechLen><TotalCM>85</TotalCM><AudibilityScore>2,2,2</AudibilityScore><AudibilityConfidence>90</AudibilityConfidence><ChannelName>δ֪</ChannelName>" +
				"<ChannelNameConfidence>98</ChannelNameConfidence><ProgramName>δ֪</ProgramName><ProgramNameConfidence>99</ProgramNameConfidence><LanguageName1>�źŽϲ�</LanguageName1>" +
				"<LanguageConfidence1>85</LanguageConfidence1><LanguageName2></LanguageName2><LanguageName3></LanguageName3><LanguageName4></LanguageName4>" +
				"<LanguageName5></LanguageName5><LanguageConfidence2></LanguageConfidence2><LanguageConfidence3></LanguageConfidence3><LanguageConfidence4></LanguageConfidence4>" +
				"<LanguageConfidence5></LanguageConfidence5></TimePeriod></TimePeriods>";
		clent.getResXml(str);
		System.out.println("2013-03-29 13:50:51".substring(11,"2013-03-29 13:50:51".length()-6)+":00:00");
		String aaa="http://114.251.47.196:80/video/2013/4/2/OAFC02/OAFC02_25481_20130402141334_20130402141434_97400_R2_��__����_SDR-2.mp3";
		aaa= aaa.replaceAll("http:([\\S\\s]+?)video/", "http://10.1.1.31:80/video/");
		System.out.println(aaa);
	    	
	}
}
