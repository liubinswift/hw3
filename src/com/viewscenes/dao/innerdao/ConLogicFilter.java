package com.viewscenes.dao.innerdao;



import com.viewscenes.dao.*;

import com.viewscenes.pub.*;

import com.viewscenes.dao.database.DbException;



import java.util.*;



import org.jdom.*;



/**

 * 逻辑表条件对象的过滤器

 * 如：

 * 表：mon_center_tab

 * 传入的字段为:center_id,loc_id

 * 条件为:loc_id = 1 and center_id = 5

 * 在配置文件节点logicconfig中：mon_center_tab是逻辑表，它和sys_location_tab loc的loc_id关联

 * 则输出为：

 * 表：mon_center_tab tab,sys_location_tab loc

 * 传入的字段为:tab.center_id,loc.loc_id

 * 条件为:loc.loc_id = 1 and tab.center_id = 5

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class ConLogicFilter

        extends ConBaseFilter {

    private Element _ele;

    //所有物理字段，别名还是物理字段

    private static final int _ALIASFIELD = 0;

    private static final int _PHSICSFIELD = 1;

    private final static String _ALIAS = "tab.";



    public ConLogicFilter(String field, GDSet condition) throws DbException {

        _ele = XmlReader.getItem("logicconfig", "table", condition.getTableName());

        //设置各个属性

        setField(field);

        setTabName();

        setCondition(condition);

    }



    /**

     * 设置查询条件的属性

     * @param condition

     * @throws DbException

     */

    private void setCondition(GDSet condition) throws DbException {

        String con = combinCondition(condition);

        con = Common.combineSQL(con,"and");

        //把配置中的从表和主表的关联条件和外部传入的条件结合

        _condition = _ele.getAttributeValue("condition") + con;

    }



    /**

     * 设置字段的属性

     * @param field

     */

    private void setField(String field) {

        _field = appendField(field);

    }



    /**

     * 设置表名的属性

     * @param tabName

     */

    private void setTabName() {

        _tabName = _ele.getAttributeValue("tab");

    }



    /**

     * 得到第i行的field值

     * @param condition

     * @param conditioni 的行数

     * @return 字段值

     * @throws GDSetException

     */

    protected String getConColumn(GDSet condition, int i) throws GDSetException {

        String col = condition.getString(i, ConditionColumn.FIELD);

        return getColAlias(col, _PHSICSFIELD);

    }



    /**

     * 得到和配置文件组合后的字段名

     * count(*)->count(*)

     * *->tab.*,tab.loc_id

     * center_id,loc_id->tab.center_id,tab.loc_id

     * center_id,loc_name->tab.center_id,loc.loc_id这是附加字段

     * @param field 返回的字段

     * @return

     */

    private String appendField(String field) {

        String fd = "";

        //count(*)->count(*)

        if (field.equals("count(*)")) {

            return field;

        }

        // *->tab.*,tab.loc_id

        if (field.equals("*")) {

            fd = _ALIAS + field + getAllField();

        } else {

            fd = getColAlias(field, _ALIASFIELD);

        }

        return fd;

    }



    /**

     * 得到逻辑字段对应的物理字段名称或逻辑字段和物理字段的全集

     * @param field

     * @param fieldType

     *     fieldType = _ALIASFIELD

     *     如:head_name->head.name head_name(_field保存从xml文件中读出的所有表的附加字段)

     *     fieldType = _PHSICSFIELD

     *     如:head_name->head.name(_field保存从xml文件中读出的所有表的附加字段)

     * @return

     */

    private String getColAlias(String field, int fieldType) {

        StringBuffer col = new StringBuffer();

        String[] fieldName = Common.Field2Array(field);

        for (int i = 0; i < fieldName.length; i++) {

            String fd = getEachColAlias(fieldName[i], fieldType);

            col.append(fd);



            if (i < fieldName.length - 1) {

                col.append(",");

            }

        }

        return col.toString();

    }



    /**

     * 得到附加的物理字段的名称或者得到附加字段的别名和物理字段的名称

     * fieldType = _ALIASFIELD

     * 如:head_name->head.name head_name(_field保存从xml文件中读出的所有表的附加字段)

     * fieldType = _PHSICSFIELD

     * 如:head_name->head.name(_field保存从xml文件中读出的所有表的附加字段)

     * @param field 附加字段

     * @return

     * 如果不属于附加字段:则tab. + field

     */

    private String getEachColAlias(String field, int fieldType) {

        List list = _ele.getChildren();

        Iterator iter = list.iterator();

        while (iter.hasNext()) {

            Element ele = (Element) iter.next();

            String alias = ele.getAttributeValue("alias_name");

            if (alias != null && alias.equals(field)) {

                if (fieldType == _PHSICSFIELD) {

                    return ele.getAttributeValue("phy_name");

                } else {

                    return ele.getAttributeValue("phy_name") + " " + alias;

                }

            }

        }

        return _ALIAS + field;

    }



    /**

     * 得到所有附加字段

     * @param field

     * @return

     */

    private String getAllField() {

        StringBuffer ret = new StringBuffer();

        List list = _ele.getChildren();

        Iterator iter = list.iterator();

        while (iter.hasNext()) {

            Element ele = (Element) iter.next();

            ret.append(",").append(ele.getAttributeValue("phy_name"));

            ret.append(" ").append(ele.getAttributeValue("alias_name"));

        }

        return ret.toString();

    }



}
