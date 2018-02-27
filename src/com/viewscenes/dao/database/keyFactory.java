package com.viewscenes.dao.database;



/**

 *  主键生成器工厂

 *  暂时只生成ORACLE的主键生成器

 */



public class keyFactory {

    public static final short ORA_KEY = 0;



    public KeyCreator create(short name) {

        if (name == ORA_KEY) {

            return new OraKeyCreator();

        }



        return null;

    }



}
