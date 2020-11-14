package com.example.emptytherefrigerator.userView.MyComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeComment;
import com.example.emptytherefrigerator.entity.RecipeIn;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyCommentViewHolder>
{
    private ArrayList<RecipeComment> list = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;
    public MyCommentAdapter(Context context, ArrayList<RecipeComment> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public void onBindViewHolder(@NonNull MyCommentAdapter.MyCommentViewHolder holder, int position)
    {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
        holder.myComment_RecipeMainImage.setImageBitmap(RecipeIn.StringToBitmap(list.get(position).getRecipeImageByte()[0]));
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

    class MyCommentViewHolder extends RecyclerView.ViewHolder
    {
        MyCommentAdapter adapter;
        TextView commentListTitle, myCommentContent, myCommentUploadDate;
        ImageView myComment_RecipeMainImage;
        ImageButton btnCommentDel;
        int commentId;

        public MyCommentViewHolder(View view, MyCommentAdapter adapter)
        {
            super(view);
            this.adapter = adapter;
            commentListTitle = itemView.findViewById(R.id.commentListTitle);
            myCommentContent = itemView.findViewById(R.id.myCommentContent);
            myCommentUploadDate = itemView.findViewById(R.id.myCommentUploadDate);
            myComment_RecipeMainImage = itemView.findViewById(R.id.myComment_RecipeMainImage);
            btnCommentDel = itemView.findViewById(R.id.btnCommentDel);
            setListener();
        }
        public void onBind(RecipeComment comment)
        {
            commentListTitle.setText(comment.getRecipeTitle());
            myCommentContent.setText(comment.getContent());
            myCommentUploadDate.setText(comment.getUploadDate());
            //이미지 설정
        }
        public void setListener()
        {
            btnCommentDel.setOnClickListener(new View.OnClickListener()//댓글 삭제
            {
                @Override
                public void onClick(View v)
                {
                    deleteComment();
                }
            });
        }

        public void deleteComment()
        {
            MyAsyncTask deleteComment = new MyAsyncTask();
            JSONObject object = new JSONObject();
            int pos = getAdapterPosition();
            String result;
            try
            {
                object.accumulate("commentId", list.get(pos).getCommentId());
                result = deleteComment.execute("deleteComment", object.toString()).get();
                if(result.equals("1"))  //성공
                    return;
                else
                    Toast.makeText(itemView.getContext(),"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
