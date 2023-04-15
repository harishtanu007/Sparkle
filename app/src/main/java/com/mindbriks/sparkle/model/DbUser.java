package com.mindbriks.sparkle.model;

import java.util.List;

public class DbUser {
    List<Interest> interests;
    String profile_image;
    String height;
    String id;
    String name;
    String email;
    String gender;
    long dob;
    String smoke_preference;
    String drinking_preference;

    public DbUser(String id, String name, String email, String gender, long dob, List<Interest> interests, String profileImage, String height, String smoke_preference, String drinking_preference) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.interests = interests;
        this.profile_image = profileImage;
        this.height = height;
        this.smoke_preference = smoke_preference;
        this.drinking_preference = drinking_preference;
    }

    public DbUser(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public DbUser(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public DbUser() {
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSmoke_preference() {
        return smoke_preference;
    }

    public void setSmoke_preference(String smoke_preference) {
        this.smoke_preference = smoke_preference;
    }

    public String getDrinking_preference() {
        return drinking_preference;
    }

    public void setDrinking_preference(String drinking_preference) {
        this.drinking_preference = drinking_preference;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
