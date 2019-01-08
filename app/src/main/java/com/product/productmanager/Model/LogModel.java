package com.product.productmanager.Model;

public class LogModel extends MyBaseModel {
    public UserModel object = new UserModel();

    public UserModel getUserModel() {
        return object;
    }

    public void setUserModel(UserModel userModel) {
        this.object = userModel;
    }
}
