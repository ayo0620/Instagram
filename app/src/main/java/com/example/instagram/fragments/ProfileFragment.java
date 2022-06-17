package com.example.instagram.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.User;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ProfileFragment extends BaseFragment {
    ImageView ivProfilePhoto;
    TextView tvUsername;


    public User user = (User) User.getCurrentUser();
    public ProfileFragment() {
        // Required empty public constructor
    }
    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         tvUsername = view.findViewById(R.id.tvUsernNameProfile);
         ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);

         ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 Take a picture
                 launchCamera();
             }
         });

         user.fetchInBackground(new GetCallback<ParseObject>() {
             @Override
             public void done(ParseObject object, ParseException e) {
                    user = (User) object;
                    displayUserInfo();
             }

         });

    }

    public void displayUserInfo()
    {
        tvUsername.setText(user.getUsername());
        ParseFile profilePhoto = user.getProfilePhoto();
        if(profilePhoto != null)
        {
            Glide.with(getContext()).load(profilePhoto.getUrl()).into(ivProfilePhoto);
        }
        else
        {
            Toast.makeText(getContext(), "profile photo does not exist for"+ user.getUsername(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                Glide.with(getContext()).load(takenImage).circleCrop().into(ivProfilePhoto);
//                ivProfilePhoto.setImageBitmap(takenImage);
                user.setProfilePhoto(new ParseFile(photoFile));
                user.saveInBackground();
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}