package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.main.MainPage;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignUp;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeView();
        setListener();
    }

    public void initializeView()
    {
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnSignUp = (Button)findViewById(R.id.buttonCreate);
    }

    public void setListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), MainPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);




                //성공시
                Toast.makeText(v.getContext(),"로그인 성공",Toast.LENGTH_LONG).show();
                v.getContext().startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), LoginSignUp.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}