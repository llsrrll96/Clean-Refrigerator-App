package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;
import com.example.emptytherefrigerator.entity.Recipe;
import com.example.emptytherefrigerator.network.JsonParsing;
import com.example.emptytherefrigerator.network.NetworkHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//doinbackground 변수 타입, onProgress 타입, onPostExcute 타입
public class RecipeSearchAsyncTask extends AsyncTask<String, Void, ArrayList<Recipe>>
{
    protected ArrayList<Recipe> doInBackground(String... strings)
    {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        NetworkHandler.getInstance().connect(strings[0]);      //서버와 연결
        String jsonData = NetworkHandler.communication(null);        //요청만 하기 때문에 보낼 데이터는 없음, result는 null
        recipeList = JsonParsing.parsingRecipe(jsonData);
        NetworkHandler.disconnect();

        return recipeList;
    }
}
