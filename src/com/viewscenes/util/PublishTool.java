package com.viewscenes.util;

import com.viewscenes.util.publish.*;

public class PublishTool {

  private static PublishManager manager = null;
  public PublishTool() {
  }

  public synchronized static void startPubAlertCount(String publishPath, int interval) {
    if (manager == null) {
      manager = new PublishManager();
    }
    PublishManager.startPubAlertCount(publishPath, interval);
  }
}
