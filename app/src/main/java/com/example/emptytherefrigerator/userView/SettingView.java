package com.example.emptytherefrigerator.userView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        context = this;

        initializeView();
        setListener();
    }

    public void initializeView()
    {
        settingToolbar = findViewById(R.id.settingToolbar);
        swAlarm = findViewById(R.id.swAlarm);
        if(UserInfo.getInt(this, UserInfo.NOTIFICATION_KEY)==1)     //이전에 이미 알림을 켜둔 상태라면
            swAlarm.setChecked(true);
        btnDelete = findViewById(R.id.btnDelete);
        setSupportActionBar(settingToolbar);        //툴바 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(UserInfo.getInt(this, UserInfo.NOTIFICATION_KEY)==0)     //이전에 설정이 꺼져있었다면
            swAlarm.setChecked(false);
        else if(UserInfo.getInt(this, UserInfo.NOTIFICATION_KEY)==1)    //이전에 설정이 켜져있었다면
            swAlarm.setChecked(true);
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

        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // 스위치 버튼이 체크되었는지 검사
                if (isChecked)
                {
                   UserInfo.setInt(context, UserInfo.NOTIFICATION_KEY, 1);
                }
                else
                    {
                    UserInfo.setInt(context, UserInfo.NOTIFICATION_KEY, 0);
                }
                updateSetting(UserInfo.getInt(context, UserInfo.NOTIFICATION_KEY));       //현재로서는 작동하지 않는 기능
            }
        });
    }

    public void updateSetting(int setting)      //알림 설정 정보 업데이트
    {
        MyAsyncTask update = new MyAsyncTask();
        JSONObject object = new JSONObject();

        System.out.println("notification : " +UserInfo.getInt(this, UserInfo.NOTIFICATION_KEY));
        try
        {
            object.accumulate("userId", UserInfo.getString(this, UserInfo.ID_KEY));
            object.accumulate("notification",UserInfo.getInt(this, UserInfo.NOTIFICATION_KEY));

            String result = update.execute("updateSetting", object.toString()).get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();
        }
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
