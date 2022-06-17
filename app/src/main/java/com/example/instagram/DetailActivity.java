package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
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
    private TextView tvCreatedAt;
    private ImageButton ibLikes;
    private ImageButton ibComment;
    private RecyclerView rvComments;
    private TextView tvLikeCounts;
    private TextView tvDescription;
    CommentsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvScreenName = findViewById(R.id.tvScreenName);
        ivDeatilImage = findViewById(R.id.ivDetailImage);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);
//        tvDescription = findViewById(R.id.tvDetailDescription);
        tvCreatedAt = findViewById(R.id.tvTimeStamp);
//        ibLikes = findViewById(R.id.ibLikes);
        ibComment = findViewById(R.id.ibComment);
        rvComments = findViewById(R.id.rvComments);
//        tvLikeCounts = findViewById(R.id.tvlikeCounts);
//        adapter = new CommentsAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        ParseFile image = post.getImage();
        tvScreenName.setText(post.getUser().getUsername());
        Glide.with(this).load(image.getUrl()).into(ivDeatilImage);

        Date createdAt = post.getCreatedAt();
        String timeAgo = post.calculateTimeAgo(createdAt);
        tvTimeStamp.setText(timeAgo);

    }

}