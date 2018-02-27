package com.viewscenes.pub;



import java.sql.*;

import java.util.*;

import com.viewscenes.util.StringTool;


/**

 *

 * <p>Title: 通用数据对象类</p>

 * <p>Description: 通用数据对象封装了一个二维的字符串表。可以通过GDSetTool类将ResultSet直接设置到GDSet对象中</p>

 * <p>Company: Novel_Tongfang</p>

 * @version 1.0

 * @see Column GDSetTool

 */





public class GDSet implements java.io.Serializable{

  public final static String DELIM = "$";

  public final static char CODECHAR = '#';

  private int columnCount = 0;

  private String tableName = null;

  private HashMap columnNameToIndex = new HashMap();

  private Column[] columns = null;

  private ArrayList rowList = null;

  private ArrayList colList = null;

  protected GDSet() {

  }

  /**
   * 根据表名称创建GDSet对象，可再使用addColumn方法添加列
   * @param tableName 表名称
   */

  public GDSet(String tableName) {

    this.tableName = tableName;

    rowList = new ArrayList();
    colList = new ArrayList();

  }

  /**
   * 根据表名称和字段名称创建GDSet。字段名称使用字符串数组输入
   * @param tableName 表名称
   * @param columnNames 所有的列名称
   * @throws GDSetException 如果列名称为null,或列名称重复则抛出异常
   */

  public GDSet(String tableName, String[] columnNames) throws GDSetException {

    this.columnCount = columnNames.length;

    this.tableName = tableName;

    columns = new Column[columnCount];

    for (int i = 0; i < columnNames.length; i++) {

      if (columnNames[i] == null) {

        throw new GDSetException("Column name can not be null!");

      }

      if (columnNameToIndex.get(columnNames[i].toLowerCase()) != null) {

        throw new GDSetException("Duplicated column name!");

      }

      columnNameToIndex.put(columnNames[i].toLowerCase(), new Integer(i));

      columns[i] = new Column();

      columns[i].setColumnName(columnNames[i]);

      columns[i].setColumnLabel(columnNames[i]);

      columns[i].setColumnType(Column.COLUMN_TYPE_STRING);

    }

    rowList = new ArrayList();
    colList = new ArrayList();

  }

  /**
   * 根据表名称和字段名称创建GDSet。字段名称使用字符串数组输入。
   * 并插入若干个空行
   * @param tableName 表名称
   * @param columnNames 所有的列名称
   * @param rowCount 插入多少空行
   * @throws GDSetException 如果列名称为null,或列名称重复则抛出异常
   */

  public GDSet(String tableName, String[] columnNames, int rowCount) throws

      GDSetException {

    this.columnCount = columnNames.length;

    this.tableName = tableName;

    columns = new Column[columnCount];

    for (int i = 0; i < columnNames.length; i++) {

      if (columnNames[i] == null) {

        throw new GDSetException("Column name can not be null!");

      }

      if (columnNameToIndex.get(columnNames[i].toLowerCase()) != null) {

        throw new GDSetException("Duplicated column name!");

      }

      columnNameToIndex.put(columnNames[i].toLowerCase(), new Integer(i));

      columns[i] = new Column();

      columns[i].setColumnName(columnNames[i]);

      columns[i].setColumnLabel(columnNames[i]);

      columns[i].setColumnType(Column.COLUMN_TYPE_STRING);

    }

    rowList = new ArrayList();
    colList = new ArrayList();

    for (int i = 0; i < rowCount; i++) {

      String[] row = new String[columnCount];

      rowList.add(row);

    }

  }

  /**
   *
   * @return 行数
   */

  public int getRowCount() {
	if (colList.size()>0&&rowList.size()==0) {
		return ((ArrayList)colList.get(0)).size();
	}else{
		return rowList.size();
	}

  }

  /**
   * 返回列的个数
   * @return 列数
   */

  public int getColumnCount() {

    return columnCount;

  }

  private void setColumnCount(int columnCount) {

    this.columnCount = columnCount;

  }

  /**
   * 根据列名称得到列的序号，列的序号从0开始
   * @param columnName 列名称
   * @return 列序号
   */

  public int getColumnIndex(String columnName) {

    Integer index = (Integer)this.columnNameToIndex.get(columnName.

        toLowerCase());

    if (index == null) {

      return -1;

    }

    return index.intValue();

  }

