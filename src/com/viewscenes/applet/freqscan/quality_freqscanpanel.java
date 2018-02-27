package com.viewscenes.applet.freqscan;

/**

 * <p>Title: Quality_Courve</p>

 * <p>Description: Generate quality courve image</p>

 * <p>Copyright: Copyright (c) 2002</p>

 * <p>Company: Novel-Tongfang</p>

 * @author Chengang

 * @version 1.0

 */



import java.awt.event.*;

import java.awt.*;

import java.applet.*;

import java.awt.*;

import java.awt.image.*;

import java.util.*;

import java.net.*;



class quality_freqscanpanel extends Panel implements MouseListener, MouseMotionListener {

    public BufferedImage m_imgCourve;

    public Graphics m_Graphics;

    public int m_nWidth = 750;

    public int m_nHeight = 395;

    public int m_nCourveHeight = 320;

    public int m_nLeftMargin = 30;

    public int m_nRightMargin = 30;

    public int m_nTopMargin = 20;

    public int m_nBottomMargin = 30;

    public int m_nTopCordMargin = 15;

    public int m_nBottomCordMargin = 0;

    public int m_nLeftCordMargin = 1;

    public int m_nRightCordMargin = 35;

    public float m_fFreqMax = (float)855.25;

    public float m_fFreqMin = (float)49.75;

    public float m_fFreqStep = 8;

    public String m_sFreqStep = "8";

    public float m_fLevelMax = 120;

    public float m_fLevelMin = 0;

    public float m_fLevelLimit = 70;

    public int m_nLevelLine;

    public int m_nSelected=-1;

    public long m_lHeadid = 0;

    public float m_fFreq = 0;

    public int m_nHScaleCount = 10;

    public int m_nVScaleCount = 5;


    public float m_fStartFreq = 0;

    public float m_fEndFreq = 0;

    public int m_nGetDataCount = 0;



    public boolean m_bStandard=false;



    public boolean m_bDrag = false;

    public int m_nDragStartX = 0;

    public int m_nDragStartY = 0;

    public int m_nDragEndX = 0;

    public int m_nDragEndY = 0;



    public boolean m_bSelect = false;

    public int m_nSelectStart = 0;

    public int m_nSelectEnd = 0;

    public float m_fSelectFreqMin = 0;

    public float m_fSelectFreqMax = 0;



    public boolean m_bDragLimit = false;

    public float m_fScale = 1;


    Vector levels = new Vector();

    Vector freqs = new Vector();

    Vector chnames = new Vector();

    Vector vData = new Vector();

    Vector m_xCord = new Vector();

    Vector m_yCord = new Vector();

    int x1;int y1;

    int x2;int y2;

    String content;

    com.viewscenes.applet.freqscan.quality_freqscanstatus m_Status;

    com.viewscenes.applet.freqscan.quality_freqscan m_parent;



    public quality_freqscanpanel(com.viewscenes.applet.freqscan.quality_freqscanstatus status) {

        setBackground(Color.white);

        addMouseMotionListener(this);

        addMouseListener(this);

        m_Status = status;

    }


    public void clearValues(){
        this.freqs.clear();
        this.levels.clear();
        this.chnames.clear();
        this.repaint();
    }


   public void addNewValue(int freq, int value, String chname){

         Float f = new Float((float)freq/1000);
         Float l = new Float((float)value/1000);
         String ch = chname;


           this.freqs.add(f);

           this.levels.add(l);

           this.chnames.add(ch);


     this.repaint();

   }





    public void initbackimage(){

      m_imgCourve = new BufferedImage(m_nWidth, m_nHeight, BufferedImage.TYPE_INT_RGB);
      m_Graphics = m_imgCourve.getGraphics();
    }



    public void mouseDragged(MouseEvent e) {

      e.consume();

      if (m_bDrag){

        m_nDragEndX = e.getX();

        m_nDragEndY = e.getY();

//        repaint(m_nDragStartX-1,m_nDragStartY-1,e.getX()-m_nDragStartX+2,e.getY()-m_nDragStartY+2);

        repaint();

      }

      if (e.isControlDown()&&(e.getModifiers()&e.BUTTON1_MASK)>0&&m_bDragLimit){

          if (mouseInCord(e.getX(),e.getY())){

                  m_fLevelLimit = CalcLevelbyCord(e.getY());

              }

          repaint();

       }

    }



