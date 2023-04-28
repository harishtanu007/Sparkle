package com.mindbriks.sparkle.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class DbUser implements Serializable {
    List<Interest> interests;
    String profile_image;
    String height;
    String id;
    String name;
    String email;
    String gender;
    long dob;
    SmokingPreference smoke_preference;
    DrinkingPreference drinking_preference;
    double compatibilityScore;
    Location location;

    public DbUser(String id, String name, String email, String gender, long dob, List<Interest> interests, String profileImage, String height, SmokingPreference smoke_preference, DrinkingPreference drinking_preference, Location location) {
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
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getCompatibilityScore() {
        return compatibilityScore;
    }

    public void setCompatibilityScore(double compatibilityScore) {
        this.compatibilityScore = compatibilityScore;
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

    public SmokingPreference getSmoke_preference() {
        return smoke_preference;
    }

    public void setSmoke_preference(SmokingPreference smoke_preference) {
        this.smoke_preference = smoke_preference;
    }

    public DrinkingPreference getDrinking_preference() {
        return drinking_preference;
    }

    public void setDrinking_preference(DrinkingPreference drinking_preference) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DbUser)) {
            return false;
        }
        DbUser other = (DbUser) obj;
        return Objects.equals(interests, other.interests) && Objects.equals(profile_image, other.profile_image) && Objects.equals(height, other.height) && Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(email, other.email) && Objects.equals(gender, other.gender) && dob == other.dob && smoke_preference == other.smoke_preference && drinking_preference == other.drinking_preference;
    }
}
