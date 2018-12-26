package com.product.productmanager.http.bean;

public class TestEntity {
    private static int SUCCESS_CODE=0;//成功的code
    private int status;
    private String mes;
    private String object;


    public boolean isSuccess(){
        return getStatus() == SUCCESS_CODE;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
