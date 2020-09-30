package com.example.emptytherefrigerator.memberView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

<<<<<<< HEAD:app/src/main/java/com/example/emptytherefrigerator/main/MainMemberMenu.java
//Main 화면의 회원창
public class MainMemberMenu extends AppCompatActivity
=======
public class memberView extends AppCompatActivity
>>>>>>> jaehe_0923:app/src/main/java/com/example/emptytherefrigerator/memberView/memberView.java
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
