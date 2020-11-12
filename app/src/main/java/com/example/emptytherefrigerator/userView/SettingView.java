package com.example.emptytherefrigerator.userView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.User;
import com.example.emptytherefrigerator.login.LoginView;
import com.example.emptytherefrigerator.login.UserInfo;

import org.json.JSONObject;

public class SettingView extends AppCompatActivity
{
    Toolbar settingToolbar;
    Switch swAlarm;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        initializeView();
        setListener();
    }

    public void initializeView()
    {
        settingToolbar = findViewById(R.id.settingToolbar);
        swAlarm = findViewById(R.id.swAlarm);
        btnDelete = findViewById(R.id.btnDelete);
        setSupportActionBar(settingToolbar);        //툴바 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public void setListener()
    {
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingView.this); //alert 창
                builder.setTitle("회원 탈퇴");
                builder.setMessage("정말 탈퇴하시겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener()   //ok 클릭시 회원정보 삭제
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        deleteUser();
                    }
                });
                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void deleteUser()
    {
        MyAsyncTask deleteUser = new MyAsyncTask();
        String result="";
        User user = new User( UserInfo.getString(this, UserInfo.ID_KEY), UserInfo.getString(this, UserInfo.PW_KEY));        //preference에서 회원 정보를 가져와 객체를 만듬
        JSONObject data = new JSONObject();

        try
        {
            data.accumulate("userId", user.getId());
            data.accumulate("pw", user.getPw());
            result = deleteUser.execute("deleteUser", data.toString()).get();
            if(result.equals("1"))      //성공
            {
                Intent intent = new Intent(getApplicationContext(), LoginView.class);      //현재 화면의 제어를 넘길 클래스 지정
                intent.putExtra("logOut", "logOut");
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("2"))
            {
                Toast.makeText(getApplicationContext(),"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();
            }
            UserInfo.clear(this);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
    }
}
