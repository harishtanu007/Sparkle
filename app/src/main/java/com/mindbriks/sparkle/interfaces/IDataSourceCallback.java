package com.mindbriks.sparkle.interfaces;

public interface IDataSourceCallback {
    void onSuccess();
    void onFailure(String errorMessage);
}
