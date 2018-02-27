package com.viewscenes.util;



import com.viewscenes.util.log.*;



/**

 *

 * <p>Title: 日志工具</p>

 * <p>Description: 日志工具可记录字符串或异常，记录的信息分为5个级别，按照

 * 从低到高的顺序分别是debug, info, warning, fatal, output。debug级别记录

 * 系统调试信息，info级别记录日常显示信息，warning级别记录一般出错信息，fatal

 * 级别记录严重的系统错误，output级别用来强制记录信息，除非有特殊需要建议不要

 * 在程序中使用</p>

 * <p>在配置文件中可以配置多个logger，每个logger可以使用不同的方式记录日志。在

 * 使用日志工具记录日志时，可以直接使用默认的logger记录日志，也可以指定logger的

 * 名字使用特定的logger。如果指定的logger不存在，日志工具使用默认的logger记录信

 * 息。建议在每个程序包中使用各自的logger来记录信息，将来就可以配置系统将日志信息

 * 归类。

 * </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author chengang

 * @version 1.0

 */

public class LogTool {

  private static String configPath = "logconfig.xml";


  private static LogManager manager = new LogManager(configPath);

  protected synchronized static LogManager getDBPoolManager() {

    if (manager == null) {

      manager = new LogManager(configPath);

    }

    return manager;

  }



  protected synchronized static Logger getLogger() {

    if (manager == null) {

      manager = new LogManager(configPath);

    }

    Logger log = null;

    try {

      log = manager.getLogger();

    }

    catch (Exception ex) {

    }

    return log;

  }



  protected synchronized static Logger getLogger(String loggerName) {

    if (manager == null) {

      manager = new LogManager(configPath);

    }

    Logger log = null;

    try {

      log = manager.getLogger(loggerName);

    }

    catch (Exception ex) {

    }

    return log;

  }



  protected synchronized static void log(String msg, int level) {

    getLogger().log(msg, level);

  }



  /**

   * 使用默认的logger记录Debug级别的日志

   * @param msg

   */

  public synchronized static void debug(String msg) {
    getLogger().log(msg, LogManager.LEVEL_DEBUG);
  }



  /**

   * 使用默认的logger记录Info级别的日志

   * @param msg

   */

  public synchronized static void info(String msg) {

    getLogger().log(msg, LogManager.LEVEL_INFO);

  }



  /**

   * 使用默认的logger记录warning级别的日志

   * @param msg

   */

  public synchronized static void warning(String msg) {

    getLogger().log(msg, LogManager.LEVEL_WARNING);

  }



  /**

   * 使用默认的logger记录fatal级别的日志

   * @param msg

   */

  public synchronized static void fatal(String msg) {

    getLogger().log(msg, LogManager.LEVEL_FATAL);

  }



  /**

   * 使用默认的logger强制输出日志

   * @param msg

   */

  public synchronized static void output(String msg) {

    getLogger().log(msg, LogManager.LEVEL_OUTPUT);

  }



  protected synchronized static void log(Exception e, int level) {

    getLogger().log(e, level);

  }



  /**

   * 使用默认的logger输出Info级别的Exception信息

   * @param e

   */

  public synchronized static void debug(Exception e) {

    getLogger().log(e, LogManager.LEVEL_DEBUG);

  }



  /**

   * 使用默认的logger输出Debug级别的Exception信息

   * @param e

   */

  public synchronized static void info(Exception e) {

    getLogger().log(e, LogManager.LEVEL_INFO);

  }



  /**

   * 使用默认的logger输出Warning级别的Exception信息

   * @param e

   */

  public synchronized static void warning(Exception e) {

    getLogger().log(e, LogManager.LEVEL_WARNING);

  }



  /**

   * 使用默认的logger输出Fatal级别的Exception信息

   * @param e

   */

  public synchronized static void fatal(Exception e) {

    getLogger().log(e, LogManager.LEVEL_FATAL);

  }



  /**

   * 使用默认的logger强制输出Exception信息

   * @param e

   */

  public synchronized static void output(Exception e) {

    getLogger().log(e, LogManager.LEVEL_OUTPUT);

  }



  protected synchronized static void log(String loggerName, Exception e,

                                         int level) {

    getLogger(loggerName).log(e, level);

  }



  /**

   * 使用指定的logger记录Debug级别的Exception信息

   * @param loggerName logger的名字

   * @param e

   */

  public synchronized static void debug(String loggerName, Exception e) {

    getLogger(loggerName).log(e, LogManager.LEVEL_DEBUG);

  }



  /**

   * 使用指定的logger记录Info级别的Exception信息

   * @param loggerName logger的名字

   * @param e

   */

  public synchronized static void info(String loggerName, Exception e) {

    getLogger(loggerName).log(e, LogManager.LEVEL_INFO);

  }



  /**

   * 使用指定的logger记录Warning级别的Exception信息

   * @param loggerName logger的名字

   * @param e

   */

  public synchronized static void warning(String loggerName, Exception e) {

    getLogger(loggerName).log(e, LogManager.LEVEL_WARNING);

  }



  /**

   * 使用指定的logger记录Fatal级别的Exception信息

   * @param loggerName logger的名字

   * @param e

   */

  public synchronized static void fatal(String loggerName, Exception e) {

    getLogger(loggerName).log(e, LogManager.LEVEL_FATAL);

  }



  /**

   * 使用指定的logger强制输出Exception信息

   * @param loggerName logger的名字

   * @param e

   */

  public synchronized static void output(String loggerName, Exception e) {

    getLogger(loggerName).log(e, LogManager.LEVEL_OUTPUT);

  }



  protected synchronized static void log(String loggerName, String msg,

                                         int level) {

    getLogger(loggerName).log(msg, level);

  }



  /**

   * 使用指定的logger记录Debug级别的信息

   * @param msg

   */

  public synchronized static void debug(String loggerName, String msg) {

    getLogger(loggerName).log(msg, LogManager.LEVEL_DEBUG);

  }



  /**

   * 使用指定的logger记录Info级别的信息

   * @param msg

   */

  public synchronized static void info(String loggerName, String msg) {

    getLogger(loggerName).log(msg, LogManager.LEVEL_INFO);

  }



  /**

   * 使用指定的logger记录Warning级别的信息

   * @param msg

   */

  public synchronized static void warning(String loggerName, String msg) {

    getLogger(loggerName).log(msg, LogManager.LEVEL_WARNING);

  }



  /**

   * 使用指定的logger记录Fatal级别的信息

   * @param msg

   */

  public synchronized static void fatal(String loggerName, String msg) {

    getLogger(loggerName).log(msg, LogManager.LEVEL_FATAL);

  }



  /**

   * 使用指定的logger强制输出信息

   * @param msg

   */

  public synchronized static void output(String loggerName, String msg) {

    getLogger(loggerName).log(msg, LogManager.LEVEL_OUTPUT);

  }

}
