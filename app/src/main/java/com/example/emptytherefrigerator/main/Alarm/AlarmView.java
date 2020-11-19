package com.example.emptytherefrigerator.main.Alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;

public class AlarmView extends Fragment
{
    RecyclerView recyclerView;
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

    }
}
