package com.mindbriks.sparkle.model;

import java.io.Serializable;
import java.util.ArrayList;
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
    boolean encrypted;

    private DbUser() {
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
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

    public static class Builder {
        private List<Interest> interests = new ArrayList<>();
        private String profile_image = "";
        private String height = "";
        private String id = "";
        private String name = "";
        private String email = "";
        private String gender = "";
        private long dob = 0L;
        private SmokingPreference smoke_preference = SmokingPreference.NEVER;
        private DrinkingPreference drinking_preference = DrinkingPreference.NEVER;
        private double compatibilityScore = 0.0;
        private Location location = new Location(0.0, 0.0);
        private boolean encrypted = false;

        public Builder() {
        } // public constructor

        public Builder interests(List<Interest> interests) {
            this.interests = interests;
            return this;
        }

        public Builder profile_image(String profile_image) {
            this.profile_image = profile_image;
            return this;
        }

        public Builder height(String height) {
            this.height = height;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder dob(long dob) {
            this.dob = dob;
            return this;
        }

        public Builder smoke_preference(SmokingPreference smoke_preference) {
            this.smoke_preference = smoke_preference;
            return this;
        }

        public Builder drinking_preference(DrinkingPreference drinking_preference) {
            this.drinking_preference = drinking_preference;
            return this;
        }

        public Builder compatibilityScore(double compatibilityScore) {
            this.compatibilityScore = compatibilityScore;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }
        public Builder encrypted(boolean encrypted) {
            this.encrypted = encrypted;
            return this;
        }

        public DbUser build() {
            DbUser user = new DbUser();
            user.interests = this.interests;
            user.profile_image = this.profile_image;
            user.height = this.height;
            user.id = this.id;
            user.name = this.name;
            user.email = this.email;
            user.gender = this.gender;
            user.dob = this.dob;
            user.smoke_preference = this.smoke_preference;
            user.drinking_preference = this.drinking_preference;
            user.compatibilityScore = this.compatibilityScore;
            user.location = this.location;
            user.encrypted = this.encrypted;
            return user;
        }
    }
}
