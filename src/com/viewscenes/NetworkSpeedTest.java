package com.viewscenes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.awt.BorderLayout;   
import java.awt.Color;   
import java.io.BufferedReader;   
import java.io.InputStreamReader;   
  
import javax.swing.JFrame;   
import javax.swing.JScrollPane;   
import javax.swing.JTextPane;   
import javax.swing.text.BadLocationException;   
import javax.swing.text.DefaultStyledDocument;   
import javax.swing.text.MutableAttributeSet;   
import javax.swing.text.SimpleAttributeSet;   
import javax.swing.text.StyleConstants;   
import javax.swing.JFrame;

class NetworkSpeedTest {

		  
		     public static void main(String[] args) {   
		         try {          
		         JFrame frame = new JFrame();   
		                  
		         JTextPane text = new JTextPane();                      
		         frame.getContentPane().setLayout(new BorderLayout());   
		         frame.getContentPane().add(new JScrollPane(text));   
		         frame.setTitle("网速测试");   
		         frame.setSize(800, 600);   
		         frame.setVisible(true);   
		                    
		         String[] cmd = new String[]{"cmd.exe","/c","ping www.baidu.com -t"};   
		         Process process = Runtime.getRuntime().exec( cmd);   
		         BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));   
		         String info = "";   
		                    
		         DefaultStyledDocument doc = (DefaultStyledDocument)text.getStyledDocument();   
		         MutableAttributeSet attr = new SimpleAttributeSet();   
		         StyleConstants.setForeground(attr,new Color(0,102,0));   
		                               
		         while((info = br.readLine()) != null){   
		         if(!"".equals(info)){   
		         try {   
		              doc.insertString(doc.getLength(), info, attr);   
		              doc.insertString(doc.getLength(), "\r\n", null);   
		         } catch (BadLocationException e) {   
		               e.printStackTrace();   
		         }   
		            text.setCaretPosition(doc.getLength());   
		         }   
		      }   
		                    
		      } catch (Exception e) {   
		                    
		      }   
		   
		     }   
		   
		   
      
	public static void main2(String[] args) {

try {
			
			/*开始时间*/
			long startTime = System.currentTimeMillis();
			
			URL url = new URL("http://114.251.47.196");// 在此指定一个有效URL地址
			URLConnection con = url.openConnection();
			
			DataInputStream in = new DataInputStream(con.getInputStream());
			
			int fileLength = 0; //阅读数据长度
			int tmp = 0;
			while((tmp = in.read()) != -1){
				
				fileLength += tmp;
				
			}
			
			/* 结束时间*/
			long endTime = System.currentTimeMillis();
			
			System.out.println(endTime - startTime);// 打印所需时间 单位毫秒
			System.out.println(startTime+"="+endTime);
			
			System.out.println(fileLength); //打印读取流的总长度 单位 byte
			
			// 用读取数据总长度/使用时间 = 单位时间所读取的数据 单位自己换算
			
			double result = fileLength/(endTime - startTime)/1024;
			
			System.out.println("网速为： "+result+" k/s");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

}

}