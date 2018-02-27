package com.viewscenes.dao.logic;

import java.util.*;

import org.jdom.*;
import com.viewscenes.dao.innerdao.DAOComponent;
import com.viewscenes.dao.XmlReader;


public class LogicFactory {

  public static DAOComponent create(String tab) {

    if (operContains("task", tab)) {

      return new Task();
    }

    if (operContains("role", tab)) {

      return new Role();
    }

    if (operContains("roleuser", tab)) {

      return new UserRole();
    }

    if (operContains("channel", tab)) {

      return new Channel();
    }

    return new DAOComponent();

  }

  /**
   * 判断表是否属于caption类的操作

   * @param caption 操作类型的名称

   * @param tabName 表名

   * @return

   * true:属于此操作

   * false:不属于此操作

   */

  private static boolean operContains(String caption, String tabName) {

    String lowerTab = tabName.toLowerCase();

    Element ele = XmlReader.getConfigItem("updateconfig");

    Element task_ele = ele.getChild(caption);

    List total_tab = task_ele.getChildren();

    for (int i = 0; i < total_tab.size(); i++) {

      Element tab = (Element) total_tab.get(i);

      String tname = tab.getAttributeValue("table");

      if (tname != null && tname.equals(lowerTab)) {

	return true;

      }

    }

    return false;

  }

}
