package com.mindbriks.sparkle.model;

public class Interest {
    public Interest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    String name;

    private boolean isSelected = false;


}