  /**
   * 根据列序号得到列的名称，列的序号从0开始
   * @param index 列序号
   * @return 列名称
   */

  public String getColumnName(int index) {

    if ( (index >= 0) && (index < columns.length)) {

      return columns[index].getColumnName();

    }
    else {

      return "";

    }

  }

  /**
   * 设置列名称
   * @param index
   * @param columnName
   * @throws GDSetException
   */

  public void setColumnName(int index, String columnName) throws GDSetException {

//    if ( (index >= 0) && (index < columns.length)) {
//
//      int i = getColumnIndex(columnName);
//
//      if ( (i >= 0) && (i != index))
//
//        throw new GDSetException("Set column name:column name "
//
//                                 + columnName + " is allready in GDSet."
//
//                                 + " GDSet Can't has two same column name.");
//
//      String oldName = getColumnName(index);
//
//      columnNameToIndex.remove(oldName);
	  this.columnCount++;
      columnNameToIndex.put(columnName.toLowerCase(), new Integer(index));

//      columns[index].setColumnName(columnName.toLowerCase());

//    }

//    else {
//
//      throw new GDSetException("Set column name: column index("
//
//                               + index + ") out of range(0-"
//
//                               + (columns.length - 1) + ")");
//
//    }

  }

  /**
   * 所有列的名称，列的序号从0开始
   *
   * @return 包含所有列名称的字符串数组
   */

  public String[] getAllColumnName() {

    String[] columnNames = new String[getColumnCount()];

    for (int i = 0; i < getColumnCount(); i++) {

      columnNames[i] = getColumnName(i);

    }

    return columnNames;

  }

  /**
   * 根据列序号得到列的显示名称，列的序号从0开始
   * @param index 列序号
   * @return 列显示名称
   */

  public String getColumnLabel(int index) {

    if ( (index >= 0) && (index < columns.length)) {

      return columns[index].getColumnLabel();

    }
    else {

      return "";

    }

  }

  /**
   * 根据列名称得到列的显示名称
   * @param columnName 列名称
   * @return 列显示名称，若找不到列返回""
   */

  public String getColumnLabel(String columnName) {

    Integer index = (Integer) columnNameToIndex.get(columnName.toLowerCase());

    if (index != null) {

      return columns[index.intValue()].getColumnLabel();

    }
    else {

      return "";

    }

  }

  public void setColumnLabel(String columnName, String columnLabel) {

    Integer index = (Integer) columnNameToIndex.get(columnName.toLowerCase());

    if (index != null) {

      columns[index.intValue()].setColumnLabel(columnLabel);

    }

  }

  public void setColumnLabel(int index, String columnLabel) {

    if ( (index >= 0) && (index < columns.length)) {

      columns[index].setColumnLabel(columnLabel);

    }

  }

  public void setColumnValue(String columnName, String value) {

    Integer index = (Integer) columnNameToIndex.get(columnName.toLowerCase());

    if (index != null) {

      if (value == null) {

        value = "";

      }

      for (int i = 0; i < this.getRowCount(); i++) {

        try {

          this.setString(i, index.intValue(), value);

        }
        catch (GDSetException ex) {

        }

      }

    }

  }

  public void setColumnValue(int index, String value) {

    if ( (index >= 0) && (index < columns.length)) {

      if (value == null) {

        value = "";

      }

      for (int i = 0; i < this.getRowCount(); i++) {

        try {

          this.setString(i, index, value);

        }
        catch (GDSetException ex) {

        }

      }

    }

  }

  /**
   * 根据列序号得到列的类型，列的序号从0开始
   * @param index 列序号
   * @return 列类型
   */

  public String getColumnType(int index) {

    if ( (index >= 0) && (index < columns.length)) {

      return columns[index].getColumnType();

    }
    else {

      return "";

    }

  }

  /**
   * 根据列名称得到列的类型
   * @param columnName 列名称
   * @return 列类型
   */

  public String getColumnType(String columnName) {

    Integer index = (Integer) columnNameToIndex.get(columnName.toLowerCase());

    if (index != null) {

      return columns[index.intValue()].getColumnType();

    }
    else {

      return "";

    }

  }

