package org.jmask.web.ui;

import java.util.*;

import org.jmask.web.ui.*;
import org.jmask.web.exception.*;

/**
 * <p>Title: Web应用程序</p>
 *
 * <p>Description: Web应用程序对象，作为Web应用的起始点，相当于Delphi中的Application</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
 * @version 1.0
 */
abstract public class WebApplication {

    //应用程序包含的主界面
    public WebPage mainPage;

    public WebApplication() {
    }

    abstract public String startApp();


}
