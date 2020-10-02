package com.example.emptytherefrigerator.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

public class SettingView extends AppCompatActivity {

    Switch swAlarm;
    Button btnDelete, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        initailize();
    }

    void initailize()
    {
        swAlarm = (Switch)findViewById(R.id.swAlarm);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
    }

    void setAlarm()
    {
        swAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {

                }else
                {

                }
            }
        });
    }

    //회원탈퇴 버튼
    void deleteAccount(View view)
    {

    }

    //뒤로가기 버튼
    void backView(View view)
    {

    }

    //doinbackground 변수 타입, onProgress 타입, onPostExcute 타입
    class SettingAsyncTask extends AsyncTask<Integer, Integer, Integer> {//프리미티브 타입 x, 레퍼런스 타입으로
        int values = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        //ui 처리하면 안된다.
        @Override
        protected Integer doInBackground(Integer... integers) {//Integer 가 여러개일 수 있음


            return null;
        }

        //백그라운드 스레드에서 작업 처리 중에 프로그레스바 진행 상태 업데이트 같은  UI작업이 필요한 경우
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //doInBackground 종료후 리턴한 값을 파라메터로 받게 된다.
        @Override
        protected void onPostExecute(Integer integer) { //결과 가져와 실행
            super.onPostExecute(integer);

        }

    }
}