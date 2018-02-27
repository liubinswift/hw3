package com.viewscenes.dao;



import java.net.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;



public class XmlReaderDeviceConfig {



  private static Element root;

  static {

    try {

      URL configFile = XmlReaderDeviceConfig.class.getClassLoader().getResource(

          "deviceconfig.xml");



      //assert configFile != null:"XmlReader读配置文件daoconfig.xml错误";

      SAXBuilder builder = new SAXBuilder(false);

      Document doc = builder.build(configFile);



      //assert doc != null:"XmlReader读配置文件daoconfig.xml错误";

      root = doc.getRootElement();


    }

    catch (JDOMException ex) {

      ex.printStackTrace();

    }



  }



  /**

   * 外部的类调用该函数获得自己的配置项

   * @param itemName 自己配置项元素的名称

   * @return 配置项元素，不为null

   */

  public static Element getConfigItem(String itemName) {

    //assert itemName != null:"XmlReader getConfigItem:getConfigItem的参数是null";



    Element e = root.getChild(itemName);

    //assert e != null:"根节点为空";

    return e;

  }



  /**

   * 得到指定子节点的下一级子节点属性值的该元素

   * @param itemName 子元素的名称，必须是root的直接下一级

   * @param atrrName 属性名称

   * @param atrrValue 属性值

   * @return null,没有该元素

   */

  public static Element getItem(String itemName, String atrrName, String atrrValue) {

    //assert (itemName != null && atrrName != null && atrrValue != null)

    //:"XmlReader getItem:getConfigItem的参数是null";



    atrrValue = atrrValue.toLowerCase();

    Element ele = getConfigItem(itemName);

    List conlist = ele.getChildren();

    for (int i = 0; i < conlist.size(); i++) {

      Element sub = (Element) conlist.get(i);

      String tem = sub.getAttributeValue(atrrName);

      if (sub.getAttributeValue(atrrName).equals(atrrValue)) {

        return sub;

      }

    }

    return null;

  }



  /**

   * 得到指定子节点的下一级指定节点的指定属性的属性值

   * 如得到root下一级的子节点为itemName,该子节点的下一级为为childName,属性名为atrrName的属性

   * @param itemName 子元素的名称，必须是root的直接下一级

   * @param childName 子节点名称

   * @param atrrName  属性名称

   * @return

   */

  public static String getAttrValue(String itemName, String childName, String atrrName) {

    //assert (itemName != null && childName != null && atrrName != null)

    //:"XmlReader getItem:getAttrValue的参数是null";



    atrrName = atrrName.toLowerCase();

    Element ele = XmlReaderDeviceConfig.getConfigItem(itemName);

    Element sub = ele.getChild(childName);

    String para = sub.getAttributeValue(atrrName);

    //assert para != null:"XmlReader getAttrValue:找不到属性:"+atrrName;

    return para;

  }

  /**

   * 得到指定子节点的下一级指定节点的指定属性的属性值

   * 如得到root下一级的子节点为itemName,该子节点的下一级为为childName,属性名为atrrName的属性

   * @param itemName 子元素的名称，必须是root的直接下一级

   * @param childName 子节点名称

   * @param atrrName  属性名称

   * @return

   */

  public static List getAttrValueList(String itemName, String childName,String atrrName,String atrrName2) {

    //assert (itemName != null && childName != null && atrrName != null)

    //:"XmlReader getItem:getAttrValue的参数是null";

    Element e = root.getChild(itemName);
    List list = e.getChildren(childName);
    System.out.println(list.size());
    List iplist=new ArrayList();
    for(int i=0;i<list.size();i++)
    {
          List li=new ArrayList();
          Element el=(Element)list.get(i) ;
          String value=el.getAttributeValue(atrrName);
          String info=el.getAttributeValue(atrrName2);
          li.add(value);
           li.add(info);
          iplist.add(li);
    }

    return iplist;

  }

  public static List getAttrValueList(String itemName, String childName,String atrrName,String atrrName2,String atrrName3,String atrrName4) {

    //assert (itemName != null && childName != null && atrrName != null)

    //:"XmlReader getItem:getAttrValue的参数是null";

    Element e = root.getChild(itemName);
    List list = e.getChildren(childName);
    System.out.println(list.size());
    List iplist=new ArrayList();
    for(int i=0;i<list.size();i++)
    {
          List li=new ArrayList();
          Element el=(Element)list.get(i) ;
          String value=el.getAttributeValue(atrrName);
          String info=el.getAttributeValue(atrrName2);
          String info2=el.getAttributeValue(atrrName3);
          String info3=el.getAttributeValue(atrrName4);
          li.add(value);
          li.add(info);
          li.add(info2);
          li.add(info3);
          iplist.add(li);
    }

    return iplist;

  }

  /**

   * 得到指定属性值

   * @param itemName 子元素的名称，必须是root的直接下一级

   * @param atrrName 属性名称

   * @param atrrValue 属性值

   * @param nextAtrName 查询的属性名称

   * @return

   */

  public static String getNextAttrValue(String itemName, String atrrName, String atrrValue,

                                        String nextAtrName) {

    //assert (itemName != null && atrrName != null && atrrValue != null && nextAtrName != null)

    //:"XmlReader getItem:getNextAttrValue的参数是null";



    atrrValue = atrrValue.toLowerCase();

    Element ele = XmlReaderDeviceConfig.getItem(itemName, atrrName, atrrValue);

    //assert ele != null:"XmlReader getNextAttrValue:节点"+itemName + "的属性" + atrrName + "找不到" +

        //atrrValue;



    String indexs = ele.getAttributeValue(nextAtrName);

    //assert indexs != null:"XmlReader getNextAttrValue:找不到节点"+itemName + "的属性" + nextAtrName;

    return indexs;

  }

}
