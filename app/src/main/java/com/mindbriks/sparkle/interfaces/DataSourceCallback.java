package com.mindbriks.sparkle.interfaces;

public interface DataSourceCallback {
    void onSuccess();
    void onFailure(String errorMessage);
}
