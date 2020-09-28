package com.example.emptytherefrigerator.memberView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

public class memberView extends AppCompatActivity
{
    private ImageButton memberEditBtn, recipeInquireBtn, commentInquireBtn, likeInquireBtn, settingBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_menu);
        initializeView();
    }

    private void initializeView()
    {
        memberEditBtn = findViewById(R.id.memberEditBtn);
        recipeInquireBtn=findViewById(R.id.recipeInquireBtn);
        commentInquireBtn = findViewById(R.id.commentInquireBtn);
        likeInquireBtn = findViewById(R.id.likeInquireBtn);
        settingBtn=findViewById(R.id.settingBtn);

        logoutBtn= findViewById(R.id.logoutBtn);
    }
    private void setListener()
    {

    }
}