  public void setColumnType(String columnName, String columnType) {

    Integer index = (Integer) columnNameToIndex.get(columnName.toLowerCase());

    if (index != null) {

      columns[index.intValue()].setColumnType(columnType);

    }

  }

  public void setColumnType(int index, String columnType) {

    if ( (index >= 0) && (index < columns.length)) {

      columns[index].setColumnType(columnType);

    }

  }

  /**
   * 得到通用数据对象存储的逻辑表名称
   * @return 表名称
   */

  public String getTableName() {

    return tableName;

  }

  /**
   * 设置通用数据对象存储的逻辑表名称
   * @param tableName 表名称
   */

  public void setTableName(String tableName) {

    this.tableName = tableName;

  }

  /**
   * 从对象中删除一行，行号从0开始
   * @param index 行号
   * @throws GDSetException 若行号超出范围，则抛出异常
   */

  public void removeRow(int index) throws GDSetException {

    if ( (index >= 0) && (index < rowList.size())) {

      rowList.remove(index);

    }
    else {

      throw new GDSetException("Remove a row: Row index out of range!");

    }

  }
  /**
   * 从对象中删除一行，行号从0开始
   * @param index 行号
   * @throws GDSetException 若行号超出范围，则抛出异常
   */
  
  public void removeCol(int index) throws GDSetException {
	  
	  if ( (index >= 0) && (index < colList.size())) {
		  
		  colList.remove(index);
		  columnCount--;
	  }
	  else {
		  
		  throw new GDSetException("Remove a row: Row index out of range!");
		  
	  }
	  
  }

  /**
   * 在指定位置插入一行
   * @param row 插入的数据，类型为字符串数组，数组的长度必须和数据对象的列长度相同
   * @param index 插入的位置，从0开始
   * @throws GDSetException
   */

  public void insertRow(String[] row, int index) throws GDSetException {

    if (row.length == columnCount) {

      rowList.add(index, row);

    }
    else {

      throw new GDSetException("Insert a row: row's columnCount("

                               + row.length +

                               ") doesn't match table's columnCount("

                               + columnCount + ")");

    }

  }

  /**
   * 在末尾添加一行
   * @param row 添加的数据，类型为字符串数组，数组的长度必须和数据对象的列长度相同
   * @return 返回数据添加到的行号，从0开始
   * @throws GDSetException
   */

  public int addRow(String[] row) throws GDSetException {

    if (row.length == columnCount) {

      String[] newRow = new String[row.length];

      System.arraycopy(row, 0, newRow, 0, row.length);

      rowList.add(newRow);

      return rowList.size() - 1;

    }
    else {

      throw new GDSetException("Insert a row: row's columnCount("

                               + row.length +

                               ") doesn't match table's columnCount("

                               + columnCount + ")");

    }

  }
  /**
   * 在末尾添加一行
   * 
   * @author liqing
   * @param row 添加的数据，类型为字符串数组，数组的长度必须和数据对象的列长度相同
   * @return 返回数据添加到的行号，从0开始
   * @throws GDSetException
   */

  public void addRow(ArrayList row) throws GDSetException {
	  rowList.add(row);
//    if (row.size() == columnCount) {
//      rowList.add(row);
//    }
  }
  public void addRow(ArrayList row,int index) throws GDSetException {
	  rowList.add(index,row);
//	  if (row.size() == columnCount) {
//		  rowList.add(index,row);
//	  }
  }
  //hg add 2005-04-13
  /**
   * 在指定位置添加一行
   * @param row 添加的数据，类型为字符串数组，数组的长度必须和数据对象的列长度相同
   * @return 返回添加完的总行数，从0开始
   * @throws GDSetException
   */
  public int addRow(String[] row, int index) throws GDSetException {

    if (row.length == columnCount) {

      String[] newRow = new String[row.length];

      System.arraycopy(row, 0, newRow, 0, row.length);

      rowList.add(index, newRow);

      return rowList.size() - 1;

    }
    else {

      throw new GDSetException("Insert a row: row's columnCount("

                               + row.length +

                               ") doesn't match table's columnCount("

                               + columnCount + ")");

    }

  }

