package com.example.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>  {
    private Context mContext;
    private List<Comment> comments;


    //protected void onRestart(){}

    public CommentsAdapter(Context mContext, List<Comment> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<com.example.instagram.Comment> newcomm) {
        comments.addAll(newcomm);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.instagram.Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView tvAuthor;
        public TextView tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvBody = itemView.findViewById(R.id.tvBody);
        }

        public void bind(com.example.instagram.Comment comment) {

            tvBody.setText(comment.getBody());
        }

    }
}

