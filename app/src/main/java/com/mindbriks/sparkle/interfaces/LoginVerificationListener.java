package com.mindbriks.sparkle.interfaces;

public interface LoginVerificationListener {
    void onLoginVerificationSuccess();
    void onUserEmailNotVerified();
    void onLoginVerificationFailure();
}