    public void mouseMoved(MouseEvent e) {

      e.consume();





      if (!e.isShiftDown()&&!e.isControlDown()) {

        if (mouseInCord(e.getX(),e.getY())){

          float selectfreq = CalcFreqByCord(e.getX());

          int i=0;

          boolean bFound=false;

          while ((!bFound)&&(i<freqs.size())){

            Float freq = (Float)freqs.elementAt(i);

            if (Math.abs(selectfreq-freq.floatValue())<=m_fFreqStep/3){

              bFound=true;

            }

            i++;

          }

          if (bFound)

          {

            i--;

            float freq = ((Float)freqs.elementAt(i)).floatValue();

            float level = ((Float)levels.elementAt(i)).floatValue();


            String sFreq ="频率:" + String.valueOf(freq) +"MHz ";

            String sLevel = "电平:" + String.valueOf(level) +"dBuV "  ;

            String sChname = "频道名称:" + (String)chnames.elementAt(i);
            m_parent.setStatusMsg(sFreq+sLevel+sChname);

          }

          else{

            m_parent.setStatusMsg("");

          }

       }

     }

    }

    public boolean mouseInCord(int x, int y){

      if ((x>m_nLeftMargin+m_nLeftCordMargin)

          &&(x<m_nWidth-m_nRightMargin-m_nRightCordMargin)

          &&(y>m_nTopMargin-m_nTopCordMargin)

          &&(y<m_nHeight-m_nBottomMargin-m_nBottomCordMargin)){

        return true;

      }

      return false;

    }



    public void mousePressed(MouseEvent e) {

        e.consume();

        if (e.isShiftDown()&&(e.getModifiers()&e.BUTTON1_MASK)>0){

          if (mouseInCord(e.getX(),e.getY())){

              m_bDrag = true;

              m_nDragStartX = e.getX();

              m_nDragStartY = e.getY();

          }

        }

        if (e.isControlDown()&&(e.getModifiers()&e.BUTTON1_MASK)>0){

          if (mouseInCord(e.getX(),e.getY())){

             if (Math.abs(e.getY()-m_nLevelLine)<5.0){

               m_bDragLimit = true;

             }

          }

        }

    }



    public void mouseReleased(MouseEvent e) {

       e.consume();

       if (e.isControlDown()&&(e.getModifiers()&e.BUTTON1_MASK)>0&&m_bDragLimit){

           if (mouseInCord(e.getX(),e.getY())){

             m_fLevelLimit = CalcLevelbyCord(e.getY());

           }

           m_bDragLimit = false;

           repaint();

       }



       if (e.isShiftDown()&&(e.getModifiers()&e.BUTTON1_MASK)>0) {

          if (!mouseInCord(e.getX(),e.getY())) {

            m_bDrag = false;

            repaint();

            return;

          }

        int x1 = m_nDragStartX;

        int x2 = e.getX();

        int x = x2-x1;

        if (x1>x2)

          x = x1-x2;

        if (m_bDrag){

          if (x>10){

             m_nDragEndX = e.getX();

             m_nDragEndY = e.getY();

             m_nSelectEnd = m_nDragEndX;

             m_nSelectStart = m_nDragStartX;

             if (m_nDragStartX>m_nDragEndX){

                 m_nSelectStart = m_nDragEndX;

                 m_nSelectEnd = m_nDragStartX;

             }

             m_fSelectFreqMin = CalcFreqByCord(m_nSelectStart);

             m_fSelectFreqMax = CalcFreqByCord(m_nSelectEnd);

             Vector scales = CalcHScale(m_fSelectFreqMax,m_fSelectFreqMin);

             if (scales.size()>0){

                  m_fSelectFreqMin = ((Float)scales.elementAt(0)).floatValue();

                  m_fSelectFreqMax = ((Float)scales.elementAt(scales.size()-1)).floatValue();

              }



             m_bSelect = true;

//             repaint();

//           repaint(m_nDragStartX-1,m_nDragStartY-1,e.getX()-m_nDragStartX+2,e.getY()-m_nDragStartY+2);

          }

        }

    }

        m_bDrag = false;

        m_nDragStartX = 0;

        m_nDragStartY = 0;

        m_nDragEndX = 0;

        m_nDragEndY = 0;

        repaint();

    }



