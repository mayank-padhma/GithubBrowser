package com.dev.githubbrowser.model;

public class Repo {

    private int id;
    private String repoOwner;
    private String repoName;
    private String repoDesc;
    private String repoUrl;

    public Repo(int id, String repoOwner, String repoName, String repoDesc, String repoUrl) {
        this.id = id;
        this.repoOwner = repoOwner;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.repoUrl = repoUrl;
    }

    public Repo(String repoOwner, String repoName, String repoDesc, String repoUrl) {
        this.repoOwner = repoOwner;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.repoUrl = repoUrl;
    }

    public Repo() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDesc() {
        return repoDesc;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

}
