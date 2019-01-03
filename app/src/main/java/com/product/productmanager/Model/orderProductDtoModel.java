package com.product.productmanager.Model;

import java.util.ArrayList;

public class orderProductDtoModel {
    private String id = "";
    private String createTime = "";
    private String updateTime = "";
    private String remark = "";
    private String orderId = "";
    private String styleId = "";
    private String versionType = "";
    private String technologyId = "";
    private String technology = "";
    private String volumeBodyType = "";
    private String codeNumber = "";
    private String productStyle = "";
    private String price = "";
    private String urgentPrice = "";
    private String procesPrice = "";
    //缝标集合信息
    private ArrayList<orderTrademarkListModel> orderTrademarkListModels = new ArrayList<>();
    //刺绣集合信息
    private ArrayList<orderEmbroideryListModel>orderEmbroideryList = new ArrayList<>();
    //工艺细节集合
    private ArrayList<orderChangeDressingListModel>orderChangeDressingList = new ArrayList<>();

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

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getVolumeBodyType() {
        return volumeBodyType;
    }

    public void setVolumeBodyType(String volumeBodyType) {
        this.volumeBodyType = volumeBodyType;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrgentPrice() {
        return urgentPrice;
    }

    public void setUrgentPrice(String urgentPrice) {
        this.urgentPrice = urgentPrice;
    }

    public String getProcesPrice() {
        return procesPrice;
    }

    public void setProcesPrice(String procesPrice) {
        this.procesPrice = procesPrice;
    }

    public ArrayList<orderTrademarkListModel> getOrderTrademarkListModels() {
        return orderTrademarkListModels;
    }

    public void setOrderTrademarkListModels(ArrayList<orderTrademarkListModel> orderTrademarkListModels) {
        this.orderTrademarkListModels = orderTrademarkListModels;
    }

    public ArrayList<orderEmbroideryListModel> getOrderEmbroideryList() {
        return orderEmbroideryList;
    }

    public void setOrderEmbroideryList(ArrayList<orderEmbroideryListModel> orderEmbroideryList) {
        this.orderEmbroideryList = orderEmbroideryList;
    }

    public ArrayList<orderChangeDressingListModel> getOrderChangeDressingList() {
        return orderChangeDressingList;
    }

    public void setOrderChangeDressingList(ArrayList<orderChangeDressingListModel> orderChangeDressingList) {
        this.orderChangeDressingList = orderChangeDressingList;
    }

    public class orderTrademarkListModel{
        private String id = "";
        private String createTime = "";
        private String updateTime = "";
        private String remark = "";
        private String productId = "";
        private String style = "";
        private String specificsTypeName = "";
        private String trademarkType = "";
        private String value = "";
        private String smallImage = "";
        private String bigImage = "";
        private int price = 0;
        private int sort = 0;
        private int valueSource = 0;
        private String productParts = "";

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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getSpecificsTypeName() {
            return specificsTypeName;
        }

        public void setSpecificsTypeName(String specificsTypeName) {
            this.specificsTypeName = specificsTypeName;
        }

        public String getTrademarkType() {
            return trademarkType;
        }

        public void setTrademarkType(String trademarkType) {
            this.trademarkType = trademarkType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getBigImage() {
            return bigImage;
        }

        public void setBigImage(String bigImage) {
            this.bigImage = bigImage;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getValueSource() {
            return valueSource;
        }

        public void setValueSource(int valueSource) {
            this.valueSource = valueSource;
        }

        public String getProductParts() {
            return productParts;
        }

        public void setProductParts(String productParts) {
            this.productParts = productParts;
        }
    }

    public class orderEmbroideryListModel{
        private String id = "";
        private String createTime = "";
        private String updateTime = "";
        private String remark = "";
        private String orderId = "";
        private String groupName = "";
        private String specificsTypeName = "";
        private String productId = "";
        private String style = "";
        private String embroideryParts = "";
        private String value = "";
        private String smallImage = "";
        private String bigImage = "";
        private int price = 0;
        private int sort = 0;
        private int valueSource = 0;
        private String productParts = "";

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

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getSpecificsTypeName() {
            return specificsTypeName;
        }

        public void setSpecificsTypeName(String specificsTypeName) {
            this.specificsTypeName = specificsTypeName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getEmbroideryParts() {
            return embroideryParts;
        }

        public void setEmbroideryParts(String embroideryParts) {
            this.embroideryParts = embroideryParts;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getBigImage() {
            return bigImage;
        }

        public void setBigImage(String bigImage) {
            this.bigImage = bigImage;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getValueSource() {
            return valueSource;
        }

        public void setValueSource(int valueSource) {
            this.valueSource = valueSource;
        }

        public String getProductParts() {
            return productParts;
        }

        public void setProductParts(String productParts) {
            this.productParts = productParts;
        }
    }

    public class orderChangeDressingListModel{
        private String id = "";
        private String createTime = "";
        private String updateTime = "";
        private String remark = "";
        private String productId = "";
        private String specificsType = "";
        private String specificsTypeName = "";
        private String partLabel = "";
        private String styleName = "";
        private String productParts = "";
        private String value = "";
        private int position = 0;
        private String smallimage = "";
        private String bigimage = "";
        private int price = 0;
        private int sort = 0;
        private int valueSource = 0;
        private String operationTutorials = "";
        private int type = 0;

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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getSpecificsType() {
            return specificsType;
        }

        public void setSpecificsType(String specificsType) {
            this.specificsType = specificsType;
        }

        public String getSpecificsTypeName() {
            return specificsTypeName;
        }

        public void setSpecificsTypeName(String specificsTypeName) {
            this.specificsTypeName = specificsTypeName;
        }

        public String getPartLabel() {
            return partLabel;
        }

        public void setPartLabel(String partLabel) {
            this.partLabel = partLabel;
        }

        public String getStyleName() {
            return styleName;
        }

        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }

        public String getProductParts() {
            return productParts;
        }

        public void setProductParts(String productParts) {
            this.productParts = productParts;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getSmallimage() {
            return smallimage;
        }

        public void setSmallimage(String smallimage) {
            this.smallimage = smallimage;
        }

        public String getBigimage() {
            return bigimage;
        }

        public void setBigimage(String bigimage) {
            this.bigimage = bigimage;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getValueSource() {
            return valueSource;
        }

        public void setValueSource(int valueSource) {
            this.valueSource = valueSource;
        }

        public String getOperationTutorials() {
            return operationTutorials;
        }

        public void setOperationTutorials(String operationTutorials) {
            this.operationTutorials = operationTutorials;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
