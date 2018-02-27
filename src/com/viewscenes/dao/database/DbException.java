package com.viewscenes.dao.database;



import com.viewscenes.pub.exception.*;



/**

 * Êý¾Ý¿âÒì³£

 */



public class DbException

        extends AppException {

    public DbException(String message) {

        super(message);

    }



    public DbException(String message, Throwable cause) {

        super(message, cause);

    }



}
