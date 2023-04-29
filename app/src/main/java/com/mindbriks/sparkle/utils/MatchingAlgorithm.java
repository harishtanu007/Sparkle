package com.mindbriks.sparkle.utils;

import static com.mindbriks.sparkle.utils.DistanceHelper.getDistance;

import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.Interest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MatchingAlgorithm {
    // define matching criteria
    static int maxDistance = 10; // in miles
    static int maxAgeDiff = 5; // in years

    public static List<DbUser> getTopMatches(DbUser currentUser, List<DbUser> allUsers) {
        String gender = "female";
        List<Interest> interests = currentUser.getInterests();

        // filter users based on matching criteria
        List<DbUser> filteredUsers = new ArrayList<>();
        for (DbUser user : allUsers) {
            // check distance between user and potential match
            double distance = getDistance(user.getLocation(), currentUser.getLocation());
            if (distance <= maxDistance) {
                // check age difference between user and potential match
                int ageDiff = Math.abs(DobHelper.calculateAge(user.getDob()) - DobHelper.calculateAge(currentUser.getDob()));
                if (ageDiff <= maxAgeDiff) {
                    // check gender of potential match
                    if (user.getGender().equals(gender)) {
                        // check interests of potential match
                        if (user.getInterests().containsAll(interests)) {
                            filteredUsers.add(user);
                        }
                    }
                }
            }
        }

// sort filtered users by compatibility score
        List<DbUser> sortedUsers = new ArrayList<>();
        for (DbUser user : filteredUsers) {
            double compatibilityScore = getCompatibilityScore(user, currentUser);
            user.setCompatibilityScore(compatibilityScore);
            sortedUsers.add(user);
        }
        Collections.sort(sortedUsers, new Comparator<DbUser>() {
            @Override
            public int compare(DbUser u1, DbUser u2) {
                return Double.compare(u2.getCompatibilityScore(), u1.getCompatibilityScore());
            }
        });

        // return top matches
        List<DbUser> topMatches = sortedUsers.subList(0, 10);
        return topMatches;
    }

    public static int getCompatibilityScore(DbUser user, DbUser currentUser) {
        int score = 0;

        // Age difference
        int ageDiff = Math.abs(DobHelper.calculateAge(user.getDob()) - DobHelper.calculateAge(currentUser.getDob()));
        if (ageDiff <= 5) {
            score += 10;
        } else if (ageDiff <= 10) {
            score += 5;
        }

        // Gender compatibility
        if (user.getGender() != currentUser.getGender()) {
            score += 10;
        }

        // Interest compatibility
        List<Interest> userInterests = user.getInterests();
        List<Interest> currentUserInterests = currentUser.getInterests();
        int commonInterests = 0;
        for (Interest interest : userInterests) {
            if (currentUserInterests.contains(interest)) {
                commonInterests++;
            }
        }
        if (commonInterests > 0) {
            score += commonInterests * 5;
        }

        return score;
    }
}