  public void setRow(String[] row, int index) throws GDSetException {

    if (index >= this.getRowCount()) {
      throw new GDSetException("index > row count!!index=" + index +
                               ",row count=" + this.getRowCount());
    }

    if (row.length == columnCount) {

      String[] newRow = new String[row.length];

      System.arraycopy(row, 0, newRow, 0, row.length);

      rowList.set(index, newRow);

      return;

    }
    else {

      throw new GDSetException("Insert a row: row's columnCount("

                               + row.length +

                               ") doesn't match table's columnCount("

                               + columnCount + ")");

    }

  }

  /**
   * 在末尾添加一空行
   * @return 返回数据添加到的行号，从0开始
   */

  public int addRow() {

    String[] newRow = new String[this.getColumnCount()];

    for (int i = 0; i < this.getColumnCount(); i++) {

      newRow[i] = "";

    }

    rowList.add(newRow);

    return rowList.size() - 1;

  }

  /**
   * 得到一行数据
   * @param index 行号，从0开始
   * @return 行数据，类型为字符串数组
   * @throws GDSetException
   */

  public String[] getRow(int index) throws GDSetException {

    if ( (index >= 0) && (index < rowList.size())) {

    	return (String[]) rowList.get(index);

    }
    else {

      throw new GDSetException("Get a row: Row index out of range(" +

                               rowList.size() + ")!");

    }

  }

  /**
   * 从通用数据对象中得到数据的子集, 如果长度为0，则返回只包含源对象列信息的数据对象
   * @param index 起始行号，从0开始
   * @param length 长度
   * @return 通用数据对象
   * @throws GDSetException
   */

  public GDSet getSubGDSet(int index, int length) throws GDSetException {

    if (index < 0) {

      throw new GDSetException("Get Sub GDSet: Row index can not < 0!");

    }

    GDSet dataset = new GDSet(this.tableName);

    for (int i = 0; i < this.columnCount; i++) {

      dataset.addColumn(columns[i].getColumnName()

                        , columns[i].getColumnLabel()

                        , columns[i].getColumnType());

    }

    int rowCount = 0;

    while ( (rowCount < length) && ( (index + rowCount) < this.getRowCount())) {

      dataset.addRow(this.getRow(index + rowCount));

      rowCount++;

    }

    return dataset;

  }

  /**
   * 将一个数据对象中的数据添加到末尾，数据对象的列必须相同
   * @param dataset
   * @throws GDSetException
   */

  public void append(GDSet dataset) throws GDSetException {

    if (dataset == null) {

      throw new GDSetException("Append: How can I append a null to GDSet?");

    }

    if (getColumnCount() == dataset.getColumnCount()) {

      for (int i = 0; i < dataset.getRowCount(); i++)

        addRow(dataset.getRow(i));

    }

    else {

      throw new GDSetException("Append: GDSet column count is not match!");

    }

  }

  /**
   * 得到一个单元格的数据
   * @param rowIndex 行号，从0开始
   * @param columnName 列名称
   * @return 字符类型的数据
   * @throws GDSetException
   */

  public String getString(int rowIndex, String columnName) throws

      GDSetException {

    Integer columnnum = (Integer) columnNameToIndex.get(columnName.

        toLowerCase());

    if (columnnum == null) {

      throw new GDSetException("Get string: table has not such a column " +

                               columnName);

    }

    if ( (rowIndex < 0) || (rowIndex >= rowList.size())) {

      throw new GDSetException("Get string: row index(" + rowIndex +

                               ") out of range(0--" + rowList.size() +

                               ")");

    }

    String[] row = (String[]) rowList.get(rowIndex);

    return row[columnnum.intValue()];

  }

  /**
   * 得到一个单元格的数据
   * @param rowIndex 行号，从0开始
   * @param columnIndex 列号，从0开始
   * @return 字符类型的数据
   * @throws GDSetException
   */

  public String getString(int rowIndex, int columnIndex) throws

      GDSetException {

    if ( (rowIndex < 0) || (rowIndex >= rowList.size())) {

      throw new GDSetException("Get string: row index(" + rowIndex +

                               ") out of range(0--" + rowList.size() +

                               ")");

    }

    if ( (rowIndex < 0) || (columnIndex >= columnCount)) {

      throw new GDSetException("Get string: column index(" + columnIndex +

                               ") out of range(0--" + columnCount + ")");

    }

    String[] row = (String[]) rowList.get(rowIndex);

    return row[columnIndex];

  }
  /**
   * 得到一个单元格的数据
   * 
   * @author liqing
   * @param rowIndex 行号，从0开始
   * @param columnName 列名称
   * @return 字符类型的数据
   * @throws GDSetException
   */
  
