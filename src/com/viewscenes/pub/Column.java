package com.viewscenes.pub;



/**

 *

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: Novel-Tongfang</p>

 * @author not attributable

 * @version 1.0

 */

public class Column implements java.io.Serializable{

    public final static String COLUMN_TYPE_STRING = "VARCHAR2";

    public final static String COLUMN_TYPE_DATE = "DATE";

    public final static String COLUMN_TYPE_TIME = "TIME";

    public final static String COLUMN_TYPE_LONG = "LONG";

    public final static String COLUMN_TYPE_DOUBLE = "DOUBLE";

    public final static String COLUMN_TYPE_NUMBER = "NUMBER";

    public final static String COLUMN_TYPE_TIMESTAMPTZ = "TIMESTAMPTZ";

    //刘斌增加,在数据自动上报时需要保存的数据量大,VARCHAR2不能满足,所以用CLUB
    public final static String COLUMN_TYPE_CLOB = "CLOB";



    String columnLabel = "";

    String columnName = "";

    String columnType = COLUMN_TYPE_STRING;



    public Column() {

    }



    public Column(String name, String type) {

        columnLabel = name;

        columnName = name;

        columnType = type;

    }



    public void setColumnName(String columnName) {

        this.columnName = columnName.toLowerCase();



    }



    public void setColumnLabel(String columnLabel) {

        this.columnLabel = columnLabel;

    }



    public void setColumnType(String columnType) {

        this.columnType = columnType;

    }



    public String getColumnName() {

        return columnName;

    }



    public String getColumnLabel() {

        return columnLabel;

    }



    public String getColumnType() {

        return columnType;

    }



}

