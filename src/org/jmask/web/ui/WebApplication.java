package org.jmask.web.ui;

import java.util.*;

import org.jmask.web.ui.*;
import org.jmask.web.exception.*;

/**
 * <p>Title: WebӦ�ó���</p>
 *
 * <p>Description: WebӦ�ó��������ΪWebӦ�õ���ʼ�㣬�൱��Delphi�е�Application</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
 *
 * @author �¸�
 * @version 1.0
 */
abstract public class WebApplication {

    //Ӧ�ó��������������
    public WebPage mainPage;

    public WebApplication() {
    }

    abstract public String startApp();


}
