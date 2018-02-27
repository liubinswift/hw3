package com.viewscenes.applet.freqscan;

import com.viewscenes.applet.AppletUtil;
import java.util.Date;

public class FreqScanData {
    public String dataTypeTag;
    public long reportTime;
    public int dataLength;
    public int[]  freqs;
    public int[]  values;


    public FreqScanData() {
    }

    public void parseData(byte[] buffer)  {
        this.dataTypeTag = new String(buffer, 6, 4);

        this.reportTime = AppletUtil.bytes2long(buffer,10)*1000;

        this.dataLength = AppletUtil.bytes2short(buffer,18);

        freqs = new int[this.dataLength];
        values = new int[this.dataLength];
        for (int i=0;i<this.dataLength;i++){
            freqs[i] = AppletUtil.bytes2int(buffer,20+8*i);
            values[i] = AppletUtil.bytes2int(buffer,20+8*i+4);
        }
}




}
