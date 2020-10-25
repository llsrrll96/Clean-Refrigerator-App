package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.AsyncTasks.LoginMngAsyncTask;
import com.example.emptytherefrigerator.AsyncTasks.UserMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.main.MainPageView;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginSignUpView extends AppCompatActivity {
    private TextView btnOk;
    private TextView btnCancel;
    private Intent intent;

    private TextView id;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up);

        initializeView();
        setListener();
    }

    public void initializeView()
    {
        btnOk = (TextView)findViewById(R.id.ok);
        btnCancel = (TextView)findViewById(R.id.cancel);
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
    }
    public void setListener()
    {
        btnOk.setOnClickListener(new View.OnClickListener() {       //회원가입 버튼 -> 뷰에서는 OK 버튼
            @Override
            public void onClick(View v)
            {
                String strId = id.getText().toString(), strPw = password.getText().toString();

                if(strId.getBytes().length<=0 && strPw.getBytes().length<=0)
                {
                    Toast.makeText(getApplicationContext(),"정보를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(strId.getBytes().length<=0)      //아이디를 입력하지 않은 경우
                {
                    Toast.makeText(getApplicationContext(),"아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(strPw.getBytes().length<=0) //비밀번호를 입력하지 않은 경우
                {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                signUp(v);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {       //회원가입 cancel 버튼 클릭시 로그인 화면으로 넘어감
            @Override
            public void onClick(View v)
            {

                intent = new Intent(v.getContext(), LoginView.class);
                v.getContext().startActivity(intent);

            }
        });
    }
    public void signUp(View view)
    {
        UserMngAsyncTask signUp = new UserMngAsyncTask();
        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("userId", id.getText().toString());
            data.accumulate("pw", password.getText().toString());
            String result = signUp.execute("signUp", data.toString()).get();      //통신 결과값 저장

            if(result.equals("1"))          //성공
            {
                Intent intent = new Intent(getApplicationContext(), LoginView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("2"))     //실패
            {
                Toast.makeText(getApplicationContext(),"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("3"))     //실패 2 인데 정확한 에러 항목에 대해서는 얘기가 되지 않은 상태이다 -> 추후 수정 필요
            {
                Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}