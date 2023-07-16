package com.mindbriks.sparkle.main_fragments.likes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.adapter.LikesAdapter;
import com.mindbriks.sparkle.databinding.FragmentLikesBinding;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.utils.GridSpacingItemDecoration;

import java.util.List;

public class LikesFragment extends Fragment {

    private LikesViewModel likesViewModel;
    private FragmentLikesBinding binding;
    private LikesAdapter mLikesAdapter;
    private NestedScrollView rootLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        likesViewModel =
                new ViewModelProvider(this).get(LikesViewModel.class);
        likesViewModel.loadLikedUsers();

        binding = FragmentLikesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootLayout = binding.rootLayout;

        DataSourceHelper.getDataSource().getCurrentUserDetails(new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                if (userDetails == null) {
                    Snackbar.make(binding.rootLayout, "Error while retrieving user details", Snackbar.LENGTH_LONG).show();
                }
                populateUsers(likesViewModel, userDetails);
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {

            }
        });
        return root;
    }

    private void populateUsers(LikesViewModel likesViewModel, DbUser dbUser) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView mLikesList = binding.likes;
        mLikesList.setLayoutManager(layoutManager);
        mLikesList.setClipToPadding(false);
        mLikesList.setHasFixedSize(true);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.horizontal_card);
        mLikesList.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        mLikesAdapter = new LikesAdapter(getContext(), dbUser);
        mLikesList.setAdapter(mLikesAdapter);
        likesViewModel.getLikedUsers().observe(getViewLifecycleOwner(), new Observer<List<DbUser>>() {
            @Override
            public void onChanged(List<DbUser> myDataList) {
                mLikesAdapter.setMyDataList(myDataList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}