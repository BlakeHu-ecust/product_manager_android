package com.product.productmanager.Model;

import java.util.ArrayList;

public class orderProductModel {
    private String id = "";
    private String workOrderCode = "";
    private String orderId = "";
    private String customerName = "";
    private String name = "";
    private String productStyle = "";
    private String storeName = "";
    private int status;
    private String tailoringTime = "";
    private String code = "";
    private String receiveTime = "";
    private String userId = "";
    private int urgent = 0;
    private String styleId = "";
    private String technology = "";
    private int fittingRequire = 0;
    private int receivingType;
    private String deliveryTime = "";
    private String remark = "";
    private String versionType = "";
    private String processName = "";
    private String startTime = "";
    private String endTime = "";
    private int processNum;
    private int completeProcessNum;
    private ArrayList<orderfabricListModel> orderfabricList = new ArrayList<>();
    private orderProductDtoModel orderProductDto = new orderProductDtoModel();
    private boolean choosed = false;

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTailoringTime() {
        return tailoringTime;
    }

    public void setTailoringTime(String tailoringTime) {
        this.tailoringTime = tailoringTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public int getFittingRequire() {
        return fittingRequire;
    }

    public void setFittingRequire(int fittingRequire) {
        this.fittingRequire = fittingRequire;
    }

    public int getReceivingType() {
        return receivingType;
    }

    public void setReceivingType(int receivingType) {
        this.receivingType = receivingType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProcessNum() {
        return processNum;
    }

    public void setProcessNum(int processNum) {
        this.processNum = processNum;
    }

    public int getCompleteProcessNum() {
        return completeProcessNum;
    }

    public void setCompleteProcessNum(int completeProcessNum) {
        this.completeProcessNum = completeProcessNum;
    }

    public ArrayList<orderfabricListModel> getOrderfabricList() {
        return orderfabricList;
    }

    public void setOrderfabricList(ArrayList<orderfabricListModel> orderfabricList) {
        this.orderfabricList = orderfabricList;
    }

    public orderProductDtoModel getOrderProductDto() {
        return orderProductDto;
    }

    public void setOrderProductDto(orderProductDtoModel orderProductDto) {
        this.orderProductDto = orderProductDto;
    }

    public class orderfabricListModel{
        private String id = "";
        private String createTime = "";
        private String updateTime = "";
        private String remark = "";
        private String orderId = "";
        private String name = "";
        private String value = "";
        private int valueSource;
        private int sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getValueSource() {
            return valueSource;
        }

        public void setValueSource(int valueSource) {
            this.valueSource = valueSource;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
