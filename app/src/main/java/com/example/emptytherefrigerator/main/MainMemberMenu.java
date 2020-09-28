package com.example.emptytherefrigerator.main;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

//Main 화면의 회원창
public class MainMemberMenu extends AppCompatActivity
{
    ImageButton memberEditBtn, recipeInquireBtn, commentInquireBtn, likeInquireBtn, settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_menu);
        initBtn();
    }

    private void initBtn()
    {
        memberEditBtn = findViewById(R.id.memberEditBtn);
        recipeInquireBtn=findViewById(R.id.recipeInquireBtn);
        commentInquireBtn = findViewById(R.id.commentInquireBtn);
        likeInquireBtn = findViewById(R.id.likeInquireBtn);
        settingBtn=findViewById(R.id.settingBtn);
    }
}
