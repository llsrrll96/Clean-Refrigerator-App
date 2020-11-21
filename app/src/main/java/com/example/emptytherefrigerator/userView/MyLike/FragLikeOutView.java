package com.example.emptytherefrigerator.userView.MyLike;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.LikeOut;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.network.JsonParsing;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragLikeOutView extends Fragment
{
    private RecyclerView recyclerView;
    private View view;
    private ArrayList<LikeOut> list=new ArrayList<>();
    private MyLikeOutAdapter adapter=null;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.user_my_like_out_list,container,false);
        getLikeOutList();
        setRecyclerView();
        return view;
    }

    public void setRecyclerView()
    {
        recyclerView = view.findViewById(R.id.likeOutRecyclerView);
        adapter = new MyLikeOutAdapter(getActivity(),list);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getLikeOutList()     //현재 어플 사용자의 좋아요 목록을 불러온다
    {
        MyAsyncTask asyncTask = new MyAsyncTask();
        JSONObject data = new JSONObject();
        try
        {
           data.accumulate("userId", UserInfo.getString(getContext(), UserInfo.ID_KEY));
           String result = asyncTask.execute("readUserLikeOut", data.toString()).get();
            if(!result.equals(null))                                                           //서버에서 받아온 값이 null이 아니라면 parsing 진행
               list = JsonParsing.parsingLikeOutList(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
