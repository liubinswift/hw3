package org.jmask.persist.annotation;

import java.lang.annotation.*;
/**
 * <p>Title: ������</p>
 *
 * <p>Description: ʹ�������Ǳ�ʾ����Գ־û����</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
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
