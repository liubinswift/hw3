package com.viewscenes.util.business.runplan;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ProgramInfo
    extends RunPlanInfo { //频率//节目编号//发射台编号
  private String programName = ""; //节目名称//发射台名称
  private String programType = ""; //节目类型//发射台位置//运行图类型
  private String coverObject = "";
  private String antenna = "";
  private String serviceArea = "";
  private String language = "";
  private String direction = "";
  private String antennaType = "";
  private String broadType = "";
  private String broadCast = ""; //运行图编号
  private String weekDays = "";
  public ProgramInfo() {
  }

  public ProgramInfo(RunPlanInfo runPlanInfo) {
    this.setFrequency(runPlanInfo.getFrequency());
    this.setPower(runPlanInfo.getPower());
    this.setRunplanID(runPlanInfo.getRunplanID());
    this.setRunplanType(runPlanInfo.getRunplanType());
    this.setStationID(runPlanInfo.getStationID());
    this.setStationType(runPlanInfo.getStationType());
    this.setStationName(runPlanInfo.getStationName());
    this.setTemporary(runPlanInfo.getTemporary());
    this.setTransmitter(runPlanInfo.getTransmitter());
    this.setTypeName(runPlanInfo.getTypeName());
    this.setValidendtime(runPlanInfo.getValidendtime());
    this.setValidstarttime(runPlanInfo.getValidstarttime());
  }

  public String getProgramName() {
    return programName;
  }

  public void setProgramName(String programName) {
    if (programName != null) {
      this.programName = programName.trim();
    }
  }

  public String getProgramType() {
    return programType;
  }

  public void setProgramType(String programType) {
    if (programType != null) {
      this.programType = programType.trim();
    }
  }

  public String getCoverObject() {
    return coverObject;
  }

  public void setCoverObject(String coverObject) {
    if (coverObject != null) {
      this.coverObject = coverObject.trim();
    }
  }

  public String getAntenna() {
    return antenna;
  }

  public void setAntenna(String antenna) {
    if (antenna != null) {
      this.antenna = antenna.trim();
    }
  }

  public String getServiceArea() {
    return serviceArea;
  }

  public void setServiceArea(String serviceArea) {
    if (serviceArea != null) {
      this.serviceArea = serviceArea.trim();
    }
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    if (language != null) {
      this.language = language.trim();
    }
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    if (direction != null) {
      this.direction = direction.trim();
    }
  }

  public String getAntennaType() {
    return antennaType;
  }

  public void setAntennaType(String antennaType) {
    if (antennaType != null) {
      this.antennaType = antennaType.trim();
    }
  }

  public String getBroadType() {
    return broadType;
  }

  public void setBroadType(String broadType) {
    if (broadType != null) {
      this.broadType = broadType.trim();
    }
  }

  public String getBroadCast() {
    return broadCast;
  }

  public void setBroadCast(String broadCast) {
    if (broadCast != null) {
      this.broadCast = broadCast.trim();
    }
  }

  public String getWeekDays() {
    return weekDays;
  }

  public void setWeekDays(String weekDays) {
    if (weekDays != null) {
      this.weekDays = weekDays.trim();
    }
  }

  public boolean equals(ProgramInfo obj) {
    return this.toString().equalsIgnoreCase(obj.toString());
  }

  public String toString() {
    return super.toString() + ":" + programName + ":" + programType + ":" +
	coverObject + ":"
	+ antenna + ":" + serviceArea + ":" + language + ":" + direction + ":"
	+ antennaType + ":" + broadType + ":" + broadCast + ":" + weekDays;
  }

}
