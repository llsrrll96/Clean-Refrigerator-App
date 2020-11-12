package com.example.emptytherefrigerator.main.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeComment;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder>
{
    private ArrayList<RecipeComment> list = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public CommentListAdapter(Context context, ArrayList<RecipeComment> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.comment_item, parent,false);
        return new CommentListViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, int position)
    {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class CommentListViewHolder extends RecyclerView.ViewHolder
    {
        CommentListAdapter adapter;
        TextView memberName,commentText,uploadDate;

        public CommentListViewHolder(View view, CommentListAdapter adapter)
        {
            super(view);
            this.adapter= adapter;
            memberName = itemView.findViewById(R.id.userId);
            commentText = itemView.findViewById(R.id.commentText);
            uploadDate = itemView.findViewById(R.id.uploadDate);
        }

        public void onBind(RecipeComment comment)
        {
            memberName.setText(comment.getUserId());
            commentText.setText(comment.getContent());
            uploadDate.setText(comment.getUploadDate());
        }
    }
}
