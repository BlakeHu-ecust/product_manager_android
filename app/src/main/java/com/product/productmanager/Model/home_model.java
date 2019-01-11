package com.product.productmanager.Model;

public class home_model {
    private String userId = "";
    private int unComplete = 0;
    private int complete = 0;
    private int monthComplete = 0;
    private int todayTotal = 0;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUnComplete() {
        return unComplete;
    }

    public void setUnComplete(int unComplete) {
        this.unComplete = unComplete;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getMonthComplete() {
        return monthComplete;
    }

    public void setMonthComplete(int monthComplete) {
        this.monthComplete = monthComplete;
    }

    public int getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(int todayTotal) {
        this.todayTotal = todayTotal;
    }
}
