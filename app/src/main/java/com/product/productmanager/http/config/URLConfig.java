package com.product.productmanager.http.config;

public interface URLConfig {
    String login_url = "login/loginApp";
    String findCount_url = "userApp/fingCountByUserId";
    String findNewWork_url = "userApp/findNewWorkOrderById";
    String findWorkOrderByList_url = "userApp/findWorkOrderByList";
    String findAllComplete_url = "userApp/findAllComplete";
    String findWorkOrderScanById_url = "userApp/findWorkOrderScanById";
    String workOrderDetail_url = "userApp/workOrderDetail";
}
