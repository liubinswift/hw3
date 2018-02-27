package com.viewscenes.dao;



import com.viewscenes.pub.*;

import com.viewscenes.util.*;



public class DAOCondition

        extends GDSet {

    public static final String _NUMBER = "NUMBER";

    public static final String _VARCHAR = "VARCHAR";

    public static final String _DATE = "DATE";



    static String field[] = {

        "field", "type", "operator", "value", "between"};



    public DAOCondition(String tab) throws GDSetException {

        super(tab, field);

    }



    /**

     * 增加一个条件

     * @param field 属性

     * @param type  类型

     * @param operator 操作符

     * @param value 值

     * @param between 与前一条件的关系(支持and,or)

     * @throws GDSetException

     */

    public void addCondition(String field, String type, String operator, String value, String between) throws

            GDSetException {

        String[] row = {

            field, type, operator, value, between};

        this.addRow(row);

    }



    /**

     * 增加一个条件(与前一条件的关系默认为and)

     * @param field 属性

     * @param type  类型

     * @param operator 操作符

     * @param value 值

     * @throws GDSetException

     */

    public void addCondition(String field, String type, String operator, String value) throws

            GDSetException {

        String[] row = {

            field, type, operator, value, "and"};

        this.addRow(row);

    }

}

