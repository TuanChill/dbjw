package com.ba.dbjw.Service.UserAuth;

public interface UserService<T> {
    T login(String userName, String password);
    Boolean register(T data);
}
