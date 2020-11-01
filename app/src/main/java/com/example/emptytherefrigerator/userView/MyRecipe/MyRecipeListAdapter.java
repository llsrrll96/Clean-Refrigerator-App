package com.example.emptytherefrigerator.userView.MyRecipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.main.RecipeDetailView;

import org.json.JSONObject;

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

    class MyRecipeListViewHolder extends RecyclerView.ViewHolder
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

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    inquireRecipeDetailView(v);
                }

            });
            setListener();
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
            btnEditRecipe.setOnClickListener(new View.OnClickListener() {   //레시피 수정 화면 넘기기
                @Override
                public void onClick(View view)
                {
                    int pos = getAdapterPosition();
                    context = view.getContext();
//                    Intent intent = new Intent(context, 레시피 수정 화면.class);
//                    intent.putExtra("RECIPE",list.get(pos));      //다음 화면에 레시피 객체 송신
//                    context.startActivity(intent);
                }
            });
            btnDelRecipe.setOnClickListener(new View.OnClickListener()  //레시피 삭제
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        int pos = getAdapterPosition();
                        RecipeMngAsyncTask deleteRecipe = new RecipeMngAsyncTask();
                        JSONObject data = new JSONObject();
                        data.accumulate("recipeInId",list.get(pos).getRecipeInId());
                        String result = deleteRecipe.execute("deleteRecipe", data.toString()).get();        //성공 값은 일단 받아놓는걸로
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });
        }

        public void inquireRecipeDetailView(View view)
        {
            int pos = getAdapterPosition();     //레시피 조회 화면 넘기기
            if(pos != RecyclerView.NO_POSITION)
//            {
                context = view.getContext();
                Intent intent = new Intent(context, RecipeDetailView.class);     //조회된 레시피 화면으로 넘어간다
                intent.putExtra("RECIPE",list.get(pos));      //다음 화면에 레시피 객체 송신
                context.startActivity(intent);
//            }

        }
    }

}
