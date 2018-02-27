package com.viewscenes.app;


import com.sun.tools.javac.tree.Tree.Break;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.web.common.Common;

import com.viewscenes.bean.ResHeadendBean;
import com.viewscenes.dao.XmlReader;

import flex.messaging.io.amf.ASObject;



import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

import org.jdom.Element;

/**
 * <p>Title:前端信息 </p>
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
 
    public class CreateFtpFolderThread extends Thread{

    	 public int lastRunday=0;
       
        public CreateFtpFolderThread() {

        }

        public void run(){
            while (true) {
				
				
				Calendar ca=Calendar.getInstance();
				ca.add(Calendar.HOUR, 1);//加一个小时 提前一个小时创建。
				int today=ca.get(Calendar.DAY_OF_MONTH);
				
				//当如果跨天的话，执行新一天的线程！
				if(lastRunday==0||lastRunday!=today)
				{
					System.out.println("lastRunday="+lastRunday+":today="+today);
					  try {
						  this.createFolder(ca);
						  lastRunday=today;
						  Thread.sleep(1000*30*60);//三十分钟休息下。
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
					   
				}
			}
        }
        /**
         * 
         * @param msg String
         * @return String
         */
        public void createFolder(Calendar cal) {
        	String path= SystemConfig.getVideo_location();
        	 /**
			  * 这里创建默认录音文件上传目录。
			  */
        	 String  runat = XmlReader.getConfigItem("RunConfig").getAttributeValue("runat");
       	  if (runat.equals("0")){
       		path= SystemConfig.getLoc_Video_location();  
       	  }
			
			if(path!=null&&path!=""&&path.length()!=0)
			{
				ArrayList list=(ArrayList) Common.getHeadendList(new ASObject());
			    ArrayList codelist=new  ArrayList();
			    for(int i=0;i<list.size();i++)
			    {
			    	ResHeadendBean bean =(ResHeadendBean) list.get(i);
			    	String code=bean.getCode();
			    	codelist.add(code);
			    }
			
                for(int days=0;days<30;days++)
                {
              	  int year=cal.get(Calendar.YEAR);//得到年
				  int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
				  int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
				   File file=new File(path);
				   if(!file.exists())
					   file.mkdir();
				   path=path+"\\"+year;
				   file=new File(path);
				   if(!file.exists())
					   file.mkdir();
				   path=path+"\\"+month;
				   file=new File(path);
				   if(!file.exists())
					   file.mkdir();
				   path=path+"\\"+day;
				   file=new File(path);
				   if(!file.exists())
					   file.mkdir();
			   for(int j=0;j<codelist.size();j++)
			   {
				   file=new File(path+"\\"+codelist.get(j)); 
				   if(!file.exists())
					   file.mkdir();
			   }
			   cal.add(Calendar.DAY_OF_MONTH, -1);
			   path= SystemConfig.getVideo_location();
			  }
			}
		    

        }
    

    }

