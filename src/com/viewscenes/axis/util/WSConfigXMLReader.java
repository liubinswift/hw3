package com.viewscenes.axis.util;

import org.jdom.JDOMException;
import java.net.URL;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import java.util.Hashtable;
import java.util.List;

/**
 * 系统与外部接口配置文件处理类
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
public class WSConfigXMLReader {

    public WSConfigXMLReader() {
    }

    private static Element root;

        private static Hashtable<String, String> WSProMap = new Hashtable<String, String>();

        static {

            try {

              URL configFile = WSConfigXMLReader.class.getClassLoader().getResource("web_service.xml");

              SAXBuilder builder = new SAXBuilder(false);

              Document doc = builder.build(configFile);

              root = doc.getRootElement();
              initWSConProperties();
            }

            catch (JDOMException ex) {

              ex.printStackTrace();

            }

        }

        /**
         * 将所有配置存放在一个hashmap中
         */
        public static void  initWSConProperties(){

                List wsList = root.getChildren("ws");

                for (int i=0; i<wsList.size(); i++){

                        Element e = (Element)wsList.get(i);

                        String name = e.getAttributeValue("name");

                        String url = e.getAttributeValue("url");

                        WSProMap.put(name, url);
                }

        }

        public static String getWSUrl(String wsName){

            if (WSProMap == null){

                initWSConProperties();

            }

            return (String)WSProMap.get(wsName);

        }


}
