package com.y.router_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元注解共有四种@Retention, @Target, @Inherited, @Documented
 * Retention 保留的范围，默认值为CLASS. 可选值有三种
 * SOURCE, 只在源码中可用
 * CLASS, 在源码和字节码中可用
 * RUNTIME, 在源码,字节码,运行时均可用
 * Target @Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、
 * 类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）
 * Inherited 是否可以被继承，默认为false
 * Documented 是否会保存到 Javadoc 文档中
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Route {
    String path();
}
