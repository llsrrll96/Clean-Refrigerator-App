package com.example.emptytherefrigerator.userView.MyRecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import java.util.ArrayList;

public class MyRecipeListAdapter extends RecyclerView.Adapter<MyRecipeListAdapter.MyRecipeListViewHolder>
{
    private ArrayList<RecipeIn> list;
    private LayoutInflater inflater;
    Context context;

    public MyRecipeListAdapter(Context context, ArrayList<RecipeIn> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public MyRecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.user_my_recipe_item, parent, false);
        return new MyRecipeListViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeListAdapter.MyRecipeListViewHolder holder, int position)
    {
        context = holder.itemView.getContext();

        holder.recipeMainImage.setImageBitmap(RecipeIn.StringToBitmap(list.get(position).getRecipeImageByte()[0]));
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount()       //전체 아이템 갯수 리턴
    {
        return list.size();
    }

    class MyRecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        MyRecipeListAdapter adapter;
        ImageView recipeMainImage;
        TextView commentCount, likeCount, uploadDate, title;
        ImageButton btnEditRecipe, btnDelRecipe;

        public MyRecipeListViewHolder(View view, MyRecipeListAdapter adapter)
        {
            super(view);
            this.adapter = adapter;
            recipeMainImage = itemView.findViewById(R.id.recipeMainImage_);
            title = itemView.findViewById(R.id.title);
            likeCount = itemView.findViewById(R.id.like_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            uploadDate = itemView.findViewById(R.id.uploadDate);
            btnEditRecipe = itemView.findViewById(R.id.recipeEditBtn);
            btnDelRecipe = itemView.findViewById(R.id.recipeDelBtn);
        }
        public void onBind(RecipeIn recipe)
        {
            title.setText(recipe.getTitle());
            commentCount.setText(Integer.toString(recipe.getCommentCount()));
            likeCount.setText(Integer.toString(recipe.getLikeCount()));
            uploadDate.setText(recipe.getUploadDate());
        }
        public void setListener()
        {
            btnEditRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //레시피 수정 화면 넘기기
                }
            });
            btnDelRecipe.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //레시피 삭제
                }
            });
        }
        @Override
        public void onClick(View view)
        {
            //레시피 조회 화면 넘기기
        }
    }

}
