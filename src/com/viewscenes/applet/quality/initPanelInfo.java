package com.viewscenes.applet.quality;



import java.io.Serializable;



public class initPanelInfo implements Serializable{

  public initPanelInfo() {

  }



  private int width; // ��ʾ��Applet�Ŀ��

  private int hight; // ��ʾ��Applet�ĸ߶�



  private double XminValue; // X������̶�

  private double XmaxValue; // X�����С�̶�



  private double YminValue; // Y������̶�

  private double YmaxValue; // Y�����С�̶�



  private double levelMin;  // ˮƽ��Сֵ

  private double levelMax;  // ˮƽ���ֵ



  private int type; // ��������1:����ͨ���ߣ�2���������������



  private String Xunit; // X��ĵ�λ

  private String Yunit = "dB"; // Y��ĵ�λ

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
