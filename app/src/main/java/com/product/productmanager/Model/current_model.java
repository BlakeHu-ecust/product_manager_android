package com.product.productmanager.Model;

import com.product.productmanager.Model.common.MyBaseModel;

public class current_model extends MyBaseModel {
    public home_current_model object = new home_current_model();

    public home_current_model getObject() {
        return object;
    }

    public void setObject(home_current_model object) {
        this.object = object;
    }
}
