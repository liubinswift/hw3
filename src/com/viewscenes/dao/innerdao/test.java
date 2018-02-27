package com.viewscenes.dao.innerdao;



import com.viewscenes.dao.*;



/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class test {

    public static void main(String[] arg) {

        SQLGenerator sql = new SQLGenerator();



        try {

            String s = null;

            DAOCondition condition = new DAOCondition("mon_center_tab");

            condition.addCondition("center_id", "NUMBER", "=", "5");

//            s = sql.generateQuerySQL("*", condition);

            System.out.println(s);



//      s = sql.generateQuerySQL("center_id,name", condition);

//      System.out.println(s);

        } catch (Exception ex) {

            ex.printStackTrace();

        }



        /*

             try {

          DAOComponent d = new DAOComponent();

          GDSet set=null;

          //insert

          String a[] = {"b", "c","d"};

          set = new GDSet("TEMP_TAB", a);

          String c[]={"10","MORNING","2000-05-06 00:00:00"};

          set.addRow(c);

          String c1[]={"11","ASDFASD","2000-05-06 00:00:00"};

          set.addRow(c1);

          String c2[]={"12","ASDFASD","2000-05-06 00:00:00"};

          set.addRow(c2);

          d.Insert(set);

          //update

    //      String b[] = {"a", "b", "c","d"};

    //      GDSet set1 = new GDSet("TEMP_TAB", b);

    //      String e[]={"100032","2","bbb","2004-05-06 00:00:00"};

    //      set1.addRow(e);

    //

    //      String e1[]={"100031","2","bbb","2004-05-06 00:00:00"};

    //      set1.addRow(e1);

    //

    //      d.Update(set1);

    //      System.out.println(d.Delete(set1));

          //query

          String f[] = {"field", "type", "operator","value","between"};

          GDSet set2 = new GDSet("TEMP_TAB", f);

          String i[]={"a","NUMBER","=","1","and"};

          set2.addRow(i);

          String g[]={"c","VARCHAR","=","hello","and"};

          set2.addRow(g);

          String h[]={"d","DATE","<","2002-10-10","and"};

          set2.addRow(h);

          GDSet set3 = d.Query("a,b,c",set2);

          for(int j=0;j<set3.getRowCount();j++)

          {

            String[] ss = set3.getRow(j);

            for(int k=0;k<ss.length;k++)

            System.out.println(ss[k]);

          }

             }

             catch (Exception ex) {

          ex.printStackTrace();

             }

         */



    }



    public test() {

    }



}
