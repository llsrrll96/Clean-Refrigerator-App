package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

public class LoginSignUp extends AppCompatActivity {

    private TextView btnOk;
    private TextView btnCancel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up);

        initializeView();
        setListener();
    }
    public void initializeView()
    {
        btnOk = (TextView)findViewById(R.id.ok);
        btnCancel = (TextView)findViewById(R.id.cancel);
    }
    public void setListener()
    {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //회원가입 완료
                v.getContext().startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), Login.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}