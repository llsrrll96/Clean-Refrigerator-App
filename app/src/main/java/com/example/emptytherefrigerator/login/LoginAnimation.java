package com.example.emptytherefrigerator.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.main.CheckConnect;

public class LoginAnimation extends AppCompatActivity
{
    Context context = this;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_beforelogin);

        imageView = (ImageView)findViewById(R.id.logoImageView);

        //뷰에 애니메이션 적용하기
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.appear_logo);
        imageView.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!CheckConnect.isNetworkAvailable(context))
                {
                    AlertDialog.Builder oDialog = new AlertDialog.Builder(context);

                    oDialog.setMessage("네트워크 연결 상태 확인 후 다시 시도해 주십시오")
                            .setTitle("네트워크 확인")
                            .setNeutralButton("예", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                                    finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                                    android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
                                }
                            })
                            .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                            .show();
                }

                //네트워크 연결 상태 확인 후 다시 시도해 주십시요
                else {
                    Intent intent = new Intent(getApplicationContext(), LoginView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}