package com.product.productmanager.Model;

public class gongdanModel {
    private String name = "";
    private String time = "";
    private String status = "";

    public gongdanModel(String name,String time,String status){
        this.name = name;
        this.time = time;
        this.status = status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
