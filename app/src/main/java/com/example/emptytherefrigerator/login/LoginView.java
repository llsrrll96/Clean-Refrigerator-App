package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.emptytherefrigerator.AsyncTasks.LoginMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.User;
import com.example.emptytherefrigerator.main.MainPageView;
import org.json.JSONObject;

public class LoginView extends AppCompatActivity {

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

        autoLogin();
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
        User user = new User(editTextID.getText().toString(), editTextPW.getText().toString());
        try
        {
            data.accumulate("userId", user.getId());
            data.accumulate("pw", user.getPw());

            String result = login.execute("login", data.toString()).get();      //통신 결과값 저장

            if(result.equals("1"))      //로그인 성공
            {
                UserInfo.setString(this, UserInfo.ID_KEY, user.getId());
                UserInfo.setString(this, UserInfo.PW_KEY, user.getPw());

                Intent intent = new Intent(getApplicationContext(), MainPageView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("2") || result.equals("3"))       //로그인 실패, 2와 3 각각 정확한 사유는 정해지지 않은 상태
            {
                Toast.makeText(getApplicationContext(),"로그인 실패 : 아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void autoLogin()
    {
        Intent intent = getIntent();
        if(intent.getStringExtra("logOut").equals("logOut"))        //로그아웃에서 넘어온거면 자동로그인을 하지 않음
        {
            return;
        }
        String id = UserInfo.getString(this, UserInfo.ID_KEY);
        String pw = UserInfo.getString(this, UserInfo.PW_KEY);

        if(!id.equals("") && !pw.equals(""))        //값이 있는 경우에만 화면을 넘어가게 함
        {
            intent = new Intent(getApplicationContext(), MainPageView.class);      //현재 화면의 제어를 넘길 클래스 지정
            startActivity(intent);      //다음 화면으로 넘어감
        }
    }
}