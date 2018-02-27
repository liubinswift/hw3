package com.viewscenes.util.log;



import java.io.*;

import java.util.*;



import org.jdom.*;

import com.viewscenes.util.*;



public class LogManager {



  public final static int LEVEL_DEBUG = 0;

  public final static int LEVEL_INFO = 5;

  public final static int LEVEL_WARNING = 10;

  public final static int LEVEL_FATAL = 100;

  public final static int LEVEL_OUTPUT = 1000;



  public final static int TYPE_NONE = 1;

  public final static int TYPE_FILE = 2;

  public final static int TYPE_SCREEN = 3;

  public final static int TYPE_BOTH = 4;



  public final static int METHOD_NORMAL = 1;

  public final static int METHOD_THREAD = 2;



  public final static int SPLIT_HOURLY = 1;

  public final static int SPLIT_DAILY = 2;

  public final static int SPLIT_WEEKLY = 3;

  public final static int SPLIT_MONTHLY = 4;

  public final static int SPLIT_YEARLY = 5;



  static Map loggerPropertyMap = null;

  static Map loggerMap = null;

  static String m_sConfigFilePath = "logconfig.xml";

  static String m_sDefaultLoggerName = "default";



  public LogManager() {

    loadConfigFile(null);

    createMultiLogger();

  }



  public LogManager(String sConfigPath) {

    loadConfigFile(sConfigPath);

    createMultiLogger();

  }



  public static synchronized void loadConfigFile(String sConfigPath) {

    if (sConfigPath == null) {

      sConfigPath = "logconfig.xml";

    }

    try {

      //把配置文件读到一个串中

      XMLConfigFile configFile = new XMLConfigFile();

      m_sConfigFilePath = configFile.getConfigFilePath(sConfigPath);

      if (m_sConfigFilePath==null)

        m_sConfigFilePath = "c:\\logconfig.xml";

      String str_msg = FileToString(m_sConfigFilePath);

      //得到Document

      StringReader read = new StringReader(str_msg);

      org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);

      Document doc = builder.build(read);



      loggerPropertyMap = new HashMap();

      //得到根节点LIT

      Element config = doc.getRootElement();

      Element dbpoolconfig = config.getChild("LogConfig"); // 得到对应模块的节点

      List dbpoollist = dbpoolconfig.getChildren();

      for (int i = 0; i < dbpoollist.size(); i++) {

        Element pool = (Element) dbpoollist.get(i);

        if (pool.getName().equals("Logger")) {

          String name = pool.getAttributeValue("name");

          String path = pool.getAttributeValue("path");

          String type = pool.getAttributeValue("type");

          String level = pool.getAttributeValue("level");

          String method = pool.getAttributeValue("method");

          String autosplit = pool.getAttributeValue("autosplit");

          LoggerProperty loggerProperty = new LoggerProperty(name, path, type

              , level, method, autosplit);

          loggerPropertyMap.put(name, loggerProperty);

        }

      }

      Element defaultpool = dbpoolconfig.getChild("DefaultLogger");

      m_sDefaultLoggerName = defaultpool.getAttributeValue("name");

    }

    catch (Exception e) {

      e.printStackTrace();

    }

  }



  public void createMultiLogger() {

    loggerMap = new HashMap();

    Iterator it = loggerPropertyMap.entrySet().iterator();

    while (it.hasNext()) {

      Map.Entry entry = (Map.Entry) it.next();

      String sKey = (String) entry.getKey();

      LoggerProperty loggerProp = (LoggerProperty) entry.getValue();

      try {

        Logger logger = new Logger(loggerProp);

        loggerMap.put(loggerProp.logName, logger);

      }

      catch (Exception e) {

        e.printStackTrace();

      }

    }

  }



  public Logger getLogger(String loggerName) {

    Logger logger = (Logger) loggerMap.get(loggerName);

    if (logger == null) {

      logger = (Logger) loggerMap.get(this.m_sDefaultLoggerName);

    }
    return logger;

  }

  public String  getLogPathByName(String loggerName) {

      LoggerProperty loggerProperty= (LoggerProperty) loggerPropertyMap.get(loggerName);

    return loggerProperty.logPath;

  }


  public Logger getLogger() {

    return (Logger) loggerMap.get(m_sDefaultLoggerName);

  }



  /**

   * 将文件转为String

   * @param fileName

   * @return

   */

  private static String FileToString(String fileName) {

    String xml = null;

    try {

      // Find out the length of the file

      File file = new File(fileName);

      long len = file.length();



      // Create an array that's just the right size for the file's

      // contents

      byte raw[] = new byte[ (int) len];



      // Open the file

      FileInputStream fin = new FileInputStream(file);



      // Read all of it into the array; if we don't get all,

      // then it's an error.

      int r = fin.read(raw);

      if (r != len) {

        throw new IOException("Can't read all, " + r + " != " + len);

      }

      fin.close();

      xml = new String(raw);

    }

    catch (Exception e) {

      e.printStackTrace();

    }

    return xml;

  }



}

