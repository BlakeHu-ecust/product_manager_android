package com.product.productmanager.Model;

import java.util.Map;

public class MyBaseModel {
        private static int SUCCESS_CODE=0;//成功的code
        private int status;
        private String mes;

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

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
    }
