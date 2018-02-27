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
public class UpXml {
  public UpXml() {
  }

  public static UpXmlText xmlup = null;
  static {
    xmlup = new UpXmlText();
  }

  public static void setUpXml(String msg) {
    xmlup.setXmlText(msg);
  }

}





