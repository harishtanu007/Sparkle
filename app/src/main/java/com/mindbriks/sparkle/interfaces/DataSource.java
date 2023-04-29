package com.mindbriks.sparkle.interfaces;

import android.net.Uri;

import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.model.SaveDetailsModel;
import com.mindbriks.sparkle.model.User;

import java.util.List;

public interface DataSource {
    List<Profile> getUsers();

    void createUser(User user, DataSourceCallback callback);

    boolean isUserDetailsExist();

    void uploadProfileImage(Uri imageUri, OnUploadProfileImageListener listener);

    void saveDetails(SaveDetailsModel saveDetailsModel, DataSourceCallback callback);

    void login(String email, String password, DataSourceCallback callback);

    void onLoginVerification(LoginVerificationListener listener);

    void logoutUser(DataSourceCallback callback);

    void getCurrentUserDetails(UserDetailsCallback callback);

    void getAllUserDetails(String userId, AllUserDetailsCallback callback);

    void getUserDetails(String userId, UserDetailsCallback callback);

    String getCurrentUserId();
}
