package com.product.productmanager.Model;

public class home_model {
    private String userId = "";
    private String unComplete = "";
    private String complete = "";
    private String monthComplete = "";
    private String todayTotal = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnComplete() {
        return unComplete;
    }

    public void setUnComplete(String unComplete) {
        this.unComplete = unComplete;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getMonthComplete() {
        return monthComplete;
    }

    public void setMonthComplete(String monthComplete) {
        this.monthComplete = monthComplete;
    }

    public String getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(String todayTotal) {
        this.todayTotal = todayTotal;
    }
}
