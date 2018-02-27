package com.viewscenes.app;

import java.util.*;

import com.viewscenes.pub.GDSetTool;
import com.viewscenes.pub.exception.AppException;
import com.viewscenes.util.StringTool;

/**
 * 发送消息的数据结构
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company:viewscenes </p>
 * @author 刘斌
 * @version 1.0
 */
public class MessageData {

  public final static String DELIM = "$";
  public final static char CODECHAR = '#';

  public MessageData() {
  }

  private String srcCenter = ""; // 发送站点
  private String destCenter = ""; // 接收站点
  private Date reportDate; // 发送时间
  private String senderName = ""; // 发送者
  private String reportType = ""; // 发送类别

  private String strMessage = ""; // 发送的数据

  public String getStrMessage() {
    return strMessage;
  }

  public void setStrMessage(String message) {
    this.strMessage = "<data>" + message + "</data>";
  }

  public void addMessageData(String message) {
    this.strMessage += "<data>" + message + "</data>";

  }

  public String[] getMessageData() {
    String[] returnValue = null;
    returnValue = strMessage.split("</data>");
    for (int i = 0; i < returnValue.length; i++) {
      if (returnValue[i].substring(0, 6).equalsIgnoreCase("<data>")) {
	returnValue[i] = returnValue[i].substring(6);
      }
    }
    return returnValue;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("<source>");
    buffer.append(GDSetTool.encode(srcCenter, DELIM, CODECHAR));
    buffer.append("</source>");

    buffer.append("<dest>");
    buffer.append(GDSetTool.encode(destCenter, DELIM, CODECHAR));
    buffer.append("</dest>");

    buffer.append("<reportdate>");
    try {
      buffer.append(GDSetTool.encode(StringTool.Date2String(reportDate), DELIM,
				     CODECHAR));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    buffer.append("</reportdate>");

    buffer.append("<sender>");
    buffer.append(GDSetTool.encode(senderName, DELIM, CODECHAR));
    buffer.append("</sender>");

    buffer.append("<type>");
    buffer.append(GDSetTool.encode(reportType, DELIM, CODECHAR));
    buffer.append("</type>");

    buffer.append("<message>");
    buffer.append(GDSetTool.encode(strMessage, DELIM, CODECHAR));
    buffer.append("</message>");

    return buffer.toString();
  }

  static public MessageData parseString2MessageData(String str) throws
      AppException {
    MessageData md = new MessageData();

    int indexStart = str.indexOf("<source>");
    int indexEnd = str.indexOf("</source>");
    if ( (indexStart == -1) || (indexEnd == -1)) {
      throw new AppException("Parse String: Can't find table name from string!");
    }
    String a = GDSetTool.decode(str.substring(indexStart + 8, indexEnd), DELIM,
				CODECHAR);
    md.setSrcCenter(a);

    indexStart = str.indexOf("<dest>");
    indexEnd = str.indexOf("</dest>");
    if ( (indexStart == -1) || (indexEnd == -1)) {
      throw new AppException("Parse String: Can't find table name from string!");
    }
    a = GDSetTool.decode(str.substring(indexStart + 6, indexEnd), DELIM,
			 CODECHAR);
    md.setDestCenter(a);

    indexStart = str.indexOf("<reportdate>");
    indexEnd = str.indexOf("</reportdate>");
    if ( (indexStart == -1) || (indexEnd == -1)) {
      throw new AppException("Parse String: Can't find table name from string!");
    }
    a = GDSetTool.decode(str.substring(indexStart + 12, indexEnd), DELIM,
			 CODECHAR);
    try {
      Date d = StringTool.stringToDate(a);
      md.setReportDate(d);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    indexStart = str.indexOf("<sender>");
    indexEnd = str.indexOf("</sender>");
    if ( (indexStart == -1) || (indexEnd == -1)) {
      throw new AppException("Parse String: Can't find table name from string!");
    }
    a = GDSetTool.decode(str.substring(indexStart + 8, indexEnd), DELIM,
			 CODECHAR);
    md.setSenderName(a);

    indexStart = str.indexOf("<type>");
    indexEnd = str.indexOf("</type>");
    if ( (indexStart == -1) || (indexEnd == -1)) {
      throw new AppException("Parse String: Can't find table name from string!");
    }
    a = GDSetTool.decode(str.substring(indexStart + 6, indexEnd), DELIM,
			 CODECHAR);
    md.setReportType(a);

    indexStart = str.indexOf("<message>");
    indexEnd = str.indexOf("</message>");
    if ( (indexStart == -1) || (indexEnd == -1)) {
      throw new AppException("Parse String: Can't find table name from string!");
    }
    a = GDSetTool.decode(str.substring(indexStart + 9, indexEnd), DELIM,
			 CODECHAR);
    md.setStrMessage(a);

    return md;

  }

  public String getDestCenter() {
    return destCenter;
  }

  public Date getReportDate() {
    return reportDate;
  }

  public String getReportType() {
    return reportType;
  }

  public String getSenderName() {
    return senderName;
  }

  public String getSrcCenter() {
    return srcCenter;
  }

  public void setDestCenter(String destCenter) {
    this.destCenter = destCenter;
  }

  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public void setSrcCenter(String srcCenter) {
    this.srcCenter = srcCenter;
  }
}