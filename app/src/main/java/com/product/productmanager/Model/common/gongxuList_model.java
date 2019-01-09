package com.product.productmanager.Model.common;

import com.product.productmanager.Model.MyBaseModel;
import com.product.productmanager.Model.gongxuModel;

import java.util.ArrayList;

public class gongxuList_model extends MyBaseModel{
    private ArrayList<gongxuModel> object = new ArrayList<>();

    public ArrayList<gongxuModel> getObject() {
        return object;
    }

    public void setObject(ArrayList<gongxuModel> object) {
        this.object = object;
    }
}
