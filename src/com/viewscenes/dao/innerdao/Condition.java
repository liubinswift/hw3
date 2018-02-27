package com.viewscenes.dao.innerdao;



/**

 * 条件对象

 * 包括条件的字段值，类型，操作符，值，和前一个条件的关系

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class Condition {



    private String column;//字段值

    private String type;//类型

    private String oper;//操作符

    private String value;//值

    private String between;//和前一个条件的关系



    public String getBetween() {

        return between;

    }



    public String getColumn() {

        return column;

    }



    public String getOper() {

        return oper;

    }



    public String getType() {

        return type;

    }



    public String getValue() {

        return value;

    }



    public void setBetween(String between) {

        this.between = between;

    }



    public void setColumn(String column) {

        this.column = column;

    }



    public void setOper(String oper) {

        this.oper = oper;

    }



    public void setType(String type) {

        this.type = type;

    }



    public void setValue(String value) {

        this.value = value;

    }



}
