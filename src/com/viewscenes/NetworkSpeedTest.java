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
		         frame.setTitle("���ٲ���");   
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
			
			/*��ʼʱ��*/
			long startTime = System.currentTimeMillis();
			
			URL url = new URL("http://114.251.47.196");// �ڴ�ָ��һ����ЧURL��ַ
			URLConnection con = url.openConnection();
			
			DataInputStream in = new DataInputStream(con.getInputStream());
			
			int fileLength = 0; //�Ķ����ݳ���
			int tmp = 0;
			while((tmp = in.read()) != -1){
				
				fileLength += tmp;
				
			}
			
			/* ����ʱ��*/
			long endTime = System.currentTimeMillis();
			
			System.out.println(endTime - startTime);// ��ӡ����ʱ�� ��λ����
			System.out.println(startTime+"="+endTime);
			
			System.out.println(fileLength); //��ӡ��ȡ�����ܳ��� ��λ byte
			
			// �ö�ȡ�����ܳ���/ʹ��ʱ�� = ��λʱ������ȡ������ ��λ�Լ�����
			
			double result = fileLength/(endTime - startTime)/1024;
			
			System.out.println("����Ϊ�� "+result+" k/s");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

}

}