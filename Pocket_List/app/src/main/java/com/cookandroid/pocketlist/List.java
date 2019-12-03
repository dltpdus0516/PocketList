package com.cookandroid.pocketlist;

public class List {
    String name, info, date;
    int star;

    public List(String name, String info, int star, String date) {
        this.name = name;
        this.info = info;
        this.star = star;
        this.date = date;
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
}