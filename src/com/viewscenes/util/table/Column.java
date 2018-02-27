package com.viewscenes.util.table;

import java.util.ArrayList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Column {
    public String columnName = "";
    public String columnType = "edit";
    public String columnFunc = "";
    public String initValue = "";
    public ArrayList optionList = new ArrayList();
    public Column(String name,String type,String func,String value) {
        this.columnName = name;
        this.columnType = type;
        this.columnFunc = func;
        this.initValue = value;
    }

    public void setOption(ArrayList list){
        this.optionList = list;
    }
}
