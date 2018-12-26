package com.product.productmanager.Model;

public class UserModel {
    private String id = "";
    private String createTime = "";
    private String updateTime = "";
    private String remark = "";
    private String userName = "";
    private String realName = "";
    private String password = "";
    private String enterpriseId = "";
    private String entryTime = "";
    private String address = "";
    private int basePay = 0;
    private String idCard = "";
    private String station = "";
    private int sex = 0;
    private String sexS = "";
    private String image = "";
    private String phone = "";
    private int status = 0;
    private String manageDepartmentId = "";
    private int type = 0;
    private String departmentId = "";
    private String token = "";
    private db produceDb = new db();

    public db getProduceDb() {
        return produceDb;
    }

    public void setProduceDb(db produceDb) {
        this.produceDb = produceDb;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBasePay() {
        return basePay;
    }

    public void setBasePay(int basePay) {
        this.basePay = basePay;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSexS() {
        return sexS;
    }

    public void setSexS(String sexS) {
        this.sexS = sexS;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getManageDepartmentId() {
        return manageDepartmentId;
    }

    public void setManageDepartmentId(String manageDepartmentId) {
        this.manageDepartmentId = manageDepartmentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public class db{
        private  String dbName = "";
        private  String dbIp = "";

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getDbIp() {
            return dbIp;
        }

        public void setDbIp(String dbIp) {
            this.dbIp = dbIp;
        }
    }
}
