package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    Post post;
    TextView tvScreenName;
    ImageView ivDeatilImage;
    TextView tvTimeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvScreenName = findViewById(R.id.tvScreenName);
        ivDeatilImage = findViewById(R.id.ivDetailImage);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        ParseFile image = post.getImage();
        tvScreenName.setText(post.getUser().getUsername());
        Glide.with(this).load(image.getUrl()).into(ivDeatilImage);

        Date createdAt = post.getCreatedAt();
        String timeAgo = post.calculateTimeAgo(createdAt);
        tvTimeStamp.setText(timeAgo);

    }

}