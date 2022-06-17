package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.PostsFragment;
import com.example.instagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager =  getSupportFragmentManager();
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this,"home!",Toast.LENGTH_SHORT).show();
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_compose:
                        Toast.makeText(MainActivity.this,"compose!",Toast.LENGTH_SHORT).show();
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(MainActivity.this,"profile!",Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.rlContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }


}