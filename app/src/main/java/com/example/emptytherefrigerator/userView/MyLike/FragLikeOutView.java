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
        recyclerView = view.findViewById(R.id.likeOutRecyclerView);
        adapter = new MyLikeOutAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getLikeInList()     //현재 어플 사용자의 좋아요 목록을 불러온다
    {
        MyAsyncTask asyncTask = new MyAsyncTask();
        JSONObject data = new JSONObject();
        try
        {
           data.accumulate("userId", UserInfo.getString(recyclerView.getContext(), UserInfo.ID_KEY));
           String result = asyncTask.execute("readUserLikeOut", data.toString()).get();        //요청 이름은 현재 서버에 없는거 같으니 나중에 말씀드리고 바꿀것
            if(result.equals(null))                                                           //서버에서 받아온 값이 null이 아니라면 parsing 진행
               list = JsonParsing.parsingLikeOutList(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
