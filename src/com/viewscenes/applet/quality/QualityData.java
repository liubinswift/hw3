package com.viewscenes.applet.quality;

import com.viewscenes.applet.AppletUtil;
import java.util.Date;

public class QualityData {
    public String dataTypeTag;
    public long reportTime;
    public int dataLength;
    public int freq;
    public int[]  values = new int[6];

    public QualityData() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parseData(byte[] buffer)  {
        for (int i=0;i<6;i++)
            values[i] = 70;
        int length = AppletUtil.bytes2int(buffer,2);
        System.out.println("原始数据:");
        for (int i=0;i<length;i++){
            System.out.print(buffer[i]+" ");
        }
        System.out.println("");
        System.out.println("解析后数据：");
        this.dataTypeTag = new String(buffer, 6, 4);
        System.out.println(this.dataTypeTag);
        this.reportTime = AppletUtil.bytes2long(buffer,10)*1000;
        Date d = new Date();
        d.setTime(this.reportTime);
        System.out.println(d);
        this.freq = AppletUtil.bytes2int(buffer,18);
        System.out.println("freq:"+this.freq);

        this.dataLength = AppletUtil.bytes2short(buffer,22);
        System.out.println("data length:"+this.dataLength);
        for (int i=0;i<this.dataLength;i++){
            byte type = buffer[24+5*i];
            if (type==0)
                continue;
            values[type-1] = AppletUtil.bytes2int(buffer,24+5*i+1);
            System.out.println("type="+type+",value="+values[type-1]);
        }
}

    private void jbInit() throws Exception {
    }


}
