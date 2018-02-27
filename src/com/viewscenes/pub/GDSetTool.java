package com.viewscenes.pub;



import java.sql.*;

import java.text.*;

import java.util.*;

import com.viewscenes.util.StringTool;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.*;



/**

 *

 * <p>Title: 通用数据对象工具</p>

 * <p>Description: 通用数据对象工具负责通用数据对象与各种对象之间的数据转换</p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: Novel-Tongfang</p>

 * @author Chen Gang

 * @version 1.0

 */

public class GDSetTool {

    /**

     * 将ResultSet中的数据添加到通用数据对象中

     * @param dataset 通用数据对象

     * @param result ResultSet对象

     * @param index 起始行号，从0开始

     * @param length 添加多少行，若为-1则将起始行之后的所后数据添加到通用对象中，若长度大于ResultSet的实际长度，则将ResultSet的起始行之后的全部数据添加到通用对象中。

     * @throws GDSetException

     */

	//liqing 添加
	public final static int Return_ResultSet=1;
	public final static int Return_ArrayListRow=2;  //每一行是一个String的ArrayList，再由这些ArrayList组成最终的ArrayList
	public final static int Return_ArrayListRowWithTitle=3;  //每一行是一个String的ArrayList，再由这些ArrayList组成最终的ArrayList，第一行为Title
	public final static int Return_ArrayListCol=4;  //每一列是一个String的ArrayList，再由这些ArrayList组成最终的ArrayList
	public final static int Return_ArrayListColWithTitle=5;  //每一列是一个String的ArrayList，再由这些ArrayList组成最终的ArrayList，每一列的第一项是Title


    public static void addResultSet(GDSet dataset, ResultSet result, int index,

                                    int length) throws GDSetException {

        if (result != null) {

            try {

                getTableMetaData(dataset, result);

                getDataFromResultSet(dataset, result, index, length);

            } catch (GDSetException ex) {
                throw ex;
            }

        } else {

            throw new GDSetException(

                    "Insert a row: result set can not be null!");

        }

    }

    /**
     * 存放ResultSet到list中
     * @author liqing
     * @param dataset
     * @param result
     * @param type
     * @throws GDSetException
     */
    public static void addResultSet(GDSet dataset, ResultSet result, int type

            ) throws GDSetException {

if (result != null) {

try {

//getTableMetaData(dataset, result);

getDataFromResultSet(dataset, result, 0, -1,type);
//----
} catch (GDSetException ex) {
throw ex;
}

} else {

throw new GDSetException(

"Insert a row: result set can not be null!");

}

}

    /**

     * 将ResultSet中的数据全部添加到通用数据对象中

     * @param dataset 通用数据对象

     * @param result ResultSet对象

     * @throws GDSetException

     */

    public static void addResultSet(GDSet dataset, ResultSet result) throws

            GDSetException {

        if (result != null) {

            try {

                getTableMetaData(dataset, result);

                getDataFromResultSet(dataset, result, 0, -1);

            } catch (GDSetException ex) {

                throw ex;

            }

        } else {

            throw new GDSetException(

                    "Insert a row: result set can not be null!");

        }

    }



    /**

     * 拷贝源对象中的一列到目标对象中，如果目标对象存在该列用源对象中的数据覆盖目标

     * 对象中的数据，如果目标对象中没有该列，则在目标对象中添加该列并拷贝源对象中的数据

     * 若源对象的行长度大于目标对象的行长度，目标对象的行长度会自动扩展到源对象的长度

     * 注意：由于通用数据对象中数据是按照行来存放的，因此列操作的效率比较低。在使用的

     * 时候请注意考虑性能问题。

     * @param srcset 源数据对象

     * @param columnName 列名称

     * @param destset 目标数据对象

     * @throws GDSetException 若原对象中没有该列则抛出异常

     */

    public static void columnCopy(GDSet srcset, String columnName,

                                  GDSet destset) throws GDSetException {

        int srcindex = srcset.getColumnIndex(columnName);

        if (srcindex < 0) {

            throw new GDSetException("Src GDSec has not such column " +

                                     columnName);

        }

        int destindex = destset.getColumnIndex(columnName);

        if (destindex < 0) {

            destset.addColumn(srcset.getColumnName(srcindex)

                              , srcset.getColumnLabel(srcindex)

                              , srcset.getColumnType(srcindex));

            destindex = destset.getColumnIndex(columnName);

        }



        for (int i = 0; i < destset.getRowCount(); i++) {

            if (i < srcset.getRowCount()) {

                destset.setString(i, destindex, srcset.getString(i, srcindex));

            }

        }

        for (int i = destset.getRowCount(); i < srcset.getRowCount(); i++) {

            String[] row = new String[destset.getColumnCount()];

            for (int j = 0; j < destset.getColumnCount(); j++) {

                row[j] = "";

            }

            row[destindex] = srcset.getString(i, srcindex);

            destset.addRow(row);

        }



    }



