package com.mindbriks.sparkle.interfaces;

import android.content.Context;

public interface IUserManager {
    void getCurrentUserDetails(Context context, IUserDetailsCallback callback);
}
