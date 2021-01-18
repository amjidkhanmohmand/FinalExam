package com.example.finalexam.Models;

import java.util.ArrayList;

public class BikeModel {
private ArrayList<String> id,cityID,companyID,bikeTypeID,model,regcityID,
        bikeNumber,price,conditions,mobile,date,imgLink,des;


    public BikeModel() {
    }

    public BikeModel(ArrayList<String> id, ArrayList<String> cityID,
                     ArrayList<String> companyID, ArrayList<String> bikeTypeID,
                     ArrayList<String> model, ArrayList<String> regcityID,
                     ArrayList<String> bikeNumber, ArrayList<String> price,
                     ArrayList<String> conditions, ArrayList<String> mobile, ArrayList<String> date,
                     ArrayList<String> imgLink,ArrayList<String> des) {
        this.id = id;
        this.cityID = cityID;
        this.companyID = companyID;
        this.bikeTypeID = bikeTypeID;
        this.model = model;
        this.regcityID = regcityID;
        this.bikeNumber = bikeNumber;
        this.price = price;
        this.conditions = conditions;
        this.mobile = mobile;
        this.date = date;
        this.imgLink = imgLink;
        this.des = des;
    }

    public ArrayList<String> getDes() {
        return des;
    }

    public void setDes(ArrayList<String> des) {
        this.des = des;
    }

    public ArrayList<String> getId() {
        return id;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }

    public ArrayList<String> getCityID() {
        return cityID;
    }

    public void setCityID(ArrayList<String> cityID) {
        this.cityID = cityID;
    }

    public ArrayList<String> getCompanyID() {
        return companyID;
    }

    public void setCompanyID(ArrayList<String> companyID) {
        this.companyID = companyID;
    }

    public ArrayList<String> getBikeTypeID() {
        return bikeTypeID;
    }

    public void setBikeTypeID(ArrayList<String> bikeTypeID) {
        this.bikeTypeID = bikeTypeID;
    }

    public ArrayList<String> getModel() {
        return model;
    }

    public void setModel(ArrayList<String> model) {
        this.model = model;
    }

    public ArrayList<String> getRegcityID() {
        return regcityID;
    }

    public void setRegcityID(ArrayList<String> regcityID) {
        this.regcityID = regcityID;
    }

    public ArrayList<String> getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(ArrayList<String> bikeNumber) {
        this.bikeNumber = bikeNumber;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<String> conditions) {
        this.conditions = conditions;
    }

    public ArrayList<String> getMobile() {
        return mobile;
    }

    public void setMobile(ArrayList<String> mobile) {
        this.mobile = mobile;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public ArrayList<String> getImgLink() {
        return imgLink;
    }

    public void setImgLink(ArrayList<String> imgLink) {
        this.imgLink = imgLink;
    }
}
