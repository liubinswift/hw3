package com.viewscenes.applet.quality;



import java.io.Serializable;

import java.util.*;



/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class chartDataInfo implements Serializable{

  public chartDataInfo() {

  }

  /**

   * �������scaleInfo���ݽṹ

   * ������������л�����Ϣ

   */

  private Vector scaleInfo;

  /**

   * ����ʹ�õ���ɫ

   */

  private int color;



  /**

   * �����ߵ�����

   */

  private String desc;



  /**

   * Ψһȷ��һ���ߣ�ȡ����ʱ�����������ж����ݷŵ��Ǹ����ݽṹ

   */

  private int type;



  // �����ߵ�y������Сֵ

  private double yMin;

  // �����ߵ�y�������ֵ

  private double yMax;

  // �����ߵ�y�ᵥλ

  private String yUnit;



  public int getColor() {

    return color;

  }

  public String getDesc() {

    return desc;

  }

  public Vector getScaleInfo() {

    return scaleInfo;

  }

  public void setColor(int color) {
    this.color = color;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setScaleInfo(Vector scaleInfo) {
    this.scaleInfo = scaleInfo;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {

    this.type = type;

  }

  public double getYMax() {

    return yMax;

  }

  public double getYMin() {

    return yMin;

  }

  public void setYMax(double yMax) {

    this.yMax = yMax;

  }

  public void setYMin(double yMin) {

    this.yMin = yMin;

  }

  public String getYUnit() {

    return yUnit;

  }

  public void setYUnit(String yUnit) {

    this.yUnit = yUnit;

  }

}