  public String getCell(int rowIndex, String columnName) throws
  
  GDSetException {
	  
	  Integer columnnum = (Integer) columnNameToIndex.get(columnName.
			  
			  toLowerCase());
	  ArrayList getlist = null;
	  if (rowList.size() > 0 && colList.size() == 0) {
		  getlist = (ArrayList) rowList.get(rowIndex);
		  return (String)getlist.get(columnnum.intValue());
		} else if (rowList.size() == 0 && colList.size() > 0) {
			getlist = (ArrayList) colList.get(columnnum.intValue());
			return (String)getlist.get(rowIndex);
		}
	  
	  return null;
	  
	  
  }
  
  /**
   * 得到一个单元格的数据
   * 
   * @author liqing
   * @param rowIndex 行号，从0开始
   * @param columnIndex 列号，从0开始
   * @return 字符类型的数据
   * @throws GDSetException
   */
  
  public String getCell(int rowIndex, int columnIndex) throws
  
  GDSetException {
	  
	  ArrayList row =null;
	  if (rowList.size() > 0 && colList.size() == 0) {
			row = (ArrayList) rowList.get(rowIndex);
			return (String) row.get(columnIndex);
		} else if (rowList.size() == 0 && colList.size() > 0) {
			row = (ArrayList) colList.get(columnIndex);
			return (String) row.get(rowIndex);
		}
	  return "-1";
	  
  }

  /**
   * 设置一个单元格的数据
   * @param rowIndex 行号，从0开始
   * @param columnName 列名称
   * @param value 设置的数据
   * @throws GDSetException 设置的行和列必须是对象中已经存在的，否则抛出异常
   */

  public void setString(int rowIndex, String columnName, String value) throws

      GDSetException {

    Integer columnnum = (Integer) columnNameToIndex.get(columnName.

        toLowerCase());

    if (columnnum == null) {

      throw new GDSetException("Set string: table has not such a column " +

                               columnName);

    }

    if ( (rowIndex < 0) || (rowIndex >= rowList.size())) {

      throw new GDSetException("Set string: row index(" + rowIndex +

                               ") out of range(0--" + rowList.size() +

                               ")");

    }

    String[] row = (String[]) rowList.get(rowIndex);

    if (value == null)
      value = "";

    row[columnnum.intValue()] = value;

  }

  /**
   * 设置一个单元格的数据,若存储的字符串为null，则自动转换为""
   * @param rowIndex 行号，从0开始
   * @param columnName 列号，从0开始
   * @param value 设置的数据
   * @throws GDSetException 设置的行和列必须是对象中已经存在的，否则抛出异常
   */

  public void setString(int rowIndex, int columnIndex, String value) throws

      GDSetException {

    if ( (rowIndex < 0) || (rowIndex >= rowList.size())) {

      throw new GDSetException("Set string: row index(" + rowIndex +

                               ") out of range(0--" + rowList.size() +

                               ")");

    }

    if ( (rowIndex < 0) || (columnIndex >= columnCount)) {

      throw new GDSetException("Get string: column index(" + columnIndex +

                               ") out of range(" + columnCount + ")");

    }

    String[] row = (String[]) rowList.get(rowIndex);

    if (value == null)
      value = "";

    row[columnIndex] = value;

  }

  /**
   * 添加一列
   * 
   * @author liqing
   * @param ArrayList col 
   */
  
  public void addColumn(ArrayList col)  throws GDSetException {
	  colList.add(col);
  }
  /**
   * 添加一列
   * 
   * @author liqing
   * @param ArrayList col 
   */
  
  public void addColumn(ArrayList col,int index)  throws GDSetException {
	  colList.add(index,col);
	  columnCount++;
  }
  /**
   * 返回一列
   * 
   * @author liqing
   * @param int col 
   * @return ArrayList
   */
  
  public ArrayList getListColumn(int col)  throws GDSetException {
	  
	  if(!(colList==null||colList.size()==0)){
	   return  (ArrayList) colList.get(col);
	  }else{
		  return null;
	  }

  }
  /**
   * 返回一行
   * 
   * @author liqing
   * @param int row 
   * @return ArrayList
   */
  
