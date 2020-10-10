package com.example.emptytherefrigerator.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
            login.execute("login", data.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /*
        @Override
        protected void onPostExecute(String result)         //doinbackground의 결과값 사용
        {
            super.onPostExecute(result);
           // System.out.println(result);       //-> ok
            if(result.equals("ok"))        //로그인 성공
            {
                Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainPageView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("wrong") || result.equals("error"))      //로그인 실패
            {
                Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
            }

        }
    }
*/
}