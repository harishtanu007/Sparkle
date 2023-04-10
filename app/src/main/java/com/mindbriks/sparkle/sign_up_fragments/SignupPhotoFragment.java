package com.mindbriks.sparkle.sign_up_fragments;

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
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.databinding.SignupGenderFragmentBinding;
import com.mindbriks.sparkle.databinding.SignupPhotoFragmentBinding;

public class SignupPhotoFragment extends Fragment {

    private SignupPhotoFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SignupPhotoFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView profileImage = binding.profileImage;

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

        ImageButton addImageButton = binding.addImage;
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);

                final ArrayAdapter<String> showMeAdapter = new ArrayAdapter<String>(getContext(), R.layout.alert_dialog_item);
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
        return root;
    }
}
