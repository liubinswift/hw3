package com.viewscenes.bean.device;

import org.jdom.Element;

import com.viewscenes.bean.runplan.RunplanBean;
import com.viewscenes.bean.task.Task;
import com.viewscenes.device.util.Zip;
import com.viewscenes.util.business.SiteRunplanUtil;

/**
 * 设备端录音文件获取返回结果类 
 * 
 * @author thinkpad
 * 
 */
public class FileRetrieveResult{

	private String fileUrl;
	private String level;
	private String fmModulation = "";
	private String amModulation = "";
	private String offset;
	
	private String fileName;
	private String headCode;
	private String taskId;
	private String startDatetime;
	private String endDatetime;
	private String freq;
	private String equCode;
	private String band;
	private String language = "";
	private String stationName = "";	//发射台名称
	private String recType = "";	//录音类型：效果，质量，临时
	
	private String result_id;
	private String filesize = "";
	private String head_id;
	private String url;			//转换成本地的MEDIA地址
	private String report_type;
	private String is_stored;
	private String store_datetime;
	private String mark_file_name;	//与打分数据相关联字段
	private String runplan_id;
	private String reveiceType;	//接收机类型
	
	private Task task; // 相关联的TASK对象
	private RunplanBean runplanBean; // 相关联的运行图对象
	
	public String getFileUrl() {
		return fileUrl;
	}


	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getFmModulation() {
		return fmModulation;
	}


	public void setFmModulation(String fmModulation) {
		this.fmModulation = fmModulation;
	}


	public String getAmModulation() {
		return amModulation;
	}


	public void setAmModulation(String amModulation) {
		this.amModulation = amModulation;
	}


	public String getOffset() {
		return offset;
	}


	public void setOffset(String offset) {
		this.offset = offset;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getHeadCode() {
		return headCode;
	}


	public void setHeadCode(String headCode) {
		this.headCode = headCode;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getStartDatetime() {
		return startDatetime;
	}


	public void setStartDatetime(String startDatetime) {
		this.startDatetime = startDatetime;
	}


	public String getEndDatetime() {
		return endDatetime;
	}


	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}


	public String getFreq() {
		return freq;
	}


	public void setFreq(String freq) {
		this.freq = freq;
	}


	public String getEquCode() {
		return equCode;
	}


	public void setEquCode(String equCode) {
		this.equCode = equCode;
	}


	public String getBand() {
		return band;
	}


	public void setBand(String band) {
		this.band = band;
	}

	

	
	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}
	

	public String getRecType() {
		return recType;
	}


	public void setRecType(String recType) {
		this.recType = recType;
	}


	
	
	public String getStationName() {
		return stationName;
	}


	public void setStationName(String stationName) {
		this.stationName = stationName;
	}


	public FileRetrieveResult(){
		
	}
	
	
	/**
	 * 根据节点赋值变量
	 * @detail  
	 * @method  
	 * @param attrs 
	 * @return  void  
	 * @author  zhaoyahui
	 * @version 2012-8-23 上午10:58:21
	 */
	public void setMap(Element attrs){
		this.setFileUrl((String) attrs.getAttributeValue("fileurl"));
		this.setLevel((String) attrs.getAttributeValue("level"));
		this.setFmModulation( (String) attrs.getAttributeValue("fm-modulation") );
		this.setAmModulation((String) attrs.getAttributeValue("am-modulation") );
		this.setOffset( (String) attrs.getAttributeValue("offset") );
		//
		//录音文件名命名规则：站点代号_任务号_录音开始日期时间_录音结束日期时间_频率_接收机代号_语言_发射台_录音类型_发射机类型.mp3
		//OAF07A_43136_20121225114102_20121225114203_97400_R1_阿尔_2022_效果_NI-1000.mp3
		this.setFileUrl(this.getFileUrl().replaceAll("\\\\", "/"));
		
		
		this.setFileName(this.getFileUrl().substring(this.getFileUrl().lastIndexOf("/")+1));
		String[] names = this.getFileName().split("_");
		
		String ext = ".mp3";
		switch (names.length){
			case 10:
				ext = fileUrl.endsWith(".mp3")?".mp3":".zip";
				this.setReveiceType(names[9].substring(0,names[9].indexOf(ext)));
			case 9:
				//录音类型：0：效果录音，1质量录音，2实时录音，3：临时录音
				String rep = names[8];
				this.setReport_type(rep);
			case 8:
				this.setStationName(names[7]);
			case 7:
				this.setLanguage(names[6]);
			case 6:
				this.setHeadCode(names[0]);
				this.setTaskId(names[1]);
				this.setStartDatetime(names[2]);
				this.setEndDatetime(names[3]);
				this.setFreq(names[4]);
				this.setEquCode(names[5]);
				this.setLanguage(names[6]);
				this.setStationName(names[7]);
				this.setBand(SiteRunplanUtil.getBandFromFreq(this.getFreq()));
			break;
			
		}
		if (ext.equals(".zip"))
			Zip.unZip2Local(fileUrl);
		
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public RunplanBean getRunplanBean() {
		return runplanBean;
	}

	public void setRunplanBean(RunplanBean runplanBean) {
		this.runplanBean = runplanBean;
	}

	public String getResult_id() {
		return result_id;
	}

	public void setResult_id(String resultId) {
		result_id = resultId;
	}


	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getHead_id() {
		return head_id;
	}

	public void setHead_id(String headId) {
		head_id = headId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String reportType) {
		report_type = reportType;
	}

	public String getIs_stored() {
		return is_stored;
	}

	public void setIs_stored(String isStored) {
		is_stored = isStored;
	}


	public String getStore_datetime() {
		return store_datetime;
	}

	public void setStore_datetime(String storeDatetime) {
		store_datetime = storeDatetime;
	}

	public String getMark_file_name() {
		return mark_file_name;
	}

	public void setMark_file_name(String markFileName) {
		mark_file_name = markFileName;
	}

	public String getRunplan_id() {
		return runplan_id;
	}

	public void setRunplan_id(String runplanId) {
		runplan_id = runplanId;
	}


	public String getReveiceType() {
		return reveiceType;
	}


	public void setReveiceType(String reveiceType) {
		this.reveiceType = reveiceType;
	}

	
	
}
