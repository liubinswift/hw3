package com.viewscenes.logic.autoupmess2db.common;



import com.viewscenes.dao.database.*;

import com.viewscenes.dao.innerdao.*;

import com.viewscenes.pub.*;

import com.viewscenes.dao.*;

import com.viewscenes.util.LogTool;

import java.util.TimerTask;



/**

 * 负责检测频道是否更新，如果更新，则需要fresh 频道的缓存

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */

public class LoadCacheThread

    extends TimerTask  {



  public void run() {

    try {

//      DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
      System.gc() ;
      //更新电视频道的cache

      GDSet gd = DbComponent.Query("select ANALOG_CH from MON_CHANNEL_CHANGE_TAB");

      if(gd.getString(0,"ANALOG_CH").equals("1"))

        Common.loadAnalogInfo();



      //更新广播频道的cache

      gd = DbComponent.Query("select RADIO_CH from MON_CHANNEL_CHANGE_TAB");

      if(gd.getString(0,"RADIO_CH").equals("1"))

        Common.loadRadioInfo();

    }

    catch (Exception ex) {

      LogTool.debug("LoadCacheThread run" + ex);

    }



  }

}