  public ArrayList getListRow(int row)  throws GDSetException {
	  
	  if(!(rowList==null||rowList.size()==0)){
		  return  (ArrayList) rowList.get(row);
	  }else{
		  return null;
	  }
	  
  }
  
  /**
   * 添加一列，该列的数据全部初始化为""
   * @param columnName 列名称
   * @param columnLabel 列显示名称
   * @param columnType 列类型，参见Column类定义的列类型
   * @see Column
   */

  public void addColumn(String columnName, String columnLabel,

                        String columnType) {

    addColumn(columnName, columnLabel, columnType, "");

  }

  /**
   * 添加一列，该列的数据全部初始化为""
   * @param columnName 列名称
   * @param columnLabel 列显示名称
   * @param columnType 列类型，参见Column类定义的列类型
   * @param value 默认值
   * @see Column
   */

  public void addColumn(String columnName, String columnLabel,

                        String columnType, String value) {

    if (columnType == null) {

      columnType = Column.COLUMN_TYPE_STRING;

    }

    if (columnName != null) {

      if (this.getColumnIndex(columnName) >= 0) {

        return;

      }

      if (value == null) {

        value = "";

      }

      columnCount++;

      Column[] newColumns = new Column[columnCount];

      if ( (columns != null) && (columns.length > 0)) {

        System.arraycopy(columns, 0, newColumns, 0, columnCount - 1);

      }

      Column col = new Column();

      col.setColumnName(columnName);

      col.setColumnLabel(columnLabel);

      col.setColumnType(columnType);

      if (columnType.equalsIgnoreCase("DATE")) {

        col.setColumnType(Column.COLUMN_TYPE_DATE);

      }

      newColumns[columnCount - 1] = col;

      columns = newColumns;

      columnNameToIndex.put(columnName.toLowerCase(),

                            new Integer(columnCount - 1));

      for (int i = 0; i < rowList.size(); i++) {

        String[] row = (String[]) rowList.get(i);

        String[] newRow = new String[columnCount];

        System.arraycopy(row, 0, newRow, 0, columnCount - 1);

        newRow[columnCount - 1] = value;

        rowList.set(i, newRow);

        row = null;

      }

    }

  }

  /**
   * 添加一列，并将values中的数据添加到该列中
   * @param columnName 列名称
   * @param columnLabel 列显示名称
   * @param columnType 列类型，参见Column类定义的列类型
   * @param values 数组
   * @see Column
   */

  public void addColumn(String columnName, String columnLabel,

                        String columnType, String[] values) {

    if (columnType == null) {

      columnType = Column.COLUMN_TYPE_STRING;

    }

    if (columnName != null) {

      columnCount++;

      Column[] newColumns = new Column[columnCount];

      if ( (columns != null) && (columns.length > 0)) {

        System.arraycopy(columns, 0, newColumns, 0, columnCount - 1);

      }

      Column col = new Column();

      col.setColumnName(columnName);

      col.setColumnLabel(columnLabel);

      col.setColumnType(columnType);

      if (columnType.equalsIgnoreCase("DATE")) {

        col.setColumnType(Column.COLUMN_TYPE_DATE);

      }

      newColumns[columnCount - 1] = col;

      columns = newColumns;

      columnNameToIndex.put(columnName.toLowerCase(),

                            new Integer(columnCount - 1));

      for (int i = 0; i < rowList.size(); i++) {

        String[] row = (String[]) rowList.get(i);

        String[] newRow = new String[columnCount];

        System.arraycopy(row, 0, newRow, 0, columnCount - 1);

        if (i < values.length) {

          if (values[i] == null) {

            values[i] = "";

          }

          newRow[columnCount - 1] = values[i];

        }
        else {

          newRow[columnCount - 1] = "";

        }

        rowList.set(i, newRow);

        row = null;

      }

      for (int i = rowList.size(); i < values.length; i++) {

        String[] newRow = new String[columnCount];

        for (int j = 0; j < columnCount; j++) {

          newRow[j] = "";

        }

        newRow[columnCount - 1] = values[i];

        rowList.add(newRow);

      }

    }

  }