    private static void getTableMetaData(GDSet dataset, ResultSet result) throws

            GDSetException {

        try {

            ResultSetMetaData metaData = result.getMetaData();

            if (dataset.getTableName() == null) {

                dataset.setTableName(metaData.getTableName(1));



            }

            if (dataset.getColumnCount() == 0) {

                int columnCount = metaData.getColumnCount();



                for (int i = 0; i < columnCount; i++) {

                    dataset.addColumn(metaData.getColumnName(i + 1)

                                      , metaData.getColumnLabel(i + 1)

                                      , metaData.getColumnTypeName(i + 1));

                }

            } else {

                if (dataset.getColumnCount() != metaData.getColumnCount()) {

                    throw new GDSetException(

                            "getTableMetaData: result set column count does not match GDSet column count!");

                }

                for (int i = 1; i < dataset.getColumnCount(); i++) {

                    int index = dataset.getColumnIndex(metaData.getColumnName(i +

                            1));

                    if (index > 0) {

                        throw new GDSetException(

                                "getTableMetaData: result set column+(" +

                                metaData.getColumnName(i + 1) +

                                ") does not match GDSet column!");

                    }

                }

            }

        } catch (SQLException e) {

            throw new GDSetException("Can't get ResultSetMetaData!", e);

        }

    }





    private static void getDataFromResultSet(GDSet dataset, ResultSet result,

                                             int start, int length) throws

