package org.jmask.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
import org.jmask.web.ui.standard.*;
import java.io.*;

public class TestObj2Xml {
    public TestObj2Xml() {
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ObjectToXml ox = new ObjectToXml();
        Button btn = new Button();
        btn.skin = "aa";
        String xml = null;
        try {
            xml = ox.toXml(btn);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        long time2 = System.currentTimeMillis();
        long time = time2-time1;
        System.out.println(xml);
        System.out.println("time:"+time);
    }
}
