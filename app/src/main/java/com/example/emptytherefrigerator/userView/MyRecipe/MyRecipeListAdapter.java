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
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Recipe;
import java.util.ArrayList;

public class MyRecipeListAdapter extends RecyclerView.Adapter<MyRecipeListAdapter.MyRecipeListViewHolder>
{
    private ArrayList<Recipe> list;
    private LayoutInflater inflater;

    public MyRecipeListAdapter(Context context, ArrayList<Recipe> list)
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
        TextView comment_count, like_count, uploadDate, title;
        ImageButton btnEditRecipe, btnDelRecipe;

        public MyRecipeListViewHolder(View itemView, MyRecipeListAdapter adapter)
        {
            super(itemView);
            this.adapter = adapter;
            recipeMainImage = itemView.findViewById(R.id.recipeMainImage);
            title = itemView.findViewById(R.id.title);
            like_count = itemView.findViewById(R.id.like_count);
            comment_count = itemView.findViewById(R.id.comment_count);
            uploadDate = itemView.findViewById(R.id.uploadDate);
            btnEditRecipe = itemView.findViewById(R.id.recipeEditBtn);
            btnDelRecipe = itemView.findViewById(R.id.recipeDelBtn);
        }
        public void onBind(Recipe recipe)
        {
            //mainimage 셋팅
            title.setText(recipe.getTitle());
            comment_count.setText(Integer.toString(recipe.getCommentCount()));
            like_count.setText(Integer.toString(recipe.getLikeCount()));
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
