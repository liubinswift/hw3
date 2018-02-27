package com.viewscenes.util.business.runplan;

import java.util.*;
/**
 * <p>Title: 运行图时间段容器</p>
 * <p>Description: 对运行图时间段组以及运行图信息的封装</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 永新同方</p>
 * @author 徐雁威
 * @version 1.0
 */

public class TimeSpanGroup {
  private Vector spanVector;
  private RunPlanInfo runPlanInfo;

  /**
   * 构造函数
   */
  public TimeSpanGroup() {
    spanVector = new Vector();
    runPlanInfo = null;
  }

  /**
   * 转换为字串
   * @return
   */
  public String toString() {
    String returnValue = "";
    for (int i = 0; i < spanVector.size(); i++) {
      returnValue += i + ". " + this.get(i).toString();
    }
    return returnValue;
  }

  /**
   * 清除数据
   */
  public void removeAll() {
    spanVector.clear();
  }

  /**
   * 添加融合形时间段
   * @param runSpan 时间段TimeSpan
   */
  public void add(TimeSpan runSpan) {
    add(runSpan, false);
  }

  /**
   * 去除指定时间段
   * @param index
   */
  public void remove(int index) {
    if (index < spanVector.size()) {
      spanVector.remove(index);
    }
  }

  /**
   * 插入时段,对跨天时段先拆分后插入
   * @param newItem 时间段TimeSpan
   * @param overWrite 覆盖标志 true:覆盖,false:融合
   */
  public void add(TimeSpan newItem, boolean overWrite) {
    TimeSpan addedItem;
    if (newItem.getProgramInfo() != null) {
      addedItem = new TimeSpan(newItem.getStartTime(), newItem.getEndTime(),
			       newItem.getType(),
			       (ProgramInfo) newItem.getProgramInfo().clone());
    }
    else {
      addedItem = new TimeSpan(newItem.getStartTime(), newItem.getEndTime(),
			       newItem.getType(), newItem.getProgramInfo());
      //split two parts
    }
    if (addedItem.getStartTime() >= addedItem.getEndTime()) {
      TimeSpan item = new TimeSpan(0, addedItem.getEndTime(), addedItem.getType(),
				   addedItem.getProgramInfo());
      addItem(item, overWrite);
      addedItem.setEndTime(24 * 3600);
      addItem(addedItem, overWrite);
    }
    else {
      addItem(addedItem, overWrite);

      //
    }
  }

  /**
   * 插入一个时间段
   * @param newItem 时间段TimeSpan
   * @param overWrite 覆盖标志 true:覆盖,false:融合
   */
  private void addItem(TimeSpan newItem, boolean overWrite) {
    TimeSpan item;
    TimeSpan sitem;
    int i;
    for (i = 0; i < spanVector.size(); i++) {
      item = this.get(i);
      //compare to the next item;
      if (newItem.getStartTime() >= item.getEndTime()) {
	continue;
      }
      //insert new item in the early most
      if (newItem.getEndTime() < item.getStartTime()) {
	spanVector.insertElementAt(newItem, i);
	break;
      }
      //split new item, continue using left parts
      if (newItem.getStartTime() < item.getStartTime()) {
	sitem = new TimeSpan(newItem.getStartTime(), item.getStartTime(),
			     newItem.getType(), newItem.getProgramInfo());
	spanVector.insertElementAt(sitem, i);
	newItem.setStartTime(item.getStartTime());
	continue;
      }
      //split exist item
      if (newItem.getStartTime() > item.getStartTime()) {
	sitem = new TimeSpan(item.getStartTime(), newItem.getStartTime(),
			     item.getType(), item.getProgramInfo());
	spanVector.insertElementAt(sitem, i);
	item.setStartTime(newItem.getStartTime());
	continue;
      }

      //split exist item
      if (newItem.getEndTime() < item.getEndTime()) {
	//create left item
	sitem = new TimeSpan(newItem.getEndTime(), item.getEndTime(),
			     item.getType(), item.getProgramInfo());
	spanVector.insertElementAt(sitem, i + 1);
	//shorten exist item end
	item.setEndTime(newItem.getEndTime());
      }
      //common part combination
      if (newItem.getType() > item.getType() || overWrite) {
	item.setType(newItem.getType());
	item.setProgramInfo(newItem.getProgramInfo());
      }

      //split new item, continue using left parts
      if (newItem.getEndTime() > item.getEndTime()) {
	newItem.setStartTime(item.getEndTime());
	continue;
      }
      else {
	//no block left
	break;
      }
    }
    //add to the last
    if (i >= spanVector.size()) {
      spanVector.add(newItem);
    }
    compressItems();
  }

  /**
   * 压缩前后链接的时间段
   */
  private void compressItems() {
    TimeSpan item;
    TimeSpan itemNext;
    int i = 0;
    while (i < spanVector.size() - 1) {
      item = this.get(i);
      itemNext = this.get(i + 1);
      if (
	  item.getEndTime() == itemNext.getStartTime() &&
	  item.getType() == itemNext.getType()
	  &&
	  (
	  (
	  item.getProgramInfo() != null && itemNext.getProgramInfo() != null
	  &&
	  (item.getProgramInfo() == itemNext.getProgramInfo() ||
	   item.getProgramInfo().equals(itemNext.getProgramInfo()))
	  )
	  ||
	  (item.getProgramInfo() == null && itemNext.getProgramInfo() == null)
	  )
	  ) {
	item.setEndTime(itemNext.getEndTime());
	spanVector.remove(i + 1);
      }
      else {
	i++;
      }
    }
  }

  /**
   * 获得一个时间段
   * @param index 索引
   * @return  时间段TimeSpan
   */
  public TimeSpan get(int index) {
    TimeSpan returnValue;
    if (index < spanVector.size()) {
      returnValue = (TimeSpan) spanVector.get(index);
    }
    else {
      returnValue = new TimeSpan();
    }
    return returnValue;
  }

  /**
   * 获取时间段个数
   * @param type 运行图类型, 0:全部,1:播出,2:检修
   * @return
   */
  public int getTimeSpanNum(long type) {
    int returnValue = 0;
    if (type == 0) {
      returnValue = spanVector.size();
    }
    else {
      for (int i = 0; i < spanVector.size(); i++) {
	if (this.get(i).getType() == type) {
	  returnValue++;
	}
      }
    }
    return returnValue;
  }

  /**
   * 获取总时长
   * @param type 运行图类型, 0:全部, 1:播出, 2:检修
   * @return (秒)
   */
  public long getTotalInterval(long type) {
    long returnValue = 0;
    TimeSpan item;
    for (int i = 0; i < spanVector.size(); i++) {
      if (type == 0 || this.get(i).getType() == type) {
	returnValue += this.get(i).getInterval();
      }
    }
    return returnValue;
  }

  /**
   * 获取运行图节目信息
   * @return ProgramInfo
   */
  public RunPlanInfo getRunPlanInfo() {
    return runPlanInfo;
  }

  /**
   * 设置运行图节目信息
   * @param programInfo ProgramInfo
   */
  public void setRunPlanInfo(RunPlanInfo runPlanInfo) {
    this.runPlanInfo = runPlanInfo;
  }

}
