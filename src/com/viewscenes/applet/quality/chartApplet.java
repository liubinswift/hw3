package com.viewscenes.applet.quality;



import java.applet.Applet;

import java.awt.HeadlessException;

import java.awt.*;

import com.viewscenes.applet.tcp.*;

import java.util.*;


/**

 * <p>Title: ���ⲿ���õ����࣬�̳�ϵͳ��Applet,��ߵ��ô�Applet����ȡ��������

 *    ��ʼ����ͼ��Panel,Ȼ����ػ�ͼ����</p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class chartApplet extends Applet {

  public static String[] TV_INDEX_NAME = {"ͼ���ز���ƽ","�����ز���ƽ","ͼ���ز�������ز��ĵ�ƽ��","�����","��ƵƵƫ","δָ֪��","δָ֪��"};
  public static String[] RADIO_INDEX_NAME = {"�ź��ز���ƽ","���ƶ�(˲ʱֵ)","��Ƶ�źŵ�ƽ","�ź��ز�Ƶ��","г��ʧ��","�����","δָ֪��","δָ֪��"};
  public static String[] TV_INDEX_UNIT = {"dBuv","dBuv","dBuv","dB","KHz","",""};
  public static String[] RADIO_INDEX_UNIT = {"dBuv","","dBuv","MHz","","",""};
  public chartApplet() throws HeadlessException {

  }

  private int            SLEEP_TIME       = 5000;  // Ĭ��ȡ���ݼ��ʱ��

  private initPanelInfo  m_panelInfo      = null;  // ��ʼ����ͼ�������ݽṹ

  private chartPanel     m_panel          = new chartPanel();   // ��ͼ���

  private HashMap        m_isDraw         = new HashMap(); // ����ĳ�����Ƿ���ʾ

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

      setPanelValue(); // ���Ҫ��ʼ����panel�ĸ��ֲ���

      this.setLayout(borderLayout);

      m_panel.InitPanel(m_panelInfo); // ��ʼ��panel

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
        setStatusMsg("ֹͣ����Ƶ�ײ�������");
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
        //�յ�һ�����ݺ�������Ͳ�ѯָ���ѯ��һ������
        //dataThread.bSendCmd = true;
    }
  /**
   * �˽ӿ��ṩscript����ĳ�����Ƿ�Ҫ��ʾ
   * @param type��  Ҫ�����ߵ����ͣ�����Ψһȷ��һ���ߣ�
   *                �ͻ��������е����Ͷ�Ӧ
   * @param isDraw�� �Ƿ���ʾ 1����ʾ��0������ʾ
   */

  public void isDrawLine(String type,String isDraw){

    m_isDraw.put(type,isDraw);

    m_panel.setIsDraw(m_isDraw);

  }



  /**

   * ����Ҫ��ʾ��y�������

   * @param type

   */

  public void setYScale(String type, String yUnit){
    m_panel.setYScale(type, yUnit);
  }

  /**

   * ��õ���Applet���˵ĸ��ֲ�����Ȼ��������ʼ����ͼ��panel

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

      m_panelInfo.setXunit("��");

      m_panelInfo.setYunit("dBuv");

      m_panelInfo.setBlowupX(true);

      m_panelInfo.setBlowupY(false);

    }catch(Exception e){

      e.printStackTrace();

    }

  }

}
