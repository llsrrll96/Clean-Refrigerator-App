package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;
import com.example.emptytherefrigerator.network.NetworkHandler;


public class UserMngAsyncTask extends AsyncTask<String, Void, String>           //회원정보 등록, 수정, 삭제, 조회
{
    protected String doInBackground(String... strings)
    {
        NetworkHandler.getInstance().connect(strings[0]);                //서버와 연결
        String result = NetworkHandler.communication(strings[1]);        //strings[1]은 보낼 데이터
        NetworkHandler.disconnect();

        return result;
    }
}
