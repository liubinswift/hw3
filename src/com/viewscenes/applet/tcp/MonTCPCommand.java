package com.viewscenes.applet.tcp;

import com.viewscenes.applet.*;

public class MonTCPCommand {
    final static byte TCP_REQ_HEAD = 1;
    final static byte TCP_REQ_TYPE_NULL = 0;
    final static byte TCP_REQ_TYPE_CLIENT = 1;
    final static byte TCP_REQ_TYPE_SERVER = 2;
    final static byte TCP_REQ_TYPE_FREQSCAN = 3;
    final static byte TCP_REQ_TYPE_QUALITY = 4;
    final static byte TCP_REQ_TYPE_OTHER = 5;

    final static String VIDEO_FREQSCAN_XML ="<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?> "
    +"   <Msg Version=\"1\" MsgID=\"2\" Type=\"TVMonDown\" Protocol=\"TCP\" DateTime=\"2006-08-17 15:30:00\" SrcCode=\"110000X01\" DstCode=\"110000N01\" Priority=\"1\">"
    +"    <SpectrumRealtimeScan EquCode=\"110000N00001\" StartFreq=\"$startFreq$\" EndFreq=\"$endFreq$\" StepFreq=\"$stepFreq$\" Steps=\"$steps$\" Action=\"Start\">"
    +"    </SpectrumRealtimeScan>"
    +"    </Msg>";

    final static String RADIO_FREQSCAN_XML ="<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?> "
    +"   <Msg Version=\"1\" MsgID=\"2\" Type=\"RadioDown\" Protocol=\"TCP\" DateTime=\"2006-08-17 15:30:00\" SrcCode=\"110000X01\" DstCode=\"110000N01\" Priority=\"1\">"
    +"    <SpectrumRealtimeScan EquCode=\"110000N00001\" StartFreq=\"$startFreq$\" EndFreq=\"$endFreq$\" StepFreq=\"$stepFreq$\" Steps=\"$steps$\" Action=\"Start\">"
    +"    </SpectrumRealtimeScan>"
    +"    </Msg>";

    final static String VIDEO_QUALITY_XML = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?> "
    +"  <Msg Version=\"1\" MsgID=\"2\" Type=\"TVMonDown\" Protocol=\"TCP\" DateTime=\"2006-08-17 15:30:00\" SrcCode=\"110000X01\" DstCode=\"110000N01\" Priority=\"1\">"
    +"           <QualityRealtimeQuery EquCode=\"110000N00001\" Freq=\"$freq$\" Action=\"Start\">"
    +"           <QualityIndex Type=\"1\" Desc=\"图像载波电平，单位:dbuv\"/>"
    +"           <QualityIndex Type=\"2\" Desc=\"伴音载波电平，单位:dbuv\"/>"
    +"           <QualityIndex Type=\"3\" Desc=\"图像载波与伴音载波的电平差，单位:dbuv\"/>"
    +"            <QualityIndex Type=\"4\" Desc=\"载噪比，单位:dbuv\"/>"
    +"           <QualityIndex Type=\"5\" Desc=\"载频频偏，单位:kHz\"/>"
    +"           </QualityRealtimeQuery>"
    +"   </Msg>";

    final static String RADIO_QUALITY_XML = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?> "
    +"  <Msg Version=\"1\" MsgID=\"2\" Type=\"RadioDown\" Protocol=\"TCP\" DateTime=\"2006-08-17 15:30:00\" SrcCode=\"110000X01\" DstCode=\"110000N01\" Priority=\"1\">"
    +"           <QualityRealtimeQuery EquCode=\"110000N00001\" Freq=\"$freq$\" Action=\"Start\">"
    +"           <QualityIndex Type=\"1\" Desc=\"信号载波电平\"/>"
    +"           <QualityIndex Type=\"2\" Desc=\"调制度(瞬时值)\"/>"
    +"           <QualityIndex Type=\"3\" Desc=\"音频信号电平\"/>"
    +"           <QualityIndex Type=\"4\" Desc=\"信号载波频率\"/>"
    +"           <QualityIndex Type=\"5\" Desc=\"谐波失真\"/>"
    +"           <QualityIndex Type=\"6\" Desc=\"信噪比\"/>"
    +"           </QualityRealtimeQuery>"
    +"   </Msg>";

    final static String CLIENT_INFO_XML = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>"
    +"  <Msg Version=\"1\" MsgID=\"2\" Type=\"TVMonUp\" Protocol=\"TCP\" DateTime=\"2006-08-17 15:30:00\" SrcCode=\"110000N01\" DstCode=\"110000X01\">"
    +"  <ClientInfo>"
    +"  <Client URL=\"$url$\" IP=\"$ip$\" UserName=\"$userName$\" Priority=\"$priority$\" CenterCode=\"$centerCode$\"/>"
    +"  </ClientInfo>"
    +"  </Msg>";

