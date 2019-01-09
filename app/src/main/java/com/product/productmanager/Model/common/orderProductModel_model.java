package com.product.productmanager.Model.common;

import com.product.productmanager.Model.MyBaseModel;
import com.product.productmanager.Model.orderProductModel;

public class orderProductModel_model extends MyBaseModel{
    public orderProductModel object = new orderProductModel();

    public orderProductModel getObject() {
        return object;
    }

    public void setObject(orderProductModel object) {
        this.object = object;
    }
}
