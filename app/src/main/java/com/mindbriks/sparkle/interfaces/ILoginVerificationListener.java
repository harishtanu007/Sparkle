package com.mindbriks.sparkle.interfaces;

public interface ILoginVerificationListener {
    void onLoginVerificationSuccess();
    void onUserEmailNotVerified();
    void onLoginVerificationFailure();
}