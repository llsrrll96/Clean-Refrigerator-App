package com.example.emptytherefrigerator.main.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeComment;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.network.JsonParsing;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;

public class CommentListView extends AppCompatActivity
{
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView userId;
    Button commentInputBtn;
    TextInputEditText commentInputText;
    ArrayList<RecipeComment> list = new ArrayList<>();
    int recipeInId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view);
        getCommentList();
        setRecyclerView();
        initializeView();       //화면 업데이트

        Intent intent = getIntent();
        recipeInId = intent.getExtras().getInt("recipeInId");
    }

    public void initializeView()
    {
        userId = findViewById(R.id.commentListMyId);
        userId.setText(UserInfo.getString(this, UserInfo.ID_KEY));
        commentInputBtn = findViewById(R.id.commentInputBtn);
        commentInputText = findViewById(R.id.commentInputText);
    }
    public void setRecyclerView()
    {
        recyclerView = findViewById(R.id.commentListRecyclerView);
        recyclerView.setAdapter(new CommentListAdapter(this, list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.commentListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기본 제목을 없애줍니다
    }
    public void getCommentList()        //서버에서 댓글 목록을 가져온다
    {
        MyAsyncTask readCommentList = new MyAsyncTask();

        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("recipeInId", recipeInId);
            String result = readCommentList.execute("readCommentList", data.toString()).get();        //요청 이름은 서버 보고 바꿀것

            if(!result.equals("2") || !result.equals("3"))      //실패가 아닌 경우
            {
                list = JsonParsing.parsingCommentList(result);
                return;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
