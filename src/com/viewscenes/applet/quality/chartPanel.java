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

 * <p>Title: 画线的主窗口，继承系统的JPanel,所有的图形都是先画到内存中，

 *          然后画到此panel

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

  private int FRAME_OFFSET = 40;     // 画图的四个边和面板的偏移距离

  private Graphics m_Graphics;       // 定义一个全局的画笔

  private BufferedImage m_imgCourve; // 开辟一块和面板相同大小的画图的内存区

  private initPanelInfo m_panelInfo; // 定义一个初始化面板的各种参数的一个数据结构

  private GDSet m_gdDesc = null;



  //{{-------有关鼠标拖拽的一些数据---:

  private int m_nDragStartX = 0;

  private int m_nDragStartY = 0;

  private int m_nDragEndX   = 0;

  private int m_nDragEndY   = 0;

  private boolean m_bDrag   = false;



  //{{-----图像在没有放大之前的X,Y轴坐标--:

  private double m_initXMin = 0;

  private double m_initXMax = 0;



  //{{--保存两条水平门线在panel中的值--:

  private int m_levelMin = 0;

  private int m_levelMax = 0;



  private HashMap m_isDraw = null;   // 用来保存某条线是否显示



  /**

   * 存放所有要画线的坐标数据，如果画图类型是画竖线，则每个点构成一个数据

   * 如果画图类型是画任意两点的线，则每次取两点作为一条线

   */

  private Vector vChartData = new Vector();



  //{{--当鼠标移动到某条线的附近要显示的内容(竖线)

  private String description = "";



  //{{--要显示的Y轴的坐标和单位

  private double m_yScaleMax = 0;

  private double m_yScaleMin = 0;

  private boolean m_isScaleY = false;

  /**

   * 初始画图的初始各个参数

   * 并开辟一块内存区用来画图

   * @param panelInfo: 初始化图形的各参数

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

   * 设置画图时某条线是否显示

   * @param h：对应画图数据中的类型作为key值，如果取出是0，则不画

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

   * 获得此panel初始化的各种值

   *

   * @return：返回一个此面板的初始化值

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

   * 刷新界面

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

   * 画出图形的基本框架包括图形的X轴，Y轴

   * 图形的横坐标刻度，纵坐标刻度

   * 如果有水平门线，还要先画出此线

   */

  private void drawFrame(){

    try{

//      m_Graphics.drawString("使用说明：Shift+鼠标左键可放大图形，单击鼠标右键还原放大的图形",

//                            FRAME_OFFSET * 2, FRAME_OFFSET - 20);

      // 横州

      m_Graphics.drawLine(FRAME_OFFSET, m_panelInfo.getHight() - FRAME_OFFSET,

                          m_panelInfo.getWidth() - FRAME_OFFSET + 10,

                          m_panelInfo.getHight() - FRAME_OFFSET);



      m_Graphics.drawString(m_panelInfo.getXunit(),

                            m_panelInfo.getWidth() - FRAME_OFFSET + 10,

                            m_panelInfo.getHight() - FRAME_OFFSET);

      // 纵州

      m_Graphics.drawLine(FRAME_OFFSET, m_panelInfo.getHight() - FRAME_OFFSET,

                          FRAME_OFFSET, FRAME_OFFSET - 10);



      drawXScale(); // 画横轴的刻度线

      drawYScale(); // 画纵轴的刻度线

      drawLevel(); // 画出两条水平比较线

    }catch(Exception e){

      e.printStackTrace();

    }

  }



  /**

   *  根据要显示的最大值和最小值设置要显示的标尺

   *  刻度轴每次平均分配成10根刻度，如果要显示的刻度最大值减最小值小于10，

   *  则先放大，直到他们的差大于10然后在平分，最后再缩小，因为double进行计算

   *  后小数点比较多，所以进行必要处理，为了显示美观

   * @param min: 标尺上要显示的最小值

   * @param max：标尺上要显示的最大值

   * @return   ：要显示的每个标尺

   */

  private Vector Scale(double min, double max) {

    Vector v = new Vector();

    try{

      //---如果放大的刻度小于10,则先放大刻度进行计算，然后在还原到原来的刻度

      double minValue = min;

      double maxValue = max;

      double offset = 1;

      while ( (maxValue - minValue) < 10) {

        maxValue = maxValue * 10;

        minValue = minValue * 10;

        offset = offset * 10;

      }

      double step = ( (maxValue - minValue) / 10) / offset;

      //---由于double进行计算后，精度比较高，所以按照时间显示情况对数据进行了格式化：

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

   * 画出横轴标尺刻度

   */

  private void drawXScale() {

    try{

      Vector v = Scale(m_panelInfo.getXminValue(), m_panelInfo.getXmaxValue());

      boolean b = false;

      //--此处v.size-1主要是计算出来的标尺最后一个值是大于横轴的，所以过滤掉最后一个

      for (int i = 0; i < v.size(); i++) {

        double scale = ( (Double) v.get(i)).doubleValue();

        int place = (int) getPanelScale(scale, 0,0,0).getX();

        //---标出坐标值，为了使坐标值不和横轴重叠，进行了一定的偏移

        m_Graphics.setColor(Color.white);

        m_Graphics.drawString(String.valueOf(scale), place - 10,

                              (m_panelInfo.getHight() - FRAME_OFFSET + 15));

        // 因为第一条线和竖轴重叠，故第一条不画

        if (b) {

          //----画出虚线的刻度轴

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

   * 画出纵轴刻度

   * 纵州的刻度和单位默认初始化Applet时的值，可以设置

   */

  private void drawYScale() {

    try{

      Vector v = Scale(m_yScaleMin,m_yScaleMax);

      //--此处v.size-1主要是计算出来的标尺最后一个值是大于纵轴的，所以过滤掉最后一个

      for (int i = 0; i < v.size(); i++) {

        double scale = ( (Double) v.get(i)).doubleValue();

        int place = (int) getPanelScale(0, scale,m_yScaleMax,m_yScaleMin).getY();

        //---画出竖轴刻度的值

        m_Graphics.setColor(Color.white);

        m_Graphics.drawString(String.valueOf(scale), FRAME_OFFSET - 30, place);

        m_Graphics.drawString(m_panelInfo.getYunit(), FRAME_OFFSET,FRAME_OFFSET - 10);

        if (place != m_panelInfo.getHight() - FRAME_OFFSET) {

          //---画出竖轴刻度虚线

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
   * 画出图的两条相对水平门线
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

   * 根据传人的一组坐标值获得在panle中的实际位置

   * 如果只需要一个值，则，另一个可以传0，返回值中对应值无效

   *

   * @param x:    要显示的x值

   * @param y:    要显示的y值

   * @param yMax: 计算此组数据时y的最大坐标值

   * @param yMin: 计算此组数据时y的最小坐标值

   *

   * @return： 返回一个包含一个坐标值的数据结构

   */

  private scaleInfo getPanelScale(double x, double y,double yMax,double yMin) {

    scaleInfo sc = new scaleInfo();



    try{

      double scaleX = ( (x - m_panelInfo.getXminValue()) *(m_panelInfo.getWidth() - FRAME_OFFSET * 2) /

                      (m_panelInfo.getXmaxValue() - m_panelInfo.getXminValue())) + FRAME_OFFSET;

      sc.setX(scaleX);

      double scaleY = ( (y - yMin) *(m_panelInfo.getHight() - FRAME_OFFSET * 2) /

                      (yMax - yMin)) + FRAME_OFFSET;

      // 因为panel中的Y轴和实际我们画线的是相反的，所以要进行处理

      sc.setY(m_panelInfo.getHight() - scaleY);

    }catch (Exception e){

      e.printStackTrace();

    }

    return sc;

  }



  /**

   * 根据panle中的位置计算出实际的数值(实际是函数getPanelScale的一个逆运算)

   * 如果只需要一个值，则，另一个可以传0，返回值中对应值无效

   *

   * @param x: 要显示的x值

   * @param y: 要显示的y值

   * @param yMax: 计算此组数据时y的最大坐标值

   * @param yMin: 计算此组数据时y的最小坐标值

   *

   * @return： 返回一个包含一个坐标值的数据结构

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

   * 在指定坐标的位置画出一条浅灰色的虚线

   * @param dashlen  ： 虚线中实线的长度

   * @param blanklen ：虚线中空白的长度

   * @param left      ：所画虚线距离左边距

   * @param top       : 所画虚线距离顶端的距离

   * @param len       : 所画虚线的长度

   * @param c         ：虚线的颜色

   * @param direction ：0：横线，1：竖线

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

   * 根据外部对某条线的颜色的设置利用此颜色重画这条线，如果是画直线就取出每个点和X轴画线，

   * 如果是画任意线段则取出两个点连线即可，同时加入了每条线的说明，以及鼠标位置的说明

   * 把vChartData中的数据画成数据线

   */

  private void drawView() {

    try {

      int descX = FRAME_OFFSET-15;

      for (Iterator i = vChartData.iterator(); i.hasNext(); ) {

        chartDataInfo chart = (chartDataInfo) i.next();

        String isDraw = (String) m_isDraw.get(String.valueOf(chart.getType()));

        if (isDraw != null) { // 判断此线是否显示

          if (isDraw.equalsIgnoreCase("0") || isDraw.equalsIgnoreCase("false")) {

            continue;

          }

        }

        Font f = new Font("宋体", Font.PLAIN, 12);

        m_Graphics.setFont(f);

        drawColor(chart.getType()); // 根据此条线的设置来改变颜色

        // 用来显示每条线的说明，先画一个正方形，然后后面跟一个说明：

        m_Graphics.fillRect(descX, m_panelInfo.getHight()-FRAME_OFFSET + 20, 10, 10);

        m_Graphics.drawString(chart.getDesc(), descX + 15,m_panelInfo.getHight()- FRAME_OFFSET + 30);

        descX = descX + chart.getDesc().length()*f.getSize() + 50;

        drawColor(chart.getColor());

        if (m_panelInfo.getType() == 1) { // 画竖线

          drawViewLine(chart);

        }else { // 画任意两点的直线

          drawViewCurve(chart);

        }

      }

    }catch (Exception e) {

      e.printStackTrace();

    }

  }



  /**

   * 根据获得的数据画一条以X轴为低的直线

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

   * 根据获得的数据画两点的任意一条直线

   * @param chart

   */

  private void drawViewCurve(chartDataInfo chart){

    try {

      Vector temp = chart.getScaleInfo();

      if (temp.size() > 1) { // 当数据中有两个点时，同时取出这两个点连线

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

   * 根据不同的类型改变画线的颜色

   * @param type：1：red,2:green,3:blue,4:yellow,5:orange,6:cyan,其他:black

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

   * 画拖拽框

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

   * 判断要画线的点是否在实际X,Y的值的范围之内(这里的范围是指坐标显示的值)

   * @param s:

   * @param yMax: 计算此组数据时y的最大坐标值

   * @param yMin: 计算此组数据时y的最小坐标值



   * @return : 如果在范围之内，则返回，此坐标在panel中的位置，否正返回null

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

   * 根据要画的线在水平门线的位置，设置不同的颜色

   * 如果大于最大水平线则画线颜色为red,如果小于最低水平线，则用blue

   * @param y

   */

  private void setLineColor(double y){

    if (y > m_panelInfo.getLevelMax()){ // 大于大的水平线用红线画

      m_Graphics.setColor(Color.red);

    }else if(y < m_panelInfo.getLevelMin()){ // 小于小的水平线用蓝线画

      m_Graphics.setColor(Color.blue);

    }

  }





  /**

   * 判断数据是否超出X轴的坐标最大值

   * 如果是画竖线  ： X值超出X轴的最大值，则停止取数的线程

   * 如果是画任意线： X值超出X轴的最大值，则X轴的坐标最大值修改为此值

   *                X轴的坐标的最小值改为 X-X轴最大值 + 原来最小值

   *

   * @param x   ：要判断的X值

   * @param type: 画线的重量 1：画普通竖线，2：画任意两点的线

   */

  private void dataIsOverStepX(String xValue,int type){

    try {

      double x = 0;

      if (xValue != null) {

        x = new Double(xValue).doubleValue();

      }

      if (m_panelInfo.getType() == 1) { // 如果画竖线，查询回来的值大于坐标最大值则停止取数线程

        if (x >= m_initXMax) {

         ;

        }

      }else {

        if (x >= m_initXMax) {

          m_initXMin = x - m_initXMax + m_initXMin;

          m_initXMax = x;

          m_panelInfo.setXminValue(m_initXMin);

          m_panelInfo.setXmaxValue(m_initXMax);

          for (int count = vChartData.size(), i = 0; i < count; i++) { // 依次查询已有数据，看是否有此条线的数据

            chartDataInfo chart = (chartDataInfo) vChartData.get(i);

            if (type == chart.getType()) { // 在有数据中找到相同类型的数据，添加新点的信息

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

   * 鼠标移动事件

   *

   * 如果是画普通竖线：当鼠标移动到某条线的前后两个象数时，取出此条线的说明

   * @param e：

   */

  public void mouseMoved(MouseEvent e) {

    try {

      e.consume();

      int x = e.getX();

      int y = e.getY();

      boolean bdesc = true;

      if (mouseInCord(x, y)) {

        if (m_panelInfo.getType() == 1) { // 画普遍竖线

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

        }else{ // 画任意两点的线

          setGdDesc(e);

        }

        repaint();

      }

    }catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  // 判断鼠标是否在画图的坐标中

  public boolean mouseInCord(int x, int y) {

    if (x > FRAME_OFFSET && x < m_panelInfo.getWidth()-FRAME_OFFSET

        && y > FRAME_OFFSET && y < m_panelInfo.getHight()-FRAME_OFFSET){

      return true;

    }

    return false;

  }



  /**

   * 按住鼠标键事件

   * 如果按下鼠标左键+shift键，则作为拖拽的开始位置

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



  // 获得鼠标拖动的坐标

  public void mouseDragged(MouseEvent e) {

    e.consume();

     if (m_bDrag) {

       m_nDragEndX = e.getX();

       m_nDragEndY = e.getY();

       repaint();

     }

  }





  /**

   * 释放鼠标事件

   * 如果鼠标释放时并且同时shift键也释放：鼠标如果拖到图像外边，则取消拖拽，直接返回

   *      否则根据初始设置放大X轴和Y轴

   * @param e

   */

  public void mouseReleased(MouseEvent e) {

    try {

      e.consume();

      if (e.isShiftDown() && (e.getModifiers() & e.BUTTON1_MASK) > 0) {

        if (!mouseInCord(e.getX(), e.getY())) { // 如果鼠标拖动到图像外，直接返回

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

   * 鼠标单击事件

   * 如果时单击鼠标右键，则返回原始坐标大小

   * 如果时单击鼠标左键并且时画普通竖线，则取出光标位置左右两个象数的值对应的url，直接刷新

   * @param e

   */

  public void mouseClicked(MouseEvent e) {

    try {

      e.consume();

      if (e.getModifiers() == e.BUTTON3_MASK) { // 单击鼠标右键，返回原始尺寸

        m_panelInfo.setXmaxValue(m_initXMax);

        m_panelInfo.setXminValue(m_initXMin);

        repaint();

      }

      if ( ( (e.getModifiers() & e.BUTTON1_MASK) > 0) && (!e.isShiftDown())) { // 单击鼠标左键

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

   * 对X轴的坐标进行放大

   *

   * 取出拖拽的开始的X坐标设置到panel初始化参数的X坐标的最小值中

   * 取出拖拽的结束的X坐标设置到panel初始化参数的X坐标的最大值中

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

   * 对Y轴的坐标进行放大

   *

   * 取出拖拽的开始的Y坐标设置到panel初始化参数的Y坐标的最小值中

   * 取出拖拽的结束的Y坐标设置到panel初始化参数的Y坐标的最大值中

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

   * 把要画线的点加入到vChartData中，根据传人的数据的类型，

   * 把这些数据加入到各自的线的数据中，然后统一刷新，同时画多条线

   *

   * @param strGDSet：要画线的数据，利用一个串传人，然后直接转为GDSet结构

   */

  public void addChartData(String strGDSet ){

    try {

      boolean b = false;

      GDSet gd = GDSetTool.parseStringToGDSet(strGDSet);

      for (int row = gd.getRowCount(), i=0; i < row; i++){ // 依次处理获得的每个数据

        int type = new Integer(gd.getString(i,"type")).intValue();

        for (int count = vChartData.size(),j = 0; j < count; j++ ){ // 依次查询已有数据，看是否有此条线的数据

          chartDataInfo chart = (chartDataInfo)vChartData.get(j);

          if (type == chart.getType()){ // 在有数据中找到相同类型的数据，添加新点的信息

            scaleInfo scale = new scaleInfo();

            scale.setX(new Double(gd.getString(i,"X")).doubleValue());

            scale.setY(new Double(gd.getString(i,"Y")).doubleValue());

            scale.setdesc(gd.getString(i,"desc"));

            scale.setUrl(gd.getString(i,"url"));

            Vector temp = chart.getScaleInfo();

            //{{--判断如果要添加的画图数据的X小于已有数据的X则，不添加此数据--:

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

            dataIsOverStepX(gd.getString(i,"X"),type);  // 判断X的值如果超出X轴的坐标，则做相应处理

            b = true;

            break;

          }

        }

        if (!b){  // 如果没有此条线的数据,则说明此条线是新加的，把它的数据加到vChartInfo中

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



        for (int count = vChartData.size(),j = 0; j < count; j++ ){ // 依次查询已有数据，看是否有此条线的数据

          chartDataInfo chart = (chartDataInfo)vChartData.get(j);

          if (type == chart.getType()){ // 在有数据中找到相同类型的数据，添加新点的信息

            scaleInfo scale = new scaleInfo();

            scale.setX(new Double(x).doubleValue());

            scale.setY(new Double(y).doubleValue());

            scale.setdesc(desc);

            scale.setUrl(url);

            Vector temp = chart.getScaleInfo();

            //{{--判断如果要添加的画图数据的X小于已有数据的X则，不添加此数据--:

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

            dataIsOverStepX(x,type);  // 判断X的值如果超出X轴的坐标，则做相应处理

            b = true;

            break;

          }

        }

        if (!b){  // 如果没有此条线的数据,则说明此条线是新加的，把它的数据加到vChartInfo中

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

   * 如果是画任意两点的线，设置鼠标移动位置处的说明

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

          if (isDraw != null) { // 判断此线是否显示

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

                (sPanel1.getX() + 2 > x && x > sPanel2.getX() - 2)) { // 鼠标在两点的X轴范围之内

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

   *画出鼠标所在位置的线的描述

   */

  private void drawTip(){

    try {

      Font f = new Font("宋体", Font.PLAIN, 12);

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

   * 设置Y轴要显示的标尺和单位

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
