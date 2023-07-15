package com.mindbriks.sparkle.main_fragments.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.mindbriks.sparkle.ChooseLoginActivity;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.adapter.ProfileListAdapter;
import com.mindbriks.sparkle.databinding.FragmentProfileBinding;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IDataSourceCallback;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.interfaces.IUserManager;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.ProfileItem;
import com.mindbriks.sparkle.utils.DobHelper;
import com.mindbriks.sparkle.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    ActivityResultLauncher<String> getImageFromGallery;
    ActivityResultLauncher<Intent> getImageFromCamera;
    IDataSource dataSource;
    IUserManager userManager;
    private FragmentProfileBinding binding;
    private ImageView mProfileImage;
    private TextView mProfileName;
    private List<ProfileItem> profileItemList = new ArrayList<>();
    private List<ProfileItem> basicsItemList = new ArrayList<>();
    private ProfileListAdapter profileListAdapter, basicsListAdapter;
    private DbUser mUser;
    private CoordinatorLayout rootLayout;
    private ProgressDialog mRegProgress;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootLayout = binding.rootLayout;

        dataSource = DataSourceHelper.getDataSource();
        userManager = UserManager.getInstance();

        setupLogOut();
        // Retrieve user details from cache or create a default user
        userManager.getCurrentUserDetails(getContext(), new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                mUser = userDetails;
                if (mUser == null) {
                    Snackbar.make(rootLayout, "Error while retrieving user details", Snackbar.LENGTH_LONG).show();
                }

                registerImagePickers();
                setupDeleteAccount();
                mProfileImage = binding.profileImage;
                mProfileName = binding.profileName;
                populateUserProfilePage();
                RecyclerView mProfileList = binding.profileListView;
                LinearLayoutManager profileListLayoutManager = new LinearLayoutManager(getContext());
                mProfileList.setLayoutManager(profileListLayoutManager);
                mProfileList.setClipToPadding(false);
                mProfileList.setHasFixedSize(true);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mProfileList.getContext(), profileListLayoutManager.getOrientation());
                mProfileList.addItemDecoration(dividerItemDecoration);

                // Add items to the list adapter
                profileItemList.add(new ProfileItem("Full Name", mUser.getName()));
                profileItemList.add(new ProfileItem("Date of Birth", DobHelper.getDateFromUnixTime(mUser.getDob())));
                profileItemList.add(new ProfileItem("Gender", mUser.getGender()));
                profileItemList.add(new ProfileItem("Sexuality", mUser.getGender()));

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
                basicsItemList.add(new ProfileItem("Height", mUser.getHeight()));
                basicsItemList.add(new ProfileItem("Smoking", mUser.getSmoke_preference().toString()));
                basicsItemList.add(new ProfileItem("Drinking", mUser.getDrinking_preference().toString()));

                basicsListAdapter = new ProfileListAdapter(basicsItemList, getContext());

                mBasicsList.setAdapter(basicsListAdapter);
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {

            }
        });

        return root;
    }

    private void setupDeleteAccount() {
        Button deleteAccount = binding.profileDeleteAccountButton;
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                // Set up the input
                final EditText inputPassword = new EditText(getActivity());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                inputPassword.setHint("Enter user password");
                builder.setView(inputPassword);

                builder
                        .setMessage("Are you sure you want to delete account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteUser(inputPassword.getText().toString());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void deleteUser(String password) {
        mRegProgress = new ProgressDialog(getContext(), R.style.AppThemeDialog);
        mRegProgress.setIndeterminate(true);
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.setMessage("Deleting user");
        mRegProgress.show();
        dataSource.deleteUser(password, new IDataSourceCallback() {
            @Override
            public void onSuccess() {
                mRegProgress.dismiss();
                Snackbar.make(rootLayout, "User Account deleted successfully", Snackbar.LENGTH_LONG).show();
                sendToStart();
            }

            @Override
            public void onFailure(String errorMessage) {
                mRegProgress.dismiss();
                Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void populateUserProfilePage() {
        setupProfileBar();
    }

    private void setupProfileBar() {
        setProfileName();
        setProfileImage();
    }

    private void setProfileName() {
        String profileName = mUser.getName();
        mProfileName.setText(profileName + ", " + DobHelper.calculateAge(mUser.getDob()));
    }

    private void setProfileImage() {
        String profileImageUrl = mUser.getProfile_image();
        if (profileImageUrl != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.card_view_place_holder_image)
                    .error(R.drawable.card_view_place_holder_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide
                    .with(getContext())
                    .load(profileImageUrl)
                    .apply(options)
                    .into(mProfileImage);
        } else {
            mProfileImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.card_view_place_holder_image));
        }
    }

    private void setupLogOut() {
        Button logOut = binding.profileLogoutButton;
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);
                builderSingle.setTitle("Are you sure you want to logout?");
                builderSingle.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataSource.logoutUser(new IDataSourceCallback() {
                            @Override
                            public void onSuccess() {
                                sendToStart();
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                            }
                        });
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
    }

    private void sendToStart() {
        Intent intent = new Intent(getContext(), ChooseLoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void registerImagePickers() {
        getImageFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                // Handle the returned Uri
                Glide.with(getContext()).load(uri).into(mProfileImage);
            }
        });

        getImageFromCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Glide.with(getContext()).load(bitmap).into(mProfileImage);
                }
            }
        });

        ImageButton editProfileImage = binding.editProfileImage;
        editProfileImage.setOnClickListener(new View.OnClickListener() {
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
                        if (which == 0) getImageFromGallery.launch("image/*");
                        else if (which == 1) {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getImageFromCamera.launch(cameraIntent);
                        } else if (which == 2) {
                            mProfileImage.setImageDrawable(null);
                        }
                    }
                });
                builderSingle.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}