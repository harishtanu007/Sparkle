package com.mindbriks.sparkle.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindbriks.sparkle.R;

public class ImagePickerDialog extends Dialog {

    private static final int REQUEST_IMAGE_GALLERY = 999;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private ImageView imageViewGallery;
    private ImageView imageViewCamera;
    private TextView textViewTitle;

    public ImagePickerDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_image_picker);
        setTitle("");

        imageViewGallery = findViewById(R.id.imageViewGallery);
        imageViewCamera = findViewById(R.id.imageViewCamera);
        textViewTitle = findViewById(R.id.textViewTitle);

        imageViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);
                } else {
                    Toast.makeText(context, "This is not a activity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                    if (context instanceof Activity) {
                        ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    } else {
                        Toast.makeText(context, "This is not a activity", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
