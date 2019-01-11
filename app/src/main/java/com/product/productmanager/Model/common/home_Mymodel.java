package com.product.productmanager.Model.common;

import com.product.productmanager.Model.home_model;

public class home_Mymodel extends MyBaseModel{
    private home_model object = new home_model();

    public home_model getObject() {
        return object;
    }

    public void setObject(home_model object) {
        this.object = object;
    }
}
