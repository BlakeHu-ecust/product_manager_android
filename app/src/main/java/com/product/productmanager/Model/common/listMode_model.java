package com.product.productmanager.Model.common;

import com.product.productmanager.Model.MyBaseModel;
import com.product.productmanager.Model.gd_model;
import com.product.productmanager.Model.listModel;

public class listMode_model extends MyBaseModel{
    private listModel<gd_model> object = new listModel<>();

    public listModel<gd_model> getObject() {
        return object;
    }

    public void setObject(listModel<gd_model> object) {
        this.object = object;
    }
}
