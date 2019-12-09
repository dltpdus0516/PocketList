package com.cookandroid.pocketlist;

public class List {
    String picture, name, info, date;
    int star, complete, num;

    public List() {}
    public List(String picture, String name, String info, int star, String date, int complete, int num) {
        this.picture = picture;
        this.name = name;
        this.info = info;
        this.star = star;
        this.date = date;
        this.complete = complete;
        this.num = num;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}