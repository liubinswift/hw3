package com.viewscenes.util.table;

import java.util.ArrayList;
import com.viewscenes.util.StringTool;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.Attribute;
import java.io.StringWriter;
import org.jdom.output.XMLOutputter;
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
public class Table {

    public int rowCount = 0;
    public int showCount = 1;
    public int startRow = 1;
    public ArrayList showRowList = new ArrayList();
    public ArrayList columnList = new ArrayList();
    public String tableXMLStr = "";

    public Table(ArrayList columns,ArrayList rows,int rowCount,int showCount,int startRow) {
        this.columnList = columns;
        this.showRowList = rows;
        this.rowCount = rowCount;
        this.showCount = showCount;
        this.startRow = startRow;
    }

    public String createTableXML(){
        Document doc = StringTool.getXmlMsg();
        Element rootNode = doc.getRootElement();
        ArrayList list = new ArrayList();
        Element table = new Element("table");
        Attribute row_count = new Attribute("rowCount",
                        String.valueOf(this.rowCount));
        table.addAttribute(row_count);
        Attribute show_count = new Attribute("showCount",
                        String.valueOf(this.showCount));
        table.addAttribute(show_count);
        Attribute start_row = new Attribute("startRow",
                        String.valueOf(this.startRow));
        table.addAttribute(start_row);


        ArrayList tableChildren = new ArrayList();
        Element tColumn = new Element("column");
        ArrayList tColumnChildren = new ArrayList();
        for(int i=0;i<this.columnList.size();i++){
            Column c = (Column)this.columnList.get(i);
            Element column = new Element("columnlist");
            Attribute columnName = new Attribute("name",
                        c.columnName);
            column.addAttribute(columnName);
            Attribute columnType = new Attribute("type",
                        c.columnType);
            column.addAttribute(columnType);
            Attribute columnFunc = new Attribute("func",
                        c.columnFunc);
            column.addAttribute(columnFunc);
            Attribute columnInitValue = new Attribute("initvalue",
                        c.initValue);
            column.addAttribute(columnInitValue);
            if(c.columnType.equals("select")){
                ArrayList optionList = c.optionList;
                ArrayList columnChildren = new ArrayList();
                for(int l=0;l<optionList.size();l++){
                    Option o = (Option) optionList.get(l);
                    Element option = new Element("option");
                    Attribute optionName = new Attribute("name",
                            o.text);
                    option.addAttribute(optionName);
                    Attribute optionValue = new Attribute("value",
                            o.value);
                    option.addAttribute(optionValue);
                    columnChildren.add(option);
                }
                column.setChildren(columnChildren);
            }
            tColumnChildren.add(column);
        }
        tColumn.setChildren(tColumnChildren);
        tableChildren.add(tColumn);

        Element tRow = new Element("row");
        ArrayList tRowChildren = new ArrayList();
        for(int j=0;j<this.showRowList.size();j++){
            Row r = (Row)this.showRowList.get(j);
            Element row = new Element("rowlist");
            Attribute rowId = new Attribute("work_id",
                        r.rowId);
            row.addAttribute(rowId);
            ArrayList cellList = r.cellList;
            ArrayList rowChildren = new ArrayList();
            for(int k=0;k<cellList.size();k++){
                Cell c = (Cell)cellList.get(k);
                Element cell = new Element("cell");
                Attribute value = new Attribute("value",
                                                c.cellValue);
                cell.addAttribute(value);
                Attribute type = new Attribute("type",
                                                "0");
                cell.addAttribute(type);

                rowChildren.add(cell);
            }
            row.setChildren(rowChildren);
            tRowChildren.add(row);
        }
        tRow.setChildren(tRowChildren);
        tableChildren.add(tRow);

        table.setChildren(tableChildren);
        list.add(table);
        rootNode.setChildren(list);
        XMLOutputter out = new XMLOutputter();
        out.setEncoding("GB2312");
        StringWriter sw = new StringWriter();
        try{
            out.output(doc, sw);
            this.tableXMLStr = sw.toString();
        }catch(java.io.IOException ex){
            ex.printStackTrace();
        }
        return this.tableXMLStr;
    }

}
