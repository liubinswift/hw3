package com.viewscenes.device.thirdparty;



import java.io.*;

import java.net.*;

import com.viewscenes.util.LogTool;



/**

 * <p>Title: TFURLConnection</p>

 * <p>Description: this class is used for Posting XML to dest URL, it will return when connect time out </p>

 * <p>&nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp

 * which will prevent connection from waiting infinitely in the PostHttpThread.

 * <p>Copyright: Copyright (c) 2002</p>

 * <p>Company:  Tsinghua Yongxin Tongfang</p>

 * @author MIKEJ

 * @version 1.0

 */



public class TFURLConnection {

  private Socket m_sock;

  private String m_strURLHeader;

  private String m_strURLResponseHeader;

  private String m_strPostContent;

  public String m_strGetContent;

  private URL m_url;

  private int m_nPort;

  /**

   *this <FONT color='FF0000'><B>priavte</B></font> function is used for get timeout param

   @return timeout, default value is <FONT color='FF0000'><B>6000ms</B></font>

   */

  private int GetTimerOutPara() {

    return 60000; //6s as default

  }



  /**

   * Initialize Connection param, namely URL String,

   * In this function ,the Dest IP and Port will be Parsed out,

   * Timeout param will be gotten.

   * Use Socket to establish Connection to Dest.

   * @param urlString This is a URL String of Destination such as http://166.111.146.208:1021/xmlReceiver/upload/,

   */

  public TFURLConnection(String urlString) {

    try {

      //get and save port

      m_url = new URL(urlString);

      if (m_url.getPort() < 0) {

        m_nPort = 80;

      } else {

        m_nPort = m_url.getPort();

        //establish socket connection

      }

      m_sock = new Socket(m_url.getHost(), m_nPort);

      m_sock.setSoTimeout(GetTimerOutPara());

    } catch (Exception ex) {

    }

  }



  /**

   * This <FONT color='FF0000'><B>private</B></font> function is use to create HTTP package.<br>

   * <P>The HTTP package is like:</P>

   * POST xmlReceiver/upload HTTP/1.1\r\n<BR>

   * Referer: 166.111.146.208\r\n<BR>

   * User_Agent:Mozila\r\n<BR>

   * Connection: close\r\n<BR>

   * Host: 166.111.146.208:1021\r\n<BR>

   * Content-Length: 1521\r\n<BR>

   * \r\n<BR>

   * --XML Content-<BR>

   * @param strContent The Content of XML

   */

  private void BuildHttpPack(String strContent) {

    m_url.getPath();

    String strHead = "POST " + m_url.getPath() + " HTTP/1.1\r\n";

    strHead += "Referer: " + m_url.getHost() + "\r\n";

    strHead += "User-Agent: Mozila\r\n";

    strHead += "Connection: close\r\n";

    strHead += "Host: " + m_url.getHost() + ":" + m_nPort + "\r\n";

    try {
      strHead += "Content-Length: " + strContent.getBytes("GB2312").length +
          "\r\n\r\n";
    }
    catch (UnsupportedEncodingException ex) {
      ex.printStackTrace();
    }

    m_strPostContent = strHead + strContent;

  }



  /**

   * This <FONT color='FF0000'><B>public</B></font> function is used to post a HTTP requet to Destination,it provide a method

   *<P>to autoclose connection when time out.</P>

   * @param strPostContent XML Content to Post,From jms

   */

  public void TFPostURLRequest(String strPostContent) {
    String strDescHost = m_url.getHost() + ":" + m_url.getPort();
    OutputStream out = null;
    try {
      BuildHttpPack(strPostContent);
      m_sock.setSendBufferSize(m_strPostContent.length());
      out = m_sock.getOutputStream();
      byte[] byOutStream = m_strPostContent.getBytes("GB2312");
      out.write(byOutStream, 0, byOutStream.length);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * This <FONT color='FF0000'><B>public</B></font> function is used to Get a HTTP response from Destination,it provide a method
   * <P>to autoclose connection when time out.</P>
   * @return String get response of http server: '200 OK' '0' 'success'
   */

  public String TFGetURLResponse() {

    String strDescHost = m_url.getHost() + ":" + m_url.getPort();
    InputStream in = null;
    try {
      //get response
      in = m_sock.getInputStream();
      byte[] ch = new byte[1000];
      int nLen = 0;
      int i = 0;
      while (true) {
        i++;
        nLen = in.read(ch);
        if (nLen == -1) {
          break;
        } else if (nLen == 0) {
          continue;

        }

        m_strGetContent += new String(ch, 0, nLen);

        continue;

      }

      //find content header

      int nHeadPos = m_strGetContent.indexOf("\r\n\r\n");
      m_strGetContent = m_strGetContent.substring(nHeadPos + 4);

      m_strGetContent.trim();

    } catch (Exception ex) {

      LogTool.fatal("devicelog","接收应答出现异常\n" + strDescHost + ":" + ex.getMessage());

      try {

        m_sock.shutdownInput();

      } catch (Exception e) {}

    } finally {

      try {

        in.close();

      } catch (Exception ex) {}

    }

    return m_strGetContent;

  }


  /**

   * This <FONT color='FF0000'><B>public</B></font> function is used to close socket. If server doesn't close socket after

   * <P>response ,this function will close it forcely.</P>

   */

  public void close() {

    try {
        if (m_sock!=null){
          m_sock.shutdownOutput();
          m_sock.shutdownInput();
        }
    } catch (IOException ex) {

    } finally {

      try {
        if (m_sock!=null)
            m_sock.close();

      } catch (Exception e) {}

    }
  }

}
