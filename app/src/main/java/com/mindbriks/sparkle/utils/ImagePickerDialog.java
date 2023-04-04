package com.mindbriks.sparkle.utils;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.R;

public class ImagePickerDialog extends Dialog {

    private static final int REQUEST_IMAGE_GALLERY = 999;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private ImageView imageViewGallery;
    private ImageView imageViewCamera;
    Uri cam_uri = null;

    public ImagePickerDialog(Context context, ActivityResultLauncher<String> mGetContent, ActivityResultLauncher<Intent> getImageFromCamera) {
        super(context);
        setContentView(R.layout.dialog_image_picker);
        setTitle("");

        imageViewGallery = findViewById(R.id.imageViewGallery);
        imageViewCamera = findViewById(R.id.imageViewCamera);

        Glide.with(context)
                .load(R.drawable.ic_baseline_photo_library_24)
                .into(imageViewGallery);

        Glide.with(context)
                .load(R.drawable.ic_baseline_photo_camera_24)
                .into(imageViewCamera);

        imageViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getImageFromCamera.launch(cameraIntent);
            }
        });
    }
}
