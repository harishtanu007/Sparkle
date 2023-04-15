package com.mindbriks.sparkle.interfaces;

public interface OnUploadProfileImageListener {
    void onSuccess(String imageUrl);
    void onFailure(String errorMessage);
}
