
package com.viewscenes.applet.freqscan;

import java.util.TimerTask;





public class quality_freqscanrefreshtask extends TimerTask {

  public com.viewscenes.applet.freqscan.quality_freqscan m_parent;

  public quality_freqscanrefreshtask() {

  }



  public quality_freqscanrefreshtask(com.viewscenes.applet.freqscan.quality_freqscan freqscan) {

    m_parent = freqscan;

  }



  public void run() {

    /**@todo Implement this java.lang.Runnable abstract method*/

    m_parent.refresh();

//    System.out.println("refresh task");

  }

  public static void main(String[] args) {

    quality_freqscanrefreshtask quality_freqscanrefreshtask1 = new quality_freqscanrefreshtask();

  }

}
