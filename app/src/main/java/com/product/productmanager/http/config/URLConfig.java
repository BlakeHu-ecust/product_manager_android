package com.product.productmanager.http.config;

public interface URLConfig {
    String login_url = "login/loginApp";
    String findCount_url = "userApp/fingCountByUserId";
    String findNewWork_url = "userApp/findNewWorkOrderById";
    String findWorkOrderByList_url = "userApp/findWorkOrderByList";
    String findAllComplete_url = "userApp/findAllComplete";
    String findWorkOrderScanById_url = "userApp/findWorkOrderScanById";
    String workOrderDetail_url = "userApp/workOrderDetail";
    String takeOrder_url = "userApp/takeOrder";
    String changePwd_url = "user/modifyPasswordByPeople";
    String workOrderProcess_url = "userApp/workOrderProcess";
    String beginWork_url = "userApp/beginWork";
    String endWork_url = "userApp/endWork";
}
