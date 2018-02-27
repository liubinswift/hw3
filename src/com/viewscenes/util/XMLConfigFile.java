package com.viewscenes.util;

import java.net.*;
import org.jdom.*;
import java.io.*;

public class XMLConfigFile {
  public XMLConfigFile() {
  }

  /**
   * 在ClassPath中查找配置文件，并返回文件的绝对路径
   * @param fileName 配置文件的名字
   * @return
   */
  public static String getConfigFilePath(String fileName) {
    String path = null;
    try {
      URL u = XMLConfigFile.class.getClassLoader().getResource(fileName);
      if (u != null) {
        path = u.getFile();
      }
      else {
        path = null;
      }
    }
    catch (Exception e) {
      LogTool.debug(e);
    }
    return path;
  }
  /**
   * 解析指定XML文件
   * @return：返回解析后的根节点
   */
  public static Element getRootElement(String fileName) throws IOException,UtilException {
    try {
      String content = FileTool.FileToString(fileName);
      if (content==null)
        throw new IOException("Can't read file "+fileName);
      StringReader read = new StringReader(content);
      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);
      Document doc = builder.build(read);
      return doc.getRootElement();
    }
    catch (Exception ex) {
      throw new UtilException("解析XML文件错误", ex);
    }
  }


  /**
   * 解析指定XML字符串
   * @return：返回解析后的根节点
   */
  public static Element getStringRootElement(String content) throws IOException,UtilException {
    try {

      StringReader read = new StringReader(content);
      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);
      Document doc = builder.build(read);
      return doc.getRootElement();
    }
    catch (Exception ex) {
      throw new UtilException("解析XML文件错误", ex);
    }
  }


  public static void main(String[] args) {
    try {
      String path = XMLConfigFile.getConfigFilePath("mapping.xml");
      XMLConfigFile.getRootElement(path);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