  /**
   * 更新一列的数据
   * @param columnName 列名称
   * @param start 起始行数，从0开始
   * @param values 数组
   */

  public void updateColumnData(String columnName, int start, String[] values) {

    if (columnName == null)

      return;

    int index = getColumnIndex(columnName);

    if (index < 0)

      return;

    for (int i = start; i < rowList.size(); i++) {

      if (i - start < values.length) {

        try {

          setString(i, index, values[i - start]);

        }
        catch (GDSetException ex) {

        }

      }

    }

  }

  /**
   * 删除一列，将列及该列的数据全部删除
   * @param columnName 列名称
   * @throws GDSetException
   */

  public void removeColumn(String columnName) throws GDSetException {

    if (columnName != null) {

      Integer columnIndex = (Integer) columnNameToIndex.get(columnName.

          toLowerCase());

      if (columnIndex == null) {

        throw new GDSetException("Remove column: not such a column " +

                                 columnName);

      }

      columnCount--;

      int index = columnIndex.intValue();

      Column[] newColumns = new Column[columnCount];

      System.arraycopy(columns, 0, newColumns, 0, index);

      System.arraycopy(columns, index + 1, newColumns, index,

                       columnCount - index);

      columns = newColumns;

      columnNameToIndex.clear();

      for (int i = 0; i < columns.length; i++) {

        columnNameToIndex.put(columns[i].columnName.toLowerCase(),

                              new Integer(i));

      }

      for (int i = 0; i < rowList.size(); i++) {

        String[] row = (String[]) rowList.get(i);

        String[] newRow = new String[columnCount];

        System.arraycopy(row, 0, newRow, 0, index);

        System.arraycopy(row, index + 1, newRow, index,

                         columnCount - index);

        rowList.set(i, newRow);

        row = null;

      }

    }
    else {

      throw new GDSetException(

          "Remove column: Column name can not be null!");

    }

  }
  /**
   * @author liqing
   * 增加一个排序功能 需要gdset有ReturnType
   * orderStr中写列index 以逗号分割
   * @param orderStr
   * @param ReturnType
   * @throws GDSetException
   */
  public void orderBy(String orderStr,int ReturnType) throws GDSetException {
	    String[] columnNames = orderStr.split(",");
	    HashMap map = new HashMap();
	    String[] temparr = null;
	    ArrayList sort = new ArrayList();
	    ArrayList templist = new ArrayList();
	    int tempint=0;
	    String temp="";
	    for (int i = 0; i < columnNames.length; i++) {
	    	map.put(columnNames[i], "");
		}
	    if(ReturnType==GDSetTool.Return_ArrayListCol){
    		for(int j=0;j<this.getRowCount();j++){
    			for (int i =0;i< columnNames.length;i++) {
	    			temp+=((ArrayList)this.colList.get(Integer.parseInt(columnNames[i]))).get(j)+"!!";
	    		}
    			for(int i =0;i<this.getColumnCount();i++){
    				if(!map.containsKey(""+i)){
    					temp+=((ArrayList)this.colList.get(i)).get(j)+"!!";
    				}
    			}
    			sort.add(temp.substring(0,temp.length()-2));
    			temp="";
			}
    		Collections.sort(sort);
    		for (int j = 0; j < this.getColumnCount(); j++) {
    			
	    		for(int i=0;i<this.getRowCount();i++){
	    			if(this.getRowCount()==18)
	    			System.out.println(i+"--"+this.getRowCount());
	    			temparr=sort.get(i).toString().split("!!");
	    			templist.add(temparr[j]);
	    			
				}
	    		if(j<columnNames.length){
		    		((ArrayList)this.colList.get(Integer.parseInt(columnNames[j]))).clear();
		    		for (int i = 0; i < templist.size(); i++) {
		    			((ArrayList)this.colList.get(Integer.parseInt(columnNames[j]))).add(templist.get(i));
					}
	    		}else{
	    			for (int i = 0; i < columnNames.length; i++) {
						if(Integer.parseInt(columnNames[i])==tempint){
							tempint++;
						}else{
							break;
						}
					}
	    			((ArrayList)this.colList.get(tempint)).clear();
	    			for (int i = 0; i < templist.size(); i++) {
	    				((ArrayList)this.colList.get(tempint)).add(templist.get(i));
					}
		    		
		    		tempint++;
	    		}
	    		templist.clear();
    		}
	    }
	  }
  
