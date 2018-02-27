package com.viewscenes.util.business.runplan;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RunPlanInfo
    implements Cloneable {
  private String runplanID = "";
  private String runplanType = "";
  private String transmitter = "";
  private String stationID = "";
  private String stationType = "";
  private long validstarttime = 0;
  private long validendtime = 0;
  private String power = "";
  private String temporary = "";
  private String frequency = "";
  private String stationName = "";
  private String typeName = "";
  public RunPlanInfo() {
  }

  public String getRunplanID() {
    return runplanID;
  }

  public void setRunplanID(String runplanID) {
    if (runplanID != null) {
      this.runplanID = runplanID.trim();
    }
  }

  public String getRunplanType() {
    return runplanType;
  }

  public void setRunplanType(String runplanType) {
    if (runplanType != null) {
      this.runplanType = runplanType.trim();
    }
  }

  public String getTransmitter() {
    return transmitter;
  }

  public void setTransmitter(String transmitter) {
    if (transmitter != null) {
      this.transmitter = transmitter.trim();
    }
  }

  public String getStationID() {
    return stationID;
  }

  public void setStationID(String stationID) {
    if (stationID != null) {
      this.stationID = stationID.trim();
    }
  }

  public String getStationType() {
    return stationType;
  }

  public void setStationType(String stationType) {
    if (stationType != null) {
      this.stationType = stationType.trim();
    }
  }

  public long getValidstarttime() {
    return validstarttime;
  }

  public void setValidstarttime(long validstarttime) {
    this.validstarttime = validstarttime;
  }

  public long getValidendtime() {
    return validendtime;
  }

  public void setValidendtime(long validendtime) {
    this.validendtime = validendtime;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    if (power != null) {
      this.power = power.trim();
    }
  }

  public String getTemporary() {
    return temporary;
  }

  public void setTemporary(String temporary) {
    if (temporary != null) {
      this.temporary = temporary.trim();
    }
  }

  public String getFrequency() {
    return frequency;
  }

  public void setFrequency(String frequency) {
    if (frequency != null) {
      this.frequency = frequency.trim();
    }
  }

  public String getStationName() {
    return stationName;
  }

  public void setStationName(String stationName) {
    if (stationName != null) {
      this.stationName = stationName.trim();
    }
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    if (typeName != null) {
      this.typeName = typeName.trim();
    }
  }

  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return this;
  }

  public boolean equals(RunPlanInfo obj) {
    return this.toString().equalsIgnoreCase(obj.toString());
  }

  public String toString() {
    return runplanID + ":" + runplanType + ":" + transmitter + ":" + stationID +
	":"
	+ stationType + ":" + validstarttime + ":" + validendtime + ":" + power +
	":"
	+ temporary + ":" + frequency + ":" + stationName + ":" + typeName;
  }

}
