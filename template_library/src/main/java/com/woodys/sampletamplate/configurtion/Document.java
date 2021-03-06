package com.woodys.sampletamplate.configurtion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Created by woodys
 * @date 2019-05-20 09:44
 * @email yuetao.315@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Document {
    /**
     * 当前文档的url地址
     */
    String value();
}
