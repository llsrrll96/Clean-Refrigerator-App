package com.example.emptytherefrigerator.userView.MyRecipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.main.RecipeDetailUpdateView;
import com.example.emptytherefrigerator.main.RecipeDetailView;
import com.example.emptytherefrigerator.userView.SettingView;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyRecipeListAdapter extends RecyclerView.Adapter<MyRecipeListAdapter.MyRecipeListViewHolder>
{
    private ArrayList<RecipeIn> list;
    private LayoutInflater inflater;
    private Intent intent;
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
    public void removeItem(int position)
    {
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount()-position);
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

                    intent = new Intent(context, RecipeDetailUpdateView.class);
                    intent.putExtra("RECIPE",list.get(pos).getRecipeInId());
                    context.startActivity(intent);
                }
            });
            btnDelRecipe.setOnClickListener(new View.OnClickListener()  //레시피 삭제
            {
                @Override
                public void onClick(View v)
                {
                    createAlertDialog();

                }
            });
        }
        public void createAlertDialog()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context); //alert 창
            builder.setTitle("레시피 삭제");
            builder.setMessage("정말 삭제하시겠습니까?");
            builder.setNegativeButton("예", new DialogInterface.OnClickListener()   //ok 클릭시 회원정보 삭제
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    deleteRecipe();
                }
            });
            builder.setPositiveButton("아니오", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {}
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        public void deleteRecipe()
        {
            try
            {
                int pos = getAdapterPosition();
                RecipeMngAsyncTask deleteRecipe = new RecipeMngAsyncTask();
                JSONObject data = new JSONObject();
                data.accumulate("recipeInId",list.get(pos).getRecipeInId());
                String result = deleteRecipe.execute("deleteRecipe", data.toString()).get();        //성공 값은 일단 받아놓는걸로
                if(result.equals("1"))
                {
                    adapter.removeItem(pos);
                }
                else
                    Toast.makeText(itemView.getContext(),"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        public void inquireRecipeDetailView(View view)
        {
            int pos = getAdapterPosition();     //레시피 조회 화면 넘기기
            if(pos != RecyclerView.NO_POSITION)
            {
                context = view.getContext();
                Intent intent = new Intent(context, RecipeDetailView.class);     //조회된 레시피 화면으로 넘어간다
                intent.putExtra("RECIPE",list.get(pos).getRecipeInId());      //다음 화면에 레시피 객체 송신
                context.startActivity(intent);
            }

        }
    }
}
