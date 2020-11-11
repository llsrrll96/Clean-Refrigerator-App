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
import com.example.emptytherefrigerator.main.MainSearchAdapter;
import com.example.emptytherefrigerator.main.RecyclerDecoration;
import com.example.emptytherefrigerator.userView.MyRecipe.MyRecipeListAdapter;

import java.util.ArrayList;

public class FragLikeInView extends Fragment
{
    private RecyclerView recyclerView;
    private View view;
    private ArrayList<LikeIn> list = new ArrayList<>();
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
        getLikeInList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyLikeInAdapter(getActivity(), list));   //서버쪽 구현이 완료되면 이걸로 바꿀 예정
    }

    public void getLikeInList()
    {
//        LikeMngAsyncTask asyncTask = new LikeMngAsyncTask();
//        JSONObject data = new JSONObject();
//        try
//        {
//           data.accumulate("userId", UserInfo.getString(recyclerView.getContext(), UserInfo.ID_KEY));
//           String result = asyncTask.execute("readLikeOutList", data.toString()).get();        //요청 이름은 현재 서버에 없는거 같으니 나중에 말씀드리고 바꿀것
//            if(result.equals(null))                                                           //서버에서 받아온 값이 null이 아니라면 parsing 진행
//               list = JsonParsing.parsingLikeOutList(result);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }
}
