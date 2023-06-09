package com.mindbriks.sparkle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mindbriks.sparkle.cache.UserCache;
import com.mindbriks.sparkle.databinding.ActivityMainBinding;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IDataSourceCallback;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;

public class MainActivity extends FragmentActivity {

    IDataSource dataSource;
    private ActivityMainBinding binding;
    private ConstraintLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rootLayout = binding.rootLayout;
        BottomNavigationView navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_likes, R.id.navigation_chats, R.id.navigation_profile).build();
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //navController.navigate(ProfileFragmentDirections.actionGlobalHomeFragment(currentUser));
        NavigationUI.setupWithNavController(binding.navView, navController);

        dataSource = DataSourceHelper.getDataSource();
        dataSource.getCurrentUserDetails(new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                UserCache.getInstance().cacheUser(userDetails);
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {
                Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        dataSource.logoutUser(new IDataSourceCallback() {
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

    private void sendToStart() {
        Intent intent = new Intent(getApplicationContext(), ChooseLoginActivity.class);
        startActivity(intent);
        finish();
    }

}