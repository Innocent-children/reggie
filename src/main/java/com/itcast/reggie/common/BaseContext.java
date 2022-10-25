package com.itcast.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户获取和保存当前登录用户ID
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
