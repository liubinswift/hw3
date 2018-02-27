package com.viewscenes.dao;



import java.sql.*;



import com.viewscenes.dao.database.*;

import com.viewscenes.dao.innerdao.*;

import com.viewscenes.pub.*;

import com.viewscenes.util.*;



import java.text.*;

import java.util.*;



public class testAll {



//    static DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);



    public static void main(String[] args) {



        ArrayList list = new ArrayList();

        list.add("a");

        list.add("b");

        String[] c = (String[]) list.toArray(new String[0]);



        testAll t = new testAll();

//        t.compareCacheDB(d);



    }



    public void Query(String field, DAOCondition condition, GDSet result_set) {



        long n = System.currentTimeMillis();

//        try {
//
//            result_set = d.Query(field, condition);
//
//        } catch (DbException ex) {
//
//            ex.printStackTrace();
//
//        }

        long n1 = System.currentTimeMillis();

        Common.displayGDSet(result_set);

        System.out.println("USE TIME:" + (n1 - n));

    }



    /**

     * 比较缓存和数据库

     * @param d

     */

    public void compareCacheDB() {

        //query

        try {

            DAOCondition condition = null;

            GDSet result_set = null;

            //按索引查

            condition = new DAOCondition("mon_center_tab");

            condition.addCondition("center_id", "NUMBER", "=", "5");

            Query("*", condition, result_set);



            //不按索引查

            condition = new DAOCondition("mon_center_tab");

            condition.addCondition("name", DAOCondition._VARCHAR, "=",

                    "总局有线广播电视监测中心");

            Query("*", condition, result_set);



            //查询全部

            condition = new DAOCondition("mon_center_tab");

            Query("*", condition, result_set);



        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }





}
