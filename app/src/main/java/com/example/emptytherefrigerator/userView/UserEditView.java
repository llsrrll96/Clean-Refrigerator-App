package com.example.emptytherefrigerator.userView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.login.UserInfo;

public class UserEditView extends AppCompatActivity
{
    TextView userEditId;
    EditText userEditPw;
    Button btnUserEdit;

    SharedPreferences userInfo = getSharedPreferences(UserInfo.PREFERENCES_NAME, MODE_PRIVATE);
    SharedPreferences.Editor preferenceEditor = userInfo.edit();

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
    }

    public void setListener()
    {
        btnUserEdit.setOnClickListener(new View.OnClickListener()       //정보 수정
        {
            @Override
            public void onClick(View view)
            {
                editUserInfo(view);
            }
        });
    }
    public void setUserInfo()
    {
        userEditId.setText(userInfo.getString(UserInfo.ID_KEY, ""));
    }

    public void editUserInfo(View view)
    {

    }
}
