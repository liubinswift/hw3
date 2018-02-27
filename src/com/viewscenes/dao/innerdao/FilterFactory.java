package com.viewscenes.dao.innerdao;



import com.viewscenes.dao.*;

import org.jdom.*;

import com.viewscenes.pub.*;

import com.viewscenes.dao.database.*;



public class FilterFactory {



    /**

     * 得到一个Filter的实例

     * @param field

     * @param condition

     * @return

     * @throws DbException

     */

    public static ConBaseFilter create(String field, GDSet condition) throws DbException {

        String tab = condition.getTableName();

        if (IsLogicTab(tab))

            return new ConLogicFilter(field, condition);



        return new ConBaseFilter(field, condition);

    }



    /**

     * 是否逻辑表

     * @param tab

     * @return

     * true:是逻辑表

     * false:物理表

     */

    private static boolean IsLogicTab(String tab) {

        Element _ele = XmlReader.getItem("logicconfig", "table", tab);

        //物理表

        if (_ele == null)

            return false;



        return true;

    }

}
