package org.jmask.web.exception;

/**
 * <p>Title: Web应用异常类</p>
 *
 * <p>Description: 所有Web应用程序的异常都以此作为基类</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
 * @version 1.0
 */
public class WebAppException extends Exception {
    public WebAppException() {
        super();
    }

    public WebAppException(String message) {
        super(message);
    }

    public WebAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebAppException(Throwable cause) {
        super(cause);
    }
}
