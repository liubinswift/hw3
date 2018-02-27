package com.viewscenes.applet.quality;



import java.applet.Applet;

import java.awt.HeadlessException;

import java.awt.*;

import com.viewscenes.applet.tcp.*;

import java.util.*;


/**

 * <p>Title: 供外部调用的主类，继承系统的Applet,外边调用此Applet后先取出参数，

 *    初始化画图的Panel,然后加载画图窗体</p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class chartApplet extends Applet {

  public static String[] TV_INDEX_NAME = {"图像载波电平","伴音载波电平","图像载波与伴音载波的电平差","载噪比","载频频偏","未知指标","未知指标"};
  public static String[] RADIO_INDEX_NAME = {"信号载波电平","调制度(瞬时值)","音频信号电平","信号载波频率","谐波失真","信噪比","未知指标","未知指标"};
  public static String[] TV_INDEX_UNIT = {"dBuv","dBuv","dBuv","dB","KHz","",""};
  public static String[] RADIO_INDEX_UNIT = {"dBuv","","dBuv","MHz","","",""};
  public chartApplet() throws HeadlessException {

  }

  private int            SLEEP_TIME       = 5000;  // 默认取数据间隔时间

  private initPanelInfo  m_panelInfo      = null;  // 初始化画图面板的数据结构

  private chartPanel     m_panel          = new chartPanel();   // 画图面板

  private HashMap        m_isDraw         = new HashMap(); // 标明某条线是否显示

  GetDataThread dataThread = new GetDataThread();

  BorderLayout borderLayout = new BorderLayout();

    long startTime = -1;
    boolean bTV = true;

  //Initialize the applet

  public void init() {

    try {

      jbInit();

    }

    catch(Exception e) {

      e.printStackTrace();

    }

  }

  //Component initialization

  private void jbInit(){

    try {

      setPanelValue(); // 获得要初始化的panel的各种参数

      this.setLayout(borderLayout);

      m_panel.InitPanel(m_panelInfo); // 初始化panel

      m_panel.m_parent = this;

      this.setSize(m_panelInfo.getWidth(), m_panelInfo.getHight());

      this.add(m_panel, BorderLayout.CENTER);

      m_panel.setIsDraw(m_isDraw);

     //setConnParam("10.1.2.143",8010);
     //setConnParam("192.168.0.110",8010);
     //this.startVideoQuality("112.25");
     //this.startRadioQuality("98.7");
    }

    catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void setConnParam(String ip,int port){
      this.dataThread.ip = ip;
      this.dataThread.port = port;
      dataThread.m_parent = this;
    }

    public void setStatusMsg(String msg){
       // status.m_sMsg = msg;
       // status.repaint();
    }

    public void startVideoQuality(String freq){
        bTV = true;
        byte[] cmd = MonTCPCommand.getVideoQualityCommand(freq);
        dataThread.setCommand(cmd);
        dataThread.start();
    }

    public void startRadioQuality(String freq){
        bTV = false;
        byte[] cmd = MonTCPCommand.getRadioQualityCommand(freq);
        dataThread.setCommand(cmd);
        dataThread.start();
    }


    public void stopQuality(){
        setStatusMsg("停止接收频谱测量数据");
        this.dataThread.bRun = false;
    }

    public void addNewValue(QualityData data){
        if (startTime==-1)
            startTime = data.reportTime;

        for (int i=0;i<data.values.length;i++){
            int value = (data.values[i]+5)/10*10;
            long time = (data.reportTime-startTime)/1000;
            String name = "";
            if (bTV)
                name = TV_INDEX_NAME[i];
            else
                name = RADIO_INDEX_NAME[i];

            String unit = "";
            if (bTV)
                unit = TV_INDEX_UNIT[i];
            else
                unit = RADIO_INDEX_UNIT[i];

            if (bTV&&i==5){
                continue;
            }
            m_panel.addChartData(i+1,""+time,""+(float)value/1000,name,"","0","0","100",unit);
        }
        //收到一条数据后继续发送查询指令，查询下一条数据
        //dataThread.bSendCmd = true;
    }
  /**
   * 此接口提供script设置某条线是否要显示
   * @param type：  要画的线的类型，可以唯一确定一条线，
   *                和画线数据中的类型对应
   * @param isDraw： 是否显示 1：显示，0：不显示
   */

  public void isDrawLine(String type,String isDraw){

    m_isDraw.put(type,isDraw);

    m_panel.setIsDraw(m_isDraw);

  }



  /**

   * 设置要显示的y轴的坐标

   * @param type

   */

  public void setYScale(String type, String yUnit){
    m_panel.setYScale(type, yUnit);
  }

  /**

   * 获得调用Applet传人的各种参数，然后用来初始化画图的panel

   */

  private void setPanelValue(){

    try{

      m_panelInfo = new initPanelInfo();
      m_panelInfo.setHight(475);
      m_panelInfo.setWidth(636);

      m_panelInfo.setXminValue(0);

      m_panelInfo.setXmaxValue(120);

      m_panelInfo.setYminValue(-30);

      m_panelInfo.setYmaxValue(120);

      m_panelInfo.setLevelMin(-30);

      m_panelInfo.setLevelMax(-30);

      m_panelInfo.setType(2);

      m_panelInfo.setXunit("秒");

      m_panelInfo.setYunit("dBuv");

      m_panelInfo.setBlowupX(true);

      m_panelInfo.setBlowupY(false);

    }catch(Exception e){

      e.printStackTrace();

    }

  }

}
