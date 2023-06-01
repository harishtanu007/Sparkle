package com.mindbriks.sparkle.interfaces;

public interface IOnUploadProfileImageListener {
    void onSuccess(String imageUrl);
    void onFailure(String errorMessage);
}
