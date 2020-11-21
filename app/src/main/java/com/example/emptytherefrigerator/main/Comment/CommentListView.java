package com.example.emptytherefrigerator.main.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentListView extends AppCompatActivity
{
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView userId;
    Button commentInputBtn;
    TextInputEditText commentInputText;
    ArrayList<RecipeComment> list = new ArrayList<>();
    CommentListAdapter adapter;
    int recipeInId;     //댓글이 하나도 없는 경우에도 레시피 번호를 알아야 하니까

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view);
        Intent intent = getIntent();
        recipeInId = intent.getExtras().getInt("recipeInId");
        getCommentList();
        setRecyclerView();
        initializeView();       //화면 업데이트
    }

    public void initializeView()
    {
        userId = findViewById(R.id.commentListMyId);
        userId.setText(UserInfo.getString(this, UserInfo.ID_KEY));
        commentInputBtn = findViewById(R.id.commentInputBtn);
        commentInputBtn.setOnClickListener(new View.OnClickListener()
        {   //댓글 등록
            @Override
            public void onClick(View view)
            {
                sendComment();
            }
        });
        commentInputText = findViewById(R.id.commentInputText);     //max length 20, 글자수 counting해줌
    }
    public void setRecyclerView()
    {
        recyclerView = findViewById(R.id.commentListRecyclerView);
        CommentListAdapter adapter = new CommentListAdapter(this, list);
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.commentListToolbar);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기본 제목을 없애줍니다
    }
    public void sendComment()
    {
        MyAsyncTask createComment = new MyAsyncTask();
        String result;
        JSONObject object = new JSONObject();
        try
        {
            RecipeComment comment = new RecipeComment();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            comment.setUserId(UserInfo.getString(this, UserInfo.ID_KEY));
            comment.setUploadDate(format.format(new Date()));
            comment.setContent(commentInputText.getText().toString());
            comment.setRecipeId(recipeInId);

            object.accumulate("userId", comment.getUserId());
            object.accumulate("recipeInId", comment.getRecipeId());
            object.accumulate("content", comment.getContent());
            object.accumulate("uploadDate", comment.getUploadDate());

            result = createComment.execute("createComment", object.toString()).get();
            if(result.equals("1"))  //등록 성공
            {
                adapter.addItem(comment);
                commentInputText.setText(null);
                return;
            }
            else
                Toast.makeText(getApplicationContext(),"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCommentList()        //서버에서 댓글 목록을 가져온다
    {
        MyAsyncTask readCommentList = new MyAsyncTask();

        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("recipeInId", recipeInId);
            String result = readCommentList.execute("readComment", data.toString()).get();        //요청 이름은 서버 보고 바꿀것
            System.out.println(result);
            if(!result.equals("2"))      //실패가 아닌 경우
            {
                list = JsonParsing.parsingCommentViewList(result);
                return;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
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
