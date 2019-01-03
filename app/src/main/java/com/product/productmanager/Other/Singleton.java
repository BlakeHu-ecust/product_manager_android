package com.product.productmanager.Other;

import android.content.Context;

import com.product.productmanager.Model.UserModel;

public class Singleton {
    public static Singleton instance = new Singleton();

    private UserModel userModel = new UserModel();
    private String token = "";
    private String enterprise;

    private Context context;

    public static Singleton getInstance() {
        return instance;
    }

    public static void setInstance(Singleton instance) {
        Singleton.instance = instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        this.setToken(userModel.getToken());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
