package com.example.emptytherefrigerator.userView.MyRecipe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeSearchAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.login.UserInfo;
import com.example.emptytherefrigerator.network.JsonParsing;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyRecipeListView extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<RecipeIn> list = new ArrayList<>();
    private MyRecipeListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_my_recipe_list);
        getRecipeList();
        initializeView();
        setListener();
    }

    public void initializeView()
    {
        recyclerView = findViewById(R.id.recipeRecycler);
        adapter = new MyRecipeListAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.myRecipeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my_recipe_list_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:     //뒤로가기 버튼이 눌렸을 때
                finish();
            case R.id.btnCreateRecipe:
                //레시피 등록 화면으로 넘어감
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setListener()
    {
//        btnCreateRecipe.setOnClickListener(new View.OnClickListener()
//        {
//           @Override
//           public void onClick(View v)
//           {
//               //레시피 등록 화면 넘기기
//           }
//
//        });
    }
    public void getRecipeList()        //내 레시피 서버에서 받아온다
    {
        RecipeSearchAsyncTask recipeSearch = new RecipeSearchAsyncTask();
        JSONObject data = new JSONObject();

        String jsonData="";
        try
        {
            data.accumulate("userId", UserInfo.getString(this,UserInfo.ID_KEY));
            jsonData = recipeSearch.execute("readUserRecipe", data.toString()).get();
            list = JsonParsing.parsingRecipe(jsonData);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
