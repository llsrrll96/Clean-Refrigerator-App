package com.example.emptytherefrigerator.userView.MyComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Comment;

import java.util.ArrayList;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyCommentViewHolder>
{
    private ArrayList<Comment> list;
    private LayoutInflater inflater;

    public MyCommentAdapter(Context context, ArrayList<Comment> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public void onBindViewHolder(@NonNull MyCommentAdapter.MyCommentViewHolder holder, int position)
    {
        holder.onBind(list.get(position));
    }
    @Override
    public int getItemCount()       //전체 아이템 갯수 리턴
    {
        return list.size();
    }

    @Override
    public MyCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.user_my_comment_item, parent, false);
        return new MyCommentAdapter.MyCommentViewHolder(itemView, this);
    }

    class MyCommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        MyCommentAdapter adapter;
        TextView commentListTitle, myCommentContent, myCommentUploadDate;
        ImageView myComment_RecipeMainImage;
        ImageButton btnCommentDel;

        public MyCommentViewHolder(View itemView, MyCommentAdapter adapter)
        {
            super(itemView);
            this.adapter = adapter;
            commentListTitle = itemView.findViewById(R.id.commentListTitle);
            myCommentContent = itemView.findViewById(R.id.myCommentContent);
            myCommentUploadDate = itemView.findViewById(R.id.myCommentUploadDate);
            myComment_RecipeMainImage = itemView.findViewById(R.id.myComment_RecipeMainImage);
            btnCommentDel = itemView.findViewById(R.id.btnCommentDel);
            setListener();
        }
        public void onBind(Comment comment)
        {
            commentListTitle.setText(comment.getRecipeTitle());
            myCommentContent.setText(comment.getUserId());
            myCommentUploadDate.setText(comment.getUploadDate());
            //이미지 설정
        }
        public void setListener()
        {
            btnCommentDel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    //댓글 삭제
                }
            });
        }
        @Override
        public void onClick(View v)
        {
            //조회 화면 넘기기
        }
    }
}
