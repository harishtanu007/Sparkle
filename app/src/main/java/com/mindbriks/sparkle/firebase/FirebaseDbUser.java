package com.mindbriks.sparkle.firebase;

import com.mindbriks.sparkle.model.Interest;

import java.util.List;

public class FirebaseDbUser {
    public String id;
    public String name;
    public String email;
    public String gender;
    public long dob;
    public List<Interest> interests;

    public FirebaseDbUser(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public FirebaseDbUser(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public FirebaseDbUser() {
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
