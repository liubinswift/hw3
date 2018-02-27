package com.viewscenes.util.publish;



public class PublishManager {

  private static PublishThread alertCountThread = null;

  public PublishManager() {

  }

  public static void startPubAlertCount(String publishPath, int interval){

    if (alertCountThread==null){

      alertCountThread = new PublishThread();

      alertCountThread.setInterval(interval);

      alertCountThread.setPublishPath(publishPath);

      alertCountThread.start();

    }

    else{

      alertCountThread.setInterval(interval);

      alertCountThread.setPublishPath(publishPath);

    }

  }

}
