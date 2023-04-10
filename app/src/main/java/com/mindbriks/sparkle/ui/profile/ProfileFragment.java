package com.mindbriks.sparkle.ui.profile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mindbriks.sparkle.ChooseLoginActivity;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.adapter.ProfileListAdapter;
import com.mindbriks.sparkle.databinding.FragmentProfileBinding;
import com.mindbriks.sparkle.model.ProfileItem;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ImageView profileImage;
    private List<ProfileItem> profileItemList = new ArrayList<>();
    private List<ProfileItem> basicsItemList = new ArrayList<>();
    private ProfileListAdapter profileListAdapter, basicsListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        TextView editProfileButton = binding.accountSettingsEditButton;
//        editProfileButton.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), EditProfileActivity.class);
//            startActivity(intent);
//        });

//        TextView mShowMeValue = binding.settingShowMeValue;
//
//        LinearLayout showMe = binding.settingShowMe;
//        showMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);
//
//                final ArrayAdapter<String> showMeAdapter = new ArrayAdapter<String>(getContext(), R.layout.show_me_item);
//                showMeAdapter.add("Men");
//                showMeAdapter.add("Women");
//
//                builderSingle.setAdapter(showMeAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mShowMeValue.setText(showMeAdapter.getItem(which));
//                    }
//                });
//                builderSingle.show();
//            }
//        });
        ActivityResultLauncher<String> getImageFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        Glide.with(getContext()).load(uri).into(profileImage);
                    }
                });

        ActivityResultLauncher<Intent> getImageFromCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Bundle bundle = result.getData().getExtras();
                            Bitmap bitmap = (Bitmap) bundle.get("data");
                            Glide.with(getContext()).load(bitmap).into(profileImage);
                        }
                    }
                });

        profileImage = binding.profileImage;
        ImageButton editProfileImage = binding.editProfileImage;
        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImagePickerDialog imagePickerDialog = new ImagePickerDialog(getContext(), getImageFromGallery, getImageFromCamera);
//                imagePickerDialog.show();
                AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);

                final ArrayAdapter<String> showMeAdapter = new ArrayAdapter<String>(getContext(), R.layout.gender_item);
                showMeAdapter.add("Upload photo");
                showMeAdapter.add("Take Photo");
                showMeAdapter.add("Remove Photo");
                builderSingle.setAdapter(showMeAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                            getImageFromGallery.launch("image/*");
                        else if (which == 1) {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getImageFromCamera.launch(cameraIntent);
                        } else if (which == 2) {
                            profileImage.setImageDrawable(null);
                        }
                    }
                });
                builderSingle.show();
            }
        });

        Button logOut = binding.profileLogoutButton;
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);
                builderSingle.setTitle("Are you sure you want to logout?");
                builderSingle.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), ChooseLoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

                builderSingle.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builderSingle.show();
            }
        });

        RecyclerView mProfileList = binding.profileListView;
        LinearLayoutManager profileListLayoutManager = new LinearLayoutManager(getContext());
        mProfileList.setLayoutManager(profileListLayoutManager);
        mProfileList.setClipToPadding(false);
        mProfileList.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mProfileList.getContext(), profileListLayoutManager.getOrientation());
        mProfileList.addItemDecoration(dividerItemDecoration);

        // Add items to the list adapter
        profileItemList.add(new ProfileItem("Full Name", "Jenny"));
        profileItemList.add(new ProfileItem("Date of Birth", "10th Aug 1996"));
        profileItemList.add(new ProfileItem("Gender", "Man"));
        profileItemList.add(new ProfileItem("Sexuality", "Straight"));

        profileListAdapter = new ProfileListAdapter(profileItemList, getContext());

        mProfileList.setAdapter(profileListAdapter);

        RecyclerView mBasicsList = binding.basicsListView;
        LinearLayoutManager basicsListLayoutManager = new LinearLayoutManager(getContext());
        mBasicsList.setLayoutManager(basicsListLayoutManager);
        mBasicsList.setClipToPadding(false);
        mBasicsList.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(mBasicsList.getContext(), basicsListLayoutManager.getOrientation());
        mBasicsList.addItemDecoration(dividerItemDecoration1);

        // Add items to the list adapter
        basicsItemList.add(new ProfileItem("Education", "Harvard"));
        basicsItemList.add(new ProfileItem("Religion", "Hindu"));
        basicsItemList.add(new ProfileItem("Height", "152 cm"));
        basicsItemList.add(new ProfileItem("Smoking", "No"));
        basicsItemList.add(new ProfileItem("Drinking", "Yes"));

        basicsListAdapter = new ProfileListAdapter(basicsItemList, getContext());

        mBasicsList.setAdapter(basicsListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}