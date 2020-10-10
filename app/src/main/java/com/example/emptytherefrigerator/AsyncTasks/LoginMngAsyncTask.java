package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;
import com.example.emptytherefrigerator.network.NetworkHandler;

import org.json.JSONObject;

public class LoginMngAsyncTask extends AsyncTask<String, String, String>
{
    @Override
    protected String doInBackground(String... strings)      //strings[0]은 login 뭐 이런식으로 요청 이름임
    {
        NetworkHandler.getInstance().connect(strings[0]);      //서버와 연결
        System.out.println(strings[0]);
        String result = NetworkHandler.communication(strings[1]);        //strings[1]은 보낼 데이터
        NetworkHandler.disconnect();

        return result;
    }
}
