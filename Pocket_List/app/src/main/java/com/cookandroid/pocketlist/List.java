package com.cookandroid.pocketlist;

public class List {
    String picture, name, info, date;
    int star;

    public List() {}
    public List(String picture, String name, String info, int star, String date) {
        this.picture = picture;
        this.name = name;
        this.info = info;
        this.star = star;
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() { return name; }

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
}