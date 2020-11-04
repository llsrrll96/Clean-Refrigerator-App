package com.example.emptytherefrigerator.userView.MyLike;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.LikeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.LikeIn;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.userView.MyRecipe.MyRecipeListAdapter;

import java.util.ArrayList;

public class FragLikeInView extends Fragment
{
    private RecyclerView recyclerView;
    private View view;
    private ArrayList<LikeIn> list;
    private MyLikeInAdapter adapter;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.user_my_like_in_list,container,false);
        recyclerView = view.findViewById(R.id.likeInRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setRecyclerView();          //이달의 레시피 창
    }

    public void setRecyclerView()
    {
        recyclerView = view.findViewById(R.id.recipeRecycler);
        adapter = new MyLikeInAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getLikeInList()
    {
        LikeMngAsyncTask asyncTas = new LikeMngAsyncTask();

    }
}
