package org.jmask.web.controller;

import org.jdom.*;
import org.jdom.output.*;
import java.io.*;
import java.util.ArrayList;

public class TestJDom {
    public TestJDom() {
    }

    public static void main(String[] args) {
        Element root = new Element("Msg");
        root.addAttribute("return", "1");
        Element urlNode = new Element("url");
        ArrayList children = new ArrayList();
        children.add(urlNode);
        root.setChildren(children);
        Document doc = new Document(root);
       System.out.print(doc.toString());


        XMLOutputter out = new XMLOutputter();
//        out.setEncoding("GB2312");
        try {
            out.output(doc, System.out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       // out.
    }
}
