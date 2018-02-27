package com.viewscenes.util;



import java.io.*;

import java.util.*;



/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2002</p>

 * <p>Company: </p>

 * @author dyt

 * @version 1.0

 */



public class ConfigFile {



  String _fileName;



  HashMap map = null;



  public ConfigFile(String fileName) {

    _fileName = fileName;

    map = null;

  }



  /**

   * 设置配置文件名(缺省为c:\MonWebConfig.sys)

   * @param fileName

   */

  public synchronized void setFileName(String fileName) {

    _fileName = fileName;

    map = null;

  }



  /**

   * 从配置文件中获取参数值

   * @param name

   * @return

   */

  public synchronized String getPara(String name) throws ConfigFileException {

    try {

      //{{add by 2002-10-26

      name = name.toLowerCase();

      if (map == null) {

        getConfig();

      }



      if (map == null) {

        throw new ConfigFileException("getConfig Null Property's Pointer");

      }



      Object obj = map.get(name);



      if (obj == null) {

        throw new ConfigFileException("Can't find the paraname:" + name);

      }



      return (String) obj;

    }

    catch (ConfigFileException e) {

      throw e;

    }

    catch (Exception e) {

      throw new ConfigFileException(e.getMessage(), e);

    }

  }



  /**

   * 读取配置数据到map中

   * @return

   * @throws ConfigFileException

   */



  public synchronized boolean getConfig() throws

      ConfigFileException {

    //如果hashmap已经把配置文件读入，则不再读

    if (map != null) {

      return true;

    }



    BufferedReader in = null;

    try {

      //new 一个hashmap

      map = new HashMap();

      //把文件读到DataInputStream



      Class cls = this.getClass(); //Class.forName("com.viewscenes.util");



      in = new BufferedReader(new InputStreamReader(

          cls.getClassLoader().getResourceAsStream(_fileName)));



      String s = null;

      //循环取文件的每一行,到null表示文件结束

      while ( (s = in.readLine()) != null) {

        s = s.trim();

        if (s.startsWith("//")) { // 去掉注释行

          continue;

        }



        //如果该行有'=',则把=前的字串作为map的index,=后的作为map的值

        int n = s.indexOf('=');

        if (n != -1) {

          map.put(s.substring(0, n).trim(), s.substring(n + 1).trim()); // modified by dyt 2002-11-04

        }

      }

    }

    catch (Exception e) {

      throw new ConfigFileException(e.getMessage(), e);

    }

    finally {

      try {

        if (in != null) {

          in.close();

        }

      }

      catch (Exception e) {

        throw new ConfigFileException(e.getMessage(), e);

      }

    }

    return true;

  }

}

