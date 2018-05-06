package com.ezerski.vladislav.uicomponents.model;

public class ProfileModel {

    private String title, subTitle, url;

    ProfileModel(String title, String subTitle, String url) {
        this.title = title;
        this.subTitle = subTitle;
        this.url = url;
    }

    public ProfileModel(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
