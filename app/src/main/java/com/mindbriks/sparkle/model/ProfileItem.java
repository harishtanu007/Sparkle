package com.mindbriks.sparkle.model;

public class ProfileItem {
    private String title;
    private String value;

    public ProfileItem(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
