package org.jmask.persist.bo;

import java.lang.reflect.*;
import java.util.*;

/**
 *
 * <p>Title: 基础数据对象</p>
 * <p>Description:数据对象的基类，使用reflection机制完成数据对象到数据库的存储和更新 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: jinshi</p>
 * @author 陈刚
 * @version 1.0
 */

public class BaseBo extends AbstractBo{
  public long id = 0;

  protected String bUpdate = "";

  public BaseBo() {
  }

  public static String xmlHead = "";

  public String toXml(){
      StringBuffer buffer = new StringBuffer();
      buffer.append(xmlHead);

      return buffer.toString();
  }
/*
  public String getTableName(){
    StringBuffer buffer = new StringBuffer();
    buffer.append("nms_");
    String name = this.getClass().getName();
    int loc = name.lastIndexOf(".");
    buffer.append(name.substring(loc+1).toLowerCase());
    buffer.append("_tab");
    return buffer.toString();
  }

  public String getSeqName(){
    StringBuffer buffer = new StringBuffer();
    buffer.append("nms_");
    String name = this.getClass().getName();
    int loc = name.lastIndexOf(".");
    buffer.append(name.substring(loc+1).toLowerCase());
    buffer.append("_seq");
    return buffer.toString();
  }

  public String getCreateSeqSql(){
    StringBuffer buffer = new StringBuffer();
    buffer.append("create sequence ");
    buffer.append(getSeqName());
    buffer.append(" start with 10000");
    return buffer.toString();
  }


  public String getDeleteSql(){
    StringBuffer buffer = new StringBuffer();
    buffer.append("delete ");
    buffer.append(getTableName());
    buffer.append(" where id = ");
    buffer.append(id);
    return buffer.toString();
  }

  private String getFieldStringValue(Field field){
    String value = "";
    String typeName = field.getType().getName();
    try {
      if (typeName.equals("java.lang.String")) {
        value = (String) field.get(this);
        value = "'"+value+"'";
      }
      else if (typeName.equals("int")) {
        value = String.valueOf(field.getInt(this));
      }
      else if (typeName.equals("long")) {
        value = String.valueOf(field.getLong(this));

      }
      else if (typeName.equals("float")) {
        value = String.valueOf(field.getFloat(this));
      }
      else if (typeName.equals("double")) {
        value = String.valueOf(field.getDouble(this));
      }
      else if (typeName.equals("boolean")) {
        boolean b = field.getBoolean(this);
        if (b)
          value = "1";
        else
          value = "0";
      }

      else if (typeName.equals("java.util.Date")) {
        Date d = (Date)field.get(this);
        value = "TO_DATE('"+StringTool.Date2String(d)+"','YYYY-MM-DD HH24:MI:SS')";
      }
      else {
        return null;
      }
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }

    return value;
  }

  private String getFieldSqlType(Field field){
    String sqlType = "";
    String typeName = field.getType().getName();
      if (typeName.equals("java.lang.String")) {
        sqlType = "varchar2(100)";
      }
      else if (typeName.equals("int")) {
        sqlType = "number";
      }
      else if (typeName.equals("long")) {
        sqlType = "number";
      }
      else if (typeName.equals("float")) {
        sqlType = "number";
      }
      else if (typeName.equals("double")) {
        sqlType = "number";
      }
      else if (typeName.equals("boolean")) {
        sqlType = "number";
      }
      else if (typeName.equals("java.util.Date")) {
        sqlType = "date";      }
      else {
        return null;
      }
    return sqlType;
  }


  public String getCreateTableSql(){
    StringBuffer buffer = new StringBuffer();
    Field[] fields = this.getClass().getFields();
    buffer.append("create table ");
    buffer.append(getTableName());
    buffer.append("(\r\n");
    for (int i=0;i<fields.length;i++){
      String sqlType = getFieldSqlType(fields[i]);
      if (sqlType==null)
        continue;
      buffer.append("       ");
      buffer.append(fields[i].getName());
      buffer.append(" ");
      buffer.append(sqlType);
      if (i<fields.length-1)
        buffer.append(",");
      buffer.append("\r\n");
    }
    buffer.append(");\r\n");
    buffer.append("alter table ");
    buffer.append(getTableName());
    buffer.append(" add (primary key(id));");

    return buffer.toString();
  }

  public String getUpdateSql(){
    StringBuffer buffer = new StringBuffer();
    Field[] fields = this.getClass().getFields();
    buffer.append("update ");
    buffer.append(getTableName());
    buffer.append(" set ");
    for (int i=0;i<fields.length;i++){
      String value = getFieldStringValue(fields[i]);
      if (value==null)
        continue;
      buffer.append(fields[i].getName());
      buffer.append("=");
      buffer.append(value);
      if (i<fields.length-1)
        buffer.append(",");
    }
    buffer.append(" where id=");
    buffer.append(id);
    return buffer.toString();
  }

  public String getInsertSql(){
    StringBuffer buffer = new StringBuffer();
    Field[] fields = this.getClass().getFields();
    buffer.append("insert into ");
    buffer.append(getTableName());
    buffer.append(" (");
    for (int i=0;i<fields.length;i++){
      String sqlType = getFieldSqlType(fields[i]);
      if (sqlType==null)
        continue;
      buffer.append(fields[i].getName());
      if (i<fields.length-1)
        buffer.append(",");
    }
    buffer.append(")");
    buffer.append(" values(");
    for (int i=0;i<fields.length;i++){
      String value = getFieldStringValue(fields[i]);
      if (value==null)
        continue;
      buffer.append(value);
      if (i<fields.length-1)
        buffer.append(",");
     }
     buffer.append(")");
    return buffer.toString();
  }

  public boolean updateFromObj(BaseBo bo){
    System.out.println("BaseBo updateFromObj mehtod is not implemented yet!Please use Sub Class Implementation!");
    return false;
  }


  public boolean save(){
    String sql = "";
    if (id==0){
      GDSet seqSet = SystemCache.loadDataBySql("select "+getSeqName()+".nextval as val from dual");
      try {
        id = Long.parseLong(seqSet.getString(0, "val"));
      }
      catch (GDSetException ex1) {
        ex1.printStackTrace();
      }
      sql = getInsertSql();
    }
    else{
      sql = getUpdateSql();
    }
    try {
      SystemCache.updateDataBySql(sql);
    }
    catch (DbException ex) {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean delete(){
    String sql = getDeleteSql();
    try {
      SystemCache.updateDataBySql(sql);
    }
    catch (DbException ex) {
      ex.printStackTrace();
    }
    return true;
  }

  public void setFieldValue(Field field, String value){
    String sqlType = "";
   String typeName = field.getType().getName();
    try {
      if (typeName.equals("java.lang.String")) {
        field.set(this, value);
      }
      else if (typeName.equals("int")) {
        field.setInt(this,Integer.parseInt(value));
      }
      else if (typeName.equals("long")) {
        field.setLong(this,Long.parseLong(value));
      }
      else if (typeName.equals("float")) {
        field.setFloat(this,Float.parseFloat(value));
      }
      else if (typeName.equals("double")) {
        field.setDouble(this,Double.parseDouble(value));
      }
      else if (typeName.equals("boolean")) {
        if (value.equals("0"))
          field.setBoolean(this,false);
        else
          field.setBoolean(this,true);
      }

      else if (typeName.equals("java.util.Date")) {
        field.set(this,StringTool.stringToDate(value));
      }
      else {

      }
    }
    catch (IllegalAccessException ex) {
      LogTool.debug(ex);
    }
    catch (IllegalArgumentException ex) {
    }

  }
/*
  public void getFromGDSet(GDSet dataSet, int row){
      Field[] fields = this.getClass().getFields();
      for (int i=0;i<fields.length;i++){
        try {
          if (fields[i].getType().getName().equals("java.lang.String")
              ||fields[i].getType().getName().equals("int")
              ||fields[i].getType().getName().equals("long")
              ||fields[i].getType().getName().equals("float")
              ||fields[i].getType().getName().equals("double")
              ||fields[i].getType().getName().equals("boolean")
              ||fields[i].getType().getName().equals("java.util.Date")
              )
          setFieldValue(fields[i], dataSet.getString(row, fields[i].getName()));
        }
        catch (GDSetException ex) {
          LogTool.debug(ex);
        }
      }
    }
*/



}
