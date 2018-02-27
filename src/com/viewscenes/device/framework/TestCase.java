package com.viewscenes.device.framework;



import java.io.*;

import java.util.*;

import java.net.*;



import com.viewscenes.device.exception.*;

//import com.viewscenes.device.matrix.*;
//
//import com.viewscenes.device.record.*;
//
//import com.viewscenes.device.tv.*;

//import com.viewscenes.device.radio.*;

import com.viewscenes.device.thirdparty.*;

import com.viewscenes.util.LogTool;



public class TestCase {

  private void print(Object o) {

    System.out.println(o);

  }

/*

  private String makeHexString(int number){

  Random r = new Random(System.currentTimeMillis());

  String s = "";

  for(int i = 0; i < number; i++){

//    String y = new java.math.BigInteger(("" + r.nextInt(99)), 10).toString(16);

//    y += m.get(new Integer(y.length())).toString();

    s += ( "<value>" + r.nextInt(99) + "." + r.nextInt(9) + "</value>");

  }

  return s;

}

  private void doTest(BaseMessageCommand cmd, BaseMessageResponse res) {

    try {

      print(cmd.getMessage().getMessage());

      new MessageClient().execute(cmd, res);

      print("OK");

    } catch (DeviceException ex) {

      ex.printStackTrace();

    }

    LogTool.info("devicelog", "end");

  }



  private void doSetDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000N01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetSecurityDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000S01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetContentDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000C01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetQualityDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000Q01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetTerminalDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000T01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetRecordDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("900000J01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetMatrixDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000M01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void doSetRadioDestCode(BaseMessageCommand cmd) {

    try {

      cmd.setDestCode("110000R01");

    } catch (DeviceNotExistException ex) {

      ex.printStackTrace();

    }

  }



  private void testChannelScanQuery() {

    MsgChannelScanQueryCmd cmd = new MsgChannelScanQueryCmd();

    doSetDestCode(cmd);

    cmd.setChannels(Arrays.asList(new MsgChannelScanQueryCmd.ChannelScan[] {

                                  new MsgChannelScanQueryCmd.ChannelScan(

        "110000N00001", "112.25", "120.25", "8", "","100","75"),

    }));

    MsgChannelScanQueryRes res = new MsgChannelScanQueryRes();

    doTest(cmd, res);

    print(res.getChannelsFreqs());

  }



  private void testChannelSet() {

    MsgChannelSetCmd cmd = new MsgChannelSetCmd();

    doSetDestCode(cmd);

//    cmd.setQualityEquCode("110000Z00001");

    cmd.setChannels(Arrays.asList(new MsgChannelSetCmd.ChannelSet[] {

                                  new MsgChannelSetCmd.ChannelSet("100CH",

        "chname", "121.1", "6870", "QAM64", "10", "20", "30", "301", "302"),

                                  new MsgChannelSetCmd.ChannelSet("200CH",

        "CCTV2", "122.1", "6870", "QAM64", "10", "20", "30", "301", "302"), }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testQualityAlarmParamSet() {

    com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd cmd = new com.viewscenes.

        device.tv.MsgQualityAlarmParamSetCmd();

    doSetDestCode(cmd);

    cmd.setChannel("110000Z00001", "Z01");

    Collection c = new ArrayList();

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("1",

        "图像载波电平", "123.4", "134.5", ""));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("2",

        "伴音载波电平", "123.4", "134.5", ""));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("3",

        "图像伴音比", "123.4", "134.5", ""));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("4",

        "载噪比", "123.4", "134.5", ""));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("5",

        "频偏", "123.4", "134.5", ""));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("6",

        "斜率", "123.4", "134.5", ""));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("10",

        "无载波", "0", "", "5"));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("11",

        "无同步", "0", "", "5"));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("12",

        "无伴音", "0", "", "5"));

    c.add(new com.viewscenes.device.tv.MsgQualityAlarmParamSetCmd.QualityAlarmParam("13",

        "图象静止", "0", "", "5"));

    cmd.setQualityAlarmParams(c);

    doTest(cmd, new MsgDefaultRes());

  }



  private void testQualityAlarmQuery() {

    MsgQualityAlarmQueryCmd cmd = new MsgQualityAlarmQueryCmd();

    doSetDestCode(cmd);

    cmd.setQualityAlarmQueryParams("110000Z00001", "Z01", "2002-08-17 15:30:00",

                                   "2002-08-17 15:30:00");

    MsgQualityAlarmQueryRes res = new MsgQualityAlarmQueryRes();

    doTest(cmd, res);

    Collection c = res.getQualityAlarms();

    if (c == null) {

      return;

    }

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgQualityAlarmQueryRes.QualityAlarmReport qa = (MsgQualityAlarmQueryRes.QualityAlarmReport)

          it.next();

      print(qa.getAlarmID());

      print(qa.getChannelCode());

      print(qa.getCheckTime());

      print(qa.getDesc());

      print(qa.getEquCode());

      print(qa.getMode());

      print(qa.getReason());

      print(qa.getType());

      print(qa.getValue());

    }

  }



  private void testQualityIndexQuery() {

    MsgQualityIndexQueryCmd cmd = new MsgQualityIndexQueryCmd();

    doSetDestCode(cmd);

    cmd.setQualityIndexQueryParams("110000Z00001", "Z01", "1",

                                   "2002-08-17 15:30:00",

                                   "2002-08-17 15:30:00");

    MsgQualityIndexQueryRes res = new MsgQualityIndexQueryRes();

    doTest(cmd, res);

    Collection q = res.getQuality();

    if (q == null) {

      return;

    }

    for (Iterator it = q.iterator(); it.hasNext(); ) {

      MsgQualityIndexQueryRes.Quality qi = (MsgQualityIndexQueryRes.Quality) it.next();

      print(qi.getChannelCode());

      print(qi.getCheckTime());

      print(qi.getEquCode());

      Collection c = qi.getQualityIndex();

      for (Iterator itt = c.iterator(); itt.hasNext(); ) {

        MsgQualityIndexQueryRes.QualityIndex qii = (MsgQualityIndexQueryRes.

            QualityIndex) itt.next();

        print(qii.getDesc());

        print(qii.getType());

        print(qii.getValue());

      }

    }

  }



  private void testQualityRealtimeQuery() {

    com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd cmd = new com.viewscenes.

        device.tv.MsgQualityRealtimeQueryCmd();

    doSetDestCode(cmd);

    cmd.setEquCode("110000Z00001", "121.1");

    cmd.setQueryTypes(Arrays.asList(new com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd.

                                    QueryTypes[] {

                                    new com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd.

                                    QueryTypes(

        "1", "图像电平"),

                                    new com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd.

                                    QueryTypes(

        "2", "伴音电平"),

                                    new com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd.

                                    QueryTypes(

        "3", "图像载波与伴音载波的电平差"),

                                    new com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd.

                                    QueryTypes(

        "4", "载噪比"),

                                    new com.viewscenes.device.tv.MsgQualityRealtimeQueryCmd.

                                    QueryTypes(

        "5", "载频频偏"),

    }));



    com.viewscenes.device.tv.MsgQualityRealtimeQueryRes res = new com.viewscenes.

        device.tv.MsgQualityRealtimeQueryRes();

    doTest(cmd, res);

    Collection q = res.getQuality();

    if (q == null) {

      return;

    }

    for (Iterator it = q.iterator(); it.hasNext(); ) {

      com.viewscenes.device.tv.MsgQualityRealtimeQueryRes.Quality qi = (com.novel_tongfang.

          mon.device.tv.MsgQualityRealtimeQueryRes.Quality) it.next();

      print(qi.getFreq());

      print(qi.getCheckTime());

      print(qi.getEquCode());

      Collection c = qi.getQualityIndex();

      for (Iterator itt = c.iterator(); itt.hasNext(); ) {

        com.viewscenes.device.tv.MsgQualityRealtimeQueryRes.QualityIndex qii = (com.

            viewscenes.device.tv.MsgQualityRealtimeQueryRes.

            QualityIndex) itt.next();

        print(qii.getDesc());

        print(qii.getType());

        print(qii.getValue());

      }

    }

  }



  private void testQualityReportTaskSet() {

    com.viewscenes.device.tv.MsgQualityReportTaskSetCmd cmd = new com.viewscenes.

        device.tv.MsgQualityReportTaskSetCmd();

    doSetDestCode(cmd);

    cmd.setQualityReportAttrs("110000Z00001", "1", "Del");

    cmd.setChannels(Arrays.asList(new String[] {"CH001", "CH002", }));

    cmd.setCircleTasks(Arrays.asList(new com.viewscenes.device.tv.

                                     MsgQualityReportTaskSetCmd.CircleTask[] {

                                     new com.viewscenes.device.tv.

                                     MsgQualityReportTaskSetCmd.CircleTask(

        "06:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "0"),

                                     new com.viewscenes.device.tv.

                                     MsgQualityReportTaskSetCmd.CircleTask(

        "07:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new com.viewscenes.device.tv.

                                     MsgQualityReportTaskSetCmd.SingleTask[] {

                                     new com.viewscenes.device.tv.

                                     MsgQualityReportTaskSetCmd.SingleTask(

        "06:08:33", "16:08:33", "0", "01:00:00", "00:00:10"),

                                     new com.viewscenes.device.tv.

                                     MsgQualityReportTaskSetCmd.SingleTask(

        "07:08:33", "16:08:33", "0", "01:00:00", "00:00:10"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testRecordTaskSet() {

    MsgRecordTaskSetCmd cmd = new MsgRecordTaskSetCmd();

    doSetDestCode(cmd);

    cmd.setRecordAttrs("110000N00001", "CODE01", "100", "Set", "352", "288",

                       "25", "700000");

    cmd.setCircleTasks(Arrays.asList(new MsgRecordTaskSetCmd.CircleTask[] {

                                     new MsgRecordTaskSetCmd.CircleTask(

        "06:08:33",

        "16:08:33", "2", "0"),

                                     new MsgRecordTaskSetCmd.CircleTask(

        "07:08:33",

        "16:08:33", "2", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new MsgRecordTaskSetCmd.SingleTask[] {

                                     new MsgRecordTaskSetCmd.SingleTask(

        "06:08:33", "16:08:33", "0"),

                                     new MsgRecordTaskSetCmd.SingleTask(

        "07:08:33", "16:08:33", "0"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testSetAutoRecordChannel() {

    com.viewscenes.device.tv.MsgSetAutoRecordChannelCmd cmd = new com.viewscenes.

        device.tv.MsgSetAutoRecordChannelCmd();

    doSetDestCode(cmd);

    cmd.setChannels("110000N00001",

                    Arrays.asList(new String[] {"CH001", "CH002"}));

    Map m = new HashMap();

    m.put("CH001", "CH002");

    cmd.setModifiedChannels("110000N00001", m,

                            Arrays.asList(new String[] {"CH003", }),

                            Arrays.asList(new String[] {"CH004", }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testSetMonChannel() {

    com.viewscenes.device.tv.MsgSetMonChannelCmd cmd = new com.viewscenes.device.tv.

        MsgSetMonChannelCmd();

    doSetDestCode(cmd);

    cmd.setChannels("110000N00001",

                    Arrays.asList(new String[] {"CH001", "CH002"}));

    Map m = new HashMap();

    m.put("CH001", "CH002");

    cmd.setModifiedChannels("110000N00001", m,

                            Arrays.asList(new String[] {"CH003", }),

                            Arrays.asList(new String[] {"CH004", }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testSpectrumRealtimeScan() {

    com.viewscenes.device.tv.MsgSpectrumRealtimeScanCmd cmd = new com.viewscenes.

        device.tv.MsgSpectrumRealtimeScanCmd();

    doSetDestCode(cmd);

    cmd.setParams("110000Z00001", "112.25", "120.25", "0.1");

    com.viewscenes.device.tv.MsgSpectrumRealtimeScanRes res = new com.viewscenes.

        device.tv.MsgSpectrumRealtimeScanRes();

    doTest(cmd, res);

    print(res.getEquCode());

    print(res.getChannelSpectrums());

  }



  private void testSpectrumScanQuery() {

    MsgSpectrumScanQueryCmd cmd = new MsgSpectrumScanQueryCmd();

    doSetDestCode(cmd);

    cmd.setParams("110000Z00001", "2002-09-01 10:00:00", "2002-09-02 10:00:00",

                  "1");

    MsgSpectrumScanQueryRes res = new MsgSpectrumScanQueryRes();

    doTest(cmd, res);

    print(res.getEquCode());

    print(res.getTaskID());

    Collection c = res.getChannelSpectrums();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgSpectrumScanQueryRes.SpectrumScan qi = (MsgSpectrumScanQueryRes.

                                                 SpectrumScan)

          it.next();

      print(qi.getScanTime());

      print(qi.getChannelSpectrums());

    }

  }



  private void testSpectrumScanTaskSet() {

    MsgSpectrumScanTaskSetCmd cmd = new MsgSpectrumScanTaskSetCmd();

    doSetDestCode(cmd);

    cmd.setScanAttrs("110000N00001", "100", "Set", "112.25", "120.25", "0.1",

                     "0");

    cmd.setCircleTasks(Arrays.asList(new MsgSpectrumScanTaskSetCmd.CircleTask[] {

                                     new MsgSpectrumScanTaskSetCmd.CircleTask(

        "06:08:33",

        "0", "0"),

                                     new MsgSpectrumScanTaskSetCmd.CircleTask(

        "07:08:33",

        "0", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new MsgSpectrumScanTaskSetCmd.SingleTask[] {

                                     new MsgSpectrumScanTaskSetCmd.SingleTask(

        "06:08:33", "0"),

                                     new MsgSpectrumScanTaskSetCmd.SingleTask(

        "07:08:33", "0"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testStreamRealtimeQuery() {

    com.viewscenes.device.tv.MsgStreamRealtimeQueryCmd cmd = new com.novel_tongfang.mon.

        device.tv.MsgStreamRealtimeQueryCmd();

    doSetDestCode(cmd);

    cmd.setEqus(Arrays.asList(new com.novel_tongfang.mon.device.tv.MsgStreamRealtimeQueryCmd.

                              RealtimeStream[] {

                              new com.novel_tongfang.mon.device.tv.MsgStreamRealtimeQueryCmd.

                              RealtimeStream(

        "110000N00001", "", "112.25", "352", "288", "25", "700000", "6870", "QAM64", "10", "20",

        "30", "301", "302"),

    }));

    MsgDefaultStreamQueryRes res = new MsgDefaultStreamQueryRes();

    doTest(cmd, res);

    print(res.getUrls());

  }



  private void testStreamRoundQuery() {

    MsgStreamRoundQueryCmd cmd = new MsgStreamRoundQueryCmd();

    doSetDestCode(cmd);

    cmd.setEqus(Arrays.asList(new MsgStreamRoundQueryCmd.RoundStream[] {

                              new MsgStreamRoundQueryCmd.RoundStream(

        "110000N00001", "", "112.25", "352", "288", "25",

        "700000",

        Arrays.asList(new MsgStreamRoundQueryCmd.ChannelSet[] {

                      new MsgStreamRoundQueryCmd.ChannelSet("", "", "", "", "", "", "", ""),

                      new MsgStreamRoundQueryCmd.ChannelSet("", "", "", "", "", "", "", ""),

    })),

    }));

    MsgDefaultStreamQueryRes res = new MsgDefaultStreamQueryRes();

    doTest(cmd, res);

    print(res.getUrls());

  }



  private void testStreamSimpleQuery() {

    com.novel_tongfang.mon.device.tv.MsgStreamSimpleQueryCmd cmd = new com.novel_tongfang.mon.

        device.tv.MsgStreamSimpleQueryCmd();

    doSetDestCode(cmd);

    cmd.setEqus(Arrays.asList(new com.novel_tongfang.mon.device.tv.MsgStreamSimpleQueryCmd.

                              SimpleStream[] {

                              new com.novel_tongfang.mon.device.tv.MsgStreamSimpleQueryCmd.

                              SimpleStream(

        "110000N00001", "CODE01", "2002-08-17 15:30:00", "2002-08-18 15:30:00",

        "", "", "", ""),

    }));

    MsgDefaultStreamQueryRes res = new MsgDefaultStreamQueryRes();

    doTest(cmd, res);

    print(res.getUrls());

  }



  private void testTaskRecordQuery() {

    MsgTaskRecordQueryCmd cmd = new MsgTaskRecordQueryCmd();

    doSetDestCode(cmd);

    cmd.setChannels(Arrays.asList(new MsgTaskRecordQueryCmd.TaskRecord[] {

                                  new MsgTaskRecordQueryCmd.TaskRecord(

        "110000N00001", "100", "2002-09-01 10:00:00", "2002-09-02 10:00:00"),

    }));

    MsgTaskRecordQueryRes res = new MsgTaskRecordQueryRes();

    doTest(cmd, res);

    Collection c = res.getTaskRecords();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgTaskRecordQueryRes.TaskRecord qi = (MsgTaskRecordQueryRes.TaskRecord)

          it.next();

      print(qi.getChannelCode());

      print(qi.getEquCode());

      print(qi.getIndex());

      print(qi.getTaskID());

      Collection cc = qi.getRecords();

      for (Iterator ccit = cc.iterator(); ccit.hasNext(); ) {

        MsgTaskRecordQueryRes.Record r = (MsgTaskRecordQueryRes.Record) ccit.

            next();

        print(r.getEndDateTime());

        print(r.getExpireDays());

        print(r.getRecordID());

        print(r.getSize());

        print(r.getStartDateTime());

        print(r.getURL());

      }

    }

  }



  private void testRTaskRecordDelete() {

    MsgRTaskRecordDeleteCmd cmd = new MsgRTaskRecordDeleteCmd();

    doSetRecordDestCode(cmd);

    cmd.setTask("110000J00001", "100", Arrays.asList(new String[] {"222", "333", }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testRTaskRecordQuery() {

    MsgRTaskRecordQueryCmd cmd = new MsgRTaskRecordQueryCmd();

    doSetRecordDestCode(cmd);

    cmd.setTask("110000J00001", "100", "2000-01-01 10:00:00", "2005-12-31 10:00:00");

    MsgRTaskRecordQueryRes res = new MsgRTaskRecordQueryRes();

    doTest(cmd, res);

    MsgRTaskRecordQueryRes.TaskRecord qi = res.getTaskRecords();

    print(qi.getURL());

    print(qi.getTaskID());

    Collection cc = qi.getRecords();

    for (Iterator ccit = cc.iterator(); ccit.hasNext(); ) {

      MsgRTaskRecordQueryRes.Record r = (MsgRTaskRecordQueryRes.Record) ccit.

          next();

      print(r.getEndDateTime());

      print(r.getExpireDays());

      print(r.getRecordID());

      print(r.getSize());

      print(r.getStartDateTime());

      print(r.getURL());

    }

  }



  private void testRTaskRecordSet() {

    MsgRTaskRecordSetCmd cmd = new MsgRTaskRecordSetCmd();

    doSetRecordDestCode(cmd);

    cmd.setRecordAttrs("110000J00001", "100", "Set", "real://192.168.6.201:5050/0");

    cmd.setCircleTasks(Arrays.asList(new MsgRTaskRecordSetCmd.CircleTask[] {

                                     new MsgRTaskRecordSetCmd.CircleTask(

        "06:08:33",

        "16:08:33", "2", "0"),

                                     new MsgRTaskRecordSetCmd.CircleTask(

        "07:08:33",

        "16:08:33", "2", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new MsgRTaskRecordSetCmd.SingleTask[] {

                                     new MsgRTaskRecordSetCmd.SingleTask(

        "06:08:33", "16:08:33", "0"),

                                     new MsgRTaskRecordSetCmd.SingleTask(

        "07:08:33", "16:08:33", "0"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testVideoMatrixSet() {

    MsgVideoMatrixSetCmd cmd = new MsgVideoMatrixSetCmd();

    doSetMatrixDestCode(cmd);

    cmd.setMatrix("1", "");

    doTest(cmd, new MsgDefaultRes());

  }



  private void testBQualityAlarmHistoryQuery() {

    MsgQualityAlarmHistoryQueryCmd cmd = new MsgQualityAlarmHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setQualityAlarmQueryParams("110000R00001", "1", "828", "2002-08-17 15:30:00",

                                   "2003-08-17 15:30:00");

    MsgQualityAlarmHistoryQueryRes res = new MsgQualityAlarmHistoryQueryRes();

    doTest(cmd, res);

    Collection c = res.getQualityAlarms();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgQualityAlarmHistoryQueryRes.QualityAlarm qa = (MsgQualityAlarmHistoryQueryRes.QualityAlarm)

          it.next();

      print(qa.getAlarmID());

      print(qa.getBand());

      print(qa.getFreq());

      print(qa.getCheckTime());

      print(qa.getDesc());

      print(qa.getEquCode());

      print(qa.getMode());

      print(qa.getReason());

      print(qa.getType());

      print(qa.getAlarmParams());

    }

  }



  private void testBQualityAlarmParamSet() {

    com.novel_tongfang.mon.device.radio.MsgQualityAlarmParamSetCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgQualityAlarmParamSetCmd();

    doSetRadioDestCode(cmd);

    cmd.setChannel("110000R00001", "1", "828");

    Collection c = new ArrayList();

    c.add(new com.novel_tongfang.mon.device.radio.MsgQualityAlarmParamSetCmd.QualityAlarmParam("1",

        "Level", "3.4", "4.5", "2", ""));

    c.add(new com.novel_tongfang.mon.device.radio.MsgQualityAlarmParamSetCmd.QualityAlarmParam("2",

        "ModulationRate", "123.4", "134.5", "", "10"));

    c.add(new com.novel_tongfang.mon.device.radio.MsgQualityAlarmParamSetCmd.QualityAlarmParam("3",

        "Offset", "123.4", "134.5", "", "4"));

    cmd.setQualityAlarmParams(c);

    doTest(cmd, new MsgDefaultRes());

  }



  private void testBQualityHistoryQuery() {

    MsgQualityHistoryQueryCmd cmd = new MsgQualityHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setQualityIndexQueryParams("110000R00001", "1", "828", "100",

                                   "2002-08-17 15:30:00", "2003-08-17 15:30:00");

    MsgQualityHistoryQueryRes res = new MsgQualityHistoryQueryRes();

    doTest(cmd, res);

    print(res.getTaskID());

    print(res.getEquCode());

    Collection c = res.getQualityReport();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgQualityHistoryQueryRes.QualityReport qr = (MsgQualityHistoryQueryRes.

          QualityReport) it.next();

      print(qr.getBand());

      print(qr.getCheckTime());

      print(qr.getFreq());

      Collection qic = qr.getQualityIndex();

      for (Iterator qictcct = qic.iterator(); qictcct.hasNext(); ) {

        MsgQualityHistoryQueryRes.QualityIndex qi = (MsgQualityHistoryQueryRes.QualityIndex)

            qictcct.next();

        print(qi.getAbnormityLength());

        print(qi.getAbnormityRate());

        print(qi.getDesc());

        print(qi.getType());

        print(qi.getValue());

      }

    }

  }



  private void testBQualityRealtimeQuery() {

    com.novel_tongfang.mon.device.radio.MsgQualityRealtimeQueryCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgQualityRealtimeQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setEquCode("110000R00001", "1", "828", "00:00:15");

    cmd.setQueryTypes(Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                    MsgQualityRealtimeQueryCmd.QueryTypes[] {

                                    new com.novel_tongfang.mon.device.radio.

                                    MsgQualityRealtimeQueryCmd.QueryTypes(

        "1", "图像电平"),

                                    new com.novel_tongfang.mon.device.radio.

                                    MsgQualityRealtimeQueryCmd.QueryTypes(

        "2", "伴音电平"),

                                    new com.novel_tongfang.mon.device.radio.

                                    MsgQualityRealtimeQueryCmd.QueryTypes(

        "3", "图像载波与伴音载波的电平差"),

                                    new com.novel_tongfang.mon.device.radio.

                                    MsgQualityRealtimeQueryCmd.QueryTypes(

        "4", "载噪比"),

                                    new com.novel_tongfang.mon.device.radio.

                                    MsgQualityRealtimeQueryCmd.QueryTypes(

        "5", "载频频偏"),

    }));



    com.novel_tongfang.mon.device.radio.MsgQualityRealtimeQueryRes res = new com.novel_tongfang.mon.

        device.radio.MsgQualityRealtimeQueryRes();

    doTest(cmd, res);

    Collection q = res.getQuality();

    for (Iterator qit = q.iterator(); qit.hasNext(); ) {

      com.novel_tongfang.mon.device.radio.MsgQualityRealtimeQueryRes.Quality qq = (com.

          novel_tongfang.mon.device.radio.MsgQualityRealtimeQueryRes.Quality) qit.next();

      print(qq.getFreq());

      print(qq.getCheckTime());

      print(qq.getEquCode());

      Collection c = qq.getQualityRealtime();

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        com.novel_tongfang.mon.device.radio.MsgQualityRealtimeQueryRes.QualityRealtime qi = (

            com.novel_tongfang.mon.device.radio.MsgQualityRealtimeQueryRes.QualityRealtime)

            it.next();

        print(qi.getDesc());

        print(qi.getType());

        print(qi.getValue());

        print(qi.getAbnormityLength());

        print(qi.getAbnormityRate());

      }

    }

  }



  private void testBQualityReportTaskSet() {

    com.novel_tongfang.mon.device.radio.MsgQualityReportTaskSetCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgQualityReportTaskSetCmd();

    doSetRadioDestCode(cmd);

    cmd.setQualityReportAttrs("110000R00001", "1", "Set", "1");

    cmd.setChannels(Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                  MsgQualityReportTaskSetCmd.Channel[] {

                                  new com.novel_tongfang.mon.device.radio.

                                  MsgQualityReportTaskSetCmd.Channel("123.5", "1"),

                                  new com.novel_tongfang.mon.device.radio.

                                  MsgQualityReportTaskSetCmd.Channel("300.0", "0"),

    }));

    cmd.setCircleTasks(Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                     MsgQualityReportTaskSetCmd.CircleTask[] {

                                     new com.novel_tongfang.mon.device.radio.

                                     MsgQualityReportTaskSetCmd.CircleTask(

        "06:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "00:00:15", "0"),

                                     new com.novel_tongfang.mon.device.radio.

                                     MsgQualityReportTaskSetCmd.CircleTask(

        "07:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "00:00:15", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                     MsgQualityReportTaskSetCmd.SingleTask[] {

                                     new com.novel_tongfang.mon.device.radio.

                                     MsgQualityReportTaskSetCmd.SingleTask(

        "06:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "00:00:15"),

                                     new com.novel_tongfang.mon.device.radio.

                                     MsgQualityReportTaskSetCmd.SingleTask(

        "07:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "00:00:15"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testBSpectrumHistoryQuery() {

    MsgSpectrumHistoryQueryCmd cmd = new MsgSpectrumHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("110000R00001", "2002-09-01 10:00:00", "2003-09-02 10:00:00", "1");

    MsgSpectrumHistoryQueryRes res = new MsgSpectrumHistoryQueryRes();

    doTest(cmd, res);

    print(res.getEquCode());

    print(res.getTaskID());

    Collection c = res.getSpectrumScan();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgSpectrumHistoryQueryRes.SpectrumScan ss = (MsgSpectrumHistoryQueryRes.

          SpectrumScan) it.next();

      print(ss.getScanTime());

      Collection ssc = ss.getScanResults();

      for (Iterator sscit = ssc.iterator(); sscit.hasNext(); ) {

        MsgSpectrumHistoryQueryRes.ScanResult sr = (MsgSpectrumHistoryQueryRes.ScanResult) sscit.

            next();

        print(sr.getBand());

        print(sr.getFreq());

        Collection qis = sr.getQualityIndex();

        for (Iterator qisit = qis.iterator(); qisit.hasNext(); ) {

          MsgSpectrumHistoryQueryRes.QualityIndex qi = (MsgSpectrumHistoryQueryRes.QualityIndex)

              qisit.next();

          print(qi.getAbnormityLength());

          print(qi.getAbnormityRate());

          print(qi.getDesc());

          print(qi.getType());

          print(qi.getValue());

        }

      }

    }

  }



  private void testBSpectrumRealtimeScan() {

    com.novel_tongfang.mon.device.radio.MsgSpectrumRealtimeScanCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgSpectrumRealtimeScanCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("110000R00001", "0", "252.25", "272.25", "0.2");

    com.novel_tongfang.mon.device.radio.MsgSpectrumRealtimeScanRes res = new com.novel_tongfang.mon.

        device.radio.MsgSpectrumRealtimeScanRes();

    doTest(cmd, res);

    print(res.getEquCode());

    Collection srs = res.getScanResults();

    for (Iterator srsit = srs.iterator(); srsit.hasNext(); ) {

      com.novel_tongfang.mon.device.radio.MsgSpectrumRealtimeScanRes.ScanResult sr = (com.

          novel_tongfang.mon.device.radio.MsgSpectrumRealtimeScanRes.ScanResult) srsit.next();

      print(sr.getBand());

      print(sr.getFreq());

      Collection qis = sr.getQueryIndexs();

      for (Iterator qisit = qis.iterator(); qisit.hasNext(); ) {

        com.novel_tongfang.mon.device.radio.MsgSpectrumRealtimeScanRes.QualityIndex qi = (com.

            novel_tongfang.mon.device.radio.MsgSpectrumRealtimeScanRes.QualityIndex) qisit.next();

        print(qi.getAbnormityLength());

        print(qi.getAbnormityRate());

        print(qi.getDesc());

        print(qi.getType());

        print(qi.getValue());

      }

    }

  }



  private void testBSpectrumTaskSet() {

    MsgSpectrumTaskSetCmd cmd = new MsgSpectrumTaskSetCmd();

    doSetRadioDestCode(cmd);

    cmd.setAttrs("110000R00001", "1", "Set", "1", "1", "828", "927", "0.1");

    cmd.setCircleTasks(Arrays.asList(new MsgSpectrumTaskSetCmd.CircleTask[] {

                                     new MsgSpectrumTaskSetCmd.CircleTask(

        "06:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "0"),

                                     new MsgSpectrumTaskSetCmd.CircleTask(

        "07:08:33", "16:08:33", "0", "01:00:00", "00:00:10", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new MsgSpectrumTaskSetCmd.SingleTask[] {

                                     new MsgSpectrumTaskSetCmd.SingleTask(

        "06:08:33", "16:08:33", "0", "01:00:00", "00:00:10"),

                                     new MsgSpectrumTaskSetCmd.SingleTask(

        "07:08:33", "16:08:33", "0", "01:00:00", "00:00:10"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testBStreamHistoryQuery() {

    MsgStreamHistoryQueryCmd cmd = new MsgStreamHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setEqus(Arrays.asList(new MsgStreamHistoryQueryCmd.SimpleStream[] {

                              new MsgStreamHistoryQueryCmd.SimpleStream(

        "110000R00001", "100", "2002-08-17 15:30:00", "2003-08-18 15:30:00"),

    }));

    MsgStreamHistoryQueryRes res = new MsgStreamHistoryQueryRes();

    doTest(cmd, res);

    Collection c = res.getTaskRecords();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgStreamHistoryQueryRes.TaskRecord qi = (MsgStreamHistoryQueryRes.TaskRecord)

          it.next();

      print(qi.getBand());

      print(qi.getFreq());

      print(qi.getEquCode());

      print(qi.getIndex());

      print(qi.getTaskID());

      Collection cc = qi.getRecords();

      for (Iterator ccit = cc.iterator(); ccit.hasNext(); ) {

        MsgStreamHistoryQueryRes.Record r = (MsgStreamHistoryQueryRes.Record) ccit.

            next();

        print(r.getEndDateTime());

        print(r.getExpireDays());

        print(r.getRecordID());

        print(r.getSize());

        print(r.getStartDateTime());

        print(r.getURL());

      }

    }

  }



  private void testBStreamRealtimeQuery() {

    com.novel_tongfang.mon.device.radio.MsgStreamRealtimeQueryCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgStreamRealtimeQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setEqus(Arrays.asList(new com.novel_tongfang.mon.device.radio.MsgStreamRealtimeQueryCmd.

                              RealtimeStream[] {

                              new com.novel_tongfang.mon.device.radio.MsgStreamRealtimeQueryCmd.

                              RealtimeStream(

        "110000R00001", "", "1", "352.8", "32000", "MPEG2"),

    }));

    com.novel_tongfang.mon.device.radio.MsgStreamRealtimeQueryRes res = new com.novel_tongfang.mon.

        device.radio.MsgStreamRealtimeQueryRes();

    doTest(cmd, res);

    print(res.getUrls());

  }



  private void testBStreamTaskSet() {

    MsgStreamTaskSetCmd cmd = new MsgStreamTaskSetCmd();

    doSetRadioDestCode(cmd);

    cmd.setAttrs("110000R00001", "100", "Set", "MPEG2", "32000");

    cmd.setTaskChannels(Arrays.asList(new MsgStreamTaskSetCmd.TaskChannel[] {

                                     new MsgStreamTaskSetCmd.TaskChannel(

         "2", "97400"),

                                     new MsgStreamTaskSetCmd.TaskChannel(

         "1", "876"),

    }));


    cmd.setCircleTasks(Arrays.asList(new MsgStreamTaskSetCmd.CircleTask[] {

                                     new MsgStreamTaskSetCmd.CircleTask(

        "06:08:33", "16:08:33", "00:05:00", "2", "0"),

                                     new MsgStreamTaskSetCmd.CircleTask(

        "07:08:33", "16:08:33", "00:05:00","3", "All"),

    }));

    cmd.setSingleTasks(Arrays.asList(new MsgStreamTaskSetCmd.SingleTask[] {

                                     new MsgStreamTaskSetCmd.SingleTask(

        "06:08:33", "16:08:33", "00:05:00", "2"),

                                     new MsgStreamTaskSetCmd.SingleTask(

        "07:08:33", "16:08:33", "00:05:00", "2"),

    }));



    doTest(cmd, new MsgDefaultRes());

  }



  private void testBEquipmentAlarmHistoryQuery() {

    MsgEquipmentAlarmHistoryQueryCmd cmd = new MsgEquipmentAlarmHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setEquipmentAlarmQueryParams("2002-08-17 15:30:00", "2003-08-17 15:30:00");

    MsgEquipmentAlarmHistoryQueryRes res = new MsgEquipmentAlarmHistoryQueryRes();

    doTest(cmd, res);

    Collection eas = res.getEquipmentAlarms();

    for (Iterator easit = eas.iterator(); easit.hasNext(); ) {

      MsgEquipmentAlarmHistoryQueryRes.EquipmentAlarm ea = (MsgEquipmentAlarmHistoryQueryRes.

          EquipmentAlarm) easit.next();

      print(ea.getAlarmID());

      print(ea.getAlarmParams());

      print(ea.getCheckTime());

      print(ea.getDesc());

      print(ea.getDeviceCode());

      print(ea.getMode());

      print(ea.getReason());

      print(ea.getType());

    }

  }



  private void testBEquipmentAlarmParamSet() {

    MsgEquipmentAlarmParamSetCmd cmd = new MsgEquipmentAlarmParamSetCmd();

    doSetRadioDestCode(cmd);

    Collection c = new ArrayList();

    c.add(new MsgEquipmentAlarmParamSetCmd.EquipmentAlarmParam("1",

        "Level", "3.4", "4.5", "2"));

    c.add(new MsgEquipmentAlarmParamSetCmd.EquipmentAlarmParam("2",

        "ModulationRate", "123.4", "134.5", "10"));

    c.add(new MsgEquipmentAlarmParamSetCmd.EquipmentAlarmParam("3",

        "Offset", "123.4", "134.5", "4"));

    cmd.setEquipmentAlarmParams(c);

    doTest(cmd, new MsgDefaultRes());

  }



  private void testBEquipmentStatusHistoryQuery() {

    MsgEquipmentStatusHistoryQueryCmd cmd = new MsgEquipmentStatusHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("2002-09-21 23:23:33", "2003-09-22 00:00:01",

                  Arrays.asList(new String[] {"150000X02", "150000X03", }));

    MsgEquipmentStatusHistoryQueryRes res = new MsgEquipmentStatusHistoryQueryRes();

    doTest(cmd, res);

    print(res.getCheckDateTime());

    Collection c = res.getEquipments();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgEquipmentStatusHistoryQueryRes.Equipment e = (

          MsgEquipmentStatusHistoryQueryRes.Equipment) it.next();

      print(e.getDesc());

      print(e.getDeviceCode());

      print(e.getEquipmentType());

      print(e.getEquStatus());

      Collection ps = e.getParameters();

      if (ps != null) {

        for (Iterator psit = ps.iterator(); psit.hasNext(); ) {

          MsgEquipmentStatusHistoryQueryRes.Parameter p = (MsgEquipmentStatusHistoryQueryRes.

              Parameter) psit.next();

          print(p.getAbnormityLength());

          print(p.getParammeterType());

          print(p.getValue());

        }

      }

    }

  }



  private void testBEquipmentStatusRealtimeQuery() {

    MsgEquipmentStatusRealtimeQueryCmd cmd = new MsgEquipmentStatusRealtimeQueryCmd();

    doSetRadioDestCode(cmd);

    MsgEquipmentStatusRealtimeQueryRes res = new MsgEquipmentStatusRealtimeQueryRes();

    doTest(cmd, res);

    print(res.getCheckDateTime());

    Collection c = res.getEquipments();

    for (Iterator it = c.iterator(); it.hasNext(); ) {

      MsgEquipmentStatusHistoryQueryRes.Equipment e = (

          MsgEquipmentStatusHistoryQueryRes.Equipment) it.next();

      print(e.getDesc());

      print(e.getDeviceCode());

      print(e.getEquipmentType());

      print(e.getEquStatus());

      Collection ps = e.getParameters();

      if (ps != null) {

        for (Iterator psit = ps.iterator(); psit.hasNext(); ) {

          MsgEquipmentStatusHistoryQueryRes.Parameter p = (MsgEquipmentStatusHistoryQueryRes.

              Parameter) psit.next();

          print(p.getAbnormityLength());

          print(p.getParammeterType());

          print(p.getValue());

        }

      }

    }

  }



  private void testBEquipmentInitParamSet() {

    MsgEquipmentInitParamSetCmd cmd = new MsgEquipmentInitParamSetCmd();

    doSetRadioDestCode(cmd);

    Map m1 = new LinkedHashMap(2);

    m1.put("q", "10");

    m1.put("p", "100");

    Map m2 = new LinkedHashMap(2);

    m2.put("a", "10");

    m2.put("b", "100");

    cmd.setEquipmentInitParams(Arrays.asList(new MsgEquipmentInitParamSetCmd.EquipmentInitParam[] {

                                             new MsgEquipmentInitParamSetCmd.EquipmentInitParam(

        "150000X01", m1),

                                             new MsgEquipmentInitParamSetCmd.EquipmentInitParam(

        "150000X02", m2),

    }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testBEquipmentLogHistoryQuery() {

    MsgEquipmentLogHistoryQueryCmd cmd = new MsgEquipmentLogHistoryQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("2002-08-17 15:30:00", "2003-08-17 15:30:00");

    MsgEquipmentLogHistoryQueryRes res = new MsgEquipmentLogHistoryQueryRes();

    doTest(cmd, res);

    Collection c = res.getEquipmentLogs();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgEquipmentLogHistoryQueryRes.EquipmentLog el = (MsgEquipmentLogHistoryQueryRes.

            EquipmentLog) it.next();

        print(el.getDateTime());

        print(el.getDesc());

        print(el.getName());

      }

    }

  }



  private void testBTaskQuery() {

    MsgTaskQueryCmd cmd = new MsgTaskQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("1", "828", "2002-08-17 15:30:00", "2002-08-17 15:30:00", "All", "0");

    MsgTaskQueryRes res = new MsgTaskQueryRes();

    doTest(cmd, res);

    Collection qts = res.getQualityTask();

    for (Iterator qtsit = qts.iterator(); qtsit.hasNext(); ) {

      MsgTaskQueryRes.QualityTask qt = (MsgTaskQueryRes.QualityTask) qtsit.next();

      if (qt != null) {

        print(qt.equCode);

        print(qt.srcCode);

        print(qt.taskID);

        print(qt.taskType);

        Collection c = qt.channels;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.QualityTask.Channel cn = (MsgTaskQueryRes.QualityTask.Channel) it.next();

            print(cn.band);

            print(cn.freq);

          }

        }

        c = qt.circleTasks;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.QualityTask.CircleTask cn = (MsgTaskQueryRes.QualityTask.CircleTask) it.

                next();

            print(cn.checkInterval);

            print(cn.dayofWeek);

            print(cn.endTime);

            print(cn.reportInterval);

            print(cn.reportMode);

            print(cn.sampleLength);

            print(cn.startTime);

          }

        }

        c = qt.singleTasks;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.QualityTask.SingleTask cn = (MsgTaskQueryRes.QualityTask.SingleTask) it.

                next();

            print(cn.checkInterval);

            print(cn.endTime);

            print(cn.reportInterval);

            print(cn.reportMode);

            print(cn.sampleLength);

            print(cn.startTime);

          }

        }

      }

    }

    Collection ssts = res.getSpectrumScanTask();

    for (Iterator sstsit = ssts.iterator(); sstsit.hasNext(); ) {

      MsgTaskQueryRes.SpectrumScanTask sst = (MsgTaskQueryRes.SpectrumScanTask) sstsit.next();

      if (sst != null) {

        print(sst.equCode);

        print(sst.taskID);

        print(sst.band);

        print(sst.endFreq);

        print(sst.startFreq);

        print(sst.stepFreq);

        print(sst.taskPriority);

        Collection c = sst.circleTasks;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.SpectrumScanTask.CircleTask cn = (MsgTaskQueryRes.SpectrumScanTask.

                CircleTask) it.next();

            print(cn.checkInterval);

            print(cn.dayofWeek);

            print(cn.endTime);

            print(cn.reportInterval);

            print(cn.reportMode);

            print(cn.startTime);

          }

        }

        c = sst.singleTasks;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.SpectrumScanTask.SingleTask cn = (MsgTaskQueryRes.SpectrumScanTask.

                SingleTask) it.next();

            print(cn.checkInterval);

            print(cn.endTime);

            print(cn.reportInterval);

            print(cn.reportMode);

            print(cn.startTime);

          }

        }

      }

    }

    Collection sts = res.getStreamTask();

    for (Iterator stsit = sts.iterator(); stsit.hasNext(); ) {

      MsgTaskQueryRes.StreamTask st = (MsgTaskQueryRes.StreamTask) stsit.next();

      if (st != null) {

        print(st.equCode);

        print(st.taskID);

        print(st.band);

        print(st.bps);

        print(st.freq);

        Collection c = st.circleTasks;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.StreamTask.CircleTask cn = (MsgTaskQueryRes.StreamTask.CircleTask) it.

                next();

            print(cn.expireDays);

            print(cn.dayofWeek);

            print(cn.endTime);

            print(cn.startTime);

          }

        }

        c = st.singleTasks;

        if (c != null) {

          for (Iterator it = c.iterator(); it.hasNext(); ) {

            MsgTaskQueryRes.StreamTask.SingleTask cn = (MsgTaskQueryRes.StreamTask.SingleTask) it.

                next();

            print(cn.expireDays);

            print(cn.endTime);

            print(cn.startTime);

          }

        }

      }

    }

  }



  private void testBFileQuery() {

    MsgFileQueryCmd cmd = new MsgFileQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("1", "2002-08-17", "2003-08-17", "100");

    cmd.setFrequencys(Arrays.asList(new MsgFileQueryCmd.Frequency[] {

                                    new MsgFileQueryCmd.Frequency("1", "573"),

                                    new MsgFileQueryCmd.Frequency("1", "570"),

    }));

    MsgFileQueryRes res = new MsgFileQueryRes();

    doTest(cmd, res);

    Collection c = res.getResultInfos();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgFileQueryRes.ResultInfo el = (MsgFileQueryRes.ResultInfo) it.next();

        print(el.getBand());

        print(el.getEndDateTime());

        print(el.getFileURL());

        print(el.getFreq());

        print(el.getResultType());

        print(el.getStartDateTime());

        print(el.getTaskID());

      }

    }

  }



  private void testBProgramCommand() {

    MsgProgramCommandCmd cmd = new MsgProgramCommandCmd();

    doSetRadioDestCode(cmd);

    cmd.setParams("Novel-Tongfang", Arrays.asList(new String[] {

                                                  "ftp://a/a.exe",

    }));

    MsgProgramCommandRes res = new MsgProgramCommandRes();

    doTest(cmd, res);

    Collection c = res.getProgramInfos();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgProgramCommandRes.ProgramInfo el = (MsgProgramCommandRes.ProgramInfo) it.next();

        print(el.getCompany());

        print(el.getName());

        print(el.getVersion());

      }

    }

  }



  private void test3HeadendStatusQuery() {

    MsgHeadendStatusQueryCmd cmd = new MsgHeadendStatusQueryCmd();

    doSetDestCode(cmd);

    cmd.setReportInterval("0:5:0");

    MsgHeadendStatusQueryRes res = new MsgHeadendStatusQueryRes();

    doTest(cmd, res);

    print(res.getDesc());

    print(res.getLastOperatorID());

    print(res.getType());

    Collection c = res.getEqus();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgHeadendStatusQueryRes.Equipment el = (MsgHeadendStatusQueryRes.Equipment) it.next();

        print(el.getCode());

        print(el.getDesc());

        print(el.getType());

      }

    }

  }



  private void test3ChannelQuery() {

    MsgChannelQueryCmd cmd = new MsgChannelQueryCmd();

    doSetSecurityDestCode(cmd);

    MsgChannelQueryRes res = new MsgChannelQueryRes();

    doTest(cmd, res);

    Collection c = res.getChannels();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgChannelQueryRes.Channel el = (MsgChannelQueryRes.Channel) it.next();

        print(el.getCode());

        print(el.getFreq());

        print(el.getName());

      }

    }

  }



  private void test3HeadendAlarmQuery() {

    MsgHeadendAlarmQueryCmd cmd = new MsgHeadendAlarmQueryCmd();

    doSetSecurityDestCode(cmd);

    cmd.setParams("2002-08-17 15:30:00", "2003-08-17 15:30:00");

    MsgHeadendAlarmQueryRes res = new MsgHeadendAlarmQueryRes();

    doTest(cmd, res);

    Collection c = res.getAlarms();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgHeadendAlarmQueryRes.Alarm el = (MsgHeadendAlarmQueryRes.Alarm) it.next();

        print(el.getAlarmID());

        print(el.getCheckTime());

        print(el.getDesc());

        print(el.getMode());

        print(el.getType());

      }

    }

  }



  private void test3HeadendQuery() {

    MsgHeadendQueryCmd cmd = new MsgHeadendQueryCmd();

    doSetSecurityDestCode(cmd);

    MsgHeadendQueryRes res = new MsgHeadendQueryRes();

    doTest(cmd, res);

    MsgHeadendQueryRes.GISInfo gis = res.getGISInfo();

    print(gis.x());

    print(gis.y());

    MsgHeadendQueryRes.Manager c = res.getManager();

    print(c.getAddr());

    print(c.getEmail());

    print(c.getName());

    print(c.getTel());

  }



  private void test3SafeAlarmQuery() {

    MsgSafeAlarmQueryCmd cmd = new MsgSafeAlarmQueryCmd();

    doSetSecurityDestCode(cmd);

    cmd.setParams("110000A00001", "CODE01", "2002-08-17 15:30:00", "2002-08-17 15:30:00");

    MsgSafeAlarmQueryRes res = new MsgSafeAlarmQueryRes();

    doTest(cmd, res);

    Collection c = res.getAlarms();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        MsgSafeAlarmQueryRes.Alarm el = (MsgSafeAlarmQueryRes.Alarm) it.next();

        print(el.getAlarmID());

        print(el.getCheckTime());

        print(el.getDesc());

        print(el.getMode());

        print(el.getType());

        print(el.getChannelCode());

        print(el.getReason());

      }

    }

  }



  private void test3SafeEquQuery() {

    MsgSafeEquQueryCmd cmd = new MsgSafeEquQueryCmd();

    doSetSecurityDestCode(cmd);

    MsgSafeEquQueryRes res = new MsgSafeEquQueryRes();

    doTest(cmd, res);

    Collection ses = res.getSafeEquipment();

    for (Iterator it = ses.iterator(); it.hasNext(); ) {

      MsgSafeEquQueryRes.SafeEquipment se = (MsgSafeEquQueryRes.SafeEquipment) it.next();

      print(se.getCode());

      print(se.getInstallDate());

      MsgSafeEquQueryRes.GISInfo gis = se.getGISInfo();

      print(gis.x());

      print(gis.y());

      MsgSafeEquQueryRes.Manager c = se.getManager();

      print(c.getAddr());

      print(c.getEmail());

      print(c.getName());

      print(c.getTel());

    }

  }



  private void testBSetAutoRecordChannel() {

    com.novel_tongfang.mon.device.radio.MsgSetAutoRecordChannelCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgSetAutoRecordChannelCmd();

    doSetRadioDestCode(cmd);

    cmd.setChannels("110000R00001",

                    Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                  MsgSetAutoRecordChannelCmd.Channel[] {

                                  new com.novel_tongfang.mon.device.radio.

                                  MsgSetAutoRecordChannelCmd.Channel("0", "112.25"),

                                  new com.novel_tongfang.mon.device.radio.

                                  MsgSetAutoRecordChannelCmd.Channel("0", "112.25"),

    }));

    Map m = new HashMap();

    m.put(new com.novel_tongfang.mon.device.radio.MsgSetAutoRecordChannelCmd.Channel("0", "120.25"),

          new com.novel_tongfang.mon.device.radio.MsgSetAutoRecordChannelCmd.Channel("0", "112.25"));

    cmd.setModifiedChannels("110000N00001", m,

                            Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                          MsgSetAutoRecordChannelCmd.Channel[] {

                                          new com.novel_tongfang.mon.device.radio.

                                          MsgSetAutoRecordChannelCmd.Channel("0", "112.25"),

                                          new com.novel_tongfang.mon.device.radio.

                                          MsgSetAutoRecordChannelCmd.Channel("0", "112.25"),

    }),

        Arrays.asList(new com.novel_tongfang.mon.device.radio.MsgSetAutoRecordChannelCmd.Channel[] {

                      new com.novel_tongfang.mon.device.radio.MsgSetAutoRecordChannelCmd.Channel(

        "0", "112.25"),

                      new com.novel_tongfang.mon.device.radio.MsgSetAutoRecordChannelCmd.Channel(

        "0", "112.25"),

    }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testBSetMonChannel() {

    com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd cmd = new com.novel_tongfang.mon.device.

        radio.MsgSetMonChannelCmd();

    doSetRadioDestCode(cmd);

    cmd.setChannels("110000R00001",

                    Arrays.asList(new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.

                                  Channel[] {

                                  new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.

                                  Channel("0", "112.25"),

                                  new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.

                                  Channel("0", "112.25"),

    }));

    Map m = new HashMap();

    m.put(new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.Channel("0", "120.25"),

          new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.Channel("0", "112.25"));

    cmd.setModifiedChannels("110000N00001", m,

                            Arrays.asList(new com.novel_tongfang.mon.device.radio.

                                          MsgSetMonChannelCmd.Channel[] {

                                          new com.novel_tongfang.mon.device.radio.

                                          MsgSetMonChannelCmd.Channel("0", "112.25"),

                                          new com.novel_tongfang.mon.device.radio.

                                          MsgSetMonChannelCmd.Channel("0", "112.25"),

    }),

        Arrays.asList(new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.Channel[] {

                      new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.Channel("0",

        "112.25"),

                      new com.novel_tongfang.mon.device.radio.MsgSetMonChannelCmd.Channel("0",

        "112.25"),

    }));

    doTest(cmd, new MsgDefaultRes());

  }



  private void testBStreamSimpleQuery() {

    com.novel_tongfang.mon.device.radio.MsgStreamSimpleQueryCmd cmd = new com.novel_tongfang.mon.

        device.radio.MsgStreamSimpleQueryCmd();

    doSetRadioDestCode(cmd);

    cmd.setEqus(Arrays.asList(new com.novel_tongfang.mon.device.radio.MsgStreamSimpleQueryCmd.

                              SimpleStream[] {

                              new com.novel_tongfang.mon.device.radio.MsgStreamSimpleQueryCmd.

                              SimpleStream(

        "110000R00001", "0", "112.25", "", "2002-08-17 15:30:00", "2002-08-18 15:30:00"),

                              new com.novel_tongfang.mon.device.radio.MsgStreamSimpleQueryCmd.

                              SimpleStream(

        "110000R00001", "0", "112.25", "32000", "", ""),

    }));

    MsgStreamSimpleQueryRes res = new MsgStreamSimpleQueryRes();

    doTest(cmd, res);

    print(res.getUrls());

  }



  private void testParser() {

    final String msg = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?><Msg Version=\"4\" MsgID=\"2\" Type=\"TVMonUp\" DateTime=\"2002-08-17 15:30:00\" SrcCode=\"110000N01\" DstCode=\"110000X01\" ReplyID=\"-1\"><QualityAlarmReport ><QualityAlarm EquCODE=\"110000N00001\" AlarmID=\"1100\" Mode=\"1\" ChannelCODE=\"Z01\" Type=\"11\" Desc=\"无同步报警\" Reason=\"报警原因\" Value=\"10.1\" CheckTime=\"2002-08-17 15:30:00\"></QualityAlarm></QualityAlarmReport></Msg>";

    for (int i = 0; i < 100; i++) {

      new PostHttpThread("http://localhost:8080/servlet/deviceservlet", msg);

      try {

        Thread.sleep(100);

      } catch (InterruptedException ex) {

        print(ex);

      }

    }

  }



  private void testSender() {

    final String pre = "QualityAlarmHistoryReport";

    final String post = ".xml";

    List positions = new ArrayList(10);

    for (int i = 0; i < 10; i++) {

      try {

        URL configFile = TestCase.class.getClassLoader().getResource(pre + i + post);

        BufferedReader in = new BufferedReader(new FileReader(configFile.getPath()));

        StringBuffer msg = new StringBuffer();

        String line = null;

        while ( (line = in.readLine()) != null) {

          msg.append(line);

        }

        positions.add(msg);

      } catch (Exception e) {

        print(e);

      }

    }



    String numberstring = "";

    for(int i = 0; i < 6; i++){

      numberstring += makeHexString(300);

    }

    String rmsg = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?><Msg Version=\"4\" MsgID=\"2\" Type=\"RadioUp\" DateTime=\"2002-08-17 15:30:00\" SrcCode=\"110000R01\" DstCode=\"110000X01\" ReplyID=\"-1\"><QualityAlarmReport>" + numberstring + "<QualityAlarm EquCODE=\"110000R00001\"><AlarmParam name=\"Level\" value=\"1\" /></QualityAlarm></QualityAlarmReport></Msg>";



//    String rmsg = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?><Msg Version=\"4\" MsgID=\"2\" Type=\"RadioUp\" DateTime=\"2002-08-17 15:30:00\" SrcCode=\"110000R01\" DstCode=\"110000X01\" ReplyID=\"-1\"><QualityAlarmReport value1=\"" + numberstring + "\" ><QualityAlarm EquCODE=\"110000R00001\"><AlarmParam name=\"Level\" value=\"1\" /></QualityAlarm></QualityAlarmReport></Msg>";

    for (int i = 0; i < 1; i++) {

      long time = System.currentTimeMillis();

//      print(rmsg);

      new PostHttpThread("http://192.168.6.44:8080/servlet1",

                         "");

      try {

        Thread.sleep(100);

      } catch (InterruptedException ex) {

        print(ex);

      }

    }

  }



  private void testSenderAll() {

    try {

      URL configFile = TestCase.class.getClassLoader().getResource("All.xml");

      BufferedReader in = new BufferedReader(new FileReader(configFile.getPath()));

      StringBuffer msg = new StringBuffer();

      String line = null;

      while ( (line = in.readLine()) != null) {

        msg.append(line);

      }

      for (int i = 0; i < 10; i++) {

        long time = System.currentTimeMillis();

        new PostHttpThread("http://192.168.6.44:8080/servlet/deviceservlet",

                           msg.toString());

        Thread.sleep(1000);

      }

    } catch (Exception e) {

      print(e);

    }

  }



  private void test3ContentEquUsageQuery() {

    MsgContentEquUsageQueryCmd cmd = new MsgContentEquUsageQueryCmd();

    doSetContentDestCode(cmd);

    MsgContentEquUsageQueryRes res = new MsgContentEquUsageQueryRes();

    doTest(cmd, res);

    print(res.getEquCode());

    Collection c = res.getRealtimeVideos();

    if (c != null) {

      for (Iterator it = c.iterator(); it.hasNext(); ) {

        print(it.next());

      }

    }

    MsgContentEquUsageQueryRes.HardDiskRecorder hdr = res.getHardDiskRecorder();

    print(hdr.getBusyChannels());

    print(hdr.getLeftDiskSpace());

    print(hdr.getLeftRecordTime());

    print(hdr.getTotalChannels());

    print(hdr.getTotalDiskSpace());

    print(hdr.getTotalRecordTime());

    MsgContentEquUsageQueryRes.NetworkBandwidth nbw = res.getNetworkBandwidth();

    print(nbw.getBitsReceivedperSecond());

    print(nbw.getBitsSendperSecond());

  }

  public static void main(String argv[]){
    TestCase tc = new TestCase();
    tc.test();
  }

  public void test() {

//    testSenderAll();

//    testSender();

//    testParser();

//    test3ContentEquUsageQuery();

//    test3SafeEquQuery();

//    test3SafeAlarmQuery();

//    test3HeadendQuery();

//    test3HeadendAlarmQuery();

//    test3ChannelQuery();

//    test3HeadendStatusQuery();

//    testChannelScanQuery();            //OK

//    testChannelSet();

//    testQualityAlarmParamSet();        //OK

//    testQualityAlarmQuery();           //没有查到数据

//    testQualityIndexQuery();           //没有查到数据

//    testQualityRealtimeQuery();        //OK

//    testQualityReportTaskSet();        //OK

//    testRecordTaskSet();               //OK

//    testSetAutoRecordChannel();        //OK

//    testSetMonChannel();               //OK

//    testSpectrumRealtimeScan();        //超时(27秒)

//    testSpectrumScanQuery();           //没有查到数据

//    testSpectrumScanTaskSet();         //OK

//    testStreamRealtimeQuery();         //OK

//    testStreamRoundQuery();            //OK

//    testStreamSimpleQuery();           //没有查到数据

//    testTaskRecordQuery();             //没有查到数据

//    testRTaskRecordSet();

//    testRTaskRecordQuery();

//    testRTaskRecordDelete();

//    testVideoMatrixSet();

//    testBQualityAlarmHistoryQuery();   //没有查到数据；超时

//    testBQualityAlarmParamSet();       //内部错误

//    testBQualityHistoryQuery();        //没有查到数据

//    testBQualityRealtimeQuery();

//    testBQualityReportTaskSet();

//    testBSpectrumHistoryQuery();

//    testBSpectrumRealtimeScan();

//    testBSpectrumTaskSet();

//    testBStreamHistoryQuery();

//    testBStreamRealtimeQuery();

    testBStreamTaskSet();

//    testBEquipmentAlarmHistoryQuery();

//    testBEquipmentAlarmParamSet();

//    testBEquipmentStatusHistoryQuery();

//    testBEquipmentStatusRealtimeQuery();

//    testBEquipmentInitParamSet();

//    testBEquipmentLogHistoryQuery();

//    testBTaskQuery();

//    testBFileQuery();

//    testBProgramCommand();

//    testBSetAutoRecordChannel();

//    testBSetMonChannel();

//    testBStreamSimpleQuery();

  }
*/
}
