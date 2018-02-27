package org.jmask.persist.annotation;


import java.lang.annotation.*;
/**
 * <p>Title:Persistent标记 </p>
 *
 * <p>Description: 使用Persistent标记，标注类中需要存储的字段</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
 * @version 1.0
 */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Persistent {
        String value() default "";
    }
