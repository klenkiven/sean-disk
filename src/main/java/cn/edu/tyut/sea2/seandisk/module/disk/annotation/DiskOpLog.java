package cn.edu.tyut.sea2.seandisk.module.disk.annotation;

import java.lang.annotation.*;

/**
 * 标记日志操作的Log注解
 * <p>其中value代表具体的操作</p>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DiskOpLog {
    /**
     * 具体的操作
     */
    String value() default "";
}
