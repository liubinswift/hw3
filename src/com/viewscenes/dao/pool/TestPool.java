package com.viewscenes.dao.pool;



import java.sql.*;



class TestPool {



    public TestPool() {

    }



    public static void main(String[] args) {

        TestPool testPool1 = new TestPool();

        long start = System.currentTimeMillis();

//    DBPoolManager poolManager = DatabaseManager.getDBPoolManager();

        long end = System.currentTimeMillis();

        System.out.println("Constructs Conn:" + (end - start) + "ms.");

        start = System.currentTimeMillis();

        int i = 0;

        try {

            Connection con[] = new Connection[1500];

            Connection conn = null;

            for (i = 0; i < 1500; i++) {

                con[i] = DatabaseManager.getConnection();

                con[i].close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        end = System.currentTimeMillis();

        System.out.println("get and free a conn  " + i + " times:" + (end - start) + "ms.");

        test1();

    }



    static void test1() {

        long start = System.currentTimeMillis();

        int i = 0;

        try {

            Connection con[] = new Connection[1500];

            Connection conn = null;

            for (i = 0; i < 1500; i++) {

                con[i] = DatabaseManager.getConnection();

                con[i].close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        long end = System.currentTimeMillis();

        System.out.println("get and free a conn  " + i + " times:" + (end - start) + "ms.");



    }

}

