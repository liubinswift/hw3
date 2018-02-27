package com.viewscenes.applet.quality;



import java.io.Serializable;

/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class scaleInfo implements Serializable {

  public scaleInfo() {

  }



  private double x;  // 坐标X轴

  private double y;  // 坐标Y轴

  private String desc; // 对这个点的描述

  private String url;  // 这个点关联的URL



  public double getX() {

    return x;

  }

  public double getY() {

    return y;

  }

  public void setX(double x) {

    this.x = x;

  }

  public void setY(double y) {

    this.y = y;

  }

  public String getdesc() {

    return desc;

  }

  public String getUrl() {

    return url;

  }

  public void setdesc(String desc) {

    this.desc = desc;

  }

  public void setUrl(String url) {

    this.url = url;

  }



}
