package com.example.emptytherefrigerator.memberView;

import android.content.Intent;
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
import com.example.emptytherefrigerator.main.MainPageView;
import com.example.emptytherefrigerator.memberView.MyRecipe.MyRecipeListView;

//Main 화면 Home
//이 달의 레시피 검색 결과
public class UserView extends Fragment
{
    private ImageButton btnEditUser, btnRecipeInquire, btnCommentInquire, btnLIkeInquire, btnSetting;
    private Button logoutBtn;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.member_menu,container,false);
        initializeView();
        setListener();
        return view;
    }
    private void initializeView()
    {
        btnEditUser = view.findViewById(R.id.memberEditBtn);
        btnRecipeInquire = view.findViewById(R.id.recipeInquireBtn);
        btnCommentInquire = view.findViewById(R.id.commentInquireBtn);
        btnLIkeInquire = view.findViewById(R.id.likeInquireBtn);
        btnSetting=view.findViewById(R.id.settingBtn);
        logoutBtn= view.findViewById(R.id.logoutBtn);
    }
    private void setListener()
    {
        btnEditUser.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //멤버 수정 화면
                }
            });
        btnRecipeInquire.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getActivity(), MyRecipeListView.class);      //현재 화면의 제어를 넘길 클래스 지정
                        startActivity(intent);      //다음 화면으로 넘어감
                    }
                });
        btnCommentInquire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //내 댓글 조회
            }
        });
        btnLIkeInquire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //내 좋아요 조회
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //셋팅 화면 넘김
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //로그아웃
            }
        });
    }
}
