package com.viewscenes.util;

import java.util.*;
import java.io.*;
import com.viewscenes.sys.SystemCache;

/**
 *
 * <p>Title: 对象队列</p>
 * <p>Description: 存放对象的队列</p>
 * <p> </p>
 * @陈刚
 * @version 1.0
 */
public class ObjectQueue<T> {

  final static int DEFAULT_MAX_COUNT = 20000;
  final static int DEFAULT_ALARM_COUNT = 1500;
  //队列的数据结构
  ArrayList<T> objList;
  //队列的最大容量，如果超过容量则丢弃消息
  int maxCount = DEFAULT_MAX_COUNT;
  //队列的报警门限，如果超过门限则在日志中记录报警
  int alarmCount = DEFAULT_ALARM_COUNT;
  //队列的名称，用于在日志中区分是哪个队列输出的消息
  String queueName = "消息";
  public ObjectQueue() {
    objList = new ArrayList(maxCount);
  }

  public ObjectQueue(String name, int maxCount, int alarmCount){
    this.queueName = name;
    this.maxCount = maxCount;
    this.alarmCount = alarmCount;
    objList = new ArrayList(maxCount);
  }

  /**
   * 向消息队列添加一条消息，若队列中的消息数量已经超过了警告门限就显示告警信息
   * @param msg
   */
  public synchronized void add(T obj){
    if (objList.size()<maxCount){
      objList.add(obj);
      //writeObjectQueue();//写序列化文件
      notify();
//      if (objList.size() > alarmCount) {
//        LogTool.warning("警告："+queueName+"队列中的未处理消息数量已经达到了" + objList.size() + "条！");
//      }
    }
    else{
      LogTool.fatal("警告："+queueName+"队列中的未处理消息数量达到了" + objList.size() + "条，已经超过了系统最大处理能力，该消息被丢弃。");
    }
  }

  /**
   * 从队列中取出一个对象，对象取出后该对象即从队列中删除
   * @return
   */
  public synchronized T get(){
    T obj = null;
    if (objList.size()>0){
      obj = objList.get(0);
      objList.remove(0);
      //writeObjectQueue();//写序列化文件

      if (objList.size() % 5 ==0)
        LogTool.output(queueName+"队列中还剩下"+objList.size()+"条未处理消息！");
    }
    else{
      try {
        wait();
      }
      catch (InterruptedException ex) {
      }
    }
    return obj;
  }

  public void removeAll(){
    objList.removeAll(objList);
  }
  /**
   * 获得当前队列中对象的个数
   * @return 对象的个数
   *
   */
  public int getQuerySize(){
    return objList.size();
  }

  /**
   * 当前队列中序列化到硬盘中
   *
   */
  public synchronized void writeObjectQueue(){
    ObjectOutputStream out = null;
    try{
      out = new ObjectOutputStream(new FileOutputStream(
          SystemCache.objectqueuepath));
      out.writeObject(objList);
    }catch(Exception ex){
      LogTool.fatal("序列化队列出错！", ex);
    }finally{
      try{
        out.close();
      }catch(Exception ex){
        LogTool.fatal("关闭序列化流出错！", ex);
      }
    }
  }

  /**
  * 当前队列中序列化到硬盘中
  *
  */
 public synchronized void readObjectQueue(){
   ObjectInputStream in = null;
   try {
     File file= new File(SystemCache.objectqueuepath);
     if (file.exists()){
       in = new ObjectInputStream(new FileInputStream(
           SystemCache.objectqueuepath));
       objList = (ArrayList) in.readObject();
     }
   }
   catch (Exception ex) {
//     LogTool.fatal("read序列化队列出错！", ex);
   }
   finally {
     try {
       in.close();
     }
     catch (Exception ex) {
       LogTool.fatal("关闭read序列化流出错！", ex);
     }
   }
 }



}
