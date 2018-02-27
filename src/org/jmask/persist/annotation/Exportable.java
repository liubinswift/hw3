package org.jmask.persist.annotation;

import java.lang.annotation.*;
/**
 * <p>Title: 输出标记</p>
 *
 * <p>Description: 使用输出标记标示类可以持久化输出</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author not attributable
 * @version 1.0
 */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Exportable {
        String description() default "";
        String name() default "";
        String value() default "";
}
