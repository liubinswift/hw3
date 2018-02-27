package com.viewscenes.device.thirdparty;



import com.viewscenes.util.LogTool;

/**

 * <p>Title: PostHttpThread</p>

 * <p>Description: this class is used for create a thread to post xml to destenation URL</p>

 * <p>Copyright: Copyright (c) 2002</p>

 * <p>Company:  Tsinghua Yongxin Tongfang </p>

 * @author MIKEJ

 * @version 1.1

 */



public class PostHttpThread

    extends Thread {

  private String httpurl;

  private String text;



  /**

   *Initialize a PostHttpThread object

   * @param httpurl  dest URL to Post

   * @param text  XML Text Info to Post

   */

  public PostHttpThread(String httpurl, String text) {

    this.httpurl = httpurl;
    this.text = text;
    start();

  }



  /**

   * This public <FONT color='FF0000'><B>public</B></font> function is custom run function for Thread

   */

  public void run() {

    try {

      LogTool.debug("devicelog", httpurl);

      int count=0;
      boolean bOK = false;
     // while (!bOK&&(count<7)){
          TFURLConnection connection = new TFURLConnection(httpurl);
          connection.TFPostURLRequest(text);
          connection.TFGetURLResponse();

          connection.close();
         // System.out.println("text=="+connection.m_strGetContent);
         // if (connection.m_strGetContent.startsWith("200"))
        //      bOK = true;
        //  else{
        //      sleep(2000);
        ///      count++;
        //  }
     // }
    } catch (Exception ex) {
      LogTool.fatal("devicelog", ex);
    }
  }
}
