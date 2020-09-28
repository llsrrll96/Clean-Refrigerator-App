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

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeList;

import java.util.ArrayList;

//MainSearchFrag 의 리싸이클러뷰의 어댑터
public class MainSearchAdapter extends RecyclerView.Adapter<MainSearchAdapter.ViewHolder>
{
    private final ArrayList<RecipeList> recipeLists;
    private Context context;
    private Intent intent;

    public MainSearchAdapter(ArrayList<RecipeList> recipeLists) {
        this.recipeLists = recipeLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_search_recipe_item,parent,false);

        return new ViewHolder(view);
    }//아이템 뷰로 사용 될 xml inflate 시킴

    @Override
    public void onBindViewHolder(@NonNull MainSearchAdapter.ViewHolder holder, final int position) {
        holder.imageView.setImageResource(recipeLists.get(position).getImage());
        holder.textView1.setText(recipeLists.get(position).getTitle());   //viewHolder 객체
        holder.textView2.setText(recipeLists.get(position).getName());   //viewHolder 객체
        holder.textView3.setText(Integer.toString(recipeLists.get(position).getCount()));   //viewHolder 객체
    }//뷰안에 필요한 정보 채움

    @Override
    public int getItemCount() {
        return recipeLists.size();
    }

    //ViewHolder 클래스
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = itemView.findViewById(R.id.recipe_image);
            textView1 = itemView.findViewById(R.id.recipe_text1); //파라메타 id 찾기
            textView2 = itemView.findViewById(R.id.recipe_text2); //파라메타 id 찾기
            textView3 = itemView.findViewById(R.id.recipe_text3); //파라메타 id 찾기

            view.setOnClickListener(new View.OnClickListener() { // 리싸이클러 아이템 클릭시
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        String title = recipeLists.get(pos).getTitle();
                        Toast.makeText(v.getContext(), title + " 클릭 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
