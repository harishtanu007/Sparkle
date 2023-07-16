package com.mindbriks.sparkle.firebase;

import static com.mindbriks.sparkle.model.FirebaseConstants.CHATS;
import static com.mindbriks.sparkle.model.FirebaseConstants.CHAT_ID;
import static com.mindbriks.sparkle.model.FirebaseConstants.CHAT_MEMBERS;
import static com.mindbriks.sparkle.model.FirebaseConstants.CONNECTIONS;
import static com.mindbriks.sparkle.model.FirebaseConstants.GENDER;
import static com.mindbriks.sparkle.model.FirebaseConstants.MATCHES;
import static com.mindbriks.sparkle.model.FirebaseConstants.NOPE;
import static com.mindbriks.sparkle.model.FirebaseConstants.PROFILE_IMAGES;
import static com.mindbriks.sparkle.model.FirebaseConstants.USERS;
import static com.mindbriks.sparkle.model.FirebaseConstants.YEPS;
import static com.mindbriks.sparkle.utils.EncryptionUtils.decryptUser;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mindbriks.sparkle.interfaces.IAllUserDetailsCallback;
import com.mindbriks.sparkle.interfaces.IChatCreationCallback;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IDataSourceCallback;
import com.mindbriks.sparkle.interfaces.ILoginVerificationListener;
import com.mindbriks.sparkle.interfaces.IMatchedUsersCallback;
import com.mindbriks.sparkle.interfaces.IOnUploadProfileImageListener;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.EncryptedDbUser;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.model.SaveDetailsModel;
import com.mindbriks.sparkle.model.User;
import com.mindbriks.sparkle.utils.EncryptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirebaseDataSource implements IDataSource {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;
    private DatabaseReference usersDb;
    private DatabaseReference chatsDb;
    private FirebaseDatabase firebaseDatabase;


    public FirebaseDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = firebaseDatabase.getReference();
        mDatabase.keepSynced(true);
        firebaseUser = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        usersDb = firebaseDatabase.getReference(USERS);
        usersDb.keepSynced(true);
        chatsDb = firebaseDatabase.getReference(CHATS);
        chatsDb.keepSynced(true);
    }

    public String getCurrentUserId() {
        if (firebaseUser == null)
            firebaseUser = mAuth.getCurrentUser();
        return firebaseUser.getUid();
    }

    @Override
    public void deleteUser(String password, IDataSourceCallback callback) {
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(firebaseUser.getEmail()), password);

            // Prompt the user to re-provide their sign-in credentials
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    usersDb.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                userSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        firebaseUser.delete().addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                callback.onFailure(e.getMessage());
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    callback.onSuccess();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callback.onFailure(databaseError.getMessage());
                        }
                    });

                }
            });
        } else {
            callback.onFailure("Error deleting the user");
        }
    }

    @Override
    public void checkIsConnectionMatch(String currentUserId, String likedUserId, IChatCreationCallback callback) {
        usersDb.child(currentUserId).child(CONNECTIONS).child(YEPS).child(likedUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Generate chat id
                    String chatId = mDatabase.child(CHATS).push().getKey();
                    //add chat id to current user
                    usersDb.child(currentUserId).child(CONNECTIONS).child(MATCHES).child(likedUserId).child(CHAT_ID).setValue(chatId);
                    //add chat id to liked user
                    usersDb.child(likedUserId).child(CONNECTIONS).child(MATCHES).child(currentUserId).child(CHAT_ID).setValue(chatId);

                    createChatThread(currentUserId, likedUserId, chatId, new IChatCreationCallback() {
                        @Override
                        public void onChatCreationSuccess(String chatId) {
                            callback.onChatCreationSuccess(chatId);
                        }

                        @Override
                        public void onChatCreationFailed(String errorMessage) {
                            callback.onChatCreationFailed(errorMessage);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createChatThread(String currentUserId, String likedUserId, String chatId, IChatCreationCallback callback) {
        ArrayList<String> members = new ArrayList<>();
        members.add(likedUserId);
        members.add(currentUserId);
        chatsDb.child(chatId).child(CHAT_MEMBERS).setValue(members)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //remove like in current user db
//                        usersDb.child(currentUserId).child(CONNECTIONS).child(YEPS).child(likedUserId).removeValue();

                        //remove like in other user db
//                        usersDb.child(likedUserId).child(CONNECTIONS).child(YEPS).child(currentUserId).removeValue();
                        callback.onChatCreationSuccess(chatId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onChatCreationFailed(e.getMessage());
                    }
                });
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
    public void createUser(User user, IDataSourceCallback callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            // Redirect the user to the OTP verification page
                            callback.onSuccess();
                        }
                    });
                } else {
                    callback.onFailure(task.getException().getMessage());
                }
            } else {
                callback.onFailure("Error creating the user");
            }
        });
    }

    @Override
    public void uploadProfileImage(Uri imageUri, IOnUploadProfileImageListener listener) {
        if (imageUri != null) {
            String userId = getCurrentUserId();
            if (userId != null) {
                StorageReference imageRef = storageReference.child(PROFILE_IMAGES).child(userId + ".jpg");
                imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    // Get image URL after successful upload
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        listener.onSuccess(imageUrl);
                    }).addOnFailureListener(e -> {
                        listener.onFailure(e.getMessage());
                    });
                }).addOnFailureListener(e -> {
                    listener.onFailure(e.getMessage());
                });
            } else {
                listener.onFailure("Error uploading the user profile picture");
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void saveDetails(SaveDetailsModel saveDetailsModel, IDataSourceCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            if (saveDetailsModel.getProfileImageUri() != null) {
                uploadProfileImage(saveDetailsModel.getProfileImageUri(), new IOnUploadProfileImageListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(String imageUrl) {
                        uploadUser(firebaseUser, saveDetailsModel, callback, imageUrl);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        callback.onFailure(errorMessage);
                    }
                });
            } else {
                uploadUser(firebaseUser, saveDetailsModel, callback, null);
            }
        } else {
            callback.onFailure("Error saving the user details");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void uploadUser(FirebaseUser firebaseUser, SaveDetailsModel saveDetailsModel, IDataSourceCallback callback, String imageUrl) {
        DbUser.Builder builder = new DbUser.Builder();
        builder.id(firebaseUser.getUid());
        builder.name(saveDetailsModel.getName());
        builder.email(firebaseUser.getEmail());
        builder.gender(saveDetailsModel.getGender());
        builder.dob(saveDetailsModel.getDob());
        builder.interests(saveDetailsModel.getInterests());
        if (imageUrl != null) {
            builder.profile_image(imageUrl);
        }
        builder.height(saveDetailsModel.getHeight());
        builder.smoke_preference(saveDetailsModel.getSmokePreference());
        builder.drinking_preference(saveDetailsModel.getDrinkingPreference());
        builder.location(saveDetailsModel.getLocation());
        builder.encrypted(saveDetailsModel.isEncrypted());
        DbUser dbUser = builder.build();
        Object encryptedDbUser;
        if (DataSourceHelper.shouldEncryptUser()) {
            //encrypt user data
            encryptedDbUser = EncryptionUtils.encryptUser(dbUser, firebaseUser.getUid());
        } else {
            encryptedDbUser = dbUser;
        }
        if (encryptedDbUser == null) {
            callback.onFailure("Error while encrypting the data");
        } else {
            //TODO: verify the error message and change it to on complete listener if error message is too long
            usersDb.child(firebaseUser.getUid()).setValue(encryptedDbUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    callback.onSuccess();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    @Override
    public void login(String email, String password, IDataSourceCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            if (mAuth.getCurrentUser().isEmailVerified()) {
                // user is signed in and email is verified
                callback.onSuccess();
            } else {
                // user is signed in but email is not verified
                // callback.onFailure("Please verify your email to login");
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            // Redirect the user to the OTP verification page
                            callback.onFailure("Verification email sent to " + firebaseUser.getEmail());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onFailure("Error sending verification email");
                        }
                    });
                } else {
                    callback.onFailure("Error logging in");
                }
            }
        }).addOnFailureListener(e -> {
            callback.onFailure(e.getMessage());
        });
    }

    @Override
    public void onLoginVerification(ILoginVerificationListener listener) {
        boolean isLoggedIn = mAuth.getCurrentUser() != null;
        if (isLoggedIn) {
            if (mAuth.getCurrentUser().isEmailVerified()) {
                listener.onLoginVerificationSuccess();
            } else {
                listener.onUserEmailNotVerified();
            }
        } else {
            listener.onLoginVerificationFailure();
        }
    }

    @Override
    public void logoutUser(IDataSourceCallback callback) {
        mAuth.signOut();
        callback.onSuccess();
    }

    @Override
    public void getCurrentUserDetails(IUserDetailsCallback callback) {
        if (mAuth.getCurrentUser() != null) {
            getUserDetails(mAuth.getCurrentUser().getUid(), new IUserDetailsCallback() {
                @Override
                public void onUserDetailsFetched(DbUser userDetails) {
                    callback.onUserDetailsFetched(userDetails);
                }

                @Override
                public void onUserDetailsFetchFailed(String errorMessage) {
                    callback.onUserDetailsFetchFailed(errorMessage);
                }
            });
        } else {
            callback.onUserDetailsFetchFailed("User details not found");
        }
    }

    @Override
    public void getAllUserDetails(String userId, IAllUserDetailsCallback callback) {
        List<DbUser> profileList = new ArrayList<>();
        usersDb.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child(GENDER).getValue() != null) {
                    if (dataSnapshot.exists() && !dataSnapshot.getKey().equals(userId) && !dataSnapshot.child(CONNECTIONS).child(NOPE).hasChild(userId) && !dataSnapshot.child(CONNECTIONS).child(YEPS).hasChild(userId)) {
                        boolean encrypted = (boolean) dataSnapshot.child("encrypted").getValue();
                        if (encrypted) {
                            try {
                                EncryptedDbUser user = dataSnapshot.getValue(EncryptedDbUser.class);
                                DbUser decryptUser = decryptUser(user, userId);
                                profileList.add(decryptUser);
                                callback.onUserDetailsFetched(profileList);
                            } catch (Exception e) {
                                callback.onUserDetailsFetchFailed(e.getMessage());
                            }
                        } else {
                            DbUser dbUser = dataSnapshot.getValue(DbUser.class);
                            profileList.add(dbUser);
                            callback.onUserDetailsFetched(profileList);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void getLikedUserDetails(String userId, IAllUserDetailsCallback callback) {
        List<String> likedUserIds = new ArrayList<>();
        usersDb.child(userId).child(CONNECTIONS).child(YEPS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot likedUser : snapshot.getChildren()) {
                        likedUserIds.add(likedUser.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onUserDetailsFetchFailed(error.getMessage());
            }
        });

        getMatchedUsers(userId, new IMatchedUsersCallback() {
            @Override
            public void onMatchedUsersFound(List<String> matchedUsers) {
                likedUserIds.removeAll(matchedUsers);
                populateLikedUsers(likedUserIds, callback);
            }

            @Override
            public void onMatchedUsersFailed(String errorMessage) {
                populateLikedUsers(likedUserIds, callback);
            }
        });
    }

    private void populateLikedUsers(List<String> likedUserIds, IAllUserDetailsCallback callback) {
        List<DbUser> likedUserList = new ArrayList<>();
        for (String likedUser : likedUserIds) {
            getLikedUser(likedUser, new IUserDetailsCallback() {
                @Override
                public void onUserDetailsFetched(DbUser userDetails) {
                    likedUserList.add(userDetails);
                    callback.onUserDetailsFetched(likedUserList);
                }

                @Override
                public void onUserDetailsFetchFailed(String errorMessage) {
                    callback.onUserDetailsFetchFailed(errorMessage);
                }
            });
        }
    }

    private void getMatchedUsers(String userId, IMatchedUsersCallback callback) {
        List<String> matchedUserIds = new ArrayList<>();
        usersDb.child(userId).child(CONNECTIONS).child(MATCHES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot matchedUser : snapshot.getChildren()) {
                        matchedUserIds.add(matchedUser.getKey());
                    }
                    callback.onMatchedUsersFound(matchedUserIds);
                }
                else {
                    callback.onMatchedUsersFailed("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onMatchedUsersFailed(error.getMessage());
            }
        });
    }

    private void getLikedUser(String likedUserId, IUserDetailsCallback callback) {
        getUserDetails(likedUserId, new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                callback.onUserDetailsFetched(userDetails);
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {
                callback.onUserDetailsFetchFailed(errorMessage);
            }
        });
    }

    @Override
    public void getUserDetails(String userId, IUserDetailsCallback callback) {
        DatabaseReference userRef = usersDb.child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addUserDetails(snapshot, userId, callback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void setUserLiked(String currentUserId, String likedUserId, IDataSourceCallback callback) {
        usersDb.child(likedUserId).child(CONNECTIONS).child(YEPS).child(currentUserId).setValue("true")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
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
                })
        ;
    }

    @Override
    public void setUserDisliked(String currentUserId, String dislikedUserId, IDataSourceCallback callback) {
        usersDb.child(dislikedUserId).child(CONNECTIONS).child(NOPE).child(currentUserId).setValue("true")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addUserDetails(DataSnapshot snapshot, String userId, IUserDetailsCallback callback) {
        if (snapshot.exists()) {
            boolean encrypted = (boolean) snapshot.child("encrypted").getValue();
            if (encrypted) {
                try {
                    EncryptedDbUser user = snapshot.getValue(EncryptedDbUser.class);
                    DbUser decryptUser = decryptUser(user, userId);
                    callback.onUserDetailsFetched(decryptUser);
                } catch (Exception e) {
                    callback.onUserDetailsFetchFailed(e.getMessage());
                }
            } else {
                DbUser dbUser = snapshot.getValue(DbUser.class);
                callback.onUserDetailsFetched(dbUser);
            }
        } else {
            callback.onUserDetailsFetchFailed("User not found");
        }
    }

}