    public void mouseEntered(MouseEvent e) {

      e.consume();

      repaint();

    }



    public void mouseExited(MouseEvent e) {

      e.consume();

      repaint();

    }



    public void mouseClicked(MouseEvent e) {

      e.consume();

      if (e.getModifiers()==e.BUTTON3_MASK){

          m_bSelect = false;

          m_bDrag = false;

          m_nSelected = -1;

          repaint();

         }

      if (((e.getModifiers()&e.BUTTON1_MASK)>0)&&(!e.isShiftDown())){

        float selectfreq = CalcFreqByCord(e.getX());

        int i=0;

        boolean bFound=false;

        while ((!bFound)&&(i<freqs.size())){

          Float freq = (Float)freqs.elementAt(i);

          if (Math.abs(selectfreq-freq.floatValue())<m_fFreqStep/3){

            bFound=true;

          }

          i++;

        }

        if (bFound)

        {

          i--;

          float freq = ((Float)freqs.elementAt(i)).floatValue();

          float level = ((Float)levels.elementAt(i)).floatValue();

          String sFreq ="频率:" + String.valueOf(freq) +"MHz ";

          String sLevel = "电平:" + String.valueOf(level) +"dBuV ";

          String sChname = "频道名称:" + (String)chnames.elementAt(i);
          m_parent.setStatusMsg(sFreq+sLevel+sChname);

          AppletContext ac = m_parent.getAppletContext();

          String sUrl = "controller?modulename=tv_spectrum_scan&action=qualityScan&headid="

                       +String.valueOf(m_lHeadid)+"&freq="+String.valueOf(freq)+

                       "&chname="+(String)chnames.elementAt(i);
         m_parent.play(String.valueOf(freq));
//          String sUrl = "redirectservlet?pagename=quality_realtimesignalframe&showlink=1&headid="

//                      +String.valueOf(m_lHeadid)+"&freq="+String.valueOf(freq);

          String path = m_parent.getDocumentBase().toString();

          int loc = path.lastIndexOf("/");

          path = path.substring(0,loc)+"/"+sUrl;

          if (m_lHeadid>0){

              try{

              URL u = new URL(path);

              //ac.showDocument(u,"qualityScan");

              }

              catch(Exception ee){

                ee.printStackTrace();

              }

          }

          m_Status.repaint();

        }

        else{


            m_parent.setStatusMsg("");

        }

      }

    }



    public void update(Graphics g) {

      DrawBackGround();

      DrawCordinate();

      DrawLimit();

      drawVScale2();

      if (m_bSelect){

        DrawHScale2(m_fSelectFreqMax, m_fSelectFreqMin);

        DrawCourve(m_fSelectFreqMax, m_fSelectFreqMin);

      }

      else{

        DrawHScale2(m_fFreqMax, m_fFreqMin);

        DrawCourve(m_fFreqMax, m_fFreqMin);

      }

      DrawTitle(m_Status.m_sTitle);

      DrawSelectRect();

      g.drawImage(m_imgCourve,0,0,this);

    }



   public void DrawSelectRect(){

     if ((m_bDrag)&&(m_nDragEndX>0)){

       m_Graphics.setColor(Color.cyan);

       int x1,x2,y1,y2;

       x1 = m_nDragStartX;

       x2 = m_nDragEndX;

       y1 = m_nDragStartY;

       y2 = m_nDragEndY;

       if (m_nDragStartX>m_nDragEndX){

           x1 = m_nDragEndX;

           x2 = m_nDragStartX;

       }

       if (m_nDragStartY>m_nDragEndY){

           y1 = m_nDragEndY;

           y2 = m_nDragStartY;

       }

       m_Graphics.drawRect(x1,y1,x2-x1,y2-y1);

     }

   }



