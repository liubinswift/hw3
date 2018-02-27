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
public class Row {
    public ArrayList cellList = new ArrayList();
    public String rowId = "";
    public Row(ArrayList list,String rowId) {
        this.cellList = list;
        this.rowId = rowId;
    }
}