    final static String RADIO_CLIENT_INFO_XML = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>"
    +"  <Msg Version=\"1\" MsgID=\"2\" Type=\"RadioUp\" Protocol=\"TCP\" DateTime=\"2006-08-17 15:30:00\" SrcCode=\"110000N01\" DstCode=\"110000X01\">"
    +"  <ClientInfo>"
    +"  <Client URL=\"$url$\" IP=\"$ip$\" UserName=\"$userName$\" Priority=\"$priority$\" CenterCode=\"$centerCode$\"/>"
    +"  </ClientInfo>"
    +"  </Msg>";


    public MonTCPCommand() {
    }

    public static byte[] getClientInfoCommand(String url,String ip,String userName,String priority,String centerCode){
        String xml = AppletUtil.replaceStr(CLIENT_INFO_XML,"$url$",url);
        xml = AppletUtil.replaceStr(xml,"$ip$",ip);
        xml = AppletUtil.replaceStr(xml,"$userName$",userName);
        xml = AppletUtil.replaceStr(xml,"$priority$",priority);
        xml = AppletUtil.replaceStr(xml,"$centerCode$",centerCode);
        System.out.println(xml);
        byte[] buffer = createSendData(TCP_REQ_TYPE_CLIENT,xml);
        return buffer;
    }

    public static byte[] getRadioClientInfoCommand(String url,String ip,String userName,String priority,String centerCode){
        String xml = AppletUtil.replaceStr(RADIO_CLIENT_INFO_XML,"$url$",url);
        xml = AppletUtil.replaceStr(xml,"$ip$",ip);
        xml = AppletUtil.replaceStr(xml,"$userName$",userName);
        xml = AppletUtil.replaceStr(xml,"$priority$",priority);
        xml = AppletUtil.replaceStr(xml,"$centerCode$",centerCode);
        System.out.println(xml);
        byte[] buffer = createSendData(TCP_REQ_TYPE_CLIENT,xml);
        return buffer;
    }


    public static byte[] getVideoFreqScanCommand(String startFreq,String endFreq,String stepFreq,String steps){
     String xml = AppletUtil.replaceStr(VIDEO_FREQSCAN_XML,"$startFreq$",startFreq);
     xml = AppletUtil.replaceStr(xml,"$endFreq$",endFreq);
     xml = AppletUtil.replaceStr(xml,"$stepFreq$",stepFreq);
     xml = AppletUtil.replaceStr(xml,"$steps$",steps);
     System.out.println(xml);
     byte[] buffer = createSendData(TCP_REQ_TYPE_FREQSCAN,xml);
     return buffer;
    }

    public static byte[] getVideoQualityCommand(String freq){
     String xml = AppletUtil.replaceStr(VIDEO_QUALITY_XML,"$freq$",freq);
     System.out.println(xml);
     byte[] buffer = createSendData(TCP_REQ_TYPE_QUALITY,xml);
     return buffer;
    }


    public static byte[] getRadioFreqScanCommand(String startFreq,String endFreq,String stepFreq,String steps){
     String xml = AppletUtil.replaceStr(RADIO_FREQSCAN_XML,"$startFreq$",startFreq);
     xml = AppletUtil.replaceStr(xml,"$endFreq$",endFreq);
     xml = AppletUtil.replaceStr(xml,"$stepFreq$",stepFreq);
     xml = AppletUtil.replaceStr(xml,"$steps$",steps);
     byte[] buffer = createSendData(TCP_REQ_TYPE_FREQSCAN,xml);
     return buffer;
    }

    public static byte[] getRadioQualityCommand(String freq){
     String xml = AppletUtil.replaceStr(RADIO_QUALITY_XML,"$freq$",freq);
     System.out.println(xml);
     byte[] buffer = createSendData(TCP_REQ_TYPE_QUALITY,xml);
     return buffer;
    }

   static byte[] createSendData(byte type,String xml){
        byte[] xmlByte = xml.getBytes();
        byte[] buffer = new byte[xmlByte.length+6];
        buffer[0] = TCP_REQ_HEAD;
        buffer[1] = type;
        byte[] length = AppletUtil.int2bytes(xmlByte.length);
        System.arraycopy(length,0,buffer,2,4);
        System.arraycopy(xmlByte,0,buffer,6,xmlByte.length);
        return buffer;
    }
}
