package com.viewscenes.device.framework;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DownXml {

  public DownXml() {
  }

  public static DownXmlText xmldown = null;

  static {
    xmldown = new DownXmlText();
  }

  public static void setDownXml(String msg) {
    xmldown.setXmlText(msg);
  }

}
