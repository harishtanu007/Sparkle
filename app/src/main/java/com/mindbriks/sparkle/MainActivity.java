package com.mindbriks.sparkle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mindbriks.sparkle.fragments.ChatsFragment;
import com.mindbriks.sparkle.fragments.LikesFragment;
import com.mindbriks.sparkle.fragments.ProfileFragment;
import com.mindbriks.sparkle.fragments.SwipeFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Show the initial fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SwipeFragment()).commit();

    }

    // Listen for navigation item selection events
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new SwipeFragment();
                            break;
                        case R.id.navigation_likes:
                            selectedFragment = new LikesFragment();
                            break;
                        case R.id.navigation_chats:
                            selectedFragment = new ChatsFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    // Replace the current fragment with the selected fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}