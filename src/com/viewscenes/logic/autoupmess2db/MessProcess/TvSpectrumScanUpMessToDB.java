/*
 * Created on 2007-2-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.viewscenes.logic.autoupmess2db.MessProcess;

import java.sql.*;
import java.util.*;

import org.jdom.*;

import com.viewscenes.dao.*;
import com.viewscenes.dao.database.*;
import com.viewscenes.logic.autoupmess2db.Exception.*;
import com.viewscenes.logic.autoupmess2db.common.*;
import com.viewscenes.pub.*;
import com.viewscenes.util.*;


public class TvSpectrumScanUpMessToDB  implements IUpMsgProcessor {

    Element _root = null;

    public void processUpMsg(Element root) throws SQLException,
            UpMess2DBException,
            GDSetException, DbException, UtilException, NoRecordException {
//
//        _root = root;
//        spectrumScanToDB(root);
    }


//    private void spectrumScanToDB(Element root) throws UpMess2DBException,
//            GDSetException, DbException, UtilException, NoRecordException {
//
//        String taskID, scanTime = null;
//
//        String headCode = root.getAttributeValue("SrcCode");
//
//        String head_id = getHeadInfoByCode(headCode).getString(0, "head_id");
//
//        Element e = root.getChild("spectrumscanreport");
//
//        taskID = e.getAttributeValue("taskid");
//
//        Collection tasks = e.getChildren();
//
//        for (Iterator tasksit = tasks.iterator(); tasksit.hasNext(); ) {
//
////  	String taskID = spectrumRes.getAttributeValue("TaskID");
//
//            Element SpectrumElement = (Element) tasksit.next();
//
//            scanTime = SpectrumElement.getAttributeValue("scantime");
//
//            if (SpectrumElement.getName().equalsIgnoreCase("spectrumscan")) {
//                Collection spectrumScan = SpectrumElement.getChildren();
//                for (Iterator resultit = spectrumScan.iterator();
//                                         resultit.hasNext(); ) {
//                    Element resultElement = (Element) resultit.next();
//                    String freq = resultElement.getAttributeValue("freq");
//                    String level = resultElement.getAttributeValue("level");
//                    setGDSet(taskID, scanTime, freq, level);
//                    ScanFreqInfo sfi = new ScanFreqInfo(freq, level, scanTime,
//                            "1", head_id);
//                    NewFreqWorkThread.taskQueue.add(sfi);
//                }
//
//            }
//        }
//    }
//
//    /**
//     * 设置GDSet
//
//     * @param alarm
//
//     * @param headinfo
//
//     * @param chInfo
//
//     * @param gd
//
//     */
//
//    private void setGDSet(String taskID, String scanTime, String freq,
//                          String level) throws
//
//            GDSetException, UtilException, DbException {
//
//        try {
//
//            String sql =
//                    "insert into task_tv_spectrum_data_tab (Task_id,Scan_time,Freq,Levels) "
//                    + " values(" + taskID + ",to_date('" + scanTime +
//                    "','yyyy-MM-dd HH24:MI:SS'),"
//                    + freq + "," + level + ")";
//            DbComponent.exeUpdate(sql);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//
//            LogTool.warning("修改频谱任务数据库失败！" + e.getMessage());
//
//        }
//    }
//
//    public static GDSet getHeadInfoByCode(String headCode) {
//        String sql = "select * from info_headend_tab where code = '" + headCode +
//                     "'";
//        GDSet headinfo = null;
//        try {
//            headinfo = DbComponent.Query(sql);
//        } catch (Exception ex) {
//
//        }
//        return headinfo;
//    }
}
