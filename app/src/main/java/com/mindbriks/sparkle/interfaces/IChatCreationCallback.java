package com.mindbriks.sparkle.interfaces;

public interface IChatCreationCallback {
    void onChatCreationSuccess(String chatId);

    void onChatCreationFailed(String errorMessage);
}
