package com.cookandroid.pocketlist;

public class Setting {
    int sort;
    int showCompl;

    public Setting(){}
    public Setting(int sort, int showCompl){
        this.sort = sort;
        this.showCompl = showCompl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public  int getShowCompl() {
        return showCompl;
    }

    public void setShowCompl(int showCompl) {
        this.showCompl = showCompl;
    }
}
