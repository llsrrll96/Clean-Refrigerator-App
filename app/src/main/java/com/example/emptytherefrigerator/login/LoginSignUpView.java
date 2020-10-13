package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.AsyncTasks.LoginMngAsyncTask;
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
        LoginMngAsyncTask login = new LoginMngAsyncTask();
        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("id", id.getText().toString());
            data.accumulate("pw", password.getText().toString());
            String result = login.execute("signUp", data.toString()).get();      //통신 결과값 저장

            if(result.equals("ok"))
            {
                Intent intent = new Intent(getApplicationContext(), LoginView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("error"))
            {
                Toast.makeText(getApplicationContext(),"내부 서버 문제로 실행할 수 없습니다", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("중복"))
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