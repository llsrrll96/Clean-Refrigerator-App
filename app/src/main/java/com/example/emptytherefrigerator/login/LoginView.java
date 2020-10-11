package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.emptytherefrigerator.AsyncTasks.LoginMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.main.MainPageView;
import org.json.JSONObject;

public class LoginView extends AppCompatActivity {
    private String serverURL = "http://192.168.0.6:3000/";

    private Button btnLogin;
    private Button btnSignUp;
    private Intent intent;
    private EditText editTextID;
    private EditText editTextPW;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initializeView();
        setListener();
    }

    public void initializeView()
    {
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnSignUp = (Button)findViewById(R.id.buttonCreate);
        editTextID = findViewById(R.id.editTextID);
        editTextPW = findViewById(R.id.editTextPW);
    }

    public void setListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login(v);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), LoginSignUpView.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void login(View view)        //로그인 버튼의 listener
    {
        LoginMngAsyncTask login = new LoginMngAsyncTask();
        JSONObject data = new JSONObject();
        try
        {
            data.accumulate("id", editTextID.getText().toString());
            data.accumulate("pw", editTextPW.getText().toString());

            String result = login.execute("login", data.toString()).get();      //통신 결과값 저장

            if(result.equals("ok"))
            {
                Intent intent = new Intent(getApplicationContext(), MainPageView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("wrong") || result.equals("error"))
            {
                Toast.makeText(getApplicationContext(),"로그인 실패 : 아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}