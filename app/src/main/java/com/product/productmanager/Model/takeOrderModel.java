package com.product.productmanager.Model;

import java.util.ArrayList;

public class takeOrderModel {
    private String id;
    private ArrayList<String> idList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<String> idList) {
        this.idList = idList;
    }

    public static class paramModel{
        private takeOrderModel param;

        public takeOrderModel getParam() {
            return param;
        }

        public void setParam(takeOrderModel param) {
            this.param = param;
        }
    }
}
