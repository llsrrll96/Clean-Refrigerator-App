package com.example.emptytherefrigerator.memberView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.login.LoginView;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.memberView.MyComment.MyCommentView;
import com.example.emptytherefrigerator.memberView.MyRecipe.MyRecipeListView;

import static android.content.Context.MODE_PRIVATE;

//회원정보 창
public class UserView extends Fragment
{
    private ImageButton btnEditUser, btnRecipeInquire, btnCommentInquire, btnLIkeInquire, btnSetting;
    private Button logoutBtn;
    private View view;
    private TextView userIdTextView, userPwTextView;
    SharedPreferences userInfo = getActivity().getSharedPreferences(UserInfo.PREFERENCES_NAME, MODE_PRIVATE);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.user_menu,container,false);
        initializeView();
        setListener();
        setUserInfo();
        return view;
    }
    public void initializeView()
    {
        btnEditUser = view.findViewById(R.id.memberEditBtn);
        btnRecipeInquire = view.findViewById(R.id.recipeInquireBtn);
        btnCommentInquire = view.findViewById(R.id.commentInquireBtn);
        btnLIkeInquire = view.findViewById(R.id.likeInquireBtn);
        btnSetting=view.findViewById(R.id.settingBtn);
        logoutBtn= view.findViewById(R.id.logoutBtn);
    }
    public void setListener()
    {
        btnEditUser.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //멤버 수정 화면
                }
            });
        btnRecipeInquire.setOnClickListener(new View.OnClickListener()
        {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getActivity(), MyRecipeListView.class);      //현재 화면의 제어를 넘길 클래스 지정
                        startActivity(intent);      //다음 화면으로 넘어감
                    }
                });
        btnCommentInquire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MyCommentView.class);
                startActivity(intent);
            }
        });
        btnLIkeInquire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //내 좋아요 조회
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //셋팅 화면 넘김
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener()     ////로그인 화면으로 돌아감
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), LoginView.class);
                startActivity(intent);
            }
        });
    }
    public void setUserInfo()
    {
        String id = userInfo.getString(UserInfo.ID_KEY, "");
        String pw = userInfo.getString(UserInfo.PW_KEY, "");

        if(!id.equals("") || !pw.equals(""))        //shared preference에 값이있다면 가져옴 -> 없는 경우에는 어떻게 처리하지 ㄷㄷ
        {
            userIdTextView.setText(id);
            userPwTextView.setText(pw);
        }
    }
}
