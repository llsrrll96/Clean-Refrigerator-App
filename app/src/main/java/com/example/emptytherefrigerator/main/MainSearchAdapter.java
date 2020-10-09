package com.example.emptytherefrigerator.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Recipe;

import java.util.ArrayList;

//MainSearchFrag 의 리싸이클러뷰의 어댑터
public class MainSearchAdapter extends RecyclerView.Adapter<MainSearchAdapter.ViewHolder>
{
    private final ArrayList<Recipe> recipeLists;
    private Context context;
    private Intent intent;

    public MainSearchAdapter(ArrayList<Recipe> recipeLists) {
        this.recipeLists = recipeLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_search_result_recipe_item,parent,false);

        return new ViewHolder(view);
    }//아이템 뷰로 사용 될 xml inflate 시킴


    @Override
    public void onBindViewHolder(@NonNull MainSearchAdapter.ViewHolder holder, final int position) {
        context = holder.itemView.getContext();

        Glide.with(context)
                .load(recipeLists.get(position).getRecipeImagePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getRecipeImage());                         //이미지

        holder.title.setText(recipeLists.get(position).getTitle());   //viewHolder 객체
        holder.commentCount.setText(Integer.toString(recipeLists.get(position).getCommentCount()));   //viewHolder 객체
        holder.likeCount.setText(Integer.toString(recipeLists.get(position).getLikeCount()));   //viewHolder 객체
        holder.uploadDate.setText(recipeLists.get(position).getUploadDate());
    }//뷰안에 필요한 정보 채움

    @Override
    public int getItemCount() {
        return recipeLists.size();
    }

    //ViewHolder 클래스 / 구현때 수정
    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView recipeImage;
        public TextView title;
        public TextView commentCount;
        public TextView likeCount;
        public TextView uploadDate;
        private Context holdercontext;

        public ViewHolder(@NonNull View view) {
            super(view);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            title = itemView.findViewById(R.id.title); //파라메타 id 찾기
            commentCount = itemView.findViewById(R.id.chatNumber); //파라메타 id 찾기
            likeCount = itemView.findViewById(R.id.likeNumber); //파라메타 id 찾기
            uploadDate = itemView.findViewById(R.id.uploadDate);

            view.setOnClickListener(new View.OnClickListener() { // 리싸이클러 아이템 클릭시
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        String title = recipeLists.get(pos).getTitle();
                        Toast.makeText(v.getContext(), title + " 클릭 되었습니다.", Toast.LENGTH_SHORT).show();

                        holdercontext = v.getContext();
                        intent = new Intent(holdercontext, RecipeDetailView.class);     //조회된 레시피 화면으로 넘어간다
                        holdercontext.startActivity(intent);
                    }
                }
            });
        }

        public ImageView getRecipeImage() {
            return recipeImage;
        }

        public void setRecipeImage(ImageView recipeImage) {
            this.recipeImage = recipeImage;
        }
    }
}
