package com.product.productmanager.Other;

import com.product.productmanager.Model.UserModel;

public class Singleton {
    public static Singleton instance = new Singleton();

    private UserModel userModel;
    private String token = "";
    private String enterprise;

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
