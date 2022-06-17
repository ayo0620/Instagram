package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivProfileImage;
        private RelativeLayout item_post;
        private TextView tvpostTimeStamp;
        private ImageButton ibFavoritePost;
        private TextView tvfavoriteCount;
        public static  final String KEY_PROFILE_IMAGE = "profileImage";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            item_post = itemView.findViewById(R.id.itemPost);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvpostTimeStamp = itemView.findViewById(R.id.tvPostTimeStamp);
            tvfavoriteCount = itemView.findViewById(R.id.tvFavoriteCount);
            ibFavoritePost = itemView.findViewById(R.id.ibFavoritePost);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            Date createdAt = post.getCreatedAt();
            String timeAgo = post.calculateTimeAgo(createdAt);
            tvpostTimeStamp.setText(timeAgo);
            ParseFile image = post.getImage();
            ParseFile profilePicture = post.getUser().getParseFile(KEY_PROFILE_IMAGE);
            if(profilePicture != null){
                Glide.with(context).load(profilePicture.getUrl()).transform(new RoundedCorners(90)).into(ivProfileImage);
            }else{
                ivImage.setVisibility(View.GONE);
            }
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            item_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                    context.startActivity(i);
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity)context;
                    activity.goToProfileTab((User) post.getUser());
                }
            });
            ibFavoritePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> likeBy = post.getLikedBy();
                    String likeCount;
                    String likeText;
                    if (!likeBy.contains(ParseUser.getCurrentUser().getObjectId()))
                    {
                        likeBy.add(ParseUser.getCurrentUser().getObjectId());
                        post.setLikedBy(likeBy);
                        Drawable newImage = context.getDrawable(R.drawable.ic_baseline_favorite_24);
                        ibFavoritePost.setImageDrawable(newImage);
                        likeText = String.valueOf(post.likeCountDisplayText());
                        likeCount = String.valueOf(likeBy.size());
                        tvfavoriteCount.setText(likeCount+ likeText);
                    }
                    else
                    {
                        likeBy.remove(ParseUser.getCurrentUser().getObjectId());
                        post.setLikedBy(likeBy);
                        Drawable newImage = context.getDrawable(R.drawable.ic_white_favorite);
                        ibFavoritePost.setImageDrawable(newImage);
                        likeText = String.valueOf(post.likeCountDisplayText());
                        likeCount = String.valueOf(likeBy.size());
                        tvfavoriteCount.setText("likes");
                    }
                    post.saveInBackground();
//                    tvFavoriteCount.setText(post.likedCount);
                }
            });
        }

    }
    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}

