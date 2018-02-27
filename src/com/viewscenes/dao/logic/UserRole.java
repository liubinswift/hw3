package com.viewscenes.dao.logic;

import com.viewscenes.pub.GDSet;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.dao.DAOCondition;
import com.viewscenes.pub.*;
import com.viewscenes.dao.innerdao.SQLGenerator;



public class UserRole
    extends Role {

  /**
   * É¾³ýÇ°²Ù×÷

   * @param data

   * @throws DbException

   */

  protected String beforeDelete(GDSet data) throws DbException {

    try {

    String roleid = null;
    try {
        roleid = data.getString(0, "role_id");
    } catch (GDSetException ex1) {
    }

      DAOCondition subcon = new DAOCondition("SEC_USER_ROLE_REL_TAB");

      subcon.addCondition("role_id", "NUMBER", "=", roleid);

      GDSet ret = Query("user_id", subcon);

      DAOCondition condition = new DAOCondition("SEC_USER_TAB");

      for (int i = 0; i < ret.getRowCount(); i++) {

	if (i == 0) {

	  condition.addCondition("user_id", "NUMBER", "=",
				 ret.getString(i, "user_id"));

	}
	else {

	  condition.addCondition("user_id", "NUMBER", "=",
				 ret.getString(i, "user_id"), "or");

	}
      }

      if (ret.getRowCount() == 0) {

	return null;
      }

      SQLGenerator sql = new SQLGenerator();

      return sql.generateDeleteXSQL(condition);

    }
    catch (GDSetException ex) {

      throw new DbException("UserRole Delete:GDSet´íÎó", ex);

    }

  }

  /**
   * µÃµ½Á¬´®sqlÓï¾ä

   * @param data

   * @return

   * @throws DbException

   */

  protected String[] getSql(GDSet data) throws DbException {

    String temp = null;

    String[] sql = null;

    temp = beforeDelete(data);

    if (temp != null) {

      sql = new String[2];

      sql[0] = commonDelete(data);

      sql[1] = temp;

    }
    else {

      sql = new String[1];

      sql[0] = commonDelete(data);

    }

    return sql;

  }

}
