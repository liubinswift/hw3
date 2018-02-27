//package quality;

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

import java.text.*;

import java.net.*;



class quality_freqscanstatus extends Panel implements MouseListener{

    String m_sTitle="";


    String m_sMsg = "";


    String m_sCondition = "";

    String m_sFreqCount = "";

    BufferedImage m_bkimg;



    com.viewscenes.applet.freqscan.quality_freqscan m_parent;

    float m_fFreq;

    long m_lHeadid;

    Graphics m_Graphics;

    public quality_freqscanstatus() {

      m_bkimg = new BufferedImage(750, 15, BufferedImage.TYPE_INT_RGB);

      m_Graphics = m_bkimg.getGraphics();

        setLayout(new BorderLayout());

        setBackground(Color.black);

        Label titleLabel = new Label("");

        add("West",titleLabel);

        addMouseListener(this);

    }



    public void update(Graphics g) {



        m_Graphics.setColor(Color.black);

        m_Graphics.fillRect(0,0,750,15);

        m_Graphics.setColor(Color.yellow);

        Font f = new Font("ËÎÌו",Font.PLAIN,12);

        m_Graphics.setFont(f);


        m_Graphics.drawString(m_sMsg,40,10);


        m_Graphics.drawString(m_sFreqCount,440,10);

        g.drawImage(m_bkimg,0,0,null);

    }



    public void mouseDragged(MouseEvent e) {

      e.consume();

    }



    public void mouseMoved(MouseEvent e) {

      e.consume();

    }



    public boolean mouseInCord(int x, int y){

      if ((x>420)

          &&(x<520)

          &&(y>0)

          &&(y<20)){

        return false;

      }

      return false;

    }



    public void mousePressed(MouseEvent e) {

        e.consume();

    }



    public void mouseReleased(MouseEvent e) {

        e.consume();

    }



    public void mouseEntered(MouseEvent e) {

      e.consume();

    }



    public void mouseExited(MouseEvent e) {

      e.consume();

    }



    public void mouseClicked(MouseEvent e) {

      e.consume();

    }



}









