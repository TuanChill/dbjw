package com.ba.dbjw.Repo.UserAuth;


public interface UserAuthRepo<T> {
    void saveUser(T data);
    T findByUserName(String userName);
}