            GDSetException {

        try {

            if (start > 0) {

                try {

                    result.absolute(start);

                } catch (SQLException ex) {

                    //若start的值超过了resultset中的记录总数，直接则返回

                    return;

                }

            } else {

                result.beforeFirst();

            }

            int rowCount = 0;

            if (length < 0) {

                length = Integer.MAX_VALUE;

            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



            while (result.next() && rowCount < length) {
              
                int columnCount = dataset.getColumnCount();

                String[] row = new String[columnCount];
            
                for (int j = 0; j < columnCount; j++) {
                	   if (dataset.getColumnType(j).equals(Column.

                            COLUMN_TYPE_DATE)) {

                        Object ob = result.getObject(j+1);

                        if (ob!=null){

                            row[j]=df.format(ob);
                        }
                        else{

                            row[j]="";
                        }
                    }else if (dataset.getColumnType(j).equals(Column.COLUMN_TYPE_TIMESTAMPTZ)) {

                        Object ob = result.getObject(j+1);

                        if (ob!=null)

                            row[j]="";

                        else

                            row[j]="";

                    }
                    /**
                     * 增加支持大数据类型.
                     *  刘斌
                     */
                    else if (dataset.getColumnType(j).equals(Column.COLUMN_TYPE_CLOB)) {

                       Clob clob=result.getClob(j+1);
                      Reader is=clob.getCharacterStream();
                      BufferedReader br=new BufferedReader(is);
                     String s = "";
                    try {
                        s = br.readLine();

                      while(s!=null)
                      {
                        row[j]+=s;
                        s=br.readLine();
                      }
                       } catch (IOException ex1) {
                        }

                    }
                    else


                        row[j] = result.getString(j + 1);

                    if (row[j] == null) {

                      row[j] = "";

                  }





                }

                dataset.addRow(row);

                rowCount++;

            }

        } catch (SQLException e) {

            throw new GDSetException("Can't get data from ResultSet!", e);

        }

    }
    
    /**
     * 存放rs到gdset中
     * 
     * @author liqing
     * @param dataset
     * @param result
     * @param start
     * @param length
     * @param type
     * @throws GDSetException
     */
    private static void getDataFromResultSet(GDSet dataset, ResultSet result,
    		
    		int start, int length,int type) throws
    		
    		GDSetException {
    	ArrayList rowAL;
		ArrayList colAL;
		ResultSetMetaData rsmd=null;
    	try {
    		
    		if (start > 0) {
    			
    			try {
    				
    				result.absolute(start);
    				
    			} catch (SQLException ex) {
    				
    				//若start的值超过了resultset中的记录总数，直接则返回
    				
    				return;
    				
    			}
    			
    		} else {
    			
//    			result.beforeFirst();
    			
    		}
    		
    		int rowCount = 0;
    		
    		if (length < 0) {
    			
    			length = Integer.MAX_VALUE;
    			
    		}
    		rsmd = result.getMetaData();
    		int colCount = rsmd.getColumnCount();
            for (int i = 0; i < colCount; i++) {
            	dataset.setColumnName(i,rsmd.getColumnName(i + 1));
            }
			//--------------------------
    		if(type==Return_ArrayListRowWithTitle){
				rowAL=new ArrayList();
				for(int i=1;i<=rsmd.getColumnCount();i++) {
					rowAL.add(rsmd.getColumnName(i));
				}
				dataset.addRow(rowAL);
			}
			
			if(type==Return_ArrayListRowWithTitle||type==Return_ArrayListRow){
			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				while (result.next() && rowCount < length) {
					rowAL=new ArrayList();
					for(int i=1;i<=rsmd.getColumnCount();i++) {
					    if (rsmd.getColumnTypeName(i).equalsIgnoreCase("DATE")) {
					        Object ob = result.getObject(i);

	                        if (ob!=null)
	                            rowAL.add(df.format(ob));
                        } else {
                            rowAL.add(result.getString(i));
                        }
						
					}
					dataset.addRow(rowAL);
					rowAL=null;
		        }
			}
			
			if(type==Return_ArrayListCol||type==Return_ArrayListColWithTitle){
				for(int i=1;i<=rsmd.getColumnCount();i++) {
					colAL=new ArrayList();
					if(type==Return_ArrayListColWithTitle){
						colAL.add(rsmd.getColumnName(i));
					}
					dataset.addColumn(colAL);
				}
			}
			if(type==Return_ArrayListCol||type==Return_ArrayListColWithTitle){
				while (result.next() && rowCount < length) {
					for(int i=1;i<=rsmd.getColumnCount();i++) {
						((ArrayList)dataset.getListColumn(i-1)).add(result.getString(i));
					}
		        }
			}
			//--------------------------
    		
    	} catch (SQLException e) {
    		
    		throw new GDSetException("Can't get data from ResultSet!", e);
    		
    	}
    	
    }



    public static GDSet parseStringToGDSet(String str) throws GDSetException{

        GDSet dataset = new GDSet("");

        int indexStart = str.indexOf("<tablename>");

        int indexEnd = str.indexOf("</tablename>");

        if ((indexStart==-1)||(indexEnd==-1))

            throw new GDSetException("Parse String: Can't find table name from string!");

        dataset.setTableName(str.substring(indexStart+11,indexEnd));



        indexStart = str.indexOf("<columns>");

        indexEnd = str.indexOf("</columns>");

        if ((indexStart==-1)||(indexEnd==-1))

            return dataset;

        String column = str.substring(indexStart+9,indexEnd);

        ArrayList columnNames = parseEncodedTokenString(column,GDSet.DELIM,GDSet.CODECHAR);

        for (int i=0;i<columnNames.size();i++){

            dataset.addColumn((String)columnNames.get(i),(String)columnNames.get(i),Column.COLUMN_TYPE_STRING);

        }



        indexStart = indexEnd+10;

        if ((indexStart==-1)||(indexEnd==-1))

            return dataset;

        String data = str.substring(indexStart);

        ArrayList dataList = parseEncodedTokenString(data,GDSet.DELIM,GDSet.CODECHAR);



        int columnCount = dataset.getColumnCount();

        int rowCount = dataList.size()/columnCount;



        for (int i=0;i<rowCount;i++){

          String [] row = new String[columnCount];

          for (int j=0;j<columnCount;j++){

              row[j] = (String)dataList.get(i*columnCount+j);

          }

          dataset.addRow(row);

        }

        return dataset;

    }



    public static ArrayList parseEncodedTokenString(String src, String delim, char code){

        ArrayList destList = new ArrayList();

        int indexStart = 0;

        int indexEnd = src.indexOf(delim);

        int delimLen = delim.length();

        if (indexEnd==-1)

            destList.add(src);

        while ((indexEnd>=0)&&(indexEnd<src.length())){

            if ((indexEnd+delimLen<src.length())&&(src.charAt(indexEnd+delimLen)==code)){

                indexEnd = src.indexOf(delim,indexEnd+delimLen);

                if (indexEnd==-1){

                    destList.add(decode(src.substring(indexStart),delim,code));

                }

            }

            else{

                destList.add(decode(src.substring(indexStart,indexEnd),delim,code));

                indexStart = indexEnd+delimLen;

                indexEnd = src.indexOf(delim,indexStart);

            }

        }

        return destList;

    }

    public static String encode(String src, String delim, char code){

        return StringTool.replace(src,delim,delim+code);

    }

    public static String decode(String src, String delim, char code){

        return StringTool.replace(src,delim+code,delim);

    }



    /**

      * field是否在GDSet的列标题中

      * @param gd

      * @param field

      * @return

      */

     private boolean GDColContain(GDSet gd, String field) {

       if (gd.getColumnIndex(field)<0)

          return false;

       else
          return true;
     }
}
