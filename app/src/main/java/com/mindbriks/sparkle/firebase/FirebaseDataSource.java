package com.mindbriks.sparkle.firebase;

import static com.mindbriks.sparkle.model.FirebaseConstants.PROFILE_IMAGES;
import static com.mindbriks.sparkle.model.FirebaseConstants.USERS;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mindbriks.sparkle.interfaces.DataSource;
import com.mindbriks.sparkle.interfaces.DataSourceCallback;
import com.mindbriks.sparkle.interfaces.OnUploadProfileImageListener;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.model.User;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataSource implements DataSource {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;

    public FirebaseDataSource() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public List<Profile> getUsers() {
        //TODO:code to get users from firebase
        List<Profile> profileList = new ArrayList<>();

        Profile profile1 = new Profile("1", "Harish", 21, "", 12);
        Profile profile2 = new Profile("2", "Barre", 26, "", 13);
        Profile profile3 = new Profile("3", "comp", 22, "", 14);

        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        return profileList;
    }

    @Override
    public void createUser(User user, DataSourceCallback callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        FirebaseDbUser firebaseDbUser = new FirebaseDbUser(firebaseUser.getUid(), user.getEmail());
                        mDatabase.child(USERS).child(firebaseUser.getUid()).setValue(firebaseDbUser)
                                .addOnCompleteListener(innerTask -> {
                                    if (innerTask.isSuccessful()) {
                                        callback.onSuccess();
                                    } else {
                                        callback.onFailure(innerTask.getException().getMessage());
                                    }
                                });
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public boolean isUserDetailsExist() {
        return false;
    }

    @Override
    public void uploadProfileImage(Uri imageUri, OnUploadProfileImageListener listener) {
        String fileName = firebaseUser.getUid();
        StorageReference imageRef = storageReference.child(PROFILE_IMAGES + "/" + fileName);
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get image URL after successful upload
                    imageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                listener.onSuccess(imageUrl);
                            })
                            .addOnFailureListener(e -> {
                                listener.onFailure(e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    listener.onFailure(e.getMessage());
                });
    }
}
