package com.example.emptytherefrigerator.userView.MyLike;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.LikeMngAsyncTask;
import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.LikeIn;
import com.example.emptytherefrigerator.entity.RecipeComment;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.userView.MyComment.MyCommentAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyLikeInAdapter extends RecyclerView.Adapter<MyLikeInAdapter.MyLikeInViewHolder>
{
    private ArrayList<LikeIn> list = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public MyLikeInAdapter(Context context, ArrayList<LikeIn> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public void onBindViewHolder(@NonNull MyLikeInAdapter.MyLikeInViewHolder holder, int position)
    {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
        //holder.myComment_RecipeMainImage.setImageBitmap(RecipeIn.StringToBitmap(list.get(position).getRecipeImageByte()[0]));
    }
    @Override
    public int getItemCount()       //전체 아이템 갯수 리턴
    {
        return list.size();
    }

    @Override
    public MyLikeInAdapter.MyLikeInViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.user_my_comment_item, parent, false);
        return new MyLikeInAdapter.MyLikeInViewHolder(itemView, this);
    }
    class MyLikeInViewHolder extends RecyclerView.ViewHolder
    {
        MyLikeInAdapter adapter;
        ImageView likeInMainImg;
        TextView likeInTitle, likeInRecipeWriter,likeInUploadDate;
        ImageButton likeDelBtn;

        public MyLikeInViewHolder(View view, MyLikeInAdapter adapter)
        {
            super(view);
            this.adapter = adapter;
            likeInMainImg = view.findViewById(R.id.likeInMainImg);
            likeInTitle = view.findViewById(R.id.likeInTitle);
            likeInRecipeWriter = view.findViewById(R.id.likeInRecipeWriter);
            likeInUploadDate = view.findViewById(R.id.likeInUploadDate);
            likeDelBtn = view.findViewById(R.id.likeDelBtn);
            setListener();
        }
        public void onBind(LikeIn likeIn)
        {
            likeInTitle.setText(likeIn.getRecipe().getTitle());
            //likeInRecipeWriter.setText(likeIn.get());
            //likeInRecipeWriter.setText(likeIn.);
            //likeInUploadDate.setText(likeIn.getUploadDate());
            //이미지 설정
        }
        public void setListener()
        {
            likeDelBtn.setOnClickListener(new View.OnClickListener(){       //좋아요 삭제
                @Override
                public void onClick(View v)
                {
                    LikeMngAsyncTask deleteLike = new LikeMngAsyncTask();

                    try
                    {
                        int pos = getAdapterPosition();
                        RecipeMngAsyncTask deleteRecipe = new RecipeMngAsyncTask();
                        JSONObject data = new JSONObject();
                        //data.accumulate("likeInId",list.get(pos).get);
                        //data.accumulate("userId", list.get(pos).getUserId());
                        String result = deleteLike.execute("deleteLikeIn",data.toString()).get();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
