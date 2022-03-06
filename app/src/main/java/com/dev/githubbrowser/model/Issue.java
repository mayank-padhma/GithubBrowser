package com.dev.githubbrowser.model;


public class Issue {

    private String title;
    private String cname;
    private String imgUrl;

    public Issue(String title, String cname, String imgUrl) {
        this.title = title;
        this.cname = cname;
        this.imgUrl = imgUrl;
    }
    public Issue() {    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
