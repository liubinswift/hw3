package com.viewscenes.applet.freqscan;


import java.awt.*;

import java.applet.*;

import java.awt.*;

import java.util.*;

import java.lang.*;
//import netscape.javascript.JSObject;

import com.viewscenes.applet.tcp.*;


public class quality_freqscan extends Applet{

        //中国电视标准频点
     public static  int[][] STD_FREQS = {
                                        {49750,65750},
                                        {77250,85250},
                                        {112250,288250},
                                        {471250,559250},
                                        {607250,855250}
                                        };
    boolean bStandard = false;

    HashMap channelMap = new HashMap();

    GetDataThread dataThread = new GetDataThread();
    String type = "video";
    public String htmlWindowID = "0";

    com.viewscenes.applet.freqscan.quality_freqscanpanel panel;
    com.viewscenes.applet.freqscan.quality_freqscanstatus status;

    public void init() {

        //set layout
        setLayout(new BorderLayout());
        status = new quality_freqscanstatus();
        panel = new quality_freqscanpanel(status);
        panel.m_parent = this;
        status.m_parent = this;

        add("Center", panel);

        add("South",status);

        //set parameter

        setParameter();
        panel.initbackimage();

       //setConnParam("10.1.2.141",8010);
       //setConnParam("192.168.0.110",8010);
        //startVideoFreqScan("49.75","65.75","8");
        //startRadioFreqScan("87","108","0.1");
       //startStandardVideoFreqScan();

        quality_freqscanrefreshtask refreshtask = new quality_freqscanrefreshtask(this);
        Timer getDataTimer = new Timer();
        Date exeDate = new Date();
        getDataTimer.schedule(refreshtask,500,200);
    }

    public void setConnParam(String ip,int port){
        this.dataThread.ip = ip;
        this.dataThread.port = port;
        dataThread.m_parent = this;
    }

