package com.mindbriks.sparkle.interfaces;

import android.net.Uri;

import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.model.SaveDetailsModel;
import com.mindbriks.sparkle.model.User;

import java.util.List;

public interface IDataSource {
    List<Profile> getUsers();

    void createUser(User user, IDataSourceCallback callback);

    void uploadProfileImage(Uri imageUri, IOnUploadProfileImageListener listener);

    void saveDetails(SaveDetailsModel saveDetailsModel, IDataSourceCallback callback);

    void login(String email, String password, IDataSourceCallback callback);

    void onLoginVerification(ILoginVerificationListener listener);

    void logoutUser(IDataSourceCallback callback);

    void getCurrentUserDetails(IUserDetailsCallback callback);

    void getAllUserDetails(String userId, IAllUserDetailsCallback callback);

    void getUserDetails(String userId, IUserDetailsCallback callback);

    String getCurrentUserId();

    void deleteUser(String password, IDataSourceCallback callback);
}
