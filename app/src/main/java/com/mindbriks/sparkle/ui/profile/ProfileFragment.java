package com.mindbriks.sparkle.ui.profile;

import static android.app.Activity.RESULT_OK;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mindbriks.sparkle.EditProfileActivity;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.databinding.FragmentProfileBinding;
import com.mindbriks.sparkle.utils.ImagePickerDialog;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ImageView profileImage;
    private static final int REQUEST_IMAGE_GALLERY = 999;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView editProfileButton = binding.accountSettingsEditButton;
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
        });

        TextView mShowMeValue = binding.settingShowMeValue;

        LinearLayout showMe = binding.settingShowMe;
        showMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);

                final ArrayAdapter<String> showMeAdapter = new ArrayAdapter<String>(getContext(), R.layout.show_me_item);
                showMeAdapter.add("Men");
                showMeAdapter.add("Women");

                builderSingle.setAdapter(showMeAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mShowMeValue.setText(showMeAdapter.getItem(which));
                    }
                });
                builderSingle.show();
            }
        });
        profileImage = binding.profileImage;
        ImageButton editProfileImage = binding.editProfileImage;
        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePickerDialog imagePickerDialog = new ImagePickerDialog(getContext());
                imagePickerDialog.show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Get the image taken by the camera and display it
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Glide.with(this).load(imageBitmap).into(profileImage);
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                // Get the image selected from the gallery and display it
                Uri selectedImage = data.getData();
                Glide.with(this).load(selectedImage).into(profileImage);
            }
        }
    }
}