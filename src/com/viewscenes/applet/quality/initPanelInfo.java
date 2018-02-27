package com.viewscenes.applet.quality;



import java.io.Serializable;



public class initPanelInfo implements Serializable{

  public initPanelInfo() {

  }



  private int width; // 显示的Applet的宽度

  private int hight; // 显示的Applet的高度



  private double XminValue; // X轴的最大刻度

  private double XmaxValue; // X轴的最小刻度



  private double YminValue; // Y轴的最大刻度

  private double YmaxValue; // Y轴的最小刻度



  private double levelMin;  // 水平最小值

  private double levelMax;  // 水平最大值



  private int type; // 画线类型1:画普通竖线，2：画任意两点的线



  private String Xunit; // X轴的单位

  private String Yunit = "dB"; // Y轴的单位

  private boolean blowupX;

  private boolean blowupY;



   public int getHight() {

     return hight;

   }

   public int getWidth() {

     return width;

   }

   public void setHight(int hight) {

     this.hight = hight;

   }

   public void setWidth(int width) {

     this.width = width;

   }

  public double getXmaxValue() {

    return XmaxValue;

  }

  public double getXminValue() {

    return XminValue;

  }

  public double getYminValue() {

    return YminValue;

  }

  public void setYminValue(double YminValue) {

    this.YminValue = YminValue;

  }

  public void setXminValue(double XminValue) {

    this.XminValue = XminValue;

  }

  public void setXmaxValue(double XmaxValue) {

    this.XmaxValue = XmaxValue;

  }

  public double getYmaxValue() {

    return YmaxValue;

  }

  public void setYmaxValue(double YmaxValue) {

    this.YmaxValue = YmaxValue;

  }

  public double getLevelMax() {

    return levelMax;

  }

  public double getLevelMin() {

    return levelMin;

  }

  public void setLevelMin(double levelMin) {

    this.levelMin = levelMin;

  }

  public void setLevelMax(double levelMax) {

    this.levelMax = levelMax;

  }

  public int getType() {

    return type;

  }

  public void setType(int type) {

    this.type = type;

  }

  public String getXunit() {

    return Xunit;

  }

  public String getYunit() {

    return Yunit;

  }

  public void setYunit(String Yunit) {

    this.Yunit = Yunit;

  }

  public void setXunit(String Xunit) {

    this.Xunit = Xunit;

  }

  public boolean isBlowupX() {

    return blowupX;

  }

  public boolean isBlowupY() {

    return blowupY;

  }

  public void setBlowupX(boolean blowupX) {

    this.blowupX = blowupX;

  }

  public void setBlowupY(boolean blowupY) {

    this.blowupY = blowupY;

  }

}
