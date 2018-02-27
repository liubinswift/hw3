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

   * 包含多个scaleInfo数据结构

   * 里面包含了所有画线信息

   */

  private Vector scaleInfo;

  /**

   * 画线使用的颜色

   */

  private int color;



  /**

   * 此条线的描述

   */

  private String desc;



  /**

   * 唯一确定一条线，取数据时根据类型来判断数据放到那个数据结构

   */

  private int type;



  // 此条线的y轴坐标小值

  private double yMin;

  // 此条线的y轴坐标大值

  private double yMax;

  // 此条线的y轴单位

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
