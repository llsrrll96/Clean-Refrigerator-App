package com.example.emptytherefrigerator.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.network.JsonParsing;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//Main 화면 Home
//이 달의 레시피 검색 결과
public class MainSearchFragView extends Fragment
{
    private View view;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private CheckBox checkBox;
    private ArrayList<RecipeIn> list;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_search,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        searchView = view.findViewById(R.id.searchRecipe);
        checkBox = view.findViewById(R.id.checkBox);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(!checkBox.isChecked())
            searchView.setQueryHint("음식명을 검색해주세요");
        setListener();
        setSearchRecipe();             //검색창
        showRecipe();                  //이달의 레시피 창
    }

    public void setListener()
    {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                    searchView.setQueryHint("재료 여러개 입력시 쉼표로 구분");
                else
                    searchView.setQueryHint("음식명을 검색해주세요");
            }
        });
    }
    public void setSearchRecipe()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색어 버튼이 눌러졌을 때 이벤트 처리
                Toast.makeText(searchView.getContext(),"검색어: "+query,Toast.LENGTH_SHORT).show();

                intent = new Intent(getContext(), MainSearchResultView.class);

                if(checkBox.isChecked())                //식재료 검색일때
                    intent.putExtra("IS_CHECKED", true);
                else
                    intent.putExtra("IS_CHECKED",false);
                intent.putExtra("QUERY", query);
                getContext().startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //검색어가 변경되었을 때 이벤트 처리

                return false;
            }
        });
    }

    //이달의 레시피 창
    public void showRecipe()   //이달의 레시피 출력
    {
        ArrayList<RecipeIn> recipeList = new ArrayList<RecipeIn>();
        String recipeListData;
        try
        {
            recipeListData =  new RecipeMngAsyncTask().execute("readRecipe","123").get();//서버쪽에 따라 변경될 수 있음
            JSONArray jsonArray = new JSONArray(recipeListData);
            for (int i = 0 ; i< jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //test
                System.out.println("jsonObject: "+ jsonObject.get("title"));

                RecipeIn recipeIn = new RecipeIn();
                recipeIn.setRecipeInId(jsonObject.getInt("recipeInId"));
                recipeIn.setTitle(jsonObject.getString("title"));
                recipeIn.setUserId(jsonObject.getString("userId"));
                recipeIn.setIngredient(jsonObject.getString("ingredient"));
                recipeIn.setIngredientUnit(jsonObject.getString("ingredientUnit"));
                recipeIn.setRecipePerson(jsonObject.getInt("recipePerson"));
                recipeIn.setRecipeTime(jsonObject.getInt("recipeTime"));
                recipeIn.setContents(jsonObject.getString("contents"));
                recipeIn.setCommentCount(jsonObject.getInt("commentCount"));
                recipeIn.setLikeCount(jsonObject.getInt("likeCount"));
                recipeIn.setUploadDate(JsonParsing.dateToString(jsonObject.getString("uploadDate")));


                JSONArray jsonArrayImage = jsonObject.getJSONArray("recipeImageBytes");
                String[] recipeImageBytes = new String [jsonArrayImage.length()];

                System.out.println("jsonArrayImage.length(): "+ jsonArrayImage.length());

                for(int j= 0; j < jsonArrayImage.length(); j++)
                {
                    JSONObject jsonObjectImage = jsonArrayImage.getJSONObject(j);
                    recipeImageBytes[j] = jsonObjectImage.getString("recipeImageByte");
                }
                recipeIn.setRecipeImageByte(recipeImageBytes);
                recipeList.add(recipeIn);
            }
        }
        catch(Exception e)
        {
            System.out.println("에러: e.printStackTrace()");
            Toast.makeText(getContext(),"내용 없음", Toast.LENGTH_SHORT).show();
        }

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //상하
        recyclerView.addItemDecoration(new RecyclerDecoration(50)); //아이템 간격
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainSearchAdapter(recipeList));   //서버쪽 구현이 완료되면 이걸로 바꿀 예정

        //리사이클러뷰 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
