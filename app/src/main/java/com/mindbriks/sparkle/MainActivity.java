package com.mindbriks.sparkle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mindbriks.sparkle.databinding.ActivityMainBinding;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.DataSource;
import com.mindbriks.sparkle.interfaces.DataSourceCallback;
import com.mindbriks.sparkle.interfaces.UserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.ui.home.HomeFragmentDirections;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DbUser currentUser;
    private ConstraintLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rootLayout = binding.rootLayout;

        DataSource dataSource = DataSourceHelper.getDataSource();
        dataSource.getCurrentUserDetails(new UserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                currentUser = userDetails;
                BottomNavigationView navView = binding.navView;
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_likes, R.id.navigation_chats, R.id.navigation_profile).build();
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                //navController.navigate(ProfileFragmentDirections.actionGlobalHomeFragment(currentUser));
                HomeFragmentDirections.ActionHomeFragmentToProfileFragment action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(currentUser);

                // Navigate to the profile fragment
                navController.navigate(action);
                NavigationUI.setupWithNavController(binding.navView, navController);
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {
                Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                dataSource.logoutUser(new DataSourceCallback() {
                    @Override
                    public void onSuccess() {
                        sendToStart();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        sendToStart();
                    }
                });
            }
        });
    }

    private void sendToStart() {
        Intent intent = new Intent(getApplicationContext(), ChooseLoginActivity.class);
        startActivity(intent);
        finish();
    }

}