    public void setStatusMsg(String msg){
        status.m_sMsg = msg;
        status.repaint();
    }
    public void setHtmlWindowID(String id){
        this.htmlWindowID = id;
    }
    public void startVideoFreqScan(String startFreq,String endFreq,String stepFreq){
        type = "video";
        panel.clearValues();
        status.m_sTitle = "无线电视频谱扫描";
        panel.m_fFreqMin = Float.parseFloat(startFreq);
        panel.m_fFreqMax = Float.parseFloat(endFreq);

        panel.m_fFreqStep = Float.parseFloat(stepFreq);
        byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,stepFreq,"1");
        dataThread.setCommand(cmd);
        dataThread.start();
    }

    public void startRadioFreqScan(String startFreq,String endFreq,String stepFreq){
        type = "radio";
        panel.clearValues();
        status.m_sTitle = "调频广播频谱扫描";
        panel.m_fFreqMin = Float.parseFloat(startFreq);
        panel.m_fFreqMax = Float.parseFloat(endFreq);
        panel.m_fFreqStep = Float.parseFloat(stepFreq);
        byte[] cmd = MonTCPCommand.getRadioFreqScanCommand(startFreq,endFreq,stepFreq,"1");
        //byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,stepFreq,"1");
        dataThread.setCommand(cmd);
        dataThread.start();
    }


    public void stopFreqScan(){
        setStatusMsg("停止接收频谱测量数据");
        this.dataThread.bRun = false;
    }

    public void startStandardVideoFreqScan(){
        panel.clearValues();
        bStandard = true;
        status.m_sTitle = "无线电视标准频谱扫描";
        panel.m_fFreqMin = (float)49.75;
        panel.m_fFreqMax = (float)855.25;
        panel.m_fFreqStep = 8;
        String startFreq = ""+(float)STD_FREQS[0][0]/1000;
        String endFreq = ""+(float)STD_FREQS[0][1]/1000+1;
        byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,"8","1");
        dataThread.setCommand(cmd);
        dataThread.start();
    }
    public void setChannelInfo(String freq, String chName){
        channelMap.put(freq,chName);
    }

    public void clearChannelInfo(){
        System.out.println("clear channel Info.");
        channelMap.clear();
    }
    public void clearValue(){
        panel.clearValues();
    }

    public void setLimitValue(String minFreq,String maxFreq){
        try{
            panel.m_fFreqMin = Float.parseFloat(minFreq);
            panel.m_fFreqMax = Float.parseFloat(maxFreq);
        }catch(Exception ex){
            ex.printStackTrace();
            panel.m_fFreqMin = (float)49.75;
            panel.m_fFreqMax = (float)855.25;
        }
    }
    public void setLevel(String level){
        try{
            System.out.println("set limit level"+level);
            panel.m_fLevelLimit = Float.parseFloat(level);
        }catch(Exception ex){
            ex.printStackTrace();
            panel.m_fLevelLimit = (float)50;
        }
    }
    public void setValue(int freq,int value){
        //四舍五入，保留两位小数
        freq = (freq+5)/10*10;
        value = (value+5)/10*10;

        System.out.println("freq="+freq+",value="+value);

        String chName = (String)channelMap.get(""+(float)freq/1000);

        if (chName==null)
          panel.addNewValue(freq,value,"未知频道");
        else
          panel.addNewValue(freq,value,chName);

    }


    public void addNewValue(int freq,int value){
        //四舍五入，保留两位小数
        freq = (freq+5)/10*10;
        value = (value+5)/10*10;

        System.out.println("freq="+freq+",value="+value);

        String chName = (String)channelMap.get(""+(float)freq/1000);

        if (chName==null)
          panel.addNewValue(freq,value,"未知频道");
        else
          panel.addNewValue(freq,value,chName);

        if (bStandard){
            if (freq==STD_FREQS[0][1]){
                String startFreq = ""+(float)STD_FREQS[1][0]/1000;
                String endFreq = ""+(float)STD_FREQS[1][1]/1000+1;
                byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,"8","1");
                dataThread.setCommand(cmd);
            }
            else if (freq==STD_FREQS[1][1]){
                String startFreq = ""+(float)STD_FREQS[2][0]/1000;
                String endFreq = ""+(float)STD_FREQS[2][1]/1000+1;
                byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,"8","1");
                dataThread.setCommand(cmd);
            }
            else if (freq==STD_FREQS[2][1]){
                String startFreq = ""+(float)STD_FREQS[3][0]/1000;
                String endFreq = ""+(float)STD_FREQS[3][1]/1000+1;
                byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,"8","1");
                dataThread.setCommand(cmd);
            }
            else if (freq==STD_FREQS[3][1]){
                String startFreq = ""+(float)STD_FREQS[4][0]/1000;
                String endFreq = ""+(float)STD_FREQS[4][1]/1000+1;
                byte[] cmd = MonTCPCommand.getVideoFreqScanCommand(startFreq,endFreq,"8","1");
                dataThread.setCommand(cmd);
            }
        }
    }

    public void setParameter(){
      //set Courve Boder
      setCourveBoder();
      setStatusTitle();
      Vector scales = panel.CalcHScale(panel.m_fFreqMax,panel.m_fFreqMin);
      if (scales.size()>0){
        panel.m_fFreqMin = ((Float)scales.elementAt(0)).floatValue();
        panel.m_fFreqMax = ((Float)scales.elementAt(scales.size()-1)).floatValue();
      }
    }

    public void setStatusTitle(){
      String sParam = getParameter("title");
      if (sParam!=null){
            status.m_sTitle = sParam;
      }
    }

    public void setCourveBoder(){
      String sParam = getParameter("Width");
      if (sParam!=null){
          try{
            panel.m_nWidth = Integer.parseInt(sParam);
          }
          catch (NumberFormatException e){
            e.printStackTrace();
          }
      }

      sParam = getParameter("CourveHeight");
      if (sParam!=null){
          try{
            panel.m_nHeight = Integer.parseInt(sParam);
          }
           catch (NumberFormatException e){
            e.printStackTrace();
          }
     }

      sParam = getParameter("RightMargin");
      if (sParam!=null){
          try{
            panel.m_nRightMargin = Integer.parseInt(sParam);
          }
          catch (NumberFormatException e){
            e.printStackTrace();
          }
      }

      sParam = getParameter("LeftMargin");

      if (sParam!=null){
        try{
          panel.m_nLeftMargin = Integer.parseInt(sParam);
        }
       catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("TopMargin");

      if (sParam!=null){
        try{
          panel.m_nTopMargin = Integer.parseInt(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("BottomMargin");

      if (sParam!=null){
        try{
          panel.m_nBottomMargin = Integer.parseInt(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("freqmax");
      if (sParam!=null){
       try{
          panel.m_fFreqMax = Float.parseFloat(sParam);
        }
        catch (NumberFormatException e){
         e.printStackTrace();
        }
      }

      sParam = getParameter("freqmin");

      if (sParam!=null){
        try{
          panel.m_fFreqMin = Float.parseFloat(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("freqstep");

      if (sParam!=null){
        try{
          panel.m_fFreqStep = Float.parseFloat(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("levelmax");

      if (sParam!=null){
        try{
          panel.m_fLevelMax = Float.parseFloat(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("levelmin");

      if (sParam!=null){
        try{
          panel.m_fLevelMin = Float.parseFloat(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }

      sParam = getParameter("levellimit");
      if (sParam!=null){
        try{
          panel.m_fLevelLimit = Float.parseFloat(sParam);
        }
        catch (NumberFormatException e){
          e.printStackTrace();
        }
      }
    }
public void play(String freq){
    System.out.println("this.htmlWindowID = "+this.htmlWindowID);
    //JSObject.getWindow(this).eval("jmask.runtime.getJMaskWindow('"+this.htmlWindowID+"').oWindowContent.play('"+freq+"')");
 //   JSObject.getWindow(this).eval("window.play('"+this.htmlWindowID+"','"+freq+"')");
}



    public void refresh(){
      this.repaint();
      this.panel.repaint();
      this.status.repaint();
    }



    public void destroy() {
        remove(panel);
        remove(status);
    }
}









