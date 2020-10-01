package com.example.emptytherefrigerator.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Recipe;

import java.util.ArrayList;

//Main 화면 Home
//이 달의 레시피 검색 결과
public class MainSearchFragView extends Fragment
{
    private View view;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<Recipe> list;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_search,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        searchView = view.findViewById(R.id.searchRecipe);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setSearchRecipe();             //검색창
        setRecoRecipe();          //이달의 레시피 창
    }

    public void setSearchRecipe()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색어 버튼이 눌러졌을 때 이벤트 처리
                Toast.makeText(searchView.getContext(),"검색어: "+query,Toast.LENGTH_SHORT).show();

                intent = new Intent(getContext(), MainSearchResultView.class);
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

    public void setRecoRecipe()
    {
        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity()); //상하
        recyclerView.addItemDecoration(new RecyclerDecoration(50)); //아이템 간격
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainSearchAdapter(insertItemList()));

        //리사이클러뷰 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    //데이터 넣는 곳 , db에서 데이터 불러서 반복문으로 list에 저장 추가 , 비동기 백그라운드 사용 예정
    ArrayList insertItemList(){
        list = new ArrayList<>(  );
        Recipe recipeList1 = new Recipe("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/420px-PNG_transparency_demonstration_1.png"
                ,"제목1~~~",100, 12,"2020-10-01");
        Recipe recipeList2 = new Recipe("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/420px-PNG_transparency_demonstration_1.png"
                ,"제목1~~~",100, 12,"2020-10-01");
        Recipe recipeList3 = new Recipe("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/420px-PNG_transparency_demonstration_1.png"
                ,"제목1~~~",100, 12,"2020-10-01");

        list.add(recipeList1);
        list.add(recipeList2);
        list.add(recipeList3);
        return list;
    }
}
