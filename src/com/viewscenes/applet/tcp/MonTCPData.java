package com.viewscenes.applet.tcp;

import com.viewscenes.applet.AppletUtil;

public class MonTCPData {
    public String dataTypeTag;
    public long reportTime;
    public int dataLength;
    public int msgType;
    public int msgDescLen;
    public String msgDesc;

    public MonTCPData() {
        super();
    }
    public void parseData(byte[] buffer)  {
        this.dataTypeTag = new String(buffer, 6, 4);
        this.reportTime = AppletUtil.bytes2long(buffer,10)*1000;
        this.dataLength = AppletUtil.bytes2short(buffer,18);
        this.msgType = AppletUtil.bytes2short(buffer,20);
        this.msgDescLen = AppletUtil.bytes2short(buffer,22);
        this.msgDesc = new String(buffer,24,this.msgDescLen);
    }

}