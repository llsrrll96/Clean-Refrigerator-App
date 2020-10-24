package com.example.emptytherefrigerator.userView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.emptytherefrigerator.AsyncTasks.UserMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.User;
import com.example.emptytherefrigerator.login.UserInfo;

import org.json.JSONObject;

public class UserEditView extends AppCompatActivity
{
    TextView userEditId;
    EditText userEditPw;
    Button btnUserEdit;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit);
        initializeView();
        setListener();
        setUserInfo();
    }

    public void initializeView()
    {
        userEditId = findViewById(R.id.userEditId);
        userEditPw = findViewById(R.id.userEditPw);
        btnUserEdit = findViewById(R.id.btnUserEdit);
        toolbar = findViewById(R.id.userEditToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기본 제목을 없애줍니다
    }

    public void setListener()       //button listener 설정
    {
        btnUserEdit.setOnClickListener(new View.OnClickListener()       //정보 수정
        {
            @Override
            public void onClick(View view)
            {
                if(userEditPw.getText().toString().equals(""))      //비밀번호를 입력하지 않은 경우
                {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateUserInfo(view);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())        //뒤로가기 버튼이 눌렸을 때
        {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void setUserInfo()       //  화면 TextView에 preference에서 아이디 값을 꺼내 설정한다
    {
        userEditId.setText(UserInfo.getString(this, UserInfo.ID_KEY));
    }

    public void updateUserInfo(View view)       //서버에 회원정보 update 요청
    {
        UserMngAsyncTask editUser = new UserMngAsyncTask();
        User user = new User(userEditId.getText().toString(),userEditPw.getText().toString());

        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("userId", user.getId());
            data.accumulate("pw", user.getPw());

            String result = editUser.execute("updateUser", data.toString()).get();      //통신 결과값 저장
            if(result.equals("1"))      //수정이 완료된 경우(성공)
            {
                UserInfo.setString(this, UserInfo.PW_KEY, user.getPw());        //preference 업데이트
            }
            else if(result.equals("2")) //실패한 경우
            {

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
