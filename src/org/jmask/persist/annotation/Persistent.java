package org.jmask.persist.annotation;


import java.lang.annotation.*;
/**
 * <p>Title:Persistent��� </p>
 *
 * <p>Description: ʹ��Persistent��ǣ���ע������Ҫ�洢���ֶ�</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
 *
 * @author �¸�
 * @version 1.0
 */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Persistent {
        String value() default "";
    }
