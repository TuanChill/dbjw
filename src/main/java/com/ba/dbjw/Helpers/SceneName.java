package com.ba.dbjw.Helpers;

/**
 * Code created by Andrius on 2020-09-28
 */
public enum SceneName {
    DASHBOARD("DASHBOARD"),
    PRODUCT("Quản lí sản phẩm"),
    ;

    private final String name;

    private SceneName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
