package com.mindbriks.sparkle.firebase;

import static com.mindbriks.sparkle.model.FirebaseConstants.PROFILE_IMAGES;
import static com.mindbriks.sparkle.model.FirebaseConstants.USERS;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mindbriks.sparkle.interfaces.DataSource;
import com.mindbriks.sparkle.interfaces.DataSourceCallback;
import com.mindbriks.sparkle.interfaces.OnUploadProfileImageListener;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.model.SaveDetailsModel;
import com.mindbriks.sparkle.model.User;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataSource implements DataSource {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;
    private DatabaseReference usersRef;


    public FirebaseDataSource() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        usersRef = FirebaseDatabase.getInstance().getReference(USERS);
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
                        DbUser dbUser = new DbUser(firebaseUser.getUid(), user.getEmail());
                        usersRef.child(firebaseUser.getUid()).setValue(dbUser)
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
        StorageReference imageRef = storageReference.child(PROFILE_IMAGES).child(fileName + ".jpg");
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

    @Override
    public void saveDetails(SaveDetailsModel saveDetailsModel, DataSourceCallback callback) {
        uploadProfileImage(saveDetailsModel.getProfileImageUri(), new OnUploadProfileImageListener() {
            @Override
            public void onSuccess(String imageUrl) {
                DbUser dbUser = new DbUser(firebaseUser.getUid(), saveDetailsModel.getName(), firebaseUser.getEmail(), saveDetailsModel.getGender(), saveDetailsModel.getDob(), saveDetailsModel.getInterests(), imageUrl, saveDetailsModel.getHeight(), saveDetailsModel.getSmokePreference(), saveDetailsModel.getDrinkingPreference());
                //TODO: verify the error message and change it to on complete listener if error message is too long
                usersRef.child(firebaseUser.getUid()).setValue(dbUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                callback.onSuccess();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callback.onFailure(e.getMessage());
                            }
                        });
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    @Override
    public void login(String email, String password, DataSourceCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }
}
