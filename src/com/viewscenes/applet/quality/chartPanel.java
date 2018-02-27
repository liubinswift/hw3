package com.viewscenes.applet.quality;



import javax.swing.JPanel;

import javax.swing.*;

import java.awt.*;

import java.awt.image.*;

import java.util.*;

import java.awt.event.*;

import java.awt.*;

import com.viewscenes.pub.*;

import java.applet.*;

import java.net.*;



/**

 * <p>Title: ���ߵ������ڣ��̳�ϵͳ��JPanel,���е�ͼ�ζ����Ȼ����ڴ��У�

 *          Ȼ�󻭵���panel

 * </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class chartPanel extends JPanel implements MouseListener, MouseMotionListener {

  public chartPanel() {

    addMouseMotionListener(this);

    addMouseListener(this);

  }



  public chartApplet m_parent;

  private int FRAME_OFFSET = 40;     // ��ͼ���ĸ��ߺ�����ƫ�ƾ���

  private Graphics m_Graphics;       // ����һ��ȫ�ֵĻ���

  private BufferedImage m_imgCourve; // ����һ��������ͬ��С�Ļ�ͼ���ڴ���

  private initPanelInfo m_panelInfo; // ����һ����ʼ�����ĸ��ֲ�����һ�����ݽṹ

  private GDSet m_gdDesc = null;



  //{{-------�й������ק��һЩ����---:

  private int m_nDragStartX = 0;

  private int m_nDragStartY = 0;

  private int m_nDragEndX   = 0;

  private int m_nDragEndY   = 0;

  private boolean m_bDrag   = false;



  //{{-----ͼ����û�зŴ�֮ǰ��X,Y������--:

  private double m_initXMin = 0;

  private double m_initXMax = 0;



  //{{--��������ˮƽ������panel�е�ֵ--:

  private int m_levelMin = 0;

  private int m_levelMax = 0;



  private HashMap m_isDraw = null;   // ��������ĳ�����Ƿ���ʾ



  /**

   * �������Ҫ���ߵ��������ݣ������ͼ�����ǻ����ߣ���ÿ���㹹��һ������

   * �����ͼ�����ǻ�����������ߣ���ÿ��ȡ������Ϊһ����

   */

  private Vector vChartData = new Vector();



  //{{--������ƶ���ĳ���ߵĸ���Ҫ��ʾ������(����)

  private String description = "";



  //{{--Ҫ��ʾ��Y�������͵�λ

  private double m_yScaleMax = 0;

  private double m_yScaleMin = 0;

  private boolean m_isScaleY = false;

  /**

   * ��ʼ��ͼ�ĳ�ʼ��������

   * ������һ���ڴ���������ͼ

   * @param panelInfo: ��ʼ��ͼ�εĸ�����

   */

  public void InitPanel(initPanelInfo panelInfo){

    try{

      this.m_panelInfo = panelInfo;

      m_initXMax = panelInfo.getXmaxValue();

      m_initXMin = panelInfo.getXminValue();

      m_yScaleMax = panelInfo.getYmaxValue();

      m_yScaleMin = panelInfo.getYminValue();



      m_imgCourve = new BufferedImage(m_panelInfo.getWidth(),m_panelInfo.getHight(),
                                      BufferedImage.TYPE_INT_RGB);
      m_Graphics = m_imgCourve.getGraphics();

      String []column = {"x","y","desc"};

      m_gdDesc = new GDSet("viewDesc",column,0);

    }catch(Exception e){
      e.printStackTrace();
    }
  }



   /**

   * ���û�ͼʱĳ�����Ƿ���ʾ

   * @param h����Ӧ��ͼ�����е�������Ϊkeyֵ�����ȡ����0���򲻻�

   */

  public void setIsDraw(HashMap h){

    this.m_isDraw = h;

    repaint();

  }

  public void setDrawLine(String key,boolean bDraw){
      if (bDraw)
          this.m_isDraw.put(key,key);
      else
          this.m_isDraw.remove(key);
      repaint();
  }


  /**

   * ��ô�panel��ʼ���ĸ���ֵ

   *

   * @return������һ�������ĳ�ʼ��ֵ

   */

  public initPanelInfo getPanelInitParameter(){

    initPanelInfo p = new initPanelInfo();

    p.setWidth(m_panelInfo.getWidth());

    p.setHight(m_panelInfo.getHight());

    p.setXminValue(m_panelInfo.getXminValue());

    p.setXmaxValue(m_panelInfo.getXmaxValue());

    p.setYminValue(m_panelInfo.getYminValue());

    p.setYmaxValue(m_panelInfo.getYmaxValue());

    p.setLevelMin(m_panelInfo.getLevelMin());

    p.setLevelMax(m_panelInfo.getLevelMax());

    p.setType(m_panelInfo.getType());

    p.setXunit(m_panelInfo.getXunit());

    p.setYunit(m_panelInfo.getYunit());

    p.setBlowupX(m_panelInfo.isBlowupX());

    p.setBlowupY(m_panelInfo.isBlowupY());

    return p;

  }



  /**

   * ˢ�½���

   * @param g

   */

  public void paint(Graphics g){

    try{

      m_Graphics.setColor(Color.black);

      m_Graphics.fillRect(0, 0, m_panelInfo.getWidth(), m_panelInfo.getHight());

      m_Graphics.setColor(Color.white);

      drawFrame();

      drawView();

      drawSelectRect();

      drawTip();

      g.drawImage(m_imgCourve, 0, 0, this);

    }catch(Exception e){

      e.printStackTrace();

    }

  }







  /**

   * ����ͼ�εĻ�����ܰ���ͼ�ε�X�ᣬY��

   * ͼ�εĺ�����̶ȣ�������̶�

   * �����ˮƽ���ߣ���Ҫ�Ȼ�������

   */

  private void drawFrame(){

    try{

//      m_Graphics.drawString("ʹ��˵����Shift+�������ɷŴ�ͼ�Σ���������Ҽ���ԭ�Ŵ��ͼ��",

//                            FRAME_OFFSET * 2, FRAME_OFFSET - 20);

      // ����

      m_Graphics.drawLine(FRAME_OFFSET, m_panelInfo.getHight() - FRAME_OFFSET,

                          m_panelInfo.getWidth() - FRAME_OFFSET + 10,

                          m_panelInfo.getHight() - FRAME_OFFSET);



      m_Graphics.drawString(m_panelInfo.getXunit(),

                            m_panelInfo.getWidth() - FRAME_OFFSET + 10,

                            m_panelInfo.getHight() - FRAME_OFFSET);

      // ����

      m_Graphics.drawLine(FRAME_OFFSET, m_panelInfo.getHight() - FRAME_OFFSET,

                          FRAME_OFFSET, FRAME_OFFSET - 10);



      drawXScale(); // ������Ŀ̶���

      drawYScale(); // ������Ŀ̶���

      drawLevel(); // ��������ˮƽ�Ƚ���

    }catch(Exception e){

      e.printStackTrace();

    }

  }



  /**

   *  ����Ҫ��ʾ�����ֵ����Сֵ����Ҫ��ʾ�ı��

   *  �̶���ÿ��ƽ�������10���̶ȣ����Ҫ��ʾ�Ŀ̶����ֵ����СֵС��10��

   *  ���ȷŴ�ֱ�����ǵĲ����10Ȼ����ƽ�֣��������С����Ϊdouble���м���

   *  ��С����Ƚ϶࣬���Խ��б�Ҫ����Ϊ����ʾ����

   * @param min: �����Ҫ��ʾ����Сֵ

   * @param max�������Ҫ��ʾ�����ֵ

   * @return   ��Ҫ��ʾ��ÿ�����

   */

  private Vector Scale(double min, double max) {

    Vector v = new Vector();

    try{

      //---����Ŵ�Ŀ̶�С��10,���ȷŴ�̶Ƚ��м��㣬Ȼ���ڻ�ԭ��ԭ���Ŀ̶�

      double minValue = min;

      double maxValue = max;

      double offset = 1;

      while ( (maxValue - minValue) < 10) {

        maxValue = maxValue * 10;

        minValue = minValue * 10;

        offset = offset * 10;

      }

      double step = ( (maxValue - minValue) / 10) / offset;

      //---����double���м���󣬾��ȱȽϸߣ����԰���ʱ����ʾ��������ݽ����˸�ʽ����

      while (min <= max) {
       if (offset == 10.0 || offset == 1.0) {
          String s = String.valueOf(min);
          int index = s.indexOf(".");
          min = new Double(s.substring(0, index) + s.substring(index, index + 2)).
              doubleValue();
        }

        else if (offset == 100.0) {
          String s = String.valueOf(min);
          int index = s.indexOf(".");
          min = new Double(s.substring(0, index) + s.substring(index, index + 3)).
              doubleValue();

        }

        else if (offset == 1000.0) {

          String s = String.valueOf(min);

          int index = s.indexOf(".");

          min = new Double(s.substring(0, index) + s.substring(index, index + 4)).

              doubleValue();

        }

        else if (offset == 10000.0) {

          String s = String.valueOf(min);

          int index = s.indexOf(".");

          min = new Double(s.substring(0, index) + s.substring(index, index + 5)).

              doubleValue();

        }

        v.add(new Double(min));

        min += step;

      }

    }catch(Exception e){

      e.printStackTrace();

    }

    return v;

  }



  /**

   * ���������߿̶�

   */

  private void drawXScale() {

    try{

      Vector v = Scale(m_panelInfo.getXminValue(), m_panelInfo.getXmaxValue());

      boolean b = false;

      //--�˴�v.size-1��Ҫ�Ǽ�������ı�����һ��ֵ�Ǵ��ں���ģ����Թ��˵����һ��

      for (int i = 0; i < v.size(); i++) {

        double scale = ( (Double) v.get(i)).doubleValue();

        int place = (int) getPanelScale(scale, 0,0,0).getX();

        //---�������ֵ��Ϊ��ʹ����ֵ���ͺ����ص���������һ����ƫ��

        m_Graphics.setColor(Color.white);

        m_Graphics.drawString(String.valueOf(scale), place - 10,

                              (m_panelInfo.getHight() - FRAME_OFFSET + 15));

        // ��Ϊ��һ���ߺ������ص����ʵ�һ������

        if (b) {

          //----�������ߵĿ̶���

          drawDashLine(4, 4, place, FRAME_OFFSET - 10,

                       m_panelInfo.getHight() - FRAME_OFFSET * 2 + 10,

                       Color.gray, 1);

        }

        b = true;

      }

    }catch(Exception e){

      e.printStackTrace();

    }

  }





  /**

   * ��������̶�

   * ���ݵĿ̶Ⱥ͵�λĬ�ϳ�ʼ��Appletʱ��ֵ����������

   */

  private void drawYScale() {

    try{

      Vector v = Scale(m_yScaleMin,m_yScaleMax);

      //--�˴�v.size-1��Ҫ�Ǽ�������ı�����һ��ֵ�Ǵ�������ģ����Թ��˵����һ��

      for (int i = 0; i < v.size(); i++) {

        double scale = ( (Double) v.get(i)).doubleValue();

        int place = (int) getPanelScale(0, scale,m_yScaleMax,m_yScaleMin).getY();

        //---��������̶ȵ�ֵ

        m_Graphics.setColor(Color.white);

        m_Graphics.drawString(String.valueOf(scale), FRAME_OFFSET - 30, place);

        m_Graphics.drawString(m_panelInfo.getYunit(), FRAME_OFFSET,FRAME_OFFSET - 10);

        if (place != m_panelInfo.getHight() - FRAME_OFFSET) {

          //---��������̶�����

          drawDashLine(4, 4, FRAME_OFFSET, place,

                       (m_panelInfo.getWidth() - FRAME_OFFSET * 2 + 10),

                       Color.gray, 0);
        }
     }
    }catch(Exception e){
     e.printStackTrace();
    }
  }

  /**
   * ����ͼ���������ˮƽ����
   */

  private void drawLevel() {

    m_levelMax = (int)getPanelScale(0, m_panelInfo.getLevelMax(),

                                    m_panelInfo.getYmaxValue(),m_panelInfo.getYminValue()).getY();

    m_Graphics.setColor(Color.white);

    m_Graphics.drawLine(FRAME_OFFSET, m_levelMax, m_panelInfo.getWidth() - FRAME_OFFSET,m_levelMax);

    m_levelMin = (int)getPanelScale(0, m_panelInfo.getLevelMin(),

                                    m_panelInfo.getYmaxValue(),m_panelInfo.getYminValue()).getY();

    m_Graphics.drawLine(FRAME_OFFSET, m_levelMin, m_panelInfo.getWidth() - FRAME_OFFSET,m_levelMin);

  }



  /**

   * ���ݴ��˵�һ������ֵ�����panle�е�ʵ��λ��

   * ���ֻ��Ҫһ��ֵ������һ�����Դ�0������ֵ�ж�Ӧֵ��Ч

   *

   * @param x:    Ҫ��ʾ��xֵ

   * @param y:    Ҫ��ʾ��yֵ

   * @param yMax: �����������ʱy���������ֵ

   * @param yMin: �����������ʱy����С����ֵ

   *

   * @return�� ����һ������һ������ֵ�����ݽṹ

   */

  private scaleInfo getPanelScale(double x, double y,double yMax,double yMin) {

    scaleInfo sc = new scaleInfo();



    try{

      double scaleX = ( (x - m_panelInfo.getXminValue()) *(m_panelInfo.getWidth() - FRAME_OFFSET * 2) /

                      (m_panelInfo.getXmaxValue() - m_panelInfo.getXminValue())) + FRAME_OFFSET;

      sc.setX(scaleX);

      double scaleY = ( (y - yMin) *(m_panelInfo.getHight() - FRAME_OFFSET * 2) /

                      (yMax - yMin)) + FRAME_OFFSET;

      // ��Ϊpanel�е�Y���ʵ�����ǻ��ߵ����෴�ģ�����Ҫ���д���

      sc.setY(m_panelInfo.getHight() - scaleY);

    }catch (Exception e){

      e.printStackTrace();

    }

    return sc;

  }



  /**

   * ����panle�е�λ�ü����ʵ�ʵ���ֵ(ʵ���Ǻ���getPanelScale��һ��������)

   * ���ֻ��Ҫһ��ֵ������һ�����Դ�0������ֵ�ж�Ӧֵ��Ч

   *

   * @param x: Ҫ��ʾ��xֵ

   * @param y: Ҫ��ʾ��yֵ

   * @param yMax: �����������ʱy���������ֵ

   * @param yMin: �����������ʱy����С����ֵ

   *

   * @return�� ����һ������һ������ֵ�����ݽṹ

   */

  private scaleInfo getScaleInPanel(double x, double y,double yMax,double yMin) {

    scaleInfo sc = new scaleInfo();

    try{

      double scaleX = (x - FRAME_OFFSET) *

          (m_panelInfo.getXmaxValue() - m_panelInfo.getXminValue()) /

          (m_panelInfo.getWidth() - FRAME_OFFSET * 2) +

          m_panelInfo.getXminValue();

      String s = String.valueOf(scaleX);

      int index = s.indexOf(".");

      scaleX = new Double(s.substring(0, index) + s.substring(index, index + 2)).doubleValue();

      sc.setX(scaleX);



      double scaleY = (m_panelInfo.getHight() - y - FRAME_OFFSET) *

          ( (int) yMax - (int) yMin) /

          (m_panelInfo.getHight() - FRAME_OFFSET * 2) + (int) yMin;

      s = String.valueOf(scaleY);

      index = s.indexOf(".");

      scaleY = new Double(s.substring(0, index) + s.substring(index, index + 2)).doubleValue();

      sc.setY(scaleY);

    }catch(Exception e){

      e.printStackTrace();

    }

    return sc;

  }



  /**

   * ��ָ�������λ�û���һ��ǳ��ɫ������

   * @param dashlen  �� ������ʵ�ߵĳ���

   * @param blanklen �������пհ׵ĳ���

   * @param left      ���������߾�����߾�

   * @param top       : �������߾��붥�˵ľ���

   * @param len       : �������ߵĳ���

   * @param c         �����ߵ���ɫ

   * @param direction ��0�����ߣ�1������

   */

  private void drawDashLine(int dashlen, int blanklen, int left, int top,int len, Color c, int direction) {

    try{

      m_Graphics.setColor(c);

      int drawlen = 0;

      while (drawlen < len) {

        int start = drawlen;

        drawlen = drawlen + dashlen;

        if (drawlen > len) {

          drawlen = len;

        }

        int end = drawlen;

        if (direction == 0)

          m_Graphics.drawLine(left + start, top, left + end, top);

        else

          m_Graphics.drawLine(left, top + start, left, top + end);

        drawlen = drawlen + blanklen;

      }

    }catch(Exception e){

      e.printStackTrace();

    }

  }





  /**

   * �����ⲿ��ĳ���ߵ���ɫ���������ô���ɫ�ػ������ߣ�����ǻ�ֱ�߾�ȡ��ÿ�����X�ử�ߣ�

   * ����ǻ������߶���ȡ�����������߼��ɣ�ͬʱ������ÿ���ߵ�˵�����Լ����λ�õ�˵��

   * ��vChartData�е����ݻ���������

   */

  private void drawView() {

    try {

      int descX = FRAME_OFFSET-15;

      for (Iterator i = vChartData.iterator(); i.hasNext(); ) {

        chartDataInfo chart = (chartDataInfo) i.next();

        String isDraw = (String) m_isDraw.get(String.valueOf(chart.getType()));

        if (isDraw != null) { // �жϴ����Ƿ���ʾ

          if (isDraw.equalsIgnoreCase("0") || isDraw.equalsIgnoreCase("false")) {

            continue;

          }

        }

        Font f = new Font("����", Font.PLAIN, 12);

        m_Graphics.setFont(f);

        drawColor(chart.getType()); // ���ݴ����ߵ��������ı���ɫ

        // ������ʾÿ���ߵ�˵�����Ȼ�һ�������Σ�Ȼ������һ��˵����

        m_Graphics.fillRect(descX, m_panelInfo.getHight()-FRAME_OFFSET + 20, 10, 10);

        m_Graphics.drawString(chart.getDesc(), descX + 15,m_panelInfo.getHight()- FRAME_OFFSET + 30);

        descX = descX + chart.getDesc().length()*f.getSize() + 50;

        drawColor(chart.getColor());

        if (m_panelInfo.getType() == 1) { // ������

          drawViewLine(chart);

        }else { // �����������ֱ��

          drawViewCurve(chart);

        }

      }

    }catch (Exception e) {

      e.printStackTrace();

    }

  }



  /**

   * ���ݻ�õ����ݻ�һ����X��Ϊ�͵�ֱ��

   * @param chart

   */

  private void drawViewLine(chartDataInfo chart){

    try {

      for (Iterator j = chart.getScaleInfo().iterator(); j.hasNext(); ) {

        scaleInfo sOld = (scaleInfo) j.next();

        scaleInfo s = isInCord(sOld,m_panelInfo.getYmaxValue(),m_panelInfo.getYminValue());

        if (s != null) {

          m_Graphics.setColor(Color.white);

          setLineColor(sOld.getY());

          m_Graphics.drawLine( (int) s.getX(),

                               m_panelInfo.getHight() - FRAME_OFFSET,

                               (int) s.getX(), (int) s.getY());

        }

      }

    }catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  /**

   * ���ݻ�õ����ݻ����������һ��ֱ��

   * @param chart

   */

  private void drawViewCurve(chartDataInfo chart){

    try {

      Vector temp = chart.getScaleInfo();

      if (temp.size() > 1) { // ����������������ʱ��ͬʱȡ��������������

        for (int size = temp.size(), j = 0; j < size - 1; j++) {

          scaleInfo s1Old = (scaleInfo) temp.get(j);

          scaleInfo s2Old = (scaleInfo) temp.get(j + 1);

          scaleInfo s1 = isInCord(s1Old,m_yScaleMax,m_yScaleMin);
          scaleInfo s2 = isInCord(s2Old,m_yScaleMax,m_yScaleMin);

          if (s1 != null && s2 != null) {
          drawColor(chart.getType());
            m_Graphics.drawLine( (int) s1.getX(), (int) s1.getY(),

                                 (int) s2.getX(), (int) s2.getY());

          }

          else if (s1 == null && s2 != null){

            scaleInfo sTemp = new scaleInfo();

            sTemp.setX(m_panelInfo.getXminValue());

            sTemp.setY(s1Old.getY());

            s1 = isInCord(sTemp,m_yScaleMax,m_yScaleMin);

            m_Graphics.drawLine( (int) s1.getX(), (int) s1.getY(),

                                 (int) s2.getX(), (int) s2.getY());

          }

        }
      }

    }catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  /**

   * ���ݲ�ͬ�����͸ı仭�ߵ���ɫ

   * @param type��1��red,2:green,3:blue,4:yellow,5:orange,6:cyan,����:black

   */

  private void drawColor(int type){

    switch (type){

      case 1:{
        m_Graphics.setColor(Color.cyan);

        break;

      }case 2:{

        m_Graphics.setColor(Color.red);

        break;

      }case 3:{

        m_Graphics.setColor(Color.yellow);

        break;

      }case 4:{

        m_Graphics.setColor(Color.green);

        break;

      }case 5:{

        m_Graphics.setColor(Color.blue);

        break;

      }case 6:{

        m_Graphics.setColor(Color.cyan);

        break;

      }case 7:{

        m_Graphics.setColor(Color.orange);

        break;

      }default :

        m_Graphics.setColor(Color.white);

    }

  }



  /**

   * ����ק��

   */

  public void drawSelectRect() {

    if ( (m_bDrag) && (m_nDragEndX > 0)) {

      m_Graphics.setColor(Color.blue);

      int x1, x2, y1, y2;

      x1 = m_nDragStartX;

      x2 = m_nDragEndX;

      y1 = m_nDragStartY;

      y2 = m_nDragEndY;

      if (m_nDragStartX > m_nDragEndX) {

        x1 = m_nDragEndX;

        x2 = m_nDragStartX;

      }

      if (m_nDragStartY > m_nDragEndY) {

        y1 = m_nDragEndY;

        y2 = m_nDragStartY;

      }

      m_Graphics.drawRect(x1, y1, x2 - x1, y2 - y1);

    }

  }



  /**

   * �ж�Ҫ���ߵĵ��Ƿ���ʵ��X,Y��ֵ�ķ�Χ֮��(����ķ�Χ��ָ������ʾ��ֵ)

   * @param s:

   * @param yMax: �����������ʱy���������ֵ

   * @param yMin: �����������ʱy����С����ֵ



   * @return : ����ڷ�Χ֮�ڣ��򷵻أ���������panel�е�λ�ã���������null

   */

  private scaleInfo isInCord(scaleInfo s,double yMax,double yMin){

    scaleInfo scale = new scaleInfo();

    if (m_panelInfo.getXminValue() <= s.getX() && s.getX()<= m_panelInfo.getXmaxValue()){

      if (s.getY() > yMax)

        scale = getPanelScale(s.getX(),yMax,yMax,yMin);

      else

        scale = getPanelScale(s.getX(),s.getY(),yMax,yMin);

      return scale;

    }

    return null;

  }



  /**

   * ����Ҫ��������ˮƽ���ߵ�λ�ã����ò�ͬ����ɫ

   * ����������ˮƽ��������ɫΪred,���С�����ˮƽ�ߣ�����blue

   * @param y

   */

  private void setLineColor(double y){

    if (y > m_panelInfo.getLevelMax()){ // ���ڴ��ˮƽ���ú��߻�

      m_Graphics.setColor(Color.red);

    }else if(y < m_panelInfo.getLevelMin()){ // С��С��ˮƽ�������߻�

      m_Graphics.setColor(Color.blue);

    }

  }





  /**

   * �ж������Ƿ񳬳�X����������ֵ

   * ����ǻ�����  �� Xֵ����X������ֵ����ֹͣȡ�����߳�

   * ����ǻ������ߣ� Xֵ����X������ֵ����X����������ֵ�޸�Ϊ��ֵ

   *                X����������Сֵ��Ϊ X-X�����ֵ + ԭ����Сֵ

   *

   * @param x   ��Ҫ�жϵ�Xֵ

   * @param type: ���ߵ����� 1������ͨ���ߣ�2���������������

   */

  private void dataIsOverStepX(String xValue,int type){

    try {

      double x = 0;

      if (xValue != null) {

        x = new Double(xValue).doubleValue();

      }

      if (m_panelInfo.getType() == 1) { // ��������ߣ���ѯ������ֵ�����������ֵ��ֹͣȡ���߳�

        if (x >= m_initXMax) {

         ;

        }

      }else {

        if (x >= m_initXMax) {

          m_initXMin = x - m_initXMax + m_initXMin;

          m_initXMax = x;

          m_panelInfo.setXminValue(m_initXMin);

          m_panelInfo.setXmaxValue(m_initXMax);

          for (int count = vChartData.size(), i = 0; i < count; i++) { // ���β�ѯ�������ݣ����Ƿ��д����ߵ�����

            chartDataInfo chart = (chartDataInfo) vChartData.get(i);

            if (type == chart.getType()) { // �����������ҵ���ͬ���͵����ݣ�����µ����Ϣ

              Vector vScanle = chart.getScaleInfo();

              for (int j = 0; j < vScanle.size(); j++) {

                scaleInfo scale = (scaleInfo) vScanle.get(j);

                if (scale.getX() < m_initXMin) {

//                  vScanle.remove(j);


                }

              }

            }

          }

        }

      }

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



//----------------------------------------------------------------------------------

  /**

   * ����ƶ��¼�

   *

   * ����ǻ���ͨ���ߣ�������ƶ���ĳ���ߵ�ǰ����������ʱ��ȡ�������ߵ�˵��

   * @param e��

   */

  public void mouseMoved(MouseEvent e) {

    try {

      e.consume();

      int x = e.getX();

      int y = e.getY();

      boolean bdesc = true;

      if (mouseInCord(x, y)) {

        if (m_panelInfo.getType() == 1) { // ���ձ�����

          m_gdDesc.clearAllRow();

          for (Iterator it = vChartData.iterator(); it.hasNext(); ) {

            chartDataInfo chart = (chartDataInfo) it.next();

            Vector vScale = chart.getScaleInfo();

            for (Iterator it1 = vScale.iterator(); it1.hasNext(); ) {

              scaleInfo s1 = (scaleInfo) it1.next();

              scaleInfo s = getPanelScale(s1.getX(),s1.getY(),m_panelInfo.getYmaxValue(),m_panelInfo.getYminValue());

              if (x - 2 < s.getX() && s.getX() < x + 2 && s.getY() < y) {

                String []row = {"x","y","desc"};

                row[0] = String.valueOf(x);

                row[1] = String.valueOf(y);

                row[2] = s1.getdesc() + ","+ s1.getY();

                description = s1.getdesc();

                m_gdDesc.addRow(row);

                break;

              }

            }

          }

        }else{ // �������������

          setGdDesc(e);

        }

        repaint();

      }

    }catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  // �ж�����Ƿ��ڻ�ͼ��������

  public boolean mouseInCord(int x, int y) {

    if (x > FRAME_OFFSET && x < m_panelInfo.getWidth()-FRAME_OFFSET

        && y > FRAME_OFFSET && y < m_panelInfo.getHight()-FRAME_OFFSET){

      return true;

    }

    return false;

  }



  /**

   * ��ס�����¼�

   * �������������+shift��������Ϊ��ק�Ŀ�ʼλ��

   * @param e

   */

  public void mousePressed(MouseEvent e) {e.consume();

    e.consume();

     if (e.isShiftDown() && (e.getModifiers() & e.BUTTON1_MASK) > 0) {

       if (mouseInCord(e.getX(), e.getY())) {

         m_bDrag = true;

         m_nDragStartX = e.getX();

         m_nDragStartY = e.getY();

       }

     }

  }



  // �������϶�������

  public void mouseDragged(MouseEvent e) {

    e.consume();

     if (m_bDrag) {

       m_nDragEndX = e.getX();

       m_nDragEndY = e.getY();

       repaint();

     }

  }





  /**

   * �ͷ�����¼�

   * �������ͷ�ʱ����ͬʱshift��Ҳ�ͷţ��������ϵ�ͼ����ߣ���ȡ����ק��ֱ�ӷ���

   *      ������ݳ�ʼ���÷Ŵ�X���Y��

   * @param e

   */

  public void mouseReleased(MouseEvent e) {

    try {

      e.consume();

      if (e.isShiftDown() && (e.getModifiers() & e.BUTTON1_MASK) > 0) {

        if (!mouseInCord(e.getX(), e.getY())) { // �������϶���ͼ���⣬ֱ�ӷ���

          m_bDrag = false;

          repaint();

          return;

        }

        if (m_panelInfo.isBlowupX()) {

          blowupX(e);

        }

        if (m_panelInfo.isBlowupY()) {

          blowupY(e);

        }

      }

      m_bDrag = false;

      m_nDragStartX = 0;

      m_nDragStartY = 0;

      m_nDragEndX = 0;

      m_nDragEndY = 0;

      repaint();

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  /**

   * ��굥���¼�

   * ���ʱ��������Ҽ����򷵻�ԭʼ�����С

   * ���ʱ��������������ʱ����ͨ���ߣ���ȡ�����λ����������������ֵ��Ӧ��url��ֱ��ˢ��

   * @param e

   */

  public void mouseClicked(MouseEvent e) {

    try {

      e.consume();

      if (e.getModifiers() == e.BUTTON3_MASK) { // ��������Ҽ�������ԭʼ�ߴ�

        m_panelInfo.setXmaxValue(m_initXMax);

        m_panelInfo.setXminValue(m_initXMin);

        repaint();

      }

      if ( ( (e.getModifiers() & e.BUTTON1_MASK) > 0) && (!e.isShiftDown())) { // ����������

        int x = e.getX();

        int y = e.getY();

        if (m_panelInfo.getType() == 1) {

          for (Iterator it = vChartData.iterator(); it.hasNext(); ) {

            chartDataInfo chart = (chartDataInfo) it.next();

            Vector vScale = chart.getScaleInfo();

            for (Iterator it1 = vScale.iterator(); it1.hasNext(); ) {

              scaleInfo s1 = (scaleInfo) it1.next();

              scaleInfo s = getPanelScale(s1.getX(), s1.getY(),m_panelInfo.getYmaxValue(),m_panelInfo.getYminValue());

              if (x - 2 < s.getX() && s.getX() < x + 2) {

                  AppletContext ac = m_parent.getAppletContext();

                  String sUrl = s1.getUrl();

                  String path = m_parent.getDocumentBase().toString();

                  int loc = path.lastIndexOf("/");

                  path = path.substring(0, loc) + "/" + sUrl;

                  URL u = new URL(path);

                  ac.showDocument(u, "qualityScan");

              }

            }

          }

        }

        repaint();

      }

    }catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  /**

   * ��X���������зŴ�

   *

   * ȡ����ק�Ŀ�ʼ��X�������õ�panel��ʼ��������X�������Сֵ��

   * ȡ����ק�Ľ�����X�������õ�panel��ʼ��������X��������ֵ��

   *

   * @param e

   */

  private void blowupX(MouseEvent e){

    int x1 = m_nDragStartX;

    int x2 = e.getX();

    int x = x2 - x1;

    if (x1 > x2)

      x = x1 - x2;

    if (m_bDrag) {

      if (x > 10) {

        m_nDragEndX = e.getX();

        m_nDragEndY = e.getY();

        double dragEndX  = getScaleInPanel(m_nDragEndX,0,0,0).getX();

        double dragStart = getScaleInPanel(m_nDragStartX,0,0,0).getX();

        m_panelInfo.setXmaxValue(dragEndX);

        m_panelInfo.setXminValue(dragStart);

        if (dragStart > dragEndX) {

          m_panelInfo.setXmaxValue(dragStart);

          m_panelInfo.setXminValue(dragEndX);

        }

      }

    }

  }





  /**

   * ��Y���������зŴ�

   *

   * ȡ����ק�Ŀ�ʼ��Y�������õ�panel��ʼ��������Y�������Сֵ��

   * ȡ����ק�Ľ�����Y�������õ�panel��ʼ��������Y��������ֵ��

   *

   * @param e

   */

  private void blowupY(MouseEvent e){

/*    int y1 = m_nDragStartX;

    int y2 = e.getY();

    int y = y2 - y1;

    if (y1 > y2)

      y = y1 - y2;

    if (m_bDrag) {

      if (y > 10) {

        m_nDragEndX = e.getX();

        m_nDragEndY = e.getY();

        double dragEndY  = getScaleInPanel(0,m_nDragEndY).getY();

        double dragStart = getScaleInPanel(0,m_nDragStartY).getY();

        m_panelInfo.setYmaxValue(dragEndY);

        m_panelInfo.setYminValue(dragStart);

        if (dragStart > dragEndY) {

          m_panelInfo.setYmaxValue(dragStart);

          m_panelInfo.setYminValue(dragEndY);

        }

      }

    }*/

  }





  /**

   * ��Ҫ���ߵĵ���뵽vChartData�У����ݴ��˵����ݵ����ͣ�

   * ����Щ���ݼ��뵽���Ե��ߵ������У�Ȼ��ͳһˢ�£�ͬʱ��������

   *

   * @param strGDSet��Ҫ���ߵ����ݣ�����һ�������ˣ�Ȼ��ֱ��תΪGDSet�ṹ

   */

  public void addChartData(String strGDSet ){

    try {

      boolean b = false;

      GDSet gd = GDSetTool.parseStringToGDSet(strGDSet);

      for (int row = gd.getRowCount(), i=0; i < row; i++){ // ���δ����õ�ÿ������

        int type = new Integer(gd.getString(i,"type")).intValue();

        for (int count = vChartData.size(),j = 0; j < count; j++ ){ // ���β�ѯ�������ݣ����Ƿ��д����ߵ�����

          chartDataInfo chart = (chartDataInfo)vChartData.get(j);

          if (type == chart.getType()){ // �����������ҵ���ͬ���͵����ݣ�����µ����Ϣ

            scaleInfo scale = new scaleInfo();

            scale.setX(new Double(gd.getString(i,"X")).doubleValue());

            scale.setY(new Double(gd.getString(i,"Y")).doubleValue());

            scale.setdesc(gd.getString(i,"desc"));

            scale.setUrl(gd.getString(i,"url"));

            Vector temp = chart.getScaleInfo();

            //{{--�ж����Ҫ��ӵĻ�ͼ���ݵ�XС���������ݵ�X�򣬲���Ӵ�����--:

            boolean bMore = false;

            for (Iterator iter = temp.iterator(); iter.hasNext();){

              scaleInfo s = (scaleInfo)iter.next();

              if(s.getX() > scale.getX()){

                bMore = true;

                break;

              }

            }

            if(!bMore){

              temp.add(scale);

            }

            chart.setScaleInfo(temp);

            dataIsOverStepX(gd.getString(i,"X"),type);  // �ж�X��ֵ�������X������꣬������Ӧ����

            b = true;

            break;

          }

        }

        if (!b){  // ���û�д����ߵ�����,��˵�����������¼ӵģ����������ݼӵ�vChartInfo��

          chartDataInfo chart = new chartDataInfo();

          chart.setColor(new Integer(gd.getString(i,"color")).intValue());

          chart.setDesc(gd.getString(i,"description"));

          chart.setType(type);

          chart.setYMax(new Double(gd.getString(i,"yMax")).doubleValue());

          chart.setYMin(new Double(gd.getString(i,"yMin")).doubleValue());

          chart.setYUnit(gd.getString(i,"yUnit"));

          scaleInfo scale = new scaleInfo();

          scale.setX(0);

          scale.setY(new Double(gd.getString(i,"Y")).doubleValue());

          scale.setdesc(gd.getString(i,"desc"));

          scale.setUrl(gd.getString(i,"url"));

          Vector temp = new Vector();

          temp.add(scale);

          chart.setScaleInfo(temp);

          vChartData.add(chart);

        }

      }

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

    repaint();

  }


  public void addChartData(int type,String x, String y, String desc, String url,String color,String yMin, String yMax, String yUnit ){

    try {

      boolean b = false;



        for (int count = vChartData.size(),j = 0; j < count; j++ ){ // ���β�ѯ�������ݣ����Ƿ��д����ߵ�����

          chartDataInfo chart = (chartDataInfo)vChartData.get(j);

          if (type == chart.getType()){ // �����������ҵ���ͬ���͵����ݣ�����µ����Ϣ

            scaleInfo scale = new scaleInfo();

            scale.setX(new Double(x).doubleValue());

            scale.setY(new Double(y).doubleValue());

            scale.setdesc(desc);

            scale.setUrl(url);

            Vector temp = chart.getScaleInfo();

            //{{--�ж����Ҫ��ӵĻ�ͼ���ݵ�XС���������ݵ�X�򣬲���Ӵ�����--:

            boolean bMore = false;

            for (Iterator iter = temp.iterator(); iter.hasNext();){

              scaleInfo s = (scaleInfo)iter.next();

              if(s.getX() > scale.getX()){

                bMore = true;

                break;

              }

            }

            if(!bMore){


              temp.add(scale);

            }

            chart.setScaleInfo(temp);

            dataIsOverStepX(x,type);  // �ж�X��ֵ�������X������꣬������Ӧ����

            b = true;

            break;

          }

        }

        if (!b){  // ���û�д����ߵ�����,��˵�����������¼ӵģ����������ݼӵ�vChartInfo��

          chartDataInfo chart = new chartDataInfo();

          chart.setColor(new Integer(color).intValue());

          chart.setDesc(desc);

          chart.setType(type);

          chart.setYMax(new Double(yMax).doubleValue());

          chart.setYMin(new Double(yMin).doubleValue());

          chart.setYUnit(yUnit);

          scaleInfo scale = new scaleInfo();

          scale.setX(0);

          scale.setY(new Double(y).doubleValue());

          scale.setdesc(desc);

          scale.setUrl(url);

          Vector temp = new Vector();

          temp.add(scale);

          chart.setScaleInfo(temp);

          vChartData.add(chart);

        }



    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

    repaint();

  }


  /**

   * ����ǻ�����������ߣ���������ƶ�λ�ô���˵��

   * @param e

   */

  private void setGdDesc(MouseEvent e){

    try {

      m_gdDesc.clearAllRow();

      e.consume();

      int x = e.getX();

      int y = e.getY();

      if (mouseInCord(x, y)) {

        for (Iterator it = vChartData.iterator(); it.hasNext(); ) {

          chartDataInfo chart = (chartDataInfo) it.next();

          String isDraw = (String) m_isDraw.get(String.valueOf(chart.getType()));

          if (isDraw != null) { // �жϴ����Ƿ���ʾ

            if (isDraw.equalsIgnoreCase("0") || isDraw.equalsIgnoreCase("false")) {

              continue;

            }

          }

          Vector vScale = chart.getScaleInfo();

          for (int i = 0; i < vScale.size() - 1; i++) {

            scaleInfo s1 = (scaleInfo) vScale.get(i);

            scaleInfo s2 = (scaleInfo) vScale.get(i + 1);

            scaleInfo sPanel1 = getPanelScale(s1.getX(), s1.getY(),m_yScaleMax,m_yScaleMin);

            scaleInfo sPanel2 = getPanelScale(s2.getX(), s2.getY(),m_yScaleMax,m_yScaleMin);

            if ( (sPanel1.getX() - 2 < x && x < sPanel2.getX() + 2) ||

                (sPanel1.getX() + 2 > x && x > sPanel2.getX() - 2)) { // ����������X�᷶Χ֮��

              double y3 =  sPanel2.getY() - ( sPanel2.getY() - sPanel1.getY()) *( sPanel2.getX() - x) /

                  ( sPanel2.getX() - sPanel1.getX());

              if (y3 - 2 < y && y3 + 2 > y) {

                String[] row = {"x", "y","desc"};

                row[0] = String.valueOf(x);

                row[1] = String.valueOf(y);

                row[2] = chart.getDesc() + ":" + getScaleInPanel(0, y3,m_yScaleMax,m_yScaleMin).getY() +

                      chart.getYUnit() + "," + getScaleInPanel(x, 0,0,0).getX() + m_panelInfo.getXunit();

                m_gdDesc.addRow(row);

                break;

              }

            }

          }

        }

      }

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  /**

   *�����������λ�õ��ߵ�����

   */

  private void drawTip(){

    try {

      Font f = new Font("����", Font.PLAIN, 12);

      m_Graphics.setFont(f);

      if (m_gdDesc.getRowCount() > 0){

        int x = new Integer(m_gdDesc.getString(0, "x")).intValue();

        int y = new Integer(m_gdDesc.getString(0, "y")).intValue();

        for (int i = 0; i < m_gdDesc.getRowCount(); i++) {

          int x2 = (m_gdDesc.getString(i, "desc").length()) * f.getSize();

          if ( (x + 10 + x2) > (m_panelInfo.getWidth() - FRAME_OFFSET)) {

            x = x - x2 - 10;

          }

          m_Graphics.setColor(new Color(255, 255, 200));

          m_Graphics.fillRect(x + 10, y, x2, 18);

          m_Graphics.setColor(Color.black);

          m_Graphics.drawRect(x + 10, y, x2, 18);

          m_Graphics.setColor(Color.black);

          m_Graphics.drawString(m_gdDesc.getString(i, "desc"), x + 15, y + 16);

          y += 18;

        }

      }

    }catch (Exception e) {

      e.printStackTrace();

    }

  }



  public void mouseEntered(MouseEvent e) { e.consume();}

  public void mouseExited(MouseEvent e) {e.consume();}

  /**

   * ����Y��Ҫ��ʾ�ı�ߺ͵�λ

   * @param type

   */

  public void setYScale(String type, String yUnit){
    m_panelInfo.setYunit(yUnit);
    repaint();
    for (Iterator it = vChartData.iterator(); it.hasNext(); ) {

      chartDataInfo chart = (chartDataInfo) it.next();

      if(type.equalsIgnoreCase(String.valueOf(chart.getType()))){

          m_yScaleMax = chart.getYMax();
          m_yScaleMin = chart.getYMin();

         m_panelInfo.setYunit(chart.getYUnit());

        m_isScaleY = true;

        repaint();

        break;

      }

    }

  }

}
