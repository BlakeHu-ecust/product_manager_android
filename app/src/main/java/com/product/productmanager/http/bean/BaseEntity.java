package com.product.productmanager.http.bean;

/**
 * @author yemao
 * @date 2017/4/9
 * @description 解析实体基类!
 */

public class BaseEntity<T> {
    private static int SUCCESS_CODE=0;//成功的code
    private int status;
    private String mes;
    private T object;


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

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