  /**
   * 对GDSet中的记录进行排序，排序字符串与sql语法中order by字句相同。
   * 如:"start_date,end_date desc"直接使用字段名称为升序，字段名称后面添加desc为降序。
   * 根据字段名称的先后顺序确定排序的优先级。
   * @param orderStr 排序字符串
   * @throws GDSetException
   */
  public void orderBy(String orderStr) throws GDSetException {
    String[] columnNames = StringTool.parseTokenString(orderStr, ",");

    for (int i = 0; i < this.getRowCount(); i++) {
      for (int j = i + 1; j < this.getRowCount(); j++) {
        if (compareRow(i, j, columnNames) > 0) {
          String[] rowi = this.getRow(i);
          String[] rowj = this.getRow(j);
          this.setRow(rowi, j);
          this.setRow(rowj, i);
        }
      }
    }
  }

  /**
   * 比较两条记录的大小
   * @param i 记录的行数
   * @param j 记录的行数
   * @param orderColumnNames 排序字段名称数组
   * @return 若记录i大于记录j,返回1，相等返回0，小于返回-1
   * @throws GDSetException 发生错误返回异常
   */
  public int compareRow(int i, int j, String[] orderColumnNames) throws
      GDSetException {
    int result = 0;
    for (int k = 0; k < orderColumnNames.length; k++) {
      String columnName = orderColumnNames[k].toLowerCase().trim();
      boolean bUpOrder = true;
      //若desc结尾则为降序排列
      if (columnName.endsWith("desc")) {
        int loc = columnName.indexOf("desc");
        columnName = columnName.substring(0, loc).trim().toLowerCase();
        bUpOrder = false;
      }

      //比较两字段的值，若该字段分出大小，则推出循环返回结果。否则继续比较下一个字段
      String valuei = this.getString(i, columnName);
      String valuej = this.getString(j, columnName);
      int compareValue = valuei.compareTo(valuej);

      //若为字段为升序
      if (bUpOrder) {
        if (compareValue > 0) {
          result = 1;
          break;
        }
        else if (compareValue < 0) {
          result = -1;
          break;
        }
      }
      //若字段为降序
      else {
        if (compareValue > 0) {
          result = -1;
          break;
        }
        else if (compareValue < 0) {
          result = 1;
          break;
        }
      }
    }
    return result;
  }

  /**
   * 将数据对象序列化为字符串。
   * GDSetTool的parseStringToGDSet方法可将字符串转换为GDSet
   * @return
   */

  public String toString() {

    StringBuffer buffer = new StringBuffer();

    buffer.append("<tablename>");

    buffer.append(GDSetTool.encode(tableName, DELIM, CODECHAR));

    buffer.append("</tablename>");

    buffer.append("<columns>");

    for (int j = 0; j < this.getColumnCount(); j++) {

      buffer.append(GDSetTool.encode(getColumnName(j), DELIM, CODECHAR));

      buffer.append(DELIM);

    }

    buffer.append("</columns>");

    for (int i = 0; i < this.getRowCount(); i++) {

      try {

        String[] row = this.getRow(i);

        for (int j = 0; j < this.getColumnCount(); j++) {

          buffer.append(GDSetTool.encode(row[j], DELIM, CODECHAR));

          buffer.append(DELIM);

        }

      }

      catch (GDSetException ex) {

        ex.printStackTrace();
      }

    }

    return buffer.toString();

  }

  public ArrayList toArrayList(){
	  ArrayList temp =new ArrayList();
	  if (colList.size()>0&&rowList.size()==0) {
		  temp.addAll(this.colList);
	  }else{
		  temp.addAll(this.rowList);
	  }
	  return temp;
  }
  /**
   * 删除对象中所有数据，保留所有列。
   */

  public void clearAllRow() {

    rowList.clear();

  }

  /**
   * 释放对象中所有的数据
   */

  public void destroy() {

    columns = null;

    columnCount = 0;

    rowList.clear();

    rowList = null;
    
    colList.clear();
    
    colList = null;

    columnNameToIndex.clear();

    columnNameToIndex = null;

    tableName = null;

  }


}


