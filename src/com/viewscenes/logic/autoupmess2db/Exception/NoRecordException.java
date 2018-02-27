package com.viewscenes.logic.autoupmess2db.Exception;

import com.viewscenes.pub.exception.AppException;



/**

 * <p>Title: </p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: </p>

 * @author not attributable

 * @version 1.0

 */



public class NoRecordException extends AppException {

  public NoRecordException() {

    super();

  }

  public NoRecordException(String string) {

    super(string);

  }

  public NoRecordException(String string, Throwable throwable) {

    super(string,throwable);

  }



}

