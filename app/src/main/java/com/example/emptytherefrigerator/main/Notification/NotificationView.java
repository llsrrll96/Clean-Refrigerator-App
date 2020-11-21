package com.example.emptytherefrigerator.main.Notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Notification;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.main.RecyclerDecoration;
import com.example.emptytherefrigerator.network.JsonParsing;

import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationView extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<Notification> list = new ArrayList<>();
    Toolbar alarmListToolbar;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.main_search,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        alarmListToolbar = view.findViewById(R.id.alarmListToolbar);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initializeView();
    }
    public void initializeView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //상하
        recyclerView.addItemDecoration(new RecyclerDecoration(10)); //아이템 간격
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NotificationAdapter(getContext(), list));   //서버쪽 구현이 완료되면 이걸로 바꿀 예정
        readNotification();
    }

    public void readNotification()
    {
        MyAsyncTask readNoti = new MyAsyncTask();
        JSONObject object= new JSONObject();

        try
        {
            object.accumulate("userId", UserInfo.getString(getActivity(),UserInfo.ID_KEY));
            String result = readNoti.execute("getNotification", object.toString()).get();

            if(!result.equals("2"))     //조회가 성공인 경우
            {
                list = JsonParsing.parsingNotificationList(result);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
