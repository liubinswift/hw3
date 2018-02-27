package com.viewscenes.dao;



import java.sql.*;



import com.viewscenes.dao.database.*;

import com.viewscenes.dao.innerdao.*;

import com.viewscenes.pub.*;



import java.text.*;

import java.util.*;



/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class test {

  private final static Map _handleMap = new HashMap();

  public static void main(String[] args) {

//    DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);



    test t = new test();



//    t.insertDAO(d);
//
//        t.queryDAO1(d);



//    t.updateDAO(d);
//
//    t.deleteDAO(d);

//    t.compareCacheDB(d);

//

//    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
//
//    t.insertDb(db);
//
//    t.batchDb(db);



  }



  public void insertDAO() {

    GDSet set = null;

    long[] key = null;

    DAOCondition cond = null;

    try {

//      String str = "<tablename>mon_av_channel_tab</tablename><columns>ch_id$name$broadcaster$code$type$</columns>$CCTV-1$北京歌华有线$54676$0$";

//      GDSet dataset = GDSetTool.parseStringToGDSet(str);

//      d.Insert(dataset, null);



      //缓存表更新

      String col1[] = {"head_id", "ch_id"};

      set = new GDSet("mon_head_ch_rel_tab", col1);

      String data1[] = {"100007", "100017"};

      set.addRow(data1);

      DbComponent.Insert(set, null);



      String col[] = {"PARAM_NAME", "PARAM_VALUE"};

      set = new GDSet("SYS_CONFIGURATION_TAB", col);

      String data[] = {"mm", "0"};

      set.addRow(data);



      DbComponent.Insert(set, null);



      cond = new DAOCondition("MON_ABNORMITY_TAB");

      cond.addCondition("ABNORMITY_ID", "NUMBER", "=", "");

      cond.addCondition("TYPE_ID", "NUMBER", "=", "1");

      cond.addCondition("IS_HANDLED", "NUMBER", "=", "0");

      String dd[] = {

          "condition", "ISPROCESS"};

      set = new GDSet("TEM_HANDLE_ABNORMITY_TAB", dd);

      String ee[] = {

          cond.toString(), "0"};

      set.addRow(ee);



      DbComponent.Insert(set, null);



      String b17[] = {

          "CHANNO", "CODE", "TYPE", "NAME", "PHYSCODE", "FREQV", "FREQA"};

      set = new GDSet("mon_analog_channel_tab", b17);

      String row17[] = {

          "3", "CCTV5", "0", "CCTV-5", "11X01", "1.51", "2.34"};

      set.addRow(row17);

      String row18[] = {

          "3", "CCTV5", "0", "CCTV-5", "11X01", "1.51", "2.34"};

      set.addRow(row18);



      long[] alTimeID = new long[2];

      DbComponent.Insert(set, alTimeID);



      String a14[] = {

          "a", "b", "c", "d"};

      //构造GDSet，表名和列标题名称

      set = new GDSet("TEMP_TAB", a14);

      //构造插入的记录

      String c21[] = {

          "", "10", "你还", "2000-05-06 00:00:00"};

      set.addRow(c21);

      String c22[] = {

          "", "11", "hello", "2000-05-06 00:00:00"};

      set.addRow(c22);

      String c23[] = {

          "", "12", "hello", "2000-05-06 00:00:00"};

      set.addRow(c23);

      key = new long[3];

      DbComponent.Insert(set, key);

      for (int i = 0; i < key.length; i++) {

        System.out.print(key[i]);



        //不自动生成主键

      }

      String b1[] = {

          "a", "b", "c", "d"};

      set = new GDSet("temp_tab", b1);

      String row3[] = {

          "2", "10", "smile", "2000-05-06"};

      set.addRow(row3);

      DbComponent.Insert(set);



      //逻辑表插入



      String b4[] = {

          "name", "description", "head_id", "tasktype"};



      set = new GDSet("mon_index_task_tab", b4);

      String row4[] = {

          "stop", "smile", "1100000001", "0"};

      set.addRow(row4);



      key = new long[1];

      DbComponent.Insert(set, key);

      for (int i = 0; i < key.length; i++) {

        System.out.print(key[i]);



      }

      String b13[] = {

          "name", "ch_id", "head_id"};



      set = new GDSet("mon_record_task_tab", b13);

      String row14[] = {

          "stop", "1100000001", "1100000001"};

      set.addRow(row14);



      key = new long[1];

      DbComponent.Insert(set, key);

      for (int i = 0; i < key.length; i++) {

        System.out.print(key[i]);



      }

      String b15[] = {

          "name", "type", "head_id"};



      set = new GDSet("mon_spectrum_task_tab", b15);

      String row16[] = {

          "stop", "1", "1100000001"};

      set.addRow(row16);



      key = new long[1];

      DbComponent.Insert(set, key);

      for (int i = 0; i < key.length; i++) {

        System.out.print(key[i]);



        //带主键的插入

        //构造列标题，必须和表字段一致

      }

      String a[] = {

          "b", "c", "d"};



      //构造GDSet，表名和列标题名称

      set = new GDSet("TEMP_TAB", a);

      //构造插入的记录

      String c[] = {

          "10", "你还", "2000-05-06 00:00:00"};

      set.addRow(c);

      String c1[] = {

          "11", "hello", "2000-05-06 00:00:00"};

      set.addRow(c1);

      String c2[] = {

          "12", "hello", "2000-05-06 00:00:00"};

      set.addRow(c2);



      key = new long[3];

      DbComponent.Insert(set, key);

      for (int i = 0; i < key.length; i++) {

        System.out.print(key[i]);



        //不带主键的插入

      }

      String b[] = {

          "a", "b", "c", "d"};

      set = new GDSet("temp_tab", b);

      String row[] = {

          "2", "10", "smile", "2000-05-06"};

      set.addRow(row);

      String row1[] = {

          "2", "10", "smile", "2000-05-06"};

      set.addRow(row1);

      DbComponent.Insert(set);



      // 带主键的插入

      String b10[] = {

          "DAYOFWEEK", "STARTTIME", "TASK_ID"};

      GDSet gdTime = new GDSet("MON_TASK_DURITEM_TAB", b10);

      String row10[] = {

          "2002-01-01", "2002-01-01", "100001"};

      gdTime.addRow(row10);

      String row11[] = {

          "2003-01-01", "2002-01-01", "100001"};

      gdTime.addRow(row11);



      alTimeID = new long[2];

      DbComponent.Insert(gdTime, alTimeID);



    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  public void updateDAO() {

    GDSet set1 = null;

    //update

    try {

      String col[] = {"PARAM_NAME", "PARAM_VALUE"};

      set1 = new GDSet("SYS_CONFIGURATION_TAB", col);

      String data[] = {"mm", "0"};

      set1.addRow(data);

      DbComponent.Update(set1);



      //updatebyCon

      DAOCondition cond = new DAOCondition("temp_tab");

      cond.addCondition("c", "VARCHAR", "like", "h%");



      String con1[] = {

          "a", "c"};

      GDSet gd = new GDSet("temp_tab", con1,1);

      gd.setString(0, "a", "99");

      gd.setString(0, "c", "kxmmm");

      DbComponent.UpdatebyCon(gd, cond);



      //updatex

//      DAOCondition condition = new DAOCondition("temp_tab");
//
//      GDSet result_set = d.Query("*", condition);

      String con[] = {

          "a", "b", "c"};

//      GDSet conGd = new GDSet("temp_tab", con);
//
//      for (int i = 0; i < result_set.getRowCount(); i++) {
//
//        result_set.setString(i, "c", "love");
//
//      }
//
//      d.UpdateX(result_set, conGd);



      //update

      String b[] = {

          "a", "b", "c", "d"};

      set1 = new GDSet("temp_tab", b);

      String e[] = {

          "100081", "2", "bbb", "2004-05-06 00:00:00"};

      set1.addRow(e);

      String e1[] = {

          "100080", "2", "bbb", "2004-05-06 00:00:00"};

      set1.addRow(e1);



      DbComponent.Update(set1);



      String b1[] = {

          "a", "b"};

      GDSet set2 = new GDSet("temp_tab", b1);

      String e2[] = {

          "3301", "5"};



      set2.addRow(e2);

      System.out.println(DbComponent.Update(set2));



    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  public void deleteDAO() {

    //update

    try {

      GDSet set1 = null;

      DAOCondition condition = null;

      int ret[] = null;

      DAOCondition scondition = null;



      String col[] = {"PARAM_NAME", "PARAM_VALUE"};

      set1 = new GDSet("SYS_CONFIGURATION_TAB", col);

      String data[] = {"mm", "0"};

      set1.addRow(data);
      //delete支持sql only

//      DbComponent.Delete(set1);



      condition = new DAOCondition("temp_tab");

      condition.addCondition("c", "VARCHAR", "like", "a%");

//      System.out.println(d.DeleteX(condition));



      scondition = new DAOCondition("temp_tab");

      scondition.addCondition("a", "NUMBER", "=", "1");

      scondition.addCondition("a", "NUMBER", "=", "10", "or");

      condition = new DAOCondition("temp_tab");

      condition.addCondition("", "", "()", scondition.toString());

//      System.out.println(d.DeleteX(condition));



      String b14[] = {

          "role_id", "role_name"};

      set1 = new GDSet("role", b14);

      String e14[] = {

          "2", "lll"};

      set1.addRow(e14);

//      ret = DbComponent.Delete(set1);



      String b13[] = {

          "role_id", "role_name"};

      set1 = new GDSet("roleuser", b13);

      String e13[] = {

          "2", "lll"};

      set1.addRow(e13);

//      ret = d.Delete(set1);



      scondition = new DAOCondition("temp_tab");

      scondition.addCondition("a", "NUMBER", "=", "1");

      condition = new DAOCondition("temp_tab");

      condition.addCondition("a", "NUMBER", "in", scondition.toString());

//      System.out.println(d.DeleteX(condition));



      condition = new DAOCondition("temp_tab");

      condition.addCondition("a", "NUMBER", "=", "10");

//      System.out.println(d.DeleteX(condition));



      condition.addCondition("b", "NUMBER", "=", "10");

//      System.out.println(d.DeleteX(condition));

//      //全部删除

      condition = new DAOCondition("temp_tab");

//      System.out.println(d.DeleteX(condition));



      condition = new DAOCondition("temp_tab");

      condition.addCondition("d", "DATE", "<", "2000-10-1");

      condition.addCondition("d", "DATE", ">", "1999-1-1");

//      System.out.println(d.DeleteX(condition));



      //删除带主键的多个记录

      String b1[] = {

          "a", "b", "c"};

      set1 = new GDSet("temp_tab", b1);

      String e1[] = {

          "100130", "11", "hello"};

      set1.addRow(e1);

      String e2[] = {

          "100088", "11", "hello"};

      set1.addRow(e2);



//      System.out.println(d.Delete(set1));



      //删除不带主键的记录

      String b[] = {

          "b", "d"};

      set1 = new GDSet("temp_tab", b);

      String e[] = {

          "12", "2000-05-06"};

      set1.addRow(e);

      //删除不带主键的多个记录

      String e3[] = {

          "11", "2000-05-06"};

      set1.addRow(e3);

//      System.out.println(d.Delete(set1));



      condition = new DAOCondition("temp_tab");

      condition.addCondition("a", "NUMBER", "=", "1");

//      GDSet result_set = d.Query("a", condition, 0, 2);
//
////      d.Delete(result_set);
//
//      Common.displayGDSet(result_set);



    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  public void queryDAO1() {

    try {

      GDSet result_set = null;

      DAOCondition condition = null;

      DAOCondition con3 = null;

      DAOCondition sub = null;

      DAOCondition scondition = null;



      condition = new DAOCondition("mon_center_tab");

      condition.addCondition("center_id", "NUMBER", ">", "5");

//      result_set = DbComponent.Query("center_id,loc_id", condition);
//
//      Common.displayGDSet(result_set);



      //like

      condition = new DAOCondition("mon_center_tab");

      condition.addCondition("name", "VARCHAR", "=", "XXXX","AND");

//      condition.addCondition("center_id", "NUMBER", ">","3");

      condition.addCondition("code", "VARCHAR", "like", "90%");

//      result_set = d.Query("*", condition, 0, 1);
//
//      Common.displayGDSet(result_set);



      //group by

      condition = new DAOCondition("mon_center_tab");

      condition.addCondition("center_id", "NUMBER", ">", "3");

      condition.addCondition("center_id,loc_id", "", "group by", "");

//      result_set = d.Query("center_id,loc_id", condition, 0, 1);
//
//      Common.displayGDSet(result_set);

      //()

      sub = new DAOCondition("mon_center_tab");

      sub.addCondition("center_id", "NUMBER", "=", "5");

      sub.addCondition("loc_id", "NUMBER", "=", "3301", "or");



      condition = new DAOCondition("mon_center_tab");

      condition.addCondition("", "", "()", sub.toString());

//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);



      condition = new DAOCondition("sec_user_tab");

      condition.addCondition("user_code", "VARCHAR", "=", "boy");

      condition.addCondition("user_password", "VARCHAR", "=", "1");

      condition.addCondition("user_code", "", "group by", "");

//      result_set = d.Query("user_code", condition);
//
//      Common.displayGDSet(result_set);



      sub = new DAOCondition("");

      sub.addCondition("Headtype", "NUMBER", "=", "3");

      sub.addCondition("Headtype", "NUMBER", "=", "100", "or");



      condition = new DAOCondition("mon_headend_tab");

      condition.addCondition("center_id", "NUMBER", "=", "5", "and");

      condition.addCondition("", "", "()", sub.toString());

//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);



      //not in

      sub = new DAOCondition("mon_head_ch_rel_tab");

      sub.addCondition("head_id", "NUMBER", "=", "1100000001");



      condition = new DAOCondition("mon_channel_tab");

      condition.addCondition("type", "NUMBER", "=", "0");

      condition.addCondition("ch_id", "NUMBER", "not in", sub.toString());

//      condition.addCondition("CODE", "VARCHAR", "=", "CH039");

//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      con3 = new DAOCondition("CONTENTTASK");
//
//      result_set = d.Query("count(*)", con3);
//
//      Common.displayGDSet(result_set);



      //order by

      condition = new DAOCondition("mon_center_tab");

      condition.addCondition("center_id", "", "order by", "");

//      result_set = d.Query("center_id,loc_id", condition, 0, 2);
//
//      Common.displayGDSet(result_set);



      condition = new DAOCondition("mon_ch_run_plan_tab");

      condition.addCondition("ch_id", "NUMBER", "=", "1100000001");



      scondition = new DAOCondition("mon_head_ch_rel_tab");

      scondition.addCondition("head_id", "NUMBER", "=", "1100000001");



      condition.addCondition("ch_id", "NUMBER", "in", scondition.toString());

      condition.addCondition("ch_id,STOP_TIME", "", "order by", "");

//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);



//      con3 = new DAOCondition("CONTENTTASK");
//
//      con3.addCondition("ch_name,create_time", "", "order by", "asc");
//
//      con3.addCondition("ch_name", "", "order by", "desc");
//
//      result_set = d.Query("*", con3);
//
//      Common.displayGDSet(result_set);



      condition = new DAOCondition("temp_tab");

      condition.addCondition("a,d", "", "order by", "desc");

//      result_set = d.Query("*", condition, 0, 2);
//
//      Common.displayGDSet(result_set);



//      con3 = new DAOCondition("CONTENTTASK");
//
//      con3.addCondition("ch_name,create_time", "", "order by", "asc");
//
//      con3.addCondition("ch_name", "", "order by", "desc");
//
//      result_set = d.Query("create_time,head_id", con3);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition = new DAOCondition("temp_tab");
//
//      condition.addCondition("a,d", "", "order by", "desc");
//
//      condition.addCondition("b", "", "order by", "asc");
//
//      condition.addCondition("c", "", "order by", "desc");
//
//      result_set = d.Query("a", condition, 8000, 8001);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //in
//
//      condition = new DAOCondition("mon_analog_channel_tab");
//
//      scondition = new DAOCondition("mon_head_ch_rel_tab");
//
//      scondition.addCondition("head_id", "NUMBER", "=", "1100000001");
//
//      condition.addCondition("ch_id", "NUMBER", "in", scondition.toString());
//
//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//
//
//      condition = new DAOCondition("mon_ch_run_plan_tab");
//
//      condition.addCondition("ch_id", "NUMBER", "=", "1100000001");
//
//
//
//      scondition = new DAOCondition("mon_head_ch_rel_tab");
//
//      scondition.addCondition("head_id", "NUMBER", "=", "1100000001");
//
//      condition.addCondition("ch_id", "NUMBER", "in", scondition.toString());
//
//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //构造一个in的子DAOCondition
//
//      sub = new DAOCondition("mon_head_ch_rel_tab");
//
//      sub.addCondition("head_id", "NUMBER", "=", "1100000001");
//
//
//
//      condition = new DAOCondition("mon_channel_tab");
//
//      condition.addCondition("type", "NUMBER", "=", "0");
//
//      condition.addCondition("ch_id", "NUMBER", "in", sub.toString());
//
////      condition.addCondition("CODE", "VARCHAR", "=", "CH039");
//
//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      result_set = d.Query("*", condition, 0, 2);
//
//      Common.displayGDSet(result_set);
//
//      //物理表
//
//      condition = new DAOCondition("temp_tab");
//
//      condition.addCondition("a", "NUMBER", "=", "1");
//
//      result_set = d.Query("a", condition, 0, 2);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition.addCondition("B", "NUMBER", "=", "30");
//
//      result_set = d.Query("a,b", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      result_set = d.Query("count(*)", condition);
//
//      Common.displayGDSet(result_set);
//
//      //逻辑表
//
//      //带条件
//
//      con3 = new DAOCondition("CONTENTTASK");
//
//      con3.addCondition("ch_name", "VARCHAR", "=", "CCTV-9");
//
//      result_set = d.Query("create_time,head_id", con3);
//
//      Common.displayGDSet(result_set);
//
//
//
//      con3.addCondition("ch_id", "NUMBER", "=", "1100000001");
//
//      result_set = d.Query("create_time,head_id", con3);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //不带条件
//
//      condition = new DAOCondition("CONTENTTASK");
//
//      result_set = d.Query("create_time,head_id", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition = new DAOCondition("contenttask");
//
//      result_set = d.Query("ch_name,head_name", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition = new DAOCondition("contenttask");
//
//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition = new DAOCondition("analog_quality_task");
//
//      result_set = d.Query("ch_name,task_id", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition = new DAOCondition("analog_quality_task");
//
//      result_set = d.Query("*", condition);
//
//      Common.displayGDSet(result_set);



//      condition = new DAOCondition("contenttask");
//
//      result_set = d.Query("create_time,head_id,head_name", condition);
//
//      Common.displayGDSet(result_set);



      //缓存

//      condition = new DAOCondition("mon_check_point_tab");
//
//      condition.addCondition("head_id", "NUMBER", "=", "1100000001");
//
//      result_set = d.Query("ck_id,code", condition);
//
//      Common.displayGDSet(result_set);



//      condition = new DAOCondition("mon_headend_tab");
//
//      result_set = d.Query("count(*)", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      condition = new DAOCondition("mon_center_tab");
//
//      result_set = d.Query("count(*)", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //查询不带条件
//
//      condition = new DAOCondition("mon_center_tab");
//
//      result_set = d.Query("center_id,loc_id", condition, 0, 2);
//
//      Common.displayGDSet(result_set);
//
//
//
//      result_set = d.Query("center_id,loc_id", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //带一个条件查询，有索引
//
//      condition.addCondition("center_id", "NUMBER", "=", "5");
//
//      result_set = d.Query("center_id,loc_id", condition);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //带一个条件查询，不从索引查询
//
//      DAOCondition con = new DAOCondition("mon_center_tab");
//
//      con.addCondition("loc_id", "NUMBER", "=", "1101");
//
//      result_set = d.Query("center_id,loc_id", con);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //带两个条件的查询，（从数据库查询）
//
//      DAOCondition con5 = new DAOCondition("mon_center_tab");
//
//      con5.addCondition("center_id", "NUMBER", "=", "5");
//
//      con5.addCondition("name", DAOCondition._VARCHAR, "=",
//
//                        "总局有线广播电视监测中心");
//
//      result_set = d.Query("center_id,loc_id", con5);
//
//      Common.displayGDSet(result_set);
//
//
//
//      DAOCondition con1 = new DAOCondition("mon_center_tab");
//
//      result_set = d.Query("*", con1);
//
//      Common.displayGDSet(result_set);
//
//
//
//      //从数据库查询
//
//      DAOCondition con2 = new DAOCondition("temp_tab");
//
//      result_set = d.Query("*", con2, 0, 2);
//
//      Common.displayGDSet(result_set);
//
////
//
//      result_set = d.Query("*", con2);
//
//      Common.displayGDSet(result_set);
//
//
//
//      con2.addCondition("a", "NUMBER", "=", "100060");
//
//      result_set = d.Query("*", con2, 0, 15);
//
//      Common.displayGDSet(result_set);

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }



  }



//  public void compareCacheDB() {
//
//    //query
//
//    try {
//
//      DAOCondition condition = null;
//
//      GDSet result_set = null;
//
//
//
//      long n = System.currentTimeMillis();
//
//      condition = new DAOCondition("mon_center_tab");
//
//      condition.addCondition("center_id", "NUMBER", "=", "5");
//
//      result_set = d.Query("*", condition);
//
//      long n1 = System.currentTimeMillis();
//
//      Common.displayGDSet(result_set);
//
//      System.out.println(n1 - n);
//
//
//
//    }
//
//    catch (Exception ex) {
//
//      ex.printStackTrace();
//
//    }
//
//  }



  public void insertDb() {

    String sql = "insert into temp_tab values(10,10,'aa',to_date('2000-12-11','yyyy-mm-dd'))";

    //insert

    try {

      DbComponent.exeUpdate(sql);

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }



  public void batchDb() {

    //batch

    String[] s = {

        "insert into temp_tab values(1,30,'aa',to_date('2000-12-11','yyyy-mm-dd'))",

        "insert into temp_tab values(2,20,'aa',to_date('2000-12-11','yyyy-mm-dd'))"};

    try {

      DbComponent.exeBatch(s);

    }

    catch (Exception ex) {

      ex.printStackTrace();

    }

  }





}
