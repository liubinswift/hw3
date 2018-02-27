package com.viewscenes.util.log;



class LoggerProperty {



  public String logName;

  public String logPath;

  public int logType;

  public int logLevel;

  public int logMethod;

  public int autoSplit;



  public LoggerProperty(String name,String path

                      ,String type, String level, String method, String split) {



    if (name==null){

      name="monweb";

    }

    logName = name;



    if (path==null){

      path="c:\\";

    }

    logPath = path;



    if (type==null){

      type="screen";

    }

    logType=-1;

    if (type.equalsIgnoreCase("screen")){

      logType = LogManager.TYPE_SCREEN;

    }

    if (type.equalsIgnoreCase("file")){

      logType = LogManager.TYPE_FILE;

    }

    if (type.equalsIgnoreCase("both")){

      logType = LogManager.TYPE_BOTH;

    }

    if (type.equalsIgnoreCase("none")){

      logType = LogManager.TYPE_NONE;

    }



    if (logType<0)

      logType = LogManager.TYPE_SCREEN;



    if (level==null)

        level="debug";

    logLevel = -1;

    if (level.equalsIgnoreCase("debug")){

      logLevel = LogManager.LEVEL_DEBUG;

    }

    if (level.equalsIgnoreCase("info")){

      logLevel = LogManager.LEVEL_INFO;

    }

    if (level.equalsIgnoreCase("warning")){

      logLevel = LogManager.LEVEL_WARNING;

    }

    if (level.equalsIgnoreCase("fatal")){

      logLevel = LogManager.LEVEL_FATAL;

    }

    if (level.equalsIgnoreCase("output")){

      logLevel = LogManager.LEVEL_OUTPUT;

    }



    if (logLevel<0){

      try {

        logLevel = Integer.parseInt(level);

      }

      catch (Exception e) {

        logLevel = LogManager.LEVEL_DEBUG;

      }

    }



    if (method==null){

      method = "normal";

    }



    logMethod = -1;

    if (method.equalsIgnoreCase("normal")){

      logMethod = LogManager.METHOD_NORMAL;

    }



    if (method.equalsIgnoreCase("thread")){

      logMethod = LogManager.METHOD_THREAD;

    }



    if (split==null){

      split = "weekly";

    }

    autoSplit = -1;

    if (split.equalsIgnoreCase("hourly")){

      autoSplit = LogManager.SPLIT_HOURLY;

    }

    if (split.equalsIgnoreCase("daily")){

      autoSplit = LogManager.SPLIT_DAILY;

    }

    if (split.equalsIgnoreCase("weekly")){

      autoSplit = LogManager.SPLIT_WEEKLY;

    }

    if (split.equalsIgnoreCase("monthly")){

      autoSplit = LogManager.SPLIT_MONTHLY;

    }

    if (split.equalsIgnoreCase("yearly")){

      autoSplit = LogManager.SPLIT_YEARLY;

    }

    if (autoSplit<0)

      autoSplit = LogManager.SPLIT_WEEKLY;

  }

}

