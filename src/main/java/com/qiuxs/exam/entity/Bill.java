package com.qiuxs.exam.entity;

public class Bill {

    protected String name;

    protected double money;

    protected int payType;

    protected int drugType;

    protected int docType;

    protected String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPayType() {
        return payType;
    }


    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getDrugType() {
        return drugType;
    }

    public void setDrugType(int drugType) {
        this.drugType = drugType;
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
