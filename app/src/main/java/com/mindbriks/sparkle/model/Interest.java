package com.mindbriks.sparkle.model;

import java.io.Serializable;

public class Interest implements Serializable {
    String name;
    private boolean isSelected = false;

    public Interest(String name) {
        this.name = name;
    }

    public Interest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
