package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.network.NetworkHandler;

import java.util.ArrayList;

//doinbackground 변수 타입, onProgress 타입, onPostExcute 타입
public class RecipeSearchAsyncTask extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... strings)
    {
        String functionType = strings[0];
        String data = strings[1];
        switch (functionType)
        {
            case "reqSearchRecipe" : break;
        }

        NetworkHandler.getInstance().connect(strings[0]);      //서버와 연결
        String result = NetworkHandler.communication(strings[1]);        //요청만 하기 때문에 보낼 데이터는 없음, result는 null
        NetworkHandler.disconnect();

        return result;
    }
}
