package com.cookandroid.pocketlist;

public class List {
    String picture, name, info, date;
    int pictureid, star, complete, num;

    public List() {}
    public List(int pictureid, String picture, String name, String info, int star, String date, int complete, int num) {
        this.pictureid = pictureid;
        this.picture = picture;
        this.name = name;
        this.info = info;
        this.star = star;
        this.date = date;
        this.complete = complete;
        this.num = num;
    }

    public int getPictureid() {
        return pictureid;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getStar() {
        return star;
    }

    public String getDate() {
        return date;
    }

    public int getComplete() {
        return complete;
    }

    public int getNum() {
        return num;
    }
}