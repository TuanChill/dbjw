package com.ba.dbjw.Helpers;


import com.ba.dbjw.Entity.UserAuth.UserAuth;

public class CurrentUser {

    private static UserAuth user;

    private CurrentUser() {
    }

    public static UserAuth getCurrentUser() {
        return user;
    }

    public static void setCurrentUser(UserAuth currentUser) {
        user = currentUser;
    }
}
