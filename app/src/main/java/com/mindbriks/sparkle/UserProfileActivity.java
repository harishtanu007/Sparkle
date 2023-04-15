package com.mindbriks.sparkle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.adapter.ProfileListAdapter;
import com.mindbriks.sparkle.databinding.ActivityUserProfileBinding;
import com.mindbriks.sparkle.model.ProfileItem;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfileBinding binding;

    private List<ProfileItem> profileItemList = new ArrayList<>();
    private ProfileListAdapter profileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Jenny");

        RecyclerView mProfileList = binding.userContent.profileListView;
        LinearLayoutManager profileListLayoutManager = new LinearLayoutManager(getApplicationContext());
        mProfileList.setLayoutManager(profileListLayoutManager);
        mProfileList.setClipToPadding(false);
        mProfileList.setHasFixedSize(true);
        mProfileList.setEnabled(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mProfileList.getContext(), profileListLayoutManager.getOrientation());
        mProfileList.addItemDecoration(dividerItemDecoration);

        // Add items to the list adapter
        profileItemList.add(new ProfileItem("Full Name", "Jenny"));
        profileItemList.add(new ProfileItem("Date of Birth", "10th Aug 1996"));
        profileItemList.add(new ProfileItem("Gender", "Man"));
        profileItemList.add(new ProfileItem("Sexuality", "Straight"));

        profileListAdapter = new ProfileListAdapter(profileItemList, getApplicationContext());

        mProfileList.setAdapter(profileListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}