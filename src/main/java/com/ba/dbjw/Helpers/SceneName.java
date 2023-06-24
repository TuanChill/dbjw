package com.ba.dbjw.Helpers;

/**
 * Code created by Andrius on 2020-09-28
 */
public enum SceneName {
    DASHBOARD("DASHBOARD"),
    PRODUCT("Quản lý sản phẩm"),
    CUSTOMER("Quản lý khách hàng"),
    EMPLOYEE("Quản lý nhân viên"),
    INVOICE("Quản lý hoá đơn"),
    ;

    private final String name;

    private SceneName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
