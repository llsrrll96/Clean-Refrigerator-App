package com.example.emptytherefrigerator.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeResult;

import java.util.ArrayList;

public class MainSearchResultAdapter extends RecyclerView.Adapter<MainSearchResultAdapter.ViewHolder>
{
    private static final int FOOTER_VIEW = 1;
    private static final int RECYCLER_VIEW = 2;
    private final ArrayList<RecipeResult> recipeResults;
    private Context context;
    private Intent intent;

    public MainSearchResultAdapter(ArrayList<RecipeResult> recipeResults) {
        this.recipeResults = recipeResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.main_search_result_recipe_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == recipeResults.size() + 1) {
            return FOOTER_VIEW;
        }
        return RECYCLER_VIEW;
    }
    @Override
    public void onBindViewHolder(@NonNull MainSearchResultAdapter.ViewHolder holder, int position) {
        holder.recipeImage.setImageResource(recipeResults.get(position).getImage());
        holder.title.setText(recipeResults.get(position).getTitle());   //viewHolder 객체
        holder.chatNumber.setText(Integer.toString(recipeResults.get(position).getChatNumber()));   //viewHolder 객체
        holder.likeNumber.setText(Integer.toString(recipeResults.get(position).getLikeNumber()));   //viewHolder 객체
        holder.uploadDate.setText(recipeResults.get(position).getUploadDate());
    }

    @Override
    public int getItemCount() {
        return recipeResults.size();
    }

    //ViewHolder 클래스
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView recipeImage;
        public TextView title;
        public TextView chatNumber;
        public TextView likeNumber;
        public TextView uploadDate;

        public ViewHolder(@NonNull View view) {
            super(view);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            title = itemView.findViewById(R.id.title); //파라메타 id 찾기
            chatNumber = itemView.findViewById(R.id.chatNumber); //파라메타 id 찾기
            likeNumber = itemView.findViewById(R.id.likeNumber); //파라메타 id 찾기
            uploadDate = itemView.findViewById(R.id.uploadDate);

            view.setOnClickListener(new View.OnClickListener() { // 리싸이클러 아이템 클릭시
                @Override
                public void onClick(View v) {           //클릭시
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){

                    }
                }
            });
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder
    {
        TextView footer;
        FooterViewHolder(View footerView) {
            super(footerView);
            footer = (TextView) footerView.findViewById(R.id.footer);
        }
    }
}
