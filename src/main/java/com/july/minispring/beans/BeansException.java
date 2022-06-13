package com.july.minispring.beans;

/**
 *定义创建bean所发生的异常
 */

public class BeansException extends RuntimeException{

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
