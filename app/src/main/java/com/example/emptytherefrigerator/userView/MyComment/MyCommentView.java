package com.example.emptytherefrigerator.userView.MyComment;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.emptytherefrigerator.AsyncTasks.RecipeSearchAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeComment;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.network.JsonParsing;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyCommentView extends AppCompatActivity
{
    RecyclerView recyclerView;
    ArrayList<RecipeComment> list = new ArrayList<>();
    Toolbar toolbar;
    MyCommentAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_my_comment_list);
        getCommentList();       //서버에서 댓글 목록을 받아옴
        initializeView();       //화면 업데이트
    }

    public void initializeView()
    {
        recyclerView = findViewById(R.id.commentRecycler);
        adapter = new MyCommentAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.myCommentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기본 제목을 없애줍니다
    }
    public void getCommentList()
    {
        RecipeSearchAsyncTask searchMyComment = new RecipeSearchAsyncTask();
        String result = "";
        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("userId", UserInfo.getString(this,UserInfo.ID_KEY));
            result = searchMyComment.execute("readUserComment", data.toString()).get();
            System.out.println(result);
            list = JsonParsing.parsingCommentList(result);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
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
