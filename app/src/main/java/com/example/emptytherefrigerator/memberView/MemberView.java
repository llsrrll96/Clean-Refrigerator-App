package com.example.emptytherefrigerator.memberView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.emptytherefrigerator.R;

//Main 화면 Home
//이 달의 레시피 검색 결과
public class MemberView extends Fragment
{
    private ImageButton memberEditBtn, recipeInquireBtn, commentInquireBtn, likeInquireBtn, settingBtn;
    private Button logoutBtn;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.member_menu,container,false);
        initializeView();
        return view;
    }


    private void initializeView()
    {
        memberEditBtn = view.findViewById(R.id.memberEditBtn);
        recipeInquireBtn=view.findViewById(R.id.recipeInquireBtn);
        commentInquireBtn = view.findViewById(R.id.commentInquireBtn);
        likeInquireBtn = view.findViewById(R.id.likeInquireBtn);
        settingBtn=view.findViewById(R.id.settingBtn);

        logoutBtn= view.findViewById(R.id.logoutBtn);
    }
    private void setListener()
    {

    }
}
