package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;

import com.example.emptytherefrigerator.network.NetworkHandler;

public class LikeMngAsyncTask extends AsyncTask<String, Void, String>
{
    protected String doInBackground(String... strings)
    {
        NetworkHandler.getInstance().connect(strings[0]);      //서버와 연결
        String jsonData = NetworkHandler.communication(strings[1]);
        NetworkHandler.disconnect();

        return jsonData;
    }
}
