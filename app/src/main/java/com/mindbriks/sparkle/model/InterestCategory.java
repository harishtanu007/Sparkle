package com.mindbriks.sparkle.model;

import java.util.List;

public class InterestCategory {
    private String categoryName;
    private List<String> interests;

    public InterestCategory(String categoryName, List<String> interests) {
        this.categoryName = categoryName;
        this.interests = interests;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<String> getInterests() {
        return interests;
    }
}