    public void DrawBackGround(){

  m_Graphics.setColor(Color.black);//

  m_Graphics.fillRect(0,0,m_nWidth,m_nHeight);

}



public void DrawCordinate(){

  m_Graphics.setColor(Color.white);

  m_Graphics.fillRect(m_nLeftMargin-1,m_nTopMargin,1,m_nHeight-m_nTopMargin-m_nBottomMargin+1);

  m_Graphics.fillRect(m_nLeftMargin-1,m_nHeight-m_nBottomMargin+1,m_nWidth-m_nLeftMargin-m_nRightMargin+1,1);

//  m_Graphics.drawLine(m_nLeftMargin,m_nTopMargin,m_nLeftMargin+7,m_nTopMargin+10);

//  m_Graphics.drawLine(m_nLeftMargin-1,m_nTopMargin,m_nLeftMargin-8,m_nTopMargin+10);

//  m_Graphics.drawLine(m_nWidth-m_nRightMargin,m_nHeight-m_nBottomMargin,m_nWidth-m_nRightMargin-10,m_nHeight-m_nBottomMargin-7);

//  m_Graphics.drawLine(m_nWidth-m_nRightMargin,m_nHeight-m_nBottomMargin+1,m_nWidth-m_nRightMargin-10,m_nHeight-m_nBottomMargin+8);

  Font f = new Font("宋体",Font.PLAIN,12);

  m_Graphics.setFont(f);

  m_Graphics.drawString("dBuV",m_nLeftMargin-28,m_nTopMargin+3);

  m_Graphics.drawString("MHz",m_nWidth-m_nRightMargin-9,m_nHeight-m_nBottomMargin+15);

//  m_Graphics.drawString("size="+String.valueOf(values.size()),0,0);

}



public void drawVScale2(){

  m_Graphics.setColor(Color.white);

  Font f = new Font("宋体",Font.PLAIN,12);

  m_Graphics.setFont(f);

  int step = (int)((m_fLevelMax-m_fLevelMin)/m_nVScaleCount);

  int startLevel = (int)m_fLevelMin;

  int[] scaleArray = new int[6];

  scaleArray[0] = 1;

  scaleArray[1] = 5;

  scaleArray[2] = 10;

  scaleArray[3] = 20;

  scaleArray[4] = 50;

  scaleArray[5] = 100;

  int k = 0;

  while ((step>=scaleArray[k])&&(k<6)){

     k++;

  }

  if (k==0) k=1;

  int stepScale = scaleArray[k-1];

  k = 0;

  int scalepoint = stepScale*k;

  Vector scaleLevel = new Vector();

  while (scalepoint<=m_fLevelMax){

    if (scalepoint+stepScale>m_fLevelMin){

        scaleLevel.add(new Float(scalepoint));

    }

    k++;

    scalepoint = stepScale*k;

  }

  Vector scalePoint = CalcYCord(scaleLevel);

  for (int i=0;i<scalePoint.size();i++){

    Long y = (Long)scalePoint.elementAt(i);

//    m_Graphics.drawLine(m_nLeftMargin,y.intValue(),m_nLeftMargin+6,y.intValue());

    drawDashLine(4,4,m_nLeftMargin,y.intValue(),m_nWidth-m_nRightMargin-m_nRightCordMargin-m_nLeftMargin-m_nLeftCordMargin,Color.gray,0);

    Float fLevel = (Float)scaleLevel.elementAt(i);

    String s = String.valueOf(fLevel.intValue());

    while (s.length()<4){

      s = " "+s;

    }

    m_Graphics.setColor(Color.white);

    m_Graphics.drawString(s,m_nLeftMargin-28,y.intValue()+5);

    if (i<scalePoint.size()-1)

    {

        Long y2 = (Long)scalePoint.elementAt(i+1);

        int middley = (y.intValue()+y2.intValue())/2;

//        m_Graphics.drawLine(m_nLeftMargin,middley,m_nLeftMargin+3,middley);

        drawDashLine(4,4,m_nLeftMargin,middley,m_nWidth-m_nRightMargin-m_nRightCordMargin-m_nLeftMargin-m_nLeftCordMargin,Color.gray,0);

    }

  }

}





//direction 0 for horizontal, 1 for vertical

public void drawDashLine(int dashlen, int blanklen, int left, int top, int len, Color c, int direction ){

  m_Graphics.setColor(c);

  int drawlen = 0;

  while (drawlen<len){

    int start = drawlen;

    drawlen = drawlen+dashlen;

    if (drawlen>len){

      drawlen=len;

    }

    int end = drawlen;

    if (direction==0)

      m_Graphics.drawLine(left+start,top,left+end,top);

    else

      m_Graphics.drawLine(left,top+start,left,top+end);

    drawlen = drawlen+blanklen;

  }

}





public Vector CalcHScale(float freqmax, float freqmin){

  long step = (long)((freqmax-freqmin)*1000/m_nHScaleCount);

  long startFreq = (long)(freqmin*1000);

  int[] scaleArray = new int[3];

  scaleArray[0] = 1;

  scaleArray[1] = 2;

  scaleArray[2] = 5;

  while (step>scaleArray[2]*1.5){

    scaleArray[0] = scaleArray[0]*10;

    scaleArray[1] = scaleArray[1]*10;

    scaleArray[2] = scaleArray[2]*10;

  }

  long scaleStep = 0;

  if (step>=scaleArray[2])

  {

      scaleStep = scaleArray[2];

  }

  else if (step>=scaleArray[1]){

           scaleStep = scaleArray[1];

        }

        else {

           scaleStep = scaleArray[0];

        }

  m_fScale = ((float)scaleStep)/1000;

  int k = 0;

  long scalepoint = scaleStep*k;

  Vector scaleFreq = new Vector();

  while (scalepoint<freqmax*1000){

    if (scalepoint+scaleStep>freqmin*1000){

           scaleFreq.add(new Float(((float)scalepoint)/1000));

       }

       k++;

       scalepoint = scaleStep*k;

     }

  scaleFreq.add(new Float(((float)scalepoint)/1000));

  return scaleFreq;

}



public void DrawHScale2(float freqmax, float freqmin){

  m_Graphics.setColor(Color.white);

  Font f = new Font("宋体",Font.PLAIN,12);

  m_Graphics.setFont(f);

  long step = (long)((freqmax-freqmin)*1000/m_nHScaleCount);

  long startFreq = (long)(freqmin*1000);

  int[] scaleArray = new int[3];

  scaleArray[0] = 1;

  scaleArray[1] = 2;

  scaleArray[2] = 5;

  while (step>scaleArray[2]*1.5){

    scaleArray[0] = scaleArray[0]*10;

    scaleArray[1] = scaleArray[1]*10;

    scaleArray[2] = scaleArray[2]*10;

  }

  long scaleStep = 0;

  if (step>=scaleArray[2])

  {

      scaleStep = scaleArray[2];

  }

  else if (step>=scaleArray[1]){

           scaleStep = scaleArray[1];

        }

        else {

           scaleStep = scaleArray[0];

        }

  m_fScale = ((float)scaleStep)/1000;

   int k = 0;

   long scalepoint = scaleStep*k;

   Vector scaleFreq = new Vector();

   while (scalepoint<=freqmax*1000){

     if (scalepoint+scaleStep>freqmin*1000){

            scaleFreq.add(new Float(((float)scalepoint)/1000));

        }

        k++;

        scalepoint = scaleStep*k;

      }

      Vector scalePoint = CalcXCord(scaleFreq,freqmax,freqmin);

      for (int i=0;i<scalePoint.size();i++){

        Long x = (Long)scalePoint.elementAt(i);

//        m_Graphics.drawLine(x.intValue(),m_nHeight-m_nBottomMargin-m_nBottomCordMargin,x.intValue(),m_nHeight-m_nBottomMargin-8);

        drawDashLine(4,4,x.intValue(),m_nTopMargin+m_nTopCordMargin,m_nHeight-m_nTopMargin-m_nTopCordMargin-m_nBottomMargin-m_nBottomCordMargin,Color.gray,1);

        Float fFreq = (Float)scaleFreq.elementAt(i);

        long nFreq = (long)(fFreq.floatValue()*1000);

        float freq = ((float)nFreq)/1000;

        String s = String.valueOf(freq);

        m_Graphics.setColor(Color.white);

        m_Graphics.drawString(s,x.intValue()-(s.length()*f.getSize())/4+2,m_nHeight-m_nBottomMargin+15);

        if (i<scalePoint.size()-1)

        {

            Long x2 = (Long)scalePoint.elementAt(i+1);

            int middlex = (x.intValue()+x2.intValue())/2;

//            m_Graphics.drawLine(middlex,m_nHeight-m_nBottomMargin-m_nBottomCordMargin,middlex,m_nHeight-m_nBottomMargin-4);

            drawDashLine(4,4,middlex,m_nTopMargin+m_nTopCordMargin,m_nHeight-m_nTopMargin-m_nTopCordMargin-m_nBottomMargin-m_nBottomCordMargin,Color.gray,1);

        }

      }

//      m_Graphics.drawString(String.valueOf((int)freqmin),m_nLeftMargin-8,m_nHeight-m_nBottomMargin+15);





}



public void DrawHScale(float freqmax, float freqmin){

  m_Graphics.setColor(Color.white);

  Font f = new Font("宋体",Font.PLAIN,12);

  m_Graphics.setFont(f);

  int step = (int)((freqmax-freqmin)/m_nHScaleCount);

  int startFreq = (int)freqmin;

  int[] scaleArray = new int[8];

  scaleArray[0] = 1;

  scaleArray[1] = 2;

  scaleArray[2] = 5;

  scaleArray[3] = 10;

  scaleArray[4] = 20;

  scaleArray[5] = 50;

  scaleArray[6] = 100;

  scaleArray[7] = 200;

  int k = 0;

  while ((step>scaleArray[k])&&(k<8)){

     k++;

  }

  int stepScale = scaleArray[k-1];

  k = 0;

  int scalepoint = stepScale*k;

  Vector scaleFreq = new Vector();

  while (scalepoint<=freqmax){

    if (scalepoint>freqmin+stepScale/3){

        scaleFreq.add(new Float(scalepoint));

    }

    k++;

    scalepoint = stepScale*k;

  }



  Vector scalePoint = CalcXCord(scaleFreq,freqmax,freqmin);

  for (int i=0;i<scalePoint.size();i++){

    Long x = (Long)scalePoint.elementAt(i);

    m_Graphics.drawLine(x.intValue(),m_nHeight-m_nBottomMargin-m_nBottomCordMargin,x.intValue(),m_nHeight-m_nBottomMargin-8);

    Float fFreq = (Float)scaleFreq.elementAt(i);

    String s = String.valueOf(fFreq.intValue());

    m_Graphics.drawString(s,x.intValue()-8,m_nHeight-m_nBottomMargin+15);

    if (i<scalePoint.size()-1)

    {

        Long x2 = (Long)scalePoint.elementAt(i+1);

        int middlex = (x.intValue()+x2.intValue())/2;

        m_Graphics.drawLine(middlex,m_nHeight-m_nBottomMargin-m_nBottomCordMargin,middlex,m_nHeight-m_nBottomMargin-4);

    }

  }

  m_Graphics.drawString(String.valueOf((int)freqmin),m_nLeftMargin-8,m_nHeight-m_nBottomMargin+15);

}



public void DrawVScale(){

  m_Graphics.setColor(Color.white);

  Font f = new Font("宋体",Font.PLAIN,12);

  m_Graphics.setFont(f);

  int step = (int)((m_fLevelMax-m_fLevelMin)/m_nVScaleCount);

  int startLevel = (int)m_fLevelMin;

  int[] scaleArray = new int[6];

  scaleArray[0] = 1;

  scaleArray[1] = 5;

  scaleArray[2] = 10;

  scaleArray[3] = 20;

  scaleArray[4] = 50;

  scaleArray[5] = 100;

  int k = 0;

  while ((step>=scaleArray[k])&&(k<6)){

     k++;

  }

  if (k==0) k=1;

  int stepScale = scaleArray[k-1];

  k = 0;

  int scalepoint = stepScale*k;

  Vector scaleLevel = new Vector();

  while (scalepoint<=m_fLevelMax){

    if (scalepoint>m_fLevelMin+stepScale/3){

        scaleLevel.add(new Float(scalepoint));

    }

    k++;

    scalepoint = stepScale*k;

  }



  Vector scalePoint = CalcYCord(scaleLevel);

  for (int i=0;i<scalePoint.size();i++){

    Long y = (Long)scalePoint.elementAt(i);

    m_Graphics.drawLine(m_nLeftMargin,y.intValue(),m_nLeftMargin+6,y.intValue());

    Float fLevel = (Float)scaleLevel.elementAt(i);

    String s = String.valueOf(fLevel.intValue());

    m_Graphics.drawString(s,m_nLeftMargin-20,y.intValue()+5);

    if (i<scalePoint.size()-1)

    {

        Long y2 = (Long)scalePoint.elementAt(i+1);

        int middley = (y.intValue()+y2.intValue())/2;

        m_Graphics.drawLine(m_nLeftMargin,middley,m_nLeftMargin+3,middley);

    }

  }

}



public void DrawLimit(){

    int p;

    m_Graphics.setColor(Color.green);

    Font f = new Font("宋体",Font.PLAIN,12);

    m_Graphics.setFont(f);

    p =(int)(m_fLevelLimit*m_nCourveHeight/(m_fLevelMax-m_fLevelMin));

    p = m_nHeight-m_nBottomMargin-m_nBottomCordMargin-p;

    m_Graphics.drawLine(m_nLeftMargin+m_nLeftCordMargin,p,m_nWidth-m_nRightMargin-m_nRightCordMargin,p);

//    drawDashLine(4,4,m_nLeftMargin+m_nLeftCordMargin,p,m_nWidth-m_nRightMargin-m_nRightCordMargin-m_nLeftMargin-m_nLeftCordMargin,Color.lightGray,0);

    m_nLevelLine = p;

    m_Graphics.setColor(Color.white);

    long nLimit = (long)m_fLevelLimit*100;

    float fLimit = ((float)nLimit)/100;

    m_Graphics.drawString(String.valueOf(fLimit)+"dBuV",m_nWidth-m_nRightMargin-m_nRightCordMargin+2,p+5);

}



public float CalcLevelbyCord(int y){

  y = m_nHeight-m_nBottomMargin-m_nBottomCordMargin-y;

  float limit = (y*(m_fLevelMax-m_fLevelMin)/m_nCourveHeight);

  return limit;

//  p =(int)(m_fLevelLimit*m_nCourveHeight/(m_fLevelMax-m_fLevelMin));

//  p = m_nHeight-m_nBottomMargin-m_nBottomCordMargin-p;

}





public void DrawTitle(String s){

  Font f = new Font("宋体",Font.BOLD,15);

  Font f2 = new Font("宋体",Font.PLAIN,12);

  m_Graphics.setFont(f);

  m_Graphics.setColor(Color.white);

  m_Graphics.drawString(s,(m_nWidth-m_nRightMargin-m_nRightCordMargin)/2-150,18);

  m_Graphics.setFont(f2);

  m_Graphics.setColor(Color.cyan);

  m_Graphics.fillRect((m_nWidth-m_nRightMargin-m_nRightCordMargin)/2+30,8,10,10);

  m_Graphics.setColor(Color.white);

  m_Graphics.drawString("低于门限频道",(m_nWidth-m_nRightMargin-m_nRightCordMargin)/2+43,17);

  m_Graphics.setColor(Color.red);

  m_Graphics.fillRect((m_nWidth-m_nRightMargin-m_nRightCordMargin)/2+130,8,10,10);

  m_Graphics.setColor(Color.white);

  m_Graphics.drawString("高于门限已知频道",(m_nWidth-m_nRightMargin-m_nRightCordMargin)/2+143,17);

  m_Graphics.setColor(Color.yellow);

  m_Graphics.fillRect((m_nWidth-m_nRightMargin-m_nRightCordMargin)/2+250,8,10,10);

  m_Graphics.setColor(Color.white);

  m_Graphics.drawString("高于门限未知频道",(m_nWidth-m_nRightMargin-m_nRightCordMargin)/2+263,17);

}



public Vector CalcYCord(Vector vData){

  Vector vPoint = new Vector();

  Float lValue;

 int p;

  for (int i=0;i<vData.size();i++){

    lValue = (Float)vData.elementAt(i);

   float x = lValue.floatValue();

    p =(int)(lValue.floatValue()*m_nCourveHeight/(m_fLevelMax-m_fLevelMin));

    p = m_nHeight-m_nBottomMargin-m_nBottomCordMargin-p;

    vPoint.add(i,new Long(p));

  }

  return vPoint;

}

public void DrawCourve(float freqmax, float freqmin){

   Vector drawfreqs = new Vector();

   Vector drawlevels = new Vector();

   Vector drawchnames = new Vector();

   for (int i=0;i<freqs.size();i++){

       Float freq = (Float)freqs.elementAt(i);

       Float level = (Float)levels.elementAt(i);

       String chname = (String)chnames.elementAt(i);

       if ((freq.floatValue()>=freqmin)&&(freq.floatValue()<=freqmax)){

            drawfreqs.add(freq);

            drawlevels.add(level);

            drawchnames.add(chname);

       }

   }

   m_xCord = CalcXCord(drawfreqs, freqmax, freqmin);

   m_yCord = CalcYCord(drawlevels);



  if (m_xCord.size()<1)

    return;

  Long x,y;

  String chname;

  int nCount =0;

  int nUnknowCount = 0;

  for (int i=0;i<m_xCord.size();i++){

      x = (Long)m_xCord.elementAt(i);

      y = (Long)m_yCord.elementAt(i);

/*      if (i>0){

        m_Graphics.setColor(new Color(92,39,52));

        Long x0 = (Long)m_xCord.elementAt(i-1);

        Long y0 = (Long)m_yCord.elementAt(i-1);

        m_Graphics.drawLine(x0.intValue(),y0.intValue(),x.intValue(),y.intValue());

      }

  */   chname = (String)drawchnames.elementAt(i);

      if (y.intValue()>m_nLevelLine)
          m_Graphics.setColor(new Color(20,238,236));
//          m_Graphics.setColor(Color.);
      else{
          m_Graphics.setColor(Color.red);
          nCount++;
          if (chname.equals("未知频道")){

            m_Graphics.setColor(Color.yellow);

            nUnknowCount++;

          }

      }

          m_Graphics.drawLine(x.intValue(),m_nHeight-m_nBottomMargin-m_nBottomCordMargin-1,x.intValue(),y.intValue());

      }

      m_Status.m_sFreqCount = "超过门限的频率数 "+String.valueOf(nCount)

                            +" 个，其中未知频道个数 "+String.valueOf(nUnknowCount)+" 个";

      m_Status.repaint();


}



public float CalcFreqByCord(int x){

  float freqmax = m_fFreqMax;

  float freqmin = m_fFreqMin;

  if (m_bSelect){

    freqmax = m_fSelectFreqMax;

    freqmin = m_fSelectFreqMin;

  }

  int nLen = m_nWidth-m_nLeftMargin-m_nLeftCordMargin-m_nRightMargin-m_nRightCordMargin;

  int xLen = x-m_nLeftMargin-m_nLeftCordMargin;

  float freq = freqmin + (xLen*(freqmax-freqmin)/nLen);

  return freq;

}



public Vector CalcXCord(Vector vData, float freqmax, float freqmin){

  Vector vYCord = new Vector();

  int p,nLen;



  nLen = m_nWidth-m_nLeftMargin-m_nLeftCordMargin-m_nRightMargin-m_nRightCordMargin;

  if (vData.size()<1)

    return vYCord;

  for (int i=0;i<vData.size();i++){

      float f = ((Float)vData.elementAt(i)).floatValue()-freqmin;

      p = (int)(nLen*f/(freqmax-freqmin));

      p = p+m_nLeftMargin+m_nLeftCordMargin;

      vYCord.add(new Long(p));

    }

  return vYCord;
  }

}



