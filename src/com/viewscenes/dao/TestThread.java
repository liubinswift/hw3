package com.viewscenes.dao;



public class TestThread

        extends Thread {

    public void run() {

        try {

            test t = new test();

//            DAOOperator d = (DAOOperator) DaoFactory.create(DaoFactory.DAO_OBJECT);
//
            while (true) {
//
//                t.insertDAO(d);
//
//                t.updateDAO(d);
//
//                t.deleteDAO(d);
//
//                t.queryDAO1(d);

                sleep(10000);

            }

        } catch (InterruptedException ex) {

            ex.printStackTrace();

        }

    }



    public static void main(String[] arv) {

        for (int i = 0; i < 20; i++) {

            new TestThread().start();

        }

    }

}
