package com.example.emptytherefrigerator.main;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;

import java.util.ArrayList;

public class RecipeDetailImageAdapter extends RecyclerView.Adapter<RecipeDetailImageAdapter.ViewHolderPage>
{
    private ArrayList<Integer> imageList;

    public RecipeDetailImageAdapter(ArrayList<Integer> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_detail_imgview, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPage holder, int position) {

        holder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }



    class ViewHolderPage extends RecyclerView.ViewHolder {
        ImageView imageView;
        ViewHolderPage(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recipe_Detail_imageView);
        }

    }
}
