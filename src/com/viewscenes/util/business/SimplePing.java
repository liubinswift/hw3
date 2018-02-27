package com.viewscenes.util.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;

   /**
    * <p>Title: </p>
    *
    * <p>Description: </p>
    *
    * <p>Copyright: Copyright (c) 2009</p>
    *
    * <p>Company: Viewscenes</p>
    *
    * @author 刘斌
    * @version 1.0
    */
   public class SimplePing {

       /**
        * PING一个站点IP
        * @param ip
        * @return 连通TRUE
        */
       public static boolean ping(String ip) {
           try {
               Process p = Runtime.getRuntime().exec("ping -n 1 " + ip);
               BufferedReader br = new BufferedReader(new InputStreamReader(p.
                       getInputStream()));
               String aLine = br.readLine();
               while (aLine != null) {
                   //        System.out.println(aLine);
                   if (aLine.indexOf("Request timed out") != -1) {
                       br.close();
                       return false;
                   }
                   else if(aLine.indexOf("请求超时")!=-1)
                   {
                	   br.close();
                       return false;  
                   }
                   else if(aLine.indexOf("无法访问目标主机")!=-1)
                   {
                	   br.close();
                       return false;  
                   }
                   else if (aLine.indexOf("Destination host unreachable") != -1) {
                       br.close();
                       return false;
                   }
                   else if (aLine.indexOf("TTL expired in transit") != -1) {
                       br.close();
                       return false;
                   }
                   else if(aLine.indexOf("address must be specified")!=-1){//add by wangfx 解决ip为空的问题
                	   br.close();
                       return false;
                   }

                   aLine = br.readLine();
               }
               br.close();
          }
          catch (Exception ex) {
              ex.printStackTrace();
          }
          return true;
      }

  }











