package com.viewscenes.util;

public class HttpGetThread extends Thread {
  private String url;

  public HttpGetThread() {
  }

  public HttpGetThread(String url) {
    this.url = url;
    start();
  }

  public void run() {
    try {
      HttpTool.HttpGet(url);
      LogTool.debug("Http Get Thread succeed: "+ url );
    } catch (Exception ex) {
      LogTool.warning("utillog", ex);
    }
  }

}
