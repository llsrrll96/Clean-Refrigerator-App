package com.example.emptytherefrigerator.memberView.MyComment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Comment;

import java.util.ArrayList;

public class MyCommentView extends AppCompatActivity
{
    RecyclerView recyclerView;
    ArrayList<Comment> list = new ArrayList<>();
    Toolbar toolbar;
    MyCommentAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_my_comment_list);
        initializeView();
    }
    public void initializeView()
    {
        getCommentList();
        recyclerView = findViewById(R.id.commentRecycler);
        adapter = new MyCommentAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.myCommentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기본 제목을 없애줍니다
    }
    public void getCommentList()        //서버에서 댓글 목록을 받아옴
    {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:     //뒤로가기 버튼이 눌렸을 때
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
