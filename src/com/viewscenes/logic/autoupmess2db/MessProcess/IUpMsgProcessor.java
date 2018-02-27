package com.viewscenes.logic.autoupmess2db.MessProcess;



import java.sql.SQLException;

import org.jdom.*;
import com.viewscenes.dao.database.*;
import com.viewscenes.logic.autoupmess2db.Exception.*;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.UtilException;



public interface IUpMsgProcessor {

  public void processUpMsg(Element root) throws SQLException, UpMess2DBException,

      GDSetException, DbException, UtilException, NoRecordException;
}
